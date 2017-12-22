package srv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.LinkedList;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import customer.CustomerNote;
import customerBase.DBWrapper;

public class ViewNotesHandler implements HttpHandler {
	DBWrapper db;
	
	public ViewNotesHandler(DBWrapper _db) {
		db = _db;
	}
	
	@Override
	public void handle(HttpExchange exc) throws IOException {
		InputStream is = exc.getRequestBody();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
				
		String body = "";
		String s = br.readLine();
		while(s != null) {
			body += s;
			
			s = br.readLine();
		}
		
		JSONObject req = new JSONObject(body);
		int cID = req.getInt("cID");
		
		LinkedList<CustomerNote> nList = db.GetAllNotes(cID);
		
		JSONObject resp = new JSONObject();
		
		for (CustomerNote n : nList) {
			JSONObject nj = n.toJSON();
			
			resp.append("notes", nj);
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