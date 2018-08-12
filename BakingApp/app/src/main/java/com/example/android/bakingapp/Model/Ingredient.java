package com.example.android.bakingapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {

    private float mQuantity;
    private String mMeasure;
    private String mIngredient;

    public Ingredient() {

    }

    public Ingredient(float quantity, String measure, String ingredient) {
        mQuantity = quantity;
        mMeasure = measure;
        mIngredient = ingredient;
    }

    protected Ingredient(Parcel in) {
        mQuantity = in.readFloat();
        mMeasure = in.readString();
        mIngredient = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public void setQuantity(float quantity) {
        mQuantity = quantity;
    }

    public float getQuantity() {
        return mQuantity;
    }

    public void setMeasure(String measure) {
        mMeasure = measure;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public void setIngredient(String ingredient) {
        mIngredient = ingredient;
    }

    public String getIngredient() {
        return mIngredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(mQuantity);
        dest.writeString(mMeasure);
        dest.writeString(mIngredient);
    }
}
