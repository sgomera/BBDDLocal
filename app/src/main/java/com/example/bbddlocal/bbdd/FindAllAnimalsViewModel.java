package com.example.bbddlocal.bbdd;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class FindAllAnimalsViewModel extends AndroidViewModel {

    public final LiveData<List<Animal>> animals;

    private AppDatabase mDb;

    public FindAllAnimalsViewModel(@NonNull Application application) {
        super(application);
        createDb();

        animals = mDb.animalDao().findAllAnimals();

    }

    public void createDb() {
        mDb = AppDatabase.getInMemoryDatabase(this.getApplication());

    }


}
