package androidsupersquad.rocketfrenzy.Fragments.Adapters;
import androidsupersquad.rocketfrenzy.DataBase.ByteArrayConverter;
import androidsupersquad.rocketfrenzy.DataBase.RocketContentProvider;
import androidsupersquad.rocketfrenzy.DataBase.RocketDB;
import androidsupersquad.rocketfrenzy.Fragments.Models.ShopItems;
import androidsupersquad.rocketfrenzy.R;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the Custom Expandable List Adapter
 * that will hold our custom information about a shop
 * item and inflate our custom layouts
 * Created by Lazer on 4/28/2017.
 */
public class ShopExpandableAdapter extends BaseExpandableListAdapter {
   //The current context the ExpandableListView is in
    private Context context;
    //The shop items to display
    private List<ShopItems> ItemList;

    /**
     * Constructs the values passed in from the Shop Fragment
     * @param context the current context the fragment is in
     * @param list the list of items
     */
    public ShopExpandableAdapter(Context context, List<ShopItems> list)
    {
        this.context=context;
        ItemList= list;
    }

    /**
     * Inflates the Description and the Buy Button of the item in the Expandable List View
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return the View
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        //Get the current shop item to display
        final ShopItems currentItem= (ShopItems) getChild(groupPosition,childPosition);
        //Inflate the current view with our custom view we made
        if(convertView==null){
            LayoutInflater inf= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.shop_child_row,null);
        }
        //set the text to the description of the item
        TextView description= (TextView) convertView.findViewById(R.id.Description);
        description.setText(currentItem.getItemDescription());
        //set the cost of the item onto the button
        Button cost = (Button) convertView.findViewById(R.id.BuyButton);
        cost.setText(((Integer)currentItem.getItemCost()).toString());
        cost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(currentItem.getItemCost()<=getPlayerCoinAmount(getPlayerName())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Want to buy this item: " + currentItem.getItemName())
                            .setTitle("Buy?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Integer coinAmount = getPlayerCoinAmount(getPlayerName())-currentItem.getItemCost();
                            addItemToPlayer(getPlayerName(),currentItem);
                            updatePlayerCoinAmount(getPlayerName(),coinAmount,true);
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else
                {
                    Toast.makeText(context,"NOT ENOUGH MINERALS",Toast.LENGTH_SHORT).show();
                }
            }
        });


        return convertView;
    }

    /**
     * Inflates the Name and the Picture of the item in the Expandable List View
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        //get the item
        ShopItems currentItem = (ShopItems) getGroup(groupPosition);
        //inflate our custom view
        if(convertView==null){
            LayoutInflater inf= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.shop_group_row,null);
        }
        //Set the Textview with the item name
        TextView ItemName = (TextView) convertView.findViewById(R.id.ItemName);
        ItemName.setText(currentItem.getItemName());
        //Set the Image with the drawable ID
        ImageView ItemImage = (ImageView) convertView.findViewById(R.id.ItemImage);
        ItemImage.setImageResource(currentItem.getItemImage());

        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ShopItems description= ItemList.get(groupPosition);
        return description;
    }
    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }
    @Override
    public int getGroupCount() {
        return ItemList.size();
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }
    @Override
    public Object getGroup(int groupPosition) {
        return ItemList.get(groupPosition);
    }
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    @Override
    public boolean hasStableIds() {
        return true;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    private String getPlayerName()
    {
        Cursor cursor = context.getContentResolver().query(RocketContentProvider.CONTENT_URI, null, null, null, null);
        int username = cursor.getColumnIndex(RocketDB.USER_NAME_COLUMN);
        cursor.moveToFirst();
        //still kind of testing//
        String name = cursor.getString(username);
        Log.d("PLAYER_NAME_INFO", "Username: " + name);
        return name;
    }
    private int getPlayerCoinAmount(String playerName)
    {
        String where = RocketDB.USER_NAME_COLUMN + "= ?";
        String whereArgs[] = {playerName};
        String[] resultColumns = {RocketDB.COIN_AMOUNT_COLUMN};
        Cursor cursor = context.getContentResolver().query(RocketContentProvider.CONTENT_URI, resultColumns, where, whereArgs, null);
        int coin = cursor.getColumnIndex(RocketDB.COIN_AMOUNT_COLUMN);
        cursor.moveToFirst();
        int coinAmount = cursor.getInt(coin);
        Log.d("COIN_INFO", "Username: " + playerName + "\nCoin amount: " + coinAmount);
        return coinAmount;
    }

    private int updatePlayerCoinAmount(String playerName, int coinAmount, boolean set)
    {
        String whereClause = RocketDB.USER_NAME_COLUMN + "= ?";
        String[] whereArgs = {playerName};
        int newCoinAmount = 0;
        ContentValues newValues = new ContentValues();
        if(set) {
            newCoinAmount = coinAmount;
        } else {
            int currentCoins = getPlayerCoinAmount(playerName);
            newCoinAmount = currentCoins + coinAmount;
        }
        newValues.put(RocketDB.COIN_AMOUNT_COLUMN, newCoinAmount);
        return context.getContentResolver().update(RocketContentProvider.CONTENT_URI, newValues, whereClause, whereArgs);
    }
    private int addItemToPlayer(String playerName, ShopItems item)
    {
        String whereClause = RocketDB.USER_NAME_COLUMN + "= ?";
        String[] whereArgs = {playerName};
        ArrayList<ShopItems> currentItems = getPlayerItems(playerName);
        if(currentItems == null)
        {
            currentItems = new ArrayList<ShopItems>();
        }
        currentItems.add(item);
        byte[]bytes = ByteArrayConverter.ObjectToByteArray(currentItems);
        ContentValues values = new ContentValues();
        values.put(RocketDB.ITEMS_OWNED_COLUMN, bytes);
        return context.getContentResolver().update(RocketContentProvider.CONTENT_URI, values, whereClause, whereArgs);
    }
    private ArrayList<ShopItems> getPlayerItems(String playerName)
    {
        String where = RocketDB.USER_NAME_COLUMN + "= ?";
        String whereArgs[] = {playerName};
        String[] resultColumns = {RocketDB.ITEMS_OWNED_COLUMN};
        Cursor cursor = context.getContentResolver().query(RocketContentProvider.CONTENT_URI, resultColumns, where, whereArgs, null);
        int items = cursor.getColumnIndex(RocketDB.ITEMS_OWNED_COLUMN);
        cursor.moveToFirst();
        try {
            ArrayList<ShopItems> itemArray = (ArrayList<ShopItems>) ByteArrayConverter.ByteArrayToObject(cursor.getBlob(items));
            String itemString = "\t";

            for (ShopItems si : itemArray) {
                itemString += (si + "\n\t");
            }
            Log.d("ITEM_INFO", itemString);
            return itemArray;
        } catch (Exception e)
        {
            Log.d("ITEM_INFO", "Username: " + playerName + "\nItem names: null");
            return null;
        }
    }
}
