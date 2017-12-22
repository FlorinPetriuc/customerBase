package customerBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.LinkedList;

import customer.Customer;
import customer.CustomerNote;
import customer.CustomerType;

public class DBWrapper {
	String username;
	String password;
	
	String url;
	String db;
	
	Connection conn;
	
	public DBWrapper(String _username, String _password, String _url, String _db) {
		username = _username;
		password = _password;
		url = _url;
		db = _db;
	}
	
	public boolean Connect() {
		String myDriver = "com.mysql.jdbc.Driver";
		String myUrl = "jdbc:mysql://" + url + "/" + db;
		
	    try {
			Class.forName(myDriver);
		} catch (ClassNotFoundException e) {
			System.out.println("Cannot find mysql class");
			e.printStackTrace();
			
			return false;
		}
	    
	    try {
			conn = DriverManager.getConnection(myUrl, username, password);
		} catch (SQLException e) {
			System.out.println("Cannot connect to database");
			e.printStackTrace();
			
			return false;
		}
	    
	    return true;
	}
	
	public void Stop() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void SafeCloseStatement(Statement s) {
		try {
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void SafeCloseStatement(PreparedStatement s) {
		try {
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public LinkedList<CustomerNote> GetAllNotes(int customerID) {
		String query = "SELECT id,note,modified FROM notes WHERE customerID=?";
		LinkedList<CustomerNote> ret = new LinkedList<>();
		
		PreparedStatement st;
		ResultSet rs;
		
		try {
			st = conn.prepareStatement(query);
		} catch (SQLException e) {
			System.out.println("Cannot prepare statement for GetAllNotes");
			e.printStackTrace();
			
			return null;
		}
		
		try {
			st.setInt(1, customerID);
		} catch (SQLException e) {
			SafeCloseStatement(st);
			
			System.out.println("Cannot setInt for GetAllNotes statement");
			e.printStackTrace();
			
			return null;
		}
		
		try {
			rs = st.executeQuery();
		} catch (SQLException e) {
			SafeCloseStatement(st);
			
			System.out.println("Cannot execute select from db in GetAllNotes");
			e.printStackTrace();
			
			return null;
		}
		
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String note = rs.getString("note");
				Timestamp modified = rs.getTimestamp("modified");
				
				CustomerNote n = new CustomerNote(id, note, modified);				
				ret.add(n);
			}
		} catch (SQLException e) {
			SafeCloseStatement(st);
			
			System.out.println("Cannot execute read from db");
			e.printStackTrace();
			
			return null;
		}
		
		SafeCloseStatement(st);
		
		return ret;
	}
	
	public LinkedList<Customer> GetAllCustomers() {
		String query = "SELECT id,status,created,name,details FROM customers";
		Statement st;
		ResultSet rs;
		
		LinkedList<Customer> ret = new LinkedList<>();
		
		try {
			st = conn.createStatement();
		} catch (SQLException e) {
			System.out.println("Cannot select create select statement");
			e.printStackTrace();
			
			return null;
		}
		
		try {
			rs = st.executeQuery(query);
		} catch (SQLException e) {
			SafeCloseStatement(st);
			
			System.out.println("Cannot execute select from db");
			e.printStackTrace();
			
			return null;
		}
		
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				int status = rs.getInt("status");
				Timestamp created = rs.getTimestamp("created");
				String name = rs.getString("name");
				String details = rs.getString("details");
				
				CustomerType ct;
				
				try {
					ct = CustomerType.values()[status];
				} catch(IndexOutOfBoundsException ex) {
					System.out.println("Corrupt entry for customer status in db for " + id);
					ct = CustomerType.PROSPECTIVE;
				}
				
				Customer c = new Customer(id, ct, created, name, details);				
				ret.add(c);
			}
		} catch (SQLException e) {
			SafeCloseStatement(st);
			
			System.out.println("Cannot execute read from db");
			e.printStackTrace();
			
			return null;
		}
		
		SafeCloseStatement(st);
		
		return ret;
	}
	
