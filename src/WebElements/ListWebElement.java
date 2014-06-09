package WebElements;

import java.util.ArrayList;

public class ListWebElement {

	ArrayList<WebElements> listElements;
	
	public ListWebElement() {
		// TODO Auto-generated constructor stub
		listElements = new ArrayList<WebElements>();
	}
	
	//add more
	public void addElement(WebElements webElement){
		
		listElements.add(webElement);
	}
	
	//Check element exist
	public boolean checkExist(String html_id){
		
		for (int i=0 ; i<listElements.size() ; i++){
			if (listElements.get(i).getHtml_id().equals(html_id)){
				
				return true;
			}
		}
		
		return false;
	}
	
	//Get element
	public WebElements getElementByIndex(int index){
		
		return listElements.get(index);
	}
	
	public WebElements getElementById(int id){
		
		for (int i=0 ; i<listElements.size() ; i++){
			if (listElements.get(i).getId() == id){
				
				return listElements.get(i);
			}
		}
		
		return null;
	}
	
	public WebElements getElementByHTML(String html_id){
		
		for (int i=0 ; i<listElements.size() ; i++){
			if (listElements.get(i).getHtml_id().equals(html_id)){
				
				return listElements.get(i);
			}
		}
		
		return null;
	}
	
	public int getSize(){
		
		return listElements.size();
	}
}
