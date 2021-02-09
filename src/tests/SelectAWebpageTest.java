package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.sourceforge.jwebunit.junit.WebTester;

class SelectAWebpageTest {

	private WebTester tester;

	/**
	 * Create a new WebTester object that performs the test.
	 */
	@Before
	public void prepare() {
		tester = new WebTester();
		tester.setBaseUrl("http://localhost:8080/VR/selectAWebPage");
	}
	@Test
	public void selectAWebPageTest() {
		// Start testing for defaultPage
		tester.beginAt("index");

		tester.assertButtonPresent("btn_backToMain");
		tester.clickButton("btn_backToMain");

		// Check the representation of the table for an empty result
		tester.assertTablePresent("UnfinalizedAppointments");
		String[][] tableHeadings = { { "#", "Name", "Description", "Location","Start Time ","End Time" } };
		tester.assertTableEquals("UnfinalizedAppointments", tableHeadings);
	
	}
}
