---

# Performance-Hall Ticketing System

A GUI application built using a JFrame for managing events and ticket sales at a performance hall. This project provides support for Admin and Customer roles, each with distinct levels of permissions and functionality.

**Language:** Java 21  
**IDE:** Eclipse 2025  

---

## Prerequisites

-   JDK 21
-   Eclipse IDE for Java Developers (2025.X)
-   Apache Maven (this is built into Eclipse so you can easily build a JAR executable)

---

## Setup & Build

### 1. Repository Cloning

```bash
git clone https://your-repo-url.git
cd performance-hall
```

### 2. Importing into Eclipse

1.  Open Eclipse.
2.  Go to `File` -> `Import...`.
3.  Select `Maven` -> `Existing Maven Projects` and click `Next`.
4.  Browse to the cloned `performance-hall` directory and click `Finish`.
5.  Eclipse will automatically download dependencies and perform setup for you.

### 3. Building the project

You can build the executable JAR from within Eclipse or via the command line.
1. Go to `File` -> `Export...`
2. Select `Java` -> `Runnable JAR file` and click `Next`.
3. Select your export destination and select `Extract required generated libraries into generated JAR` to true
4. Select finish and be sure to include both .txt files in the source file of the jar.

---

## Data Files

The application's data is stored in 2 .txt files.

### `users.txt`

Stores user information.

**Format:** `user ID, username, name,
house number, postcode, city, role`

**Sample:**
```txt
101, user1, Sarasvati, 12, LE11 3TU, Loughborough, admin
102, user2, Felix, 14, E20 3BS, London, customer
103, user3, Hikaru, 100, BN1 3XP, Brighton, customer
104, user4, Salma, 57, PA3 2SW, Glasgow, customer
```

### `events.txt`

Stores the initial list of events in stock.

**Format:** `event ID, event category, event type, event name,
age restrictions, quantity of tickets, performance fee, ticket price, additional information`

**Sample:**
```txt
 53201, Performance, Theatre, Shakespeare’s Macbeth, All, 400, 5000, 50, English
 21908, Music, DJ Set, EDM Madness, Adults, 1000, 7000, 30, 2
 56723, Performance, Stand-up Comedy, Laugh Out Loud, Adults, 250, 1500, 25, Japanese
 37412, Music, Live Concert, Rock Legends Live, All, 500, 5000, 50, 5
 12039, Music, Live Concert, Jazz Night, All, 300, 3000, 40, 3
 68251, Performance, Theatre, A Midsummer Night’s Dream, All, 450, 6000, 60, Arabic
 57381, Music, DJ Set, Underground Sounds, Adults, 600, 4000, 20, 3
 14935, Performance, Magic, Enchanted Wonders, All, 150, 2500, 35, English
 92674, Music, Live Concert, Summer Beats Festival, All, 1000, 15000, 25, 10
 47125, Performance, Theatre, The Great Escape, All, 350, 4500, 55, Spanish
```

---

## Features

### Admin Role

-   **View All Events:** View all events alongside their attributes sorted ascending by ticket price.
-   **Add New Event:**  Add an event to the existing event list (stock). Duplicate IDs are not supported.

### Customer Role

-   **View All Events:** View all available events along with their attributes, excluding the performance fee, sorted
ascending by ticket price
-   **Search Events:**
    -   By Event ID for a direct lookup.
    -   By language for performance-type events.
-   **Shopping Basket:**
    -   Add tickets to their shopping basket.
    -   View the contents of their shopping basket.
    -   Cancel their shopping basket which empties out the entire content of the basket.
-   **Checkout:**
    -   Complete the payment for all items in the basket by selecting one of the available payment
methods:
        1.  PayPal, requiring the entry of the customer’s PayPal email address.
        2.  Credit Card, requiring the entry of a 6-digit card number and a 3-digit security code.
    -   Upon successful payment, stock is updated, the basket is cleared and a formatted receipt is printed to the screen.

#### Receipt Formats

-   **PayPal:**
    ```
    [amount] paid via PayPal using [email] on [date], and the billing address is [full address]
    ```
-   **Credit Card:**
    ```
    [amount] paid by Credit Card using [card number] on [date], and the billing address is [full address]
    ```

---

## Error Handling

- The system alerts customers if a purchase is made for an out-of-stock item.
-   The system will not let admins add events with an already existing ID in the database.
