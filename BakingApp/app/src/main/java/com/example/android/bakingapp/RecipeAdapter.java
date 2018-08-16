package com.example.android.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.Model.Recipe;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

    private Context mContext;
    private final RecipeAdapterOnClickHandler mClickHandler;
    private Recipe[] mRecipeData;

    public RecipeAdapter(RecipeAdapterOnClickHandler handler) {
        mClickHandler = handler;
    }

    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_list_items;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeAdapterViewHolder holder, int position) {
        Recipe recipe = mRecipeData[position];
        String recipeName = recipe.getRecipeName();
        holder.mRecipeTextView.setText(recipeName);

        String recipeImage = recipe.getImage();
        if (recipeImage != null && recipeImage.length() > 0) {
            Picasso.with(mContext).load(recipeImage).into(holder.mRecipeImageView);
        }
    }

    @Override
    public int getItemCount() {
        if (mRecipeData == null) {
            return 0;
        }
        return mRecipeData.length;
    }

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_recipe_name) TextView mRecipeTextView;

        @BindView(R.id.iv_image_recipe)
        ImageView mRecipeImageView;

        public RecipeAdapterViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Recipe recipe = mRecipeData[adapterPosition];
            mClickHandler.onClick(recipe);
        }
    }

    /**
     * The interface that receives onClick messages.
     */
    public interface RecipeAdapterOnClickHandler {
        void onClick(Recipe recipeClicked);
    }

    public void setRecipeData(Recipe[] recipes) {
        mRecipeData = recipes;
        notifyDataSetChanged();
    }
}
