package api;
import java.io.Serializable;

/**
 * This class represents a user with its characteristics like name,surname,username,password and user type.
 * The user type of the user can either be Customer or Provider.
 */
public class User implements Serializable {
    private String name;
    private String surname;
    private String username;
    private String password;
    private String userType;

    /**
     * Empty Constructor
     */
    public User(){}

    /**
     * Constructor
     * @param name Name of the user
     * @param surname Surname of the user
     * @param username Username of the user
     * @param password Password of the user
     */
    public User(String name,String surname , String username , String password){
        this.name=name;
        this.surname=surname;
        this.username=username;
        this.password=password;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name=name;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname=surname;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password=password;
    }

    /**
     * @return Returns the type of the user.
     */
    public String getUserType(){
        return userType;
    }

    /**
     * This method sets the type of user to Provider or Customer
     * @param userType The type can be one of the above.
     */
    public void setUserType(String userType){
        this.userType=userType;
    }

}


