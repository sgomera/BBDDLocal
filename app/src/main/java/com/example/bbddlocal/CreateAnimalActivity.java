package com.example.bbddlocal;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
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
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bbddlocal.bbdd.Animal;
import com.example.bbddlocal.bbdd.AnimalsViewModel;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateAnimalActivity extends AppCompatActivity implements DatePicker.OnDateChangedListener{

    //variables for EditText fields
    private EditText animalId;
    private ImageView imgAnimal;
    private Button takePictureButton;
    private Button selectFileButton;
    private EditText animalName;
    private EditText animalAge;
    private EditText regDate;
    private EditText animalType;
    private CheckBox isChipped;

    private Uri file;

    private boolean permissionGranted;

    private Animal animal;

    private AnimalsViewModel mViewModel;


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

        takePictureButton = findViewById(R.id.button_image);
        selectFileButton = findViewById(R.id.button_file);
        imgAnimal = findViewById(R.id.img_animal);


        //request permissions
        if (!permissionGranted) {
            checkPermissions();
        }


        // Get a reference to the ViewModel for this screen (activity).
        mViewModel = ViewModelProviders.of(this).get(AnimalsViewModel.class);


        final Button button = findViewById(R.id.bt_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                createAnimal();
                mViewModel.insert(animal);
                //return to main activity
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }



    private Animal createAnimal() {

        Date lastWeek = getTodayPlusDays(-7);


        animal = addAnimal(
                Integer.parseInt(animalId.getText().toString()),
                animalName.getText().toString(),
                Integer.parseInt(animalAge.getText().toString()),
                isChipped.isChecked(),
                animalType.getText().toString(),
                // regDate.get TODO get register date from datepicker
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
    private Animal addAnimal(
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

    //mètode per fer la foto TODO fix! not working (app crashes)
    public void takePicture(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

        startActivityForResult(intent, 100);
    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                imgAnimal.setImageURI(file);
            }
        }

        if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using data.getData().
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                imgAnimal.setImageURI(uri);
                //        Log.i(TAG, "Uri: " + uri.toString());
                //       showImage(uri);
            }
        }
    }


    //mètode per guardar la foto en un fitxer
    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
    }

    //mètodes per sel·leccionar un fitxer: Fires an intent to spin up the "file chooser" UI and select an image
    public void performFileSearch(View view) {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        intent.setType("image/*");

        startActivityForResult(intent, 200);
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

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        //TODO passar el resultat del date picker a l'editText de regDate (no funciona)
        //hem de rescatar les dades del bundle de la data de registre.
        Bundle data = getIntent().getExtras();

        //String enteredText = data.getString("text");
        Integer enteredDay = data.getInt("mDay");
        Integer enteredMonth = data.getInt("mMonth");
        Integer enteredYear = data.getInt("mYear");

        //mostrem el resultat en la vista corresponent
        regDate.setText(new StringBuilder().
                append(enteredDay).append("/").
                append(enteredMonth).append("/").
                append(enteredYear)
        );
    }
}
