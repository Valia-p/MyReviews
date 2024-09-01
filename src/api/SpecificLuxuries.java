package api;

public class SpecificLuxuries {
    String[] luxs;

    /**
     * Constructor which initializes the luxuries array with the luxuries the accommodation provides.
     * @param luxs String array with luxuries the accommodation provides.
     */
    public SpecificLuxuries(String[] luxs){
        this.luxs=luxs;
    }

    /**
     * This method is called when we need to find if an accommodation provides the luxuries selected from customer.
     * @param state An array which has true if a customer has selected the specific luxury , otherwise it has false in its position.
     * @return True if the accommodation provides all selected luxuries , otherwise return false.
     */
    public boolean checkForLuxs(Boolean[] state){
        for (int i = 0; i < 9; i++)
            if (state[i] &&  luxs[i].equals("none"))
                return false;
        return true;
    }
}
