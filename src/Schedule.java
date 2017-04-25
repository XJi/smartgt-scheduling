import java.util.ArrayList;

import util.Constant;

public class Schedule {
	private ArrayList<TimeSlot> dining;
	private ArrayList<TimeSlot> crc;
	private ArrayList<Student> students;
	public Schedule(){
		this.dining = new ArrayList<TimeSlot>();
		this.crc = new ArrayList<TimeSlot>();
		for(int i = 0; i < Constant.slots; i++){
			dining.add(new TimeSlot(Constant.start[i],Constant.end[i],Constant.slots));
			crc.add(new TimeSlot(Constant.start[i],Constant.end[i],Constant.slots));
		}
	}
	public static void main(String args[]){
		Schedule schedule = new Schedule();
		
	}

}
