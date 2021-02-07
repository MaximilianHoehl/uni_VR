package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.CA_Application;
import datatypes.Appointment;
import dbadapter.DBFacade;

//Returns Index
public class SelectAServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {

		// Set pagetitle and navtype
		request.setAttribute("pagetitle", "Unfinalized Appointments");
		request.setAttribute("navtype", "general");
		ArrayList<Appointment> appointmentList = new ArrayList<Appointment>();
		CA_Application ca = CA_Application.createInstance();
		appointmentList = ca.getUnfinalizedAppointments(1); //hardcoded CalendarID
		
		request.setAttribute("appointmentList", appointmentList);

		try {
			
			request.getRequestDispatcher("/templates/selectAWebPage.ftl").forward(request, response);
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