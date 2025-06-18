package oopCourseworkOne;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.swing.*;

public class PerformanceHallMainGUI extends JFrame {
	private JPanel contentPane;
	private CardLayout cardLayout;

	private JPanel loginPanel, adminPanel, customerPanel;
	private JComboBox<String> userComboBox;

	private List<User> userList;
	private List<LiveEvent> eventStock;
	private User currentUser;
	private int userAge;

	private JTextArea adminTextArea;
	private JTextArea customerTextArea;
	private JTextArea basketTextArea;

	public static void main(String[] args) {
		try {
			PerformanceHallMainGUI frame = new PerformanceHallMainGUI();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PerformanceHallMainGUI() {
		// Load stock and user data and setup main frame properties
		userList = LoadData.loadAllUsers();
		eventStock = LoadData.loadAllEvents();
		setTitle("Performance Hall System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 650);
		contentPane = new JPanel();
		cardLayout = new CardLayout();
		contentPane.setLayout(cardLayout);
		setContentPane(contentPane);

		buildLoginPanel();
		buildAdminPanel();
		buildCustomerPanel();

		cardLayout.show(contentPane, "LoginPanel");
	}

	private void buildLoginPanel() {
		loginPanel = new JPanel();
		loginPanel.setLayout(null);

		JLabel title = new JLabel("Login - Select User");
		title.setFont(new Font("Yu Gothic UI", Font.BOLD, 20));
		title.setBounds(350, 50, 250, 30);
		loginPanel.add(title);

		userComboBox = new JComboBox<>();
		for (User u : userList) {
			userComboBox.addItem(u.getUsername());
		}
		userComboBox.setBounds(350, 120, 200, 25);
		loginPanel.add(userComboBox);

		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(400, 170, 100, 30);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selected = (String) userComboBox.getSelectedItem();
				for (User u : userList) {
					if (u.getUsername().equalsIgnoreCase(selected)) {
						currentUser = u;
						break;
					}
				}
				if (currentUser == null) {
					JOptionPane.showMessageDialog(null, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (currentUser.getUserType().equals(UserCategory.ADMIN)) {
					updateAdminPanel();
					cardLayout.show(contentPane, "AdminPanel");
				} else if (currentUser.getUserType().equals(UserCategory.CUSTOMER)) {
					try {
						userAge = Integer.parseInt(JOptionPane.showInputDialog(loginPanel, "Enter User Age:"));// request
																												// customer
																												// age  :)
																												// for
																												// age
																												// verification!
					if(userAge<1) {
						throw new IllegalArgumentException("INVALID");
					}
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(loginPanel, "Invalid age Input! Exiting.", "Error",
								JOptionPane.ERROR_MESSAGE);
						System.exit(1);
					}
					updateCustomerPanel();
					cardLayout.show(contentPane, "CustomerPanel");
				}
			}
		});
		loginPanel.add(btnLogin);

		contentPane.add(loginPanel, "LoginPanel");
	}

	private void buildAdminPanel() {
		adminPanel = new JPanel();
		adminPanel.setLayout(null);

		JLabel lblTitle = new JLabel("Administrator Dashboard");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitle.setBounds(320, 20, 300, 30);
		adminPanel.add(lblTitle);

		adminTextArea = new JTextArea();
		adminTextArea.setEditable(false);
		adminTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
		JScrollPane scroll = new JScrollPane(adminTextArea);
		scroll.setBounds(50, 70, 800, 400);
		adminPanel.add(scroll);

		JButton btnAdd = new JButton("Add New Event");
		btnAdd.setBounds(50, 500, 150, 30);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adminAddEvent();
			}
		});
		adminPanel.add(btnAdd);

