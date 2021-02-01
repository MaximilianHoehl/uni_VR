package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import datatypes.Appointment;
import datatypes.GroupCalendar;
import datatypes.LocationData;
import datatypes.TimeData;
import dbadapter.Configuration;
import dbadapter.DBFacade;
import junit.framework.TestCase;

class DBFacadeTest extends TestCase {
	
	@BeforeEach
	protected
	void setUp() throws Exception {		//Setup system with generic value to get to a state to test the problem
		//General Setup
		System.out.println("Start Setup");
		try (Connection connection = DriverManager.getConnection(
						"jdbc:" + Configuration.getType() + "://" + Configuration.getServer() + ":"
								+ Configuration.getPort() + "/" + Configuration.getDatabase(),
								Configuration.getUser(), Configuration.getPassword())) {
			
			System.out.println("SETUP: DB successfully connected");
			//Reset Tablecontent and add one single item for testing
			try {
				String reset = "DELETE FROM appointments"; //Existing groupcalendar in DB is assumed since its not part of the task 
				PreparedStatement ps_reset = connection.prepareStatement(reset);
				ps_reset.executeUpdate();
				System.out.println("SETUP: successfully executed RESET Query");
				
				//Add one single entry to test the fetchCalendarInfo method. Will be ignored in testAddAppointment
				String sqlInsertAppointment = "INSERT INTO appointments (cid,name,description,location,startTime,endTime,deadline,finalized)"
						+ " VALUES (?,?,?,?,?,?,?,?)";
				PreparedStatement ps_IA = connection.prepareStatement(sqlInsertAppointment);
				ps_IA.setInt(1, 1);
				ps_IA.setString(2, "presetName");
				ps_IA.setString(3, "presetDesc");
				ps_IA.setString(4, "testStreet 0000 testTown testCountry");
				ps_IA.setTimestamp(5, Timestamp.valueOf("2021-01-01 13:00:00"));
				ps_IA.setTimestamp(6, Timestamp.valueOf("2021-01-01 15:00:00"));
				ps_IA.setTimestamp(7, Timestamp.valueOf("2020-12-25 00:00:00"));
				ps_IA.setBoolean(8, false);
				ps_IA.executeUpdate();
				System.out.println("SETUP: Successfully added debugIntem to DB");
				System.out.println("SETUP COMPLETE");
				
			}catch(Exception e) {
				System.out.println("TEST: Block2Fail");
				e.printStackTrace();
			}
			
		}catch(Exception e) {
			System.out.println("TEST: Block2Fail");
			e.printStackTrace();
		}
	}
	
	@AfterEach
	protected
	void tearDown() throws Exception {
		try {
			
			Connection connection = DriverManager.getConnection(
			"jdbc:" + Configuration.getType() + "://" + Configuration.getServer() + ":"
			+ Configuration.getPort() + "/" + Configuration.getDatabase(),
			Configuration.getUser(), Configuration.getPassword());
			
			String reset = "DELETE FROM appointments"; //Existing groupcalendar in DB is assumed since its not part of the task 
			PreparedStatement ps_reset = connection.prepareStatement(reset);
			ps_reset.executeUpdate();
			System.out.println("SETUP: successfully executed RESET Query");
		}catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	@Test
	void testAddAppointment() {		//Setup with problemspecific values
		System.out.println("START TEST: addAppointment");
		
		//first get dbconnection to be able to test if function stored everything well
		try (Connection connection = DriverManager.getConnection(
				"jdbc:" + Configuration.getType() + "://" + Configuration.getServer() + ":"
						+ Configuration.getPort() + "/" + Configuration.getDatabase(),
						Configuration.getUser(), Configuration.getPassword())) {
			
			System.out.println("TestAddAppiontment: successfully created Connection to db");
			
			//Setup Function-inputdata
			int cid = 1;
			String name = "testName";
			String desc = "testDescription";
			LocationData locationString = new LocationData("testStreet 4444 testTown testCountry");
			TimeData startTime = new TimeData(Timestamp.valueOf("2022-02-01 00:00:00"));
			TimeData endTime = new TimeData(Timestamp.valueOf("2022-02-02 00:00:00"));
			TimeData deadline = new TimeData(Timestamp.valueOf("2022-01-02 00:00:00"));
			
			//Execute the function 
			DBFacade fixture = DBFacade.getInstance();
			Boolean success = fixture.addAppointment(cid, name, desc, locationString, deadline, startTime, endTime);
			System.out.println("TestAddAppointment: executed method");
			//Test if there are no technical errors
			assertTrue(success);
			
			//Test of exaclty one value was stored in DB
			String sqlCountContent = "SELECT COUNT(*) FROM appointments";
			PreparedStatement ps_CountContent = connection.prepareStatement(sqlCountContent);
			ResultSet rs = ps_CountContent.executeQuery();
			while(rs.next()) {
				int fetchedDBContentCount = rs.getInt("count(*)");
				assertTrue(fetchedDBContentCount == 2); //We already add one item in setup() to test the testFetchCalendarInfos method later on
			}
			
			
			//Test if the data was correctly stored in db
			String sqlSelect = "SELECT * FROM appointments";
			PreparedStatement ps_selectAll = connection.prepareStatement(sqlSelect);
			ResultSet data = ps_selectAll.executeQuery();
			while(data.next()) {
				if(data.getString("name").equals("presetName")) {//ignore the first entry from Setup
					continue;
				}
				assertTrue(data.getInt("cid") == 1);
				assertEquals(data.getString("name"), "testName");
				assertEquals(data.getString("description"), "testDescription");
				assertEquals(data.getString("location"), "testStreet 4444 testTown testCountry");
				assertEquals(data.getTimestamp("startTime"), Timestamp.valueOf("2022-02-01 00:00:00"));
				assertEquals(data.getTimestamp("endTime"), Timestamp.valueOf("2022-02-02 00:00:00"));
				assertEquals(data.getTimestamp("deadline"), Timestamp.valueOf("2022-01-02 00:00:00"));
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	@Test
	protected void testFetchCalendarInfos() throws ParseException {
		
		System.out.println("START TEST: FetchCalendarInfos");
		
		DBFacade fixture = DBFacade.getInstance();
		
		GroupCalendar result = fixture.fetchCalendarInfos(1, null, null, null, null);
		
		//Check if anything returned
		assertTrue(result != null);
		
		//Check if correct data returned
		assertTrue(result.getGroupID() == 1);
		assertEquals(result.getName(), "Calendar1");
		assertEquals(result.getDescription(), "Kalender der Gruppe 1");
		assertTrue(result.getAppointments() instanceof ArrayList);
		
	}

}
