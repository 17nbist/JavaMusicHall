package oopCourseworkOne;

public class PerformanceEvent extends LiveEvent {
	private PerformanceType type; // comedy, theatre or magic.
	private String language; // the additional information for Performances is the language used.

	public PerformanceEvent(int eventID, String eventName, AgeRestrictionCategory restriction, int quantityInStock,
			double performanceFee, double ticketPrice, LiveEventCategory liveEventCategory, PerformanceType type, String language) {
		super(eventID, eventName, restriction, quantityInStock, performanceFee, ticketPrice, liveEventCategory);
		this.type=type;
		this.language = language;
	}

	public PerformanceType getPerformanceType() {
		return type;
	}

	public String getLanguage() {
		return language;
	}
	
	//Used to output the object information to the GUI interface
	@Override
	public String toString() {
	    return "PerformanceEvent [ID=" + getEventID() 
	         + ", Name=" + getEventName() 
	         + ", Age Restriction=" + getAgeRestriction() 
	         + ", Stock=" + getQuantityInStock() 
	         + ", Ticket Price=£" + getTicketPrice() 
	         + ", Performance Fee=£" + getPerformanceFee() 
	         + ", Category=" + getEventCategory() 
	         + ", Type=" + type 
	         + ", Language=" + language 
	         + "]";
	}
}
