package androidsupersquad.rocketfrenzy.Fragments.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import androidsupersquad.rocketfrenzy.Fragments.Models.Rocket;
import androidsupersquad.rocketfrenzy.Fragments.Models.ShopItems;
import androidsupersquad.rocketfrenzy.R;

/**
 * Created by Jimmy on 5/6/2017.
 */

public class RocketGridAdapter extends BaseAdapter{
    private Context mContext;
    private List rocket;

    public RocketGridAdapter(Context c, List rockets)
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
            if(Rocket.class.isInstance(rocket.get(position)))
            {
                Rocket currentRocket = (Rocket) rocket.get(position);
                rocketImage.setImageResource(currentRocket.getImage());
                rocketImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext,"Rocket Clicked",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else
            {
                ShopItems currentRocket = (ShopItems) rocket.get(position);
                rocketImage.setImageResource(currentRocket.getItemImage());
            }


        }
        else
        {
            grid= convertView;
        }

        return grid;
    }
}
