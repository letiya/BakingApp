package com.example.android.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

    private Context mContext;
    private String[] mRecipeNames;

    public RecipeAdapter() {
        mRecipeNames = new String[2];
        mRecipeNames[0] = "recipe 1";
        mRecipeNames[1] = "recipe 2";
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
        String recipe = mRecipeNames[position];
        holder.mRecipeTextView.setText(recipe);
    }

    @Override
    public int getItemCount() {
        return mRecipeNames.length;
    }

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_recipe_name) TextView mRecipeTextView;

        public RecipeAdapterViewHolder(View itemView) {
            super(itemView);
            mRecipeTextView = itemView.findViewById(R.id.tv_recipe_name);
        }
    }
}
