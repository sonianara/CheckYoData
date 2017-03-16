package backend;

public class User implements DatabaseObject {
	private String username;
	private String password;
	
	public User() {
		
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getKeys() {
		return "username, password";
	}

	@Override
	public String getValues() {
		return "'" + username + "', '" + password + "'";
	}

	@Override
	public String getTable() {
		return "users";
	}

	@Override
	public String getKeyIdentifier() {
		return "id";
	}
	
}
