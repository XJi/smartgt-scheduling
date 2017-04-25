import java.util.Comparator;

public class Lecture implements Comparator{
	private String day;
	private int startTime;
	private int endTime;
	
	Lecture(String day, int startTime, int endTime){
		this.day = day;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public String getDay(){
		return this.day;
	}
	
	public void setDay(String day){
		this.day = day;
	}
	
	public void setStartTime(int startTime){
		this.startTime = startTime;
	}
	public int getStartTime(){
		return this.startTime;
	}
	
	public void setEndTime(int endTime){
		this.endTime = endTime;
	}
	
	public int getEndTime(){
		return this.endTime;
	}
	
	@Override
	public int compare(Object o1, Object o2) {	
		return ((Lecture) o1).getEndTime()-((Lecture) o2).getEndTime();
	}
	
}
