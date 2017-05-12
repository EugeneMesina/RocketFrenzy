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

import androidsupersquad.rocketfrenzy.R;

public class HelpFragment extends Fragment{

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final RelativeLayout ObjectiveHelp= (RelativeLayout) getActivity().findViewById(R.id.ObjectiveHelp);
        final RelativeLayout GameHelp= (RelativeLayout) getActivity().findViewById(R.id.GameHelp);
        final RelativeLayout MenuHelp= (RelativeLayout) getActivity().findViewById(R.id.MenuHelp);
        Button ObjectiveB =(Button) getActivity().findViewById(R.id.Objectives);
        Button  GameHelpB=(Button) getActivity().findViewById(R.id.Games);
        Button MenuB =(Button) getActivity().findViewById(R.id.Menu_Buttons);
        ObjectiveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectiveHelp.setVisibility(View.VISIBLE);
                GameHelp.setVisibility(View.INVISIBLE);
                MenuHelp.setVisibility(View.INVISIBLE);
            }
        });
        GameHelpB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectiveHelp.setVisibility(View.INVISIBLE);
                GameHelp.setVisibility(View.VISIBLE);
                MenuHelp.setVisibility(View.INVISIBLE);
            }
        });
        MenuB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectiveHelp.setVisibility(View.INVISIBLE);
                GameHelp.setVisibility(View.INVISIBLE);
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
