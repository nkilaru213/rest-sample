package com.hoopladigital.guice;

import com.elmsoftware.env.EnvironmentSettingsModule;
import com.google.inject.Binder;
import com.google.inject.Provides;
import org.apache.derby.drda.NetworkServerControl;
import org.apache.ibatis.io.ResolverUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.Map;
import java.util.Set;

public class GuiceModule extends EnvironmentSettingsModule {

	private static final Logger log = LoggerFactory.getLogger(GuiceModule.class);

	@Override
	protected void configure(final Binder binder, final Map<String, String> properties) {

		for (final Class resourceClass : getClasses("com.hoopladigital.resource")) {
			log.debug("Registering resource {}", resourceClass);
			binder.bind(resourceClass);
		}

//		for (final Class converterClass : getClasses("com.hoopladigital.converter")) {
//			log.debug("Registering converter {}", converterClass);
//			binder.bind(converterClass);
//		}
//		for (final Class securityClass : getClasses("com.hoopladigital.security")) {
//			log.debug("Registering security interceptor {}", securityClass);
//			binder.bind(securityClass);
//		}

	}

	@Provides
	public NetworkServerControl networkServerControl() throws Exception {

		// create a temporary log file for derby
		final File logFile = File.createTempFile("derby-temp", ".log");
		log.debug("temp file for derby logging: {}", logFile.getAbsolutePath());

		// get the temporary directory for derby
		final File tempDir = logFile.getParentFile();
		System.setProperty("derby.system.home", tempDir.getAbsolutePath());
		log.debug("derby home directory: {}", tempDir.getAbsolutePath());

		// address and port for derby
		final InetAddress address = InetAddress.getByAddress(new byte[]{127, 0, 0, 1});
		final int port = 18080;
		log.debug("starting derby at {}:{}", address.toString(), port);

		// create the derby service and start it
		final NetworkServerControl networkServerControl = new NetworkServerControl(address, port);

		log.info("starting derby");
		networkServerControl.start(new PrintWriter(logFile));
		log.info("started derby");

		return networkServerControl;

	}

	private Set<Class<? extends Class>> getClasses(final String packageName) {
		return new ResolverUtil<Class>().find(new ResolverUtil.IsA(Object.class), packageName).getClasses();
	}

}
