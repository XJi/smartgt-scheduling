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
			if(i+6 < Constant.slots && i < Constant.slots-12)
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
	public boolean updateSchedule(int startIndex, int duration, ArrayList<TimeSlot> temp,int id){
		if(duration <= Constant.defaultDuration || startIndex == temp.size()-1){
			temp.get(startIndex).setRemain(-1);
			//System.out.println("@updateSchedule simple: start: "+temp.get(startIndex).getStartTime()+"-> end: "+temp.get(startIndex).getEndTime());
			sTime = temp.get(startIndex).getStartTime();
			endTime = temp.get(startIndex).getEndTime();
			temp.get(startIndex).updateSlot(id);
			return true;
		}
		else{
			if(startIndex+1 < temp.size() && temp.get(startIndex+1).getRemain() > 0){
				temp.get(startIndex).setRemain(-1);
				temp.get(startIndex+1).setRemain(-1);
				//System.out.println("@updateSchedule simple: start: "+temp.get(startIndex).getStartTime()+"-> end: "+temp.get(startIndex+1).getEndTime());
				sTime = temp.get(startIndex).getStartTime();
				endTime = temp.get(startIndex+1).getEndTime();
				temp.get(startIndex).updateSlot(id);
				return true;
			}
		}
		return false;
	}
	
	public void printSchedule(){
		System.out.println("============DINING SCHEDULE=============");
		for(TimeSlot slot: dining){
			System.out.print("StartTime: "+slot.getStartTime()+",   EndTime: "+slot.getEndTime()+",  Remain Space: "+slot.getRemain()+" ");
			if(slot.getUsers().size() > 0){
				for(Integer s: slot.getUsers()){
					System.out.print("id: "+s+" ");
				}
			}
			System.out.println();
		}
		System.out.println("============CRC SCHEDULE=============");
		for(TimeSlot slot: crc){
			System.out.print("StartTime: "+slot.getStartTime()+",   EndTime: "+slot.getEndTime()+",  Remain Space: "+slot.getRemain()+"  ");
			if(slot.getUsers().size() > 0){
				for(Integer s: slot.getUsers()){
					System.out.print("id: "+s+" ");
				}
			}
			System.out.println();
		}
		
	}
	
	public static void main(String args[]){
		Schedule schedule = new Schedule();
		
	}

}
