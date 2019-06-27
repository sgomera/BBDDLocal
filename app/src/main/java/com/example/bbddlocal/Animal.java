package com.example.bbddlocal;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity
public class Animal {
    @PrimaryKey
    @NonNull
    public int id;

    public String name;

    public int age;

    public boolean isChipped;

    public String animalType;

    public Date regDate;

    public String photo;

}
