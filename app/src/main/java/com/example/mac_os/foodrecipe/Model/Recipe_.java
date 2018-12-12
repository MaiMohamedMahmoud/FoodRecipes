
package com.example.mac_os.foodrecipe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recipe_ {

    @SerializedName("recipe_description")
    @Expose
    private String recipeDescription;
    @SerializedName("recipe_id")
    @Expose
    private Long recipeId;
    @SerializedName("recipe_image")
    @Expose
    private String recipeImage;
    @SerializedName("recipe_name")
    @Expose
    private String recipeName;
    @SerializedName("recipe_url")
    @Expose
    private String recipeUrl;
    @SerializedName("cooking_time_min")
    @Expose
    private String cookingTimeMin;
    @SerializedName("directions")
    @Expose
    private Directions directions;
    @SerializedName("ingredients")
    @Expose
    private Ingredients ingredients;
    @SerializedName("number_of_servings")
    @Expose
    private String numberOfServings;
    @SerializedName("preparation_time_min")
    @Expose
    private String preparationTimeMin;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("recipe_categories")
    @Expose
    private RecipeCategories recipeCategories;
    @SerializedName("serving_sizes")
    @Expose
    private ServingSizes servingSizes;
    @SerializedName("recipe_types")
    @Expose
    private RecipeTypes recipeTypes;
//    @SerializedName("recipe_images")
//    @Expose
//    private RecipeImages recipeImages;


    public String getCookingTimeMin() {
        return cookingTimeMin;
    }

    public void setCookingTimeMin(String cookingTimeMin) {
        this.cookingTimeMin = cookingTimeMin;
    }

    public Directions getDirections() {
        return directions;
    }

    public void setDirections(Directions directions) {
        this.directions = directions;
    }

    public Ingredients getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredients ingredients) {
        this.ingredients = ingredients;
    }

    public String getNumberOfServings() {
        return numberOfServings;
    }

    public void setNumberOfServings(String numberOfServings) {
        this.numberOfServings = numberOfServings;
    }

    public String getPreparationTimeMin() {
        return preparationTimeMin;
    }

    public void setPreparationTimeMin(String preparationTimeMin) {
        this.preparationTimeMin = preparationTimeMin;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public RecipeCategories getRecipeCategories() {
        return recipeCategories;
    }

    public void setRecipeCategories(RecipeCategories recipeCategories) {
        this.recipeCategories = recipeCategories;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }

    public RecipeTypes getRecipeTypes() {
        return recipeTypes;
    }

    public void setRecipeTypes(RecipeTypes recipeTypes) {
        this.recipeTypes = recipeTypes;
    }

    public ServingSizes getServingSizes() {
        return servingSizes;
    }

    public void setServingSizes(ServingSizes servingSizes) {
        this.servingSizes = servingSizes;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeUrl() {
        return recipeUrl;
    }

    public void setRecipeUrl(String recipeUrl) {
        this.recipeUrl = recipeUrl;
    }

//    public RecipeImages getRecipeImages() {
//        return recipeImages;
//    }
//
//    public void setRecipeImages(RecipeImages recipeImages) {
//        this.recipeImages = recipeImages;
//    }
}
