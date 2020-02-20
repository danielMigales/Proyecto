package com.example.proyectonavigation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SciencePreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_science_preferences);

        getSupportActionBar().hide();
    }
}
