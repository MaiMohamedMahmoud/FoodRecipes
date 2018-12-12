
package com.example.mac_os.foodrecipe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipeDetail {

    @SerializedName("recipe")
    @Expose
    private Recipe_ recipe;

    public Recipe_ getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe_ recipe) {
        this.recipe = recipe;
    }

}
