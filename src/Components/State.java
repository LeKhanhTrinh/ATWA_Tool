package Components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import Argument.ElementsType;
import Argument.GetAttribute;
import Argument.StatusValue;
import WebElements.ListStatusOfElement;
import WebElements.ListWebElement;
import WebElements.StatusOfElement;
import WebElements.WebElements;

public class State {

	public int id;
	public String name;
	public ListWebElement listWebElement;
	public ListStatusOfElement listStatusOfElement;
	
	public State(int _id, String _name, ListWebElement _listWebElement, ListStatusOfElement _listStatusOfElement) {
		// TODO Auto-generated constructor stub
		id = _id;
		name = _name;
		listStatusOfElement = _listStatusOfElement;
		listWebElement = _listWebElement;
	}
	
	public State(String _name, ListWebElement _listWebElement, ListStatusOfElement _listStatusOfElement) {
		// TODO Auto-generated constructor stub
		name = _name;
		listStatusOfElement = _listStatusOfElement;
		listWebElement = _listWebElement;
	}
	
	
	public String getValueFromHtmlById(WebElement webelem, WebElements eh, int test_current){
	
		try{
			
			String value="";
			if (eh.getType().compareTo(ElementsType.TEXTBOX)==0){
		
				value=webelem.getAttribute(GetAttribute.ATT_VALUE);
			} else if (eh.getType().compareTo(ElementsType.CHECKBOX)==0){
				
				if (webelem.isSelected()){
					value = "1";
				} else {
					value = "0";
				}
			}else if (eh.getType().compareTo(ElementsType.RADIO) == 0){
				
				if (webelem.isSelected()){
					value = "1";
				} else {
					value = "0";
				}
			}else if (eh.getType().compareTo(ElementsType.LISTBOX) == 0){
				
				Select clickThis = new Select(webelem);			
				clickThis.selectByValue(eh.getValueByTestCase(test_current));
				value = eh.getValueByTestCase(test_current);
			}
			else {
				
				value=webelem.getText();
			}
			
			return value;
		} catch (Exception e){
			
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean checkState(WebDriver driver, int test_current){
		
		try{
			
			for (int i=0; i<listStatusOfElement.getSize(); i++){
				
				StatusOfElement statusOfElement = listStatusOfElement.getElementByIndex(i);
				if (statusOfElement.getStatus().compareTo(StatusValue.IGNORE)==0){
					continue;
				}
				
				WebElements webElements = listWebElement.getElementById(statusOfElement.getId());
				if (webElements.getValueByTestCase(test_current).compareTo(StatusValue.IGNORE)==0){
					continue;
				}
				
				WebElement webElemDriver = driver.findElement(By.id(webElements.getHtml_id()));
				String value = getValueFromHtmlById(webElemDriver, webElements, test_current);

				if (statusOfElement.getStatus().compareTo(StatusValue.EMPTY)==0){
					
					if (value.length()!=0){
						System.out.println("f1");
						return false;
					}
				} else if (statusOfElement.getStatus().compareTo(StatusValue.DEFAULT)==0){ 
					
					if (value.compareTo(webElements.getValueByTestCase(test_current))!=0){
						
						System.out.println("f2");
						System.out.println(value+":\t"+webElements.getValueByTestCase(test_current));
						if (webElements.getValueByTestCase(test_current).compareTo(StatusValue.IGNORE)==0){
							continue;
						}
						return false;
					}
				} else if (value.compareTo(statusOfElement.getStatus())!=0){
					
					System.out.println("Strings not compare("+value+"):"
							+ webElements.getHtml_id()
							+ "_" + statusOfElement.getStatus());
					System.out.println("f3");
					return false;
				}
			
			}
			
			return true;
		} catch (Exception e){
			System.out.println("check fail");
			//e.printStackTrace();
			return false;
		}
	}
	
	//Show detail of bug in transition
	public String showDetailOfBug(WebDriver driver, int test_current) {
		
		String result="";
		
		try{
			
			for (int i=0; i<listStatusOfElement.getSize(); i++){
				
				StatusOfElement statusOfElement = listStatusOfElement.getElementByIndex(i);
				if (statusOfElement.getStatus().compareTo(StatusValue.IGNORE)==0){
					continue;
				}
				
				WebElements webElements = listWebElement.getElementById(statusOfElement.getId());
				if (webElements.getValueByTestCase(test_current).compareTo(StatusValue.IGNORE)==0){
					continue;
				}
				
				WebElement webElemDriver = driver.findElement(By.id(webElements.getHtml_id()));
				String value = getValueFromHtmlById(webElemDriver, webElements, test_current);

				if (statusOfElement.getStatus().compareTo(StatusValue.EMPTY)==0){
					
					if (value.length()!=0){
						System.out.println("f1");
						result = "Bug's type 1: the value of element is empty.\n";
					}
					
				} else if (statusOfElement.getStatus().compareTo(StatusValue.DEFAULT)==0){ 
					
					if (value.compareTo(webElements.getValueByTestCase(test_current))!=0){
						
						System.out.println("f2");
						System.out.println(value+":\t"+webElements.getValueByTestCase(test_current));
						result = webElements.getHtml_id() + " 's value is not compare with expected output.\n";
						if (webElements.getValueByTestCase(test_current).compareTo(StatusValue.IGNORE)==0){
							continue;
						}
						
					}
				} else if (value.compareTo(statusOfElement.getStatus())!=0){
					
					System.out.println("Strings not compare("+value+"):"
							+ webElements.getHtml_id()
							+ "_" + statusOfElement.getStatus());
					System.out.println("f3");
					result = "Strings not compare("+value+"):" + webElements.getHtml_id()
							+ "_" + statusOfElement.getStatus();
				}
			
			}
			
			
		} catch (Exception e){
			result = "Cannot locate HTML ID of Web Elements";
			//e.printStackTrace();
			//return false;
		}
		return result;
	}
	
	public void printState(){
		System.out.println("PRINT STATE: " + name);
		for (int i=0 ; i<listWebElement.getSize() ; i++){
			System.out.println(listWebElement.getElementByIndex(i).getHtml_id() + " / " + listStatusOfElement.getElementByIndex(i).getStatus());
		}
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setListStatusOfElement(ListStatusOfElement listStatusOfElement) {
		this.listStatusOfElement = listStatusOfElement;
	}
	
	public void setListWebElement(ListWebElement listWebElement) {
		this.listWebElement = listWebElement;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public ListStatusOfElement getListStatusOfElement() {
		return listStatusOfElement;
	}
	
	public ListWebElement getListWebElement() {
		return listWebElement;
	}
	
	public String getName() {
		return name;
	}
}
