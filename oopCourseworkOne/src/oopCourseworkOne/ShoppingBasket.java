package oopCourseworkOne;

import java.util.ArrayList;
import java.util.List;

public class ShoppingBasket {
	private List<BasketItem> items;

	public ShoppingBasket() {
		items = new ArrayList<>();
	}

	public void addTickets(LiveEvent event, int quantity) {
		for (BasketItem item : items) {
			if (item.getEvent().getEventID() == event.getEventID()) {
				item.addQuantity(quantity);
				return;
			}
		}
		// Not found- create new BasketItem
		items.add(new BasketItem(event, quantity));
	}

	public void clear() {
		items.clear();
	}

	public double getTotalCost() {
		double total = 0;
		for (BasketItem item : items) {
			total += item.getPrice();
		}
		return total;
	}

	public List<BasketItem> getItems() {
		return items;
	}

	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		for (BasketItem item : items) {
			output.append(item);
			output.append("\n");
		}
		output.append("Total price: £");
		output.append(getTotalCost());
		return output.toString();
	}
}
