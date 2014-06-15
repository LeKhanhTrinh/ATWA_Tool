package components;

import htmlElement.ListWebElements;
import htmlElement.ElementStatus;
import htmlElement.ListElementStatus;
import htmlElement.WebElements;

import org.openqa.selenium.WebDriver;

import arg.ElementStatusValue;


//import CellString.SubCompo_list;

public class Transition {
	Event event;
	State beginState;
	State endState;
	Condition transCond;
	
	
	public Transition(Event e, State s1, State s2, Condition c){
		beginState = s1;
		endState = s2;
		event = e;
		transCond = c;
	}
	
	public boolean changeTrans(WebDriver driver, int test_case){
		try{
			boolean test = true;
			ListWebElements  listWebElements = beginState.listWebElements;
			ListElementStatus listElementStatus = beginState.listElementStatus;
			
			
			for (int i=0; i<listElementStatus.getSize(); i++){
				
				ElementStatus elementStatus = listElementStatus.getElementByIndex(i);
				if (elementStatus.getStatus().compareTo(ElementStatusValue.IGNORE)==0){
					continue;
				}
				
				WebElements eh = listWebElements.getElementById(elementStatus.getId());
				if (eh.getValueAt(test_case).compareTo(ElementStatusValue.IGNORE)==0){
					continue;
				}
				
				// Check [guard] trong moi transition de biet trans do co thuc hien dc ko
				if(transCond.getHtml_id() == null){
					test = true;
				}else{
					String value_tc = eh.getValueAt(transCond.getHtml_id(), test_case);

					if (value_tc != null){
						if (transCond.getHtml_id().equals(eh.getHtml_id())){
							if (!transCond.getValues().equals(value_tc)){
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
	
	//Getter & Setter
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
	
	public void setNameEndState(String name){
		endState.setName(name);
	}
	
	public void setNameEvent(String name){
		event.setName(name);
	}
	
	public String logTrans(){
		String trans = "\t"+beginState.getName() + "---->" + endState.getName() + "\n";
		System.out.println("\t"+beginState.getName() + "---->" + endState.getName());
		return trans;
	}
	
	public void printTrans(){
		System.out.println("\t"+beginState.getName() + "----" + event.getName() + "---->" + endState.getName());
	}
	
	
	
}
