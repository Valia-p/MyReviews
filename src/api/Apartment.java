package api;

import java.io.Serializable;

/**
 * This class represents an apartment which is a type of accommodation.
 * It inherits all the properties of an Accommodation,but it has some additional features.
 */
public class Apartment extends Accommodation implements Serializable {
    private int squareSize;
    private int floor;
    private int constructionYear;

    /**
     * This constructor initialises all the characteristics of an apartment.
     * @param name The name of the accommodation
     * @param description Accommodation's description
     * @param loc Accommodation's location
     * @param lux Accommodation's luxuries
     * @param provider Accommodation's provider
     * @param stars Accommodation's total stars
     * @param num Accommodation's total number of customer reviews
     * @param squareSize Apartment's square size
     * @param floor In which floor the apartment is
     * @param constructionYear Apartment's constructor year
     */
    public Apartment(String name, String description,Location loc, Luxuries lux, Provider provider,int stars,int num,int squareSize,int floor,int constructionYear ) {
        super(name, description, loc, lux, provider, stars, num);
        this.constructionYear=constructionYear;
        this.floor=floor;
        this.squareSize=squareSize;
        setType("Apartment");
    }

    public void setSquareSize(int squareSize){this.squareSize=squareSize;}
    public void setFloor(int floor){this.floor=floor;}
    public void setConstructionYear(int constructionYear){this.constructionYear=constructionYear;}

    public int getSquareSize(){return squareSize;}
    public int getFloor(){return floor;}
    public int getConstructionYear(){return constructionYear;}
}
