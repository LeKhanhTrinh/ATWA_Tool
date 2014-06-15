package FSM_GRAPH;

import java.util.ArrayList;


public class GeneratingTestPath {
	
	boolean [][] isUsed;
	String [][] nameTransition;
	ArrayTransList[][] arrTranLists;
	int numberStates;
	ArrayList<Integer> listEndState;
	ArrayList<Integer> addMore;
	ArrayList<Integer> arrTemp;
	
	public GeneratingTestPath(FSM fsm){
		arrTemp = new ArrayList<Integer>();
		listEndState = new ArrayList<Integer>();
		addMore = new ArrayList<Integer>();
		numberStates = fsm.getNumberOfState();
		
		System.out.println("N = " + numberStates);
		
		isUsed = new boolean[numberStates][];

		for (int i=0; i<numberStates; i++){ 
			
			isUsed[i] = new boolean[numberStates];
			for (int j=0 ; j<numberStates ; j++){
				isUsed[i][j] = false;
			}
		}
		
		//arrTransition = null
		arrTranLists = new ArrayTransList[numberStates][];
		for (int i=0; i<numberStates; i++){ 
			
			arrTranLists[i] = new ArrayTransList[numberStates];
			for (int j=0 ; j<numberStates ; j++){
				arrTranLists[i][j] = new ArrayTransList();
			}
		}
				
		//nameTransition = null
		nameTransition = new String[numberStates][];
		for (int i=0; i<numberStates; i++){ 
			
			nameTransition[i]=new String[numberStates];
			for (int j=0 ; j<numberStates ; j++){
				nameTransition[i][j] = "";
			}
		}
		
		
		for (int i=0; i<fsm.getNumberOfTransition(); i++){
			
			int a = fsm.getIndexBeginStateOfTransition(i);
			int b = fsm.getIndexEndStateOfTransition(i);

			nameTransition[a][b] = fsm.getNameOfTransitioin(i);
			isUsed[a][b] = false;
			arrTranLists[a][b].Add(fsm.getNameOfTransitioin(i));		
		}
		
		for (int i=0 ; i<fsm.getSizeOfEndStateList() ; i++){
			
			String tempName = fsm.getNameEndStateByIndex(i);
			for (int j=0 ; j<numberStates ; j++){
				if (tempName.equals(fsm.getNameStateByIndex(j))){
					listEndState.add(j);
				}
			}
		}
		
		
		for (int i=0; i<numberStates; i++){
			for (int j=0; j<numberStates; j++){
				if (arrTranLists[i][j].getSize() > 0){
					System.out.println(i + "; " + j + ": " + arrTranLists[i][j].printAll());
				}
			}
		}
		
		System.out.println("END STATE LIST:");
		for (int i=0 ; i<listEndState.size() ; i++){
			System.out.println(listEndState.get(i));
		}
		
	}
	
	//Xoa phan sort va add
	public ThePath automatedGeneratingTestPath(){
		ThePath PATH;
		PATH = new ThePath();
		arrTemp.clear();
		arrTemp.add(0);
		DFS(0, PATH);
		addToPath(PATH);
		PATH.sort();
		PATH.printPath();
		printList();
		return PATH;
	}
	
	
	//Xoa phan danh dau dinh
	private void DFS(int i, ThePath PATH){
		int t=0;
		
		for (int j=0; j<numberStates; j++){
			
			if (arrTranLists[i][j].getSize()>0 && isUsed[i][j] == false){
				t++;
				
				//overState[j]=1;
				arrTranLists[i][j].RemoveHead();
				if (arrTranLists[i][j].IsEmpty()){
					isUsed[i][j] = true;
				}
				arrTemp.add(j);
		
				DFS(j, PATH);
				arrTemp.remove(arrTemp.size()-1);
				
			}
		}

		if (t==0){
			System.out.println();
			PATH.Add(arrTemp);

		}
		
	}
	
	//Kiem tra
	public void printCheck(){
		System.out.println("CHECK DUYET");
		for (int i=0 ; i<numberStates ; i++){
			for (int j=0 ; j<numberStates ; j++){
				if (isUsed[i][j] != false)
					System.out.println("["+ i + "," + j +"]" + isUsed[i][j]);
			}
		}
	}
	
	//In chuoi bat dau tu mot vi tri i
	public void printList(){
		System.out.println("Chuoi sau khi them");
		
		for (int j=0 ; j<addMore.size() ; j++){
			System.out.print(addMore.get(j) + " ");
		}
		System.out.println();
	}
	
	
	public void addToPath(ThePath PATH){
		for (int i=0 ; i<PATH.getSize() ; i++){
			if (!inEndStateList(PATH.getLastElemFromPathNumber(i), listEndState)){
				System.out.println("i=" + i);
				addToPathAfter(PATH.getLastElemFromPathNumber(i));
				for (int j=0 ; j<addMore.size() ; j++){
					PATH.getListByIndex(i).add(addMore.get(j));
				}
				while (!addMore.isEmpty()){
					addMore.remove(0);
				}
			}
		}
	}
	
	// Them state vao path
	public void addToPathAfter(int i){		
		if (inEndStateList(i, listEndState) == true){
			return ;
		}
		
		for (int j=0; j<numberStates; j++){
			if (nameTransition[i][j].length() > 0 && isUsed[i][j] == true){
				isUsed[i][j] = false;
				addMore.add(j);
				System.out.println("IAM HERE: " + j);
				addToPathAfter(j);
				break;
			}
		}
		
		
	}
	
	public boolean inEndStateList(int gt, ArrayList<Integer> listInt){
		for (int i=0 ; i<listInt.size() ; i++){
			if (gt == listInt.get(i)){
				return true;
			}
		}
		return false;
	}
	
}



class ArrayTransList{
	ArrayList<String> arrList;
	
	public ArrayTransList() {
		// TODO Auto-generated constructor stub
		arrList = new ArrayList<String>();
	}
	
	public ArrayTransList(ArrayList<String> arrList){
		this.arrList = arrList;
	}
	
	public void Add(String element){
		arrList.add(element);
	}
	
	public void RemoveHead(){
		arrList.remove(0);
	}
	
	public boolean IsEmpty(){
		return arrList.isEmpty();
	}
	
	public String printAll(){
		String rs = "{";
		if (arrList.size() > 0){
			for (int i=0 ; i<arrList.size()-1 ; i++){
				rs += arrList.get(i) + ", ";
			}
			rs += arrList.get(arrList.size()-1);
		}
		rs += "}";
		return rs;
	}
	
	public int getSize(){
		return arrList.size();
	}
	
	public String getByIndex(int i){
		return arrList.get(i);
	}
}