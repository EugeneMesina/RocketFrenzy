package androidsupersquad.rocketfrenzy.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidsupersquad.rocketfrenzy.R;

/**
 * This is the Help Fragment
 * This provides the basic rules of the game, UI information, and Game Objectives
 *
 * Created by: Jimmy Chao (Lazer)
 */
public class HelpFragment extends Fragment{

    /**
     * Called for final instantiation
     *
     * @param savedInstanceState the saved data
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        //Get UI elements from the layout
        final ScrollView ObjectiveHelp= (ScrollView) getActivity().findViewById(R.id.ObjectiveSV);
        final ScrollView MenuHelp= (ScrollView) getActivity().findViewById(R.id.MenuSV);
        final RelativeLayout ObjectiveHelpL= (RelativeLayout) getActivity().findViewById(R.id.ObjectiveHelp);
        final RelativeLayout GameHelpL= (RelativeLayout) getActivity().findViewById(R.id.GameHelp);
        final RelativeLayout MenuHelpL= (RelativeLayout) getActivity().findViewById(R.id.MenuHelp);
        final Button ObjectiveB =(Button) getActivity().findViewById(R.id.Objectives);
        Button  GameHelpB=(Button) getActivity().findViewById(R.id.Games);
        Button MenuB =(Button) getActivity().findViewById(R.id.Menu_Buttons);
        //Sets OnclickListener for the buttons to show Objectives/Game Rules/UI Navigations Description
        ObjectiveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectiveHelp.setVisibility(View.VISIBLE);
                ObjectiveHelpL.setVisibility(View.VISIBLE);
                GameHelpL.setVisibility(View.INVISIBLE);
                MenuHelpL.setVisibility(View.INVISIBLE);
                MenuHelp.setVisibility(View.INVISIBLE);
            }
        });
        GameHelpB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectiveHelp.setVisibility(View.INVISIBLE);
                ObjectiveHelpL.setVisibility(View.INVISIBLE);
                GameHelpL.setVisibility(View.VISIBLE);
                MenuHelpL.setVisibility(View.INVISIBLE);
                MenuHelp.setVisibility(View.INVISIBLE);
            }
        });
        MenuB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectiveHelp.setVisibility(View.INVISIBLE);
                ObjectiveHelpL.setVisibility(View.INVISIBLE);
                GameHelpL.setVisibility(View.INVISIBLE);
                MenuHelpL.setVisibility(View.VISIBLE);
                MenuHelp.setVisibility(View.VISIBLE);
            }
        });
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help, container, false);
    }


}
