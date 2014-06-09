package FSM;

import java.util.ArrayList;

public class ThePath {

	public ArrayList<ArrayList<Integer>> listPaths;
	
	public ThePath() {
		// TODO Auto-generated constructor stub
		listPaths = new ArrayList<ArrayList<Integer>>();
	}
	
	//-----------------------------------------------------------------------
	//List path
	//-----------------------------------------------------------------------
	
	//get the number of the paths
	public int theNumberOfThePath(){
		return listPaths.size();
	}
	
	//get a path by index
	public ArrayList<Integer> getPathByIndex(int i){
		return listPaths.get(i);
	}
	
	//add a new path
	public void addNewPath(ArrayList<Integer> aPath){
		
		ArrayList<Integer> temp = new ArrayList<Integer>();
		
		for (int i=0 ; i<aPath.size() ; i++){
			temp.add(aPath.get(i));
		}
		
		listPaths.add(temp);
	}
	
	//delete a path
	public void removePathByIndex(int i){
		listPaths.remove(i);
	}
	
	//Sorting 
	public void sortingAlgorithm(){
		//swap
		for (int i=0 ; i<listPaths.size()-1 ; i++){
			for (int j=i+1 ; j<listPaths.size() ; j++){			
				if (listPaths.get(i).size() > listPaths.get(j).size()){
					
					ArrayList<Integer> temp = listPaths.get(i);	
					listPaths.set(i, listPaths.get(j));
					listPaths.set(j, temp);
				}
			}
		}
		
		//remove Child
		for (int i=0 ; i<listPaths.size()-1 ; i++){
			for (int j=i+1 ; j<listPaths.size() ; j++){	
				if (isChild(listPaths.get(i), listPaths.get(j))){
					
					listPaths.remove(i);
					i=0;
					j=1;
				}
			}
		}
	}
	
	//Print
	public void printPath(){
		
		for (int i=0; i<listPaths.size(); i++){
			
			System.out.print(""+(i+1)+". ");
			ArrayList<Integer> temp = listPaths.get(i);
			
			for (int k=0; k<temp.size(); k++){
				System.out.print(temp.get(k) + " ");
			}
			System.out.println();
			
		}
	}
	
	public int getSize(){
		return listPaths.size();
	}
	//-----------------------------------------------------------------------
	//A path
	//-----------------------------------------------------------------------
	
	//get size of a path
	public int getSizeOfPath(int i){
		return listPaths.get(i).size();
	}
	
	//get the last state of a path
	public int getLastStateOfThePath(int i){
		return listPaths.lastIndexOf(listPaths.get(i));
	}
	
	//Path1 is path2's child
	public boolean isChild(ArrayList<Integer> path1, ArrayList<Integer> path2){
		
		if (path1.size() != path2.size()) return false;
		
		for (int i=0 ; i<path1.size() ; i++){
			if (path1.get(i) != path2.get(i))
				return false;
		}
		
		return true;
	}
	
	//Print
	public void printSomeThingsOfPath(){
		System.out.println("About PATH: ");
		for (int i=0; i<listPaths.size(); i++){
			System.out.println("Size:\t" + getSizeOfPath(i) + "\tEnd:\t" + getLastStateOfThePath(i));
		}
	}
}
