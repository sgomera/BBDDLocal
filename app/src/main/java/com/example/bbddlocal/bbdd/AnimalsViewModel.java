package com.example.bbddlocal.bbdd;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;


import java.util.List;

public class AnimalsViewModel extends AndroidViewModel {

    private Repository mRepository;
    public final LiveData<List<Animal>> mAllAnimals;

    private String mAnimalName;
   // public LiveData<List<Animal>> mSearchedAnimals;

    MutableLiveData<List<Animal>> mSearchedAnimals = new MutableLiveData<>();


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


    private void refresh(String animalName) {
        mAnimalName = animalName;
        String formattedSearchText = "%" + mAnimalName + "%";
        new GetSearchDataTask.execute(formattedSearchText);
    }

    //for Async Work
    private class GetSearchDataTask extends AsyncTask<Void, Void, List<Animal>> {

        @Override
        protected List<Animal> doInBackground(String... params) {
            return mRepository.getAllAnimals(params[0]);
        }

        @Override
        protected void onPostExecute(List<Animal> animals) {
            super.onPostExecute(animals);
            mSearchedAnimals.setValue(animals); //change LiveData value
        }
    }




    //TODO create factory subclass. See Basic sample example

    //TODO review https://stackoverflow.com/questions/55076046/room-livedata-viewmodel-search-by-name-strange-filtering-behavior
}
