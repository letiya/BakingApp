package com.example.android.bakingapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipe_master ORDER BY recipeId")
    List<RecipeEntry> loadAllRecipes();

    @Query("SELECT recipeName FROM recipe_master ORDER BY recipeId")
    LiveData<List<String>> loadAllRecipeNames();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipe(RecipeEntry recipeEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRecipe(RecipeEntry recipeEntry);

    @Delete
    void deleteRecipe(RecipeEntry recipeEntry);
}
