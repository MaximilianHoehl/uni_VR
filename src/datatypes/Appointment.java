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
			String description, TimeData deadline, int id, int cid, Boolean finalized) {
		
		this.name = name;
		this.location = location;
		this.startTime = startTime;
		this.endTime = endTime;
		this.description = description;
		this.deadline = deadline; 
		this.id = id;
		this.cid = cid;
		this.setFinalized(finalized);
	}
	//Constructor for getting not-finalized appointments (->selectAWebPage suggestion/selection)
	public Appointment(int res_id, int res_cid, String res_name, String res_description, LocationData res_location,
			TimeData res_startTime, TimeData res_endTime, TimeData res_deadline, Boolean res_finalized) {
		this.name = res_name;
		this.location = res_location;
		this.startTime = res_startTime;
		this.endTime = res_endTime;
		this.description = res_description;
		this.deadline = res_deadline; 
		this.id = res_id;
		this.cid = res_cid;
		this.setFinalized(res_finalized);
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
	
}

