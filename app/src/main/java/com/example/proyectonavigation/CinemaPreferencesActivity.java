package com.example.proyectonavigation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class CinemaPreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_preferences);

        getSupportActionBar().hide();
    }
}
