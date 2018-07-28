package com.example.android.bakingapp.utilities;

import com.example.android.bakingapp.database.AppDatabase;
import com.example.android.bakingapp.database.RecipeEntry;
import com.example.android.bakingapp.database.RecipeIngredientEntry;
import com.example.android.bakingapp.database.RecipeStepEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecipeJsonUtils {

    private static final String JSON_RECIPE_ID = "id";
    private static final String JSON_RECIPE_NAME = "name";
    private static final String JSON_INGREDIENTS = "ingredients";

    private static final String JSON_QUANTITY = "quantity";
    private static final String JSON_MEASURE = "measure";
    private static final String JSON_INGREDIENT = "ingredient";

    private static final String JSON_STEPS = "steps";
    private static final String JSON_STEP_ID = "id";
    private static final String JSON_SHORT_DESCRIPTION = "shortDescription";
    private static final String JSON_DESCRIPTION = "description";
    private static final String JSON_VIDEO_URL = "videoURL";
    private static final String JSON_THUMBNAIL_URL = "thumbnailURL";

    private static final String JSON_SERVINGS = "servings";
    private static final String JSON_IMAGE = "image";

    private static AppDatabase mDb;

    public static void parseRecipeJson(String json, AppDatabase appDatabase) {
        if (json == null) {
            return;
        }
        mDb = appDatabase;
        try {
            JSONArray recipesArray = new JSONArray(json);
            if (recipesArray != null) {
                int numberOfRecipes = recipesArray.length();
                for (int i = 0; i < numberOfRecipes; i++) {
                    JSONObject recipeObject = recipesArray.getJSONObject(i);
                    getRecipeAttributes(recipeObject);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void getRecipeAttributes(JSONObject recipeObject) {
        if (recipeObject != null) {
            try {
                int recipeId = Integer.parseInt(recipeObject.getString(JSON_RECIPE_ID));
                String recipeName = recipeObject.getString(JSON_RECIPE_NAME);

                JSONArray ingredientsArray = recipeObject.getJSONArray(JSON_INGREDIENTS);
                if (ingredientsArray != null) {
                    for (int i = 0; i < ingredientsArray.length(); i++) {
                        JSONObject ingredientObject = ingredientsArray.getJSONObject(i);
                        float quantity = Float.parseFloat(ingredientObject.getString(JSON_QUANTITY));
                        String measure = ingredientObject.getString(JSON_MEASURE);
                        String ingredient = ingredientObject.getString(JSON_INGREDIENT);
                        // Update table, recipeIngredientEntry.
                        final RecipeIngredientEntry recipeIngredientEntry = new RecipeIngredientEntry(recipeId, quantity, measure, ingredient);

                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                mDb.recipeIngredientDao().insertIngredient(recipeIngredientEntry);
                            }
                        });
                    }
                }

                JSONArray stepsArray = recipeObject.getJSONArray(JSON_STEPS);
                if (stepsArray != null) {
                    for (int i = 0; i < stepsArray.length(); i++) {
                        JSONObject stepObject = stepsArray.getJSONObject(i);
                        int stepId = Integer.parseInt(stepObject.getString(JSON_STEP_ID));
                        String shortDescription = stepObject.getString(JSON_SHORT_DESCRIPTION);
                        String description = stepObject.getString(JSON_DESCRIPTION);
                        String videoURL = stepObject.getString(JSON_VIDEO_URL);
                        String thumbnailURL = stepObject.getString(JSON_THUMBNAIL_URL);
                        // Update table, recipeStepEntry.
                        final RecipeStepEntry recipeStepEntry = new RecipeStepEntry(recipeId, stepId, shortDescription, description, videoURL, thumbnailURL);

                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                mDb.recipeStepDao().insertStep(recipeStepEntry);
                            }
                        });
                    }
                }

                int servings = Integer.parseInt(recipeObject.getString(JSON_SERVINGS));
                String image = recipeObject.getString(JSON_IMAGE);
                // Update table, recipeEntry.
                final RecipeEntry recipeEntry = new RecipeEntry(recipeId, recipeName, servings, image);

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mDb.recipeDao().insertRecipe(recipeEntry);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
