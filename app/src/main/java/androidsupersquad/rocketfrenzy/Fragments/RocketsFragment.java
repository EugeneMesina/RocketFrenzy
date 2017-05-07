package androidsupersquad.rocketfrenzy.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import androidsupersquad.rocketfrenzy.Fragments.Adapters.RocketGridAdapter;
import androidsupersquad.rocketfrenzy.Fragments.Models.Rocket;
import androidsupersquad.rocketfrenzy.R;


public class RocketsFragment extends Fragment {
List<Rocket> rockets;
    public RocketsFragment() {
        rockets= new ArrayList<Rocket>();
        rockets.add(new Rocket("Apollo",R.drawable.apollorocket,"heck"));
        rockets.add(new Rocket("Apollo",R.drawable.apollorocket,"heck"));
        rockets.add(new Rocket("Apollo",R.drawable.apollorocket,"heck"));
        rockets.add(new Rocket("Apollo",R.drawable.apollorocket,"heck"));
        rockets.add(new Rocket("Apollo",R.drawable.kamakazirocket,"heck"));
        rockets.add(new Rocket("Apollo",R.drawable.kamakazirocket,"heck"));

    }
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        GridView inventory = (GridView) getActivity().findViewById(R.id.Inventory);
        inventory.setAdapter(new RocketGridAdapter(getActivity().getBaseContext(),rockets));
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rockets, container, false);
    }

}
