package datatypes;

import java.sql.Timestamp;

public class Suggestion {
	
	private int id;
	private Timestamp startTime;
	private Timestamp endTime;
	private int confirmations;
	
	public Suggestion() {
		
	}
	
	public Suggestion(int id, Timestamp startTime, Timestamp endTime, int confirmations) {
		
		this.id = id;
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
}
