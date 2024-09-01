package api;

import java.io.*;
import java.util.ArrayList;

/**
 * Class for reviews initialization from txt file.
 * It contains an ArrayList with all reviews made by the customers in the app.
 */
public class ReviewsInitialization {
    private ArrayList<Review> allReviews;
    File file = new File("Reviews.txt");

    /**
     * Constructor that checks if the file,where all the reviews are saved, is empty.
     * If the file is not empty, it loads all the data from previous program executions.
     */
    public ReviewsInitialization() {
        allReviews = new ArrayList<>();
        if (!isEmptyFile()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                allReviews = (ArrayList<Review>) ois.readObject();
                ois.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method checks if the file with all reviews is empty.
     * @return True if it's empty else false.
     */
    private boolean isEmptyFile() {
        boolean isEmpty = false;
        try {
            FileInputStream fis = new FileInputStream(file);
            if (fis.read() == -1) {
                isEmpty = true;
            }
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isEmpty;
    }

    /**
     * This method  updates the file where all reviews are saved when a customer deletes or process a review.
     */
    private void updateReview() {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(allReviews);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called when a customer wants to add a new review for an accommodation.
     * @param review The new review.
     */
    public void addNewReview(Review review) {
        allReviews.add(review);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(allReviews);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method deletes a review.
     * @param review Selected review that customer wants to delete.
     */
    public void deleteReview(Review review){
        allReviews.remove(review);
        updateReview();
    }

    /**
     * This method is called when a customer wants to process a selected review.
     * @param review Initial review that the customer had made.
     * @param newR The modified review.
     */
    public void processReview(Review review,Review newR){
        for(Review r : allReviews){
            if(r.getCustomer().getUsername().equals(review.getCustomer().getUsername()) && r.getAccommodation().getName().equals(review.getAccommodation().getName())){
                r.setText(newR.getText());
                r.setStars(newR.getStars());
                r.setStamp(newR.getStamp());
                updateReview();
                break;
            }
        }
    }

    public ArrayList<Review> getAllReviews(){
        return allReviews;
    }

    /**
     * Find all reviews of specific customer.
     * @param customer Customer who is now using the app.
     * @return A list with all the reviews of the customer.
     */
    public String[][] getCustomerReviews(Customer customer){
        ArrayList<Review> cr = new ArrayList<>();
        for(Review r: allReviews){
            if(r.getCustomer().getUsername().equals(customer.getUsername())){
                cr.add(r);
            }
        }
        int count = 0;
        String[][] data = new String[cr.size()][5];
        for(Review r : cr){
            data[count][0] = r.getAccommodation().getName();
            data[count][1] = r.getAccommodation().getType();
            data[count][2] = r.getAccommodation().getLocation().getCity();
            data[count][3] = String.valueOf(r.getStars());
            data[count][4] = String.valueOf(r.getStamp());
            count++;
        }
        return data;
    }

    /**
     * Find the reviews of specific accommodation.
     * @param accommodation Selected accommodation for view.
     * @return A list with all reviews of accommodation.
     */
    public String[][] getAccommodationReviews(Accommodation accommodation){
        ArrayList<Review> ar = new ArrayList<>();
        for(Review r : allReviews)
            if(r.getAccommodation().getName().equals(accommodation.getName()))
                ar.add(r);
        String[][] data = new String[ar.size()][4];
        int count = 0;
        for(Review r : ar){
            data[count][0] = r.getCustomer().getUsername();
            data[count][1] = r.getText();
            data[count][2] = String.valueOf(r.getStars());
            data[count][3] = String.valueOf(r.getStamp());
            count++;
        }
        return data;
    }

    /**
     * Deletes all the reviews of the deleted accommodation.
     * @param acc Deleted accommodation
     */
    public void DeleteAllReviews(Accommodation acc){
        for(Review r : allReviews){
            if(r.getAccommodation().getName().equals(acc.getName())){
                allReviews.remove(r);
                updateReview();
                break;
            }
        }
    }

    /**
     * This method is called after existing accommodation has been modified from its provider.
     * @param previous The characteristics of previous accommodation.
     * @param newA The characteristics of modified accommodation.
     */
    public void UpdateReviewsAfterAccommodationProcess(Accommodation previous,Accommodation newA){
        for(Review r : allReviews){
            if(r.getAccommodation().getName().equals(previous.getName())){
                r.getAccommodation().setName(newA.getName());
                r.getAccommodation().setType(newA.getType());
                r.getAccommodation().setDescription(newA.getDescription());
                r.getAccommodation().setLocation(newA.getLocation());
                r.getAccommodation().setLuxuries(newA.getLuxuries());
            }
        }
        updateReview();
    }

    /**
     * Find specific review based on given username and accommodation name.
     * @param username customer's username
     * @param accName accommodation's name
     * @return The proper review if it exists else return null.
     */
    public Review findReview(String username , String accName){
        for(Review r : allReviews)
            if(r.getCustomer().getUsername().equals(username) && r.getAccommodation().getName().equals(accName))
                return r;
        return null;
    }

    /**
     * This method approves a review if the customer does not already have made one in this accommodation.
     * @param c Customer who wants to make a review.
     * @param a Selected accommodation to review.
     * @return True if the customer has not already made a review for this accommodation , otherwise return false.
     */
    public boolean ApproveReview(Customer  c, Accommodation a){
        for(Review r : allReviews)
            if(r.getCustomer().getUsername().equals(c.getUsername()) && r.getAccommodation().getName().equals(a.getName()))
                return false;
        return true;
    }

    /**
     *This method calculates the average score of reviews that a customer has made.
     * @param customer Signed in customer
     * @return "0" if the customer has made 0 reviews , otherwise return calculate the average(total stars / total reviews) as a string.
     */
    public String calculateMO(Customer customer){
        int  count = 0;
        int sum = 0;
        for(Review r : getCustomerReviewsMo(customer)){
            count++;
            sum+=r.getStars();
        }
        if(sum!=0){
            return String.valueOf(sum/count);
        }
        return "0";
    }

    /**
     * This method is called in order to get all reviews of a customer to calculate the average stars grade.
     * @param customer Signed in customer.
     * @return A list with all the reviews a customer has made.
     */
    private ArrayList<Review> getCustomerReviewsMo(Customer customer){
        ArrayList<Review> cr = new ArrayList<>();
        for(Review r: allReviews){
            if(r.getCustomer().getUsername().equals(customer.getUsername())){
                cr.add(r);
            }
        }
        return cr;
    }

}