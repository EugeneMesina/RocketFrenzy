package androidsupersquad.rocketfrenzy.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

import androidsupersquad.rocketfrenzy.DataBase.ByteArrayConverter;
import androidsupersquad.rocketfrenzy.DataBase.RocketContentProvider;
import androidsupersquad.rocketfrenzy.DataBase.RocketDB;
import androidsupersquad.rocketfrenzy.Fragments.Adapters.RocketGridAdapter;
import androidsupersquad.rocketfrenzy.Fragments.Models.Rocket;
import androidsupersquad.rocketfrenzy.Fragments.Models.ShopItems;
import androidsupersquad.rocketfrenzy.R;

/**
 * Inventory Fragment to display all the user's inventory items
 *
 * Created by: Jimmy Chao(Lazer)
 */
public class RocketsFragment extends Fragment {
    /** The player's list of rockets */
    private ArrayList rockets;

    //Empty Constructor for the fragment
    public RocketsFragment() {
    }

    /**
     * Called when activity is created
     *
     * @param savedInstanceState saved data
     */
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //get all player's rockets
        rockets = getPlayerRockets(getPlayerName());
        //get all player's Shop Items
        rockets.addAll(getPlayerItems(getPlayerName()));
        GridView inventory = (GridView) getActivity().findViewById(R.id.Inventory);
        inventory.setAdapter(new RocketGridAdapter(getActivity(), rockets));
    }

    /**
     * Creates the fragment view
     *
     * @param inflater The inflater used to inflate the fragment
     * @param container The fragment layout to inflate
     * @param savedInstanceState saved data
     * @return The fragment view
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rockets, container, false);
    }

    /**
     * Ran when app is resumed
     */
    public void onResume() {
        super.onResume();
        //Refreshes the view with the newly removed launch pad and rocket
        rockets = getPlayerRockets(getPlayerName());
        rockets.addAll(getPlayerItems(getPlayerName()));
        GridView inventory = (GridView) getActivity().findViewById(R.id.Inventory);
        inventory.setAdapter(new RocketGridAdapter(getActivity(), rockets));
    }

    /**
     * DataBase Method to Retrieve the UserName
     *
     * @return UserName
     */
    private String getPlayerName() {
        Cursor cursor = getActivity().getContentResolver().query(RocketContentProvider.CONTENT_URI, null, null, null, null);
        int username = cursor.getColumnIndex(RocketDB.USER_NAME_COLUMN);
        cursor.moveToFirst();
        //still kind of testing//
        String name = cursor.getString(username);
        Log.d("PLAYER_NAME_INFO", "Username: " + name);
        return name;
    }

    /**
     * DataBase Method to get all player's items
     *
     * @param playerName: UserName
     * @return inventory items
     */
    private ArrayList<ShopItems> getPlayerItems(String playerName) {
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
        } catch (Exception e) {
            Log.d("ITEM_INFO", "Username: " + playerName + "\nItem names: null");
            return null;
        }
    }

    /**
     * DataBase Method to get all the player's current rockets
     *
     * @param playerName UserName
     * @return The list of rockets owned by the player
     */
    private ArrayList<Rocket> getPlayerRockets(String playerName) {
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
        } catch (Exception e) {
            Log.d("ROCKET_INFO", "Username: " + playerName + "\nRocketLaunch names: null");
            return null;
        }
    }
}
