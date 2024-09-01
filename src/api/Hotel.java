package api;

import java.io.Serializable;

/**
 * This class represents a hotel which is a type of accommodation.
 * It inherits all the properties of an Accommodation,but it has some additional features.
 */
public class Hotel extends Accommodation implements Serializable {
    private int rooms;
    private int suites;
    private boolean breakfast;
    private boolean pool;

    /**
     * This constructor initialises all the characteristics of a hotel.
     * @param rooms Number of rooms the hotel has
     * @param suites Number of suites the hotel has
     * @param breakfast If the hotel offers breakfast or not
     * @param pool If the hotel has a public pool or not
     */
    public Hotel(String name, String description,Location loc, Luxuries lux, Provider provider,int stars,int num,int rooms,int suites,boolean breakfast,boolean pool){
        super(name,description,loc,lux, provider,stars,num);
        this.breakfast=breakfast;
        this.pool=pool;
        this.rooms=rooms;
        this.suites=suites;
        setType("Hotel");
    }

    public void setRooms(int rooms){this.rooms=rooms;}
    public void setSuites(int suites){this.suites=suites;}
    public void setBreakfast(boolean breakfast){this.breakfast=breakfast;}
    public void setPool(boolean pool){this.pool=pool;}

    public int getRooms(){return rooms;}
    public int getSuites(){return suites;}
    public boolean getBreakfast(){return breakfast;}
    public boolean getPool(){return pool;}
}
