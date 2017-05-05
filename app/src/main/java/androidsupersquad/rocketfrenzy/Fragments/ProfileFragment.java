package androidsupersquad.rocketfrenzy.Fragments;


import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidsupersquad.rocketfrenzy.R;

public class ProfileFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private TextView mTextView1;

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
        TextView userName = (TextView) view.findViewById(R.id.UserName);
        Typeface myCustomFont = Typeface.createFromAsset(view.getContext().getAssets(),"fonts/TwoLines.ttf");
        userName.setTypeface(myCustomFont);
        //TODO: Set USERNAME from DataBase
        userName.setText("Android Super Squad");
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mTextView1 = (TextView) view.findViewById(R.id.profile_textview_1);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
