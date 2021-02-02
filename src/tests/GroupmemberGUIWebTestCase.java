package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import net.sourceforge.jwebunit.junit.WebTester;

class GroupmemberGUIWebTestCase {
	
	private WebTester tester;
	
	/**
	 * Create a new WebTester object that performs the test.
	 */
	@BeforeEach
	public void setUp() {
		tester = new WebTester();
		tester.setBaseUrl("http://localhost:8080/VR/index");
		System.out.println("Checked");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void defaultWebPage() {
		// Start testing for guestgui
		tester.beginAt("DefaultWebPage");
		System.out.println("triggered");
		// Check all components of the search form
		tester.assertTitleEquals("CalendarApplication - Welcome");
		tester.assertFormPresent();
		tester.assertTextPresent("Welcome to our Calendar Webapplication");
		tester.assertTextPresent("Assumed Groupmembership: ");
		tester.assertFormElementPresent("identity");
		tester.assertTextPresent("Departure Time");
		tester.assertFormElementPresent("departureTime");
		tester.assertTextPresent("Persons");
		tester.assertFormElementPresent("persons");
		tester.assertButtonPresent("SelectHOWebpage");

		// Submit the form with given parameters
		tester.setTextField("arrivalTime", "06/23/2016");
		tester.setTextField("departureTime", "06/24/2016");
		tester.setTextField("persons", "2");

		tester.clickButton("SelectHOWebpage");

		// Check the representation of the table for an empty result
		tester.assertTablePresent("availableHOs");
		String[][] tableHeadings = { { "#", "Street", "Town", "Capacity" } };
		tester.assertTableEquals("availableHOs", tableHeadings);
	}

}
