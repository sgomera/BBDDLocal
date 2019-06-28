package com.example.bbddlocal.bbdd;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.bbddlocal.utils.DateConverter;

import java.util.Date;

@Entity
@TypeConverters(DateConverter.class)
public class Animal {
    @PrimaryKey
    @NonNull
    public int id;//TODO how to autoincrement the ID??

    public String name;

    public int age;

    public boolean isChipped;

    public String animalType;

    public Date regDate;

    public String photo;

}
