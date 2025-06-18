package oopCourseworkOne;

public class BasketItem {
	private LiveEvent event;
	private int quantity;

	public BasketItem(LiveEvent event, int quantity) {
		this.event = event;
		this.quantity = quantity;
	}

	public LiveEvent getEvent() {
		return event;
	}

	public int getQuantity() {
		return quantity;
	}

	public void addQuantity(int count) {
		this.quantity += count;
	}

	public double getPrice() {
		return event.getTicketPrice() * quantity;
	}
	
	//Used to output all items currently in basket for a GUI component.
	@Override
	public String toString() {
		return event.getEventName() + " (x" + quantity + ") - £" + event.getTicketPrice() + " each; Subtotal: £"
				+ (event.getTicketPrice() * quantity);
	}

}
