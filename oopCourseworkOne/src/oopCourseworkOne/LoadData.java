package oopCourseworkOne;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class LoadData {

    // Load all users from "UserAccounts.txt".
    public static List<User> loadAllUsers() {
        List<User> users = new ArrayList<>();
        StringBuilder errors = new StringBuilder();
        try {
            File f = new File("UserAccounts.txt"); // hardcoded file location
            Scanner s = new Scanner(f);

            while (s.hasNextLine()) {
                String l = s.nextLine().trim();
                if (l.isEmpty()) {
                    continue;
                }
                String[] tokens = l.split(",");
                if (tokens.length != 7) {
                    errors.append("Invalid user (expected 7 fields): ").append(l).append("\n");
                    continue;
                }

                try {
                    int userID = Integer.parseInt(tokens[0].trim());
                    String username = tokens[1].trim();
                    String name = tokens[2].trim();
                    int houseNumber = Integer.parseInt(tokens[3].trim());
                    String postcode = tokens[4].trim();
                    String city = tokens[5].trim();
                    String role = tokens[6].trim().toLowerCase();

                    Address address = new Address(houseNumber, postcode, city);

                    // User initialised based on if admin or customer
                    if (role.equals("admin")) {
                        users.add(new Admin(userID, username, name, address, UserCategory.ADMIN));
                    } else if (role.equals("customer")) {
                        users.add(new Customer(userID, username, name, address, UserCategory.CUSTOMER));
                    } else {
                        errors.append("Unknown role for user ").append(username).append(": ").append(role).append("\n");
                    }
                } catch (Exception ex) {
                    errors.append("Error parsing user line: ").append(l).append(" - ").append(ex.getMessage()).append("\n");
                }
            }
            s.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "User accounts file not found: UserAccounts.txt \n" + e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }

        if (errors.length() > 0) {
            JOptionPane.showMessageDialog(null, errors.toString(), "User Loading Errors", JOptionPane.ERROR_MESSAGE);
        }
        return users;
    }

    // Load all events from "Stock.txt".
    public static List<LiveEvent> loadAllEvents() {
        List<LiveEvent> events = new ArrayList<>();
        StringBuilder errors = new StringBuilder();
        try {
            File f = new File("Stock.txt");
            Scanner s = new Scanner(f);

            while (s.hasNextLine()) {
                String l = s.nextLine().trim();
                if (l.isEmpty()) {
                    continue;
                }
                String[] tokens = l.split(",");
                if (tokens.length != 9) { // Expect exactly 9 fields per event
                    errors.append("Invalid event line (expected 9 fields): ").append(l).append("\n");
                    continue;
                }

                try {
                    int eventID = Integer.parseInt(tokens[0].trim());
                    String eventCategoryRaw = tokens[1].trim();
                    String eventTypeRaw = tokens[2].trim();
                    String eventName = tokens[3].trim();
                    String ageRestrictionRaw = tokens[4].trim();
                    int quantity = Integer.parseInt(tokens[5].trim());
                    double performanceFee = Double.parseDouble(tokens[6].trim());
                    double ticketPrice = Double.parseDouble(tokens[7].trim());
                    String additionalInfo = tokens[8].trim();

                    // Get Age restriction category
                    AgeRestrictionCategory restriction = AgeRestrictionCategory.valueOf(ageRestrictionRaw.toUpperCase());
                    //Logic if the event category is Music
                    if (eventCategoryRaw.equalsIgnoreCase("MUSIC")) {
                        MusicType eventType;
                        if (eventTypeRaw.equalsIgnoreCase("Live Concert"))
                            eventType = MusicType.LIVE;
                        else if (eventTypeRaw.equalsIgnoreCase("DJ Set"))
                            eventType = MusicType.DJ;
                        else
                            throw new Exception("Invalid event Type- Music!");
                        
                        int performanceCount = Integer.parseInt(additionalInfo);
                        events.add(new MusicEvent(eventID, eventName, restriction, quantity, performanceFee,
                                ticketPrice, LiveEventCategory.MUSIC, eventType, performanceCount));
                    }
                    //Logic if event category is Performance
                    else if (eventCategoryRaw.equalsIgnoreCase("PERFORMANCE")) {
                        PerformanceType eventType;
                        if (eventTypeRaw.equalsIgnoreCase("Stand-up Comedy"))
                            eventType = PerformanceType.COMEDY;
                        else if (eventTypeRaw.equalsIgnoreCase("Theatre"))
                            eventType = PerformanceType.THEATRE;
                        else if (eventTypeRaw.equalsIgnoreCase("Magic"))
                            eventType = PerformanceType.MAGIC;
                        else
                            throw new Exception("Invalid event Type- Performance!");
                        
                        events.add(new PerformanceEvent(eventID, eventName, restriction, quantity, performanceFee,
                                ticketPrice, LiveEventCategory.PERFORMANCE, eventType, additionalInfo));
                    }
                    //Invalid data in file
                    else {
                        errors.append("Unknown event category for event ID ").append(eventID).append(": ").append(eventCategoryRaw).append("\n");
                    }

                } catch (Exception ex) {
                    errors.append("Error parsing event line: ").append(l).append(" - ").append(ex.getMessage()).append("\n");
                }
            }
            s.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Stock file not found: Stock.txt \n" + e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }

        if (errors.length() > 0) {
            JOptionPane.showMessageDialog(null, errors.toString(), "User Loading Errors", JOptionPane.ERROR_MESSAGE);
        }
        return events;
    }

    // Save all events to "Stock.txt".
    public static void saveAllEvents(List<LiveEvent> events) {
        try {
            PrintWriter pw = new PrintWriter(new File("Stock.txt"));
            for (LiveEvent event : events) {
                pw.println(createEventLine(event));
            }
            pw.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error saving events: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Convert a LiveEvent object into a savable format
    private static String createEventLine(LiveEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append(event.getEventID()).append(",");
        // Different formatting based on event category
        if (event.getEventCategory() == LiveEventCategory.MUSIC) {
            MusicEvent musicEvent = (MusicEvent) event; // Typecast to access child specific method
            sb.append("MUSIC").append(",");
            if (musicEvent.getMusicType() == MusicType.LIVE)
                sb.append("Live Concert").append(",");
            else if (musicEvent.getMusicType() == MusicType.DJ)
                sb.append("DJ Set").append(",");
            else
                sb.append("UNKNOWN").append(",");
            sb.append(musicEvent.getEventName()).append(",");
            sb.append(event.getAgeRestriction().name()).append(",");
            sb.append(event.getQuantityInStock()).append(",");
            sb.append(event.getPerformanceFee()).append(",");
            sb.append(event.getTicketPrice()).append(",");
            sb.append(musicEvent.getPerformerCount());
        } else if (event.getEventCategory() == LiveEventCategory.PERFORMANCE) {
            PerformanceEvent performanceEvent = (PerformanceEvent) event; // Typecast to access child specific method
            sb.append("PERFORMANCE").append(",");
            if (performanceEvent.getPerformanceType() == PerformanceType.COMEDY)
                sb.append("Stand-up Comedy").append(",");
            else if (performanceEvent.getPerformanceType() == PerformanceType.THEATRE)
                sb.append("Theatre").append(",");
            else if (performanceEvent.getPerformanceType() == PerformanceType.MAGIC)
                sb.append("Magic").append(",");
            else
                sb.append("UNKNOWN").append(","); //validation is performed in main class.
            
            sb.append(performanceEvent.getEventName()).append(",");
            sb.append(event.getAgeRestriction().name()).append(",");
            sb.append(event.getQuantityInStock()).append(",");
            sb.append(event.getPerformanceFee()).append(",");
            sb.append(event.getTicketPrice()).append(",");
            sb.append(performanceEvent.getLanguage());
        }
        return sb.toString();
    }

    // Add a new event to hard drive persistence.
    public static void addNewEvent(LiveEvent newEvent) {
        List<LiveEvent> events = loadAllEvents();
        events.add(newEvent);
        saveAllEvents(events);
    }

    // Modify an existing event to hard drive persistence
    // Note: This method does not preserve the original file order.
    public static void modifyEvent(LiveEvent event) {
        List<LiveEvent> events = loadAllEvents();
        Iterator<LiveEvent> iteration = events.iterator();
        while (iteration.hasNext()) {
            LiveEvent current = iteration.next();
            if (current.getEventID() == event.getEventID()) {
                iteration.remove(); // Remove the old event so it can be replaced
                break;
            }
        }
        events.add(event); // Append the modified event at the end
        saveAllEvents(events);
    }
}
