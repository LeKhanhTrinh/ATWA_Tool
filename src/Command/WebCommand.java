package Command;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import WebElements.ListStatusOfElement;
import WebElements.ListWebElement;
import WebElements.StatusOfElement;
import WebElements.WebElements;

import Components.Event;
import Components.Condition;
import Components.ListEvents;
import Components.ListStates;
import Components.ListTransition;
import Components.ListTransitionSequence;
import Components.State;
import Components.Transition;
import Components.TransitionSequence;
import FSM.FSM;

public class WebCommand {

	FSM fsm; // do thi bieu dien state machine
	WebDriver driver; // webdriver object
	
	//FSM'components
	State beginState;
	ListWebElement listWebElement = new ListWebElement();
	ListStates listStates = new ListStates();
	ListEvents listEvent = new ListEvents();
	ListTransition listTransition = new ListTransition();
	ListStates listEndState = new ListStates();
	
	//Variables
	int numberOfTest;
	String name;
	String testPathS = "";
	String theResultS = "";
	String detailS = "";
	public String textRS = "";
	public String textFail = "";
	
	//list show
	ArrayList<String> testPath = new ArrayList<String>();
	ArrayList<String> theResult = new ArrayList<String>();
	ArrayList<String> detail = new ArrayList<String>();
	
