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
import dbadapter.DBFacade;

//Returns Index
public class AppointmentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {

		// Set pagetitle and navtype
		request.setAttribute("pagetitle", "Welcome");
		request.setAttribute("navtype", "general");
		
		int id = Integer.valueOf(request.getParameter("clickedAppointmentID"));
		CA_Application ca = CA_Application.createInstance();
		
		ArrayList<Suggestion> appointmentSuggestions = ca.getSuggestions(id);
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
		doGet(request, response);
	}
}