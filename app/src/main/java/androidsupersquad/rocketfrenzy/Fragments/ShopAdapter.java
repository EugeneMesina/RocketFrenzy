package androidsupersquad.rocketfrenzy.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidsupersquad.rocketfrenzy.Fragments.Models.ShopItems;
import androidsupersquad.rocketfrenzy.R;

/**
 * Created by Jimmy on 4/22/2017.
 */

public class ShopAdapter extends ArrayAdapter<ShopItems> {
    //The list of animals
    private final List<ShopItems> Items;
    //Sets the custom adapter with the layout
    public ShopAdapter(Context context, int resource, List<ShopItems> people)
    {
        super(context, resource, people);
        this.Items = people;
    }
    @Override
    //applys the resources to the textview and image view
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.shop_row, null);
        ShopItems currentItem= this.Items.get(position);
        final TextView textView =(TextView) row.findViewById(R.id.ItemName);
        textView.setText(currentItem.getItemName());
        ImageView imageView = (ImageView) row.findViewById(R.id.ItemImage);
        imageView.setBackgroundResource(currentItem.getItemImage());
        Button buyButton = (Button) row.findViewById(R.id.BuyButton);
        buyButton.setText(((Float)currentItem.getItemCost()).toString());

        return row;
    }

}
