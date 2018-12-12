
package com.example.mac_os.foodrecipe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeImages {

    @SerializedName("recipe_image")
    @Expose
    private List<String> recipeImage;

    public List<String> getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(List<String> recipeImage) {
        this.recipeImage = recipeImage;
    }

}
