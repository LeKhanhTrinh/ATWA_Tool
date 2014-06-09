package Components;

import java.util.ArrayList;

public class ListStates {

	ArrayList<State> listStates;
	
	public ListStates() {
		// TODO Auto-generated constructor stub
		listStates = new ArrayList<State>();
	}
	
	public void addState(State _state){
		
		listStates.add(_state);
	}
	
	public boolean checkExist(String _name){
		
		for (int i=0 ; i<listStates.size() ; i++){
			if (listStates.get(i).getName().equals(_name)){
				return true;
			}
		}
		return false;
	}
	
	public State getStateByIndex(int index){
		
		return listStates.get(index);
	}
	
	public State getStateByName(String _name){
		
		for (int i=0; i<listStates.size(); i++){
			if (listStates.get(i).getName().compareTo(_name)==0){
				return listStates.get(i);
			}
		}
		return null;
	}
	
	
	public int getSize(){
		
		return listStates.size();
	}
	
	public void removeStateByName(String name){
		State temp = getStateByName(name);
		if (temp != null){
			listStates.remove(temp);
		}
	}
	
	public void printStateDetail(){
		
		for (int i=0 ; i<listStates.size() ; i++){
			listStates.get(i).printState();
		}
	}
	
	public void printStateList(){
		
		for (int i=0 ; i<listStates.size() ; i++){
			listStates.get(i).setId(i);
			System.out.println(listStates.get(i).id + " / " + listStates.get(i).name);
		}
	}
}
