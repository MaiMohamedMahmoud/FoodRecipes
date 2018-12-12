package com.example.mac_os.foodrecipe.data;

import com.example.mac_os.foodrecipe.Model.Food;
import com.example.mac_os.foodrecipe.Model.Food_;
import com.example.mac_os.foodrecipe.Model.Foods;
import com.example.mac_os.foodrecipe.Model.Recipe;
import com.example.mac_os.foodrecipe.Model.RecipeTypes;
import com.example.mac_os.foodrecipe.Model.RecipeDetail;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface FatSecretApi {


    //  http://platform.fatsecret.com/rest/server.api?format=json&max_results=20&method=foods.search&
    // oauth_consumer_key=e7c5644c6e59405eb10d3e5b215bb28d&
    // oauth_nonce=97108971049910999&
    // oauth_signature=zuoWq6AyG2z%2FGTyVxav%2BSAc02xE%3D
    // &oauth_signature_method=HMAC-SHA1&
    // oauth_timestamp=3081651030674&
    // oauth_version=1.0&page_number=1
    // &search_expression=soup

    @GET("rest/server.api")
    Call<Food> getFoodBySearch(@Query("format") String format,
                               @Query("max_results") String max_results,
                               @Query("method") String method,
                               @Query("oauth_consumer_key") String oauth_consumer_key,
                               @Query("oauth_nonce") String oauth_nonce,
                               @Query(value = "oauth_signature", encoded = true) String oauth_signature,
                               @Query("oauth_signature_method") String oauth_signature_method,
                               @Query("oauth_timestamp") String oauth_timestamp,
                               @Query("oauth_version") String oauth_version,
                               @Query("page_number") String page_number,
                               @Query(value = "search_expression", encoded = true) String search_expression);

    @GET("rest/server.api")
    Call<Recipe> getRecipesBySearch(@Query("format") String format,
                                    @Query("max_results") String max_results,
                                    @Query("method") String method,
                                    @Query("oauth_consumer_key") String oauth_consumer_key,
                                    @Query("oauth_nonce") String oauth_nonce,
                                    @Query(value = "oauth_signature", encoded = true) String oauth_signature,
                                    @Query("oauth_signature_method") String oauth_signature_method,
                                    @Query("oauth_timestamp") String oauth_timestamp,
                                    @Query("oauth_version") String oauth_version,
                                    @Query("page_number") String page_number,
                                    @Query(value = "recipe_type", encoded = true) String search_expression);

    @GET("rest/server.api")
    Call<RecipeTypes> getRecipeType(@Query("format") String format,
                                    @Query("method") String method,
                                    @Query("oauth_consumer_key") String oauth_consumer_key,
                                    @Query("oauth_nonce") String oauth_nonce,
                                    @Query(value = "oauth_signature", encoded = true) String oauth_signature,
                                    @Query("oauth_signature_method") String oauth_signature_method,
                                    @Query("oauth_timestamp") String oauth_timestamp,
                                    @Query("oauth_version") String oauth_version);


    @GET("rest/server.api")
    Call<RecipeDetail> getRecipeDetailsById(@Query("format") String format,
                                             @Query("method") String method,
                                             @Query("oauth_consumer_key") String oauth_consumer_key,
                                             @Query("oauth_nonce") String oauth_nonce,
                                             @Query(value = "oauth_signature", encoded = true) String oauth_signature,
                                             @Query("oauth_signature_method") String oauth_signature_method,
                                             @Query("oauth_timestamp") String oauth_timestamp,
                                             @Query("oauth_version") String oauth_version,
                                             @Query(value = "recipe_id", encoded = true) Long recipe_id);

}
