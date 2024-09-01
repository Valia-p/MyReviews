package api;

import java.io.Serializable;

/**
 * This class represents an accommodation with its characteristics like name,type,description,location,luxuries,stars,number of reviews and provider.
 */
public class Accommodation implements Serializable {
    private String name, description,type;
    private Location loc;
    private Luxuries lux;
    private Provider provider;
    private int stars;
    private int ReviewsNum;

    /**
     * Empty constructor
     */
    public Accommodation(){}

    /**
     * Creates accommodation with specific characteristics.
     * @param name Accommodation's name
     * @param description Accommodation's description
     * @param loc Accommodation's location
     * @param lux Accommodation's luxuries
     * @param provider Accommodation's provider
     * @param stars Accommodation's star score
     * @param num Accommodation's reviews number
     */
    public Accommodation (String name,String description,Location loc, Luxuries lux, Provider provider,int stars,int num) {
        this.name=name;
        this.loc=loc;
        this.description=description;
        this.lux=lux;
        this.provider=provider;
        this.stars=stars;
        this.ReviewsNum=num;
    }

    public void setName(String name) {this.name=name;}

    public void setType(String type) {this.type=type;}

    public void setLocation (Location loc) {this.loc=loc;}

    public void setDescription (String description) {this.description=description;}

    public void setLuxuries (Luxuries lux) {this.lux=lux;}

    public void setUser(Provider provider) {this.provider=provider;}

    public void setStars(int stars) {this.stars=stars;}

    public void setReviewsNum(int num) {this.ReviewsNum=num;}

    public String getName () {return name;}

    public String getType () {return type;}

    public Location getLocation() {return loc;}

    public String getDescription () {return description;}

    public Luxuries getLuxuries() {return lux;}

    public Provider getUser(){ return provider;}

    public int getStars(){return stars;}

    public int getReviewsNum(){return ReviewsNum;}
}


