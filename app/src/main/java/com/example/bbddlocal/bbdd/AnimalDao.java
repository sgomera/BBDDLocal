package com.example.bbddlocal.bbdd;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import com.example.bbddlocal.utils.DateConverter;

import java.util.List;


import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
@TypeConverters(DateConverter.class)
public interface AnimalDao {
    //USING VIEWMODEL TODO: uncomment when using viewmodel and comment next query
    /*@Query("select * from animal")
    LiveData<List<Animal>> findAllAnimals();*/

    @Query("select * from animal")
    List<Animal> findAllAnimals();

    @Query("select * from animal where id = :id")
    Animal findAnimalById(int id);

    @Query("select * from animal where name = :name")
    LiveData<List<Animal>> findAnimalByName(String name);

    //insert---------------------------
    @Insert(onConflict = IGNORE)
    void insertAnimal(Animal animal);

    //delete---------------------------
    @Delete
    void deleteAnimal(Animal animal);

    @Query("delete from animal where id = :id")
    int deleteAnimalbyId(int id);

    @Query("delete from animal where name = :name")
    int deleteAnimalByName(String name);

    @Query("DELETE FROM animal")
    void deleteAll();

    //update--------------------------
    @Update
    void updateAnimal(Animal animal);




}
