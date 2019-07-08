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

import com.example.bbddlocal.bbdd.Animal;
import com.example.bbddlocal.bbdd.InsertAnimalViewModel;

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

    //variable for button //TODO: commented to do test code example
 //   private Button saveAnimal;

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    //create varible for viewmodel
    private InsertAnimalViewModel mViewModel;


    private Animal animal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_animal);

        //find views by id and store them
        animalId = findViewById(R.id.et_animalId);
        imgAnimal = (ImageView)findViewById(R.id.img_animal);
        animalName = (EditText)findViewById(R.id.et_name);
        animalAge = (EditText)findViewById(R.id.et_age);
        regDate = (EditText)findViewById(R.id.et_reg_date);
        animalType = (EditText)findViewById(R.id.et_animal_type);
        isChipped = (CheckBox)findViewById(R.id.checkBox);

        // Get a reference to the ViewModel for this screen. Does this execute the methods of the viewmodel????
        //or do I have to execute them later in the onclick method???
        mViewModel = ViewModelProviders.of(this).get(InsertAnimalViewModel.class);


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

    //create onclick method to insert a new register in animals TODO commented to do test code example, with listener
    /*public void onClickSave(View view) {
        createAnimal();

      //això peta:
      //  mViewModel.InsertAnimal(animal);
        Toast.makeText(this, "animal created successfully", Toast.LENGTH_SHORT).show();
    }*/

    private Animal createAnimal() {

        Date today = getTodayPlusDays(0);
        Date yesterday = getTodayPlusDays(-1);
        Date twoDaysAgo = getTodayPlusDays(-2);
        Date lastWeek = getTodayPlusDays(-7);
        Date twoWeeksAgo = getTodayPlusDays(-14);


        animal = addAnimal(
                4 ,
                animalName.toString(),
            //    Integer.getInteger(animalAge.toString()),
                5,
                true,
                animalType.toString(),
                lastWeek,
                "photo");
        return animal;
    }

    private static Date getTodayPlusDays(int daysAgo) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, daysAgo);
        return calendar.getTime();
    }


    private static Animal addAnimal(
                                 //   final AppDatabase db,
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

      //  db.animalModel().insertAnimal(animal);
        return animal;
    }


}
