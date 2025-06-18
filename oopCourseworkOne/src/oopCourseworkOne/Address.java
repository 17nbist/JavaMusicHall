package oopCourseworkOne;

// Class representing an address with house number, postcode, and city.
public class Address {

    // Instance variables for storing address details.
    private int houseNumber;
    private String postcode;
    private String city;

    // Constructor: Initisialise the Address object with a house number, postcode, and city.
    public Address(int houseNumber, String postcode, String city) {
        this.houseNumber = houseNumber;
        this.postcode = postcode;
        this.city = city;
    }

    // Overrides the toString() method to return a string formatted address
    @Override
    public String toString() {
        return houseNumber + ", " + postcode + ", " + city;
    }
}