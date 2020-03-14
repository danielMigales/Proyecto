package com.example.proyectonavigation.preferences_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectonavigation.R;
import com.example.proyectonavigation.start.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PreferencesActivity extends AppCompatActivity {

    private CheckBox cinema;
    private CheckBox food;
    private CheckBox music;
    private CheckBox outdoor;
    private CheckBox tv;
    private CheckBox culture;
    private CheckBox books;
    private CheckBox videogames;
    private CheckBox sports;
    private Button submit;

    ArrayList<String> preferences = new ArrayList();

    private static String URL_PREFER = "https://proyectogrupodapp.000webhostapp.com/users/setPreferences.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_preferences);

        getSupportActionBar().hide();

        cinema = (CheckBox) findViewById(R.id.checkBoxCinema);
        food = (CheckBox) findViewById(R.id.checkBoxFood);
        music = (CheckBox) findViewById(R.id.checkBoxMusic);
        outdoor = (CheckBox) findViewById(R.id.checkBoxOutdoor);
        tv = (CheckBox) findViewById(R.id.checkBoxTV);
        culture = (CheckBox) findViewById(R.id.checkBoxCulture);
        books = (CheckBox) findViewById(R.id.checkBoxBooks);
        videogames = (CheckBox) findViewById(R.id.checkBoxVideoGames);
        sports= (CheckBox) findViewById(R.id.checkBoxSports);

        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");
        final String name = intent.getStringExtra("name");

        submit = findViewById(R.id.btn_save);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprobarCheckbox(v);
                savePreferences();
                Intent intent = new Intent(PreferencesActivity.this, MainActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
    }

    public void comprobarCheckbox(View v) {

        if (cinema.isChecked()){
            preferences.add("cine");
        }
        if (food.isChecked()){
            preferences.add("gastronomia");
        }
        if (music.isChecked()){
            preferences.add("musica");
        }
        if (outdoor.isChecked()){
            preferences.add("salir");
        }
        if (tv.isChecked()){
            preferences.add("television");
        }
        if (culture.isChecked()){
            preferences.add("cultura");
        }
        if (books.isChecked()){
            preferences.add("literatura");
        }
        if (videogames.isChecked()){
            preferences.add("videojuegos");
        }
        if (sports.isChecked()){
            preferences.add("deportes");
        }
    }

    public void savePreferences(){

        for (int i = 0; i < preferences.size(); i++) {
            Toast.makeText(this,"Preferencias guardadas: " + preferences.toString(), Toast.LENGTH_LONG).show();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PREFER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText(PreferencesActivity.this, "Preferencias guardadas", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(PreferencesActivity.this, "JSON Error " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PreferencesActivity.this, "Response Error " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                Intent intent = getIntent();
                String email = intent.getStringExtra("email");
                params.put("email", email);
                params.put("preferences",preferences.toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}





