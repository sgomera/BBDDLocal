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
        getDb();

        animals = mDb.animalDao().findAllAnimals();

    }



    public void getDb() {
        mDb = AppDatabase.getInMemoryDatabase(this.getApplication());

    }

}
