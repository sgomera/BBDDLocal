package com.example.bbddlocal;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

    public String animalNametoString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_animal);

        animalName = findViewById(R.id.et_name_search);

        animalNametoString = animalName.toString();

        myResultTextView = (TextView) findViewById(R.id.tv_results);

        //get a reference to the viewmodel for this screen
        // Create a ViewModel the first time the system calls an activity's onCreate() method.
        // Re-created activities receive the same MyViewModel instance created by the first activity.
        mViewModel = ViewModelProviders.of(this).get(AnimalsViewModel.class);

    }


    public void onClickSearchDetail(View view) {

        // Update the UI whenever there's a change in the ViewModel's data.
        subscribeUiSearchAnimals();

      //  mViewModel.FindAnimalViewModelByName(animalName.toString());

    }


    private void subscribeUiSearchAnimals() {
        mViewModel.mAllAnimals.observe(this, new Observer<List<Animal>>() {
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

            myResultTextView.setText(Html.fromHtml(sb.toString()));
        }
    }
}
