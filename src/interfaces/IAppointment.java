package interfaces;

import datatypes.LocationData;
import datatypes.TimeData;

public interface IAppointment {
	
	public Boolean addAppointment(int aid, String name, String description, LocationData location,
			TimeData deadline, TimeData startTime, TimeData endTime, String[] pp, String suggestions, String plannedParticipants, String confirmations);
	
	public Boolean setChosenDate();
	
	public Boolean saveAppointment();
	
	public TimeData get_deadlineDate();
	
	public TimeData get_appointment();
	
	public int get_confirmations();
	
}
