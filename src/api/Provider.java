package api;

import java.io.Serializable;

/**
 * This class represents a provider which is a specific type of user.
 * A provider owns accommodations in the app.
 */
public class Provider extends User implements Serializable {
    /**
     * Initializing provider's characteristics.
     * @param name
     * @param surname
     * @param username
     * @param password
     */
    public Provider(String name, String surname, String username, String password) {
        super(name, surname, username, password);
        setUserType("Provider");
    }

}
