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
	
	
	public Appointment (String name, LocationData location, TimeData startTime, TimeData endTime, String description, TimeData deadline, int id, int cid) {
		
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
public Appointment (String name, LocationData location, TimeData startTime, TimeData endTime, String description, TimeData deadline, int id, int cid, Boolean finalized) {
		
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

