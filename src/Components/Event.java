package components;

import htmlElement.ListWebElements;
import htmlElement.WebElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import arg.WebdriverActionType;


public class Event {
	
	String name;
	String html_id;
	String action;
	ListWebElements listElement;
	
	public Event(String _name, String _html_id, String _action, ListWebElements _listElement){
		name=_name;
		html_id =_html_id;
		action=_action;
		listElement=_listElement;
	}
	
	public void doEvent(WebDriver driver, int test_current) {
		//System.out.println("doing event called");
		try{

			WebElements elem = listElement.getElementByName(html_id);
			WebElement webelem = driver.findElement(By.id(elem.getHtml_id()));
			
			if (this.action.compareTo(WebdriverActionType.ADDTEXT)==0){
				webelem.sendKeys(elem.getValueAt(test_current));
			} else
			if (this.action.compareTo(WebdriverActionType.DELTEXT)==0){
				webelem.clear();
			} else
			if (this.action.compareTo(WebdriverActionType.CLICK)==0){
				webelem.click();
			}
			if (this.action.compareTo(WebdriverActionType.SELECT)==0){
				webelem.click();
			}
			
		} catch (Exception e){
			e.printStackTrace();
			
		}

	}
	
	//Getter & Setter
	public String getName(){
		return name;
	}
	
	public String getAction(){
		return action;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
}
