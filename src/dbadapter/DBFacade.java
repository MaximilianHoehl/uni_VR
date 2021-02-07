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
import debugging.Debugging;
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
			TimeData deadline, TimeData startTime, TimeData endTime, String[] pp, String suggestions, String PlannedParticipants, String confirmations) {
		
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
									+ " (cid, name, description, location, startTime, endTime, deadline, finalized, Suggestions, PlannedParticipants, Confirmations)"
									+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
							String sqlInsertSugg = "INSERT INTO suggestions(uid, aid, startTime, endTime) VALUES (?, ?, ?, ?)";
							try(PreparedStatement psI = connection.prepareStatement(sqlInsertA)){
								psI.setInt(1, cid);
								psI.setString(2, name);
								psI.setString(3, description);
								psI.setString(4, location.getLocationstring());
								psI.setTimestamp(5, startTime.getTimestamp());
								psI.setTimestamp(6, endTime.getTimestamp());
								psI.setTimestamp(7, deadline.getTimestamp());
								psI.setBoolean(8, false);
								psI.setString(9, suggestions);
								psI.setString(10, PlannedParticipants);
								psI.setString(11, confirmations);
								try{
									psI.executeUpdate();
									try(PreparedStatement psS = connection.prepareStatement(sqlInsertSugg)){
										int lastAppointmentID = getLastAppointmentID(connection);
										psS.setInt(1, Debugging.getCurrentUser());
										psS.setInt(2, lastAppointmentID);
										psS.setTimestamp(3, startTime.getTimestamp());
										psS.setTimestamp(4, endTime.getTimestamp());
										psS.executeUpdate();
										
										if(insertPlannedParticipants(pp, lastAppointmentID, connection)) {
											System.out.println("INSERTED PP");
											return true;
										}else {
											System.out.println("FAILED PP");
											return false;
										}
										
									}catch(Exception e) {
										e.printStackTrace();
										System.out.println("Block5 failed");
										return false;
									}
									
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
	
	
	public Appointment getAppointmentById(int id) {
		
		Appointment result = null;
		try (Connection connection = createDBConnection()) {
			String sql = "SELECT * FROM appointment WHERE id=?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int res_id = rs.getInt(1);
				int res_cid = rs.getInt(2);
				String res_name = rs.getString(3); 
				String res_description = rs.getString(4);
				LocationData res_location = new LocationData(rs.getString(5));
				TimeData res_startTime = new TimeData(rs.getTimestamp(6));
				TimeData res_endTime = new TimeData(rs.getTimestamp(7));
				TimeData res_deadline = new TimeData(rs.getTimestamp(8));
				Boolean res_finalized = rs.getBoolean(9);
				String res_suggestions = rs.getString(10);
				String res_plannedParticipants = rs.getString(11);
				String res_Confirmations = rs.getString(12);
				result = new Appointment(res_id, res_cid, res_name, res_description, res_location, res_startTime, res_endTime, res_deadline, res_finalized, res_suggestions, res_plannedParticipants, res_Confirmations);
				return result;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Boolean setChosenDate(int uid, int sid, int aid) {
		
		String clearConfirmations = "DELETE FROM confirmations WHERE aid=? AND uid=?";
		String insertSugg = "INSERT INTO confirmations (uid, sid, aid) VALUES (?, ?, ?)";
		try(Connection connection = createDBConnection()){
			PreparedStatement psCon = connection.prepareStatement(clearConfirmations);
			psCon.setInt(1, aid);
			psCon.setInt(2, uid);
			PreparedStatement psSugg = connection.prepareStatement(insertSugg);
			psSugg.setInt(1, uid);
			psSugg.setInt(2, sid);
			psSugg.setInt(3, aid);
			try {
				psCon.executeUpdate();
				psSugg.executeUpdate();
				return true;
			}catch(Exception e) {
				System.out.println("DBFACADE: setChosenDate: FAILED AT BLOCK2");
				e.printStackTrace();
				return false;
			}
		}catch(Exception e) {
			System.out.println("DBFACADE: setChosenDate: FAILED AT BLOCK1");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Boolean saveSuggestion(int uid, int aid, TimeData startTime, TimeData endTime) {
		String sql = "INSERT INTO suggestions (uid, aid, startTime, endTime) VALUES (?, ?, ?, ?)";
		try(Connection connection = createDBConnection()){
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, uid);
			ps.setInt(2, aid);
			ps.setTimestamp(3, startTime.getTimestamp());
			ps.setTimestamp(4, endTime.getTimestamp());
			
			ps.executeUpdate();
			return true;
			
		}catch(Exception e) {
			System.out.println("DBFACADE: saveSuggestion: FAILED AT BLOCK1");
			e.printStackTrace();
			return false;
		}
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
	
	private int getLastAppointmentID(Connection connection) {
		String sql = "SELECT id FROM appointments ORDER BY id DESC LIMIT 1";
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs;
			rs = ps.executeQuery();
			while(rs.next()) {
				
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 0;
	}
	
	private Boolean insertPlannedParticipants(String[] pp, int aid, Connection connection) {
		
		//Create dynamic SQL String depending on array lenth
		String sql = "INSERT INTO plannedparticipants (aid, uid) VALUES ";
		for(int i=0; i<pp.length; i++) {
			if(i==pp.length-1) {
				sql += "(?, ?)";	//End of String
			}else {
				sql += "(?, ?), "; //Internal String
			}
		}
		System.out.println("GENERATED DYNAMIC STRING: " + sql);
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			int counter = 0;
			for(String i : pp) {
				counter++;
				ps.setInt(counter, aid);
				counter++;
				ps.setInt(counter, Integer.valueOf(i));
			}
			System.out.println("INSERTED DYNAMIC VALUES");
			ps.executeUpdate();
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}	
	}

	@Override
	public ArrayList<Suggestion> fetchSuggestions(int aid) {
		
		ArrayList<Suggestion> result = new ArrayList<Suggestion>();
		try (Connection connection = createDBConnection()) {
			String sqlSugg = "SELECT * FROM suggestions WHERE aid=?"; //COUNT for total Suggestion can also be calculated here
			String sqlConf = "SELECT COUNT(*) FROM confirmations WHERE sid=?";
			PreparedStatement psSugg = connection.prepareStatement(sqlSugg);
			psSugg.setInt(1, aid);
			ResultSet rsSugg = psSugg.executeQuery();
			while(rsSugg.next()) {
				try {
					PreparedStatement psConf = connection.prepareStatement(sqlConf);
					psConf.setInt(1, rsSugg.getInt("sid")); //get suggestionID and set it to sql request
					ResultSet rsConf = psConf.executeQuery();
					while(rsConf.next()) {
						int confirmationCount = rsConf.getInt(1);
						result.add(new Suggestion(rsSugg.getInt("sid"), rsSugg.getInt("uid"), rsSugg.getInt("aid"),
								rsSugg.getTimestamp("startTime"), rsSugg.getTimestamp("endTime"), confirmationCount));
					}
				}catch(Exception e) {
					System.out.println("DBFACADE: FETCHSUGGESTIONS: FAILED AT BLOCK2");
					e.printStackTrace();
					return null;
				}
				
			}
			int requiredConfirmationCount = result.size();
			System.out.println("FETCHED SUGGESTIONS; CONFIRMATIONS. TOTAL CONFIRMATIONCOUNT: " + requiredConfirmationCount);
			for(Suggestion s : result) {
				s.setRequiredConfirmations(requiredConfirmationCount);
			}
			return result;
		}catch(Exception e) {
			System.out.println("DBFACADE: FETCHSUGGESTIONS: FAILED AT BLOCK1");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ArrayList<Appointment> fetchUnfinalizedAppointments(int cid) {
		ArrayList<Appointment> result = new ArrayList<Appointment>();
		try (Connection connection = createDBConnection()) {
			String sql = "SELECT * FROM appointments WHERE cid=? AND finalized=false";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, cid);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int res_id = rs.getInt(1);
				int res_cid = rs.getInt(2);
				String res_name = rs.getString(3); 
				String res_description = rs.getString(4);
				LocationData res_location = new LocationData(rs.getString(5));
				TimeData res_startTime = new TimeData(rs.getTimestamp(6));
				TimeData res_endTime = new TimeData(rs.getTimestamp(7));
				TimeData res_deadline = new TimeData(rs.getTimestamp(8));
				Boolean res_finalized = rs.getBoolean(9);
				String res_suggestions = rs.getString(10);
				String res_plannedParticipants = rs.getString(11);
				String res_Confirmations = rs.getString(12);
				result.add(new Appointment(res_id, res_cid, res_name, res_description, res_location, res_startTime, res_endTime, res_deadline, res_finalized, res_suggestions, res_plannedParticipants, res_Confirmations));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}