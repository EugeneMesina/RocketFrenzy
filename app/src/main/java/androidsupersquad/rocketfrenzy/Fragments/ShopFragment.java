package androidsupersquad.rocketfrenzy.Fragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import androidsupersquad.rocketfrenzy.Fragments.Models.ShopItems;
import androidsupersquad.rocketfrenzy.R;


public class ShopFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<ShopItems> Items;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShopFragment() {
        Items = new ArrayList<ShopItems>();
        Items.add(new ShopItems("Bleach ",R.drawable.bleach,"Killer Substance",10000));
        Items.add(new ShopItems("Bleach1",R.drawable.bleach,"Killer Substance",10000));
        Items.add(new ShopItems("Bleach2",R.drawable.bleach,"Killer Substance",10000));
        Items.add(new ShopItems("Bleach3",R.drawable.bleach,"Killer Substance",10000));
        Items.add(new ShopItems("Bleach4",R.drawable.bleach,"Killer Substance",10000));
        Items.add(new ShopItems("Bleach5",R.drawable.bleach,"Killer Substance",10000));
        Items.add(new ShopItems("Bleach6",R.drawable.bleach,"Killer Substance",10000));
        Items.add(new ShopItems("Bleach7",R.drawable.bleach,"Killer Substance",10000));
        Items.add(new ShopItems("Bleach8",R.drawable.bleach,"Killer Substance",10000));
        Items.add(new ShopItems("Bleach9",R.drawable.bleach,"Killer Substance",10000));
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopFragment newInstance(String param1, String param2) {
        ShopFragment fragment = new ShopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        ShopAdapter adapter = new ShopAdapter(this.getContext(),R.layout.shop_row,Items);
        ExpandableListView listView = (ExpandableListView) getActivity().findViewById(R.id.ShopList);
        listView.setAdapter(new ShopExpandableAdapter(getActivity().getBaseContext(),Items));
       // ListView current = (ListView) getActivity().findViewById(R.id.ShopList);
        //current.setAdapter(adapter);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop, container, false);
    }
}
