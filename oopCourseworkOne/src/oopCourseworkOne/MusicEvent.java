package oopCourseworkOne;

public class MusicEvent extends LiveEvent {
	private MusicType type; // live concert or dj set.
	private int performerCount; // additional information for Music Events are the number of Bands/DJs playing.

	public MusicEvent(int eventID, String eventName, AgeRestrictionCategory restriction, int quantityInStock,
			double performanceFee, double ticketPrice, LiveEventCategory liveEventCategory, MusicType type,
			int performerCount) {
		super(eventID, eventName, restriction, quantityInStock, performanceFee, ticketPrice, liveEventCategory);
		this.type = type;
		this.performerCount = performerCount;
	}

	public MusicType getMusicType() {
		return type;
	}

	public int getPerformerCount() {
		return performerCount;
	}
	
	//Used to output the object information to the GUI interface
	@Override
	public String toString() {
		return "MusicEvent [ID=" + getEventID() + ", Name=" + getEventName() + ", Age Restriction="
				+ getAgeRestriction() + ", Stock=" + getQuantityInStock() + ", Ticket Price=£" + getTicketPrice()
				+ ", Performance Fee=£" + getPerformanceFee() + ", Category=" + getEventCategory() + ", Type=" + type
				+ ", Performer Count=" + performerCount + "]";
	}

}
