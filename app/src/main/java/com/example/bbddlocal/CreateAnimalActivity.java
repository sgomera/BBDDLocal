package com.example.bbddlocal;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bbddlocal.bbdd.Animal;
import com.example.bbddlocal.bbdd.AnimalsViewModel;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class CreateAnimalActivity extends AppCompatActivity {

    //variables for EditText fields
    private EditText animalId;
    private ImageView imgAnimal;
    private EditText animalName;
    private EditText animalAge;
    private EditText regDate;
    private EditText animalType;
    private CheckBox isChipped;


    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    private Animal animal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_animal);

        //find views by id and store them
        animalId = findViewById(R.id.et_animalId);
        imgAnimal = findViewById(R.id.img_animal);
        animalName = findViewById(R.id.et_name);
        animalAge = findViewById(R.id.et_age);
        regDate = findViewById(R.id.et_reg_date);
        animalType = findViewById(R.id.et_animal_type);
        isChipped = findViewById(R.id.checkBox);


        final Button button = findViewById(R.id.bt_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                createAnimal();
                Intent replyIntent = new Intent();
                replyIntent.putExtra(EXTRA_REPLY, (Serializable) animal);
                setResult(RESULT_OK, replyIntent);

                finish();
            }
        });

    }


    private Animal createAnimal() {

        Date lastWeek = getTodayPlusDays(-7);

        animal = addAnimal(
                Integer.parseInt (animalId.getText().toString()),
                animalName.getText().toString(),
                Integer.parseInt (animalAge.getText().toString()),
                true,
                animalType.getText().toString(),
                lastWeek,
                "photo"
        );
        return animal;
    }

    private static Date getTodayPlusDays(int daysAgo) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, daysAgo);
        return calendar.getTime();
    }


    // constructor
    private  Animal addAnimal(
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

        return animal;
    }


}
