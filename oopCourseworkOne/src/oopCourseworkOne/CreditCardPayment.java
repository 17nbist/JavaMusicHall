package oopCourseworkOne;

import java.time.LocalDate;

/**
 * Processes payment via Credit Card. Requires a 6-digit card number and a
 * 3-digit security code.
 */
public class CreditCardPayment implements PaymentMethod {
	private String cardNumber;
	private String securityCode;
	private paymentType payType;

	public CreditCardPayment(String cardNumber, String securityCode) {
		this.cardNumber = cardNumber;
		this.securityCode = securityCode;
		this.payType = paymentType.CARD;
	}
	
	//Receipt is then outputted to user in GUI prompt.
	@Override
	public Receipt processPayment(double amount, Address fullAddress) {
		return new Receipt(amount, cardNumber, payType, fullAddress, LocalDate.now());
	}
}
