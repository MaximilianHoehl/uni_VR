package interfaces;

import java.util.ArrayList;
import datatypes.*;

public interface CmCmds {

		public GroupCalendar getCalendarInfos(int cid, String name, LocationData location, String description, AppointmentData appointments);
		
		public Boolean makeAppointmentRequest(int aid, String name, String description, LocationData location, TimeData deadline, TimeData startTime, TimeData endTime, String[] pp, String suggestions, String plannedParticipants, String confirmations);
		
		public ArrayList<Suggestion> getSuggestions(int aid);
		
		public ArrayList<Appointment> getUnfinalizedAppointments(int cid);
		
		public Appointment selectingDate();
		
		public Appointment suggestingDate();
		
}

