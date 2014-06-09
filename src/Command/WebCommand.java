package Command;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import TestPath2Webdriver.TransitionSequences;
import WebElements.ListWebElement;

import Components.Event;
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
					System.err.println("\n\nFAIL HERE\tIgnore this");
					textRS += "\n\nFAIL HERE!!\n";
					textFail += "\n\nFAIL HERE!!\n";
					theResultS += "IGNORE\n";
					detailS += "Ignore this test path.\n";
					//continue;
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
				
				boolean passone = true;
				for (int j=0; j<tranSq.getSize(); j++){
					Transition tran = tranSq.getTransitionByIndex(j);
					Event e = tran.getEvent();
					State s1 = tran.getBeginState();
					State s2 = tran.getEndState();
					//System.out.println("check run: " + !tran.changeTrans(driver, test_c));
					if (!tran.doThisTrans(driver, testCase)){
						//System.out.println("\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName());
						System.err.println("\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName());
						//System.out.println("\tFAIL TEST PATH: " + (j+1) + "\n");
						textRS += "\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName() + "\n";
						textFail += "\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName() + "\n";
						detailS += "FAIL:\n\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName() + "\n";
						System.err.println("\tFAIL");
						textRS += "\tFAIL\n\n\n";
						textFail += "\tFAIL\n\n\n";
						theResultS = "FAIL\n";
						
						break;

						
					}else{
						if (next){
							System.err.println("\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName());
							textRS += "\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName() + "\n";
						}else{
							System.out.println("\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName());
							textRS += "\t"+(j+1)+": "+s1.getName()+"*"+e.getName()+"="+s2.getName() + "\n";
						}
						
						
						
						//tran.printTrans();
						// thuc thi event
						try{
							e.doEvent(driver, testCase);
						} catch (Exception err){
							passone = false;
							System.out.println("FAIL EVENT");
							theResultS = "FAIL\n";
							detailS += "FAIL EVENT:	Event couldn't do.\n";
							break;
						}
						// check state sau do
						if (!s2.checkState(driver, testCase)){
							passone = false;
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
				}
				
				if (passone == false){
					result = false;
					System.out.println("FAILT HERE:");
					System.out.print("Test path "+(i+1)+": ");
					
					System.out.print(tranSq.getTransitionByIndex(0).getBeginState().getName());
					textFail += "Test path "+(i+1)+": " + tranSq.getTransitionByIndex(0).getBeginState().getName();
					textRS += "FAILT HERE:\n" + "Test path "+(i+1)+": " + tranSq.getTransitionByIndex(0).getBeginState().getName();
					for (int j=0; j<tranSq.getSize(); j++){
						Transition tran = tranSq.getTransitionByIndex(j);
						Event e = tran.getEvent();
						//State s1 = tran.getBeginState();
						State s2 = tran.getEndState();
						
						System.out.print("*"+e.name+"="+s2.name);
						textFail += "*"+e.name+"="+s2.name;
						textRS += "*"+e.name+"="+s2.name;
					}
					
					System.out.println();
					textFail += "\n";
					textRS += "\n";
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

				//tran.printTrans();
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
				//tran.changeTrans(driver, test_c);
				
				Event e = tran.getEvent();
				//State s1 = tran.getBeginState();
				State s2 = tran.getEndState();
				
				System.out.print("*"+e.name+"="+s2.name);
				textRS += "*"+e.name+"="+s2.name;
				testPathS += "*"+e.name+"="+s2.name;
			}
			System.out.println();
			textRS += "\n";
			testPathS += "\n";
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
		
		//print the details of each test path
		public void printDetailOfTestPath(ListTransitionSequence listTranSq){
			System.out.println("==============================================================================================");
			textRS += "==========================================================================================================================\n";
			listTranSq.printElem();
			textRS += listTranSq.logElems();
			textRS += "========================================================================================================================\n\n";
			System.out.println("==============================================================================================\n\n");
		}
}
