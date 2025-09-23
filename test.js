<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>OIDC PKCE Demo</title>
  <style>
    body { font-family: Arial, sans-serif; margin: 30px; }
    button {
      padding: 10px 20px;
      margin: 5px;
      background: #0073bb;
      color: white;
      border: none;
      border-radius: 6px;
      cursor: pointer;
    }
    button:hover { background: #005f9e; }
    pre {
      background: #f4f4f4;
      padding: 15px;
      border-radius: 6px;
      white-space: pre-wrap;
      word-wrap: break-word;
    }
  </style>
</head>
<body>
  <h2>OIDC PKCE Flow Example</h2>
  <button id="loginBtn">Login with Citi OIDC</button>
  <button id="logoutBtn" style="display:none;">Logout</button>
  <pre id="output"></pre>

  <script>
    // ==== Config ====
    const AUTH_URL = "";
    const TOKEN_URL = "";
    const USERINFO_URL = "";
    const LOGOUT_URL = ""; // check .well-known config
    const CLIENT_ID = "124RT"; // replace with your actual Client ID
    const REDIRECT_URI = ""; // must match registered redirect
    const SCOPE = "openid profile";

    // ==== Helpers ====
    function generateRandomString(length) {
      const charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-._~";
      let result = "";
      for (let i = 0; i < length; i++) {
        result += charset.charAt(Math.floor(Math.random() * charset.length));
      }
      return result;
    }

    function base64urlencode(arrayBuffer) {
      let binary = '';
      const bytes = new Uint8Array(arrayBuffer);
      for (let i = 0; i < bytes.byteLength; i++) {
        binary += String.fromCharCode(bytes[i]);
      }
      return btoa(binary).replace(/\+/g, "-").replace(/\//g, "_").replace(/=+$/, "");
    }

    async function sha256(plain) {
      const encoder = new TextEncoder();
      const data = encoder.encode(plain);
      return await crypto.subtle.digest("SHA-256", data);
    }

    async function createCodeChallenge(verifier) {
      const hashed = await sha256(verifier);
      return base64urlencode(hashed);
    }

    // ==== Step 1: Login (redirect to IdP) ====
    async function login() {
      const state = generateRandomString(16);
      const verifier = generateRandomString(64);
      const challenge = await createCodeChallenge(verifier);

      sessionStorage.setItem("pkce_state", state);
      sessionStorage.setItem("pkce_verifier", verifier);

      const url = `${AUTH_URL}?response_type=code&client_id=${encodeURIComponent(CLIENT_ID)}&redirect_uri=${encodeURIComponent(REDIRECT_URI)}&scope=${encodeURIComponent(SCOPE)}&state=${state}&code_challenge=${challenge}&code_challenge_method=S256`;

      window.location = url;
    }

    document.getElementById("loginBtn").addEventListener("click", login);

    // ==== Step 2: Handle Redirect ====
    async function handleRedirect() {
      const params = new URLSearchParams(window.location.search);
      const code = params.get("code");
      const state = params.get("state");
      if (!code) return;

      const savedState = sessionStorage.getItem("pkce_state");
      const verifier = sessionStorage.getItem("pkce_verifier");

      if (state !== savedState) {
        document.getElementById("output").textContent = "❌ Invalid state";
        return;
      }

      // ==== Step 3: Exchange Code for Tokens (POST) ====
      const body = new URLSearchParams({
        grant_type: "authorization_code",
        code: code,
        redirect_uri: REDIRECT_URI,
        client_id: CLIENT_ID,
        code_verifier: verifier
      });

      const tokenResponse = await fetch(TOKEN_URL, {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: body.toString()
      });

      const tokenData = await tokenResponse.json();
      console.log("Token Response:", tokenData);

      if (tokenData.error) {
        document.getElementById("output").textContent = "❌ Token Error:\n" + JSON.stringify(tokenData, null, 2);
        return;
      }

      sessionStorage.setItem("access_token", tokenData.access_token);
      sessionStorage.setItem("id_token", tokenData.id_token);

      // ==== Step 4: Use Access Token to Call UserInfo ====
      const userResponse = await fetch(USERINFO_URL, {
        headers: { "Authorization": `Bearer ${tokenData.access_token}` }
      });

      const userInfo = await userResponse.json();
      document.getElementById("output").textContent =
        "✅ User Info:\n" + JSON.stringify(userInfo, null, 2) +
        "\n\n🔑 Access Token:\n" + tokenData.access_token;

      // Toggle buttons
      document.getElementById("loginBtn").style.display = "none";
      document.getElementById("logoutBtn").style.display = "inline-block";

      // Clean query params
      window.history.replaceState({}, document.title, REDIRECT_URI);
    }

    handleRedirect();

    // ==== Step 5: Logout ====
    function logout() {
      sessionStorage.clear();
      document.getElementById("output").textContent = "👋 Logged out";
      document.getElementById("loginBtn").style.display = "inline-block";
      document.getElementById("logoutBtn").style.display = "none";

      // Redirect to IdP logout (if supported)
      const logoutUrl = `${LOGOUT_URL}?client_id=${CLIENT_ID}&post_logout_redirect_uri=${encodeURIComponent(REDIRECT_URI)}`;
      window.location = logoutUrl;
    }

    document.getElementById("logoutBtn").addEventListener("click", logout);
  </script>
</body>
</html>
