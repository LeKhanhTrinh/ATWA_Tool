package WebElements;

import java.util.ArrayList;

public class WebElements {

	private int id;
	private String html_id;
	private String type;
	private ArrayList<String> values;
	
	public WebElements(int _id, String _html_id, String _type, ArrayList<String> _values) {
		// TODO Auto-generated constructor stub
		id=_id;
		html_id=_html_id;
		type=_type;
		values = _values;
	}
	
	//Get Value By Test Case Number
	public String getValueByTestCase(int tcNumber){
		
		return values.get(tcNumber);
	}
	
	public String getValueOfElementByTestCase(String html_id, int tcNumber){
		
		if (html_id.compareTo(this.html_id) == 0){
			return values.get(tcNumber);
		}
		return null;
	}

	//Getter
	public int getId(){
		return id;
	}
	
	public String getHtml_id(){
		return html_id;
	}
	
	public String getType(){
		return type;
	}
	
	public ArrayList<String> getValues() {
		return values;
	}
	
	//Setter
	public void setHtml_id(String html_id) {
		this.html_id = html_id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setValues(ArrayList<String> values) {
		this.values = values;
	}
}
