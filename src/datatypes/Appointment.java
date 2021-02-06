package datatypes;

public class Appointment {
	
	private int id;
	private int cid;
	private String name;
	private String description;
	private LocationData location;
	private TimeData startTime;
	private TimeData endTime;
	private TimeData deadline;
	private Boolean finalized;
	private String suggestions;
	private String plannedParticipants;
	private String confirmations;
	
	
	public Appointment (String name, LocationData location, TimeData startTime, TimeData endTime, 
			String description, TimeData deadline, int id, int cid) {
		
		this.name = name;
		this.location = location;
		this.startTime = startTime;
		this.endTime = endTime;
		this.description = description;
		this.deadline = deadline; 
		this.id = id;
		this.cid = cid;
		this.setFinalized(false);
	}
	public Appointment (String name, LocationData location, TimeData startTime, TimeData endTime, 
			String description, TimeData deadline, int id, int cid, Boolean finalized, String suggestions, 
			String plannedParticipants, String confirmations) {
		
		this.name = name;
		this.location = location;
		this.startTime = startTime;
		this.endTime = endTime;
		this.description = description;
		this.deadline = deadline; 
		this.id = id;
		this.cid = cid;
		this.setFinalized(finalized);
		this.suggestions = suggestions;
		this.plannedParticipants = plannedParticipants;
		this.confirmations = confirmations;
	}
	//Constructor for getting not-finalized appointments (->selectAWebPage suggestion/selection)
	public Appointment(int res_id, int res_cid, String res_name, String res_description, LocationData res_location,
			TimeData res_startTime, TimeData res_endTime, TimeData res_deadline, Boolean res_finalized,
			String res_suggestions, String res_plannedParticipants, String res_Confirmations) {
		this.name = res_name;
		this.location = res_location;
		this.startTime = res_startTime;
		this.endTime = res_endTime;
		this.description = res_description;
		this.deadline = res_deadline; 
		this.id = res_id;
		this.cid = res_cid;
		this.setFinalized(res_finalized);
		this.suggestions = res_suggestions;
		this.plannedParticipants = res_plannedParticipants;
		this.confirmations = res_Confirmations;
	}
	public String getName() {
		return name;
	}

	public LocationData getLocation() {
		return location;
	}

	public TimeData getStartTime() {
		return startTime;
	}

	public TimeData getEndTime() {
		return endTime;
	}

	public String getDescription() {
		return description;
	}

	public TimeData getDeadline() {
		return deadline;
	}

	public int getId() {
		return id;
	}

	public int getCid() {
		return cid;
	}
	public Boolean getFinalized() {
		return finalized;
	}
	public void setFinalized(Boolean finalized) {
		this.finalized = finalized;
	}
	public String getSuggestions() {
		return suggestions;
	}
	public String getPlannedParticipants() {
		return plannedParticipants;
	}
	public String getConfirmations() {
		return confirmations;
	}
	
}

