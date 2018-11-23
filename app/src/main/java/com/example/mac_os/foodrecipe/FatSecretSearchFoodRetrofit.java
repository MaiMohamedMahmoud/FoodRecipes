package com.example.mac_os.foodrecipe;

import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.mac_os.foodrecipe.Model.Food;
import com.example.mac_os.foodrecipe.Model.Food_;
import com.example.mac_os.foodrecipe.Model.Foods;
import com.example.mac_os.foodrecipe.Model.Recipe;
import com.example.mac_os.foodrecipe.Model.RecipeTypes;
import com.example.mac_os.foodrecipe.Model.RecipeTypesDetails;
import com.example.mac_os.foodrecipe.Model.Recipe_;
import com.example.mac_os.foodrecipe.Model.Recipes;
import com.example.mac_os.foodrecipe.data.FatSecretApi;
import com.example.mac_os.foodrecipe.data.Utility.ApiUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
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
    String SearchItemKeyWord;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_list);
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

//    private void searchFood(final String item, final int page_num) {
//        new AsyncTask<String, String, String>() {
//            @Override
//            protected void onPreExecute() {
//
//            }
//
//            @Override
//            protected String doInBackground(String... arg0) {
//
//                JSONObject foodOBJ = null;
//                try {
//                    foodOBJ = mFatSecretSearch.searchFood(item, page_num);
//                    Log.i("MAI", foodOBJ.toString());
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//                JSONArray FOODS_ARRAY = null;
//                if (foodOBJ != null) {
//                    try {
//                        FOODS_ARRAY = foodOBJ.getJSONArray("food");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    if (FOODS_ARRAY != null) {
//                        for (int i = 0; i < FOODS_ARRAY.length(); i++) {
//                            Log.i("LENGHT", FOODS_ARRAY.length() + "");
//                            JSONObject food_items = FOODS_ARRAY.optJSONObject(i);
//                            try {
//                                Food_ food = new Food_();
//                                food.setFoodName(food_items.get("food_name").toString());
//                                food.setFoodDescription(food_items.get("food_description").toString());
//                                food.setFoodType(food_items.get("food_type").toString());
//                                foodList.add(food);
//                                Log.i("OOOOh", food_items.get("food_description").toString());
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                    }
//                }
//                return "";
//            }
//
//            @Override
//            protected void onPostExecute(String result) {
//                super.onPostExecute(result);
//                Log.i("Mai", result);
//                foodAdapter.notifyDataSetChanged();
//                if (result.equals("Error"))
//                    Toast.makeText(getApplicationContext(), "No Items Containing Your Search", Toast.LENGTH_SHORT).show();
//            }
//        }.execute();
//    }

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
            String page_number = mFatSecretSearch.getpage_number(page_num);
            mSecretApi.getRecipesBySearch(format, max_results, method, oauth_consumer_key
                    , oauth_nonce, oauth_signature, oauth_signature_method,
                    oauth_timestamp, oauth_version, page_number, search_expression).enqueue(recipeCallback);


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    private void getRecipeType() {
        try {
            String oauth_signature = mFatSecretSearch.getRecipeType();
            String oauth_consumer_key = mFatSecretSearch.getoauth_consumer_key();
            String oauth_signature_method = mFatSecretSearch.getoauth_signature_method();
            String oauth_timestamp = mFatSecretSearch.getoauth_timestamp();
            String oauth_nonce = mFatSecretSearch.getoauth_nonce();
            String oauth_version = mFatSecretSearch.getoauth_version();
            String format = mFatSecretSearch.getformat();
            String max_results = mFatSecretSearch.getmax_results();
            String method = mFatSecretSearch.getmethod();
            mSecretApi.getRecipeType(format, method, oauth_consumer_key
                    , oauth_nonce, oauth_signature, oauth_signature_method,
                    oauth_timestamp, oauth_version).enqueue(recipeTypeCallback);


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    Callback<RecipeTypes> recipeTypeCallback = new Callback<RecipeTypes>() {

        @Override
        public void onResponse(Call<RecipeTypes> call, Response<RecipeTypes> response) {
            Log.i("body call", call.request() + "");
            Log.i("body response types", response.body() + "");
            RecipeTypes recipeTypesObj = response.body();
            RecipeTypesDetails recipeTypesDetailsObj = recipeTypesObj.getRecipeTypes();
            recipesTypesList.addAll(recipeTypesDetailsObj.getRecipeTypesDetails());

            Log.i("recipe types", recipesTypesList.size() + "");
            Log.i("recipe types obj", recipesTypesList.get(0) + "");
        }

        @Override
        public void onFailure(Call<RecipeTypes> call, Throwable t) {
            Log.i("body fail", call.request() + "Fail");
            t.printStackTrace();
        }
    };


    Callback<Recipe> recipeCallback = new Callback<Recipe>() {
        @Override
        public void onResponse(Call<Recipe> call, Response<Recipe> response) {

            Log.i("body call", call.request() + "");
            Log.i("body response recipe", response.body() + "");
            Toast.makeText(getApplicationContext(), "gowa el fun", Toast.LENGTH_LONG).show();
            Recipe recipeObj = response.body();
            if (response != null) {
                Recipes recipesDetailsObj = recipeObj.getRecipes();
                Toast.makeText(getApplicationContext(), "gowa el if el 2ola", Toast.LENGTH_LONG).show();
                if (recipesDetailsObj != null) {
                    JsonElement jsonElementRecipes = recipesDetailsObj.getRecipe();
                    Toast.makeText(getApplicationContext(), "gowa el if el tanya", Toast.LENGTH_LONG).show();

                    if (jsonElementRecipes != null) {

                        if (jsonElementRecipes.isJsonObject()) {
                            JsonObject ss = jsonElementRecipes.getAsJsonObject();
                            Recipe_ obj_recipe = new Recipe_();

                            obj_recipe.setRecipeDescription(ss.get("recipe_description").toString());
                            obj_recipe.setRecipeId(ss.get("recipe_id").toString());
                            obj_recipe.setRecipeImage(ss.get("recipe_image").toString());
                            obj_recipe.setRecipeName(ss.get("recipe_name").toString());
                            obj_recipe.setRecipeUrl(ss.get("recipe_url").toString());
                            recipesList.add(obj_recipe);
                        } else if (jsonElementRecipes.isJsonArray()) {
                            JsonArray jss = jsonElementRecipes.getAsJsonArray();

                            List<Recipe_> recipe_s_obj = new ArrayList<>();
                            for (int i = 0; i < jss.size(); i++) {
                                JsonObject ss = (JsonObject) jss.get(i);
                                 Toast.makeText(getApplicationContext(), "gowa"+ss.get("recipe_description").toString() , Toast.LENGTH_LONG).show();

                                Recipe_ obj_recipe = new Recipe_();

                                obj_recipe.setRecipeDescription(ss.get("recipe_description").toString());
                                obj_recipe.setRecipeId(ss.get("recipe_id").toString());
                                // obj_recipe.setRecipeImage(ss.get("recipe_image").toString());
                                obj_recipe.setRecipeName(ss.get("recipe_name").toString());
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
                Toast.makeText(getApplicationContext(), "gowa el else", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), R.string.no_recipes, Toast.LENGTH_LONG).show();
                finish();

            }
        }

        @Override
        public void onFailure(Call<Recipe> call, Throwable t) {
            Log.i("body fail", call.request() + "Fail");
            Toast.makeText(getApplicationContext(), "gowa el fail" + call.request(), Toast.LENGTH_LONG).show();
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

    private class FoodHolder extends RecyclerView.ViewHolder {

        TextView foodDescription;
        TextView foodName;
        TextView foodType;

        public FoodHolder(View itemView) {
            super(itemView);
            getItemView(itemView);
        }

        private View getItemView(View itemView) {
            foodDescription = (TextView) itemView.findViewById(R.id.food_description);
            foodName = (TextView) itemView.findViewById(R.id.food_name);
            foodType = (TextView) itemView.findViewById(R.id.food_type);
            return itemView;
        }
    }

    private class FoodAdapter extends RecyclerView.Adapter<FatSecretSearchFoodRetrofit.FoodHolder> {

        private List<Food_> mFoodList;

        public FoodAdapter(List<Food_> foods) {
            mFoodList = foods;
        }


        @NonNull
        @Override
        public FatSecretSearchFoodRetrofit.FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View v = layoutInflater.inflate(R.layout.food_list_item, parent, false);
            return new FatSecretSearchFoodRetrofit.FoodHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull FatSecretSearchFoodRetrofit.FoodHolder holder, int position) {
            Log.i("Desc", mFoodList.get(position).getFoodDescription());
            holder.foodDescription.setText(mFoodList.get(position).getFoodDescription());
            holder.foodName.setText(mFoodList.get(position).getFoodName());
            holder.foodType.setText(mFoodList.get(position).getFoodType());
        }


        @Override
        public int getItemCount() {
            return mFoodList.size();
        }
    }

    private class RecipeAdapter extends RecyclerView.Adapter<FatSecretSearchFoodRetrofit.FoodHolder> {

        private List<Recipe_> mRecipeList;

        public RecipeAdapter(List<Recipe_> recipes) {
            mRecipeList = recipes;
        }

        @NonNull
        @Override
        public FatSecretSearchFoodRetrofit.FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View v = layoutInflater.inflate(R.layout.food_list_item, parent, false);
            return new FatSecretSearchFoodRetrofit.FoodHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull FatSecretSearchFoodRetrofit.FoodHolder holder, int position) {
            Log.i("Desc", mRecipeList.get(position).getRecipeDescription());
            holder.foodDescription.setText(mRecipeList.get(position).getRecipeDescription());
            holder.foodName.setText(mRecipeList.get(position).getRecipeName());
            holder.foodType.setText(mRecipeList.get(position).getRecipeUrl());
        }


        @Override
        public int getItemCount() {
            return mRecipeList.size();
        }
    }
}
