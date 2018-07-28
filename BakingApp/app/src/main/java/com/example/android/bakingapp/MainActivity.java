package com.example.android.bakingapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.example.android.bakingapp.utilities.MainViewModel;
import com.example.android.bakingapp.utilities.RecipeFetcher;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview_recipe) RecyclerView mRecyclerView;

    private RecipeAdapter mRecipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview_recipe);
        GridLayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecipeAdapter = new RecipeAdapter();
        mRecyclerView.setAdapter(mRecipeAdapter);

        ButterKnife.bind(this);

        RecipeFetcher recipeFetcher = new RecipeFetcher(this);
        getSupportLoaderManager().initLoader(0, null, recipeFetcher);

        setupViewModel();
    }

    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getRecipeNames().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> recipeNameList) {
                String[] recipeNameArray = new String[recipeNameList.size()];
                for (int i = 0; i < recipeNameList.size(); i++) {
                    recipeNameArray[i] = recipeNameList.get(i);
                }
                mRecipeAdapter.setRecipeNames(recipeNameArray);
            }
        });
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
}
