package Components;

import java.util.ArrayList;

public class TransitionSequence {

	ArrayList<Transition> listTransition;
	
	public TransitionSequence() {
		// TODO Auto-generated constructor stub
		listTransition = new ArrayList<Transition>();
	}
	
	public void addTransition(Transition transition){
		
		listTransition.add(transition);
	}
	
	public void removeTransition(Transition transition){
		listTransition.remove(transition);
	}
	
	public Transition getTransitionByIndex(int i){
		return listTransition.get(i);
	}
	
	public int getSize(){
		return listTransition.size();
	}
	
	public String logTransq(){
		String transqs = "";
		for (int i=0 ; i<getSize(); i++){
			transqs += listTransition.get(i).logTrans();
		}
		
		return transqs;
	}
	
	
	public void printTransq(){
		for (int i=0 ; i<getSize(); i++){
			listTransition.get(i).printTrans();
		}
	}
}
