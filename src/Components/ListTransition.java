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
	

	public ArrayList<Transition> findListBy2S(State state1, State state2) {
		
		ArrayList<Transition> listTrans = new ArrayList<Transition>();
		
		for (int i=0; i<listTransition.size(); i++){
			if (state1==listTransition.get(i).getBeginState() && state2==listTransition.get(i).getEndState()){
				listTrans.add(listTransition.get(i));
				//return arrTransition.get(i);
			}
		}
		
		return listTrans;
	}
	
	public int getSize(){
		return listTransition.size();
	}
}
