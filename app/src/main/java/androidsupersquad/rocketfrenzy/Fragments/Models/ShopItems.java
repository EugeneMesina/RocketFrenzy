package androidsupersquad.rocketfrenzy.Fragments.Models;

import java.io.Serializable;

/**
 * These are items in the shop
 * <p>
 * Created by: Jimmy Chao (Lazer)
 */
public class ShopItems implements Serializable {
    /** The image file name */
    private int ItemImage;
    /** The shop item name */
    private String ItemName;
    /** The shop item Description */
    private String ItemDescription;
    /** The shop item Cost */
    private int ItemCost;
    /** The serialized version*/
    private static final long serialVersionUID = 1L;

    public ShopItems(String Name) {
        this.ItemName = Name;
    }

    /**
     * Constructs the Shop Item
     *
     * @param Name The shop item name
     * @param image The shop item image
     * @param Description The description of the shop item
     */

    public ShopItems(String Name, int image, String Description, int ItemCost) {
        this.ItemImage = image;
        this.ItemName = Name;
        this.ItemDescription = Description;
        this.ItemCost = ItemCost;
    }

    /**
     * Gets the item name
     * @return the item name
     */
    public String getItemName() {
        return ItemName;
    }

    /**
     * Gets the item image
     *
     * @return the item image
     */
    public int getItemImage() {
        return ItemImage;
    }

    /**
     * Gets the item description
     *
     * @return the item desc
     */
    public String getItemDescription() {
        return ItemDescription;
    }

    /**
     * Gets the item cost
     *
     * @return the item cost
     */
    public int getItemCost() {
        return ItemCost;
    }

    /**
     * Returns name and item cost of a shop item
     *
     * @return a string representation of a shop item
     */
    @Override
    public String toString() {
        return "Name: " + ItemName + " Cost: " + ItemCost;
    }

    /**
     * Compares two shop items together to see if they're the same
     *
     * @param obj The shop item being compared to
     * @return true or false depending on whether the shop items are the same
     */
    @Override
    public boolean equals(Object obj) {
        ShopItems current = (ShopItems) obj;
        if (current.ItemName.equals(this.ItemName)) {
            return true;
        }
        return false;
    }
}



