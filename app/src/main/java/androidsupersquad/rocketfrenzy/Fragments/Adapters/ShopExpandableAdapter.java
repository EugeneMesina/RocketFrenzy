package androidsupersquad.rocketfrenzy.Fragments.Adapters;
import androidsupersquad.rocketfrenzy.Fragments.Models.ShopItems;
import androidsupersquad.rocketfrenzy.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * This is the Custom Expandable List Adapter
 * that will hold our custom information about a shop
 * item and inflate our custom layouts
 * Created by Lazer on 4/28/2017.
 */
public class ShopExpandableAdapter extends BaseExpandableListAdapter {
   //The current context the ExpandableListView is in
    private Context context;
    //The shop items to display
    private List<ShopItems> ItemList;

    /**
     * Constructs the values passed in from the Shop Fragment
     * @param context the current context the fragment is in
     * @param list the list of items
     */
    public ShopExpandableAdapter(Context context, List<ShopItems> list)
    {
        this.context=context;
        ItemList= list;
    }

    /**
     * Inflates the Description and the Buy Button of the item in the Expandable List View
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return the View
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        //Get the current shop item to display
        ShopItems currentItem= (ShopItems) getChild(groupPosition,childPosition);
        //Inflate the current view with our custom view we made
        if(convertView==null){
            LayoutInflater inf= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.shop_child_row,null);
        }
        //set the text to the description of the item
        TextView description= (TextView) convertView.findViewById(R.id.Description);
        description.setText(currentItem.getItemDescription());
        //set the cost of the item onto the button
        Button cost = (Button) convertView.findViewById(R.id.BuyButton);
        cost.setText(((Integer)currentItem.getItemCost()).toString());
        cost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                System.out.println("HERE");
            }
        });


        return convertView;
    }

    /**
     * Inflates the Name and the Picture of the item in the Expandable List View
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        //get the item
        ShopItems currentItem = (ShopItems) getGroup(groupPosition);
        //inflate our custom view
        if(convertView==null){
            LayoutInflater inf= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.shop_group_row,null);
        }
        //Set the Textview with the item name
        TextView ItemName = (TextView) convertView.findViewById(R.id.ItemName);
        ItemName.setText(currentItem.getItemName());
        //Set the Image with the drawable ID
        ImageView ItemImage = (ImageView) convertView.findViewById(R.id.ItemImage);
        ItemImage.setImageResource(currentItem.getItemImage());

        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ShopItems description= ItemList.get(groupPosition);
        return description;
    }
    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }
    @Override
    public int getGroupCount() {
        return ItemList.size();
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }
    @Override
    public Object getGroup(int groupPosition) {
        return ItemList.get(groupPosition);
    }
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    @Override
    public boolean hasStableIds() {
        return true;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
