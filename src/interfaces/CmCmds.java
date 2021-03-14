package interfaces;

import java.util.ArrayList;
import datatypes.*;

public interface CmCmds {

		public GroupCalendar getCalendarInfos(int cid);
		
		public Boolean makeAppointmentRequest(int aid, String name, String description, LocationData location, TimeData deadline, TimeData startTime, TimeData endTime, String[] pp);
		
		public ArrayList<Suggestion> getSuggestions(int aid);
		
		public ArrayList<Appointment> getUnfinalizedAppointments(int cid);
		
		public Boolean selectingDate(int uid, int sid, int aid);
		
		public Boolean suggestingDate(int uid, int aid, TimeData startTime, TimeData endTime);
		
}

