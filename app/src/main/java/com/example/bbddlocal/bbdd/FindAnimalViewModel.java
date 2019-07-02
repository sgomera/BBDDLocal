package com.example.bbddlocal.bbdd;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class FindAnimalViewModel extends AndroidViewModel {

    public LiveData<List<Animal>> animals;

    private AppDatabase mDb;

    public FindAnimalViewModel(@NonNull Application application, String name) {
        super(application);

        animals = mDb.animalModel().findAnimalByName(name);
    }
}
