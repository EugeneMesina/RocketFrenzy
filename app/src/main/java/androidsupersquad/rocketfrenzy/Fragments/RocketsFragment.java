package androidsupersquad.rocketfrenzy.Fragments;


import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import androidsupersquad.rocketfrenzy.DataBase.ByteArrayConverter;
import androidsupersquad.rocketfrenzy.DataBase.RocketContentProvider;
import androidsupersquad.rocketfrenzy.DataBase.RocketDB;
import androidsupersquad.rocketfrenzy.Fragments.Adapters.RocketGridAdapter;
import androidsupersquad.rocketfrenzy.Fragments.Models.Rocket;
import androidsupersquad.rocketfrenzy.Fragments.Models.ShopItems;
import androidsupersquad.rocketfrenzy.R;


public class RocketsFragment extends Fragment {
ArrayList rockets;
    public RocketsFragment() {
    }
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        //Delete After
      /*  addRocketToPlayer(getPlayerName(),new Rocket("Apollo",R.drawable.apollorocket, "Heck"));
        addRocketToPlayer(getPlayerName(),new Rocket("Horizon",R.drawable.horizonrocket, "Heck"));
        addRocketToPlayer(getPlayerName(),new Rocket("Something",R.drawable.kamakazirocket, "Heck"));*/
        //Delete After
        rockets=getPlayerRockets(getPlayerName());
        rockets.addAll(getPlayerItems(getPlayerName()));
        GridView inventory = (GridView) getActivity().findViewById(R.id.Inventory);
        inventory.setAdapter(new RocketGridAdapter(getActivity().getBaseContext(),rockets));
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rockets, container, false);
    }
    private int addRocketToPlayer(String playerName, Rocket rocket)
    {
        String whereClause = RocketDB.USER_NAME_COLUMN + "= ?";
        String[] whereArgs = {playerName};
        ArrayList<Rocket> currentRockets = getPlayerRockets(playerName);
        if(currentRockets == null)
        {
            currentRockets = new ArrayList<Rocket>();
        }
        currentRockets.add(rocket);
        byte[]bytes = ByteArrayConverter.ObjectToByteArray(currentRockets);
        ContentValues values = new ContentValues();
        values.put(RocketDB.ROCKETS_OWNED_COLUMN, bytes);
        return getActivity().getContentResolver().update(RocketContentProvider.CONTENT_URI, values, whereClause, whereArgs);
    }
    private String getPlayerName()
    {
        Cursor cursor = getActivity().getContentResolver().query(RocketContentProvider.CONTENT_URI, null, null, null, null);
        int username = cursor.getColumnIndex(RocketDB.USER_NAME_COLUMN);
        cursor.moveToFirst();
        //still kind of testing//
        String name = cursor.getString(username);
        Log.d("PLAYER_NAME_INFO", "Username: " + name);
        return name;
    }
    private ArrayList<ShopItems> getPlayerItems(String playerName)
    {
        String where = RocketDB.USER_NAME_COLUMN + "= ?";
        String whereArgs[] = {playerName};
        String[] resultColumns = {RocketDB.ITEMS_OWNED_COLUMN};
        Cursor cursor = getActivity().getContentResolver().query(RocketContentProvider.CONTENT_URI, resultColumns, where, whereArgs, null);
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
        Cursor cursor = getActivity().getContentResolver().query(RocketContentProvider.CONTENT_URI, resultColumns, where, whereArgs, null);
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
            Log.d("ROCKET_INFO", "Username: " + playerName + "\nRocket names: null");
            return null;
        }
    }
}
