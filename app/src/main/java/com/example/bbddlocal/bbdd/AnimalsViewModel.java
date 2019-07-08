package com.example.bbddlocal.bbdd;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class AnimalsViewModel extends AndroidViewModel {

    private Repository mRepository;
    public final LiveData<List<Animal>> mAllAnimals;


    public AnimalsViewModel(@NonNull Application application) {
        super(application);

        mRepository = new Repository(application);
        mAllAnimals = mRepository.getAllAnimals();

    }

    LiveData<List<Animal>> getAllWords() {
        return mAllAnimals;
    }

    public void insert(Animal animal) {
        mRepository.insert(animal);
    }


}
