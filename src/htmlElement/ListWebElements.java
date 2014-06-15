package htmlElement;

import java.util.ArrayList;

public class ListWebElements {
	ArrayList<WebElements> arrElement;
	
	public ListWebElements(){
		arrElement = new ArrayList<WebElements>();
	}
	
	public void addElement(WebElements _e){
		arrElement.add(_e);
	}
	
	public WebElements getElementById(int id){
		
		for (int i=0; i<arrElement.size(); i++){
			if (arrElement.get(i).getId()==id){
				return arrElement.get(i);
			}
		}
		
		return null;
		
	}
	
	public boolean checkExist(String html_id){
		for (int i=0 ; i<arrElement.size() ; i++){
			if (arrElement.get(i).getHtml_id().equals(html_id)){
				return true;
			}
		}
		return false;
	}
	
	public WebElements getElementByName(String html_id){
		for (int i=0 ; i<arrElement.size() ; i++){
			if (arrElement.get(i).getHtml_id().equals(html_id)){
				return arrElement.get(i);
			}
		}
		return null;
	}
	
	public WebElements getElementByIndex(int index){
		return arrElement.get(index);
	}
	
	public int getSize(){
		return arrElement.size();
	}
	
}
