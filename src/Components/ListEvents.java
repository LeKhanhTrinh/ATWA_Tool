package Components;

import java.util.ArrayList;


public class ListEvents {

	ArrayList<Event> listEvent;
	
	public ListEvents() {
		// TODO Auto-generated constructor stub
		listEvent = new ArrayList<Event>();
	}
	
	public void addEvent(Event e){
		listEvent.add(e);
	}
	
	public void removeEvent(int i){
		listEvent.remove(i);
	}
	
	public Event getEventByIndex(int index){
		return listEvent.get(index);
	}
	
	public Event getEventByName(String _name){
		for (int i=0; i<listEvent.size(); i++){
			if (listEvent.get(i).getName().compareTo(_name)==0){
				return listEvent.get(i);
			}
		}
		return null;
	}
	
	public int getSize(){
		return listEvent.size();
	}
}
