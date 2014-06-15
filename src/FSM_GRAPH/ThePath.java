package FSM_GRAPH;

import java.util.ArrayList;

public class ThePath {
	ArrayList<ArrayList<Integer>> arrPath;
	
	public int getSize(){
		return arrPath.size();
	}
	
	public ThePath(){
		arrPath = new ArrayList<ArrayList<Integer>>();
	}
	
	
	public void Add(ArrayList<Integer> arr){
		ArrayList<Integer> t = new ArrayList<Integer>();
		for (int i=0; i<arr.size(); i++){
			t.add(arr.get(i));
		}
		arrPath.add(t);
		
	}
	
	public ArrayList<Integer> getListByIndex(int index){
		return arrPath.get(index);
	}
	
	public int getSizeByIndex(int index){
		return arrPath.get(index).size();
	}
	
	public int getLastElemFromPathNumber(int index){
		return arrPath.get(index).get(arrPath.get(index).size()-1);
	}

	
	
	public void sort(){
		for (int i=0; i< arrPath.size()-1; i++){
			for (int j=i+1; j<arrPath.size(); j++){
				if (arrPath.get(i).size()>arrPath.get(j).size()){
					ArrayList<Integer> t = arrPath.get(i);
					
					arrPath.set(i, arrPath.get(j));
					arrPath.set(j, t);
					
				}
			}
		}
		
		for (int i=0; i< arrPath.size()-1; i++){
			for (int j=i+1; j<arrPath.size(); j++){
				if (laCon(arrPath.get(i), arrPath.get(j))){
					arrPath.remove(i);
					i=0;
					j=1;
				}
			}
		}
	}
	
	public boolean laCon(ArrayList<Integer> arr1, ArrayList<Integer> arr2){
		if (arr1.size()>arr2.size()) return false;
		for (int i=0; i<arr1.size(); i++){
			if (arr1.get(i).intValue()!=arr2.get(i).intValue()){
				return false;
			}
		}
		
		return true;
	}
	
	public void printPath(){
		for (int i=0; i<arrPath.size(); i++){
			System.out.print(""+(i+1)+". ");
			ArrayList<Integer> t = arrPath.get(i);
			for (int k=0; k<t.size(); k++){
				System.out.print(t.get(k) + " ");
				
			}
			System.out.println();
			
		}
	}
	
	public void printSomeThingsOfPath(){
		System.out.println("About PATH: ");
		for (int i=0; i<arrPath.size(); i++){
			System.out.println("Size:\t" + getSizeByIndex(i) + "\tEnd:\t" + getLastElemFromPathNumber(i));
		}
	}
	
}
