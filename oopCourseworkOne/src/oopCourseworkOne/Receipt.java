package oopCourseworkOne;

import java.time.LocalDate;

public class Receipt {
	private double amount;
	private String paymentMethodDetails; // is paypal email or Credit Card Details.
	private paymentType payType; // enum for payment type.
	private Address billingAddress;
	private LocalDate date;

	public Receipt(double amount, String paymentMethodDetails, paymentType payType, Address billingAddress,
			LocalDate date) {
		this.payType = payType;
		this.amount = amount;
		this.paymentMethodDetails = paymentMethodDetails;
		this.billingAddress = billingAddress;
		this.date = date;
	}

	@Override
	public String toString() {
		switch (payType) {
		case PAYPAL:
			return amount + " paid via Paypal using " + paymentMethodDetails + " on " + date
					+ ", and the billing address is " + billingAddress;
		case CARD:
			return amount + " paid by Credit Card using " + paymentMethodDetails + " on " + date
					+ ", and the billing address is " + billingAddress;
		default:
			return "error";
		}
	}
}
