package com.example.bbddlocal.bbdd;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.bbddlocal.utils.DatabaseInitializer;

import java.util.Date;


public class InsertAnimalViewModel extends AndroidViewModel {

    public Animal animal;
    private AppDatabase mDb;

    public InsertAnimalViewModel(@NonNull Application application) {
        super(application);
        //com viatgen les dades desde la classe al viewmodel??
       // animal = mDb.animalModel().insertAnimal();

    }

    public void createDb() {
        mDb = AppDatabase.getInMemoryDatabase(this.getApplication());

        // Populate it with initial data
        DatabaseInitializer.populateAsync(mDb);
    }

    public void InsertAnimal(Animal animal){
        mDb.animalModel().insertAnimal(animal);
    }

}
