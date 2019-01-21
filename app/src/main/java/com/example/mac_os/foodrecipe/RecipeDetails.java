package com.example.mac_os.foodrecipe;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mac_os.foodrecipe.Model.Direction;
import com.example.mac_os.foodrecipe.Model.Ingredient;
import com.example.mac_os.foodrecipe.Model.RecipeDetail;
import com.example.mac_os.foodrecipe.Model.Recipe_;
import com.example.mac_os.foodrecipe.data.FatSecretApi;
import com.example.mac_os.foodrecipe.data.Utility.ApiUtils;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDetails extends AppCompatActivity {

    FatSecretSearchFood mFatSecretSearch;
    FatSecretApi mSecretApi;
    Toolbar toolbar;
    TextView description;
    CollapsingToolbarLayout toolbarLayout;
    SquareImageView imagetoolbar;
    RecyclerView rvIngredient;
    RecyclerView rvDirections;
    RecyclerView.Adapter rvIngredientAdapter;
    RecyclerView.Adapter rvDirectionsAdapter;
    List<Ingredient> listOfIngredient;
    List<Direction> listOfDirections;
    TextView txt_cooking_time;
    TextView txt_preparation_time;
    TextView txt_servings_number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        CheckConnection check = new CheckConnection(RecipeDetails.this);
        if(!check.isConnected()){
            check.show();
            finish();
        }

        Bundle extras = getIntent().getExtras();
        Long recipeId = extras.getLong("recipeId");
        mSecretApi = ApiUtils.getFoodService();
        mFatSecretSearch = new FatSecretSearchFood();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txt_preparation_time = (TextView) findViewById(R.id.preparation_time_txt);
        txt_servings_number = (TextView) findViewById(R.id.number_of_servings_txt);
        txt_cooking_time = (TextView) findViewById(R.id.cooking_time_txt);
        listOfIngredient = new ArrayList<>();
        description = (TextView) findViewById(R.id.description);
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        imagetoolbar = (SquareImageView) findViewById(R.id.imagetoolbar);
        listOfDirections = new ArrayList<>();
        rvIngredientAdapter = new RecipeDetails.RecipeDetailsAdapter(listOfIngredient);
        rvDirectionsAdapter = new RecipeDetails.RecipeDetailsDirectionAdapter(listOfDirections);
        rvIngredient = (RecyclerView) findViewById(R.id.recycle_view_ingredient);
        rvIngredient.setLayoutManager(new LinearLayoutManager(this));
        rvIngredient.setAdapter(rvIngredientAdapter);

        rvDirections = (RecyclerView) findViewById(R.id.recycle_view_direction);
        rvDirections.setLayoutManager(new LinearLayoutManager(this));
        rvDirections.setAdapter(rvDirectionsAdapter);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        Toast.makeText(getApplicationContext(), recipeId + "", Toast.LENGTH_LONG).show();
        searchRecipeById(recipeId, 1);

    }


    private class RecipeDetailsDirectionHolder extends RecyclerView.ViewHolder {

        TextView directionNumber;
        TextView directionDescription;

        public RecipeDetailsDirectionHolder(View itemView) {
            super(itemView);
            getItemView(itemView);
        }

        public View getItemView(View itemView) {
            directionNumber = (TextView) itemView.findViewById(R.id.direction_number);
            directionDescription = (TextView) itemView.findViewById(R.id.direction_description);
            return itemView;
        }

    }

    private class RecipeDetailsDirectionAdapter extends RecyclerView.Adapter<RecipeDetailsDirectionHolder> {


        List<Direction> mDirectionList;

        RecipeDetailsDirectionAdapter(List<Direction> directionList) {

            mDirectionList = directionList;
        }

        @NonNull
        @Override
        public RecipeDetailsDirectionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View v = inflater.inflate(R.layout.direction_list_item, parent, false);
            return new RecipeDetailsDirectionHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull RecipeDetailsDirectionHolder holder, int position) {

            holder.directionNumber.setText(mDirectionList.get(position).getDirectionNumber() + ". ");
            holder.directionDescription.setText(mDirectionList.get(position).getDirectionDescription());

        }

        @Override
        public int getItemCount() {
            return mDirectionList.size();
        }
    }

    private class RecipeDetailsHolder extends RecyclerView.ViewHolder {
        CheckBox mCheckBox;

        public RecipeDetailsHolder(View itemView) {
            super(itemView);
            getItemView(itemView);
        }

        public View getItemView(View itemView) {
            mCheckBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            return itemView;
        }
    }

    private class RecipeDetailsAdapter extends RecyclerView.Adapter<RecipeDetailsHolder> {

        private List<Ingredient> mRecipeIngredientList;

        public RecipeDetailsAdapter(List<Ingredient> recipesIngredient) {
            mRecipeIngredientList = recipesIngredient;
        }

        @NonNull
        @Override
        public RecipeDetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View v = layoutInflater.inflate(R.layout.ingredient_list_item, parent, false);
            return new RecipeDetailsHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull RecipeDetailsHolder holder, int position) {
            holder.mCheckBox.setText(mRecipeIngredientList.get(position).getFoodName());
        }

        @Override
        public int getItemCount() {
            return mRecipeIngredientList.size();
        }
    }


    private void searchRecipeById(final Long recipe_id, final int page_num) {

        try {
            //String url = mFatSecretSearch.searchFood(item, page_num);
            String oauth_signature = mFatSecretSearch.searchRecipeById(recipe_id, page_num);
            String oauth_consumer_key = mFatSecretSearch.getoauth_consumer_key();
            String oauth_signature_method = mFatSecretSearch.getoauth_signature_method();
            String oauth_timestamp = mFatSecretSearch.getoauth_timestamp();
            String oauth_nonce = mFatSecretSearch.getoauth_nonce();
            String oauth_version = mFatSecretSearch.getoauth_version();
            String format = mFatSecretSearch.getformat();
            String method = mFatSecretSearch.getmethod();
            mSecretApi.getRecipeDetailsById(format, method, oauth_consumer_key
                    , oauth_nonce, oauth_signature, oauth_signature_method,
                    oauth_timestamp, oauth_version, recipe_id).enqueue(recipeDetailsCallback);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    Callback<RecipeDetail> recipeDetailsCallback = new Callback<RecipeDetail>() {
        @Override
        public void onResponse(Call<RecipeDetail> call, Response<RecipeDetail> response) {
            RecipeDetail recipeDetail = response.body();
            Log.i("body response recipeDetailsCallback", response.body() + "");
            Recipe_ recipe_obj = recipeDetail.getRecipe();
            toolbarLayout.setTitle(recipe_obj.getRecipeName());
            description.setText(recipe_obj.getRecipeDescription());
            listOfIngredient.addAll(recipe_obj.getIngredients().getIngredient());
            rvIngredientAdapter.notifyDataSetChanged();

            listOfDirections.addAll(recipe_obj.getDirections().getDirection());
            rvDirectionsAdapter.notifyDataSetChanged();

            txt_cooking_time.setText(recipe_obj.getCookingTimeMin() + " m");
            txt_preparation_time.setText(recipe_obj.getPreparationTimeMin() + " m");
            txt_servings_number.setText(recipe_obj.getNumberOfServings());
//            Picasso.with(
//                    getApplicationContext())
//                    .load(recipe_obj.getRecipeImages().getRecipeImage())
//                    .fit()
//                    .centerCrop()
//                    .into(imagetoolbar);
        }

        @Override
        public void onFailure(Call<RecipeDetail> call, Throwable t) {
            Log.i("body fail", call.request() + "Fail");
            t.printStackTrace();
        }
    };


}
