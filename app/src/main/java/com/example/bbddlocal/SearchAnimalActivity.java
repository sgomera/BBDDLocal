package com.example.bbddlocal;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bbddlocal.bbdd.Animal;
import com.example.bbddlocal.bbdd.AnimalsViewModel;
import com.example.bbddlocal.bbdd.AppDatabase;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class SearchAnimalActivity extends AppCompatActivity {
    private AppDatabase mDb;
    private EditText animalName;
    private TextView myResultTextView;
    private AnimalsViewModel mViewModel;

    public List<Animal> mSearchedAnimals;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_animal);

        animalName = (EditText) findViewById(R.id.et_name_search);

        myResultTextView = (TextView) findViewById(R.id.tv_results);

        //get a reference to the ViewModel for this screen
        mViewModel = ViewModelProviders.of(this ).get(AnimalsViewModel.class);

        final Button button = findViewById(R.id.bt_search);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                subscribeUiSearchAnimals();
            }
        });
    }

    private LiveData<List<Animal>> findAnimals(String name) {
        return  mViewModel.getByName(name);
    }


    private void subscribeUiSearchAnimals() {
        mViewModel.getByName(animalName.getText().toString()).observe(this, new Observer<List<Animal>>() {
            @Override
            public void onChanged(@NonNull final List<Animal> animals) {

               // showSearchAnimalsInUi(findAnimals(animalName.getText().toString()));
                showSearchAnimalsInUi(animals);
                Toast.makeText(getApplicationContext(), animalName.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    //TODO create a instance of a factory subclass of the viewmodel to be able to pass to viewmodel

    private void showSearchAnimalsInUi(final @NonNull List<Animal> animals) {
        StringBuilder sb = new StringBuilder();

        java.text.SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy",
                Locale.FRENCH);

        for (Animal animal : animals) {
            if (animal.isChipped) {
                sb.append("<font color='blue'>");
                sb.append("<strong>");
                sb.append(String.format(Locale.FRENCH, "%s", animal.name));
                sb.append("</strong>");
                sb.append("<br>");
                sb.append("</font>");
                sb.append("<font color='black'>");
                sb.append(String.format(Locale.FRENCH, "%s", simpleDateFormat.format(animal.regDate)));
                sb.append("<br>");
                sb.append("<br>");
                sb.append("</font>");
            } else {
                sb.append("<font color='grey'>");
                sb.append(String.format(Locale.FRENCH, "%s", animal.name));
                sb.append("<br>");
                sb.append("</font>");
                sb.append("<font color='black'>");
                sb.append(String.format(Locale.FRENCH, "%s", simpleDateFormat.format(animal.regDate)));
                sb.append("<br>");
                sb.append("<br>");
                sb.append("</font>");
            }

            myResultTextView.setText(Html.fromHtml(sb.toString()));
        }
    }
}
