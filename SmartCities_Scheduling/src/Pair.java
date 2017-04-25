import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Pair{
	private Lecture lecture;
	private int id;
	
	public Pair(int id, Lecture lecture) {
		this.id = id;
		this.lecture = lecture;
	}
	
	public int getPairId(){
		return this.id;
	}
	
	public Lecture getPairLecture(){
		return this.lecture;
	}
	
	public boolean samePerson(Pair p){
		return this.id==p.id?true:false;
	}
	
	public static Comparator<Pair> getCompByEndtime(){   
		 Comparator<Pair> comp = new Comparator<Pair>(){
		     @Override
		     public int compare(Pair n1, Pair n2){
		         return n1.getPairLecture().getEndTime()-(n2.getPairLecture().getEndTime());
		     }        
		 };
		 return comp;
	}  
	
	public static void main(String[] args) {
		List<Pair> pairs = new ArrayList<Pair>();
		pairs.add(new Pair(101,new Lecture("T",1505,1755)));
		pairs.add(new Pair(103,new Lecture("T",1205,1325)));
		pairs.add(new Pair(101,new Lecture("T",935,1055)));
		pairs.add(new Pair(102,new Lecture("T",1335,1455)));
		Collections.sort(pairs,Pair.getCompByEndtime());
		for(Pair p: pairs){
			System.out.println(p.getPairLecture().getEndTime()+ "<-endTime, id-> "+p.getPairId());
		}

	}

}
