package testing;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import application.CA_Application;
import application.VRApplication;
import dbadapter.DBFacade;
import junit.framework.TestCase;

class CA_ApplicationTestCase extends TestCase{
	
	public CA_ApplicationTestCase() {
		super();
	}

	@Test
	void testGetCalendarInfos() {
		DBFacade stub = mock(DBFacade.class);
		DBFacade.setInstance(stub);
		
		CA_Application.createInstance().getCalendarInfos(null, null, null, null, null);
		
		verify(stub, times(1)).fetchCalendarInfos(null, null, null, null, null);
	}
	@Test
	void testMakeAppointmentRequest() {
		DBFacade stub = mock(DBFacade.class);
		DBFacade.setInstance(stub);
		
		CA_Application.createInstance().makeAppointmentRequest(null, null, null, null, null, null, null);
		
		verify(stub, times(1)).addAppointment(null, null, null, null, null, null, null);
	}

}
