package com.hoopladigital.guice;

import com.google.inject.Injector;
import com.hoopladigital.derby.DerbyHelper;
import org.apache.derby.drda.NetworkServerControl;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContextEvent;

public class GuiceContextListener extends GuiceResteasyBootstrapServletContextListener {

	private static final Logger log = LoggerFactory.getLogger(GuiceContextListener.class);

	@Inject @Named("initialize_database")
	private String initDb;
	@Inject
	private NetworkServerControl networkServerControl;
	@Inject
	private DerbyHelper derbyHelper;

	protected void withInjector(final Injector injector) {
		super.withInjector(injector);
		log.info("creating context");
		injector.injectMembers(this);
		try {
			derbyHelper.init("true".equals(this.initDb));
		} catch (final Exception e) {
			throw new RuntimeException(e.toString(), e);
		}
	}

	public void contextDestroyed(final ServletContextEvent servletContextEvent) {
		log.info("destroying context");
		// stop the derby service
		try {
			derbyHelper.destroy(networkServerControl);
		} catch (final Exception e) {
			throw new RuntimeException(e.toString(), e);
		}
	}

}
