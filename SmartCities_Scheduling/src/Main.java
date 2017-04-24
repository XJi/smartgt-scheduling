import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import util.Constant;

public class Main {
	private ArrayList<Student> students;
	static int STUDENT_ID = 101;
	private ArrayList<Pair> diningPool;
	private ArrayList<Pair> crcPool;
	private int expMinDining = 0;
	private int expMinCrc = 0;
	
	public Student findStudent(int id){
		for(Student s: students){
			if(s.getID() == id)
				return s;
		}
		return null;
	}
	public void removeStudentFromPool(int id,ArrayList<Pair> pool){
		int[] count = {-1,-1,1-1,-1,-1};
		int index = 0;
		for(int i = 0; i < pool.size();i++){
			if(id == pool.get(i).getPairId())
				count[index++] = i;
		}
		for(int i = index-1; i>=0; i--){
			pool.remove(count[i]);
		}
	}
	
	public void printPool(ArrayList<Pair> pool){
		System.out.println("START--POOL");
		for(Pair p:  pool){
			System.out.println("ID: "+p.getPairId()+",startTime: "+p.getPairLecture().getStartTime()+",endTime: "+p.getPairLecture().getEndTime());
		}
		System.out.println("END--POOL");
	}
	
	public ArrayList<Student> addStudent(String fileName){
		JSONArray jsonObject = readFile(fileName);//"schedule.json");
		JSONArray jsonArray = (JSONArray) jsonObject.get(0);
		//System.out.println(jsonArray.get(3));
		//System.out.println(jsonArray.size());
		Student student = new Student(STUDENT_ID, 0);
		for (int i=0; i<jsonArray.size(); ++i) {
			JSONObject temp_jsonObject = (JSONObject) jsonArray.get(i);
			JSONArray meeting = (JSONArray) temp_jsonObject.get("meeting_times");
			JSONObject jsonObject3 = (JSONObject) meeting.get(0);
			constructLecture(jsonObject3,student);			
		}
		if(students == null)
			students = new ArrayList<Student>();
		students.add(student);
		//student.printStudentSchedule();
		STUDENT_ID += 1;
		System.out.println("Size is "+students.get(students.size()-1).getLectures("R").size());
		return students;
	}
	
	public void scheduleTask(Schedule schedule, int prefer, boolean compromise, ArrayList<Pair> pool,  ArrayList<Pair> minorPool){
		ArrayList<TimeSlot> time_slots;
		int dummy = 0;
		if(prefer ==Constant.PREFER_DINING){
			time_slots= schedule.getDining();
			if(!compromise)  
				dummy = expMinCrc;
		}
		else{
			time_slots= schedule.getCrc();
			if(!compromise)
				dummy = expMinDining;
		}

		while(pool.size()>dummy){
			boolean scheduled = false;
			System.out.println("pool size is "+pool.size());
			for(int j = 0; j < time_slots.size()&&!scheduled;j++){
				//System.out.println("Remain space is "+time_slots.get(j).getRemain()+"slot is"+j);
				dummy++;
				if(time_slots.get(j).getRemain() > 0){
					Student tempS = findStudent(pool.get(0).getPairId());
					if(tempS.getPreference() == prefer || (tempS.getPreference() != prefer && compromise)){
						//tempS.printStudentSchedule();
						if(tempS.isAvailable(time_slots.get(j).getStartTime(), tempS.diningDuration, Constant.weekday)){
							System.out.println("Available ^^!");
							if(schedule.updateSchedule(pool.get(0).getPairLecture().getEndTime(), tempS.diningDuration,0)){
								//Scheduled that person to the current time slot
								//Remove all that person's pairs in the diningPool 
								System.out.println("SCHEDULED! Remove students in pool");
								if(minorPool.size() > 0){  //The minorPool hasn't been scheduled
									minorPool.add(new Pair(pool.get(0).getPairId(),new Lecture(Constant.weekday, schedule.getStartTime(),schedule.getEndTime())));
								}
								removeStudentFromPool(pool.get(0).getPairId(),pool);
								scheduled = true;
								printPool(pool);
							}
							else diningPool.remove(0);
						}
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
		//Load the schedule for Dining and CRC
		Schedule schedule = new Schedule();
		m.diningPool = new ArrayList<Pair>();
		m.crcPool = new ArrayList<Pair>();
		//Create pools of lectures from all students
		for(int i = 0; i < m.students.size(); i++){
			ArrayList<Lecture> tempLec =  m.students.get(i).getLectures(Constant.weekday);
			for(Lecture lecture: tempLec){
				m.diningPool.add(new Pair(m.students.get(i).getID(),lecture));
				m.crcPool.add(new Pair(m.students.get(i).getID(),lecture));
				if(m.students.get(i).getPreference() == Constant.PREFER_DINING)
					m.expMinDining++;
				else m.expMinCrc++;
			}
		}
		//Sort all lectures in the earliest endTime order
		Collections.sort(m.diningPool,Pair.getCompByEndtime());
		Collections.sort(m.crcPool,Pair.getCompByEndtime());
		m.printPool(m.diningPool);
		//Greedy Activity Selection
		System.out.println("Dining pool size: crc pool size = " +m.diningPool.size()+": "+m.crcPool.size());
		m.scheduleTask(schedule, Constant.PREFER_DINING, false, m.diningPool, m.crcPool); //No compromise if can't get scheduled;
		m.scheduleTask(schedule, Constant.PREFER_CRC, false, m.crcPool, m.diningPool); 
		m.scheduleTask(schedule, Constant.PREFER_DINING,true, m.diningPool, m.crcPool); 
		m.scheduleTask(schedule, Constant.PREFER_CRC, true, m.crcPool, m.diningPool); 
		/*while(m.diningPool.size()>0 ){
			ArrayList<TimeSlot> dining = schedule.getDining();
			boolean scheduled = false;
			System.out.println("pool size is "+m.diningPool.size());
			for(int j = 0; j < dining.size()&&!scheduled;j++){
				System.out.println("Remain space is "+dining.get(j).getRemain()+"slot is"+j);
				if(dining.get(j).getRemain() > 0){
					Student tempS = m.findStudent(m.diningPool.get(0).getPairId());
					System.out.println(tempS.getLectures("T").get(0).getStartTime() +" "+tempS.getLectures("T").get(1).getStartTime());
					//tempS.printStudentSchedule();
					if(tempS.isAvailable(dining.get(j).getStartTime(), tempS.diningDuration, "T")){
						System.out.println("Available ^^!");
						if(schedule.updateSchedule(m.diningPool.get(0).getPairLecture().getEndTime(), tempS.diningDuration,0)){
							//Scheduled that person to the current time slot
							//Remove all that person's pairs in the diningPool 
							System.out.println("SCHEDULED! Remove students in pool");
							m.removeStudentFromPool(m.diningPool.get(0).getPairId(),m.diningPool);
							scheduled = true;
						}
						else m.diningPool.remove(0);
					}
				}	
			}
		}*/

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
}