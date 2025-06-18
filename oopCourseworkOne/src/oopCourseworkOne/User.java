package oopCourseworkOne;

public abstract class User {
	private int userID;
	private String username;
	private String name;
	private Address userAddress;
	private UserCategory userType;
	public User(int userID, String username, String name, Address userAddress, UserCategory userType) {
		this.userID = userID;
		this.username = username;
		this.name = name;
		this.userAddress = userAddress;
		this.userType=userType;
	}
	public int getUserID() {
		return userID;
	}
	public String getUsername() {
		return username;
	}
	public String getName() {
		return name;
	}
	public Address getUserAddress() {
		return userAddress;
	}
	public UserCategory getUserType() {
		return userType;
	}
	
}
