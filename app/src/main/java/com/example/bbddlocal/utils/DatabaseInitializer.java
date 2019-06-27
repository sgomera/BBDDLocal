package com.example.bbddlocal.utils;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.bbddlocal.Animal;
import com.example.bbddlocal.AppDatabase;

import java.util.Calendar;
import java.util.Date;


public class DatabaseInitializer {

    public static void populateAsync(final AppDatabase db) {

        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

   /* public static void populateSync(@NonNull final AppDatabase db) {
        populateWithTestData(db);
    }*/


    private static Animal addAnimal(final AppDatabase db,
                                    final int id,
                                    final String name,
                                    final int age,
                                    final boolean isChipped,
                                    final String animalType,
                        //            final Date regDate,
                                    final String photo
                                    ) {

        Animal animal = new Animal();
        animal.id = id;
        animal.name = name;
        animal.age = age;
        animal.isChipped = isChipped;
        animal.animalType = animalType;
   //     animal.regDate = regDate; //TODO @TypeConverter
        animal.photo = photo;

        db.animalModel().insertAnimal(animal);
        return animal;
    }

    private static void populateWithTestData(AppDatabase db) {
        db.animalModel().deleteAll();

        Animal animal1 = addAnimal(db, 1,"Teddy", 3, true, "dog","photoTeedy");
        Animal animal2 = addAnimal(db, 2,"Nemo", 1, false, "fish","photoNemo");
        Animal animal3 = addAnimal(db, 2,"Catty", 2, true, "cat","photoCatty");

    }

    private static Date getTodayPlusDays(int daysAgo) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, daysAgo);
        return calendar.getTime();
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;

        PopulateDbAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(mDb);
            return null;
        }

    }
}
