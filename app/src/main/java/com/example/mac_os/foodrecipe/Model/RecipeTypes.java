package com.example.mac_os.foodrecipe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipeTypes {
    @SerializedName("recipe_types")
    @Expose
    private RecipeTypesDetails mRecipeTypesDetails;

    public RecipeTypesDetails getRecipeTypes() {
        return mRecipeTypesDetails;
    }

    public void setRecipeTypes(RecipeTypesDetails recipeTypesDetails) {
        this.mRecipeTypesDetails = recipeTypesDetails;
    }
}
