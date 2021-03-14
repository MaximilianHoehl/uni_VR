package timer;

import java.util.Timer;
import java.util.TimerTask;

import application.CA_Application;

//import application.VRApplication;

/**
 * Timer class to call the method checkPayment in the application. Main method
 * can be executed in a scheduled way.
 * 
 * @author swe.uni-due.de
 *
 */
public class FinalizationTimer {

	public static void main(String[] args) {
		
		CA_Application ca = CA_Application.createInstance();
		
		TimerTask ts = new TimerTask() {
			
			public void run() {
				ca.checkFinalization();
			}
		};
		Timer timer = new Timer();
		timer.schedule(ts, 1000, 2500);
	}
}
