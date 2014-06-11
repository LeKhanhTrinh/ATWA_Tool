package FSM;

import java.util.ArrayList;
import Components.ListStates;
import Components.ListTransition;
import Components.ListTransitionSequence;
import Components.State;
import Components.Transition;
import Components.TransitionSequence;
import FSM.ThePath;

public class FSM {

	String name;
	private int numOfTest;
	private State beginState;
	private ListStates listEndState;
	private ListTransition listTransition;
	public ListStates listState;
	
	public FSM(int _numOfTest, String _name, ListStates _listStates, ListTransition _listTransition, 
					State _beginState, ListStates _listEndStates) {
		// TODO Auto-generated constructor stub
		numOfTest = _numOfTest;
		beginState = _beginState;
		listEndState = _listEndStates;
		name = _name;
		listTransition = _listTransition;
		listState = _listStates;
	} 
	
	//Add
	//-----------------------------------------------------------------
	public void addEndState(State endst){
		listEndState.addState(endst);
	}
	
	public void addBeginState(State begin){
		beginState = begin;
	}
	
	//Functional
	//-----------------------------------------------------------------
	public ListTransitionSequence conVertFromPath(ThePath PATH){
		ListTransitionSequence transqlist = new ListTransitionSequence();
		
		//Khoi tao mang dem
				int[][] count = new int [listState.getSize()][];
				for (int i=0 ; i<listState.getSize() ; i++){
					
					count[i] = new int [listState.getSize()];
					for (int j=0 ; j<listState.getSize() ; j++){
						count[i][j] = 0;
					}
				}
				
				for (int i=0; i<PATH.getSize(); i++){
					TransitionSequence transq = new TransitionSequence();
					
					ArrayList<Integer> arr1 = PATH.getPathByIndex(i);

					//System.out.println("Size = " + arr1.size());
					for (int j=0; j<arr1.size()-1; j++){
						
						ArrayList<Transition> listTrans = listTransition.findListBy2S(listState.getStateByIndex(arr1.get(j)), 
								listState.getStateByIndex(arr1.get(j+1)));
						
						Transition tran1;
						
						if (listTrans.size() > 1){
							if (count[arr1.get(j)][arr1.get(j+1)] >= listTrans.size()){
								tran1 = listTrans.get(listTrans.size()-1);
							}else{
								tran1 = listTrans.get(count[arr1.get(j)][arr1.get(j+1)]);
							}
						}else{
							tran1 = listTrans.get(0);
						}
						count[arr1.get(j)][arr1.get(j+1)]++;
						transq.addTransition(tran1);
					}
					
					transqlist.addTransitionSequence(transq);
				}
		
		return transqlist;
	}
	
	public ListTransitionSequence getPath_DFS(){
		
		//System.out.println("chua bao search");
		Generating searcher = new Generating(this);
		
		//System.out.println("Da khai bao search");
		ThePath PATH = searcher.AutoGeneratingTestPath();
		
		//System.out.println("Path khoi tao: ");
		return conVertFromPath(PATH);
	}
	
	//Getter
	//------------------------------------------------------------------
	public int getNumOfTest() {
		return numOfTest;
	}
	
	public int getNumberOfState(){
		return listState.getSize();
	}
	
	// tim tat ca even mot state
	public ArrayList<String> getAllPathOfState(String name){
		
		ArrayList<String> allEvent = new ArrayList<String>();
		for (int i=0; i<listTransition.getSize(); i++){
			if (listTransition.getTransitionByIndex(i).getBeginState().getName().compareTo(name)==0){
				allEvent.add(listTransition.getTransitionByIndex(i).getName());
			}
		}
		
		return allEvent;
	}
	
	//Lay tat ca cac transition den 1 state
	public ListTransition getTransByEndState(String name){
		
		ListTransition transList = new ListTransition();
		for (int i=0; i<listTransition.getSize(); i++){
			if (listTransition.getTransitionByIndex(i).getEndState().getName().equals(name)){
				transList.addTransition(listTransition.getTransitionByIndex(i));
			}
		}
		
		return transList;
	}
	
	//Get State Index
	public int getIndexOfState(String name) {
		for (int i=0; i<listState.getSize(); i++){
			if (name.compareTo(listState.getStateByIndex(i).getName())==0){
				return i;
			}
		}
		return -1;
	}
	
	//Get name by index
	public String getNameStateByIndex(int index){
		return listState.getStateByIndex(index).getName();
	}
	
	//Get name by index
	public String getNameEndStateByIndex(int index){
		return listEndState.getStateByIndex(index).getName();
	}
		
	//Get index Begin State
	public int getIndexBeginStateOfTransition(int i){
		return getIndexOfState(listTransition.getTransitionByIndex(i).getBeginState().getName());
	}
	
	//Get index End State
	public int getIndexEndStateOfTransition(int i){
		return getIndexOfState(listTransition.getTransitionByIndex(i).getEndState().getName());
	}
	
	//Get name Begin State
	public String getNameBeginStateOfTransition(int i){
		return listTransition.getTransitionByIndex(i).getBeginState().getName();
	}
	
	//Get name End State
	public String getNameEndStateOfTransition(int i){
		return listTransition.getTransitionByIndex(i).getEndState().getName();
	}
		
	//Transition Name
	public String getNameOfTransition(int i){
		return listTransition.getTransitionByIndex(i).getName();
	}
	
	//get Transition
	public Transition getTransition(int i){
		return listTransition.getTransitionByIndex(i);
	}
	
	//Transition number
	public int getNumberOfTransition(){
		return listTransition.getSize();
	}
	
	
	public String getName(){
		return name;
	}
	
	public ListTransition getTransList(){
		return listTransition;
	}
	
	public ListStates getStateList(){
		return listState;
	}
	
	public State getBeginState(){
		return beginState;
	}
	
	public ListStates getEndListState(){
		return listEndState;
	}
	
	public int getSizeOfEndStateList(){
		return listEndState.getSize();
	}
	
	public void printAll(){
		System.out.println("G - State number:" + listState.getSize());
		System.out.println("G - Transition number:" + listTransition.getSize());
	}
	
	public void printTransition(){
		System.out.println("Transition: ");
		for (int i=0 ; i<listTransition.getSize() ; i++){
			System.out.println(i + ":");
			listTransition.getTransitionByIndex(i).printTrans();
		}
	}
	
	public void printBeginState(){
		System.out.println("BEGIN STATE:\t" + beginState.getName() + "\n");
	}
	
	public void printEndState(){
		System.out.print("End state:\t");
		for (int i=0 ; i<listEndState.getSize() ; i++){
			System.out.print(listEndState.getStateByIndex(i).getName() + "\t");
		}
		System.out.println();
	}
	
	public void printState(){
		listState.printStateList();
	}
}
