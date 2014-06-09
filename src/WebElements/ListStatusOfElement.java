package WebElements;

import java.util.ArrayList;


public class ListStatusOfElement {

	ArrayList<StatusOfElement> listStatusOfElement;
	
	public ListStatusOfElement() {
		// TODO Auto-generated constructor stub
		listStatusOfElement = new ArrayList<StatusOfElement>();
	}
	
	public void addElement(StatusOfElement _e){
		
		listStatusOfElement.add(_e);
	}
	
	//Get
	public StatusOfElement getElementById(int id){
		
		for (int i=0; i<listStatusOfElement.size(); i++){
			if (listStatusOfElement.get(i).getId()==id){
				
				return listStatusOfElement.get(i);
			}
		}
		
		return null;
	}
	
	public StatusOfElement getElementByName(String _status){
		
		for (int i=0; i<listStatusOfElement.size(); i++){
			if (listStatusOfElement.get(i).getStatus().equals(_status)){
				
				return listStatusOfElement.get(i);
			}
		}
		
		return null;
	}
	
	public StatusOfElement getElementByIndex(int index){
		
		return listStatusOfElement.get(index);
	}
	
	public int getSize(){
		
		return listStatusOfElement.size();
	}
}
