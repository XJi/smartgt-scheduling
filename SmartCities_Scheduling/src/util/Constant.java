package util;

public class Constant {
	public static final int space = 2;
	
	public static final int start[] = {800,830,900,930,1000,1030,1100,1130,1200,1230,
									1300,1330,1400,1430,1500,1530,1600,1630,
									1700,1730,1800,1830,1900,1930,2000,2030	};
	public static final int end[] = {830,900,930,1000,1030,1100,1130,1200,1230,
									1300,1330,1400,1430,1500,1530,1600,1630,
									1700,1730,1800,1830,1900,1930,2000,2030,2100};
	
	public static final int slots = start.length;
	public static final String[] weekdays = {"M","T","W","R","F"};
	public static final String[] users = {"schedule.json","schedule.json","schedule.json","schedule.json","schedule.json","schedule.json","schedule.json","schedule.json","schedule.json"};
	public static final int numberOfUsers = users.length;
	public static final int defaultDuration = 30;
	public static final String weekday = "R";
	public static final int PREFER_CRC = 1;
	public static final int PREFER_DINING = 0;
	
}
