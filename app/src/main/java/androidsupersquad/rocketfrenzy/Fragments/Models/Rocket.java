package androidsupersquad.rocketfrenzy.Fragments.Models;

import java.io.Serializable;

/**
 * Rocket Model Class to hold data about rockets
 *
 * Created by: Jimmy Chao(Lazer)
 */

public class Rocket implements Serializable {
    /** Rocket name */
    private String Name;
    /** Rocket drawable ID from res file */
    private int Image;
    /** Rocket Description */
    private String Description;

    /**
     * Constructor
     *
     * @param Name name of the rocket
     * @param Image image associated with the rocket
     * @param Description description of the rocket
     */
    public Rocket(String Name, int Image, String Description) {
        this.Name=Name;
        this.Image=Image;
        this.Description=Description;
    }

    /**Getters**/
    /**
     * Gets the name of the rocket
     *
     * @return The name of the rocket
     */
    public String getName() {
        return Name;
    }

    /**
     * Gets the image associated with the rocket
     *
     * @return The rocket's image
     */
    public int getImage() {
        return Image;
    }

    /**
     * Gets the rocket's description
     * @return the rocket's description
     */
    public String getDescription() {
        return Description;
    }

    /**
     * Basically returns the name of the rocket
     *
     * @return The string representation of a rocket
     */
    public String toString()
    {
        return "Name: " + Name;
    }
}
