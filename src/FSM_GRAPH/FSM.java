package FSM_GRAPH;

import java.util.ArrayList;

import components.ListState;
import components.ListTransition;
import components.ListTransitionSequence;
import components.State;
import components.Transition;
import components.TransitionSequences;


public class FSM {
	
	private String name;
	private int numOfTest;
	public ListState stateList;
	private ListTransition transitionList;
	private State beginState;
	private ListState endStateList;
	
	
	public FSM(int _num, String _name, ListState _stateList, ListTransition _transitionList, State _beginState, ListState _endStateList){
		name = _name;
		numOfTest = _num;
		stateList=_stateList;
		transitionList=_transitionList;
		beginState = _beginState;
		endStateList = _endStateList;
	}
	
	
	//------------------------------------------------------------------------------------------
	//Ham GET
	//------------------------------------------------------------------------------------------
	
	public int getNumOfTest(){
		return numOfTest;
	}
	
	//State Number
	public int getNumberOfState(){
		return stateList.getSize();
	}
	
	// tim tat ca even mot state
	public ArrayList<String> getAllPathOfState(String name){
		ArrayList<String> ret = new ArrayList<String>();
		
		for (int i=0; i<transitionList.getSize(); i++){
			if (transitionList.getTransitionByIndex(i).getBeginState().getName().compareTo(name)==0){
				ret.add(transitionList.getTransitionByIndex(i).getName());
			}
		}
		
		return ret;
	}
	
	//Lay tat ca cac transition den 1 state
	public ListTransition getTransByEndState(String name){
		ListTransition transList = new ListTransition();
		
		for (int i=0; i<transitionList.getSize(); i++){
			if (transitionList.getTransitionByIndex(i).getEndState().getName().equals(name)){
				transList.addTransition(transitionList.getTransitionByIndex(i));
			}
		}
		
		return transList;
	}
	
	//Get State Index
	public int getIndexOfState(String name) {
		for (int i=0; i<stateList.getSize(); i++){
			if (name.compareTo(stateList.getStateByIndex(i).getName())==0){
				return i;
			}
		}
		return -1;
	}
	
	//Get name by index
	public String getNameStateByIndex(int index){
		return stateList.getStateByIndex(index).getName();
	}
	
	//Get name by index
	public String getNameEndStateByIndex(int index){
		return endStateList.getStateByIndex(index).getName();
	}
		
	//Get index Begin State
	public int getIndexBeginStateOfTransition(int i){
		return getIndexOfState(transitionList.getTransitionByIndex(i).getBeginState().getName());
	}
	
	//Get index End State
	public int getIndexEndStateOfTransition(int i){
		return getIndexOfState(transitionList.getTransitionByIndex(i).getEndState().getName());
	}
	
	//Get name Begin State
	public String getNameBeginStateOfTransition(int i){
		//return getIndexOfState(transitionList.getTransitionByIndex(i).getBeginState().getName());
		return transitionList.getTransitionByIndex(i).getBeginState().getName();
	}
	
	//Get name End State
	public String getNameEndStateOfTransition(int i){
		return transitionList.getTransitionByIndex(i).getEndState().getName();
	}
		
	//Transition Name
	public String getNameOfTransitioin(int i){
		return transitionList.getTransitionByIndex(i).getName();
	}
	
	//Transition number
	public int getNumberOfTransition(){
		return transitionList.getSize();
	}
	
	
	public String getName(){
		return name;
	}
	
	public ListTransition getTransList(){
		return transitionList;
	}
	
	public ListState getStateList(){
		return stateList;
	}
	
	public State getBeginState(){
		return beginState;
	}
	
	public ListState getEndListState(){
		return endStateList;
	}
	
	public int getSizeOfEndStateList(){
		return endStateList.getSize();
	}
	//------------------------------------------------------------------------------------------
	//Ham SET
	//------------------------------------------------------------------------------------------
	public void addEndState(State endst){
		endStateList.addState(endst);
	}
	
	public void addBeginState(State begin){
		beginState = begin;
	}
	
	//------------------------------------------------------------------------------------------
	//Ham functionally
	//------------------------------------------------------------------------------------------
	public ListTransitionSequence conVertFromPath(ThePath PATH){
		ListTransitionSequence transqlist = new ListTransitionSequence();
		
		//Khoi tao mang dem
		int[][] count = new int [stateList.getSize()][];
		for (int i=0 ; i<stateList.getSize() ; i++){
			
			count[i] = new int [stateList.getSize()];
			for (int j=0 ; j<stateList.getSize() ; j++){
				count[i][j] = 0;
			}
		}
		
		for (int i=0; i<PATH.getSize(); i++){
			TransitionSequences transq = new TransitionSequences();
			ArrayList<Integer> arr1 = PATH.getListByIndex(i);

			for (int j=0; j<arr1.size()-1; j++){
				
				ArrayList<Transition> listTrans = transitionList.findListFromTwoStates(stateList.getStateByIndex(arr1.get(j)), 
											stateList.getStateByIndex(arr1.get(j+1)));
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
			
			transqlist.addTransitionsq(transq);
		}
		
		return transqlist;
	}
	
	
	public ListTransitionSequence getPath_DFS(){
		//System.out.println("chua bao search");
		GeneratingTestPath searcher = new GeneratingTestPath(this);
		//System.out.println("Da khai bao search");
		ThePath PATH = searcher.automatedGeneratingTestPath();
		//System.out.println("Path khoi tao: ");
		return conVertFromPath(PATH);
	}
	
	public void printTransition(){
		System.out.println("Transition: ");
		for (int i=0 ; i<transitionList.getSize() ; i++){
			System.out.println(i + ":");
			transitionList.getTransitionByIndex(i).printTrans();
		}
	}
	
	public void printAll(){
		System.out.println("G - State number:" + stateList.getSize());
		System.out.println("G - Transition number:" + transitionList.getSize());
	}
	
	
	public void printBeginState(){
		System.out.println("BEGIN STATE:\t" + beginState.getName() + "\n");
	}
	
	public void printEndState(){
		System.out.print("End state:\t");
		for (int i=0 ; i<endStateList.getSize() ; i++){
			System.out.print(endStateList.getStateByIndex(i).getName() + "\t");
		}
		System.out.println();
	}
	
	public void printState(){
		stateList.printStateList();
	}
	
	//public void prin
}
