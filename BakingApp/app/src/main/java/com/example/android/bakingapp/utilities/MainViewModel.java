package com.example.android.bakingapp.utilities;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.android.bakingapp.database.AppDatabase;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<String>> recipeNameListLiveData;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getInstance(this.getApplication());
        recipeNameListLiveData = appDatabase.recipeDao().loadAllRecipeNames();
    }

    public LiveData<List<String>> getRecipeNames() {
        return recipeNameListLiveData;
    }
}
