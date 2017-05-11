package androidsupersquad.rocketfrenzy.Data;

import android.content.res.Resources;

import androidsupersquad.rocketfrenzy.Fragments.Models.ShopItems;
import androidsupersquad.rocketfrenzy.R;

/**
 * Created by Jimmy on 5/11/2017.
 */

public class ShopData {
        public final static ShopItems LaunchPad =new ShopItems("Launch Pad",R.drawable.launchpad, Resources.getSystem().getString(R.string.launchpad),50);
        public final static ShopItems LaunchPadBundle =new ShopItems("Launch Pad Bundle",R.drawable.launchpadbundle,Resources.getSystem().getString(R.string.launchpad_bundle),100);
        public final static ShopItems HorizonIcon =new ShopItems("Horizon Icon",R.drawable.horizonicon,Resources.getSystem().getString(R.string.Horizon_Icon),500);
        public final static ShopItems FireIcon =new ShopItems("Fire Icon",R.drawable.fireemblem,Resources.getSystem().getString(R.string.Flame_Icon),1000);
        public final static ShopItems SkullIcon =new ShopItems("Skull Icon",R.drawable.skullicon,Resources.getSystem().getString(R.string.Skull_Icon),250);
        public final static ShopItems Bleach =new ShopItems("Bleach",R.drawable.bleach,Resources.getSystem().getString(R.string.Bleach),1000);
}
