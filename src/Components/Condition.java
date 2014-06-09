package Components;

public class Condition {

	String html_id;
	String values;
	
	public Condition() {
		// TODO Auto-generated constructor stub
	}
	
	public Condition(String html_id, String values){
		this.html_id = html_id;
		this.values = values;
	}
	
	/*
	 * Getter & Setter
	 */
	
	public void setHtml_id(String html_id) {
		this.html_id = html_id;
	}
	
	public void setValues(String values) {
		this.values = values;
	}
	
	public String getHtml_id() {
		return html_id;
	}
	
	public String getValues() {
		return values;
	}
}
