import java.util.ArrayList;

import util.Constant;

public class TimeSlot {
	private int startTime;
	private int endTime;
	private int remain = Constant.space;
	private ArrayList<Integer> students;
	
	public TimeSlot(int startTime, int endTime, int remain) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.remain = remain;
		students = new ArrayList<Integer>();
	}
	
	public int getStartTime(){
		return this.startTime;
	}
	
	public int getEndTime(){
		return this.endTime;
	}
	
	public int getRemain(){
		return this.remain;
	}
	
	public void setRemain(int value){
		this.remain += value;
	}
	
	public void updateSlot(int id){
		this.students.add(id);
	}
	
	public ArrayList<Integer> getUsers(){
		return this.students;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
