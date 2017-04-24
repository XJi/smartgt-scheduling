import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Main {

static int STUDENT_ID = 101;
	
	public static void main(String[] args) throws IOException, ParseException {
		JSONArray jsonObject = readFile("schedule.json");
		
		JSONArray jsonArray = (JSONArray) jsonObject.get(0);
//		System.out.println(jsonArray.get(0));
//		System.out.println(jsonArray.size());
		Student student = new Student(STUDENT_ID, 0);
		for (int i=0; i<jsonArray.size(); ++i) {
			JSONObject temp_jsonObject = (JSONObject) jsonArray.get(i);
			JSONArray meeting = (JSONArray) temp_jsonObject.get("meeting_times");
			JSONObject jsonObject3 = (JSONObject) meeting.get(0);
			ArrayList<Lecture> lectures = getLectures(jsonObject3);
			for (Lecture lecture: lectures) {
				student.setLectures(lecture);
			}
		}
		STUDENT_ID += 1;
	}
	
	public static JSONArray readFile(String filename) {
		JSONParser parser = new JSONParser();
		try {
			JSONArray jsonObject = (JSONArray) parser.parse(new FileReader(filename));
			return jsonObject;
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<Lecture> getLectures(JSONObject jsonObject) {
		ArrayList<Lecture> lectures = new ArrayList<>();
		String day = (String) jsonObject.get("days");
//		System.out.println(day);
		int begin_time = Integer.parseInt((String) jsonObject.get("begin_time"));
		int end_time = Integer.parseInt((String) jsonObject.get("end_time"));
		for (int i=0; i<day.length(); ++i) {
//			System.out.println(day.charAt(i));
			Lecture lecture = new Lecture(Character.toString(day.charAt(i)), begin_time, end_time);
			lectures.add(lecture);
		}
		return lectures;
	}
}