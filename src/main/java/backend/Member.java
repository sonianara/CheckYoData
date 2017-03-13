package backend;

public class Member implements DatabaseObject {
	private String firstName; 
	private String lastName; 
	private String email; 
	private String phoneNumber; 
	private String address; 
	private String city; 
	private String state; 
	private int zipCode;
	private String memberType; 
	
	public Member() {
		
	}

	public Member(String firstName, String lastName, String email, String phoneNumber, String address, String city,
			String state, int zipCode, String memberType) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.memberType = memberType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	@Override
	public String getKeys() {
		return "email, first_name, last_name, phone_number, address, city, state, zip_code, member_type, password";
	}

	@Override
	public String getValues() {
		return "'" + email + "', '" + this.firstName + "', '" + this.lastName + "', '" + this.phoneNumber + "', '" +
				this.address + "', '" + this.city + "', '" + this.state + "', " + this.zipCode + ", '" + this.memberType + 
				"', '" + this.phoneNumber + "'";
	}

	@Override
	public String getTable() {
		return "members";
	}

	@Override
	public String getKeyIdentifier() {
		return "phone_number";
	}
	
}
