package com.hoopladigital.derby;

import com.hoopladigital.test.AbstractTest;
import org.apache.derby.drda.NetworkServerControl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class DerbyHelperTest extends AbstractTest {

	private final DerbyHelper derbyHelper = new DerbyHelper();

	@Test
	public void should_not_init_db_if_not_requested() throws Exception {
		// setup
		// run test
		derbyHelper.init(false);
		// verify mocks / capture values
		// assert results
	}

	@Test
	public void should_init_db_if_requested() throws Exception {
		// setup
		// run test
		derbyHelper.init(true);
		// verify mocks / capture values
		// assert results
	}

	@Test
	public void should_stop_db() throws Exception {

		// setup
		final NetworkServerControl networkServerControl = mock(NetworkServerControl.class);

		// run test
		derbyHelper.destroy(networkServerControl);

		// verify mocks / capture values
		verify(networkServerControl).shutdown();

		// assert results

	}

	@Test
	public void should_explode_if_unable_to_stop_db() throws Exception {

		// setup
		final NetworkServerControl networkServerControl = mock(NetworkServerControl.class);
		doThrow(new RuntimeException("snapped-the-frame"))
				.when(networkServerControl).shutdown();

		// run test
		try {
			derbyHelper.destroy(networkServerControl);
			fail("this should have exploded by now");
		} catch (final Exception e) {
			// expected
			assertEquals("snapped-the-frame", e.getLocalizedMessage());
		}

		// verify mocks / capture values
		verify(networkServerControl).shutdown();

		// assert results

	}

}
