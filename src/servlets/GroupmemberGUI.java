package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import datatypes.GroupCalendar;
import datatypes.LocationData;
import datatypes.TimeData;
import application.CA_Application;

public class GroupmemberGUI extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		
		
		
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		CA_Application app = CA_Application.createInstance();
		
		int cid = Integer.valueOf(request.getParameter("identity"));
		
		switch((String)request.getParameter("action")){
			case "showCalendar":
				request.setAttribute("pagetitle", "My Calendar");
				request.setAttribute("navtype", "showCalendar");

				
				GroupCalendar res = app.getCalendarInfos(cid, null, null, null, null);
				
				request.setAttribute("GroupCalendarInfo", res);
				request.setAttribute("AppointmentList", res.getAppointments());
				
				request.getRequestDispatcher("/templates/selectGCWebpage.ftl").forward(request, response);
				break;
				
			case "createAppointment":
				
				System.out.println("arrived at cA");
				//Extract data from formular - Daten aus dem Formular extrahieren
				String name = request.getParameter("name");
				String description = request.getParameter("description");
				String street = request.getParameter("street");
				String postcode_inp = request.getParameter("postcode");
				String town = request.getParameter("town");
				String country = request.getParameter("country");
				String startTime_date = request.getParameter("startDate");
				String startTime_time = request.getParameter("startTime");
				String endTime_date = request.getParameter("endDate");
				String endTime_time = request.getParameter("endTime");
				String deadline_inp = request.getParameter("deadline");
				
				System.out.println(name + description + street
						+ postcode_inp + town + country + 
						startTime_date + startTime_time + endTime_date 
						+endTime_time + deadline_inp);
				
				//Prepare data for constructors - Daten f�r Konstruktoren anpassen
				int postcode = Integer.valueOf(postcode_inp);
				String[] date_start = startTime_date.split(":");
				String[] time_start = startTime_time.split(":");
				String[] date_end = endTime_date.split(":");
				String[] time_end = endTime_time.split(":");
				String[] deadLine = deadline_inp.split(":");
				
				for(String e : date_start) {
					System.out.println("date_start: " + e);
				}
				for(String e : time_start) {
					System.out.println("time_start: " + e);
				}
				for(String e : date_end) {
					System.out.println("date_end: " + e);
				}
				for(String e : time_end) {
					System.out.println("time_end: " + e);
				}
				for(String e : deadLine) {
					System.out.println("deadLine: " + e);
				}
				
				//Setup complex datatypes - komplexe Datentypen konstruieren
				LocationData location = new LocationData(street, town, postcode, country);
				TimeData startTime = new TimeData(Integer.valueOf(date_start[2]), 
													Integer.valueOf(date_start[1]), 
													Integer.valueOf(date_start[0]), 
													Integer.valueOf(time_start[0]), 
													Integer.valueOf(time_start[1]), 
													Integer.valueOf(time_start[2]));
				TimeData endTime = new TimeData(Integer.valueOf(date_end[2]), 
													Integer.valueOf(date_end[1]), 
													Integer.valueOf(date_end[0]), 
													Integer.valueOf(time_end[0]), 
													Integer.valueOf(time_end[1]), 
													Integer.valueOf(time_end[2]));
				TimeData deadline = new TimeData(Integer.valueOf(deadLine[2]), 
													Integer.valueOf(deadLine[1]), 
													Integer.valueOf(deadLine[0]), 
													Integer.valueOf(deadLine[3]), 
													Integer.valueOf(deadLine[4]), 
													Integer.valueOf(deadLine[5]));
				
				
				Boolean success = app.makeAppointmentRequest(cid, name, description, location, startTime, endTime, deadline);
				
				if(success){
					System.out.println(success);
					request.setAttribute("pagetitle", "Success");
					request.setAttribute("navtype", "showCalendar");
					request.getRequestDispatcher("/templates/showCreateConfirmation.ftl").forward(request, response);
				 }else{
					request.setAttribute("pagetitle", "Fail");
					request.setAttribute("navtype", "showCalendar");
					request.getRequestDispatcher("/templates/showCreateFail.ftl").forward(request, response);
				 } 
				break;
				
			default:
				System.out.println("GroupmemberGUI: Action not found.." + (String)request.getAttribute("action"));
		}
		
		
	}

}
