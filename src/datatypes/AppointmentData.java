package datatypes;

public class AppointmentData {
	
	private String name;
	private LocationData location;
	private TimeData startTime;
	private TimeData endTime;
	private String description;
	private TimeData deadline;
	private int id;
	private Boolean finalized;
	
	public AppointmentData (int id, String name,  String description, LocationData location, TimeData startTime, TimeData endTime, TimeData deadline, Boolean finalized) {
		
		this.name = name;
		this.location = location;
		this.startTime = startTime;
		this.endTime = endTime;
		this.description = description;
		this.deadline = deadline; 
		this.id = id;
		this.finalized = finalized;
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

	public Boolean getFinalized() {
		return finalized;
	}
	
}
