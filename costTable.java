package CS486AI.A1Q4;
import java.util.ArrayList;

public class costTable {
	ArrayList<Integer> ar = new ArrayList<Integer>();
	boolean valid = true;
	
	public costTable(){
		// create node
		for (int i =0; i <9; i++){
			ar.add(i+1);
		}
		valid = true;
	}
	
	public costTable(ArrayList<Integer> arrayList){
		this.ar = arrayList;
		valid = true;
	}
	
	
	public void add(int target){
		this.ar.add(target);
	}
	
	public boolean isEmpty(){
		if (ar.size() == 0)
			return true;
		else 
			return false;
	}
	
	public boolean contain(int target){
		if (this.ar.contains(target))
			return true;
		else
			return false;
	}
	
	public void pop(int target){
		this.ar.remove(target-1);
	}
	
	public void print(){
		for (int i = 0; i < this.ar.size(); i++){
			System.out.println(this.ar.get(i));
		}
	}
	
	public int getLocation(int target){
		for (int i = 0; i < this.ar.size(); i++){
			if (this.ar.get(i) == target)
				return i;
		}
		return -1;
	}
	
	public void deleteNum(int target){
		for (int i = 0; i < this.ar.size(); i++){
			if (this.ar.get(i) == target)
				this.ar.remove(i);
		}
	}
	
	public int get(int target){
		for (int i = 0; i < this.ar.size(); i++){
			if (this.ar.get(i) == target)
				return ar.get(i);
		}
		return -1;
	}
}
