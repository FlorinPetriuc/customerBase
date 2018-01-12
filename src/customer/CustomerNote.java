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
