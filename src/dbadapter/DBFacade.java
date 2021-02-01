package dbadapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import datatypes.*;

import interfaces.IGroupCalendar;
import interfaces.IAppointment;

public class DBFacade implements IGroupCalendar, IAppointment {
	
	private static DBFacade instance;

	private DBFacade() {
		try {
			Class.forName("com." + Configuration.getType() + ".jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static DBFacade getInstance() {
		if (instance == null) {
			instance = new DBFacade();
		}

		return instance;
	}

	public static void setInstance(DBFacade dbfacade) {
		instance = dbfacade;
	}


	public GroupCalendar fetchCalendarInfos(int cid, String name, LocationData location, String description,AppointmentData appointments) {
		
		//GroupCalendar-Variable deklarieren (bekommt den Rückgabewert)
		GroupCalendar result = null;
		ArrayList<AppointmentData> appointmentList = new ArrayList<AppointmentData>();
		
		// Declare the necessary SQL queries.
		String sqlSelect = "SELECT * FROM groupcalendars gc WHERE gc.cid=?";

		// Query all offers that fits to the given criteria.
		try (Connection connection = createDBConnection()) {
			
			//SQL Anfrage absichern 
			try (PreparedStatement ps = connection.prepareStatement(sqlSelect)) {
				//calendar-id der SQL-Anfrage hinzufügen
				ps.setInt(1, cid);
				//SQL-Anfrage ausführen und das Ergebnis in "rs" speichern
				try (ResultSet rs = ps.executeQuery()) { 
			
					while (rs.next()) {
						
						String sqlSelectAppointments = "SELECT * FROM appointments WHERE cid=?";
						try(PreparedStatement psSA = connection.prepareStatement(sqlSelectAppointments)){
							System.out.println("DBFacade: FetchCalendarAppointments: prepared Appointment SELECT statement");
							psSA.setInt(1, cid);
							System.out.println("DBFacade: FetchCalendarAppointments: Setted cid value in prepared SELECT psSA statement: " + cid);
							try(ResultSet res = psSA.executeQuery()){
								System.out.println("DBFacade: FetchCalendarAppointments: executed Query psSA");
								while(res.next()) {
									int res_id = res.getInt(1);
									String res_name = res.getString(3); 
									String res_description = res.getString(4);
									LocationData res_location = new LocationData(res.getString(5));
									TimeData res_startTime = new TimeData(res.getTimestamp(6));
									TimeData res_endTime = new TimeData(res.getTimestamp(7));
									TimeData res_deadline = new TimeData(res.getTimestamp(8));
									Boolean res_finalized = res.getBoolean(9);
									System.out.println("DBFacade: FetchCalendarAppointments: fetched data and created complex datatypes from DB result");
									appointmentList.add(new AppointmentData(res_id, res_name, res_description, 
											res_location, res_startTime, res_endTime, res_deadline, res_finalized));
									System.out.println("DBFacade: FetchCalendarAppointments: Created AppointmentList from Calendar");
								}
								
							}catch(Exception e) {
								System.out.println("DBFacade: FetchCalendarAppointments: Block4 failed");
								e.printStackTrace();
							}
						}catch(Exception e) {
							System.out.println("DBFacade: FetchCalendarAppointments: Block3 failed");
							e.printStackTrace();
						}
						
						
						result = new GroupCalendar(rs.getInt(1), rs.getString(2), rs.getString(3), appointmentList);
						System.out.println("DBFacade: FetchCalendarAppointments: Created Result");
					}
					System.out.println("DBFacade: FetchCalendarAppointments: Returned Result");
					return result;
		
					
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Fetch Calendar: Block2 failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Fetch Calendar: Block1 failed");
		}
		
		System.out.println("fetchCalendarInfos ran through! :(");
		return null; 
		}

	@Override
	public Boolean addAppointment(int cid, String name, String description, LocationData location,
			TimeData deadline, TimeData startTime, TimeData endTime) {
		
		//check if requested Appointment overlaps
		String sqlSelectOverlap = "SELECT COUNT(*) FROM appointments a"
				+ " WHERE ((a.startTime >= ? ) AND (a.startTime <= ? ))"
				+ " OR ((a.endTime >= ? ) AND (a.endTime <= ? ))"
				+ " OR ((a.startTime < ? ) AND (a.endTime > ? ))";

		try (Connection connection = createDBConnection()) {
			Timestamp startTimestamp = startTime.getTimestamp();
			Timestamp endTimestamp = endTime.getTimestamp();
			//Create prepered statement - SQL Anfrage absichern 
			try (PreparedStatement ps = connection.prepareStatement(sqlSelectOverlap)) {
				ps.setTimestamp(1, startTimestamp);
				ps.setTimestamp(2, endTimestamp);
				ps.setTimestamp(3, startTimestamp);
				ps.setTimestamp(4, endTimestamp);
				ps.setTimestamp(5, startTimestamp);
				ps.setTimestamp(6, startTimestamp);
				//SQL-Anfrage ausführen und das Ergebnis in "rs" speichern
				try (ResultSet rs = ps.executeQuery()) { 		
					while(rs.next()) {
						if(rs.getInt("count(*)")==0) {
							System.out.println("No overlap");
							System.out.println(rs.getInt("count(*)")==0);
							
							String sqlInsertA = "INSERT INTO appointments"
									+ " (cid, name, description, location, startTime, endTime, deadline, finalized)"
									+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
							try(PreparedStatement psI = connection.prepareStatement(sqlInsertA)){
								System.out.println("TimestampStart passed to Timestamp-function: " + startTime.getTimestamp());
								System.out.println("TimestampEnd passed to Timestamp-function: " + endTime.getTimestamp());
								System.out.println("TimestampDeadline passed to Timestamp-function: " + deadline.getTimestamp());
								psI.setInt(1, cid);
								psI.setString(2, name);
								psI.setString(3, description);
								psI.setString(4, location.getLocationstring());
								psI.setTimestamp(5, startTime.getTimestamp());
								psI.setTimestamp(6, endTime.getTimestamp());
								psI.setTimestamp(7, deadline.getTimestamp());
								psI.setBoolean(8, false);
								try{
									psI.executeUpdate();
									return true;
									
								}catch(Exception e) {
									e.printStackTrace();
									System.out.println("Block4 failed");
									return false;
								}
							}catch(Exception e) {
								e.printStackTrace();
								System.out.println("Block3 failed");
								return false;
							}
						}else {
							System.out.println("OVERLAP");
							System.out.println(rs.getInt("count(*)")==0);
							return false;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Block2 failed");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Block1 failed");
			return false;
		}
		return false;
	}

	@Override
	public Boolean setChosenDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean saveAppointment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TimeData get_deadlineDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TimeData get_appointment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int get_confirmations() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private Connection createDBConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:" + Configuration.getType() + "://" + Configuration.getServer() + ":"
			+ Configuration.getPort() + "/" + Configuration.getDatabase(), Configuration.getUser(), Configuration.getPassword());
	}
}