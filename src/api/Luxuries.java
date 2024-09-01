package api;

import java.io.Serializable;

/**
 * This class represents the luxuries that accommodation offers.
 */
public class Luxuries implements Serializable {
    private static final long serialVersionUID = 7919764121313917550l;
    private String[] luxs = new String[9];

    /**
     * Constructor that sets all luxuries to null because they are not mandatory.
     */
    public Luxuries() {
        for (int i = 0; i < luxs.length; i++) {
            luxs[i] = "none";
        }
    }

    /**
     * Constructor that sets accommodation luxuries after the provider has added them.
     * @param l Luxuries that provider added.
     */
    public Luxuries(String[] l) {
        for (int i = 0; i < 9; i++) {
            luxs[i] = l[i];
        }
    }

    /**
     * This method is called when the accommodation luxuries must be viewed.
     *
     * @return Luxuries
     */
    public String[] getLuxs() {
        return luxs;
    }

    /**
     * This method is called when available luxuries of accommodation must be viewed.
     * @param accommodation Selected accommodation.
     * @return Only the available luxuries.
     */
    public String[] AvailableLuxuries(Accommodation accommodation) {
        String[] lux = new String[9];
        int i = 0;
        for (String s : accommodation.getLuxuries().getLuxs()) {
            if (!s.equals("none")) {
                lux[i] = s;
                i++;
            }
        }
        return lux;
    }

    /** This method is called when a provider adds luxuries for his accommodation.
     * @param i Used to specify the position of the luxury in the array(luxs).
     * @param value The description of the specific luxury.
     */
    public void setSpecificLux(int i, String value) {
        luxs[i] = value;
    }
}

