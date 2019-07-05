package com.example.bbddlocal.bbdd;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;


public class InsertAnimalViewModel extends AndroidViewModel {

    /*public Animal animal;
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
    }*/




        private Repository mRepository;

        private LiveData<List<Animal>> mAllAnimals;

        public InsertAnimalViewModel (Application application) {
            super(application);
            mRepository = new Repository(application);
            mAllAnimals = mRepository.getAllAnimals();
        }

        LiveData<List<Animal>> getAllAnimals() { return mAllAnimals; }

        public void insert(Animal animal) { mRepository.insert(animal); }

}
