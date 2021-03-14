package tests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;

import dbadapter.Configuration;
import net.sourceforge.jwebunit.junit.WebTester;

public class DefaultGUITest {
		
		private WebTester tester;

		/**
		 * Create a new WebTester object that performs the test.
		 */
		@Before
		public void prepare() {
			tester = new WebTester();
			tester.setBaseUrl("http://localhost:8080/VR");
		}

		@Test
		public void testShowDefaultWebGui() {
			// Start testing for defaultPage
			tester.beginAt("index");

			// Check all components of the search form
			tester.assertTitleEquals("CalendarApplication - Welcome");
			tester.assertFormPresent();
			tester.assertTextPresent("Assumed Identity");
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
			tester.assertFormElementPresent("deadline");
			
			
			tester.assertButtonPresent("btn_createAppointment");

			// Submit the form with given parameters
			tester.setTextField("name", "");
			tester.setTextField("description", "");
			tester.setTextField("street", "Krefelderstr");
			tester.setTextField("postcode", "3636");
			tester.setTextField("town", "Koeln");
			tester.setTextField("country", "Germany");
			tester.setTextField("startDate", "06:23:2021");
			tester.setTextField("startTime", "11:11:11");
			tester.setTextField("endDate", "06:24:2021");
			tester.setTextField("endTime", "12:12:12");
			tester.setTextField("deadline", "06:24:2021");

			tester.clickButton("btn_showCalendar");
			
			tester.assertTablePresent("Calendar");
			String[] tableHeadings = { "#", "Name", "Description", "Location", "Start Time", "End Time" };
			tester.assertTextInTable("Calendar", tableHeadings);
			tester.assertButtonPresent("btn_backToMain");
			tester.clickButton("btn_backToMain");
			
			tester.assertButtonPresent("btn_createAppointment");

			
		
		}
		
		@AfterEach
		protected
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



