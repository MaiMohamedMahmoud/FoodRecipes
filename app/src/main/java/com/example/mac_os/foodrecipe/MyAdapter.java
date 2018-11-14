package com.example.mac_os.foodrecipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mac_os.foodrecipe.Model.Item;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseAdapter {
    private final List<Item> mItems = new ArrayList<Item>();
    private final LayoutInflater mInflater;

    public MyAdapter(Context context) {
        mInflater = LayoutInflater.from(context);

        mItems.add(new Item("Red",       R.drawable.image1));
        mItems.add(new Item("Magenta",   R.drawable.image2));
        mItems.add(new Item("Dark Gray", R.drawable.image3));
        mItems.add(new Item("Gray",      R.drawable.image4));
        mItems.add(new Item("Green",     R.drawable.image5));
        mItems.add(new Item("Cyan",      R.drawable.image6));
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Item getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mItems.get(i).getPic();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        ImageView picture;
        TextView name;

        if (v == null) {
            v = mInflater.inflate(R.layout.recipe_grid_item, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.text, v.findViewById(R.id.text));
        }

        picture = (ImageView) v.getTag(R.id.picture);
        name = (TextView) v.getTag(R.id.text);

        Item item = getItem(i);

        picture.setImageResource(item.getPic());
        name.setText(item.getName());

        return v;
    }

}
