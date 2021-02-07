package debugging;

public class Debugging {
	
	public static int currentUser;
	
	public static void setCurrentUser(int uid) {
		currentUser = uid;
	}
	
	public static int getCurrentUser() {
		
		return currentUser;
	}

}
