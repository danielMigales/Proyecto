package com.example.proyectonavigation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class OutdoorPreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outdoor_preferences);

        getSupportActionBar().hide();
    }
}
