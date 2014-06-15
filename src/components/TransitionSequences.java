package components;

import java.util.ArrayList;

public class TransitionSequences {
	ArrayList<Transition> listTransition;
	
	public TransitionSequences(){
		listTransition = new ArrayList<Transition>();
	}
	
	
	public void addTransition(Transition _t){
		listTransition.add(_t);
	}
	
	public Transition getTransitionByIndex(int i){
		return listTransition.get(i);
	}
	
	
	public int getSize(){
		return listTransition.size();
	}
	
	public void removeTran(Transition tr){
		listTransition.remove(tr);
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
