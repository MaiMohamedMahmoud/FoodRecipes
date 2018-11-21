package com.example.mac_os.foodrecipe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeTypesDetails {

    @SerializedName("recipe_type")
    @Expose
    private List<String> recipeTypes = null;

    public List<String> getRecipeTypesDetails() {
        return recipeTypes;
    }

    public void setRecipeTypesDetails(List<String> recipeTypes) {
        this.recipeTypes = recipeTypes;
    }

}