		JButton btnModify = new JButton("Modify Event");
		btnModify.setBounds(220, 500, 150, 30);
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adminModifyEvent();
			}
		});
		adminPanel.add(btnModify);

		JButton btnLogout = new JButton("Logout");
		btnLogout.setBounds(700, 500, 150, 30);
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentUser = null;
				cardLayout.show(contentPane, "LoginPanel");
			}
		});
		adminPanel.add(btnLogout);

		contentPane.add(adminPanel, "AdminPanel");
	}

	private void adminAddEvent() {
		try {
			String idString = JOptionPane.showInputDialog(adminPanel, "Enter Event ID (5-digit integer):");
			//forbid invalid types.
			if (idString == null || idString.length()!=5) {
				JOptionPane.showMessageDialog(adminPanel, "Invalid id entered!", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			int id = Integer.parseInt(idString.trim());
			String name = JOptionPane.showInputDialog(adminPanel, "Enter Event Name:");
			if (name == null)
				return;
			String category = JOptionPane.showInputDialog(adminPanel, "Enter Event Category (Music or Performance):");
			if (category == null)
				return;
			category = category.trim();
			String typeInput;
			LiveEvent newEvent = null;
			String ticketPriceStr = JOptionPane.showInputDialog(adminPanel, "Enter Ticket Price:");
			double ticketPrice = Double.parseDouble(ticketPriceStr.trim());
			String quantityString = JOptionPane.showInputDialog(adminPanel, "Enter Quantity in Stock:");
			int quantity = Integer.parseInt(quantityString.trim());
			//forbid negative numbers from being entered.
			if (quantity < 1 || quantityString == null) {
				JOptionPane.showMessageDialog(adminPanel, "Invalid number entered!", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			String feeString = JOptionPane.showInputDialog(adminPanel, "Enter Performance Fee:");
			double fee = Double.parseDouble(feeString.trim());
			String ageString = JOptionPane.showInputDialog(adminPanel, "Enter Age Restriction (All or Adults) invalid input defaults to All:");
			AgeRestrictionCategory restriction = ageString.trim().equalsIgnoreCase("all") ? AgeRestrictionCategory.ALL
					: AgeRestrictionCategory.ADULTS;
			if (category.equalsIgnoreCase("Music")) {
				typeInput = JOptionPane.showInputDialog(adminPanel, "Enter Music Type (LIVE CONCERT or DJ SET):");
				MusicType mType;
				if (typeInput.trim().equalsIgnoreCase("LIVE CONCERT"))
					mType = MusicType.LIVE;
				else if (typeInput.trim().equalsIgnoreCase("DJ SET"))
					mType = MusicType.DJ;
				else
					throw new IllegalArgumentException("Invalid Music Type");
				String countStr = JOptionPane.showInputDialog(adminPanel, "Enter number of Bands/DJs:");
				int count = Integer.parseInt(countStr.trim());
				newEvent = new MusicEvent(id, name, restriction, quantity, fee, ticketPrice, LiveEventCategory.MUSIC,
						mType, count);
			} else if (category.equalsIgnoreCase("Performance")) {
				typeInput = JOptionPane.showInputDialog(adminPanel,
						"Enter Performance Type (STAND-UP COMEDY, THEATRE, MAGIC):");
				PerformanceType pType;
				if (typeInput.trim().equalsIgnoreCase("STAND-UP COMEDY"))
					pType = PerformanceType.COMEDY;
				else if (typeInput.trim().equalsIgnoreCase("THEATRE"))
					pType = PerformanceType.THEATRE;
				else if (typeInput.trim().equalsIgnoreCase("MAGIC"))
					pType = PerformanceType.MAGIC;
				else
					throw new IllegalArgumentException("Invalid Performance Type");
				String language = JOptionPane.showInputDialog(adminPanel, "Enter Language:");
				newEvent = new PerformanceEvent(id, name, restriction, quantity, fee, ticketPrice,
						LiveEventCategory.PERFORMANCE, pType, language);
			} else {
				JOptionPane.showMessageDialog(adminPanel, "Invalid Category!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			// Check for duplicate eventID
			for (LiveEvent e : eventStock) {
				if (e.getEventID() == id) {
					JOptionPane.showMessageDialog(adminPanel, "Event with this ID exists!", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			
			LoadData.addNewEvent(newEvent);
			eventStock = LoadData.loadAllEvents();
			JOptionPane.showMessageDialog(adminPanel, "Event added!");
			updateAdminPanel();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(adminPanel, "Error adding event: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void adminModifyEvent() {
		try {
			// request for eventID to modify
			String idStr = JOptionPane.showInputDialog(adminPanel, "Enter Event ID to modify:");
			if (idStr == null)
				return;
			int id = Integer.parseInt(idStr.trim());

			// Find the event with given eventID
			LiveEvent target = null;
			for (LiveEvent e : eventStock) {
				if (e.getEventID() == id) {
					target = e;
					break;
				}
			}

			// Request updated common event details
			if (target == null) {
				JOptionPane.showMessageDialog(adminPanel, "Event not found!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String name = JOptionPane.showInputDialog(adminPanel, "Enter new Event Name:", target.getEventName());
			if (name == null)
				return;
			String ticketPriceString = JOptionPane.showInputDialog(adminPanel, "Enter new Ticket Price:",
					target.getTicketPrice());
			double ticketPrice = Double.parseDouble(ticketPriceString.trim());
			String quantityString = JOptionPane.showInputDialog(adminPanel, "Enter new Quantity in Stock:",
					target.getQuantityInStock());
			int quantity = Integer.parseInt(quantityString.trim());
			//forbid negative numbers from being entered.
			if (quantity < 1 || quantityString == null) {
				JOptionPane.showMessageDialog(adminPanel, "Invalid number entered!", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			String feeString = JOptionPane.showInputDialog(adminPanel, "Enter new Performance Fee:",
					target.getPerformanceFee());
			double fee = Double.parseDouble(feeString.trim());
			String ageString = JOptionPane.showInputDialog(adminPanel, "Enter Age Restriction (All or Adults) defaults to All if invalid:",
					target.getAgeRestriction().name());
			AgeRestrictionCategory restriction = ageString.trim().equalsIgnoreCase("all") ? AgeRestrictionCategory.ALL
					: AgeRestrictionCategory.ADULTS;

			LiveEvent updated = null;

			// Process event type specific fields
			if (target.getEventCategory() == LiveEventCategory.MUSIC) {
				MusicEvent me = (MusicEvent) target;
				String typeInput = JOptionPane.showInputDialog(adminPanel,
						"Enter new Music Type (LIVE CONCERT or DJ SET):", me.getMusicType().name());
				MusicType mType;
				if (typeInput.trim().equalsIgnoreCase("LIVE CONCERT"))
					mType = MusicType.LIVE;
				else if (typeInput.trim().equalsIgnoreCase("DJ SET"))
					mType = MusicType.DJ;
				else
					throw new IllegalArgumentException("Invalid Music Type");
				String countString = JOptionPane.showInputDialog(adminPanel, "Enter new number of Bands/DJs:",
						me.getPerformerCount());
				int count = Integer.parseInt(countString.trim());
				updated = new MusicEvent(id, name, restriction, quantity, fee, ticketPrice, LiveEventCategory.MUSIC,
						mType, count);
			} else if (target.getEventCategory() == LiveEventCategory.PERFORMANCE) {
				PerformanceEvent pe = (PerformanceEvent) target; // typecast to access specifics
				String typeInput = JOptionPane.showInputDialog(adminPanel,
						"Enter new Performance Type (STAND-UP COMEDY, THEATRE, MAGIC):",
						pe.getPerformanceType().name());
				PerformanceType pType;
				if (typeInput.trim().equalsIgnoreCase("STAND-UP COMEDY"))
					pType = PerformanceType.COMEDY;
				else if (typeInput.trim().equalsIgnoreCase("THEATRE"))
					pType = PerformanceType.THEATRE;
				else if (typeInput.trim().equalsIgnoreCase("MAGIC"))
					pType = PerformanceType.MAGIC;
				else
					throw new IllegalArgumentException("Invalid Performance Type");
				String language = JOptionPane.showInputDialog(adminPanel, "Enter new Language:", pe.getLanguage());
				updated = new PerformanceEvent(id, name, restriction, quantity, fee, ticketPrice,
						LiveEventCategory.PERFORMANCE, pType, language);
			} else {
				JOptionPane.showMessageDialog(adminPanel, "Incompatible event type!", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Save the changes and reload display.
			LoadData.modifyEvent(updated);
			eventStock = LoadData.loadAllEvents();
			JOptionPane.showMessageDialog(adminPanel, "Event modified!");
			updateAdminPanel();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(adminPanel, "Error modifying event: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void updateAdminPanel() {
		// Sort events ascending style and present display text
		Collections.sort(eventStock, Comparator.comparingDouble(LiveEvent::getTicketPrice));
		StringBuilder sb = new StringBuilder();
		for (LiveEvent e : eventStock) {
			sb.append(e.toString()).append(" | Performance Fee: £").append(e.getPerformanceFee()).append("\n");
		}
		adminTextArea.setText(sb.toString());

	}

	private void buildCustomerPanel() {
		customerPanel = new JPanel();
		customerPanel.setLayout(null);

		JLabel lblTitle = new JLabel("Customer Dashboard");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitle.setBounds(350, 20, 250, 30);
		customerPanel.add(lblTitle);

		customerTextArea = new JTextArea();
		customerTextArea.setEditable(false);
		customerTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
		JScrollPane scrollCust = new JScrollPane(customerTextArea);
		scrollCust.setBounds(50, 70, 800, 200);
		customerPanel.add(scrollCust);

		basketTextArea = new JTextArea();
		basketTextArea.setEditable(false);
		basketTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
		JScrollPane scrollBasket = new JScrollPane(basketTextArea);
		scrollBasket.setBounds(50, 290, 800, 100);
		customerPanel.add(scrollBasket);

		JButton btnAddTickets = new JButton("Add Tickets");
		btnAddTickets.setBounds(50, 410, 140, 30);
		btnAddTickets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTicketsToBasket();
			}
		});
		customerPanel.add(btnAddTickets);

		JButton btnCheckout = new JButton("Checkout");
		btnCheckout.setBounds(210, 410, 140, 30);
		btnCheckout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processCheckout();
			}
		});
		customerPanel.add(btnCheckout);

		JButton btnCancel = new JButton("Cancel Basket");
		btnCancel.setBounds(370, 410, 140, 30);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Customer cust = (Customer) currentUser;
				cust.getBasket().clear();
				updateBasketTextArea(cust.getBasket());
				JOptionPane.showMessageDialog(customerPanel, "Basket cleared.", "Info",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		customerPanel.add(btnCancel);

		JButton btnSearchID = new JButton("Search Event ID");
		btnSearchID.setBounds(530, 410, 140, 30);
		btnSearchID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchEventByID();
			}
		});
		customerPanel.add(btnSearchID);

		JButton btnSearchLang = new JButton("Search by Language");
		btnSearchLang.setBounds(50, 460, 140, 30);
		btnSearchLang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchPerformancesByLanguage();
			}
		});
		customerPanel.add(btnSearchLang);

		JButton btnRefresh = new JButton("Refresh Events");
		btnRefresh.setBounds(210, 460, 140, 30);
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateCustomerPanel();
			}
		});
		customerPanel.add(btnRefresh);

		JButton btnLogout = new JButton("Logout");
		btnLogout.setBounds(700, 550, 150, 30);
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentUser = null;
				cardLayout.show(contentPane, "LoginPanel");
			}
		});
		customerPanel.add(btnLogout);

		contentPane.add(customerPanel, "CustomerPanel");
	}

	private void updateCustomerPanel() {
		// Sort events ascending style and present display text
		Collections.sort(eventStock, Comparator.comparingDouble(LiveEvent::getTicketPrice));

		StringBuilder sb = new StringBuilder();
		for (LiveEvent e : eventStock) {
			sb.append("ID: ").append(e.getEventID()).append(" | Name: ").append(e.getEventName()).append(" | Age: ")
					.append(e.getAgeRestriction()).append(" | Stock: ").append(e.getQuantityInStock())
					.append(" | Ticket Price: £").append(e.getTicketPrice()).append("\n");
		}
		customerTextArea.setText(sb.toString());
	}

	private void updateBasketTextArea(ShoppingBasket basket) {
		basketTextArea.setText(basket.toString());
	}

	private void addTicketsToBasket() {
		String idStr = JOptionPane.showInputDialog(customerPanel, "Enter Event ID:");
		if (idStr == null || idStr.trim().isEmpty())
			return;
		int id;
		try {
			id = Integer.parseInt(idStr.trim());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(customerPanel, "Invalid Event ID", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		LiveEvent selected = null;
		for (LiveEvent e : eventStock) {
			if (e.getEventID() == id) {
				selected = e;
				break;
			}
		}
		if (selected == null) {
			JOptionPane.showMessageDialog(customerPanel, "Event not found!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// age verification check against ticket.
		if (userAge < 18 && selected.getAgeRestriction() == AgeRestrictionCategory.ADULTS) {
			JOptionPane.showMessageDialog(customerPanel, "You must be 18+ to buy this ticket.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		String quantityString = JOptionPane.showInputDialog(customerPanel, "Enter Ticket Quantity:");
		if (quantityString == null || quantityString.trim().isEmpty())
			return;
		int quantity;
		try {
			quantity = Integer.parseInt(quantityString.trim());
			if (quantity < 1) {
				JOptionPane.showMessageDialog(adminPanel, "Negative number not allowed!", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(customerPanel, "Invalid quantity", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (quantity > selected.getQuantityInStock()) {
			JOptionPane.showMessageDialog(customerPanel, "Not enough tickets in stock!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		Customer cust = (Customer) currentUser;
		cust.getBasket().addTickets(selected, quantity);
		updateBasketTextArea(cust.getBasket());
		JOptionPane.showMessageDialog(customerPanel, "Tickets added to basket.", "Info",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void processCheckout() {
		Customer customer = (Customer) currentUser;
		double total = customer.getBasket().getTotalCost();
		if (total <= 0) {
			JOptionPane.showMessageDialog(customerPanel, "Basket is empty!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String choice = JOptionPane.showInputDialog(customerPanel,
				"Total: £" + total + "\nEnter Payment Method:\n1 = PayPal\n2 = Credit Card:");
		if (choice == null || choice.trim().isEmpty())
			return;
		PaymentMethod pm = null;
		if (choice.trim().equals("1")) {
			String email = JOptionPane.showInputDialog(customerPanel, "Enter your PayPal email:");
			if (email == null || email.trim().isEmpty())
				return;
			//regex match to make sure email is of valid input- must end in valid domain and have an @ symbol.
			if (!email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
				JOptionPane.showMessageDialog(customerPanel,
						"Email must match following form- ABCD@XYZ.(com//org//etc)", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			pm = new PayPalPayment(email.trim());
		} else if (choice.trim().equals("2")) {
			String cardNumber = JOptionPane.showInputDialog(customerPanel, "Enter your 6-digit card number:");
			String securityCode = JOptionPane.showInputDialog(customerPanel, "Enter your 3-digit security code:");
			if (cardNumber == null || securityCode == null || cardNumber.trim().isEmpty()
					|| securityCode.trim().isEmpty())
				return;
			pm = new CreditCardPayment(cardNumber.trim(), securityCode.trim());
		} else {
			JOptionPane.showMessageDialog(customerPanel, "Invalid payment method selection!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		Receipt receipt = pm.processPayment(total, customer.getUserAddress());
		for (BasketItem item : customer.getBasket().getItems()) {
			LiveEvent event = item.getEvent();
			int newStock = event.getQuantityInStock() - item.getQuantity();
			event.setQuantityInStock(newStock);
		}
		customer.getBasket().clear();
		updateBasketTextArea(customer.getBasket());
		JOptionPane.showMessageDialog(customerPanel, receipt.toString(), "Receipt", JOptionPane.INFORMATION_MESSAGE);
		LoadData.saveAllEvents(eventStock);
		updateCustomerPanel();
	}

	private void searchEventByID() {
		String idString = JOptionPane.showInputDialog(customerPanel, "Enter Event ID to search:");
		if (idString == null || idString.trim().isEmpty())
			return;
		int id;
		try {
			id = Integer.parseInt(idString.trim());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(customerPanel, "Invalid Event ID!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		LiveEvent found = null;
		for (LiveEvent e : eventStock) {
			if (e.getEventID() == id) {
				found = e;
				break;
			}
		}
		if (found != null) {
			JOptionPane.showMessageDialog(customerPanel, found.toString(), "Event Found",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(customerPanel, "Event not found!", "Info", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void searchPerformancesByLanguage() {
		String lang = JOptionPane.showInputDialog(customerPanel, "Enter language to search for performances:");
		if (lang == null || lang.trim().isEmpty())
			return;
		StringBuilder sb = new StringBuilder();
		for (LiveEvent e : eventStock) {
			if (e.getEventCategory() == LiveEventCategory.PERFORMANCE) {
				PerformanceEvent pe = (PerformanceEvent) e;
				if (pe.getLanguage().equalsIgnoreCase(lang.trim())) {
					sb.append(pe.toString()).append("\n");
				}
			}
		}
		if (sb.length() == 0)
			JOptionPane.showMessageDialog(customerPanel, "No performances found for language: " + lang, "Info",
					JOptionPane.INFORMATION_MESSAGE);
		else
			JOptionPane.showMessageDialog(customerPanel, sb.toString(), "Search Results",
					JOptionPane.INFORMATION_MESSAGE);
	}
}
