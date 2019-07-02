package com.example.bbddlocal;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bbddlocal.bbdd.Animal;
import com.example.bbddlocal.bbdd.FindAllAnimalsViewModel;
import com.example.bbddlocal.bbdd.FindAnimalViewModel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class SearchAnimalActivity extends AppCompatActivity {

    private EditText animalName;
    private TextView myResultTextView;
    private FindAnimalViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_animal);

        animalName = findViewById(R.id.et_name_search);

        myResultTextView = (TextView) findViewById(R.id.tv_results);

    }

    public void onClickSearchDetail(View view) {
        mViewModel = ViewModelProviders.of(this).get(FindAnimalViewModel.class);

        // Update the UI whenever there's a change in the ViewModel's data.
        subscribeUiSearchAnimals();
    }

    private void subscribeUiSearchAnimals() {

        mViewModel.animals.observe(this, new Observer<List<Animal>>() {
            @Override
            public void onChanged(@NonNull final List<Animal> animals) {
                showSearchAnimalsInUi(animals);
            }
        });
    }


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
            myResultTextView.setText(sb.toString());
        }
    }
}
