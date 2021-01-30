package interfaces;

import datatypes.LocationData;
import datatypes.GroupCalendar;
import datatypes.AppointmentData;

public interface IGroupCalendar {
	
	public GroupCalendar fetchCalendarInfos(int cid, String name, LocationData location, String description, AppointmentData Appointment);

}
