package com.example.bbddlocal.bbdd;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;


import java.util.List;

public class Repository {
    private AnimalDao animalDao;
    private LiveData<List<Animal>> mAllAnimals;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public Repository(Application application) {
        AppDatabase db = AppDatabase.getInMemoryDatabase(application);
        animalDao = db.animalDao();
        mAllAnimals = animalDao.findAllAnimals();
    }
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Animal>> getAllAnimals() {
        return mAllAnimals;
    }

    public LiveData<List<Animal>> getAnimalsByName(String name) {
        return mAllAnimals;
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
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
