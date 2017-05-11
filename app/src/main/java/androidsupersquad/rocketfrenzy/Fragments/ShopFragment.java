package androidsupersquad.rocketfrenzy.Fragments;
import androidsupersquad.rocketfrenzy.Data.ShopData;
import androidsupersquad.rocketfrenzy.DataBase.RocketContentProvider;
import androidsupersquad.rocketfrenzy.DataBase.RocketDB;
import androidsupersquad.rocketfrenzy.Fragments.Adapters.ShopExpandableAdapter;
import androidsupersquad.rocketfrenzy.Fragments.Models.ShopItems;
import androidsupersquad.rocketfrenzy.R;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 *  This is the Shop Menu where it will display to the user
 *  different shop items they can buy
 *  Created by Lazer
 */
public class ShopFragment extends Fragment {
    //Data Structure to hold all shop items
    private ArrayList<ShopItems> Items;
    /**
     * Fragment Constructor that creates all the items in the shop
     */
    public ShopFragment() {}
    public void CreateItemList()
    {
        //Create new ArrayList to hold existing items or add items
        Items = new ArrayList<ShopItems>();
        Items.add(ShopData.LaunchPad);
        Items.add(ShopData.LaunchPadBundle);
        Items.add(ShopData.HorizonIcon);
        Items.add(ShopData.FireIcon);
        Items.add(ShopData.SkullIcon);
        Items.add(ShopData.Bleach);
    }
    /**
     * Sets teh Expandable List View Adapter with specific
     * Group Layout
     * Child Layout
     * @param savedInstanceState
     */
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        CreateItemList();
        TextView coins = (TextView) getActivity().findViewById(R.id.ShopCoinAmount);
        Integer coinsamount= getPlayerCoinAmount(getPlayerName());
        coins.setText(coinsamount.toString());
        //get Expandable List View
        ExpandableListView listView = (ExpandableListView) getActivity().findViewById(R.id.ShopList);
        //Set it's adapter to the custom Adapter
        listView.setAdapter(new ShopExpandableAdapter(getActivity(),Items));
    }

    /**
     * Inflates the fragment with the specific layout in the resource file
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop, container, false);
    }
    private int getPlayerCoinAmount(String playerName)
    {
        String where = RocketDB.USER_NAME_COLUMN + "= ?";
        String whereArgs[] = {playerName};
        String[] resultColumns = {RocketDB.COIN_AMOUNT_COLUMN};
        Cursor cursor = getActivity().getContentResolver().query(RocketContentProvider.CONTENT_URI, resultColumns, where, whereArgs, null);
        int coin = cursor.getColumnIndex(RocketDB.COIN_AMOUNT_COLUMN);
        cursor.moveToFirst();
        int coinAmount = cursor.getInt(coin);
        Log.d("COIN_INFO", "Username: " + playerName + "\nCoin amount: " + coinAmount);
        return coinAmount;
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
}
