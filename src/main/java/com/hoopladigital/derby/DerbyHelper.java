package com.hoopladigital.derby;

import org.apache.derby.drda.NetworkServerControl;
import org.apache.derby.tools.ij;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

public class DerbyHelper {

	private static final Logger log = LoggerFactory.getLogger(DerbyHelper.class);

	public void init(final boolean initDb) throws Exception {

		if (initDb) {
			log.info("running db-init.sql");
			final URL scriptUrl = Thread.currentThread().getContextClassLoader().getResource("db-init.sql");
			ij.main(new String[]{scriptUrl.getFile()});
		}

	}

	public void destroy(final NetworkServerControl networkServerControl) throws Exception {

		log.info("stopping derby");
		networkServerControl.shutdown();
		log.info("stopped derby");

	}

}
