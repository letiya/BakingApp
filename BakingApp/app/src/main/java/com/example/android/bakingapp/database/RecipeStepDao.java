package com.example.android.bakingapp.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface RecipeStepDao {

    @Query("SELECT * FROM recipe_steps ORDER BY recipeId")
    List<RecipeStepEntry> loadAllSteps();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStep(RecipeStepEntry recipeStepEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateStep(RecipeStepEntry recipeStepEntry);

    @Delete
    void deleteStep(RecipeStepEntry recipeStepEntry);
}
