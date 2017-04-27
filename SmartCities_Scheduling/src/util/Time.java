package util;

public class Time {
	
	/*Assumption: time is formatted as a four digit integer
	 * For instance: 11:30 AM => 1130;
	 * 				 1:30 PM => 1330,
	 * */
	public static int convertTime(int time, int duration){
		int temp = 0;
		if(time%100+duration > 60){
			temp = duration-60;
			time += 100+temp;	
		}
		else if(time%100+duration == 60)
			time = (time+100)/100 * 100;
		else time += duration;
		return time;
	}
	
	public static void main(String args[]){
		System.out.println(convertTime(1155, 57));
		System.out.println(convertTime(1150, 10));
		System.out.println(convertTime(1105, 30));
	}
}
