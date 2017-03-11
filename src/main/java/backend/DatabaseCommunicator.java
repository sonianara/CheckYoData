package backend;

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
	private static final String DATABASE_NAME = "CheckYoRecords"; 
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
	 * Method to query the database for some information 
	 * @param query The SQL query describing the desired information
	 * @return
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