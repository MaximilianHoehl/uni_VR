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
	@Test
	void test_addAppointment() {		//Setup with problemspecific values
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
			String[] pp = {"1", "2"};
			
			//Execute the function 
			DBFacade fixture = DBFacade.getInstance();
			Boolean success = fixture.addAppointment(cid, name, desc, locationString, deadline, startTime, endTime, pp);
			System.out.println("TestAddAppointment: executed method");
			//Test if there are no technical errors
			assertTrue(success);
			
			//Test of exaclty one value was stored in DB
			String sqlCountApp = "SELECT COUNT(*) FROM appointments";
			String sqlCountSugg = "SELECT COUNT(*) FROM suggestions";
			PreparedStatement ps_CountApp = connection.prepareStatement(sqlCountApp);
			ResultSet rs = ps_CountApp.executeQuery();
			while(rs.next()) {
				int fetchedAppointmentCount = rs.getInt("count(*)");
				assertTrue(fetchedAppointmentCount == 2); //We already add one item in setup() to test the testFetchCalendarInfos method later on
			}
			PreparedStatement ps_CountSugg = connection.prepareStatement(sqlCountSugg);
			ResultSet rsS = ps_CountSugg.executeQuery();
			while(rsS.next()) {
				int fetchedSuggestionCount = rsS.getInt("count(*)");
				assertTrue(fetchedSuggestionCount == 1); //We only added 1 appointment over the addAppointment method
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
	protected void test_FetchCalendarInfos() throws ParseException {
		
		System.out.println("START TEST: FetchCalendarInfos");
		DBFacade fixture = DBFacade.getInstance();
		GroupCalendar result = fixture.fetchCalendarInfos(1, null, null, null, null);
		
		//Check if anything returned
		assertTrue(result != null);
		
		//Check if correct data returned
		assertTrue(result.getGroupID() == 1);
		assertEquals(result.getName(), "Calendar 1");
		assertEquals(result.getDescription(), "Kalender der Gruppe 1");
		assertTrue(result.getAppointments() instanceof ArrayList);
		
	}
	@Test
	protected void test_SetChosenDate() {
		try (Connection connection = DriverManager.getConnection(
				"jdbc:" + Configuration.getType() + "://" + Configuration.getServer() + ":"
						+ Configuration.getPort() + "/" + Configuration.getDatabase(),
						Configuration.getUser(), Configuration.getPassword())) {
			
			//Ausführen der Methode
			System.out.println("START TEST: SetChrosenDate");
			DBFacade fixture = DBFacade.getInstance();
			fixture.setChosenDate(1, 1, 1);
			fixture.setChosenDate(1, 1, 1); //execute twice so we can check if first gets deleted
			
			//Ergebnisse aus DB holen
			int confirmNum = 0;
			String sqlFetchResults = "SELECT COUNT(*) FROM confirmations c WHERE (c.uid=1 AND c.sid=1 AND c.aid=1)";
			PreparedStatement ps = connection.prepareStatement(sqlFetchResults);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				confirmNum = rs.getInt(1);
			}
			
			//Validation
			assertTrue(confirmNum == 1);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	@Test
	protected void test_saveSuggestion() {
		try (Connection connection = DriverManager.getConnection(
				"jdbc:" + Configuration.getType() + "://" + Configuration.getServer() + ":"
						+ Configuration.getPort() + "/" + Configuration.getDatabase(),
						Configuration.getUser(), Configuration.getPassword())) {
			
			//Prepare input-data
			int uid = 1;
			int aid = 1;
			TimeData startTime = new TimeData(2000, 1, 1, 0, 0, 0);
			TimeData endTime = new TimeData(2001, 1, 1, 0, 0, 0);
			
			//setup and rum method
			DBFacade fixture = DBFacade.getInstance();
			fixture.saveSuggestion(uid, aid, startTime, endTime);
			
			//fetch results
			int resCount = 0;
			String sqlResult = "SELECT COUNT(*) FROM suggestions WHERE uid=1 AND aid=1";
			PreparedStatement ps = connection.prepareStatement(sqlResult);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				resCount = rs.getInt(1);
			}
			
			//Validation
			assertTrue(resCount == 1);
		}catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	@Test 
	protected void test_finalizeAppointment() {
		try (Connection connection = DriverManager.getConnection(
				"jdbc:" + Configuration.getType() + "://" + Configuration.getServer() + ":"
						+ Configuration.getPort() + "/" + Configuration.getDatabase(),
						Configuration.getUser(), Configuration.getPassword())) {
			
			DBFacade fixture = DBFacade.getInstance();
			String[] pp = {"1", "2"};
			for(int i=0; i<2; i++) { //Create 2 appointments for past-case-testing
				fixture.addAppointment(1, "TestFinalization", "TestFinalization",
						new LocationData("testStreet 4444 testTown testCountry"),
						new TimeData(2000 + i, 1, 2, 0, 0, 0),
						new TimeData(2001 + i, 1, 2, 0, 0, 0),
						new TimeData(2000 + i, 1, 1, 0, 0, 0),
						pp);
			}
			for(int i=0; i<2; i++) { //Create 2 appointments for future-case-testing
				fixture.addAppointment(1, "TestFinalization", "TestFinalization",
						new LocationData("testStreet 4444 testTown testCountry"),
						new TimeData(2030 + i, 1, 2, 0, 0, 0),
						new TimeData(2031 + i, 1, 2, 0, 0, 0),
						new TimeData(2030 + i, 1, 1, 0, 0, 0),
						pp);
			}
			
			//Give one appointment all required confirmations
			String sql = "INSERT INTO confirmations (uid, sid, aid) VALUES (?,?,?)";
			PreparedStatement ps1 = connection.prepareStatement(sql);
			ps1.setInt(1, 1);
			ps1.setInt(2, 1);
			ps1.setInt(3, 1);
			PreparedStatement ps2 = connection.prepareStatement(sql);
			ps2.setInt(1, 2);
			ps2.setInt(2, 1);
			ps2.setInt(3, 1);
			ps1.executeUpdate();
			ps2.executeUpdate();
			
			//run method
			fixture.finalizeAppointment();
			
			//Fetch results
			String sqlFetchRes = "SELECT * FROM appointments";
			ArrayList<Boolean> results = new ArrayList<Boolean>();
			PreparedStatement ps = connection.prepareStatement(sqlFetchRes);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				results.add(rs.getBoolean("finalized"));
			}
			
			//Validate
			System.out.println("Results of finalize appointment query: ");
			for(Boolean b : results) {
				System.out.println(b);
			}
			assertTrue(results.get(0));
			assertTrue(results.get(1));
			
		}catch(Exception e) {
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
			
			String resetAppointments = "DELETE FROM appointments"; //Existing groupcalendar in DB is assumed since its not part of the task 
			String resetConfirmations = "DELETE FROM confirmations";
			String resetSuggestions = "DELETE FROM suggestions";
			String resetPlannedParticipants = "DELETE FROM plannedparticipants";
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
