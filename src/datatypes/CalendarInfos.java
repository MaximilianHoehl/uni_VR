package datatypes;

import java.util.ArrayList;


public class CalendarInfos {
	
	private int cid;
	private String name;
	private String description;
	private ArrayList<AppointmentData> Appointments;
	private int groupID = cid;
	
	public CalendarInfos( int cid, String name, String description, ArrayList<AppointmentData> Appointments) {
		
		this.cid = cid;
		this.name = name;
		this.description = description;
		this.Appointments = Appointments;
	}

}
