package servlets;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.CA_Application;
import datatypes.Appointment;
import datatypes.Suggestion;
import datatypes.TimeData;
import dbadapter.DBFacade;
import debugging.Debugging;

//Returns Index
public class AppointmentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	ArrayList<Suggestion> appointmentSuggestions;
	int clickedAppointmentID;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {

		// Set pagetitle and navtype
		request.setAttribute("pagetitle", "Welcome");
		request.setAttribute("navtype", "general");
		
		clickedAppointmentID = Integer.valueOf(request.getParameter("clickedAppointmentID"));
		CA_Application ca = CA_Application.createInstance();
		
		appointmentSuggestions = ca.getSuggestions(clickedAppointmentID);
		for(Suggestion s : appointmentSuggestions) {
			System.out.println("-----SUGGESTION-----");
			System.out.println(s.getId());
			System.out.println(s.getConfirmations());
			System.out.println(s.getRequiredConfirmations());
		}
		//request.setAttribute("appointmentName", appointmentSuggestions.get(0).getAppointmentName());
		request.setAttribute("suggestions", appointmentSuggestions);
		try {
			
			request.getRequestDispatcher("/templates/appointment.ftl").forward(request, response);
		} catch (ServletException | IOException e) {
			
			request.setAttribute("errormessage", "Template error: please contact the administrator");
			e.printStackTrace();
		}
	}

	/**
	 * Call doGet instead of doPost
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		
		CA_Application ca = CA_Application.createInstance();
		switch((String)request.getParameter("action")){
			case "selectSuggestion":
				int selectedSuggestionId = Integer.valueOf(request.getParameter("selectedSuggestion"));
				
				Suggestion selectedSuggestion = null;
				for(Suggestion s : appointmentSuggestions) {
					if(s.getId() == selectedSuggestionId) {
						selectedSuggestion = s;
						break;
					}
				}
				if(selectedSuggestion==null) {
					System.out.print("ERROR: APPOINTMENTSERVLET: SELECTEDSUGGESTION NOT FOUND!");
				}
				int uid = Debugging.getCurrentUser();
				int sid = selectedSuggestion.getId();
				int aid = selectedSuggestion.getAid();
				if(ca.selectingDate(uid, sid, aid)) {
					request.setAttribute("success", 1);
				}else {
					request.setAttribute("success", 0);
				}
				
			try {
				request.setAttribute("pagetitle", "Welcome");
				request.setAttribute("navtype", "general");
				request.getRequestDispatcher("/templates/showselectstatus.ftl").forward(request, response);
			} catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
			case "newSuggestion":
				
				String startTime_date = request.getParameter("startDate");
				String startTime_time = request.getParameter("startTime");
				String endTime_date = request.getParameter("endDate");
				String endTime_time = request.getParameter("endTime");
				
				String[] date_start = startTime_date.split(":");
				String[] time_start = startTime_time.split(":");
				String[] date_end = endTime_date.split(":");
				String[] time_end = endTime_time.split(":");
				
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
				
				int userID = Debugging.getCurrentUser();
				
				if(ca.suggestingDate(userID, clickedAppointmentID, startTime, endTime)) {
					request.setAttribute("success", 1); //TRUE
				}else {
					request.setAttribute("success", 0); //FALSE
				}
				try {
					request.setAttribute("pagetitle", "Welcome");
					request.setAttribute("navtype", "general");
					request.getRequestDispatcher("/templates/showSuggestStatus.ftl").forward(request, response);
				} catch (ServletException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			default:
				break;
		}
	}
}