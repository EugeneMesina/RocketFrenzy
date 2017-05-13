package androidsupersquad.rocketfrenzy.Data;

import java.util.Random;

import androidsupersquad.rocketfrenzy.Fragments.Models.Rocket;
import androidsupersquad.rocketfrenzy.R;

/**
 * Different types of rockets
 * <p>
 * Created by: Jimmy Chao (Lazer)
 */

public class RocketData {
    /**
     * Static Premade Rockets
     **/
    public static Rocket ApolloRocket = new Rocket("Apollo", R.drawable.tiny_rocket, "Heck");
    public static Rocket HorizonRocket = new Rocket("Horizon", R.drawable.tiny_rocket_2, "Heck");
    public static Rocket KamakaziRocket = new Rocket("Something", R.drawable.tiny_rocket_3, "Heck");

    /**
     * Reward System
     *
     * @return random rocket
     */
    public static Rocket giveRocket() {
        Random giveRocket = new Random();
        int number = giveRocket.nextInt(3);
        switch (number) {
            case 1:
                return ApolloRocket;
            case 2:
                return HorizonRocket;
            case 3:
                return KamakaziRocket;
            default:
                return ApolloRocket;
        }
    }
}
