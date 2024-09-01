package api;

import java.io.Serializable;

/**
 * This class represents a villa which is a type of an accommodation.
 * It inherits all the properties of an Accommodation,but it has some added features.
 */
public class Villa extends Accommodation implements Serializable {
    private boolean cinemaRoom;
    private boolean gym;
    private boolean privatePool;

    /**
     * This constructor initialises all the characteristics of a villa.
     * @param cinemaRoom If the villa has a cinemaRoom or not
     * @param gym If the villa has a gym
     * @param privatePool If the villa has a private pool or not
     */
    public Villa(String name, String description,Location loc, Luxuries lux, Provider provider,int stars,int num,boolean cinemaRoom,boolean gym,boolean privatePool ) {
        super(name,description, loc, lux, provider, stars, num);
        this.cinemaRoom=cinemaRoom;
        this.gym=gym;
        this.privatePool=privatePool;
        setType("Villa");
    }

    public void setCinemaRoom(boolean cinemaRoom){this.cinemaRoom=cinemaRoom;}
    public void setGym(boolean gym){this.gym=gym;}
    public void setPrivatePool(boolean privatePool){this.privatePool=privatePool;}


    public boolean getCinemaRoom(){return cinemaRoom;}
    public boolean getGym(){return gym;}
    public boolean getPrivatePool(){return privatePool;}
}
