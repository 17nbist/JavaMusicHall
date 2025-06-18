package oopCourseworkOne;

import java.time.LocalDate;

/**
 * Processes payment via PayPal and requires email address. email address is validated in main class.
 */
public class PayPalPayment implements PaymentMethod {
	private String email;
	private paymentType payType;

	public PayPalPayment(String email) {
		this.email = email;
		payType = paymentType.PAYPAL;
	}

	@Override
	public Receipt processPayment(double amount, Address fullAddress) {
		return new Receipt(amount, email, payType, fullAddress, LocalDate.now());
	}
}
