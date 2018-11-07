
package com.example.mac_os.foodrecipe.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recipes {

    @SerializedName("max_results")
    @Expose
    private String maxResults;
    @SerializedName("page_number")
    @Expose
    private String pageNumber;
    @SerializedName("recipe")
    @Expose
    private List<Recipe_> recipe = null;
    @SerializedName("total_results")
    @Expose
    private String totalResults;

    public String getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(String maxResults) {
        this.maxResults = maxResults;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public List<Recipe_> getRecipe() {
        return recipe;
    }

    public void setRecipe(List<Recipe_> recipe) {
        this.recipe = recipe;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

}
