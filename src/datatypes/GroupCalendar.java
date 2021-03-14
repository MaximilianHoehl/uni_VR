package datatypes;

import java.util.ArrayList;

public class GroupCalendar {
	
	private int cid;
	private String name;
	private String description;
	private ArrayList<AppointmentData> appointmentList;
	private int groupID = cid;
	
	public GroupCalendar( int cid, String name, String description, ArrayList<AppointmentData> Appointments) {
		
		this.cid = cid;
		this.groupID = cid;
		this.name = name;
		this.description = description;
		this.appointmentList = Appointments;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public ArrayList<AppointmentData> getAppointments() {
		return appointmentList;
	}

	public int getGroupID() {
		return groupID;
	}

}
