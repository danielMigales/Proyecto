package com.example.proyectonavigation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class BooksPreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_preferences);

        getSupportActionBar().hide();
    }
}
