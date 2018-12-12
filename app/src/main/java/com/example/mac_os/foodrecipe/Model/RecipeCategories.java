
package com.example.mac_os.foodrecipe.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipeCategories {

    @SerializedName("recipe_category")
    @Expose
    private List<RecipeCategory> recipeCategory = null;

    public List<RecipeCategory> getRecipeCategory() {
        return recipeCategory;
    }

    public void setRecipeCategory(List<RecipeCategory> recipeCategory) {
        this.recipeCategory = recipeCategory;
    }

}
