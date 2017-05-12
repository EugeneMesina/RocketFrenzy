package androidsupersquad.rocketfrenzy.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidsupersquad.rocketfrenzy.R;

/**
 * Created by Jimmy Chao (Lazer)
 * A Easter Egg Fragment that shows only when the player has 100 Bleach Items in their inventory
 */
public class KamikaziFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kamikazi, container, false);
    }

}
