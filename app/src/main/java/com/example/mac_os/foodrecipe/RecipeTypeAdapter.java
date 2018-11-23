
package com.example.mac_os.foodrecipe;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mac_os.foodrecipe.Model.RecipeTypesItemImage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecipeTypeAdapter extends BaseAdapter {
    private final List<RecipeTypesItemImage> mRecipeTypesItemImages = new ArrayList<RecipeTypesItemImage>();
    private final List<String> mRecipeTypesDetails;
    private final LayoutInflater mInflater;
    Context mContext;
    RecipeTypesItemImage recipeTypesItemImage;

    public RecipeTypeAdapter(Context context, List<String> recipeTypesDetails) {
        mInflater = LayoutInflater.from(context);
        mRecipeTypesDetails = recipeTypesDetails;
        mContext = context;
        add();

    }

    private void add() {
        for (int i = 0; i < mRecipeTypesDetails.size(); i++) {
            int resource_Of_Image = mContext.getResources()
                    .getIdentifier(  mRecipeTypesDetails.get(i).toLowerCase().replace(" ", "") , "drawable", mContext.getPackageName());

            if(resource_Of_Image ==0){
                resource_Of_Image = R.drawable.noimage;
            }

            Log.i("Drawable", resource_Of_Image + "");
            mRecipeTypesItemImages.add(new RecipeTypesItemImage(mRecipeTypesDetails.get(i), resource_Of_Image));
        }

//        mRecipeTypesItemImages.add(new RecipeTypesItemImage("Magenta", R.drawable.image2));
//        mRecipeTypesItemImages.add(new RecipeTypesItemImage("Dark Gray", R.drawable.image3));
//        mRecipeTypesItemImages.add(new RecipeTypesItemImage("Gray", R.drawable.image4));
//        mRecipeTypesItemImages.add(new RecipeTypesItemImage("Green", R.drawable.image5));
//        mRecipeTypesItemImages.add(new RecipeTypesItemImage("Cyan", R.drawable.image6));

    }

    @Override
    public int getCount() {
        return mRecipeTypesItemImages.size();
    }

    @Override
    public RecipeTypesItemImage getItem(int i) {
        return mRecipeTypesItemImages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mRecipeTypesItemImages.get(i).getPic();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        SquareImageView picture;
        TextView name;
        if (v == null) {
            v = mInflater.inflate(R.layout.recipe_grid_item, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.text, v.findViewById(R.id.text));
            v.setTag(R.id.recipe_type_card_view,v.findViewById(R.id.recipe_type_card_view));
        }

        picture = (SquareImageView) v.getTag(R.id.picture);
        name = (TextView) v.getTag(R.id.text);

        recipeTypesItemImage = getItem(i);

        Picasso.with(mContext)
                .load(recipeTypesItemImage.getPic())
                .fit()
                .centerCrop()
                .into(picture);
        name.setText(recipeTypesItemImage.getName());

//        v.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(mContext,position+" pos",Toast.LENGTH_LONG).show();
//            }
//        });
        return v;
    }

}
