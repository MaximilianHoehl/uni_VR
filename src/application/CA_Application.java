package application;

import java.util.ArrayList;

import datatypes.Appointment;
import datatypes.AppointmentData;
import datatypes.CalendarInfos;
import datatypes.GroupCalendar;
import datatypes.LocationData;
import datatypes.TimeData;
import interfaces.CmCmds;

import dbadapter.DBFacade;

public class CA_Application implements CmCmds {
	
	private static CA_Application instance;
	
	private CA_Application() {
		
	}
	
	public static CA_Application createInstance() {
		
		if(instance == null) {
			instance = new CA_Application();
		}
		
		return instance;
	}

	@Override
	public GroupCalendar getCalendarInfos(int cid, String name, LocationData location, String description, AppointmentData appointments) {
		
		DBFacade dbFacade = DBFacade.getInstance();
		GroupCalendar gc = dbFacade.fetchCalendarInfos(cid, name, location, description, appointments);
		return gc;
	}

	@Override
	public Boolean makeAppointmentRequest(int cid, String name, String description, LocationData location,
			TimeData deadline, TimeData startTime, TimeData endTime) {
		
		DBFacade dbFacade = DBFacade.getInstance();
		return dbFacade.addAppointment(cid, name, description, location, deadline, startTime, endTime);
	
	}

	@Override
	public Appointment selectingDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Appointment suggestingDate() {
		// TODO Auto-generated method stub
		return null;
	}



}
