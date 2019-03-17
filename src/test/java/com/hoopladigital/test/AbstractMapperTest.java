package com.hoopladigital.test;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.hoopladigital.derby.DerbyHelper;
import com.hoopladigital.guice.GuiceModule;
import com.hoopladigital.guice.SampleMyBatisModule;
import org.apache.derby.drda.NetworkServerControl;
import org.apache.ibatis.session.SqlSessionManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.DriverManager;

public abstract class AbstractMapperTest extends AbstractTest {

	private static final Logger log = LoggerFactory.getLogger(AbstractMapperTest.class);

	private static final Injector injector = Guice.createInjector(
			new GuiceModule(),
			new SampleMyBatisModule()
	);

	@Inject
	private static DerbyHelper derbyHelper;
	@Inject
	private static NetworkServerControl networkServerControl;

	@Inject
	private SqlSessionManager sqlSessionManager;

	@BeforeClass
	public static void beforeAbstractMapperTestClass() throws Exception {
		derbyHelper = injector.getInstance(DerbyHelper.class);
		networkServerControl = injector.getInstance(NetworkServerControl.class);
		derbyHelper.init(true);
	}

	@Before
	public void beforeAbstractMapperTest() throws Exception {
		DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
		injector.injectMembers(this);
		sqlSessionManager.startManagedSession();
	}

	@After
	public void afterAbstractMapperTest() {
		sqlSessionManager.rollback(true);
		sqlSessionManager.close();
	}

	@AfterClass
	public static void afterAbstractMapperTestClass(){
		try {
			derbyHelper.destroy(networkServerControl);
		} catch (final Exception e) {
			// this can fail sometimes...if it does, swallow it.
			log.warn(e.toString());
		}
	}

}
