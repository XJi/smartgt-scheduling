import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import util.Constant;

public class Main {
	private ArrayList<Student> students;
	static int STUDENT_ID = 101;
	private ArrayList<Pair> diningPool;
	private ArrayList<Pair> crcPool;
	private ArrayList<Pair> compDiningPool;
	private ArrayList<Pair> compCrcPool;
	
	public Student findStudent(int id){
		for(Student s: students){
			if(s.getID() == id)
				return s;
		}
		return null;
	}
	public void removeStudentFromPool(int id,ArrayList<Pair> pool){
		int[] count = {-1,-1,-1,-1,-1,-1};
		int index = 0;
		for(int i = 0; i < pool.size();i++){
			if(id == pool.get(i).getPairId())
				count[index++] = i;
		}
		for(int i = index-1; i>=0; i--){
			pool.remove(count[i]);
		}
	}
	

	
	public ArrayList<Student> addStudent(String fileName){
		JSONArray jsonObject = readFile(fileName);
		JSONArray jsonArray = (JSONArray) jsonObject.get(0);
		Random random = new Random();
		int index = random.nextInt(2);
		Student student = new Student(STUDENT_ID, index);
		for (int i=0; i<jsonArray.size(); ++i) {
			JSONObject temp_jsonObject = (JSONObject) jsonArray.get(i);
			JSONArray meeting = (JSONArray) temp_jsonObject.get("meeting_times");
			JSONObject jsonObject3 = (JSONObject) meeting.get(0);
			constructLecture(jsonObject3,student);			
		}
		if(students == null)
			students = new ArrayList<Student>();
		students.add(student);
		STUDENT_ID += 1;
		return students;
	}
	
	public void scheduleTask(Schedule schedule, int prefer, boolean compromise, ArrayList<Pair> pool,  ArrayList<Pair> minorPool){
		ArrayList<TimeSlot> time_slots = (prefer ==Constant.PREFER_DINING )?schedule.getDining():schedule.getCrc();
		while(pool.size() > 0){
			boolean scheduled = false;			
			for(int j = 0; j < time_slots.size()&&!scheduled;j++){
				if(time_slots.get(j).getRemain() > 0 && pool.size() >0){
					if(j > 18) System.out.println("Reach 19!");
					Student tempS = findStudent(pool.get(0).getPairId());
					if(tempS.getPreference() == prefer || (tempS.getPreference() != prefer && compromise)){
						int durationTime = (time_slots.equals(schedule.getCrc()))?tempS.crcDuration:tempS.diningDuration;
						int sType = (time_slots.equals(schedule.getCrc()))?Constant.PREFER_CRC:Constant.PREFER_DINING;
						if(tempS.isAvailable(time_slots.get(j).getStartTime(), durationTime, Constant.weekday)){
							System.out.println("Available ^^!"+time_slots.get(j).getStartTime()+",Type: "+sType);
							if(schedule.updateSchedule(j, durationTime,time_slots)){
								/*Scheduled that person to the current time slot
								Remove all that person's pairs in the diningPool */
								System.out.println("SCHEDULED! Remove students in pool: "+pool.get(0).getPairId());
								if(minorPool != null && minorPool.size() > 0){  //The minorPool hasn't been scheduled
									minorPool.add(new Pair(pool.get(0).getPairId(),
											new Lecture(Constant.weekday, schedule.getStartTime(),schedule.getEndTime())));
									tempS.setLectures(new Lecture(Constant.weekday, schedule.getStartTime(),schedule.getEndTime()));
								}
								removeStudentFromPool(pool.get(0).getPairId(),pool);
								scheduled = true;								
							}
							else if(minorPool != null){
								minorPool.add(pool.remove(0));
							}
						}
						else pool.remove(0);
					}
				}	
			}
		}
	}

