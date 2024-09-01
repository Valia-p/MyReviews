package api;

import java.io.Serializable;

/**
 * This class represents a customer which is a specific type of user.
 * A customer can search for accommodations as well as review them.
 */
public class Customer extends User implements Serializable {
    /**
     * Initializing the characteristics of the customer.
     * @param name
     * @param surname
     * @param username
     * @param password
     */
    public Customer(String name, String surname, String username, String password) {
        super(name, surname, username, password);
        setUserType("Customer");
    }
}
