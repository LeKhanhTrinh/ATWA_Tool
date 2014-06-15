package components;

import java.util.ArrayList;

public class ListTransition {
	
	ArrayList<Transition> listTransition;
	
	public ListTransition(){
		listTransition = new ArrayList<Transition>();
	}
	
	public int getSize(){
		return listTransition.size();
	}
	
	public void addTransition(Transition _t){
		listTransition.add(_t);
	}
	
	public Transition getTransitionByIndex(int index){
		return listTransition.get(index);
	}
	
	public ArrayList<Transition> findListFromTwoStates(State beginState, State endState){
		
		ArrayList<Transition> listTrans = new ArrayList<Transition>();
		
		for (int i=0; i<listTransition.size(); i++){
			if (beginState==listTransition.get(i).getBeginState() && endState==listTransition.get(i).getEndState()){
				listTrans.add(listTransition.get(i));
			}
		}
		
		return listTrans;
	}

	public Transition findBy2S(State s1, State s2){
		
		for (int i=0; i<listTransition.size(); i++){
			if (s1==listTransition.get(i).getBeginState() && s2==listTransition.get(i).getEndState()){
				return listTransition.get(i);
			}
		}
		
		return null;
	}
	
}