	public WebCommand(String _name, String _folder) throws Exception{
		// TODO Auto-generated constructor stub
		name = _name;
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); 
		
		
		File directory = new File(_folder);
		File[] contents = directory.listFiles();
		for ( File f : contents) {
		  inputData(f);
		  System.out.println(f.getName());
		}
	    fsm.printState();
		fsm.printTransition();
	}
	
	public boolean runTestCaseWithUrl(String url, int nSleep, boolean log){
		System.out.println("Number of Test: " + fsm.getNumOfTest());
		for (int i=0; i<fsm.getNumOfTest(); i++){
			System.out.println("\n\nStart run test case with values: "+(i+1));
			textRS += "\n\nStart run test case with values: "+(i+1) + "\n";
			try{
				if (!runTestPath(url, nSleep, log, i)){
					return false;
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
			System.out.print("PASS testing with values: "+(i+1)+"\n");
			textRS += "PASS testing with values: "+(i+1)+"\n";
			textRS += "\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n";
		}
		//System.out.println("co o day");
		return true;
	}
	
	//run transition sequence
	public boolean runTestPath(String url, int nsleep, boolean log, int testCase){
		
		boolean result = true;
		int index = 0;
		
		System.out.println("runing:");
		textRS += "runing:\n";
		ListTransitionSequence listTranSq = fsm.getPath_DFS();	//FSM SAU KHI NOI

		//Print the detail of all test paths;
		printDetailOfTestPath(listTranSq);
		
		//Run test paths
		for (int i=0 ; i<listTranSq.getSize() ; i++){
			TransitionSequence tranSq = listTranSq.getTransitionSequenceByIndex(i);
			
			testPathS = "";
			theResultS = "";
			detailS = "";
			boolean next = false;
			driver.get(url);
			
			//index = i;
			//Neu khong dung guard se remove
			next = checkRemove(tranSq, url, testCase);
			if (next){
				continue;
			}
			
			//In chi tiet tung chuyen trang thai
			if (log){			
				logTheTestPath(index, tranSq);
				index++;
			}
			
			//System.out.println("check run: " + !transq.getTransitionByIndex(0).getBeginState().checkState(driver, test_c));
			if (!tranSq.getTransitionByIndex(0).getBeginState().checkState(driver, testCase)){
				result = false;
			}
			
			boolean isPassed = checkPass(tranSq, testCase, next, nsleep);
			
			if (isPassed == false){
				result = false;
				logTheFailTestPath(i, tranSq);
			}
			
			testPath.add(testPathS);
			if (!theResultS.equals("")){
				theResult.add(theResultS);
			}else{
				theResult.add("PASS\n");
			}
			detail.add(detailS);
		}
		
		textFail += "--------------------------------------------------------------------------";
		
		return result;
	}

	//Check test path is passed or failed
	public boolean checkPass(TransitionSequence tranSq, int testCase, boolean next, int nsleep){
		boolean isPassed = true;
		
		for (int j=0; j<tranSq.getSize(); j++){
			
			Transition tran = tranSq.getTransitionByIndex(j);
			Event e = tran.getEvent();
			State s1 = tran.getBeginState();
			State s2 = tran.getEndState();
			
			System.out.println("\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName());
			textRS += "\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName() + "\n";

			// thuc thi event
			try{
				e.doEvent(driver, testCase);
			} catch (Exception err){
				isPassed = false;
				System.out.println("FAIL EVENT");
				theResultS = "FAIL\n";
				detailS += "FAIL EVENT:	Event couldn't do.\n";
				break;
			}
			// check state sau do
			if (!s2.checkState(driver, testCase)){
				isPassed = false;
				textFail += "\n\nFAIL HERE!!\n";
				textFail += "FAIL STATE: \"" + s2.getName() + "\" (We can't found this state)\n";
				theResultS = "FAIL\n";
				detailS += "FAIL STATE: \"" + s2.getName() + "\" (We can't found this state)\n";
				System.out.println("FAIL STATE");
				break;
			}
			if (next){
				System.err.println("\tOK");
				textRS += "\tOK\n";
			}else{
				System.out.println("\tOK");
				textRS += "\tOK\n";
			}
			//System.out.println("\tOK");
			try{
				Thread.sleep(nsleep);
			} catch (Exception ex){
				ex.printStackTrace();
			}
		}
		
		
		return isPassed;
	}
		
	//print a path
	public void logTheTestPath(int index, TransitionSequence tranSq){
		
		//System.out.println("So index = " + index);
		System.out.print("Test path "+(index+1)+": ");
		textRS += "Test path "+(index+1)+": ";
		testPathS += "Test path "+(index+1)+": ";
		System.out.print(tranSq.getTransitionByIndex(0).getBeginState().getName());
		textRS += tranSq.getTransitionByIndex(0).getBeginState().getName();
		testPathS += tranSq.getTransitionByIndex(0).getBeginState().getName();
		
		for (int j=0 ; j<tranSq.getSize() ; j++){
			
			Transition tran = tranSq.getTransitionByIndex(j);				
			Event e = tran.getEvent();
			State s2 = tran.getEndState();
			
			System.out.print("*"+e.name+"="+s2.name);
			textRS += "*"+e.name+"="+s2.name;
			testPathS += "*"+e.name+"="+s2.name;
		}
		System.out.println();
		textRS += "\n";
		testPathS += "\n";
	}
	
	//print fail test
	public void logTheFailTestPath(int index, TransitionSequence tranSq){
		
		System.out.println("FAILT HERE:");
		System.out.print("Test path "+(index+1)+": ");
		
		System.out.print(tranSq.getTransitionByIndex(0).getBeginState().getName());
		textFail += "Test path "+(index+1)+": " + tranSq.getTransitionByIndex(0).getBeginState().getName();
		textRS += "FAILT HERE:\n" + "Test path "+(index+1)+": " + tranSq.getTransitionByIndex(0).getBeginState().getName();
		for (int j=0; j<tranSq.getSize(); j++){
			
			Transition tran = tranSq.getTransitionByIndex(j);
			Event e = tran.getEvent();
			State s2 = tran.getEndState();
			
			System.out.print("*" + e.name + "=" + s2.name);
			textFail += "*" + e.name + "=" + s2.name;
			textRS += "*" + e.name + "=" + s2.name;
		}
		
		System.out.println();
		textFail += "\n";
		textRS += "\n";
	}
	
	//Check xem co xoa hay ko
	public boolean checkRemove(TransitionSequence transq, String url, int test_c){
		driver.get(url);
		for (int j=0 ; j<transq.getSize() ; j++){
			Transition tran = transq.getTransitionByIndex(j);
			if(!tran.doThisTrans(driver, test_c)) {
				return true;
			}
		}
		return false;
	}
	
	
	//------------------------------------------------------------------------------------------
	//Input & Output
	//------------------------------------------------------------------------------------------
	// ham input du lieu tu file excel
	
	public void inputData(File file) throws Exception{

		int numOfElem = listWebElement.getSize();
		int numOfState = listStates.getSize();
		System.out.println("Num Of Element: " + numOfElem);
		System.out.println("Num Of State: " + numOfState);
		
		
		
		System.out.println("Input data: ");
		Workbook workbook = Workbook.getWorkbook(file);
        Sheet sheet = workbook.getSheet(0);
        
        // so cac html element
        int numOfElement = Integer.valueOf(sheet.getCell(0, 0).getContents().trim()).intValue();
        numberOfTest = Integer.valueOf(sheet.getCell(4, 0).getContents().trim()).intValue();
       
        for (int i=0 ; i<numOfElement ; i++){
        	
        	int id = Integer.valueOf(sheet.getCell(1, i+2).getContents().trim()).intValue();
        	String html_id = sheet.getCell(2, i+2).getContents().trim();
        	String type = sheet.getCell(3, i+2).getContents().trim();
        	
        	//String defaultV = sheet.getCell(4, i+2).getContents().trim();
        	
        	ArrayList<String> values = new ArrayList<String>();
        	for (int j=0 ; j<numberOfTest ; j++){
        		String tvalue = sheet.getCell(4+j, i+2).getContents().trim();
        		//if (tvalue.length()==0){ Exception e = new Exception(); throw e;} // kiem tra chuan du lieu
        		if (tvalue.length()==0){
        			tvalue = "_";
        		}
        		values.add(tvalue);
        	}
        	
        	//if(elemHtmlList.checkExist(html_id)){
        	//	elemHtmlList.getElementByName(html_id).addMoreElem(html_id, type, values);
        	//}else{
        		listWebElement.addElement(new WebElements(numOfElem + id, html_id, type, values));
        	//}
        }
        
        System.out.println("Number of html element: " + listWebElement.getSize());
        System.out.println("Number of testing values: " + numberOfTest);
        
        int nState = Integer.valueOf(sheet.getCell(0, numOfElement + 2).getContents().trim()).intValue();
        
        //System.out.println(nstate);
        
        for (int i=0; i<nState; i++){
        	String name = sheet.getCell(1, i+2+numOfElement+2).getContents().trim();
        	//Integer.valueOf(sheet.getCell(0, i+2+nelem+2).getContents().trim());
        	int beginEnd = Integer.valueOf(sheet.getCell(0, i+2+numOfElement+2).getContents().trim());
        	//System.out.println(name);
        	ListStatusOfElement listStatusElement = new ListStatusOfElement();
        	
        	for (int j=0 ; j<numOfElement ; j++){
        		
        		String id = sheet.getCell(j+2, numOfElement+2+1).getContents().trim();
        		String st = sheet.getCell(j+2, i+2+numOfElement+2).getContents().trim();
        		if (st.length() == 0){
        			st = "_";
        		}
        		//System.out.println(id+"-"+st);
        		listStatusElement.addElement(new StatusOfElement(Integer.valueOf(id).intValue() + numOfElem, st));
        	}
        	
    		listStates.addState(new State(name, listWebElement, listStatusElement));
        	
        	
        	
        	//-------------------------------
        	// Gan cac gia tri cho state bat dau va ket thuc
        	// Co the co nhieu endstate
        	//-------------------------------
        	
        	if (beginEnd == 0 && !listStates.checkExist(name)){
        		beginState = new State(name, listWebElement, listStatusElement);
        	}else if (beginEnd == 1){
        		listEndState.addState(new State(name, listWebElement, listStatusElement));
        	}
        	
        }
        System.out.println("Number of states: " + listStates.getSize());
        
        int nEvent = Integer.valueOf(sheet.getCell(0, numOfElement+2+nState+2).getContents().trim()).intValue();
        
        //System.out.println(nevent);
        
        for (int i=0 ; i<nEvent ; i++){
        	
        	String name = sheet.getCell(1, i+2+numOfElement+2+nState+2).getContents().trim();
        	String elem_id = sheet.getCell(2, i+2+numOfElement+2+nState+2).getContents().trim();
        	String action = sheet.getCell(3, i+2+numOfElement+2+nState+2).getContents().trim();
        	
        	//eventList.addEvent(new Event(name, Integer.valueOf(elem_id).intValue(), action, elemHtmlList));
        	listEvent.addEvent(new Event(name, elem_id, action, listWebElement));
        }
        
        System.out.println("Number of events: " + listEvent.getSize());
        //Them thong so ve so dau vao
        int ntrans = Integer.valueOf(sheet.getCell(0, numOfElement+2+nState+2+nEvent+2).getContents().trim()).intValue();
        
        for (int i=0 ; i<ntrans ; i++){
        	//cellstring
        	for (int j=0 ; j<nState ; j++){
        		//String cellString = sheet.getCell(2, i+2+nevent+2+nelem+2+nstate+2).getContents().trim();
        		//System.out.println("Sheet Condition:" + j + "\t" + cellString);
        		String eventNConds = sheet.getCell(j+2,i+2+nEvent+2+numOfElement+2+nState+2).getContents().trim();	//Dich lai 1 cot
        		String s1name = sheet.getCell(1,i+2+nEvent+2+numOfElement+2+nState+2).getContents().trim();
        		String s2name = sheet.getCell(j+2,1+nEvent+2+numOfElement+2+nState+2).getContents().trim();	//Dich lai 1 cot
        		
        		if (eventNConds.length() == 0){
        			eventNConds = "_";
        		}
        		
        		ArrayList<String> ECnames = new ArrayList<String>();
        		ECnames = subEvents(eventNConds); //List cac event va condition
        		
        		//System.out.println(s1name + "--" + ename + "-->" + s2name + " :\t" + cellString);
        		for (int k=0 ; k<ECnames.size() ; k++){
        			if (ECnames.get(k).length()==0 || ECnames.get(k).compareTo("_")==0){
            			continue;
            		}
        			
        			String eName = getNameEvent(ECnames.get(k));
        			Condition cond = getCond(ECnames.get(k));
        			System.out.println("Name Event: " + eName);
        			listTransition.addTransition(new Transition(listEvent.getEventByName(eName), 
            				listStates.getStateByName(s1name), 
            				listStates.getStateByName(s2name), cond));
        		}
        	}
        	
        }
        System.out.println("Number of transitions: " + listTransition.getSize());   
        fsm = new FSM(numberOfTest, this.name, listStates, listTransition, beginState, listEndState);
        //return fsm1;
	}
	
	//get list eventNCond
	public ArrayList<String> subEvents(String events){
		String tempEvent = events;
		ArrayList<String> result = new ArrayList<String>();
		
		if (!events.equals("_")){
			
			while (tempEvent != null){
				
				String buff1 = "";
				String buff2 = "";
				
				int charac = tempEvent.indexOf(",");
				if (charac >= 0){
					
					buff1 = tempEvent.substring(0, charac);
					buff2 = tempEvent.substring(charac + 1, tempEvent.length());
					result.add(buff1);
					tempEvent = buff2;
				}else{
					
					buff1 = tempEvent;
					result.add(buff1);
					tempEvent = null;
				}
			}
		}else{
			
			result.add("_");
		}
		
		return result;
	}
	
	//export Data
	public void exportData(File file) throws Exception{
		
		try{
			WritableWorkbook workbook1 = Workbook.createWorkbook(file);
			WritableSheet sheet1 = workbook1.createSheet("First Sheet", 0); 
			
			for (int i=0 ; i<testPath.size() ; i++){
				Label tp = new Label(0, i, testPath.get(i));
				Label rs = new Label(5, i, theResult.get(i));
				Label dt = new Label(8, i, detail.get(i));
				sheet1.addCell(tp);
				sheet1.addCell(rs);
				sheet1.addCell(dt);
			}
			workbook1.write();
            workbook1.close();
		}catch(Exception ex){
			
		}
	}
	
	//Get event name from transition
	public String getNameEvent(String input){
		String temp = "";
		if (input.indexOf("]") >= 0){
			temp = input.substring(input.indexOf("]")+1, input.length());
		}else{
			temp = input;
		}
		
		return temp;
	}
	
	//Get condition from transition
	public Condition getCond(String input){
		int moNgoac = input.indexOf("[");
		int dongNgoac = input.indexOf("]");
		String conds = "";
		Condition conditions;
		if (moNgoac >= 0 && dongNgoac > moNgoac){
			conds = input.substring(moNgoac+1, dongNgoac);
			int gachCheo = conds.indexOf("/");
			String id = conds.substring(0, gachCheo);
			String values = conds.substring(gachCheo+1, conds.length());
			conditions = new Condition(id, values);
		}else{
			conditions = new Condition();
		}
		return conditions;
	}
	
	//print the details of each test path
	public void printDetailOfTestPath(ListTransitionSequence listTranSq){
		System.out.println("==============================================================================================");
		textRS += "==========================================================================================================================\n";
		listTranSq.printElem();
		textRS += listTranSq.logElems();
		textRS += "========================================================================================================================\n\n";
		System.out.println("==============================================================================================\n\n");
	}
	
	public String getTxt(){
		return textRS;
	}
		
	public void quitDriver(){
		driver.quit();
	}

}
