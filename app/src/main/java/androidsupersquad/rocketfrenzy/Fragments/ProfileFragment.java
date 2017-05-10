package androidsupersquad.rocketfrenzy.Fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.method.KeyListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidsupersquad.rocketfrenzy.R;

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
        Typeface myCustomFont = Typeface.createFromAsset(view.getContext().getAssets(),"fonts/TwoLines.ttf");
        userName.setTypeface(myCustomFont);
        //TODO: Set USERNAME from DataBase
        userName.setText("Android Super Squad");
        userName.setGravity(Gravity.CENTER);
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
