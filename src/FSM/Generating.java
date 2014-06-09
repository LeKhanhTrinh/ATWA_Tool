package FSM;

import java.util.ArrayList;

import Components.Transition;

public class Generating {

	int stateNumber;
	ArrayList<Integer> listEndState;
	ArrayList<Integer> listAddingState;
	ArrayList<Integer> listTemp;
	boolean [][] checkUsed;
	Transition[][] arrayTransitions;
	String[][] nameTransitions;
	
	public Generating(FSM fsm) {
		// TODO Auto-generated constructor stub
		stateNumber = fsm.getNumberOfState();
		
		//initial checkUsed
		checkUsed = new boolean[stateNumber][];
		for (int i=0 ; i<stateNumber ; i++){
			
			checkUsed [i] = new boolean[stateNumber];
			for (int j=0 ; j<stateNumber ; j++){
				checkUsed[i][j] = false;
			}
		}
		
		//initial arrayTransitions
		arrayTransitions = new Transition[stateNumber][];
		for (int i=0 ; i<stateNumber ; i++){
			
			arrayTransitions [i] = new Transition[stateNumber];
			for (int j=0 ; j<stateNumber ; j++){
				arrayTransitions[i][j] = new Transition();
			}
		}
		
		//initial arrayTransitions
		nameTransitions = new String[stateNumber][];
		for (int i=0 ; i<stateNumber ; i++){
			
			nameTransitions [i] = new String[stateNumber];
			for (int j=0 ; j<stateNumber ; j++){
				nameTransitions[i][j] = "";
			}
		}
		
		//initial Values
		initialValues(fsm);
		
		//Print
		for (int i=0; i<stateNumber; i++){
			for (int j=0; j<stateNumber; j++){
				if (arrayTransitions[i][j].getEventSize() > 0){
					System.out.println(i + "; " + j + ": " + arrayTransitions[i][j].printAllEvent());	
				}
			}
		}
		
		System.out.println("END STATE LIST:");
		for (int i=0 ; i<listEndState.size() ; i++){
			System.out.println(listEndState.get(i));
		}
	}
	
	//Get Transition value
	public void initialValues(FSM fsm){
		
		//Get Transitions
		for (int i=0 ; i<fsm.getNumberOfTransition() ; i++){
			
			int beginState = fsm.getIndexBeginStateOfTransition(i);
			int endState = fsm.getIndexEndStateOfTransition(i);
			
			arrayTransitions[beginState][endState] = fsm.getTransition(i);
			checkUsed[beginState][endState] = false;
		}
		
		//Get State
		for (int i=0 ; i<fsm.getSizeOfEndStateList() ; i++){
			String tempName = fsm.getNameEndStateByIndex(i);
			for (int j=0 ; j<stateNumber ; j++){
				if (tempName.equals(fsm.getNameStateByIndex(j))){
					listEndState.add(j);
				}
			}
		}
	}
	
	public ThePath AutoGeneratingTestPath(){
		ThePath PATH;
		PATH = new ThePath();
		listTemp.clear();
		listTemp.add(0);
		DFS(0, PATH);
		addToPath(PATH);
		PATH.sortingAlgorithm();
		PATH.printPath();
		printList();
		return PATH;
	}
	
	//Depth First Search
	private void DFS(int i, ThePath PATH){
		int t=0;
		for (int j=0 ; j<stateNumber ; j++){
			
			if (arrayTransitions[i][j].getEventSize()>0 && checkUsed[i][j]==false){
				t++;
				
				arrayTransitions[i][j].getEvents().remove(0);
				if (arrayTransitions[i][j].getEventSize() == 0){
					checkUsed[i][j] = true;
				}
				listTemp.add(j);
		
				DFS(j, PATH);
				listTemp.remove(listTemp.size()-1);
				
			}
		}
		
		if (t==0){
			System.out.println();
			PATH.addNewPath(listTemp);
		}
	}
	
	//In chuoi bat dau tu mot vi tri i
	public void printList(){
		System.out.println("Chuoi sau khi them");
		
		for (int j=0 ; j<listAddingState.size() ; j++){
			System.out.print(listAddingState.get(j) + " ");
		}
		System.out.println();
	}
	
	//Add more state to a path
	public void addToPath(ThePath PATH){
		for (int i=0 ; i<PATH.getSize() ; i++){
			if (!inEndStateList(PATH.getLastStateOfThePath(i), listEndState)){
				System.out.println("i=" + i);
				addToTheTailOfPath(PATH.getLastStateOfThePath(i));
				for (int j=0 ; j<listAddingState.size() ; j++){
					PATH.getPathByIndex(i).add(listAddingState.get(j));
				}
				while (!listAddingState.isEmpty()){
					listAddingState.remove(0);
				}
			}
		}
	}
	
	// Them state vao path
	public void addToTheTailOfPath(int i){
		
		if (inEndStateList(i, listEndState) == true){
			return ;
		}
		
		for (int j=0 ; j<stateNumber ; j++){
			if (nameTransitions[i][j].length() > 0 && checkUsed[i][j] == true){
				checkUsed[i][j] = false;
				listAddingState.add(j);
				System.out.println("IAM HERE: " + j);
				addToTheTailOfPath(j);
				break;
			}
		}
	}
	
	//check a State depend the list
	public boolean inEndStateList(int aState, ArrayList<Integer> listState){
		
		for (int i=0 ; i<listState.size() ; i++){
			if (aState == listState.get(i)){
				return true;
			}
		}
		return false;
	}
}
