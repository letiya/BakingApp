package com.example.android.bakingapp.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface RecipeIngredientDao {

    @Query("SELECT * FROM recipe_ingredients ORDER BY recipeId")
    List<RecipeIngredientEntry> loadAllIngredients();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertIngredient(RecipeIngredientEntry recipeIngredientEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateIngredient(RecipeIngredientEntry recipeIngredientEntry);

    @Delete
    void deleteIngredient(RecipeIngredientEntry recipeIngredientEntry);
}
