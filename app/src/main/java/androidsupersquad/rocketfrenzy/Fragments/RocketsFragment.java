package androidsupersquad.rocketfrenzy.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidsupersquad.rocketfrenzy.R;


public class RocketsFragment extends Fragment {

    public RocketsFragment() {
    }
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        GridView inventory = (GridView) getActivity().findViewById(R.id.Inventory);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rockets, container, false);
    }

}
