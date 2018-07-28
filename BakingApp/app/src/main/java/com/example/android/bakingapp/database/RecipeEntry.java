package com.example.android.bakingapp.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "recipe_master")
public class RecipeEntry {

    @PrimaryKey(autoGenerate = true)
    private int recipeId;
    private String recipeName;
    private int servings;
    private String image;

    public RecipeEntry(int recipeId, String recipeName, int servings, String image) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.servings = servings;
        this.image = image;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public int getServings() {
        return servings;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}
