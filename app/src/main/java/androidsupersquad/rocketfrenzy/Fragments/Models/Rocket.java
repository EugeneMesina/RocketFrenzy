package androidsupersquad.rocketfrenzy.Fragments.Models;

import java.io.Serializable;

/**
 * Created by Jimmy on 5/6/2017.
 */

public class Rocket implements Serializable {

    private String Name;
    private int Image;
    private String Description;
    public Rocket(String Name, int Image, String Description)
    {
        this.Name=Name;
        this.Image=Image;
        this.Description=Description;
    }
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
