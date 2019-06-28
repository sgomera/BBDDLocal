package com.example.bbddlocal;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.arch.lifecycle.ViewModelProviders;

import com.example.bbddlocal.bbdd.Animal;
import com.example.bbddlocal.bbdd.AppDatabase;
import com.example.bbddlocal.bbdd.FindAllAnimalsViewModel;
import com.example.bbddlocal.utils.DatabaseInitializer;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private AppDatabase mDb;
    private TextView mAnimalsTextView;

    //USING VIEWMODEL
   // private FindAllAnimalsViewModel mViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAnimalsTextView = (TextView) findViewById(R.id.animals_tv);

        // Note: Db references should not be in an activity.
        mDb = AppDatabase.getInMemoryDatabase(getApplicationContext());
        populateDb();
        fetchData();

/*      //USING VIEWMODEL----------------------------------------------------
        // Get a reference to the ViewModel for this screen.
        mViewModel = ViewModelProviders.of(this).get(FindAllAnimalsViewModel.class);

        // Update the UI whenever there's a change in the ViewModel's data.
        subscribeUiAnimals();*/
    }

    //USING VIEWMODEL---------------------------------------------------------
    public void onRefreshBtClicked(View view) {
     //   mViewModel.createDb();
    }

    private void populateDb() {
        DatabaseInitializer.populateSync(mDb);
    }

    private void fetchData() {
        // Note: this kind of logic should not be in an activity.
        StringBuilder sb = new StringBuilder();

        //TODO when using viewmodel change next Statement to LiveData<List<Animal>>
        List<Animal> animals = mDb.animalModel().findAllAnimalsOrderedByDateDesc();

        java.text.SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy",
                Locale.FRENCH);
        for (Animal animal : animals) {
            if (animal.isChipped){
                sb.append("<font color='blue'>");
                sb.append("<strong>");
                sb.append(String.format(Locale.FRENCH,"%s",animal.name));
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
    //USING VIEWMODEL---------------------------------------------------------
    /*private void subscribeUiAnimals() {
        mViewModel.animals.observe(this, new Observer<List<Animal>>() {
            @Override
            public void onChanged(@NonNull final List<Animal> animals) {
                showAnimalsInUi(animals);
            }
        });
    }*/

    //USING VIEWMODEL---------------------------------------------------------
   /* private void showAnimalsInUi(final @NonNull List<Animal> animals) {
        StringBuilder sb = new StringBuilder();
        for (Animal animal : animals) {
            sb.append(animal.name);
            sb.append("\n");
        }
        mAnimalsTextView.setText(sb.toString());
    }*/
}
