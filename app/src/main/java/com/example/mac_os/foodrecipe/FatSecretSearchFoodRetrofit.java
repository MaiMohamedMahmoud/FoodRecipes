package com.example.mac_os.foodrecipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.mac_os.foodrecipe.Model.Food;
import com.example.mac_os.foodrecipe.Model.Food_;
import com.example.mac_os.foodrecipe.Model.Foods;
import com.example.mac_os.foodrecipe.Model.PicassoCircleTransformation;
import com.example.mac_os.foodrecipe.Model.Recipe;
import com.example.mac_os.foodrecipe.Model.Recipe_;
import com.example.mac_os.foodrecipe.Model.Recipes;
import com.example.mac_os.foodrecipe.data.FatSecretApi;
import com.example.mac_os.foodrecipe.data.Utility.ApiUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FatSecretSearchFoodRetrofit extends AppCompatActivity {
    final static private String APP_METHOD = "GET";
    final static private String APP_KEY = "e7c5644c6e59405eb10d3e5b215bb28d";
    final static private String APP_SECRET = "d510bf3fe2e14aa4b673105505ff4f0e";
    final static private String APP_URL = "http://platform.fatsecret.com/rest/server.api";
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    RecyclerView rvListFood;
    FatSecretSearchFood mFatSecretSearch;
    //private List<Food_> foodDetailsList;
    private List<Food_> foodList;
    private List<Recipe_> recipesList;
    private List<String> recipesTypesList;
    private FatSecretApi mSecretApi;
    RecyclerView.Adapter foodAdapter;
    RecyclerView.Adapter RecipeAdapter;
    BounceInterpolator bounceInterpolator;
    ScaleAnimation scaleAnimation;

    String SearchItemKeyWord;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);
        rvListFood = (RecyclerView) findViewById(R.id.food_list);
        rvListFood.setLayoutManager(new LinearLayoutManager(this));
        foodList = new ArrayList<>();
        recipesList = new ArrayList<>();
        recipesTypesList = new ArrayList<>();
        foodAdapter = new FatSecretSearchFoodRetrofit.FoodAdapter(foodList);
        RecipeAdapter = new FatSecretSearchFoodRetrofit.RecipeAdapter(recipesList);
        Bundle extras = getIntent().getExtras();
        String searchItem = extras.getString("SearchItemKeyWord");
        Toast.makeText(getApplicationContext(), searchItem, Toast.LENGTH_LONG).show();
        rvListFood.setAdapter(foodAdapter);
        rvListFood.setAdapter(RecipeAdapter);
        mSecretApi = ApiUtils.getFoodService();
        mFatSecretSearch = new FatSecretSearchFood();
        //searchFood("soup", 1);
        searchRecipe(searchItem, 1);

    }

    private void searchFood(final String item, final int page_num) {

        try {
            //String url = mFatSecretSearch.searchFood(item, page_num);
            String oauth_signature = mFatSecretSearch.searchFood(item, page_num);
            String oauth_consumer_key = mFatSecretSearch.getoauth_consumer_key();
            String oauth_signature_method = mFatSecretSearch.getoauth_signature_method();
            String oauth_timestamp = mFatSecretSearch.getoauth_timestamp();
            String oauth_nonce = mFatSecretSearch.getoauth_nonce();
            String oauth_version = mFatSecretSearch.getoauth_version();
            String format = mFatSecretSearch.getformat();
            String search_expression = mFatSecretSearch.getsearch_expression(item);
            String max_results = mFatSecretSearch.getmax_results();
            String method = mFatSecretSearch.getmethod();
            String page_number = mFatSecretSearch.getpage_number(page_num);
            mSecretApi.getFoodBySearch(format, max_results, method, oauth_consumer_key
                    , oauth_nonce, oauth_signature, oauth_signature_method,
                    oauth_timestamp, oauth_version, page_number, search_expression).enqueue(foodsCallback);


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    private void searchRecipe(final String item, final int page_num) {

        try {
            //String url = mFatSecretSearch.searchFood(item, page_num);
            String oauth_signature = mFatSecretSearch.searchRecipe(item, page_num);
            String oauth_consumer_key = mFatSecretSearch.getoauth_consumer_key();
            String oauth_signature_method = mFatSecretSearch.getoauth_signature_method();
            String oauth_timestamp = mFatSecretSearch.getoauth_timestamp();
            String oauth_nonce = mFatSecretSearch.getoauth_nonce();
            String oauth_version = mFatSecretSearch.getoauth_version();
            String format = mFatSecretSearch.getformat();
            String search_expression = mFatSecretSearch.getsearch_expression(item);
            String max_results = mFatSecretSearch.getmax_results();
            String method = mFatSecretSearch.getmethod();
            String recipe_type = mFatSecretSearch.getrecipe_type(item);
            String page_number = mFatSecretSearch.getpage_number(page_num);
            mSecretApi.getRecipesBySearch(format, max_results, method, oauth_consumer_key
                    , oauth_nonce, oauth_signature, oauth_signature_method,
                    oauth_timestamp, oauth_version, page_number, recipe_type).enqueue(recipeCallback);


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    Callback<Recipe> recipeCallback = new Callback<Recipe>() {
        @Override
        public void onResponse(Call<Recipe> call, Response<Recipe> response) {

            Log.i("body call", call.request() + "");
            Log.i("body response recipe", response.body() + "");
            Recipe recipeObj = response.body();
            if (response != null) {
                Recipes recipesDetailsObj = recipeObj.getRecipes();
                if (recipesDetailsObj != null) {
                    JsonElement jsonElementRecipes = recipesDetailsObj.getRecipe();

                    if (jsonElementRecipes != null) {

                        if (jsonElementRecipes.isJsonObject()) {
                            JsonObject ss = jsonElementRecipes.getAsJsonObject();
                            Recipe_ obj_recipe = new Recipe_();

                            obj_recipe.setRecipeDescription(ss.get("recipe_description").getAsString());
                            obj_recipe.setRecipeId(ss.get("recipe_id").getAsLong());
                            if (ss.get("recipe_image").getAsString() != null) {
                                obj_recipe.setRecipeImage(ss.get("recipe_image").getAsString());
                            } else {
                                obj_recipe.setRecipeImage("data:image/png;base64,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");
                            }
                            obj_recipe.setRecipeName(ss.get("recipe_name").getAsString());
                            obj_recipe.setRecipeUrl(ss.get("recipe_url").getAsString());
                            recipesList.add(obj_recipe);
                        } else if (jsonElementRecipes.isJsonArray()) {
                            JsonArray jss = jsonElementRecipes.getAsJsonArray();

                            List<Recipe_> recipe_s_obj = new ArrayList<>();
                            for (int i = 0; i < jss.size(); i++) {
                                JsonObject ss = (JsonObject) jss.get(i);

                                Recipe_ obj_recipe = new Recipe_();

                                obj_recipe.setRecipeDescription(ss.get("recipe_description").getAsString());
                                obj_recipe.setRecipeId(ss.get("recipe_id").getAsLong());
                                // obj_recipe.setRecipeImage(ss.get("recipe_image").toString());
                                if (ss.get("recipe_image").getAsString() != null) {
                                    obj_recipe.setRecipeImage(ss.get("recipe_image").getAsString());
                                } else {
                                    obj_recipe.setRecipeImage("data:image/png;base64,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");
                                }
                                obj_recipe.setRecipeName(ss.get("recipe_name").getAsString());
                                obj_recipe.setRecipeUrl(ss.get("recipe_url").toString());
                                recipesList.add(obj_recipe);
                            }
                        }
                        // recipesList.addAll(recipesDetailsObj.getRecipe());
                        RecipeAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.no_recipes, Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
                //  Log.i("body obj", recipesList.get(0).getRecipeName() + "");
                else {
                    Toast.makeText(getApplicationContext(), R.string.no_recipes, Toast.LENGTH_LONG).show();
                    finish();
                }

            } else {
                Toast.makeText(getApplicationContext(), R.string.no_recipes, Toast.LENGTH_LONG).show();
                finish();

            }
        }

        @Override
        public void onFailure(Call<Recipe> call, Throwable t) {
            Log.i("body fail", call.request() + "Fail");
            t.printStackTrace();
        }
    };

    Callback<Food> foodsCallback = new Callback<Food>() {
        @Override
        public void onResponse(Call<Food> call, Response<Food> response) {
            Log.i("body call", call.request() + "");
            Food foodObj = response.body();
            Log.i("body res", foodObj.getFoods().getMaxResults() + "");
            Foods foodsDetailsObj = foodObj.getFoods();
            foodList.addAll(foodsDetailsObj.getFood());
            Log.i("body obj", foodList.get(0).getFoodType() + "");
//            for (int i = 0; i < foodList.size(); i++) {
//                Food_ foodListDetailObj = new Food_();
//                foodListDetailObj.setFoodType(foodList.get(i).getFoodType());
//                foodListDetailObj.setFoodName(foodList.get(i).getFoodName());
//                foodListDetailObj.setFoodDescription(foodList.get(i).getFoodDescription());
//                foodDetailsList.add(foodListDetailObj);
//            }
            foodAdapter.notifyDataSetChanged();
        }

        @Override
        public void onFailure(Call<Food> call, Throwable t) {
            Log.i("body fail", call.request() + "Fail");

            t.printStackTrace();
        }
    };

    private class RecipeHolder extends RecyclerView.ViewHolder {
        SquareImageView square_image_recipe_item;
        TextView recipeDescription;
        TextView recipeName;
        ToggleButton buttonFavorite;

        public RecipeHolder(View itemView) {
            super(itemView);
            getItemView(itemView);
        }

        private View getItemView(View itemView) {
            recipeDescription = (TextView) itemView.findViewById(R.id.food_description);
            recipeName = (TextView) itemView.findViewById(R.id.food_name);
            square_image_recipe_item = (SquareImageView) itemView.findViewById(R.id.square_image_recipe_item);
            buttonFavorite = (ToggleButton) itemView.findViewById(R.id.button_favorite);
            return itemView;
        }
    }

    private class FoodAdapter extends RecyclerView.Adapter<RecipeHolder> {

        private List<Food_> mFoodList;

        public FoodAdapter(List<Food_> foods) {
            mFoodList = foods;
        }


        @NonNull
        @Override
        public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View v = layoutInflater.inflate(R.layout.recipe_list_item, parent, false);
            return new RecipeHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull RecipeHolder holder, int position) {
            Log.i("Desc", mFoodList.get(position).getFoodDescription());
            holder.recipeDescription.setText(mFoodList.get(position).getFoodDescription());
            holder.recipeName.setText(mFoodList.get(position).getFoodName());
        }


        @Override
        public int getItemCount() {
            return mFoodList.size();
        }
    }

    private class RecipeAdapter extends RecyclerView.Adapter<RecipeHolder> {

        private List<Recipe_> mRecipeList;

        public RecipeAdapter(List<Recipe_> recipes) {
            mRecipeList = recipes;
        }

        @NonNull
        @Override
        public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View v = layoutInflater.inflate(R.layout.recipe_list_item, parent, false);
            return new RecipeHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull RecipeHolder holder, final int position) {
            Log.i("Image", mRecipeList.get(position).getRecipeImage());
            holder.recipeDescription.setText(mRecipeList.get(position).getRecipeDescription());
            holder.recipeName.setText(mRecipeList.get(position).getRecipeName() + " ");
            scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
            scaleAnimation.setDuration(500);
            bounceInterpolator = new BounceInterpolator();
            scaleAnimation.setInterpolator(bounceInterpolator);
            holder.buttonFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    //animation
                    compoundButton.startAnimation(scaleAnimation);
                }
            });
            Picasso.with(
                    getApplicationContext())
                    .load(mRecipeList.get(position).getRecipeImage())
                    .fit()
                    .centerCrop()
                    .transform(new PicassoCircleTransformation())
                    .into(holder.square_image_recipe_item);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(FatSecretSearchFoodRetrofit.this, RecipeDetails.class);
                    intent.putExtra("recipeId",mRecipeList.get(position).getRecipeId());
                    startActivity(intent);
                   // searchRecipeById(mRecipeList.get(position).getRecipeId(), 1);
                }
            });
        }


        @Override
        public int getItemCount() {
            return mRecipeList.size();
        }
    }
}
