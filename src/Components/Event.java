package Components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Argument.EventsAction;
import WebElements.ListWebElement;
import WebElements.WebElements;

public class Event {

	public String name;
	String html_id;
	String action;
	ListWebElement listWebElement;
	
	public Event(String _name, String _html_id, String _action, ListWebElement _listWebElement) {
		// TODO Auto-generated constructor stub
		name= _name;
		html_id = _html_id;
		action= _action;
		listWebElement= _listWebElement;
	}
	
	//do the event
	public void doEvent(WebDriver driver, int test_current){
		
		try{
			
			WebElements webElements = listWebElement.getElementByHTML(html_id);
			WebElement webElementDriver = driver.findElement(By.id(webElements.getHtml_id()));
			
			if (this.action.compareTo(EventsAction.ADDTEXT)==0){
				webElementDriver.sendKeys(webElements.getValueByTestCase(test_current));
			} else
			if (this.action.compareTo(EventsAction.DELTEXT)==0){
				webElementDriver.clear();
			} else
			if (this.action.compareTo(EventsAction.CLICK)==0){
				webElementDriver.click();
			}
			if (this.action.compareTo(EventsAction.SELECT)==0){
				webElementDriver.click();
			}
			
		} catch (Exception e){
			e.printStackTrace();
			
		}
	}
	
	//Getter
	public String getName() {
		return name;
	}
	
	public String getAction() {
		return action;
	}
	
	public String getHtml_id() {
		return html_id;
	}
	
	public ListWebElement getListWebElement() {
		return listWebElement;
	}
	
	//Setters
	public void setName(String name) {
		this.name = name;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public void setHtml_id(String html_id) {
		this.html_id = html_id;
	}
	
	public void setListWebElement(ListWebElement listWebElement) {
		this.listWebElement = listWebElement;
	}
}
