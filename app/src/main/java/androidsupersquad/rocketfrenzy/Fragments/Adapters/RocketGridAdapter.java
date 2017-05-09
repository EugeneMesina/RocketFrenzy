package androidsupersquad.rocketfrenzy.Fragments.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import androidsupersquad.rocketfrenzy.Fragments.Models.Rocket;
import androidsupersquad.rocketfrenzy.R;

/**
 * Created by Jimmy on 5/6/2017.
 */

public class RocketGridAdapter extends BaseAdapter{
    private Context mContext;
    private List<Rocket> rocket;

    public RocketGridAdapter(Context c, List<Rocket> rockets)
    {
        mContext=c;
        rocket=rockets;
    }

    @Override
    public int getCount() {
        return rocket.size();
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null){
            grid= new View(mContext);
            grid=inflater.inflate((R.layout.rocket_grid_single),null);
            ImageView rocketImage= (ImageView) grid.findViewById(R.id.rocket);
            rocketImage.setImageResource(rocket.get(position).getImage());
        }
        else
        {
            grid= convertView;
        }

        return grid;
    }
}
