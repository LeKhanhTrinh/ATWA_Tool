package Components;

import org.openqa.selenium.WebDriver;
import Argument.StatusValue;
import WebElements.ListStatusOfElement;
import WebElements.ListWebElement;
import WebElements.StatusOfElement;
import WebElements.WebElements;

public class Transition {

	Event event;
	State beginState;
	State endState;
	Condition transCondition;
	
	public Transition() {
		// TODO Auto-generated constructor stub
	}
	
	public Transition(Event _event, State _beginState, State _endState, Condition _transCondition) {
		// TODO Auto-generated constructor stub
		event = _event;
		beginState = _beginState;
		endState = _endState;
		transCondition = _transCondition;
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
		//System.out.println("\t"+beginState.getName() + "----" + listEvents.getEventByIndex(0).getName() + "---->" + endState.getName());
		System.out.println("\t"+beginState.getName() + "----" + event.getName() + "---->" + endState.getName());
	}
	
	/*
	public String printAllEvent(){
		
		String result = "{";
		if (listEvents.getSize() > 0){
			for (int i=0 ; i<listEvents.getSize()-1 ; i++){
				result += listEvents.getEventByIndex(i) + ", ";
			}
			result += listEvents.getEventByIndex(listEvents.getSize()-1);
		}
		result += "}";
		return result;
	}
	*/
	public String getName(){
		return event.getName();
	}
	
	public State getBeginState(){
		return beginState;
	}
	
	public State getEndState(){
		return endState;
	}
	
	public Event getEvent(){
		return event;
	}
	
	/*
	public int getEventSize(){
		return listEvents.getSize();
	}
	
	public ListEvents getEvents(){
		return listEvents;
	}
	*/
	
	public void setNameEndState(String name){
		endState.setName(name);
	}
	
	public void setNameEvent(String name){
		event.setName(name);
	}
}
