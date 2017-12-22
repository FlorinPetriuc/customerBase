package customer;

import java.sql.Timestamp;
import java.util.LinkedList;

import org.json.JSONObject;

public class Customer {
	int id;
	
	CustomerType type;
	
	Timestamp creationDate;
	
	String name;
	String details;
	
	LinkedList<CustomerNote> notes;
		
	public Customer(int _id, CustomerType _type, Timestamp _creationDate, String _name, String _details) {
		id = _id;
		type = _type;
		creationDate = (Timestamp)_creationDate.clone();
		name = _name;
		details = _details;
		
		notes = null;
	}
	
	public void SetNotes(LinkedList<CustomerNote> _notes) {
		notes = _notes;
	}
	
	public int GetID() {
		return id;
	}
	
	public JSONObject toJSON() {
		JSONObject ret = new JSONObject();
		
		ret.put("id", id);
		ret.put("created", creationDate.getTime() / 1000);
		ret.put("name", name);
		ret.put("details", details);
		ret.put("type", type.ordinal());
		
		return ret;
	}
}
