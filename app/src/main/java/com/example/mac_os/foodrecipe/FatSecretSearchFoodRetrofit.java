package com.example.mac_os.foodrecipe;

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
import com.example.mac_os.foodrecipe.data.FatSecretApi;
import com.example.mac_os.foodrecipe.data.Utility.ApiUtils;
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
    private FatSecretApi mSecretApi;
    RecyclerView.Adapter foodAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_list);
        rvListFood = (RecyclerView) findViewById(R.id.food_list);
        rvListFood.setLayoutManager(new LinearLayoutManager(this));
        foodList = new ArrayList<>();
        foodAdapter = new FatSecretSearchFoodRetrofit.FoodAdapter(foodList);
        rvListFood.setAdapter(foodAdapter);
        mSecretApi = ApiUtils.getFoodService();
        mFatSecretSearch = new FatSecretSearchFood();
        searchFood("soup", 1);
        //mSecretApi.getFoodBySearch().enqueue(foodsCallback);

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
}
