package androidsupersquad.rocketfrenzy.Fragments.Models;

import java.io.Serializable;

/**
 * Created by Jimmy Chao(Lazer) on 5/6/2017.
 * Rocket Model Class to hold data about rockets
 */

public class Rocket implements Serializable {
    //Rocket Name
    private String Name;
    //Rocket drawable ID from res file
    private int Image;
    //Rocket Description
    private String Description;
    //Constructor
    public Rocket(String Name, int Image, String Description)
    {
        this.Name=Name;
        this.Image=Image;
        this.Description=Description;
    }
    /**Getters**/
    public String getName() {
        return Name;
    }
    public int getImage() {
        return Image;
    }
    public String getDescription() {
        return Description;
    }
    public String toString()
    {
        return "Name: " + Name;
    }
}
