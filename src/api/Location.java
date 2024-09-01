package api;

import java.io.Serializable;

/**
 * This class represents a location of accommodation.
 */
public class Location implements Serializable {
    private static final long serialVersionUID = 6076497903262407518L;
    private String address, city;
    private String postcode;

    /**
     * This constructor sets the necessary information for accommodation's location.
     * @param address The address of the accommodation.
     * @param city The city that the accommodation is located.
     * @param postcode The postcode of the area the accommodation is located.
     */
    public Location (String address, String city, String postcode) {
        this.address=address;
        this.city=city;
        this.postcode=postcode;
    }

    public String getAddress() {return address;}

    public String getCity() {return city;}

    public String getPostcode() {return postcode;}
}