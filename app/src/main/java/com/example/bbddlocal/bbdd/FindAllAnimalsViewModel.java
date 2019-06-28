package com.example.bbddlocal.bbdd;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.bbddlocal.utils.DatabaseInitializer;

import java.util.List;

public class FindAllAnimalsViewModel extends AndroidViewModel {

  //TODO uncomment when using viewmodel
  //  public final LiveData<List<Animal>> animals;

    private AppDatabase mDb;

    public FindAllAnimalsViewModel(@NonNull Application application) {
        super(application);
    //TODO uncomment when using viewmodel
   //     animals = mDb.animalModel().findAllAnimals();

    }

    public void createDb() {
        mDb = AppDatabase.getInMemoryDatabase(this.getApplication());

        // Populate it with initial data
        DatabaseInitializer.populateAsync(mDb);
    }
}
