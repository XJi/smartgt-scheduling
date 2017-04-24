import java.util.ArrayList;

import util.Constant;

public class Schedule {
	private ArrayList<TimeSlot> dining;
	private ArrayList<TimeSlot> crc;
	private int sTime = -1;
	private int endTime = -1;
	public Schedule(){
		this.dining = new ArrayList<TimeSlot>();
		this.crc = new ArrayList<TimeSlot>();
		for(int i = 0; i < Constant.slots; i++){
			if(i+6 < Constant.slots)
				dining.add(new TimeSlot(Constant.start[i+6],Constant.end[i+6],Constant.space));
			crc.add(new TimeSlot(Constant.start[i],Constant.end[i],Constant.space));
		}
		System.out.println("Total crc slots = "+crc.size()+"; Total dining slots = "+dining.size());
	}
	
	public ArrayList<TimeSlot> getCrc(){
		return this.crc;
	}
	
	public ArrayList<TimeSlot> getDining(){
		return this.dining;
	}
	
	public int getStartTime(){
		return this.sTime;
	}
	
	public int getEndTime(){
		return this.endTime;
	}
	
	public void setStartTime(int time){
		this.sTime = time;
	}
	
	public void setEndTime(int time){
		this.endTime = time;
	}
	
	public int findSlotIndex(int startTime, int type){
		int index = 0;

		
		return index;
	}
	public boolean updateSchedule(int startTime, int duration, int type){
		ArrayList<TimeSlot> temp;
		if(type == 0) // 0 refers to Dining
			temp = this.dining;
		else temp = this.crc;
		for(int i = 0; i < temp.size(); i++){
			if(temp.get(i).getStartTime() >= startTime){
				if(duration <= Constant.defaultDuration ){
					if(temp.get(i).getRemain() > 0){
						temp.get(i).setRemain(-1);  // Remain_space = Remain_space -1
						System.out.println("@updateSchedule: start: "+temp.get(i).getStartTime()+"-> end: "+temp.get(i).getEndTime());
						sTime = temp.get(i).getStartTime();
						endTime = temp.get(i).getEndTime();
						return true;
					}
				}
				else{
					if(i+1 < temp.size() && temp.get(i).getRemain() > 0 && temp.get(i+1).getRemain() > 0){
						temp.get(i).setRemain(-1);
						temp.get(i+1).setRemain(-1);
						System.out.println("@updateSchedule: start: "+temp.get(i).getStartTime()+"-> end: "+temp.get(i+1).getEndTime());
						sTime = temp.get(i).getStartTime();
						endTime = temp.get(i+1).getEndTime();
						return true;
					}
				}
				if(i+1 >= temp.size() && temp.get(i).getRemain() > 0){
					temp.get(i).setRemain(-1); 
					sTime = temp.get(i).getStartTime();
					endTime = temp.get(i).getEndTime();
					return true;
				}
			}
		}
		return false;
	}
	
	public static void main(String args[]){
		Schedule schedule = new Schedule();
		
	}

}
