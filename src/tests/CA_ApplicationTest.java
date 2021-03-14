package testing;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

import org.junit.Test;

import application.CA_Application;
import application.VRApplication;
import datatypes.Appointment;
import datatypes.Suggestion;
import dbadapter.DBFacade;
import junit.framework.TestCase;

class CA_ApplicationTestCase extends TestCase{
	
	public CA_ApplicationTestCase() {
		super();
	}

	@Test
	void test_GetCalendarInfos() {
		DBFacade stub = mock(DBFacade.class);
		DBFacade.setInstance(stub);
		
		CA_Application.createInstance().getCalendarInfos(0);
		
		verify(stub, times(1)).fetchCalendarInfos(0);
	}
	@Test
	void test_MakeAppointmentRequest() {
		DBFacade stub = mock(DBFacade.class);
		DBFacade.setInstance(stub);
		
		CA_Application.createInstance().makeAppointmentRequest(0, null, null, null, null, null, null, null);
		
		verify(stub, times(1)).addAppointment(0, null, null, null, null, null, null, null);
	}
	@Test
	void test_selectingDate() {
		DBFacade stub = mock(DBFacade.class);
		DBFacade.setInstance(stub);
		
		CA_Application.createInstance().selectingDate(0, 0, 0);
		
		verify(stub, times(1)).setChosenDate(0, 0, 0);
	}
	@Test
	void test_suggestingDate() {
		DBFacade stub = mock(DBFacade.class);
		DBFacade.setInstance(stub);
		
		CA_Application.createInstance().suggestingDate(0, 0, null, null);
		verify(stub, times(1)).saveSuggestion(0, 0, null, null);
	}
	@Test
	void test_getSuggestions() {
		DBFacade stub = mock(DBFacade.class);
		DBFacade.setInstance(stub);
		
		CA_Application.createInstance().getSuggestions(0);
		verify(stub, times(1)).fetchSuggestions(0);
	}
	@Test
	void test_getUnfinalizedAppointments() {
		DBFacade stub = mock(DBFacade.class);
		DBFacade.setInstance(stub);
		
		CA_Application.createInstance().getUnfinalizedAppointments(0);
		verify(stub, times(1)).fetchSuggestions(0);
	}
	@Test
	void test_checkFinalization() {
		DBFacade stub = mock(DBFacade.class);
		DBFacade.setInstance(stub);
		
		CA_Application.createInstance().checkFinalization();
		verify(stub, times(1)).finalizeAppointment();
	}
}
