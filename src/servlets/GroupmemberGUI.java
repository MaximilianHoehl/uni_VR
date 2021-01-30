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
		System.out.println(request.getParameter("action"));
		
		switch((String)request.getParameter("action")){
			case "showCalendar":
				request.setAttribute("pagetitle", "My Calendar");
				request.setAttribute("navtype", "showCalendar");

				int cid = Integer.valueOf(request.getParameter("identity"));
				GroupCalendar res = app.getCalendarInfos(cid, null, null, null, null);
				
				request.setAttribute("GroupCalendarInfo", res);
				request.setAttribute("AppointmentList", res.getAppointments());
				
				request.getRequestDispatcher("/templates/selectGCWebpage.ftl").forward(request, response);
				break;
			case "createAppointment":
				
				LocationData testLoc = new LocationData(null, null, 0, null);
				TimeData faketd = new TimeData(2020, 11, 30, 12, 10, 10);
				TimeData faketd2 = new TimeData(2020, 12, 22, 13, 11, 12);
				Boolean success = app.makeAppointmentRequest(0, "name", "desc", testLoc, faketd, faketd, faketd);
				if(success){
					System.out.println(success);
					request.getRequestDispatcher("/templates/showCreateConfirmation.ftl").forward(request, response);
				 }else{
				  	
					request.getRequestDispatcher("/templates/showCreateFail.ftl").forward(request, response);
				 } 
				break;
			default:
				System.out.println("GroupmemberGUI: Action not found.." + (String)request.getAttribute("action"));
		}
		
		
	}

}
