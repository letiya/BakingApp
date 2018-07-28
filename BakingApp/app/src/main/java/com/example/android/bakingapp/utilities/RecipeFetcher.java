package com.example.android.bakingapp.utilities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.database.AppDatabase;

import java.net.URL;

public class RecipeFetcher extends AppCompatActivity implements LoaderManager.LoaderCallbacks {

    private Context mContext;
    private AppDatabase mDb;

    public RecipeFetcher(Context context) {
        mContext = context;
        mDb = AppDatabase.getInstance(mContext);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader(mContext) {

            @Override
            protected void onStartLoading() {
                forceLoad();
            }

            @Override
            public Object loadInBackground() {
                URL recipeRequestUrl = NetworkUtils.buildRecipeUrl(mContext);
                String jsonRecipeResponse = NetworkUtils.getResponseFromHttpUrl(recipeRequestUrl);
                RecipeJsonUtils.parseRecipeJson(jsonRecipeResponse, mDb);
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}