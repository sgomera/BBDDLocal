package com.example.bbddlocal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.arch.lifecycle.ViewModelProviders;



public class MainActivity extends AppCompatActivity {
    private AnimalsViewModel mViewModel;
    private TextView mAnimalsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get a reference to the ViewModel for this screen.
        mViewModel = ViewModelProviders.of(this).get(AnimalsViewModel.class);

    }

    public void onRefreshBtClicked(View view) {
        mViewModel.createDb();
    }
}
