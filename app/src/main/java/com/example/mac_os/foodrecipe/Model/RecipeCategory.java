
package com.example.mac_os.foodrecipe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipeCategory {

    @SerializedName("recipe_category_name")
    @Expose
    private String recipeCategoryName;
    @SerializedName("recipe_category_url")
    @Expose
    private String recipeCategoryUrl;

    public String getRecipeCategoryName() {
        return recipeCategoryName;
    }

    public void setRecipeCategoryName(String recipeCategoryName) {
        this.recipeCategoryName = recipeCategoryName;
    }

    public String getRecipeCategoryUrl() {
        return recipeCategoryUrl;
    }

    public void setRecipeCategoryUrl(String recipeCategoryUrl) {
        this.recipeCategoryUrl = recipeCategoryUrl;
    }

}
