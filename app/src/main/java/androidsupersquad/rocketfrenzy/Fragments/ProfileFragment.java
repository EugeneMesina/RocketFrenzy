package androidsupersquad.rocketfrenzy.Fragments;


import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import androidsupersquad.rocketfrenzy.Data.ShopData;
import androidsupersquad.rocketfrenzy.DataBase.ByteArrayConverter;
import androidsupersquad.rocketfrenzy.DataBase.RocketContentProvider;
import androidsupersquad.rocketfrenzy.Fragments.Models.ShopItems;
import androidsupersquad.rocketfrenzy.R;
import androidsupersquad.rocketfrenzy.DataBase.RocketDB;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * This Fragment shows the User's Profile with
 * their favorite icon, their username, and their coin count
 * <p>
 * Created by: Jimmy Chao (Lazer)
 */
public class ProfileFragment extends Fragment {
    /** Current profile icon the player is using */
    private ImageView profilePicture;

    /**
     * onCreate method
     *
     * @param savedInstanceState saved data
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Ran when activity is created
     *
     * @param savedInstanceState saved data
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * The Custom Context Menu to allow player to change icon
     *
     * @param menu     the menu
     * @param v        the view
     * @param menuInfo menu info
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        //get the Items the player owns
        ArrayList<ShopItems> Icons = getPlayerItems(getPlayerName());
        //Searches through player items to see what icons they own to show in the context menu
        if (Icons.contains(ShopData.HorizonIcon)) {
            MenuItem HIcon = menu.findItem(R.id.Horizon);
            HIcon.setVisible(true);
        }
        if (Icons.contains(ShopData.FireIcon)) {
            MenuItem FIcon = menu.findItem(R.id.FireEmblem);
            FIcon.setVisible(true);
        }
        if (Icons.contains(ShopData.SkullIcon)) {
            MenuItem SIcon = menu.findItem(R.id.SkullIcon);
            SIcon.setVisible(true);
        }

    }

    /**
     * Set Onclick listener for each context menu item
     *
     * @param item The item selected
     * @return Whether the item was clicked or not
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //Finds the item click and set the icon based on the item clicked
        switch (item.getItemId()) {
            case R.id.WaterIcon:
                profilePicture.setImageResource(R.drawable.watericon);
                updatePlayerIconString(getPlayerName(), "Water Icon");
                return true;
            case R.id.Horizon:
                profilePicture.setImageResource(R.drawable.horizonicon);
                updatePlayerIconString(getPlayerName(), ShopData.HorizonIcon.getItemName());
                return true;
            case R.id.FireEmblem:
                profilePicture.setImageResource(R.drawable.fireemblem);
                updatePlayerIconString(getPlayerName(), ShopData.FireIcon.getItemName());
                return true;
            case R.id.SkullIcon:
                profilePicture.setImageResource(R.drawable.skullicon);
                updatePlayerIconString(getPlayerName(), ShopData.SkullIcon.getItemName());
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    /**
     * Creates the fragment view
     *
     * @param inflater The inflater used to inflate the fragment
     * @param container The fragment layout to inflate
     * @param savedInstanceState saved data
     * @return The fragment view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        //Find UI Elements by ID
        final TextView userName = (TextView) view.findViewById(R.id.UserName);
        TextView coins = (TextView) view.findViewById(R.id.CoinAmount);
        final EditText rename = (EditText) view.findViewById(R.id.NameChange);
        ImageView Icon = (ImageView) view.findViewById(R.id.ProfilePicture);
        profilePicture = (ImageView) view.findViewById(R.id.ProfilePicture);
        Integer coinAmount = getPlayerCoinAmount(getPlayerName());
        //Set the Context menu to ImageView (OnLongClick)
        registerForContextMenu(profilePicture);
        //Create Custom Font for the user name
        Typeface myCustomFont = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/TwoLines.ttf");
        userName.setTypeface(myCustomFont);
        //coins.setTypeface(myCustomFont);
        userName.setText(getPlayerName());
        //Center the text
        userName.setGravity(Gravity.CENTER);
        //Set the coin amount
        coins.setText(coinAmount.toString());
        rename.setTypeface(myCustomFont);
        //Find the icon the player is using currently
        String currentIcon = getPlayerIconString(getPlayerName());
        if (currentIcon.equals("Water Icon")) {
            Icon.setImageResource(R.drawable.watericon);
        } else if (currentIcon.equals(ShopData.FireIcon.getItemName())) {
            Icon.setImageResource(R.drawable.fireemblem);
        } else if (currentIcon.equals(ShopData.HorizonIcon.getItemName())) {
            Icon.setImageResource(R.drawable.horizonicon);
        } else if (currentIcon.equals(ShopData.SkullIcon.getItemName())) {
            Icon.setImageResource(R.drawable.skullicon);
        }
        //Set the rename editbox to update the username on Enter key press
        rename.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    //Hide the Edit View after user is done
                    rename.setVisibility(View.INVISIBLE);
                    String newName = rename.getText().toString();
                    updatePlayerUsername(getPlayerName(), newName);
                    userName.setText(newName);
                    userName.setGravity(Gravity.CENTER);
                    return true;
                }
                return false;
            }
        });
        //Set On Click Listener for the Username text view to pop up a dialogue to ask if they want to change their username
        //Set the edit view box to visible if they do
        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Want to Change your name")
                        .setTitle("Name Change");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        rename.setVisibility(View.VISIBLE);
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
        });
        return view;
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
     * DataBase Method to get Player's Coin Amount
     *
     * @return UserName
     */
    private int getPlayerCoinAmount(String playerName) {
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
     * DataBase Method to update the UserName
     *
     * @return UserName
     */
    private int updatePlayerUsername(String playerName, String newName) {
        String whereClause = RocketDB.USER_NAME_COLUMN + "= ?";
        String[] whereArgs = {playerName};
        ContentValues newValues = new ContentValues();
        newValues.put(RocketDB.USER_NAME_COLUMN, newName);
        return getActivity().getContentResolver().update(RocketContentProvider.CONTENT_URI, newValues, whereClause, whereArgs);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /**
     * DataBase Method to Retrieve the Icon Name
     *
     * @return User Icon
     */
    private String getPlayerIconString(String playerName) {
        String where = RocketDB.USER_NAME_COLUMN + "= ?";
        String whereArgs[] = {playerName};
        String[] resultColumns = {RocketDB.PLAYER_ICON_COLUMN};
        Cursor cursor = getActivity().getContentResolver().query(RocketContentProvider.CONTENT_URI, resultColumns, where, whereArgs, null);
        int icon = cursor.getColumnIndex(RocketDB.PLAYER_ICON_COLUMN);
        cursor.moveToFirst();
        String iconString = cursor.getString(icon);
        Log.d("ICON_INFO", "Username: " + playerName + "\nIcon string: " + iconString);
        return iconString;
    }

    /**
     * DataBase Method to Update the Icon Name
     *
     * @return User Icon
     */
    private int updatePlayerIconString(String playerName, String newIconString) {
        String whereClause = RocketDB.USER_NAME_COLUMN + "= ?";
        String[] whereArgs = {playerName};
        ContentValues newValues = new ContentValues();
        newValues.put(RocketDB.PLAYER_ICON_COLUMN, newIconString);
        return getActivity().getContentResolver().update(RocketContentProvider.CONTENT_URI, newValues, whereClause, whereArgs);
    }

}
