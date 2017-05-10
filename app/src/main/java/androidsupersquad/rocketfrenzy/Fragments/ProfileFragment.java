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
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidsupersquad.rocketfrenzy.DataBase.RocketContentProvider;
import androidsupersquad.rocketfrenzy.R;
import androidsupersquad.rocketfrenzy.DataBase.RocketDB;
import static android.content.Context.INPUT_METHOD_SERVICE;

public class ProfileFragment extends Fragment {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView userName = (TextView) view.findViewById(R.id.UserName);
        TextView coins = (TextView) view.findViewById(R.id.CoinAmount);
        Typeface myCustomFont = Typeface.createFromAsset(view.getContext().getAssets(),"fonts/TwoLines.ttf");
        userName.setTypeface(myCustomFont);
        //coins.setTypeface(myCustomFont);
        //TODO: Set USERNAME from DataBase
        userName.setText(getPlayerName());
        userName.setGravity(Gravity.CENTER);
        Integer coinAmount=getPlayerCoinAmount(getPlayerName());
        coins.setText(coinAmount.toString() );
        System.out.println(coinAmount);
        final EditText rename = (EditText) view.findViewById(R.id.NameChange);
        rename.setTypeface(myCustomFont);
        rename.setOnKeyListener(new View.OnKeyListener() {
           public boolean onKey(View v,int keyCode, KeyEvent event)
           {
               if(event.getAction()==KeyEvent.ACTION_DOWN&&(keyCode==KeyEvent.KEYCODE_ENTER))
               {
                   System.out.println("HELLO");
                   InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                   imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                   rename.setVisibility(View.INVISIBLE);
                   String newName= rename.getText().toString();
                   updatePlayerUsername(getPlayerName(),newName);
                   userName.setText(newName);
                   userName.setGravity(Gravity.CENTER);
                   return true;
               }
               return false;
           }
        });

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

    private int updatePlayerUsername(String playerName, String newName)
    {
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
}
