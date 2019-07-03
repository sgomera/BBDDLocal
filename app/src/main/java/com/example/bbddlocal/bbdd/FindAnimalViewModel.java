package com.example.bbddlocal.bbdd;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;



import java.util.List;


public class FindAnimalViewModel extends AndroidViewModel {

    public LiveData<List<Animal>> animals;

    private AppDatabase mDb;


    //this works but searching for all the animals, not only the one with the name the user entered:
    public  FindAnimalViewModel(@NonNull Application application) {
        super(application);
        createDb();

        animals = mDb.animalModel().findAllAnimals();

    }



    public void createDb() {
        mDb = AppDatabase.getInMemoryDatabase(this.getApplication());

        // It is already populated with the initial data, since the mainActivity
     //   DatabaseInitializer.populateAsync(mDb);
    }

}
