package androidsupersquad.rocketfrenzy.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidsupersquad.rocketfrenzy.R;

/**
 * A Easter Egg Fragment that shows only when the player has 100 Bleach Items in their inventory
 *
 * Created by: Jimmy Chao (Lazer)
 */
public class KamikaziFragment extends Fragment {

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
        return inflater.inflate(R.layout.fragment_kamikazi, container, false);
    }

}
