package Components;

import java.util.ArrayList;


public class ListTransition {

	ArrayList<Transition> listTransition;
	
	public ListTransition() {
		// TODO Auto-generated constructor stub
		listTransition = new ArrayList<Transition>();
	}
	
	public void addTransition(Transition transition){
		listTransition.add(transition);
	}
	
	public Transition getTransitionByIndex(int index){
		return listTransition.get(index);
	}
	
	public Transition findBy2S(State s1, State s2){
		
		for (int i=0; i<listTransition.size(); i++){
			if (s1==listTransition.get(i).getBeginState() && s2==listTransition.get(i).getEndState()){
				return listTransition.get(i);
			}
		}
		
		return null;
	}
	
	public int getSize(){
		return listTransition.size();
	}
}
