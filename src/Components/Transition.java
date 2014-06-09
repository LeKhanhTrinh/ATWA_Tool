package Components;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;

import Argument.StatusValue;
import WebElements.ListStatusOfElement;
import WebElements.ListWebElement;
import WebElements.StatusOfElement;
import WebElements.WebElements;

public class Transition {

	ArrayList<Event> events;
	State beginState;
	State endState;
	Condition transCondition;
	
	public Transition() {
		// TODO Auto-generated constructor stub
	}
	
	public Transition(ArrayList<Event> _events, State _beginState, State _endState, Condition _transCondition) {
		// TODO Auto-generated constructor stub
		events = _events;
		beginState = _beginState;
		endState = _endState;
		transCondition = _transCondition;
	}
	
	public boolean transExist(WebDriver driver, int test_case){
		
		boolean test = true;
		try{

			ListWebElement  listWebElement = beginState.listWebElement;
			ListStatusOfElement listStatusOfElement = beginState.listStatusOfElement;
			
			for (int i=0; i<listStatusOfElement.getSize(); i++){
				
				StatusOfElement statusOfElement = listStatusOfElement.getElementByIndex(i);
				if (statusOfElement.getStatus().compareTo(StatusValue.IGNORE)==0){
					continue;
				}
				
				WebElements webElements = listWebElement.getElementById(statusOfElement.getId());
				if (webElements.getValueByTestCase(test_case).compareTo(StatusValue.IGNORE)==0){
					continue;
				}
				
				//Kiem tra condition
				if (transCondition !=null){
						
					test=true;
					//Lay gia tri cua elem tai testcase n
					String valueTestCase = webElements.getValueOfElementByTestCase(transCondition.getHtml_id(),test_case); 
					
					if (valueTestCase != null){
						
						if (transCondition.getHtml_id().equals(webElements.getHtml_id()) 
								&& !transCondition.getValues().equals(valueTestCase) ){
							
							test = false;
							break;
						}else{
							test = true;
						}
					}
				}
			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return test;
	}
	
	public boolean doThisTrans(WebDriver driver, int test_case){
		try{
			boolean test = true;
			ListWebElement  listWebElement = beginState.listWebElement;
			ListStatusOfElement listStatusOfElement = beginState.listStatusOfElement;
			
			
			for (int i=0; i<listStatusOfElement.getSize(); i++){
				
				StatusOfElement statusOfElement = listStatusOfElement.getElementByIndex(i);
				if (statusOfElement.getStatus().compareTo(StatusValue.IGNORE)==0){
					continue;
				}
				
				WebElements webElements = listWebElement.getElementById(statusOfElement.getId());
				if (webElements.getValueByTestCase(test_case).compareTo(StatusValue.IGNORE)==0){
					continue;
				}

				if(transCondition.getHtml_id() == null){
					test = true;
				}else{
					
					String value_tc = webElements.getValueOfElementByTestCase(transCondition.getHtml_id(), test_case);
					if (value_tc != null){
						if (transCondition.getHtml_id().equals(webElements.getHtml_id())){
							
							if (!transCondition.getValues().equals(value_tc)){
								test = false;
							}else{
								test = true;
							}
						}
					}
				}
			}		
			
			return test;
		}catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}
	
	public String logTrans(){
		String trans = "\t"+beginState.getName() + "---->" + endState.getName() + "\n";
		System.out.println("\t"+beginState.getName() + "---->" + endState.getName());
		return trans;
	}
	
	public void printTrans(){
		System.out.println("\t"+beginState.getName() + "----" + events.get(0).getName() + "---->" + endState.getName());
	}
	
	public String printAllEvent(){
		
		String result = "{";
		if (events.size() > 0){
			for (int i=0 ; i<events.size()-1 ; i++){
				result += events.get(i) + ", ";
			}
			result += events.get(events.size()-1);
		}
		result += "}";
		return result;
	}
	
	public String getName(){
		return events.get(0).getName();
	}
	
	public State getBeginState(){
		return beginState;
	}
	
	public State getEndState(){
		return endState;
	}
	
	public Event getEvent(){
		return events.get(0);
	}
	
	public int getEventSize(){
		return events.size();
	}
	
	public ArrayList<Event> getEvents(){
		return events;
	}
	
	public void setNameEndState(String name){
		endState.setName(name);
	}
	
	public void setNameEvent(String name){
		events.get(0).setName(name);
	}
}
