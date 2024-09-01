package api;

/**
 * This class represents an authentication system for users that want to sign up or sign in to the app.
 */
public class Authenticator {
    String username;
    String password;

    public Authenticator() {}

    /**
     * This constructor gets the information of the user that wants to sign up.
     * @param u User that wants to sing up.
     */
    public Authenticator(User u){
        username = u.getUsername();
        password = u.getPassword();
    }

    /**
     * This constructor gets the information of the user that wants to sign in.
     * @param username
     * @param password
     */
    public Authenticator(String username,String password){
        this.username=username;
        this.password=password;
    }

    /**
     * Check if the user's username is already in use.
     * @param newuser username of the new user
     * @return True or False to allow sign up.
     */
    public boolean checkUsername(String newuser){
        if (newuser.equals(username)) {
            System.out.println("Username " + username + " is already in use.");
            return false;
        }
        return true;
    }

    /**
     * Check if the user's password is already in use.
     * @param pass password of the new user
     * @return True/False to allow sign up.
     */
    public boolean checkPassword(String pass){
        if(pass.equals(password)){
            System.out.println("This password is already in use.");
            return false;
        }
        return true;
    }

    /**
     * Search if the user who wants to sign in has already signed up.
     * @param username
     * @param password
     * @return True/False to indicate if the user is signed up.
     */
    public boolean SearchUser(String username,String password){
        if(username.equals(this.username) && password.equals(this.password)){
            return true;
        }
        return false;
    }
}