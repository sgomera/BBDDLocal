package com.example.bbddlocal;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;

import com.example.bbddlocal.bbdd.Animal;
import com.example.bbddlocal.bbdd.AppDatabase;
import com.example.bbddlocal.bbdd.AnimalsViewModel;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;




public class MainActivity extends AppCompatActivity {
    private TextView mAnimalsTextView;
    private AnimalsViewModel mViewModel;

    private Button takePictureButton;
    private Button selectFileButton;

    private boolean permissionGranted;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAnimalsTextView = (TextView) findViewById(R.id.animals_tv);

        //request permissions
        if (!permissionGranted) {
            checkPermissions();
        }

        // Get a reference to the ViewModel for this screen (activity).
        mViewModel = ViewModelProviders.of(this).get(AnimalsViewModel.class);

        // Update the UI whenever there's a change in the ViewModel's data.
        subscribeUiAnimals();

    }

    private void subscribeUiAnimals() {
        // Add an observer on the LiveData returned by findAllAnimals.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        //mViewModel.mAllAnimals.observe(this, new Observer<List<Animal>>() {
        mViewModel.getAllAnimals().observe(this, new Observer<List<Animal>>() {
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
        startActivity(intent);
    }


    public void onClickSearch(View view) {
        Intent intent = new Intent(this, SearchAnimalActivity.class);
        startActivity(intent);
    }




    // Initiate check for permissions.
    private boolean checkPermissions() {

        int permissionCheckCamera = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        int permissionCheckReadStorage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if ((permissionCheckCamera != PackageManager.PERMISSION_GRANTED) || permissionCheckReadStorage != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                    0);
            return false;
        } else {
            return true;
        }
    }


    // Handle permissions result
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {

            case 0:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    //          && grantResults[1] == PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(this, "Camera permission granted",
                            Toast.LENGTH_SHORT).show();
                    takePictureButton.setEnabled(true);
                    if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        selectFileButton.setEnabled(true);
                        Toast.makeText(this, "Read storage permission granted",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Select file button disabled", Toast.LENGTH_SHORT).show();
                        selectFileButton.setEnabled(false);
                    }
                    break;
                } else {
                    Toast.makeText(this, "Take picture button disabled", Toast.LENGTH_SHORT).show();
                    takePictureButton.setEnabled(false);
                    if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        selectFileButton.setEnabled(true);
                        Toast.makeText(this, "Read storage permission granted",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Select file button disabled", Toast.LENGTH_SHORT).show();
                        selectFileButton.setEnabled(false);
                    }
                    break;
                }

        }
    }
}
