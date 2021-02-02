package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import net.sourceforge.jwebunit.junit.WebTester;

public class DefaultGUITest {
		
		private WebTester tester;

		/**
		 * Create a new WebTester object that performs the test.
		 */
		@Before
		public void prepare() {
			tester = new WebTester();
			tester.setBaseUrl("http://localhost:8080/CA/");
		}

		@Test
		public void testShowDefaultWebGui() {
			// Start testing for defaultPage
			tester.beginAt("index");

			// Check all components of the search form
			tester.assertTitleEquals("My Calendar");
			tester.assertFormPresent();
			tester.assertTextPresent("Assumed Groupmembership");
			tester.assertTextPresent("Assumed Groupmembership");
			
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
			tester.assertTextPresent("From(date)");
			tester.assertFormElementPresent("startDate");
			tester.assertTextPresent("From(time)");
			tester.assertFormElementPresent("startTime");
			tester.assertTextPresent("To(date)");
			tester.assertFormElementPresent("endDate");
			tester.assertTextPresent("To(time)");
			tester.assertFormElementPresent("endTime");
			tester.assertTextPresent("Deadline");
			tester.assertFormElementPresent("deadline");
			
			
			tester.assertButtonPresent("createAppointment");

			// Submit the form with given parameters
			tester.setTextField("name", "");
			tester.setTextField("description", "");
			tester.setTextField("Street", "Krefelderstr");
			tester.setTextField("postcode", "3636");
			tester.setTextField("town", "Koeln");
			tester.setTextField("country", "Germany");
			tester.setTextField("startDate", "06:23:2021");
			tester.setTextField("startTime", "11:11:11");
			tester.setTextField("endDate", "06:24:2021");
			tester.setTextField("endTime", "12:12:12");
			tester.setTextField("Deadline", "06:24:2021");


			tester.clickButton("createAppointment");

			// Check the representation of the table for an empty result
			tester.assertTablePresent("Calendar1");
			String[][] tableHeadings = { { "cid", "Name", "Description", "Appointment" } };
			tester.assertTableEquals("Calendar1", tableHeadings);
		
		}
	}



