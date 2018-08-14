package com.example.android.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.Model.Ingredient;
import com.example.android.bakingapp.Model.Recipe;
import com.example.android.bakingapp.Model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFragment extends Fragment implements RecipeDetailAdapter.RecipeDetailAdapterOnClickHandler {

    private Context mContext;

    @BindView(R.id.tv_recipe_ingredient)
    TextView mIngredient;

    @BindView(R.id.recyclerview_recipe_detail)
    RecyclerView mRecyclerView;

    private RecipeDetailAdapter mRecipeDetailAdapter;

    private final String TAG_CLICKED_RECIPE = "clickedRecipe";
    private final String TAG_CLICKED_STEP = "clickedStep";
    private final String TAG_TABLET = "isTablet";

    private boolean mTablet;

    @BindView(R.id.iv_image_recipe)
    ImageView mFavoriteImageView;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the Recipe Detail fragment layout
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        mContext = rootView.getContext();

        Intent intentThatStartedThisActivity = getActivity().getIntent();
        final Recipe clickedRecipe = intentThatStartedThisActivity.getParcelableExtra(TAG_CLICKED_RECIPE);
        mTablet = intentThatStartedThisActivity.getBooleanExtra(TAG_TABLET, false);

        ButterKnife.bind(this, rootView);

        // Ingredients
        Ingredient[] ingredients = clickedRecipe.getIngredients();
        String ingredientSentence = "";
        for (int i = 0; i < ingredients.length; i++) {
            Ingredient ingredient = ingredients[i];
            ingredientSentence += "+  " + ingredient.getQuantity() + " " + ingredient.getMeasure() + " " + ingredient.getIngredient();
            if (i != ingredients.length - 1) {
                ingredientSentence += System.lineSeparator();
            }
        }
        mIngredient.setText(ingredientSentence);

        // Step 1 ~ Step n
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecipeDetailAdapter = new RecipeDetailAdapter(this);
        mRecipeDetailAdapter.setClickedRecipeData(clickedRecipe);
        mRecyclerView.setAdapter(mRecipeDetailAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);






        final String finalIngredientSentence = ingredientSentence;

        mFavoriteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RecipeWidgetProvider.class);
                intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
                int[] ids = appWidgetManager.getAppWidgetIds(new ComponentName(mContext, RecipeWidgetProvider.class));

                RecipeWidgetProvider.updateRecipeWidget(mContext, appWidgetManager, finalIngredientSentence, ids);
            }
        });






        return rootView;
    }

    @Override
    public void onClick(Step step) {
        mCallback.OnRecipeStepClicked(step);
    }



    OnRecipeStepClickListener mCallback;

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnRecipeStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnRecipeStepClickListener");
        }
    }

    public interface OnRecipeStepClickListener {
        void OnRecipeStepClicked(Step step);
    }
}

