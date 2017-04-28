package androidsupersquad.rocketfrenzy.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.EventLogTags;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import androidsupersquad.rocketfrenzy.Fragments.Models.ShopItems;
import androidsupersquad.rocketfrenzy.R;

/**
 * Created by Jimmy on 4/28/2017.
 */

public class ShopExpandableAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<ShopItems> ItemList;

    public ShopExpandableAdapter(Context context, List<ShopItems> list)
    {
        this.context=context;
        ItemList= list;
    }
    @Override

    public Object getChild(int groupPosition, int childPosition)
    {
        ShopItems description= ItemList.get(groupPosition);
        return description;
    }
    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ShopItems currentItem= (ShopItems) getChild(groupPosition,childPosition);
        if(convertView==null){
            LayoutInflater inf= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.shop_child_row,null);
        }
        TextView description= (TextView) convertView.findViewById(R.id.Description);
        description.setText(currentItem.getItemDescription());
        Button cost = (Button) convertView.findViewById(R.id.BuyButton);
        cost.setText(((Float)currentItem.getItemCost()).toString());


        return convertView;
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ShopItems currentItem = (ShopItems) getGroup(groupPosition);
        if(convertView==null){
            LayoutInflater inf= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.shop_group_row,null);
        }
        TextView ItemName = (TextView) convertView.findViewById(R.id.ItemName);
        ItemName.setText(currentItem.getItemName());
        ImageView ItemImage = (ImageView) convertView.findViewById(R.id.ItemImage);
        ItemImage.setImageResource(currentItem.getItemImage());

        return convertView;
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
