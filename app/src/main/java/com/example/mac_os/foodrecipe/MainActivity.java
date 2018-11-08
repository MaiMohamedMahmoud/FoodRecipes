package com.example.mac_os.foodrecipe;
//
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.example.mac_os.foodrecipe.Model.Food_;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_type_list);
        GridView gridView = (GridView) findViewById(R.id.recipe_type_layout);
        gridView.setAdapter(new MyAdapter(this));
    }
}