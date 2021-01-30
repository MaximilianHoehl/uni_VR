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
		//Declarate GroupCalendar-variable
		GroupCalendar result = null;
		System.out.println("getGroupCalendar");
		
		// Declare the necessary SQL queries.
		// SQL-anfrage vorbereiten
		String sqlSelect = "SELECT * FROM groupcalendars gc WHERE gc.cid=?";

		// Query all offers that fits to the given criteria.
		//Verbindung zur Datenbank herstellen und in "connection"-objekt speichern
		try (Connection connection = DriverManager
				.getConnection(
						"jdbc:" + Configuration.getType() + "://" + Configuration.getServer() + ":"
								+ Configuration.getPort() + "/" + Configuration.getDatabase(),
						Configuration.getUser(), Configuration.getPassword())) {
			
			//SQL Anfrage absichern 
			//Create prepered statement
			try (PreparedStatement ps = connection.prepareStatement(sqlSelect)) {
				//calendar-id der SQL-Anfrage hinzufügen
				ps.setInt(1, cid);
				//SQL-Anfrage ausführen und das Ergebnis in "rs" speichern
				try (ResultSet rs = ps.executeQuery()) { 
					
					
					while (rs.next()) {
						//Fake List for debugging
						ArrayList<AppointmentData> fakeList = new ArrayList<AppointmentData>();
						LocationData fakeloc = new LocationData("Street", "town", 44687, "Country");
						LocationData fakeloc2 = new LocationData("Street", "town", 14297, "Country");
						TimeData faketd = new TimeData(1, 7, 9 ,6 ,7 ,2);
						TimeData faketd2 = new TimeData(1, 7, 9 ,6 ,7 ,2);
						TimeData faket3 = new TimeData(1, 7, 9 ,6 ,7 ,2);
						TimeData faketd4 = new TimeData(1, 7, 9 ,6 ,7 ,2);
						fakeList.add(new AppointmentData("Appointment1", fakeloc, faketd, faket3, "-", null, 1));
						fakeList.add(new AppointmentData("Appointment2", fakeloc2, faketd2, faketd4, "-", null, 2));	
						
						
						result = new GroupCalendar(rs.getInt(1), rs.getString(2), rs.getString(3), fakeList);
					}
					System.out.println("Returned GroupCalendarObject: " + result);
					return result;
		
					
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Block2 failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Block1 failed");
		}
		
		System.out.println("fetchCalendarInfos ran through! :(");
		return result; 
		}

	@Override
	public Boolean addAppointment(int cid, String name, String description, LocationData location,
			TimeData deadline, TimeData startTime, TimeData endTime) {
		
		//check if requested Appointment overlaps
		String startTimestamp = "TIMESTAMP('" 
								+ String.valueOf(startTime.getYear()) + "-"
								+ String.valueOf(startTime.getMonth()) + "-"
								+ String.valueOf(startTime.getDay()) + " "
								+ String.valueOf(startTime.getHour()) + ":"
								+ String.valueOf(startTime.getMinute()) + ":"
								+ String.valueOf(startTime.getSecond())
								+ "')";
		String endTimestamp = "TIMESTAMP('" 
								+ String.valueOf(endTime.getYear()) + "-"
								+ String.valueOf(endTime.getMonth()) + "-"
								+ String.valueOf(endTime.getDay()) + " "
								+ String.valueOf(endTime.getHour()) + ":"
								+ String.valueOf(endTime.getMinute()) + ":"
								+ String.valueOf(endTime.getSecond())
								+ "')";
		System.out.println(startTimestamp);
		System.out.println(endTimestamp);
		
		String sqlSelectOverlap = "SELECT COUNT(*) FROM appointments a"
				+ " WHERE ((a.startTime >= " + startTimestamp + ") AND (a.startTime <= " + endTimestamp + "))"
				+ " OR ((a.endTime >= " + startTimestamp + ") AND (a.endTime <= " + endTimestamp +"))"
				+ " OR ((a.startTime < " + startTimestamp +") AND (a.endTime > " + startTimestamp + "))";
		System.out.println(sqlSelectOverlap);
		
		try (Connection connection = DriverManager
				.getConnection(
						"jdbc:" + Configuration.getType() + "://" + Configuration.getServer() + ":"
								+ Configuration.getPort() + "/" + Configuration.getDatabase(),
						Configuration.getUser(), Configuration.getPassword())) {
			
			//Create prepered statement - SQL Anfrage absichern 
			try (PreparedStatement ps = connection.prepareStatement(sqlSelectOverlap)) {
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
	}

	/**
	 * Inserts a new offer in the database.
	 * 
	 * @param startTime
	 * @param endTime
	 * @param address
	 * @param capacity
	 * @param fee
	 */
	
	/*
	public void insertOffer(Timestamp startTime, Timestamp endTime, AddressData address, int capacity, double fee) {

		// Declare SQL query to insert offer.
		String sqlInsert = "INSERT INTO HolidayOffer (startTime,endTime,street,town,capacity,fee) VALUES (?,?,?,?,?,?)";
		System.out.println("insertOffer");
		// Insert offer into database.
		try (Connection connection = DriverManager
				.getConnection(
						"jdbc:" + Configuration.getType() + "://" + Configuration.getServer() + ":"
								+ Configuration.getPort() + "/" + Configuration.getDatabase(),
						Configuration.getUser(), Configuration.getPassword())) {

			try (PreparedStatement ps = connection.prepareStatement(sqlInsert)) {
				ps.setTimestamp(1, startTime);
				ps.setTimestamp(2, endTime);
				ps.setString(3, address.getStreet());
				ps.setString(4, address.getTown());
				ps.setInt(5, capacity);
				ps.setDouble(6, fee);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Inserts a booking into the database if there are enough capacities
	 * 
	 * @param arrivalTime
	 * @param departureTime
	 * @param hid
	 * @param guestData
	 * @param persons
	 * @return new booking object if available or null if not available
	 */
	
	/*
	public Booking bookingHolidayOffer(Timestamp arrivalTime, Timestamp departureTime, int hid, GuestData guestData,
			int persons) {
		HolidayOffer ho = null;
		ArrayList<Booking> bookings = new ArrayList<Booking>();
		Booking booking = null;
		System.out.println("bookingHO");
		// Declare necessary SQL queries.
		String sqlSelectHO = "SELECT * FROM HolidayOffer WHERE id=?";
		String sqlInsertBooking = "INSERT INTO Booking (creationDate,arrivalTime,departureTime,paid,name,email,price,hid) VALUES (?,?,?,?,?,?,?,?)";
		String sqlSelectB = "SELECT * FROM Booking WHERE hid=?";

		// Get selected offer
		try (Connection connection = DriverManager
				.getConnection(
						"jdbc:" + Configuration.getType() + "://" + Configuration.getServer() + ":"
								+ Configuration.getPort() + "/" + Configuration.getDatabase(),
						Configuration.getUser(), Configuration.getPassword())) {
			try (PreparedStatement psSelect = connection.prepareStatement(sqlSelectHO);
					PreparedStatement psSelectB = connection.prepareStatement(sqlSelectB);
					PreparedStatement psInsert = connection.prepareStatement(sqlInsertBooking,
							PreparedStatement.RETURN_GENERATED_KEYS)) {
				psSelect.setInt(1, hid);
				try (ResultSet hors = psSelect.executeQuery()) {
					if (hors.next()) {
						ho = new HolidayOffer(hors.getInt(1), hors.getTimestamp(2), hors.getTimestamp(3),
								new AddressData(hors.getString(4), hors.getString(5)), hors.getInt(6),
								hors.getDouble(7));
					}
				}

				// Check if offer is still available
				if (ho != null) {
					psSelectB.setInt(1, hid);
					try (ResultSet brs = psSelectB.executeQuery()) {
						while (brs.next()) {
							bookings.add(new Booking(brs.getInt(1), brs.getTimestamp(2), brs.getTimestamp(3),
									brs.getTimestamp(4), brs.getBoolean(5),
									new GuestData(brs.getString(6), brs.getString(7)), brs.getDouble(8),
									brs.getInt(9)));
						}
						ho.setBookings(bookings);
					}

					// Insert new booking
					if (ho.available(arrivalTime, departureTime) && ho.getCapacity() >= persons) {
						Timestamp creationDate = new Timestamp(new Date().getTime());
						booking = new Booking(0, new Timestamp(creationDate.getTime()), arrivalTime, departureTime,
								false, guestData, calculatePrice(arrivalTime, departureTime, ho.getFee()), ho.getId());
						psInsert.setTimestamp(1, booking.getCreationDate());
						psInsert.setTimestamp(2, booking.getArrivalTime());
						psInsert.setTimestamp(3, booking.getDepartureTime());
						psInsert.setBoolean(4, booking.isPaid());
						psInsert.setString(5, booking.getGuestData().getName());
						psInsert.setString(6, booking.getGuestData().getEmail());
						psInsert.setDouble(7, booking.getPrice());
						psInsert.setInt(8, booking.getHid());
						psInsert.executeUpdate();
						try (ResultSet generatedKeys = psInsert.getGeneratedKeys()) {
							if (generatedKeys.next()) {
								booking.setId(generatedKeys.getInt(1));
							}
						}

					} else
						ho = null;

				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return booking;
	}

	/**
	 * Delete all bookings not paid and older than 14 days.
	 */
	
	/*
	public void setAvailableHolidayOffer() {

		// Declare necessary SQL statement.
		String deleteBO = "DELETE FROM Booking WHERE (paid=false) AND \"creationDate + 14 days < date\"";
		System.out.println("setAvailableHO");
		// Update Database.
		try (Connection connection = DriverManager
				.getConnection(
						"jdbc:" + Configuration.getType() + "://" + Configuration.getServer() + ":"
								+ Configuration.getPort() + "/" + Configuration.getDatabase(),
						Configuration.getUser(), Configuration.getPassword())) {
			try (PreparedStatement psDelete = connection.prepareStatement(deleteBO)) {
				psDelete.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks if offer with given id exists.
	 * 
	 * @param hid
	 * @return
	 */
	
	/*
	public boolean checkHolidayOfferById(int hid) {

		// Declare necessary SQL query.
		String queryHO = "SELECT FROM HolidayOffer WHERE id=?";
		System.out.println("CheckHObyId");
		// query data.
		try (Connection connection = DriverManager
				.getConnection(
						"jdbc:" + Configuration.getType() + "://" + Configuration.getServer() + ":"
								+ Configuration.getPort() + "/" + Configuration.getDatabase(),
						Configuration.getUser(), Configuration.getPassword())) {
			try (PreparedStatement psSelect = connection.prepareStatement(queryHO)) {
				psSelect.setInt(1, hid);
				try (ResultSet rs = psSelect.executeQuery()) {
					return rs.next();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Function used to calculate the price for a booking.
	 * 
	 * @param date1 arrival date
	 * @param date2 departure date
	 * @param fee   price per night for the offer
	 * @return
	 */
	
	/*
	private double calculatePrice(Timestamp date1, Timestamp date2, double fee) {
		long dayDifference = (date2.getTime() - date1.getTime()) / 1000 / 60 / 60 / 24;

		return dayDifference * fee;
	}
}
*/