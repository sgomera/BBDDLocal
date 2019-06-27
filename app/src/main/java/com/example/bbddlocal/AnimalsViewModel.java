package com.example.bbddlocal;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.bbddlocal.utils.DatabaseInitializer;

import java.util.List;

public class AnimalsViewModel extends AndroidViewModel {

    public final LiveData<List<Animal>> animals;

    private AppDatabase mDb;

    public AnimalsViewModel(@NonNull Application application) {
        super(application);

        animals = mDb.animalModel().findAnimalByName("Nemo");

    }

    public void createDb() {
        mDb = AppDatabase.getInMemoryDatabase(this.getApplication());

        // Populate it with initial data
        DatabaseInitializer.populateAsync(mDb);
    }
}
