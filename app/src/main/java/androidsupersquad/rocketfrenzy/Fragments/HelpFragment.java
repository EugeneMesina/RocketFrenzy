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

public class HelpFragment extends Fragment{

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ScrollView ObjectiveHelp= (ScrollView) getActivity().findViewById(R.id.ObjectiveSV);
        final ScrollView MenuHelp= (ScrollView) getActivity().findViewById(R.id.MenuSV);
        final RelativeLayout ObjectiveHelpL= (RelativeLayout) getActivity().findViewById(R.id.ObjectiveHelp);
        final RelativeLayout GameHelpL= (RelativeLayout) getActivity().findViewById(R.id.GameHelp);
        final RelativeLayout MenuHelpL= (RelativeLayout) getActivity().findViewById(R.id.MenuHelp);
        final Button ObjectiveB =(Button) getActivity().findViewById(R.id.Objectives);
        Button  GameHelpB=(Button) getActivity().findViewById(R.id.Games);
        Button MenuB =(Button) getActivity().findViewById(R.id.Menu_Buttons);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help, container, false);
    }


}
