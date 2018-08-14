package com.example.android.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.Model.Recipe;
import com.example.android.bakingapp.Model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailAdapter.RecipeDetailAdapterViewHolder> {

    private Context mContext;

    private String[] mRecipeDetailData;
    private void getRecipeDetailData() {
        mRecipeDetailData = new String[mClickedRecipe.getSteps().length];

        // Steps
        Step[] steps = mClickedRecipe.getSteps();
        for (int i = 0; i < steps.length; i++) {
            Step step = steps[i];
            mRecipeDetailData[i] = step.getShortDescription();
        }
    }

    public RecipeDetailAdapter(RecipeDetailAdapterOnClickHandler handler) {
        mClickHandler = handler;
    }

    private Recipe mClickedRecipe;
    public void setClickedRecipeData(Recipe recipe) {
        mClickedRecipe = recipe;
//        notifyDataSetChanged();
    }

    @Override
    public RecipeDetailAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_detail_list_items;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new RecipeDetailAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeDetailAdapterViewHolder holder, int position) {
        getRecipeDetailData();
        String shortDescription = "Step " + position + ": " + mRecipeDetailData[position];
        holder.mRecipeDetailTextView.setText(shortDescription);
    }

    @Override
    public int getItemCount() {
        return mClickedRecipe.getSteps().length;
    }

    public class RecipeDetailAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_recipe_detail_name)
        TextView mRecipeDetailTextView;

        public RecipeDetailAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Step step = mClickedRecipe.getSteps()[adapterPosition];
            mClickHandler.onClick(step);
        }
    }

    private final RecipeDetailAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface RecipeDetailAdapterOnClickHandler {
        void onClick(Step step);
    }
}
