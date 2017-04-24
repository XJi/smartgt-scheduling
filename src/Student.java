import java.util.ArrayList;

import util.Time;

public class Student {
	private int id;
	private ArrayList<Lecture> lectures;
	int preference;
	int diningDuration = 30;
	int crcDuration = 30;
	
	Student(int id, int preference){
		this.id = id;
		this.preference = preference;
		this.lectures = new ArrayList<Lecture>();
	}
	
	public void setLectures(Lecture lecture){
		this.getLectures().add(lecture);
	}
	
	public ArrayList<Lecture> getLectures(){
		return this.lectures;
	}
	
	public boolean isAvailable(int time, int duration){
		ArrayList<Lecture> lec = this.getLectures();
		for(int i = 0; i < lec.size(); i++){
			if(lec.get(i).getEndTime() < time){
				if(i < lec.size()-1){
					if(Time.convertTime(time, duration)<lec.get(i+1).getStartTime())
						return true;
				}
			}
		}
		return false;
	}
	

	public static void main(String args[]){
		Student s = new Student(1, 1);
		s.setLectures(new Lecture("Monday",930,1055));
		System.out.println(s.getLectures().get(0).getDay());
	}


	
}