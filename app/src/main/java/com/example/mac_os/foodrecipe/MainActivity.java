package com.example.mac_os.foodrecipe;
//

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mac_os.foodrecipe.Model.RecipeTypes;
import com.example.mac_os.foodrecipe.Model.RecipeTypesDetails;
import com.example.mac_os.foodrecipe.Model.RecipeTypesItemImage;
import com.example.mac_os.foodrecipe.data.FatSecretApi;
import com.example.mac_os.foodrecipe.data.Utility.ApiUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//
//public class MainActivity extends AppCompatActivity {
//    FatSecretSearchFood mFatSecretSearch;
//    private List<Food_> foodList;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        foodList = new ArrayList<>();
//        mFatSecretSearch = new FatSecretSearchFood();
//        searchFood("soup",1);
//    }
//
//    private void searchFood(final String item, final int page_num) {
//        new AsyncTask<String, String, String>() {
//            @Override
//            protected void onPreExecute() {
//
//            }
//
//            @Override
//            protected String doInBackground(String... arg0) {
//                JSONObject foodOBJ = null;
//                try {
//                    foodOBJ = mFatSecretSearch.searchFood(item, page_num);
//                    Log.i("MAI",foodOBJ.toString());
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
//                            Log.i("LENGHT", FOODS_ARRAY.length()+"");
//                            JSONObject food_items = FOODS_ARRAY.optJSONObject(i);
//                            try {
//                                Food_ food = new Food_();
//                                food.setFoodName(food_items.get("food_name").toString());
//                                food.setFoodDescription(food_items.get("food_description").toString());
//                                food.setFoodType(food_items.get("food_type").toString());
//                                foodList.add(food);
//                                Log.i("OOOOh",food_items.get("food_description").toString());
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }
//                return "";
//            }
//
//            @Override
//            protected void onPostExecute(String result) {
//                super.onPostExecute(result);
//                Log.i("Mai",result);
//                if (result.equals("Error"))
//                    Toast.makeText(getApplicationContext(), "No Items Containing Your Search", Toast.LENGTH_SHORT).show();
//
//            }
//        }.execute();
//    }
//
//}


public class MainActivity extends AppCompatActivity {

    FatSecretSearchFood mFatSecretSearch;
    private FatSecretApi mSecretApi;

    private List<String> recipesTypesList;
    GridView gridView;
    String SearchItemKeyWord;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_type_list);
        recipesTypesList = new ArrayList<>();
        mSecretApi = ApiUtils.getFoodService();
        mFatSecretSearch = new FatSecretSearchFood();
        getRecipeType();

        gridView = (GridView) findViewById(R.id.recipe_type_layout);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent fatsecretIntent = new Intent(MainActivity.this, FatSecretSearchFoodRetrofit.class);
                    fatsecretIntent.putExtra("SearchItemKeyWord",recipesTypesList.get(position));
                    startActivity(fatsecretIntent);


//                Toast.makeText(getApplicationContext(),"aaaa"+recipesTypesList.get(position),Toast.LENGTH_LONG).show();
            }
        });

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

            RecipeTypes recipeTypesObj = response.body();
            RecipeTypesDetails recipeTypesDetailsObj = recipeTypesObj.getRecipeTypes();
            recipesTypesList.addAll(recipeTypesDetailsObj.getRecipeTypesDetails());
            gridView.setAdapter(new RecipeTypeAdapter(getApplicationContext(), recipesTypesList));



        }

        @Override
        public void onFailure(Call<RecipeTypes> call, Throwable t) {
            Log.i("body fail", call.request() + "Fail");
            t.printStackTrace();
        }
    };
}