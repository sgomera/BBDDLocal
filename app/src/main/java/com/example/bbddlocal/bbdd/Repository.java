package com.example.bbddlocal.bbdd;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.bbddlocal.bbdd.Animal;
import com.example.bbddlocal.bbdd.AnimalDao;
import com.example.bbddlocal.bbdd.AppDatabase;

import java.util.List;

public class Repository {
    private AnimalDao animalDao;
    private LiveData<List<Animal>> mAllAnimals;

    public Repository(Application application) {
        AppDatabase db = AppDatabase.getInMemoryDatabase(application);
        animalDao = db.animalDao();
        mAllAnimals = animalDao.findAllAnimals();
    }

    public LiveData<List<Animal>> getAllAnimals() {
        return mAllAnimals;
    }

    public LiveData<List<Animal>> getAnimalsByName(String name) {
        return mAllAnimals;
    }


    public void insert (Animal animal) {
        new insertAsyncTask(animalDao).execute(animal);
    }

    private static class insertAsyncTask extends AsyncTask<Animal, Void, Void> {

        private AnimalDao mAsyncTaskDao;

        insertAsyncTask(AnimalDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Animal... params) {
            mAsyncTaskDao.insertAnimal(params[0]);
            return null;
        }
    }

}
