package com.example.android.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.example.android.bakingapp.Model.Recipe;
import com.example.android.bakingapp.utilities.RecipeFetcher;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler {

    private final String TAG_CLICKED_RECIPE = "clickedRecipe";
    private final String TAG_TABLET = "isTablet";

    @BindView(R.id.recyclerview_recipe) RecyclerView mRecyclerView;
    private RecipeAdapter mRecipeAdapter;

    private boolean mTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview_recipe);

        RecyclerView.LayoutManager layoutManager;

        if (findViewById(R.id.main_linear_layout) != null) {
            // Cell phone
            mTablet = false;
            layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        } else {
            // Tablet
            mTablet = true;
            layoutManager = new GridLayoutManager(this, numberOfColumns());
        }

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecipeAdapter = new RecipeAdapter(this);
        mRecyclerView.setAdapter(mRecipeAdapter);

        ButterKnife.bind(this);

        RecipeFetcher recipeFetcher = new RecipeFetcher(this, mRecipeAdapter);
        getSupportLoaderManager().initLoader(0, null, recipeFetcher);

    }

    /**
     * Dynamically calculate the number of columns and the layout would adapt to the screen size and orientation
     * @return number of columns in the grid for GridLayoutManager.
     */
    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the recipe cards
        int widthDivider = 300;
        int width = displayMetrics.widthPixels;
        int numOfColumns = width / widthDivider;
        if (numOfColumns < 2) {
            return 2;
        }
        return numOfColumns;
    }

    @Override
    public void onClick(Recipe clickedRecipe) {
        Intent intentToStartDetailActivity = new Intent(this, DetailActivity.class);
        intentToStartDetailActivity.putExtra(TAG_CLICKED_RECIPE, clickedRecipe);
        intentToStartDetailActivity.putExtra(TAG_TABLET, mTablet);
        startActivity(intentToStartDetailActivity);
    }
}
