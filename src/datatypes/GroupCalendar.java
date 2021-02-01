package datatypes;

import java.util.ArrayList;

public class GroupCalendar {
	
	private int cid;
	private String name;
	private String description;
	private ArrayList<AppointmentData> Appointments;
	private int groupID = cid;
	
	public GroupCalendar( int cid, String name, String description, ArrayList<AppointmentData> Appointments) {
		
		this.cid = cid;
		this.groupID = cid;
		this.name = name;
		this.description = description;
		this.Appointments = Appointments;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public ArrayList<AppointmentData> getAppointments() {
		return Appointments;
	}

	public int getGroupID() {
		return groupID;
	}

}
