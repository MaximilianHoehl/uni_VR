package dbadapter;

/**
 * This class is used to declare access data for the SQL server
 * 
 * @author swe.uni-due.de
 *
 */
public class Configuration {

	// Replace these static values with your database configuation.
	private static final String SERVER = "127.0.0.1";
	private static final String TYPE = "mysql";
	private static final int PORT = 3306;
	private static final String DATABASE = "calendarapp";
	private static final String USER = "root";
	/**
	 * This password in stored in plaintext. We assume that only the owner can
	 * access this file and that all login data is tranfered in an encrypted way
	 * (e.g. SSL).
	 */
	private static final String PASSWORD = "";

	public static String getServer() {
		//System.out.println("Got SERVER");
		return SERVER;
	}

	public static String getType() {
		//System.out.println("Got TYPE");
		return TYPE;
	}

	public static int getPort() {
		//System.out.println("Got PORT");
		return PORT;
	}

	public static String getDatabase() {
		//System.out.println("Got DB");
		return DATABASE;
	}

	public static String getUser() {
		//System.out.println("Got USER");
		return USER;
	}

	public static String getPassword() {
		//System.out.println("Got PW");
		return PASSWORD;
	}

}
