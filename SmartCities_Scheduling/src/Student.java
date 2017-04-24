import java.util.ArrayList;


public class Student {
	private int id;
	private ArrayList<Lecture> lectures;
	int preference;
	
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
}
