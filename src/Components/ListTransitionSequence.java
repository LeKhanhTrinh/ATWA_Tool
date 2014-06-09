package Components;

import java.util.ArrayList;

public class ListTransitionSequence {

	ArrayList<TransitionSequence> listTransitionSequences;
	
	public ListTransitionSequence() {
		// TODO Auto-generated constructor stub
		listTransitionSequences = new ArrayList<TransitionSequence>();
	}
	
	public void addTransitionSequence(TransitionSequence transitionSequence){
		listTransitionSequences.add(transitionSequence);
	}
	
	public TransitionSequence getTransitionSequenceByIndex(int i){
		return listTransitionSequences.get(i);
	}
	
	public void removeTransitionSequence(int i){
		listTransitionSequences.remove(i);
	}
	
	public void removeTransitionSequence(TransitionSequence transitionSequence){
		listTransitionSequences.remove(transitionSequence);
	}
	
	public String logElems(){
		String result = "";
		result += "Number of Transition Sequence: " + getSize() + "\n";
		
		for (int i=0 ; i<getSize() ; i++){
			System.out.println("Transition Sequence " + i + " - " + listTransitionSequences.get(i).getSize() + ":");
			result += "Transition Sequence " + (i+1) + " - " + listTransitionSequences.get(i).getSize() + ":\n";
			result += listTransitionSequences.get(i).logTransq();
		}
		
		
		return result;
	}
	
	public void printElem(){
		System.out.println("Number of Transition Sequence: " + getSize());
		
		for (int i=0 ; i<getSize() ; i++){
			System.out.println("Transition Sequence " + i + " - " + listTransitionSequences.get(i).getSize() + ":");
			listTransitionSequences.get(i).printTransq();
		}
	}
	
	public int getSize(){
		return listTransitionSequences.size();
	}
}
