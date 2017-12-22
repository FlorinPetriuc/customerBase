package customerBase;

import java.io.IOException;

import srv.ServerWrapper;

public class CustomerBase {

	public static void main(String[] args) {
		int port = 4080;
		
		String dbUser = "propuser";
		String dbPass = "propuser";
		String dbURL = "localhost";
		String db = "propellerhead";
		
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
