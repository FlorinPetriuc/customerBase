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
import java.net.InetSocketAddress;

import com.sun.net.httpserver.*;

import customerBase.DBWrapper;

public class ServerWrapper {
	int port;
	
	HttpServer srv;
	
	DBWrapper db;
	
	public ServerWrapper(int _port, DBWrapper _db) {
		port = _port;
		db = _db;
	}
	
	public void Stop() {
		srv.stop(0);
	}
	
	public boolean Start() {
		System.out.println("Starting server on port " + port);
		
		InetSocketAddress sAddr = new InetSocketAddress(port);
		
		try {
			srv = HttpServer.create(sAddr, 0);
		} catch (IOException e) {
			System.out.println("Cannot start server on port " + port);
			e.printStackTrace();
			
			return false;
		}
		
		srv.createContext("/viewCustomers", new ViewCustomersHandler(db));
		srv.createContext("/viewNotes", new ViewNotesHandler(db));
		srv.createContext("/addCustomer", new AddCustomerHandler(db));
		srv.createContext("/changeCustomerStatus", new ChangeCustomerStatus(db));
		srv.createContext("/addNote", new AddNoteHandler(db));
		srv.createContext("/changeNote", new ChangeNoteHandler(db));
		
		srv.setExecutor(java.util.concurrent.Executors.newCachedThreadPool());
		srv.start();
		
		return true;
	}
}
