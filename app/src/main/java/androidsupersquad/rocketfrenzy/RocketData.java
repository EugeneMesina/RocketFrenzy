package androidsupersquad.rocketfrenzy;

import java.util.Random;

import androidsupersquad.rocketfrenzy.Fragments.Models.Rocket;

/**
 * Created by Jimmy on 5/11/2017.
 */

public class RocketData {
    public final static Rocket ApolloRocket = new Rocket("Apollo",R.drawable.apollorocket, "Heck");
    public final static Rocket HorizonRocket=new Rocket("Horizon",R.drawable.horizonrocket, "Heck");
    public final static Rocket KamakaziRocket=new Rocket("Something",R.drawable.kamakazirocket, "Heck");
    public static Rocket giveRocket()
    {
        Random giveRocket = new Random();
        int number= giveRocket.nextInt(3);
        switch(number)
        {
            case 1: return ApolloRocket;
            case 2: return HorizonRocket;
            case 3: return KamakaziRocket;
            default: return ApolloRocket;
        }
    }
}
