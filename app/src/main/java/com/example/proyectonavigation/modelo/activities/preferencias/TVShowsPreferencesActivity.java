package com.example.proyectonavigation.modelo.activities.preferencias;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectonavigation.R;

public class TVShowsPreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshows_preferences);

        getSupportActionBar().hide();
    }
}
