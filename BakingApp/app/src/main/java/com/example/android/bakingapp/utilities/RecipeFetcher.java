package com.example.android.bakingapp.utilities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.Model.Recipe;
import com.example.android.bakingapp.RecipeAdapter;

import java.net.URL;

public class RecipeFetcher implements LoaderManager.LoaderCallbacks<Recipe[]> {

    private Context mContext;
    private RecipeAdapter mRecipeAdapter;

    public RecipeFetcher(Context context, RecipeAdapter recipeAdapter) {
        mContext = context;
        mRecipeAdapter = recipeAdapter;
    }

    @Override
    public Loader<Recipe[]> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Recipe[]>(mContext) {

            @Override
            protected void onStartLoading() {
                forceLoad();
            }

            @Override
            public Recipe[] loadInBackground() {
                URL recipeRequestUrl = NetworkUtils.buildRecipeUrl(mContext);
                String jsonRecipeResponse = NetworkUtils.getResponseFromHttpUrl(recipeRequestUrl);
                return RecipeJsonUtils.parseRecipeJson(jsonRecipeResponse);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Recipe[]> loader, Recipe[] recipes) {
        mRecipeAdapter.setRecipeData(recipes);
    }

    @Override
    public void onLoaderReset(Loader<Recipe[]> loader) {

    }
}