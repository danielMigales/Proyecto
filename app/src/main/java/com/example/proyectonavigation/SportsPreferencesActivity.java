package com.example.proyectonavigation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SportsPreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_preferences);

        getSupportActionBar().hide();
    }
}
