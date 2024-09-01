package api;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Class for users initialization from csv file.
 * It contains a Hashset with all users of the app that have been created in previous executions of the app.
 */
public class UsersInitialization {
    private HashSet<User> allUsers;
    public static final String delimiter = ",";
    File csvFile = new File("Users.csv");

    /**
     * Constructor that checks if the csv file,where all the users are saved, is empty.
     * If the file is not empty then it loads all saved users to the Hashset.
     */
    public UsersInitialization(){
        allUsers=new HashSet<>();
        String line;
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null)
            {
                User u=new User();
                String[] str = line.split(delimiter);
                u.setName(str[0]);
                u.setSurname(str[1]);
                u.setUsername(str[2]);
                u.setPassword(str[3]);
                u.setUserType(str[4]);
                allUsers.add(u);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This method is called when a new user wants to sign up to the app.
     * First it checks if the username and password given are unique.If they are then the user is approved.
     * @param u It contains user's characteristics.
     * @return @param u if characteristics are approved otherwise,return null.
     * @throws IOException
     */
    public User signUp(User u) throws IOException {
        Authenticator auth = new Authenticator(u);
        Iterator<User> it = allUsers.iterator();
        boolean validU = true;
        boolean validP = true;
        while (it.hasNext() && validU && validP) {
            User ur = it.next();
            if (!auth.checkUsername(ur.getUsername())) {
                validU = false;
            }
            if (!auth.checkPassword(ur.getPassword())) {
                validP = false;
            }
        }
        if (validU && validP) {
            return u;
        }
        return null;
    }

    /**
     * This method adds to the csv file, where all users are stored , the new approved user who signed up to the app.
     * @param u It contains the new user's characteristics
     * @throws IOException
     */
    public void addToCsv(User u) throws IOException {
        allUsers.add(u);
        String[] data = new String[5];
        data[0] = u.getName();
        data[1] = u.getSurname();
        data[2] = u.getUsername();
        data[3] = u.getPassword();
        data[4] = u.getUserType();

        FileWriter fileWriter = new FileWriter(csvFile, true);
        StringBuilder line = new StringBuilder();
        line.append("\n");
        for (int i = 0; i < data.length; i++) {
            line.append(data[i]);
            if (i != data.length - 1) {
                line.append(',');
            }
        }

        fileWriter.write(line.toString());
        fileWriter.close();
    }

    /**
     * This method is called when a user wants to sign in to the app.
     * First it checks if the user has signed up by checking if the given username and password correspond to a user in the Hashset.
     * @param username Username given by the user.
     * @param password Password given by the user.
     * @return User characteristics if the user is signed up otherwise, return null.
     */
    public User signIn(String username,String password) {
        Authenticator auth = new Authenticator(username, password);
        Iterator<User> it = allUsers.iterator();
        User ur;
        while (it.hasNext()) {
            ur = it.next();
            if (auth.SearchUser(ur.getUsername(), ur.getPassword())) {
                return ur;
            }
        }
        return null;
    }
}




