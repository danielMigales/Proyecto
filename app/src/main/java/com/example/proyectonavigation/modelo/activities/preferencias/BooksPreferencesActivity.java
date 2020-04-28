package com.example.proyectonavigation.modelo.activities.preferencias;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectonavigation.R;

public class BooksPreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_preferences);

        getSupportActionBar().hide();
    }
}
