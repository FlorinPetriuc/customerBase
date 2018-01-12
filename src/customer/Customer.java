/*
 * Copyright (C) 2018 Florin Petriuc. All rights reserved.
 * Initial release: Florin Petriuc <petriuc.florin@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version
 * 2 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 */

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
