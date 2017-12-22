package customer;

import java.sql.Timestamp;

import org.json.JSONObject;

public class CustomerNote {
	int id;
	
	String note;
	
	Timestamp modifiedDate;
	
	public CustomerNote(int _id, String _note, Timestamp _modifiedDate) {
		id = _id;
		note = _note;
		modifiedDate = (Timestamp)_modifiedDate.clone();
	}
	
	public JSONObject toJSON() {
		JSONObject ret = new JSONObject();
		
		ret.put("id", id);
		ret.put("note", note);
		ret.put("modified", modifiedDate.getTime() / 1000);
		
		return ret;
	}
}
