package htmlElement;

import java.util.ArrayList;

public class WebElements {
	private int id;
	private String html_id;
	private String type;
	private ArrayList<String> values;
	

	public WebElements(int _id, String _html_id, String _type, ArrayList<String> _values){
		id=_id;
		html_id=_html_id;
		type=_type;
		values = _values;
	}
	
	public int getId(){
		return id;
	}
	public String getHtml_id(){
		return html_id;
	}
	public String getType(){
		return type;
	}
	
	//test case
	public String getValueAt(int i){
		return values.get(i);
	}
	
	//test case
	public String getValueAt(String html, int i){
		if (html.compareTo(html_id) == 0){
			return values.get(i);
		}
		return null;
	}
	
	public int getIdFromHtmlId(String html_id){
		if (this.html_id.compareTo(html_id)==0){
			return this.id;
		}
		return -1;
	}

	public String getHTMLIdFromId(int id){
		if (this.id == id){
			return this.html_id;
		}
		return null;
	}
	
}
