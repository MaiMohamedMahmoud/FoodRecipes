package com.example.mac_os.foodrecipe;

import android.app.ProgressDialog;
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
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mac_os.foodrecipe.Model.Food_;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class FoodList extends AppCompatActivity {

    RecyclerView rvListFood;
    FatSecretSearchFood mFatSecretSearch;
    private List<Food_> foodList;
    RecyclerView.Adapter foodAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_list);
        rvListFood = (RecyclerView) findViewById(R.id.food_list);
        rvListFood.setLayoutManager(new LinearLayoutManager(this));
        foodList = new ArrayList<>();
        foodAdapter = new FoodAdapter(foodList);
        rvListFood.setAdapter(foodAdapter);
        mFatSecretSearch = new FatSecretSearchFood();
        searchFood("soup", 1);

    }

    private void searchFood(final String item, final int page_num) {
        new AsyncTask<String, String, String>() {
            @Override
            protected void onPreExecute() {

            }

            @Override
            protected String doInBackground(String... arg0) {

                JSONObject foodOBJ = null;
                try {
                    foodOBJ = mFatSecretSearch.searchFood(item, page_num);
                    Log.i("MAI", foodOBJ.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                JSONArray FOODS_ARRAY = null;
                if (foodOBJ != null) {
                    try {
                        FOODS_ARRAY = foodOBJ.getJSONArray("food");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (FOODS_ARRAY != null) {
                        for (int i = 0; i < FOODS_ARRAY.length(); i++) {
                            Log.i("LENGHT", FOODS_ARRAY.length() + "");
                            JSONObject food_items = FOODS_ARRAY.optJSONObject(i);
                            try {
                                Food_ food = new Food_();
                                food.setFoodName(food_items.get("food_name").toString());
                                food.setFoodDescription(food_items.get("food_description").toString());
                                food.setFoodType(food_items.get("food_type").toString());
                                foodList.add(food);
                                Log.i("OOOOh", food_items.get("food_description").toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
                return "";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Log.i("Mai", result);
                foodAdapter.notifyDataSetChanged();
                if (result.equals("Error"))
                    Toast.makeText(getApplicationContext(), "No Items Containing Your Search", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

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

    private class FoodAdapter extends RecyclerView.Adapter<FoodHolder> {

        private List<Food_> mFoodList;

        public FoodAdapter(List<Food_> foods) {
            mFoodList = foods;
        }


        @NonNull
        @Override
        public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View v = layoutInflater.inflate(R.layout.food_list_item, parent, false);
            return new FoodHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull FoodHolder holder, int position) {
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
