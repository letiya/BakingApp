package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.Model.Recipe;
import com.example.android.bakingapp.Model.Step;

public class DetailActivity extends AppCompatActivity implements DetailFragment.OnRecipeStepClickListener {

    private final String TAG_CLICKED_RECIPE = "clickedRecipe";
    private final String TAG_CLICKED_STEP = "clickedStep";
    private final String TAG_TABLET = "isTablet";
    private boolean mTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intentThatStartedThisActivity = getIntent();
        Recipe clickedRecipe = intentThatStartedThisActivity.getParcelableExtra(TAG_CLICKED_RECIPE);
        mTablet = intentThatStartedThisActivity.getBooleanExtra(TAG_TABLET, false);

        if (savedInstanceState == null) {
            // Ingredient + Step 1 ~ Step n
            DetailFragment detailFragment = new DetailFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.detail_container, detailFragment)
                    .commit();

            if (mTablet) {
                // Assign an initial step.
                intentThatStartedThisActivity.putExtra(TAG_CLICKED_STEP, clickedRecipe.getSteps()[0]);

                // Video + Step description
                StepDetailFragment stepDetailFragment = new StepDetailFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.step_detail_container, stepDetailFragment)
                        .commit();

            }
        }
    }

    @Override
    public void OnRecipeStepClicked(Step step) {
        if (mTablet) {
            Intent intentThatStartedThisActivity = getIntent();
            intentThatStartedThisActivity.putExtra(TAG_CLICKED_STEP, step);

            StepDetailFragment newFragment = new StepDetailFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.step_detail_container, newFragment)
                    .commit();
        } else {
            Intent intentToStartStepDetailActivity = new Intent(this, StepDetailActivity.class);
            intentToStartStepDetailActivity.putExtra(TAG_CLICKED_STEP, step);
            startActivity(intentToStartStepDetailActivity);
        }
    }
}
