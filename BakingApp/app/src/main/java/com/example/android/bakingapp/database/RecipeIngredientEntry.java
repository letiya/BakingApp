package com.example.android.bakingapp.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

//@Entity(tableName = "recipe_ingredients", foreignKeys = @ForeignKey(entity = RecipeEntry.class, parentColumns = "recipeId", childColumns = "recipeId", onDelete = CASCADE))
@Entity(tableName = "recipe_ingredients")
public class RecipeIngredientEntry {

    @PrimaryKey(autoGenerate = true)
    private int recipeId;
    private float quantity;
    private String measure;
    private String ingredient;

    public RecipeIngredientEntry(int recipeId, float quantity, String measure, String ingredient) {
        this.recipeId = recipeId;
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getMeasure() {
        return measure;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getIngredient() {
        return ingredient;
    }
}
