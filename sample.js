const express = require("express");
const app = express();
const PORT = 8080;

app.get("/", (req, res) => {
  const code = req.query.code;
  const state = req.query.state;

  console.log("Authorization code:", code);
  console.log("State:", state);

  res.send("Code received, check server logs.");
});

app.listen(PORT, () => {
  console.log(`Listening at https://localhost:${PORT}`);
});


# Generate a private key
openssl genrsa -out localhost-key.pem 2048

# Generate a certificate signing request (CSR)
openssl req -new -key localhost-key.pem -out localhost.csr

# Generate a self-signed certificate valid for 365 days
openssl x509 -req -in localhost.csr -signkey localhost-key.pem -out localhost-cert.pem -days 365

When asked for details (Country, Organization, Common Name, etc.),
use localhost as the Common Name (CN) — that’s critical.

You’ll now have two files:

localhost-key.pem
localhost-cert.pem


const fs = require("fs");
const https = require("https");
const express = require("express");

const app = express();
const PORT = 8080;

// Capture redirect
app.get("/", (req, res) => {
  const code = req.query.code;
  const state = req.query.state;
  if (code) {
    console.log("✅ Authorization code:", code);
    console.log("✅ State:", state);
    res.send("Code received, check terminal logs!");
  } else {
    res.send("No code found.");
  }
});

// Load cert + key
const options = {
  key: fs.readFileSync("localhost-key.pem"),
  cert: fs.readFileSync("localhost-cert.pem")
};

https.createServer(options, app).listen(PORT, () => {
  console.log(`🚀 HTTPS Server running at https://localhost:${PORT}`);
});



node server.js



// Retrieve PKCE verifier (you must have saved it in sessionStorage or somewhere persistent)
  const codeVerifier = "89"; // replace with your generated one

  const tokenUrl = "token.oauth2";

  try {
    const body = new URLSearchParams({
      grant_type: "authorization_code",
      code: code,
      redirect_uri: "https://localhost:8080",
      client_id: "124RT",  // replace with your actual client_id
      code_verifier: codeVerifier
    });

    const tokenResponse = await fetch(tokenUrl, {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      body: body.toString()
    });

    const tokenData = await tokenResponse.json();
    console.log("🔑 Token Response:", tokenData);

    res.json(tokenData);
  } catch (err) {
    console.error("❌ Error exchanging code:", err);
    res.status(500).send("Token exchange failed");
  }

