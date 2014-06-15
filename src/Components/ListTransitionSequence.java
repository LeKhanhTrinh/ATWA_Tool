package components;

import java.util.ArrayList;

public class ListTransitionSequence {
	ArrayList<TransitionSequences> listTranSq;
	
	public ListTransitionSequence(){
		listTranSq = new ArrayList<TransitionSequences>();
	}
	
	public void addTransitionsq(TransitionSequences _tranSq){
		listTranSq.add(_tranSq);
	}
	
	public TransitionSequences getTransitionByIndex(int i){
		return listTranSq.get(i);
	}
	
	
	public int getSize(){
		return listTranSq.size();
	}
	
	public void removeTransq(int i){
		listTranSq.remove(i);
	}
	
	
	public void removeTransq(TransitionSequences transq){
		listTranSq.remove(transq);
	}
	
	public String logElems(){
		String result = "";
		result += "Number of Transition Sequence: " + getSize() + "\n";
		
		for (int i=0 ; i<getSize() ; i++){
			System.out.println("Transition Sequence " + i + " - " + listTranSq.get(i).getSize() + ":");
			result += "Transition Sequence " + (i+1) + " - " + listTranSq.get(i).getSize() + ":\n";
			result += listTranSq.get(i).logTransq();
		}
		
		return result;
	}
	
	public void printElem(){
		System.out.println("Number of Transition Sequence: " + getSize());
		
		for (int i=0 ; i<getSize() ; i++){
			System.out.println("Transition Sequence " + i + " - " + listTranSq.get(i).getSize() + ":");
			listTranSq.get(i).printTransq();
		}
	}

}
