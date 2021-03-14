package interfaces;

import java.util.ArrayList;

import datatypes.Appointment;
import datatypes.LocationData;
import datatypes.Suggestion;
import datatypes.TimeData;

public interface IAppointment {
	
	public Boolean addAppointment(int aid, String name, String description, LocationData location,
			TimeData deadline, TimeData startTime, TimeData endTime, String[] pp);
	
	public Boolean setChosenDate(int uid, int sid, int aid);
	
	public Boolean saveSuggestion(int uid, int aid, TimeData startTime, TimeData endTime);
	
	public ArrayList<Suggestion> fetchSuggestions(int aid);
	
	public ArrayList<Appointment> fetchUnfinalizedAppointments(int cid);
	
	public TimeData get_deadlineDate();
	
	public TimeData get_appointment();
	
	public int get_confirmations();
	
	public void finalizeAppointment();
}
