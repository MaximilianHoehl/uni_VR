package tests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;

import dbadapter.Configuration;
import net.sourceforge.jwebunit.junit.WebTester;

public class GUITest {
		
		private WebTester tester;

		/**
		 * Create a new WebTester object that performs the test.
		 */
		@Before
		public void prepare() {
			tester = new WebTester();
			tester.setBaseUrl("http://localhost:8080/VR");
		}
		
		//test for the complete GUI
		@Test
		public void testGUI() {
			tester.beginAt("index");

			// DefaultWebPage
			tester.assertTitleEquals("CalendarApplication - Welcome");
			tester.assertFormPresent();
			tester.assertTextPresent("Welcome to our Calendar Webapplication");
			tester.assertTextPresent("Assumed Identity");
			
			tester.assertTextPresent("Name");
			tester.assertFormElementPresent("name");
			tester.assertTextPresent("Description");
			tester.assertFormElementPresent("description");
			tester.assertTextPresent("Street");
			tester.assertFormElementPresent("street");
			tester.assertTextPresent("Postcode");
			tester.assertFormElementPresent("postcode");
			tester.assertTextPresent("Town");
			tester.assertFormElementPresent("town");
			tester.assertTextPresent("Country");
			tester.assertFormElementPresent("country");
			tester.assertTextPresent("From (date)");
			tester.assertFormElementPresent("startDate");
			tester.assertTextPresent("From (time)");
			tester.assertFormElementPresent("startTime");
			tester.assertTextPresent("To (date)");
			tester.assertFormElementPresent("endDate");
			tester.assertTextPresent("To (time)");
			tester.assertFormElementPresent("endTime");
			tester.assertTextPresent("Deadline");
			tester.assertFormElementPresent("deadline");
			tester.assertTextPresent("Planned Participants");
			tester.assertFormElementPresent("plannedParticipants");

			tester.setTextField("name", "UnitTest");
			tester.setTextField("description", "TestAppointment");
			tester.setTextField("street", "NoStreet");
			tester.setTextField("postcode", "3636");
			tester.setTextField("town", "Koeln");
			tester.setTextField("country", "Germany");
			tester.setTextField("startDate", "06:12:2023");
			tester.setTextField("startTime", "11:11:11");
			tester.setTextField("endDate", "06:12:2023");
			tester.setTextField("endTime", "12:12:12");
			tester.setTextField("deadline", "06:10:2021:00:00:00");
			tester.setTextField("plannedParticipants", "1:2:3");
			
			tester.assertButtonPresent("btn_createAppointment");
			tester.assertButtonPresent("btn_showCalendar");
			tester.assertButtonPresent("btn_pendingRequests");
			
			tester.clickButton("btn_createAppointment");
			
			//showCreateConfirmation
			tester.assertTextPresent("Confirm");
			tester.assertButtonPresent("btn_back");
			tester.clickButton("btn_back");
			
			//DefaultWebPage
			tester.clickButton("btn_createAppointment");
			
			//error
			tester.assertTextPresent("Form Inputfehler!");
			tester.assertButtonPresent("btn_back");
			tester.clickButton("btn_back");
			
			//DefaultWebPage
			tester.clickButton("btn_showCalendar");
			
			//SelectAWebPage
			tester.assertTablePresent("Calendar");
			String[] tableHeadings = { "#", "Name", "Description", "Location", "Start Time", "End Time" };
			tester.assertTextInTable("Calendar", tableHeadings);
			tester.assertButtonPresent("btn_backToMain");
			tester.clickButton("btn_backToMain");
			
			// DefaultWebPage
			tester.clickButton("btn_pendingRequests");
			
			//selectAWebPage
			tester.assertTextPresent("Select an Appointment for further action");
			tester.assertTextPresent("Unfinalized Appointments");
			
			tester.assertFormPresent("form_table_UA");
			tester.assertTablePresent("UnfinalizedAppointments");
			String[] tableUAHeadings = { "#", "Name", "Description", "Location", "Start Time", "End Time" };
			tester.assertTextInTable("UnfinalizedAppointments", tableUAHeadings);
			tester.assertFormElementPresent("clickedAppointmentID");
			
			tester.assertButtonPresent("btn_UA");
			tester.assertButtonPresent("btn_backToMain");
			tester.clickButton("btn_backToMain");
			
			// DefaultWebPage
			tester.clickButton("btn_pendingRequests");
			
			//selectAWebPage
			tester.clickButton("btn_UA");
			
			//appointment
			tester.assertTextPresent("Appointment Suggestions");
			
			tester.assertFormPresent("form_table_suggestions");
			tester.assertTablePresent("suggestions");
			String[] tableSuggHeadings = { "#", "StartTime", "EndTime", "Confirmations" };
			tester.assertTextInTable("suggestions", tableSuggHeadings);
			tester.assertButtonPresent("btn_selectSuggestion");
			
			tester.assertTextPresent("Suggest another date");
			
			tester.assertFormPresent("form_table_suggestions");
			tester.assertTextPresent("From (date)");
			tester.assertFormElementPresent("startDate");
			tester.assertTextPresent("From (time)");
			tester.assertFormElementPresent("startTime");
			tester.assertTextPresent("To (date)");
			tester.assertFormElementPresent("endDate");
			tester.assertTextPresent("To (time)");
			tester.assertFormElementPresent("endTime");
			tester.assertButtonPresent("btn_selectSuggestion");
			tester.assertButtonPresentWithText("New Suggestion");
			
			tester.clickButton("btn_selectSuggestion");
			
			//showselectstatus
			tester.assertTextPresent("You successfully selected an appointment-suggestion");
			tester.assertButtonPresent("btn_continue");
			
			tester.clickButton("btn_continue");
			
			// DefaultWebPage
			tester.clickButton("btn_pendingRequests");
						
			//selectAWebPage
			tester.clickButton("btn_UA");
			
			//appointment
			tester.setTextField("startDate", "23:11:2024");
			tester.setTextField("startTime", "13:30:00");
			tester.setTextField("endDate", "23:11:2024");
			tester.setTextField("endTime", "17:00:00");
			
			tester.clickButtonWithText("New Suggestion");
			
			//showSuggestStatus
			tester.assertTextPresent("You successfully inserted a new Suggestion");
			tester.assertButtonPresent("btn_continue");
			
			tester.clickButton("btn_continue");
			
			// DefaultWebPage
			tester.assertTextPresent("Welcome to our Calendar Webapplication");
			
		}
		
