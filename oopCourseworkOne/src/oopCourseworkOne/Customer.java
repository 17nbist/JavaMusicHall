package oopCourseworkOne;

public class Customer extends User {
	private ShoppingBasket basket;

	public Customer(int userID, String username, String name, Address userAddress, UserCategory userType) {
		super(userID, username, name, userAddress, userType);
		this.basket= new ShoppingBasket();
	}
	
	//get shopping basket to make it accessible for read/write in main
	public ShoppingBasket getBasket() {
		return basket;
	}

}
