
public class Lecture {
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
}
