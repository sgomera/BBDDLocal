package com.example.bbddlocal;

import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.arch.lifecycle.ViewModelProviders;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private AnimalsViewModel mViewModel;
    private TextView mAnimalsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get a reference to the ViewModel for this screen.
        mViewModel = ViewModelProviders.of(this).get(AnimalsViewModel.class);

        // Update the UI whenever there's a change in the ViewModel's data.
        subscribeUiAnimals();
    }

    public void onRefreshBtClicked(View view) {
        mViewModel.createDb();
    }

    private void subscribeUiAnimals() {
        mViewModel.animals.observe(this, new Observer<List<Animal>>() {
            @Override
            public void onChanged(@NonNull final List<Animal> animals) {
                showAnimalsInUi(animals);
            }
        });
    }

    private void showAnimalsInUi(final @NonNull List<Animal> animals) {
        StringBuilder sb = new StringBuilder();


        for (Animal animal : animals) {
            sb.append(animal.name);
            sb.append("\n");

        }
        mAnimalsTextView.setText(sb.toString());



    }
}
