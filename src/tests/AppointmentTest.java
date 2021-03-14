package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.sourceforge.jwebunit.junit.WebTester;

class AppointmentTest {

	private WebTester tester;

	/**
	 * Create a new WebTester object that performs the test.
	 */
	@Before
	public void prepare() {
		tester = new WebTester();
		tester.setBaseUrl("http://localhost:8080/VR"); ///appointment?clickedAppointmentID=0
	}

	@Test
	public void testAppointment() {
		tester.gotoPage("http://localhost:8080/VR/appointment?clickedAppointmentID=0");
		//tester.beginAt("");
		// Check all components of the search form
		tester.assertTitleEquals("CalendarApplication - Welcome");
		
		tester.assertTextPresent("Appointment Suggestions");
		tester.assertTablePresent("suggestions");
		String[][] tableHeadings = { { "#", "StartTime", "EndTime", "Confirmations" } };
		tester.assertTableEquals("suggestions", tableHeadings);
		
		tester.assertTextPresent("Suggest another date");
		
		tester.assertTextPresent("From(date):");
		tester.assertFormElementPresent("startDate");
		
        tester.assertTextPresent("From(time):");
		tester.assertFormElementPresent("startTime");
		
		tester.assertTextPresent("To(date):");
		tester.assertFormElementPresent("endDate");
		
		tester.assertTextPresent("To(time):");
		tester.assertFormElementPresent("endTime");

		// Submit the form with given parameters
		tester.setTextField("startDate", "06:23:2021");
		tester.setTextField("startTime", "11:11:11");
		tester.setTextField("endDate", "06:24:2021");
		tester.setTextField("endTime", "12:12:12");
		
		tester.assertButtonPresent("New Suggestion");
		tester.clickButton("New Suggestion");

		// Check the representation of the table for an empty result
		//tester.assertTablePresent("createSuggestions");
		//String[][] tableHeadings = { { "#", "StartTime", "EndTime", "Confirmation" } };
		//tester.assertTableEquals("createSuggestions", tableHeadings);
	
	}

}
