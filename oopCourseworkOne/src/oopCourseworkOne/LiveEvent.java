package oopCourseworkOne;

public abstract class LiveEvent {
	private int eventID;
	private String eventName;
	private LiveEventCategory liveEventCategory;
	private double performanceFee;
	private int quantityInStock;
	private AgeRestrictionCategory restriction;
	private double ticketPrice;
	
	public LiveEvent(int eventID, String eventName, AgeRestrictionCategory restriction, int quantityInStock, double performanceFee, 
			double ticketPrice, LiveEventCategory liveEventCategory) {
		this.eventID = eventID;
		this.eventName = eventName;
		this.liveEventCategory = liveEventCategory;
		this.performanceFee = performanceFee;
		this.quantityInStock = quantityInStock;
		this.restriction = restriction;
		this.ticketPrice = ticketPrice;
	}
	
    public int getEventID() {
        return eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public AgeRestrictionCategory getAgeRestriction() {
        return restriction;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int newQuantityInStock) {
        quantityInStock=newQuantityInStock;
    }

    public double getPerformanceFee() {
        return performanceFee;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public LiveEventCategory getEventCategory() {
        return liveEventCategory;
    }

    @Override
    public abstract String toString();
}
