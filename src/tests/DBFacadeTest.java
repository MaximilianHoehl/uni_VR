package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Timestamp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import datatypes.Appointment;
import dbadapter.Configuration;
import dbadapter.HolidayOffer;

class DBFacadeTest {
	
	private Appointment testAp;
	
	@BeforeEach
	void setUp() throws Exception {
		
		try (Connection connection = DriverManager.getConnection(
						"jdbc:" + Configuration.getType() + "://" + Configuration.getServer() + ":"
								+ Configuration.getPort() + "/" + "testDB",
								Configuration.getUser(), Configuration.getPassword())) {
		
			
			String statement = "";	
		}catch(Exception e) {
			
			
			e.printStackTrace();
		}
	}
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}
	
	@Test
	void testAddAppointment() {
		
		int id = 1; 
		int cid = 0;
		String name = "testName";
		String desc = "testDescription";
		String locationString = "testStreet, testTown, testPostCode, testCountry";
		Timestamp startTime = Timestamp.valueOf("2020-02-01 00:00:00");
		Timestamp endTime = Timestamp.valueOf("2020-02-02 00:00:00");
		Timestamp deadline = Timestamp.valueOf("2020-01-02 00:00:00");
		
		int dbContentSize = 1;
		
		
		
	}

}
