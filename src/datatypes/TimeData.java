package datatypes;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeData {
	
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private int second;
	
	public TimeData(int year, int month, int day, int hour, int minute, int second) {
		
		
		this.setYear(year);
		this.setMonth(month);
		this.setDay(day);
		this.setHour(hour);
		this.setMinute(minute);
		this.setSecond(second);	
		
	}
	
	@SuppressWarnings("deprecation")
	public TimeData(Timestamp timestamp) {
		
		String[] e = timestamp.toString().split("\\s+");
		for(String i : e) {
			System.out.println(i);
		}
		
		
		year = Integer.valueOf(e[0].split("-")[0]);
		month = Integer.valueOf(e[0].split("-")[1]);
		day = Integer.valueOf(e[0].split("-")[2]);
		hour = Integer.valueOf(e[1].split(":")[0]);
		minute = Integer.valueOf(e[1].split(":")[1]);
		second = Math.round(Float.valueOf(e[1].split(":")[2]));
		
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}
	
	//Get Values as String for representation
	public String getYearString() {
		
		String yearString = String.valueOf(year);
		return yearString;
	}
	public String getMonthString() {
		
		String monthString = convert(month);
		return monthString;
	}
	public String getDayString() {
		
		String dayString = convert(day);
		return dayString;
	}
	public String getHourString() {
		
		String hourString = convert(hour);
		return hourString;
	}
	public String getMinuteString() {
		
		String minuteString = convert(minute);
		return minuteString;
	}
	public String getSecondString() {
		
		String secondString = convert(second);
		return secondString;
	}
	//Specal Getter
	public Timestamp getTimestamp() throws ParseException {
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("TimeData raw vars: " + year + " " + month + " " + day + " " + hour + " " + minute + " " + second + " ");
		
		Date date = df.parse(String.valueOf(year) + "-" 
							+ String.valueOf(month) + "-" 
							+ String.valueOf(day) + " " 
							+ String.valueOf(hour) + ":" 
							+ String.valueOf(minute) + ":" 
							+ String.valueOf(second));
		System.out.println("Timedata Dateobject-String" + date.toString());
		long time = date.getTime();
		
		return new Timestamp(time);
	}
	//Internal methods
	private String convert(int inp) {
		
		return inp>9?String.valueOf(inp):"0" +String.valueOf(inp);
	}

}
