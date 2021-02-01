package tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.VRApplication;
import dbadapter.DBFacade;
import junit.framework.TestCase;

class CA_ApplicationTestCase extends TestCase{
	
	public CA_ApplicationTestCase() {
		super();
	}
	@BeforeEach
	protected void setUp() throws Exception {
	}

	@AfterEach
	protected void tearDown() throws Exception {
	}

	@Test
	void test() {
		DBFacade stub = mock(DBFacade.class);
		DBFacade.setInstance(stub);
		
		VRApplication.getInstance().checkPayment();
		
		verify(stub, times(1)).setAvailableHolidayOffer();
	}

}
