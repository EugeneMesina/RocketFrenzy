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
    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getItemImage() {
        return ItemImage;
    }

    public void setItemImage(String itemImage) {
        ItemImage = itemImage;
    }

    public String getItemDescription() {
        return ItemDescription;
    }

    public void setItemDescription(String itemDescription) {
        ItemDescription = itemDescription;
    }

    public float getItemCost() {
        return ItemCost;
    }

    public void setItemCost(float itemCost) {
        ItemCost = itemCost;
    }
}