		@After
		public
		void tearDown() throws Exception {
			try {
				
				Connection connection = DriverManager.getConnection(
				"jdbc:" + Configuration.getType() + "://" + Configuration.getServer() + ":"
				+ Configuration.getPort() + "/" + Configuration.getDatabase(),
				Configuration.getUser(), Configuration.getPassword());
				
				String resetAppointments = "TRUNCATE TABLE appointments"; //Existing groupcalendar in DB is assumed since its not part of the task 
				String resetConfirmations = "TRUNCATE TABLE confirmations";
				String resetSuggestions = "TRUNCATE TABLE suggestions";
				String resetPlannedParticipants = "TRUNCATE TABLE plannedparticipants";
				PreparedStatement ps_rA = connection.prepareStatement(resetAppointments);
				PreparedStatement ps_rC = connection.prepareStatement(resetConfirmations);
				PreparedStatement ps_rS = connection.prepareStatement(resetSuggestions);
				PreparedStatement ps_rP = connection.prepareStatement(resetPlannedParticipants);
				ps_rA.executeUpdate();
				ps_rC.executeUpdate();
				ps_rS.executeUpdate();
				ps_rP.executeUpdate();
				System.out.println("SETUP: successfully executed RESET Opterations");
			}catch (Exception e) {
				
				e.printStackTrace();
			}
			
		}
	}