	public static void main(String[] args) throws IOException, ParseException {
		Main m = new Main();
		//Add students who request at the same time to this system
		for(int i = 0; i < Constant.numberOfUsers; i++){
			m.addStudent(Constant.users[i]);
		}
		m.printStudents(m.students);
		for(Student s: m.students){
			for(int i = 0; i < Constant.weekdays.length; i++){
				if (s.getLectures(Constant.weekdays[i]) == null || s.getLectures(Constant.weekdays[i]).size() == 0){
					s.setLectures(new Lecture(Constant.weekdays[i], 2100, 2130));
				}
			}
		}
		/*Load the schedule for Dining and CRC*/
		Schedule schedule = new Schedule();
		m.diningPool = new ArrayList<Pair>();
		m.crcPool = new ArrayList<Pair>();
		m.compDiningPool = new ArrayList<Pair>();
		m.compCrcPool = new ArrayList<Pair>();
		/*Create pools of lectures from all students*/
		for(int i = 0; i < m.students.size(); i++){
			//Get lectures for a student on certain weekday
			ArrayList<Lecture> tempLec =  m.students.get(i).getLectures(Constant.weekday);
			for(Lecture lecture: tempLec){
				if(m.students.get(i).getPreference() == Constant.PREFER_DINING){ 
					m.diningPool.add(new Pair(m.students.get(i).getID(),lecture));
					m.compCrcPool.add(new Pair(m.students.get(i).getID(),lecture));
				}
				else{
					m.crcPool.add(new Pair(m.students.get(i).getID(),lecture));
					m.compDiningPool.add(new Pair(m.students.get(i).getID(),lecture));
				}
			}
		}
		//Sort all lectures in the earliest endTime order
		Collections.sort(m.diningPool,Pair.getCompByEndtime());
		Collections.sort(m.crcPool,Pair.getCompByEndtime());
		m.printPool(m.diningPool);
		//Greedy Activity Selection
		System.out.println("Dining pool size: crc pool size = " +m.diningPool.size()+": "+m.crcPool.size());
		m.scheduleTask(schedule, Constant.PREFER_DINING, false, m.diningPool, m.compCrcPool); //No compromise if can't get scheduled;
		m.scheduleTask(schedule, Constant.PREFER_CRC, false, m.crcPool, m.compDiningPool);
		System.out.println("Dining pool size: "+m.diningPool.size()+", CRC pool size: "+m.crcPool.size());
		
		Collections.sort(m.compDiningPool,Pair.getCompByEndtime());
		Collections.sort(m.compCrcPool,Pair.getCompByEndtime());
		m.printPool(m.compDiningPool);
		m.printPool(m.compCrcPool);
		
		//m.scheduleTask(schedule, Constant.PREFER_DINING,true, m.compDiningPool,  null);
		m.scheduleTask(schedule, Constant.PREFER_CRC, true, m.compCrcPool, null); 
		schedule.printSchedule();
	}
	
	public static JSONArray readFile(String filename) {
		JSONParser parser = new JSONParser();
		try {
			JSONArray jsonObject = (JSONArray) parser.parse(new FileReader(filename));
			return jsonObject;
		} catch (IOException | ParseException e) {
			System.out.println("Cannot read file");
			e.printStackTrace();
			System.exit(-1);
			return null;
		}
	}
	
	public static void constructLecture(JSONObject jsonObject, Student s) {
		String days = (String) jsonObject.get("days");
		for(int i = 0; i < days.length();i++){
			int begin_time = Integer.parseInt((String) jsonObject.get("begin_time"));
			int end_time = Integer.parseInt((String) jsonObject.get("end_time"));
			String day = ""+days.charAt(i);
			Lecture lecture = new Lecture(day, begin_time, end_time);
			s.setLectures(lecture);
		}
	}
	
	public void printPool(ArrayList<Pair> pool){
		if(pool.equals(diningPool)|| pool.equals(compDiningPool)) System.out.print("Dining Pool ");
		else System.out.print("CRC Pool ");
		System.out.println("START--POOL");
		for(Pair p:  pool){
			System.out.println("ID: "+p.getPairId()+",startTime: "+p.getPairLecture().getStartTime()+",endTime: "+p.getPairLecture().getEndTime());
		}
		System.out.println("END--POOL");
	}
	
	public void printStudents(ArrayList<Student> s){
		System.out.println("============STUDENTS LIST=============");
		for(Student student: s){
			System.out.println("ID: "+student.getID() + ", Preference: "+student.getPreference());
		}
		System.out.println("============END=============");
	}
}