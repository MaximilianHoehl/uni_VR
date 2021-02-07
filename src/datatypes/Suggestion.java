package datatypes;

import java.sql.Timestamp;

public class Suggestion {
	
	private int id;
	private int userID;
	private int aid;
	private Timestamp startTime;
	private Timestamp endTime;
	private int confirmations;
	private int requiredConfirmations;
	
	public Suggestion() {
		
	}
	
	public Suggestion(int id, int userID, int aid, Timestamp startTime, Timestamp endTime, int confirmations) {
		
		this.id = id;
		this.setUserID(userID);
		this.setAid(aid);
		this.startTime = startTime;
		this.endTime = endTime;
		this.confirmations = confirmations;
	}

	public int getId() {
		return id;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public void setConfirmations(int confirmations) {
		this.confirmations = confirmations;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public int getConfirmations() {
		return confirmations;
	}

	public int getRequiredConfirmations() {
		return requiredConfirmations;
	}

	public void setRequiredConfirmations(int requiredConfirmations) {
		this.requiredConfirmations = requiredConfirmations;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}
}
