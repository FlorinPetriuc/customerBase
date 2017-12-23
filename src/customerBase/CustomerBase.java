package customerBase;

import java.io.IOException;

import srv.ServerWrapper;

public class CustomerBase {
	
	static String GetParameter(String[] args, String arg) {
		String ret = null;
		
		for (String s : args) {
			String[] sArr = s.split("=");
			
			if(sArr.length != 2) {
				continue;
			}
			
			if(sArr[0].equals("-" + arg)) {
				return sArr[1];
			}
		}
		
		return ret;
	}

	public static void main(String[] args) {
		int port;
		
		String _port;
		String dbUser;
		String dbPass;
		String dbURL;
		String db;
		
		System.out.println("Starting up. Version 1.0.");
		
		dbUser = GetParameter(args, "dbuser");
		if(dbUser == null) {
			dbUser = "propuser";
		}
		
		dbPass = GetParameter(args, "dbpass");
		if(dbPass == null) {
			dbPass = "propuser";
		}
		
		dbURL = GetParameter(args, "dburl");
		if(dbURL == null) {
			dbURL = "localhost";
		}
		
		db = GetParameter(args, "db");
		if(db == null) {
			db = "propellerhead";
		}
		
		_port = GetParameter(args, "port");
		if(_port == null) {
			_port = "4080";
		}
		
		try {
			port = Integer.parseInt(_port);
		} catch(NumberFormatException ex) {
			System.out.println("Port number is NAN");
			
			return;
		}
		
		System.setProperty("user.timezone", "GMT");
		
		DBWrapper dw = new DBWrapper(dbUser, dbPass, dbURL, db);
		
		if(dw.Connect() == false) {
			System.out.println("DB cannot start");
			
			return;
		}
		
		System.out.println("DB started OK");
		
		ServerWrapper sw = new ServerWrapper(port, dw);
		
		if(sw.Start() == false) {
			System.out.println("Server cannot start");
			
			dw.Stop();
			
			return;
		}
		
		System.out.println("Server started on port " + port);
		
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sw.Stop();
		dw.Stop();
		
		System.out.println("Stopped");
			
	}

}
