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
	
	// find user matching the specified credentials
	List<HashMap<String, Object>> rows = DatabaseCommunicator.queryDatabase(
			query);
	
	// if a user was found, give login permission; else, notify of wrong credentials
	if (rows.size() == 1)
		return true;
	return false;
  } 

}
