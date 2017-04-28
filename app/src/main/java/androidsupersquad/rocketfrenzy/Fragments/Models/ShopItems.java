package androidsupersquad.rocketfrenzy.Fragments.Models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Lazer on 4/7/2017.
 */

public class ShopItems implements Serializable
{
        //The image file name
        private int ItemImage;
        private String ItemName;
        private String ItemDescription;
        private float ItemCost;
        /**
         * Constructs the Shop Item
         * @param Name of Shop Item
         * @param image of Shop Item
         * @param Description of Shop Item
         */
    public ShopItems(String Name, int image,String Description, float ItemCost){
        this.ItemImage=image;
        this.ItemName=Name;
        this.ItemDescription=Description;
        this.ItemCost=ItemCost;
    }
    public String getItemName()
    {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public int getItemImage() {
        return ItemImage;
    }

    public void setItemImage(int itemImage) {
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



