
package com.example.mac_os.foodrecipe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ingredient {

    @SerializedName("food_id")
    @Expose
    private String foodId;
    @SerializedName("food_name")
    @Expose
    private String foodName;
    @SerializedName("ingredient_description")
    @Expose
    private String ingredientDescription;
    @SerializedName("ingredient_url")
    @Expose
    private String ingredientUrl;
    @SerializedName("measurement_description")
    @Expose
    private String measurementDescription;
    @SerializedName("number_of_units")
    @Expose
    private String numberOfUnits;
    @SerializedName("serving_id")
    @Expose
    private String servingId;

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getIngredientDescription() {
        return ingredientDescription;
    }

    public void setIngredientDescription(String ingredientDescription) {
        this.ingredientDescription = ingredientDescription;
    }

    public String getIngredientUrl() {
        return ingredientUrl;
    }

    public void setIngredientUrl(String ingredientUrl) {
        this.ingredientUrl = ingredientUrl;
    }

    public String getMeasurementDescription() {
        return measurementDescription;
    }

    public void setMeasurementDescription(String measurementDescription) {
        this.measurementDescription = measurementDescription;
    }

    public String getNumberOfUnits() {
        return numberOfUnits;
    }

    public void setNumberOfUnits(String numberOfUnits) {
        this.numberOfUnits = numberOfUnits;
    }

    public String getServingId() {
        return servingId;
    }

    public void setServingId(String servingId) {
        this.servingId = servingId;
    }

}
