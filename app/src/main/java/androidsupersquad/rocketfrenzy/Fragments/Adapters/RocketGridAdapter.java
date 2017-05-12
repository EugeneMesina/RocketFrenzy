package androidsupersquad.rocketfrenzy.Fragments.Adapters;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidsupersquad.rocketfrenzy.DataBase.ByteArrayConverter;
import androidsupersquad.rocketfrenzy.DataBase.RocketContentProvider;
import androidsupersquad.rocketfrenzy.DataBase.RocketDB;
import androidsupersquad.rocketfrenzy.Fragments.Models.Rocket;
import androidsupersquad.rocketfrenzy.Fragments.Models.ShopItems;
import androidsupersquad.rocketfrenzy.R;
import androidsupersquad.rocketfrenzy.RocketLaunch;

/**
 * Created by Jimmy on 5/6/2017.
 */

public class RocketGridAdapter extends BaseAdapter{
    private Activity mContext;
    private List rocket;

    public RocketGridAdapter(Activity c, List rockets)
    {
        mContext=c;
        rocket=rockets;
    }

    @Override
    public int getCount() {
        return rocket.size();
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null){
            grid= new View(mContext);
            grid=inflater.inflate((R.layout.rocket_grid_single),null);
            final ImageView rocketImage= (ImageView) grid.findViewById(R.id.rocket);
            final TextView itemName = (TextView) grid.findViewById(R.id.rocketName);
            final ArrayList<ShopItems> PlayerItems = getPlayerItems(getPlayerName());
            if(Rocket.class.isInstance(rocket.get(position)))
            {
                final Rocket currentRocket = (Rocket) rocket.get(position);
                rocketImage.setImageResource(currentRocket.getImage());
                itemName.setText(currentRocket.getName());
                rocketImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    ShopItems launchPad =new ShopItems("Launch Pad",R.drawable.launchpad,mContext.getString(R.string.launchpad),50);
                    if(PlayerItems.contains(launchPad)) {
                        Toast.makeText(mContext, "RocketLaunch Clicked", Toast.LENGTH_SHORT).show();
                        removeItemFromPlayer(getPlayerName(),launchPad);
                        removeRocketFromPlayer(getPlayerName(),currentRocket);
                        //Create a new intent to move to another class
                        Intent ShowName = new Intent(mContext, RocketLaunch.class);
                        //Send the name to the new activity
                        //ShowName.putExtra("uname",name.getText().toString());
                        //Start new activity
                        mContext.startActivity(ShowName);
                    }
                    else
                    {
                        Toast.makeText(mContext, "You don't have a launch pad, buy one from the shop!", Toast.LENGTH_SHORT).show();
                    }
                    }
                });
            }
            else
            {
                ShopItems currentRocket = (ShopItems) rocket.get(position);
                rocketImage.setImageResource(currentRocket.getItemImage());
                itemName.setText(currentRocket.getItemName());
            }


        }
        else
        {
            grid= convertView;
        }

        return grid;
    }
    private String getPlayerName()
    {
        Cursor cursor = mContext.getContentResolver().query(RocketContentProvider.CONTENT_URI, null, null, null, null);
        int username = cursor.getColumnIndex(RocketDB.USER_NAME_COLUMN);
        cursor.moveToFirst();
        //still kind of testing//
        String name = cursor.getString(username);
        Log.d("PLAYER_NAME_INFO", "Username: " + name);
        return name;
    }

    private int removeItemFromPlayer(String playerName, ShopItems item)
    {
        String whereClause = RocketDB.USER_NAME_COLUMN + "= ?";
        String[] whereArgs = {playerName};
        ArrayList<ShopItems> currentItems = getPlayerItems(playerName);
        if(currentItems == null)
        {
            currentItems = new ArrayList<ShopItems>();
        }
        for(int i = 0; i < currentItems.size(); i++)
        {
            if(currentItems.get(i).getItemName().equals(item.getItemName()))
            {
                currentItems.remove(i);
                i = currentItems.size();
                Log.d("REMOVAL", "SUCCESSFUL: " + item.getItemName() + " " + item.getItemCost());
            }
        }
        byte[]bytes = ByteArrayConverter.ObjectToByteArray(currentItems);
        ContentValues values = new ContentValues();
        values.put(RocketDB.ITEMS_OWNED_COLUMN, bytes);
        return mContext.getContentResolver().update(RocketContentProvider.CONTENT_URI, values, whereClause, whereArgs);
    }
    private ArrayList<ShopItems> getPlayerItems(String playerName)
    {
        String where = RocketDB.USER_NAME_COLUMN + "= ?";
        String whereArgs[] = {playerName};
        String[] resultColumns = {RocketDB.ITEMS_OWNED_COLUMN};
        Cursor cursor = mContext.getContentResolver().query(RocketContentProvider.CONTENT_URI, resultColumns, where, whereArgs, null);
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
    private ArrayList<Rocket> getPlayerRockets(String playerName)
    {
        String where = RocketDB.USER_NAME_COLUMN + "= ?";
        String whereArgs[] = {playerName};
        String[] resultColumns = {RocketDB.ROCKETS_OWNED_COLUMN};
        Cursor cursor = mContext.getContentResolver().query(RocketContentProvider.CONTENT_URI, resultColumns, where, whereArgs, null);
        int rockets = cursor.getColumnIndex(RocketDB.ROCKETS_OWNED_COLUMN);
        cursor.moveToFirst();
        try {
            ArrayList<Rocket> rocketArray = (ArrayList<Rocket>) ByteArrayConverter.ByteArrayToObject(cursor.getBlob(rockets));
            String rocketString = "\t";

            for (Rocket r : rocketArray) {
                rocketString += (r + "\n\t");
            }
            Log.d("ROCKET_INFO", rocketString);
            return rocketArray;
        } catch (Exception e)
        {
            Log.d("ROCKET_INFO", "Username: " + playerName + "\nRocketLaunch names: null");
            return null;
        }
    }
    private int removeRocketFromPlayer(String playerName, Rocket rocket)
    {
        String whereClause = RocketDB.USER_NAME_COLUMN + "= ?";
        String[] whereArgs = {playerName};
        ArrayList<Rocket> currentRockets = getPlayerRockets(playerName);
        if(currentRockets == null)
        {
            currentRockets = new ArrayList<Rocket>();
        }
        for(int i = 0; i < currentRockets.size(); i++)
        {
            if(currentRockets.get(i).getName().equals(rocket.getName()))
            {
                currentRockets.remove(i);
                i = currentRockets.size();
                Log.d("REMOVAL", "SUCCESSFUL: " + rocket.getName());
            }
        }
        byte[]bytes = ByteArrayConverter.ObjectToByteArray(currentRockets);
        ContentValues values = new ContentValues();
        values.put(RocketDB.ROCKETS_OWNED_COLUMN, bytes);
        return mContext.getContentResolver().update(RocketContentProvider.CONTENT_URI, values, whereClause, whereArgs);
    }


}
