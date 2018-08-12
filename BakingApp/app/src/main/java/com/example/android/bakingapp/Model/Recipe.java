package com.example.android.bakingapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable {

    private int mRecipeId;
    private String mRecipeName;
    private Ingredient[] mIngredients;
    private Step[] mSteps;
    private int mServings;
    private String mImage;

    public Recipe() {

    }

    public Recipe(int recipeId, String recipeName, Ingredient[] ingredients, Step[] steps, int servings, String image) {
        mRecipeId = recipeId;
        mRecipeName = recipeName;
        mIngredients = ingredients;
        mSteps = steps;
        mServings = servings;
        mImage = image;
    }

    protected Recipe(Parcel in) {
        mRecipeId = in.readInt();
        mRecipeName = in.readString();
        mIngredients = in.createTypedArray(Ingredient.CREATOR);
        mSteps = in.createTypedArray(Step.CREATOR);
        mServings = in.readInt();
        mImage = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public void setRecipeId(int recipeId) {
        mRecipeId = recipeId;
    }

    public int getRecipeId() {
        return mRecipeId;
    }

    public void setRecipeName(String recipeName) {
        mRecipeName = recipeName;
    }

    public String getRecipeName() {
        return mRecipeName;
    }

    public void setIngredients(Ingredient[] ingredients) {
        mIngredients = ingredients;
    }

    public Ingredient[] getIngredients() {
        return mIngredients;
    }

    public void setSteps(Step[] steps) {
        mSteps = steps;
    }

    public Step[] getSteps() {
        return mSteps;
    }

    public void setServings(int servings) {
        mServings = servings;
    }

    public int getServings() {
        return mServings;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getImage() {
        return mImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mRecipeId);
        dest.writeString(mRecipeName);
        dest.writeTypedArray(mIngredients, 0);
        dest.writeTypedArray(mSteps, 0);
        dest.writeInt(mServings);
        dest.writeString(mImage);
    }
}
