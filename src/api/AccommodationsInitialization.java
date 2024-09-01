package api;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Class for accommodations initialization from txt file.
 * It contains an ArrayList with all the accommodations registered by all providers in the app.
 */
public class AccommodationsInitialization {
    private ArrayList<Accommodation> allAccommodations;
    File file  = new File("Accommodations.txt");

    /**
     * Constructor that checks if the file,where all the accommodations are saved, is empty.
     * If the file is not empty, it loads all the data from previous program executions.
     */
    public AccommodationsInitialization() {
        allAccommodations = new ArrayList<>();
        if (!FileIsEmpty()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                allAccommodations = (ArrayList<Accommodation>) ois.readObject();
                ois.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method checks if the file,where all the accommodations are saved,is empty.
     * @return True or False.
     */
    public boolean FileIsEmpty(){
        boolean empty = false;
        try {
            FileInputStream fis = new FileInputStream("Accommodations.txt");
            if(fis.read() == -1){
                empty = true;
            }
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return empty;
    }

    public ArrayList<Accommodation> getAllAccommodations() {
        return allAccommodations;
    }

    /**
     * This method searches the accommodations of a specific provider.
     * @param provider It contains the characteristics of the given provider.
     * @return A list with the provider's accommodations if these exist.Otherwise, it returns null.
     */
    public String[][] AccommodationList(Provider provider) {
        ArrayList<Accommodation> accommodationList = new ArrayList<>();
        for (Accommodation a : allAccommodations) {
            if (a.getUser().getUsername().equals(provider.getUsername())) {
                accommodationList.add(a);
            }
        }
        int count = 0;
        int rn = 0;
        int totalStars=0;
        String[][] data = new String[accommodationList.size()+1][9];
        for(Accommodation a : accommodationList){
            data[count][0] = a.getName();
            data[count][1] = a.getType();
            data[count][2] = a.getLocation().getAddress();
            data[count][3] = a.getLocation().getCity();
            data[count][4] = a.getLocation().getPostcode().toUpperCase(Locale.ROOT);
            if(a.getReviewsNum()!=0){
                data[count][5] = String.valueOf(a.getStars()/a.getReviewsNum());
            }
            else
                data[count][5] = "0";
            //data[count][5] = String.valueOf(a.getStars());
            data[count][6] = String.valueOf(a.getReviewsNum());

            rn+=a.getReviewsNum();
            totalStars+=a.getStars();
            count++;
        }

        data[accommodationList.size()][7]= String.valueOf(rn);
        if(rn!=0) {
            data[accommodationList.size()][8] = String.valueOf(totalStars / rn);
        }
        else
            data[accommodationList.size()][8] = "0";
        return data;
    }

    /**
     * This method adds new accommodation to the ArrayList and saves it to the file only if it does not already exist.
     * @param accommodation It contains the characteristics of given accommodation.
     * @throws IOException
     */
    public boolean addNewAccommodation(Accommodation accommodation) throws IOException {
        if(!AccommodationExists(accommodation)) {
            allAccommodations.add(accommodation);
            addToFile();
            return true;
        }
        return false;
    }

    /**
     * This method saves the new accommodation to the file.
     * @throws IOException
     */
    private void addToFile() {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(allAccommodations);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the given accommodation already exists in the ArrayList(Check if given name or address are already in use).
     * @return True or False.
     */
    private boolean AccommodationExists(Accommodation accommodation){
        for(Accommodation a : allAccommodations)
            if(a.getName().equals(accommodation.getName()) || a.getLocation().getAddress().equals(accommodation.getLocation().getAddress()))
                return true;
        return false;
    }

    /**
     * This method deletes the chosen accommodation from the Hashset and then updates the file.
     * @param accommodation Chosen accommodation for deletion.
     * @throws IOException
     * @return True if the deletion was successful , otherwise return false.
     */
    public boolean deleteAccommodation(Accommodation accommodation) throws IOException {
        allAccommodations.remove(accommodation);
        updateFile();
        return true;
    }

    /**
     * This method updates the file, where all the accommodations are saved , so the deleted accommodation no longer exists.
     * @throws IOException
     */
    private void updateFile() throws IOException {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(allAccommodations);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called when a provider makes changes to his accommodation's characteristics, so the changes can be saved to the Hashset and then to the file.
     * Firstly it searches the accommodation given and if it exists , it makes the changes needed.
     * @param previous Chosen accommodation for modification.
     * @param newA previous accommodation but with wanted changes.
     * @throws IOException
     * @return True if the changes were saved successfully, otherwise return false.
     */
    public boolean processAccommodation(Accommodation previous, Accommodation newA) throws IOException {
        for(Accommodation acc : allAccommodations){
            if(acc.getName().equals(previous.getName())){
                acc.setName(newA.getName());
                acc.setDescription(newA.getDescription());
                acc.setLocation(newA.getLocation());
                acc.setLuxuries(newA.getLuxuries());
                if(acc instanceof Apartment && newA instanceof Apartment){
                    ((Apartment) acc).setSquareSize(((Apartment) newA).getSquareSize());
                    ((Apartment) acc).setFloor(((Apartment) newA).getFloor());
                    ((Apartment) acc).setConstructionYear(((Apartment) newA).getConstructionYear());
                }
                else if(acc instanceof Hotel && newA instanceof Hotel){
                    ((Hotel) acc).setRooms(((Hotel) newA).getRooms());
                    ((Hotel) acc).setSuites(((Hotel) newA).getSuites());
                    ((Hotel) acc).setBreakfast(((Hotel) newA).getBreakfast());
                    ((Hotel) acc).setPool(((Hotel) newA).getPool());
                }
                else if(acc instanceof Villa && newA instanceof Villa){
                    ((Villa) acc).setCinemaRoom(((Villa) newA).getCinemaRoom());
                    ((Villa) acc).setGym(((Villa) newA).getGym());
                    ((Villa) acc).setPrivatePool(((Villa) newA).getPrivatePool());
                }
                updateFile();
                return true;
            }
        }
        return false;
    }

    /**
     * Show all accommodations without filters.
     * @return All accommodations in the app.
     */
    public String[][] view(){
        String[][] data = new String[allAccommodations.size()][9];
        int count = 0;
        for(Accommodation a : allAccommodations){
            data[count][0] = a.getName();
            data[count][1] = a.getType();
            data[count][2] = a.getLocation().getAddress();
            data[count][3] = a.getLocation().getCity();
            data[count][4] = a.getLocation().getPostcode().toUpperCase(Locale.ROOT);
            if(a.getReviewsNum()!=0){
                data[count][5] = String.valueOf(a.getStars()/a.getReviewsNum());
            }
            else
                data[count][5] = "0";
            data[count][6] = String.valueOf(a.getReviewsNum());
            count++;
        }
        return data;
    }

    /**
     * This method is called when a customer wants to view the selected accommodation , process or delete his review related to this accommodation.
     * Also the method is called when a provider wants to view , delete or process the selected accommodation.
     * @param name Name of the accommodation.
     * @return The relevant accommodation.
     */
    public Accommodation findAccommodation(String name){
        for(Accommodation a : allAccommodations)
            if(name.equals(a.getName()))
                return a;
        return null;
    }

    /**
     * This method is called when a customer searches accommodations with specific luxuries.
     * @param state An array which has true if a luxury is checked , otherwise it has false.
     * @return A list with accommodations that provide the selected luxuries.
     */
    public String[][] searchForLuxuries(Boolean[] state) {
        ArrayList<Accommodation> accommodationsWithLuxs = new ArrayList<>();
        SpecificLuxuries sl ;
        for (Accommodation a : allAccommodations){
            sl=new SpecificLuxuries(a.getLuxuries().getLuxs());
            if(sl.checkForLuxs(state))
                accommodationsWithLuxs.add(a);
        }
        String[][] data = new String[accommodationsWithLuxs.size()][7];
        int count=0;
        for(Accommodation a : accommodationsWithLuxs){
            data[count][0] = a.getName();
            data[count][1] = a.getType();
            data[count][2] = a.getLocation().getAddress();
            data[count][3] = a.getLocation().getCity();
            data[count][4] = a.getLocation().getPostcode().toUpperCase(Locale.ROOT);
            data[count][5] = String.valueOf(a.getStars());
            data[count][6] = String.valueOf(a.getReviewsNum());
            count++;
        }
        return data;
    }

    /**
     * This method is called when a provider changes the name of existing accommodation without changing its address.
     * @param name Modified name
     * @param address Old address
     * @return False if the modified name is already used , otherwise return true.
     */
    public boolean ApproveName(String name , String address){
        for(Accommodation a: allAccommodations)
            if(a.getName().equals(name) && !a.getLocation().getAddress().equals(address))
                return false;
        return true;
    }

    /**
     * This method is called when a provider changes the name and the address of existing accommodation.
     * @param name Modified name
     * @param address Modified address
     * @return False if name or address are already in use , otherwise return true.
     */
    public boolean ApproveNameLocation(String name , String address){
        for(Accommodation a : allAccommodations)
            if(a.getName().equals(name) || a.getLocation().getAddress().equals(address))
                return false;
        return true;
    }

    /**
     * This method is called when a provider changes the address of existing accommodation without changing its name.
     * @param name Old name
     * @param address Modified address
     * @return False if the modified address is already used , otherwise return true.
     */
    public boolean ApproveAddress(String name , String address){
        for(Accommodation a : allAccommodations)
            if(a.getLocation().getAddress().equals(address) && !a.getName().equals(name))
                return false;
        return true;
    }

    /**
     * This method is called when a customer adds a new review for accommodation.
     * Update total stars of the accommodation and increase its reviews number by one.
     * Then update the csv file where all the accommodations are stored.
     * @param acc Selected accommodation.
     * @param stars Given score
     */
    public void updateRating(Accommodation acc,int stars) {
        int total = stars + acc.getStars();
        acc.setStars(total);
        acc.setReviewsNum(acc.getReviewsNum() + 1);
        try {
            updateFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called when a customer deletes a review so the accommodation reviews number and stars must be updated.
     * @param stars The stars the review had.
     */
    public void UpdateAfterReviewDelete(Accommodation a,String stars){
        a.setReviewsNum(a.getReviewsNum() - 1);
        if(a.getStars()-Integer.parseInt(stars)<0)
            a.setStars(0);
        else
            a.setStars(a.getStars()-Integer.parseInt(stars));
        try {
            updateFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called when a customer processes a review so the accommodation stars must be updated.
     * @param stars The stars the review has.
     */
    public void UpdateAfterReviewProcess(Accommodation a,int stars , int prevstars){
        a.setStars(a.getStars() + stars - prevstars);
        try {
            updateFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
