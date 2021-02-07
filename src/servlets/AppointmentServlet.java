package servlets;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		DBFacade df = DBFacade.getInstance();
		Appointment appo = df.getAppointmentById(id);
		
		
		
		request.setAttribute("appointmentName", appo.getName());
		//request.setAttribute("suggestions", suggestinList);
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