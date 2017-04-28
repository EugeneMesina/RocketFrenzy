package androidsupersquad.rocketfrenzy.Fragments.Models;
import java.io.Serializable;
/**
 * These are items in the shop
 * Created by Lazer on 4/7/2017.
 */
public class ShopItems implements Serializable
{
        //The image file name
        private int ItemImage;
        //The shop item name
        private String ItemName;
        //The shop item Description
        private String ItemDescription;
        //The shop item Cost
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
    public String getItemName(){return ItemName; }
    public int getItemImage() {
        return ItemImage;
    }
    public String getItemDescription() {
        return ItemDescription;
    }
    public float getItemCost() {
        return ItemCost;
    }
}