	public boolean ChangeCustomerStatus(int id, int cType) {
		String query = "UPDATE customers SET status=? WHERE id=?";
		
		PreparedStatement st;
		
		if(cType >= CustomerType.values().length || cType < 0) {
			System.out.println("ChangeCustomerStatus with invalid cType: " + cType);
			return false;
		}
		
		try {
			st = conn.prepareStatement(query);
		} catch (SQLException e) {
			System.out.println("Cannot prepare statement for GetAllNotes");
			e.printStackTrace();
			
			return false;
		}
		
		try {
			st.setInt(1, cType);
			st.setInt(2, id);
		} catch (SQLException e) {
			SafeCloseStatement(st);
			
			System.out.println("Cannot set parameters for ChangeCustomerStatus statement");
			e.printStackTrace();
			
			return false;
		}
		
		try {
			st.execute();
		} catch (SQLException e) {
			SafeCloseStatement(st);
			
			System.out.println("Cannot set execute ChangeCustomerStatus statement");
			e.printStackTrace();
			
			return false;
		}
		
		SafeCloseStatement(st);
		
		return true;
	}
	
	public int AddCustomer(int cType, Timestamp date, String name, String details) {
		String query = "INSERT INTO customers(status,created,name,details) VALUES(?,?,?,?)";
		
		PreparedStatement st;
		ResultSet rs;
		
		int ret = -1;
		
		if(cType >= CustomerType.values().length || cType < 0) {
			System.out.println("AddCustomer with invalid cType: " + cType);
			return -1;
		}
		
		try {
			st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		} catch (SQLException e) {
			System.out.println("Cannot prepare statement for AddCustomer");
			e.printStackTrace();
			
			return -1;
		}
		
		try {
			st.setInt(1, cType);
			st.setTimestamp(2, date);
			st.setString(3, name);
			st.setString(4, details);
		} catch (SQLException e) {
			SafeCloseStatement(st);
			
			System.out.println("Cannot set parameters for AddCustomer statement");
			e.printStackTrace();
			
			return -1;
		}
		
		try {
			st.execute();
		} catch (SQLException e) {
			SafeCloseStatement(st);
			
			System.out.println("Cannot set execute AddCustomer statement");
			e.printStackTrace();
			
			return -1;
		}
		
		try {
			rs = st.getGeneratedKeys();
			if(rs.next()) {
				ret = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("Cannot set execute AddCustomer statement");
			e.printStackTrace();
		}
		
		SafeCloseStatement(st);
		
		return ret;
	}
	
	public int AddNote(String note, Timestamp modified, int customerID) {
		String query = "INSERT INTO notes(note,modified,customerID) VALUES(?,?,?)";
		
		PreparedStatement st;
		ResultSet rs;
		
		int ret = -1;
		
		try {
			st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		} catch (SQLException e) {
			System.out.println("Cannot prepare statement for AddNote");
			e.printStackTrace();
			
			return -1;
		}
		
		try {
			st.setString(1, note);
			st.setTimestamp(2, modified);
			st.setInt(3, customerID);
		} catch (SQLException e) {
			SafeCloseStatement(st);
			
			System.out.println("Cannot set parameters for AddNote statement");
			e.printStackTrace();
			
			return -1;
		}
		
		try {
			st.execute();
		} catch (SQLException e) {
			SafeCloseStatement(st);
			
			System.out.println("Cannot set execute AddNote statement");
			e.printStackTrace();
			
			return -1;
		}
		
		try {
			rs = st.getGeneratedKeys();
			if(rs.next()) {
				ret = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("Cannot set execute AddNote statement");
			e.printStackTrace();
		}
		
		SafeCloseStatement(st);
		
		return ret;
	}
	
	public boolean ChangeNote(int id, String text, Timestamp date) {
		String query = "UPDATE notes SET note=?,modified=? WHERE id=?";
		
		PreparedStatement st;
		
		try {
			st = conn.prepareStatement(query);
		} catch (SQLException e) {
			System.out.println("Cannot prepare statement for ChangeNote");
			e.printStackTrace();
			
			return false;
		}
		
		try {
			st.setString(1, text);
			st.setTimestamp(2, date);
			st.setInt(3, id);
		} catch (SQLException e) {
			SafeCloseStatement(st);
			
			System.out.println("Cannot set parameters for ChangeNote statement");
			e.printStackTrace();
			
			return false;
		}
		
		try {
			st.execute();
		} catch (SQLException e) {
			SafeCloseStatement(st);
			
			System.out.println("Cannot set execute ChangeNote statement");
			e.printStackTrace();
			
			return false;
		}
		
		SafeCloseStatement(st);
		
		return true;
	}
}
