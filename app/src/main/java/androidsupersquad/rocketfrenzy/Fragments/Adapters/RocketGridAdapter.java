package androidsupersquad.rocketfrenzy.Fragments.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidsupersquad.rocketfrenzy.DataBase.ByteArrayConverter;
import androidsupersquad.rocketfrenzy.DataBase.RocketContentProvider;
import androidsupersquad.rocketfrenzy.DataBase.RocketDB;
import androidsupersquad.rocketfrenzy.Fragments.Models.Rocket;
import androidsupersquad.rocketfrenzy.Fragments.Models.ShopItems;
import androidsupersquad.rocketfrenzy.R;

/**
 * Created by Jimmy on 5/6/2017.
 */

public class RocketGridAdapter extends BaseAdapter{
    private Context mContext;
    private List rocket;

    public RocketGridAdapter(Context c, List rockets)
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
            final ArrayList<ShopItems> PlayerItems = getPlayerItems(getPlayerName());
            if(Rocket.class.isInstance(rocket.get(position)))
            {
                Rocket currentRocket = (Rocket) rocket.get(position);
                rocketImage.setImageResource(currentRocket.getImage());
                rocketImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShopItems launchPad =new ShopItems("Launch Pad",R.drawable.launchpad,mContext.getString(R.string.launchpad),50);
                        if(PlayerItems.contains(launchPad)) {
                            Toast.makeText(mContext, "Rocket Clicked", Toast.LENGTH_SHORT).show();
                            removeItemFromPlayer(getPlayerName(),launchPad);
                        }
                        else
                            {
                                Toast.makeText(mContext, "Need Launch Pads", Toast.LENGTH_SHORT).show();
                            }
                    }
                });
            }
            else
            {
                ShopItems currentRocket = (ShopItems) rocket.get(position);
                rocketImage.setImageResource(currentRocket.getItemImage());
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
}
