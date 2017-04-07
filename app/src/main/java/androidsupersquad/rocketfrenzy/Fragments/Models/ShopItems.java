package androidsupersquad.rocketfrenzy.Fragments.Models;

import java.io.Serializable;

/**
 * Created by Lazer on 4/7/2017.
 */

public class ShopItems implements Serializable
{
        //The image file name
        private String ItemImage;
        private String ItemName;
        private String ItemDescription;
        private float ItemCost;
        /**
         * Constructs the spirit
         * @param Name of Spirit
         * @param image of Spirit
         * @param Description of Spirit
         */
    public ShopItems(String Name, String image,String Description, float ItemCost){
        this.ItemImage=image;
        this.ItemName=Name;
        this.ItemDescription=Description;
        this.ItemCost=ItemCost;
    }


}



