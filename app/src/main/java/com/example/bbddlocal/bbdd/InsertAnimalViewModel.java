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

        getDb();

        //com viatgen les dades desde la classe al viewmodel??
       // animal = mDb.animalModel().insertAnimal();

    }

    public void getDb() {
        mDb = AppDatabase.getInMemoryDatabase(this.getApplication());
    }

    public void InsertAnimal(Animal animal){
        mDb.animalModel().insertAnimal(animal);
    }

}
