package com.example.bbddlocal.bbdd;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

@Database(entities = {Animal.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;


    public abstract AnimalDao animalDao();

    public static AppDatabase getInMemoryDatabase(Context context) {

        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "word_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    public static void destroyInstance() {
        INSTANCE = null;
    }


    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     * <p>
     * If you want to populate the database only when the database is created for the 1st time,
     * override RoomDatabase.Callback()#onCreate
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    public static void populateAsync(final AppDatabase db) {

        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    /**
     * Populate the database in the background.
     * If you want to start with more words, just add them.
     */
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

    private static void populateWithTestData(AppDatabase db) {
        db.animalDao().deleteAll();


        Date today = getTodayPlusDays(0);
        Date yesterday = getTodayPlusDays(-1);
        Date twoDaysAgo = getTodayPlusDays(-2);
        Date lastWeek = getTodayPlusDays(-7);
        Date twoWeeksAgo = getTodayPlusDays(-14);

        //TODO improve registerDate calculation and formatting

        Animal animal1 = addAnimal(db, 1,"Teddy", 3, true, "dog",lastWeek,"photoTeedy");
        Animal animal2 = addAnimal(db, 2,"Nemo", 1, false, "fish",yesterday,"photoNemo");
        Animal animal3 = addAnimal(db, 3,"Catty", 2, true, "cat",today,"photoCatty");

    }

    private static Date getTodayPlusDays(int daysAgo) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, daysAgo);
        return calendar.getTime();
    }

    private static Animal addAnimal(final AppDatabase db,
                                    final int id,
                                    final String name,
                                    final int age,
                                    final boolean isChipped,
                                    final String animalType,
                                    final Date regDate,
                                    final String photo
    ) {

        Animal animal = new Animal();
        animal.id = id;
        animal.name = name;
        animal.age = age;
        animal.isChipped = isChipped;
        animal.animalType = animalType;
        animal.regDate = regDate;
        animal.photo = photo;

        db.animalDao().insertAnimal(animal);
        return animal;
    }

}



