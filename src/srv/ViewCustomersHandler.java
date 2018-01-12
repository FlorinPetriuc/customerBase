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

package srv;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import customer.Customer;
import customerBase.DBWrapper;

public class ViewCustomersHandler implements HttpHandler {

	DBWrapper db;
	
	public ViewCustomersHandler(DBWrapper _db) {
		db = _db;
	}
	
	@Override
	public void handle(HttpExchange exc) throws IOException {
		LinkedList<Customer> cList = db.GetAllCustomers();
		
		JSONObject resp = new JSONObject();
		
		for (Customer c : cList) {
			JSONObject cj = c.toJSON();
			
			resp.append("customers", cj);
		}
		
		String sResp = resp.toString();
		
		exc.getResponseHeaders().set("Content-Type", "application/json");
		exc.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
		exc.sendResponseHeaders(200, sResp.length());
		OutputStream os = exc.getResponseBody();
		os.write(sResp.getBytes());
		os.close();
	}

}
