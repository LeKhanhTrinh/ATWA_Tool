package components;

import java.util.ArrayList;

public class ListEvent {
	ArrayList<Event> listEvent;
	
	public ListEvent(){
		listEvent=new ArrayList<Event>();
	}
	
	public void addEvent(Event e){
		listEvent.add(e);
	}
	
	public Event getEventByIndex(int index){
		return listEvent.get(index);
	}
	
	public Event getEventByName(String _name){
		for (int i=0 ; i<listEvent.size() ; i++){
			if (listEvent.get(i).getName().compareTo(_name) == 0){
				return listEvent.get(i);
			}
		}
		return null;
	}
	
	
	public int getSize(){
		return listEvent.size();
	}
	

}
