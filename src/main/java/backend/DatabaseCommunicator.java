package backend;

import backend.DatabaseObject;
import backend.Member;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;

/**
 * Singleton class to manage the connection the the database 
 * @author cbrown
 *
 */
public class DatabaseCommunicator 
{
	private static final String DATABASE_URL = "jdbc:mysql://checkyodata.cqxvubssbsqb.us-west-2.rds.amazonaws.com:3306/";
	private static final String DATABASE_NAME = "meathead_manager"; 
	private static DatabaseCommunicator instance; 
	private static Connection connection = null; 
	
	private DatabaseCommunicator() {}; 
	
	/**
	 * Method to retrieve the DatabaseCommunicator that handles the database queries
	 * @return the DatabaseCommunicator instance with an established connection
	*/
	public static DatabaseCommunicator getInstance() {
		if (instance == null) {
			instance = new DatabaseCommunicator(); 
		}
		connection = connect(); 
		return instance; 
	}
	
	/** 
	 * Method to insert Java objects to the database
	 * @param object any instance of a DatabaseObject
	 */
	public static void addToDatabase(DatabaseObject object) {
		String insertStatement = "INSERT INTO " + object.getTable() + "(" + object.getKeys() + ") VALUES (" + 
				object.getValues() + ");"; 
		databaseUpdate(insertStatement); 
	}
	
	public static void deleteFromDatabase(DatabaseObject object, String keyValue) {
	  System.out.println("This should be class id: " + object.getKeyIdentifier());
		String deleteStatement = "DELETE FROM " + object.getTable() + " WHERE " + object.getKeyIdentifier() + "=" + 
				keyValue + ";"; 
		System.out.println("Delete statement: " + deleteStatement);
		databaseUpdate(deleteStatement); 
	}
	/**
	 * Method to update the values of an entity in the database
	 * Use of replace rather than update allows bypassing checking which values were changed
	 * Not best practice but used for short implementation timeline
	 * @param object the object with updated values
	 */
	public static void replaceDatabase(DatabaseObject object)
	{
		String update = ("REPLACE INTO " + object.getTable() + " (" + object.getKeys() + ") "
				+ "VALUES (" + object.getValues() + ");");
		databaseUpdate(update);
	}
	
	public static GymClass getGymClass(String classID) {
	  HashMap<String, Object> row = queryDatabase("SELECT * FROM classes WHERE classID = '" + classID + "';").get(0);
	  String name = row.get("name").toString();
	  String startTime = row.get("startTime").toString();
	  String endTime = row.get("endTime").toString();
	  String date = row.get("days").toString();
	  String room = row.get("room").toString();
	  int capacity = (int) row.get("capacity");
	  int reserved = (int) row.get("reserved");
	  
	  GymClass gClass = new GymClass(classID, name, startTime, endTime, date, room, capacity, reserved);
	  return gClass;
	  
	}
	
	public static Member getMember(String phoneNumber) {
		HashMap<String, Object> row = queryDatabase("SELECT * FROM members WHERE phone_number='" + phoneNumber + "';").get(0); 
		String firstName = row.get("first_name").toString();
		String lastName = row.get("last_name").toString(); 
		String email = row.get("email").toString(); 
		String address = row.get("address").toString();
		String city = row.get("city").toString(); 
		String state = row.get("state").toString();
		int zipCode = Integer.parseInt(row.get("zip_code").toString()); 
		String memberType = row.get("member_type").toString(); 

		Member member = new Member(firstName, lastName, email, phoneNumber, address, city, state, zipCode, memberType); 
		return member; 
	}

	public static InventoryItem getInventoryItem(String name) {
		HashMap<String, Object> row = queryDatabase("SELECT * FROM inventory WHERE name='" + name + "';").get(0);
		String itemName = row.get("name").toString();
		int itemCount = Integer.parseInt(row.get("count").toString());

		InventoryItem inventoryItem = new InventoryItem(itemName, itemCount);
		return inventoryItem;
	}
	/**
	 * Method to query the database for some information 
	 * Note the keys from the returned HashMaps are the table column names
	 * @param query The SQL query describing the desired information
	 * @return a list of "rows" resulting from the query
	 */
	public static List<HashMap<String, Object>> queryDatabase(String query)
	{
		ResultSet rs = null;
		Statement stmt = null;
		
		List<HashMap<String,Object>> result = new ArrayList<HashMap<String,Object>>();
		
		if(connection != null)
		{
			try {
				stmt = (Statement) connection.createStatement();
				rs = stmt.executeQuery(query);
				
				ResultSetMetaData md = (ResultSetMetaData) rs.getMetaData();
				int col = md.getColumnCount();

				while (rs.next()) {
				    HashMap<String, Object> row = new HashMap<String, Object>(col);
				    for(int i = 1; i <= col; ++i) {
				        row.put(md.getColumnName(i), rs.getObject(i));
				    }
				    result.add(row);
				}
				
				rs.close();
				stmt.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * Method to update database information
	 * @param action The SQL query string describing the desired update
	 */
	private static void databaseUpdate(String action)
	{
		Statement stmt = null;
		
		if(connection != null)
		{
			try {
			stmt = (Statement) connection.createStatement();
			stmt.executeUpdate(action);
			stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Method to establish a connection to the Amazon RDS database instance
	 * @return the connection to the database to be used for queries and updates
	 */
	private static Connection connect()
	{
		
		String url = DATABASE_URL; 
		String dbName = DATABASE_NAME; 

		// SUPER NOT SECURE OR ANYTHING
		String userName = "admin";
		String password = "stancheV365";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = (Connection) DriverManager.getConnection(url + dbName, userName, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return connection;
	}
	
	/**
	 * Method to be invoked when the application window gets closed
	 * @throws SQLException
	 */
	public static void closeConnection() throws SQLException {
		connection.close();
	}
	
}