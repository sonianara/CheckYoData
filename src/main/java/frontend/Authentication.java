package frontend;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import backend.DatabaseCommunicator;

public class Authentication {

  public Authentication() {
    // TODO Auto-generated constructor stub
  }
  
  public boolean login(String username, String password) throws ClassNotFoundException, SQLException {
	DatabaseCommunicator.getInstance();
	
	String query = "SELECT * from users where username = '" + username + "' and password = '" + password + "';";
	
	// populate member list from back end
	List<HashMap<String, Object>> rows = DatabaseCommunicator.queryDatabase(
			query);
	
	if (rows.size() == 1)
		return true;
	return false;
    /*
    boolean login;
    Connection conn = null;
    // USER
    String dbuser = "checkyodata";
    String dbpassw = "stancheV365";
    // NAME OF OUR DATABASE
    String databasename = "checkyodata";
    // NOT SURE ABOUT THE URL 
    String url = "jdbc:mysql://127.0.0.1:3306/DBNAME";
    Class.forName("com.mysql.jdbc.Driver");
    conn = DriverManager.getConnection(url,dbuser,dbpassw);
    try {
      Statement st = conn.createStatement();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  
    PreparedStatement ps = conn.prepareStatement("SELECT `customer_name`, `customer_password` FROM `customer` WHERE `customer_name` = ? AND `customer_password` = ?");

    ps.setString(1, dbuser);
    ps.setString(2, dbpassw);
 
    ResultSet result = ps.executeQuery();
    if(result.next()){
      login = true;
    } 
    else {
      login = false;
    }
    return login; */
  } 

}
