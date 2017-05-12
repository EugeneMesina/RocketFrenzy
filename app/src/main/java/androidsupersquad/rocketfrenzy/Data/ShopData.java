package androidsupersquad.rocketfrenzy.Data;

import androidsupersquad.rocketfrenzy.Fragments.Models.ShopItems;
import androidsupersquad.rocketfrenzy.R;

/**
 * Created by Jimmy on 5/11/2017.
 */

public class ShopData {
        /**Pre-made Shop Items**/
        public static ShopItems LaunchPad =new ShopItems("Launch Pad",R.drawable.launchpad, "The launch pad allows you to launch rockets that you own. Works on any types of rockets.",50);
        public static ShopItems LaunchPadBundle =new ShopItems("Launch Pad Bundle",R.drawable.launchpadbundle,"A pack of 3 launch pads that will allows you to launch more rockets!",100);
        public static ShopItems HorizonIcon =new ShopItems("Horizon Icon",R.drawable.horizonicon,"A nice looking icon of the horizon. Change up your profile now! ",500);
        public static ShopItems FireIcon =new ShopItems("Fire Icon",R.drawable.fireemblem,"A nice looking icon of fire. Change up your profile now! ",1000);
        public static ShopItems SkullIcon =new ShopItems("Skull Icon",R.drawable.skullicon,"A nice looking icon of a skull. Change up your profile now! ",250);
        public static ShopItems Bleach =new ShopItems("Bleach",R.drawable.bleach,"A Killer Substance",1000);
}
