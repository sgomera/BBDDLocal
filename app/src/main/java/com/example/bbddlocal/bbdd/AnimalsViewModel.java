package com.example.bbddlocal.bbdd;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.NonNull;


import java.util.List;

public class AnimalsViewModel extends AndroidViewModel {

    private Repository mRepository;
    public final LiveData<List<Animal>> mAllAnimals;

    private String mAnimalName;
    public LiveData<List<Animal>> mSearchedAnimals;

    public AnimalsViewModel(@NonNull Application application
    ) {
        super(application);

        mRepository = new Repository(application);
        mAllAnimals = mRepository.getAllAnimals();


    }

    public LiveData<List<Animal>> getAllAnimals() {
        return mAllAnimals;
    }

    public LiveData<List<Animal>> getByName(String name) {
        mAnimalName = name;
        mSearchedAnimals = mRepository.getAnimalsByName(mAnimalName);
        return mSearchedAnimals;

    }

    public void insert(Animal animal) {
        mRepository.insert(animal);
    }


    //TODO create factory subclass. See Basic sample example
}
