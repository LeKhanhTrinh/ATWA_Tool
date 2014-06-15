package components;

import htmlElement.ListWebElements;
import htmlElement.ElementStatus;
import htmlElement.ListElementStatus;
import htmlElement.WebElements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import arg.ElementStatusValue;
import arg.ElementType;
import arg.WebdriverGetAttribute;



public class State {
	int id;
	String name;
	ListElementStatus listElementStatus; // for this state
	ListWebElements listWebElements;
	
	public State(){
		
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
	
	public State(int _id, String _name, ListElementStatus _listElementStatus, ListWebElements _listWebElements){
		id = _id;
		name = _name;
		listElementStatus = _listElementStatus;
		listWebElements = _listWebElements;
	}
	
	public State(String _name, ListElementStatus _elem_st_list, ListWebElements _elem_html_list){
		name = _name;
		listElementStatus = _elem_st_list;
		listWebElements = _elem_html_list;
	}
	
	public String getName(){
		return name;
	}
	
	public void printState(){
		
		System.out.println("PRINT STATE: " + name);
		for (int i=0 ; i<listWebElements.getSize() ; i++){
			System.out.println(listWebElements.getElementByIndex(i).getHtml_id() + " / " + listElementStatus.getElementByIndex(i).getStatus());
		}
	}
	
	public void addMoreInfo(String _name, ListElementStatus _elem_st_list, ListWebElements _elem_html_list){
		
		if (name.equals(_name)){
			for (int i=0 ; i<_elem_html_list.getSize() ; i++){
				listWebElements.addElement(_elem_html_list.getElementByIndex(i));
				listElementStatus.addElement(_elem_st_list.getElementByIndex(i));
			}
			
		}
	}
	
	public String getStringFromHtmlById(WebElement webelem, WebElements eh, int test_current){
		
		try{
			String value = "";
			if (eh.getType().compareTo(ElementType.TEXTBOX) == 0){

				value=webelem.getAttribute(WebdriverGetAttribute.ATT_VALUE);
				
			} else if (eh.getType().compareTo(ElementType.CHECKBOX)==0){
				
				if (webelem.isSelected()){
					value = "1";
				} else {
					value = "0";
				}
			}else if (eh.getType().compareTo(ElementType.RADIO) == 0){
				
				if (webelem.isSelected()){
					value = "1";
				} else {
					value = "0";
				}
			}else if (eh.getType().compareTo(ElementType.LISTBOX) == 0){
				Select clickThis = new Select(webelem);			
				clickThis.selectByValue(eh.getValueAt(test_current));
				value = eh.getValueAt(test_current);
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
	
	// test_current la bien chi xem test lan thu may, tuong ung voi bo gia tri nao
	public boolean checkState(WebDriver driver, int test_current){
		try{
			
			for (int i=0; i<listElementStatus.getSize(); i++){
				ElementStatus e = listElementStatus.getElementByIndex(i);
				if (e.getStatus().compareTo(ElementStatusValue.IGNORE)==0){
					continue;
				}
				
				WebElements eh = listWebElements.getElementById(e.getId());
				if (eh.getValueAt(test_current).compareTo(ElementStatusValue.IGNORE)==0){
					continue;
				}
				
				WebElement webelem = driver.findElement(By.id(eh.getHtml_id()));	
				String value = getStringFromHtmlById(webelem, eh, test_current);
				if (e.getStatus().compareTo(ElementStatusValue.EMPTY)==0){
					if (value.length()!=0){
						System.out.println("f1");
						return false;
					}
				} else if (e.getStatus().compareTo(ElementStatusValue.DEFAULT)==0){ 
					if (value.compareTo(eh.getValueAt(test_current))!=0){
						
						System.out.println("f2");
						System.out.println(value+":\t"+eh.getValueAt(test_current));
						if (eh.getValueAt(test_current).compareTo(ElementStatusValue.IGNORE)==0){
							continue;
						}
						return false;
					}
				} else if (value.compareTo(e.getStatus())!=0){
					System.out.println("Strings not compare("+value+"):"+eh.getHtml_id()+"_"+e.getStatus());
					System.out.println("f3");
					return false;
				}
			
			}
			
			return true;
		} catch (Exception e){
			System.out.println("check fail");
			return false;
		}
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	
	
	
}
