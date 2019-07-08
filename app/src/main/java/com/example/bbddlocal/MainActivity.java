package com.example.bbddlocal;


import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;

import com.example.bbddlocal.bbdd.Animal;
import com.example.bbddlocal.bbdd.AppDatabase;
import com.example.bbddlocal.bbdd.AnimalsViewModel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private TextView mAnimalsTextView;
    private AnimalsViewModel mViewModel;

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAnimalsTextView = (TextView) findViewById(R.id.animals_tv);

        // Get a reference to the ViewModel for this screen.
        mViewModel = ViewModelProviders.of(this).get(AnimalsViewModel.class);

        // Update the UI whenever there's a change in the ViewModel's data.
        subscribeUiAnimals();
    }

    private void subscribeUiAnimals() {
        mViewModel.mAllAnimals.observe(this, new Observer<List<Animal>>() {
            @Override
            public void onChanged(@NonNull final List<Animal> animals) {
                showAnimalsInUi(animals);
            }
        });
    }

    private void showAnimalsInUi(final @NonNull List<Animal> animals) {
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
        }
        mAnimalsTextView.setText(Html.fromHtml(sb.toString()));
    }


    public void onClickCreate(View view) {
        Intent intent = new Intent(this, CreateAnimalActivity.class);
        //startActivity(intent);
        startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
    }

    public void onClickSearch(View view) {
        Intent intent = new Intent(this, SearchAnimalActivity.class);
        startActivity(intent);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {


            //aquí es crea l'animal amb les dades de l'altra pantalla. S'ha de crear un bundle a l'altra pantalla
            //que contingui l'animal a crear
            /*Animal animal = new Animal(data.getStringExtra(CreateAnimalActivity.EXTRA_REPLY));
            mViewModel.insert(animal);*/
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
