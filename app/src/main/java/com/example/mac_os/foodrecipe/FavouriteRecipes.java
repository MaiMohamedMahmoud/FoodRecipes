package com.example.mac_os.foodrecipe;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mac_os.foodrecipe.Model.PicassoCircleTransformation;
import com.example.mac_os.foodrecipe.Model.Recipe;
import com.example.mac_os.foodrecipe.Model.Recipe_;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class FavouriteRecipes extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter FavouriteAdapter;
    List<Recipe_> FavouriteList;
    private SharedPreferences mSharedPreferences;
    public static final String PREFERENCE = "preference";
    Gson gson;
    SharedPreferences.Editor prefsEditor;
    Toolbar toolbar;
    Context context;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        /**
         *  the following code is what make the menu favourite appears
         *  but because we didn't want it anymore i'll comment it
         */
        //getMenuInflater().inflate(R.menu.menu_favourite_recipe, menu);
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /**
         * this code is not used any more .. we don't need edit the favourite title at this stage of app..
         *
         */

        int id = item.getItemId();
        if (id == R.id.action_edit) {
         //here create the dialog that make u edit your favourite list tilte page
            AlertDialog.Builder builder;
            final View inflater = LayoutInflater.from(FavouriteRecipes.this).inflate(R.layout.dialog_edit_favourite_name, null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(context);
            }
            builder.setTitle("Change Title")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with change
                            EditText edit=(EditText)inflater.findViewById(R.id.editText);
                            Toast.makeText(getApplicationContext(),edit.getText(),Toast.LENGTH_LONG).show();
                            toolbar.setTitle(edit.getText());
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    }).setView(inflater)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

            //Toast.makeText(getApplicationContext(),"Edit clicked here", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //to store the array of object in sharedpref.
        String json = gson.toJson(FavouriteList);
        Toast.makeText(getApplicationContext(),FavouriteList.size()+"",Toast.LENGTH_LONG).show();
        prefsEditor.putString("MyFavouriteList", json);
        prefsEditor.commit();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_favourite_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.recipe_fav_list);
        mSharedPreferences = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        prefsEditor = mSharedPreferences.edit();
        gson = new Gson();
        context = FavouriteRecipes.this;
        String json = mSharedPreferences.getString("MyFavouriteList", "");
        Type type = new TypeToken<List<Recipe_>>() {}.getType();
        if (json == "") {
            FavouriteList = new ArrayList<>();
        } else {
            FavouriteList = gson.fromJson(json, type);
        }

        Toast.makeText(getApplicationContext(),FavouriteList.size()+"",Toast.LENGTH_LONG).show();
        toolbar.setTitle("My Favourite List");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        FavouriteAdapter = new RecipeFavouriteAdapter(FavouriteList);
        mRecyclerView.setAdapter(FavouriteAdapter);

    }

    public class RecipeFavouriteViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtDescription;
        ImageView recipeImage;
        Button exploreRecipe;
        Button deleteRecipe;

        public RecipeFavouriteViewHolder(View itemView) {
            super(itemView);
            getItemView(itemView);
        }
        public View getItemView (View itemView){
            txtName = (TextView) itemView.findViewById(R.id.fav_name);
            txtDescription = (TextView)itemView.findViewById(R.id.fav_description);
            recipeImage =(ImageView) itemView.findViewById(R.id.fav_img);
            exploreRecipe = (Button) itemView.findViewById(R.id.explore_fav_list);
            deleteRecipe = (Button) itemView.findViewById(R.id.delete_fav_list);
          return itemView;
        }

        public void Bind(final Recipe_ mRecipeItem){
            txtName.setText(mRecipeItem.getRecipeName());
            txtDescription.setText(mRecipeItem.getRecipeDescription());

            Picasso.with(getApplicationContext()).
                    load(mRecipeItem.getRecipeImage()).
                    fit().
                    centerCrop().
                    into(recipeImage);
            exploreRecipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentRecipeDetails = new Intent(FavouriteRecipes.this,RecipeDetails.class);
                    intentRecipeDetails.putExtra("recipeId", mRecipeItem.getRecipeId());
                    startActivity(intentRecipeDetails);
                }
            });
            deleteRecipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"delete onclick"+ FavouriteList.indexOf(mRecipeItem), Toast.LENGTH_LONG).show();

                    FavouriteList.remove(mRecipeItem);
                    FavouriteAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    public class RecipeFavouriteAdapter extends RecyclerView.Adapter<RecipeFavouriteViewHolder>{

        List<Recipe_> listOfFavouriteRecipes;
        public RecipeFavouriteAdapter(List<Recipe_> FavouriteList){
            listOfFavouriteRecipes = FavouriteList;
        }
        @NonNull
        @Override
        public RecipeFavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflateView = getLayoutInflater();
            View view = inflateView.inflate(R.layout.recipe_favourite_layout,parent,false);
            return new RecipeFavouriteViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecipeFavouriteViewHolder holder, int position) {
            holder.Bind(listOfFavouriteRecipes.get(position));
        }

        @Override
        public int getItemCount() {
            return listOfFavouriteRecipes.size();
        }
    }
}
