package com.example.travelbuddy.activities;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelbuddy.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        setContentView(R.layout.activity_main);


        // hide the action bar
        // testsubject

        getSupportActionBar().hide();

    }
}
