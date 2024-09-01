package api;

import java.io.Serializable;
import java.util.Date;

public class Review implements Serializable {
    private Customer customer;
    private Accommodation accommodation;
    private String text;
    private int stars;
    private Date date;

    /**
     * Constructor which creates a customer's review for accommodation.
     * @param customer Customer's characteristics
     * @param acc Chosen accommodation for review
     * @param text Review text
     * @param stars Given score(1-5)
     * @param stamp The date-time of given the review
     */
    public Review(Customer customer,Accommodation acc,String text,int stars,Date stamp){
        this.customer=customer;
        this.accommodation=acc;
        this.text=text;
        this.stars=stars;
        this.date=stamp;
    }

    public void setCustomer(Customer customer){this.customer=customer;}
    public void setAccommodation(Accommodation accommodation){this.accommodation=accommodation;}
    public void setText(String text){this.text=text;}
    public void setStars(int stars){this.stars=stars;}
    public void setStamp(Date stamp){this.date=stamp;}

    public Customer getCustomer(){return this.customer;}
    public Accommodation getAccommodation(){return this.accommodation;}
    public String getText(){return this.text;}
    public int getStars(){return this.stars;}
    public Date getStamp(){return date;}
}
