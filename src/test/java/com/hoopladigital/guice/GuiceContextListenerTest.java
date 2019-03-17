package com.hoopladigital.guice;

import com.google.inject.Injector;
import com.hoopladigital.derby.DerbyHelper;
import com.hoopladigital.test.AbstractTest;
import org.apache.derby.drda.NetworkServerControl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static com.hoopladigital.test.MockHelper.allDeclaredMocks;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GuiceContextListenerTest extends AbstractTest {

	private GuiceContextListener guiceContextListener;

	@Mock
	private Injector injector;

	@Mock
	private NetworkServerControl networkServerControl;

	@Mock
	private DerbyHelper derbyHelper;


	@Before
	public void beforeGuiceContextListenerTest() throws Exception {
		guiceContextListener = new GuiceContextListener();
		beanTestHelper.setPrivateField(guiceContextListener, "derbyHelper", derbyHelper);
		beanTestHelper.setPrivateField(guiceContextListener, "networkServerControl", networkServerControl);
	}

	@Test
	public void should_try_to_initialize_db() throws Exception {

		// setup
		beanTestHelper.setPrivateField(guiceContextListener, "initDb", "true");

		// run test
		guiceContextListener.withInjector(injector);

		// verify mocks / capture values
		verify(injector).injectMembers(guiceContextListener);
		verify(derbyHelper).init(true);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results

	}

	@Test
	public void should_explode_if_unable_to_initialize_db() throws Exception {

		// setup
		beanTestHelper.setPrivateField(guiceContextListener, "initDb", "true");
		doThrow(new RuntimeException("snapped-the-frame"))
				.when(derbyHelper).init(true);

		// run test
		try {
			guiceContextListener.withInjector(injector);
			fail("this should have exploded already");
		} catch (final Exception e) {
			assertEquals("snapped-the-frame", e.getCause().getLocalizedMessage());
		}
		// verify mocks / capture values
		verify(injector).injectMembers(guiceContextListener);
		verify(derbyHelper).init(true);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results

	}

	@Test
	public void should_stop_derby() throws Exception {
		// setup
		// run test
		guiceContextListener.contextDestroyed(null);
		// verify mocks / capture values
		verify(derbyHelper).destroy(networkServerControl);
		verifyNoMoreInteractions(allDeclaredMocks(this));
		// assert results
	}

	@Test
	public void should_explode_if_unable_to_stop_derby() throws Exception {

		// setup
		doThrow(new RuntimeException("snapped-the-frame"))
				.when(derbyHelper).destroy(networkServerControl);

		// run test
		try {
			guiceContextListener.contextDestroyed(null);
			fail("this should have exploded already");
		} catch (final Exception e) {
			assertEquals("snapped-the-frame", e.getCause().getLocalizedMessage());
		}

		// verify mocks / capture values
		verify(derbyHelper).destroy(networkServerControl);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results

	}

}
