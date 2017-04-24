import java.util.ArrayList;
import java.util.HashMap;

import util.Constant;
import util.Time;

public class Student {
	private int id;
	private HashMap<String, ArrayList<Lecture>> lectures;
	int preference;
	int diningDuration = 30;
	int crcDuration = 30;
	
	Student(int id, int preference){
		this.id = id;
		this.preference = preference;
		this.lectures = new HashMap<String,ArrayList<Lecture>>();
		for(int i = 0; i < Constant.weekdays.length; i++){
			this.lectures.put(Constant.weekdays[i], new ArrayList<Lecture>());
		}
	}
	
	public void setLectures(Lecture lecture){
		this.getLectures(lecture.getDay()).add(lecture);
	}
	
	public ArrayList<Lecture> getLectures(String weekday){
		return this.lectures.get(weekday);
	}
	public int getPreference(){
		return this.preference;
	}
	public int getID(){
		return this.id;
	}
	public boolean isAvailable(int time, int duration,String weekday){
		System.out.println("@isAvailable: Startime: "+time+",duration"+duration+",weekday:"+weekday);
		ArrayList<Lecture> lec = this.getLectures(weekday);
		for(int i = 0; i < lec.size(); i++){
			if(lec.get(i).getEndTime() < time){
				System.out.println("@isAvailable: getEndTime: "+lec.get(i).getEndTime());
				if(i < lec.size()-1){
					if(Time.convertTime(time, duration)<lec.get(i+1).getStartTime())
						return true;
				}
				else return true;
			}
		}
		return false;
	}
	
	public void printStudentSchedule(){
		ArrayList<Lecture> l;
		for(int i = 0; i <Constant.weekdays.length;i++ ){
			 l= this.lectures.get(Constant.weekdays[i]);
			 if(l == null || l.size() == 0){
				 System.out.println("l is null");
			 }
			 for(Lecture lec: l){
				 System.out.println("Day: "+lec.getDay()+", startTime: "+lec.getStartTime()+", endTime: "+lec.getEndTime());
			 }
		}
	}
	public static void main(String args[]){
		Student s = new Student(1, 1);
		s.setLectures(new Lecture("M",930,1055));
		
		s.setLectures(new Lecture("T",930,1055));
		System.out.println(s.getLectures("M").get(0).getDay());
		System.out.println(s.isAvailable(1035,30,"T"));
		s.printStudentSchedule();
	}


	
}