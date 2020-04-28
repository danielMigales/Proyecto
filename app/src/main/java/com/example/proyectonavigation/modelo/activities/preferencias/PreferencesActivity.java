package com.example.proyectonavigation.modelo.activities.preferencias;

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
import com.example.proyectonavigation.modelo.activities.start_activities.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PreferencesActivity extends AppCompatActivity {

    ArrayList<String> preferences;
    boolean categoriesLenght = false;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        getSupportActionBar().hide();

        cinema = findViewById(R.id.checkBoxCinema);
        food = findViewById(R.id.checkBoxFood);
        music = findViewById(R.id.checkBoxMusic);
        outdoor = findViewById(R.id.checkBoxOutdoor);
        tv = findViewById(R.id.checkBoxTV);
        books = findViewById(R.id.checkBoxBooks);
        videogames = findViewById(R.id.checkBoxVideoGames);
        sports = findViewById(R.id.checkBoxSports);
        culture = findViewById(R.id.checkBoxCulture);

        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");
        final String name = intent.getStringExtra("name");

        submit = findViewById(R.id.btn_save);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recuentoPreferencias(v);
                if (categoriesLenght) {
                    Intent intent = new Intent(PreferencesActivity.this, MainActivity.class);
                    intent.putExtra("email", email);
                    intent.putExtra("name", name);
                    startActivity(intent);
                }
            }
        });
    }

    public void comprobarCheckbox(View v) {
        int contador = 0;
        preferences = new ArrayList<>();

        if (cinema.isChecked()) {
            preferences.add("cine");
            contador++;
        }
        if (food.isChecked()) {
            preferences.add("gastronomia");
            contador++;
        }
        if (music.isChecked()) {
            preferences.add("musica");
            contador++;
        }
        if (outdoor.isChecked()) {
            preferences.add("outdoor");
            contador++;
        }
        if (tv.isChecked()) {
            preferences.add("television");
            contador++;
        }
        if (culture.isChecked()) {
            preferences.add("cultura");
            contador++;
        }
        if (books.isChecked()) {
            preferences.add("literatura");
            contador++;
        }
        if (videogames.isChecked()) {
            preferences.add("videojuegos");
            contador++;
        }
        if (sports.isChecked()) {
            preferences.add("deportes");
            contador++;
        }
        if (contador > 3) {
            Toast.makeText(getApplicationContext(), "Solo debe escoger tres preferencias", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Perfecto, sus gustos son excelentes.", Toast.LENGTH_SHORT).show();
        }
    }

    public void recuentoPreferencias(View view) {
        comprobarCheckbox(view);
        System.out.println(preferences.size());
        if (preferences.size() == 3) {
            savePreferences();
            categoriesLenght = true;
        } else {
            Toast.makeText(PreferencesActivity.this, "Escoja un maximo de tres preferencias", Toast.LENGTH_SHORT).show();
            categoriesLenght = false;
        }
    }

    public void savePreferences() {
        String URL_PREFER = "https://proyectogrupodapp.000webhostapp.com/users/user_data_queries/setPreferences.php";
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
                        Toast.makeText(PreferencesActivity.this, "No se han guardado sus preferencias " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                Intent intent = getIntent();
                String email = intent.getStringExtra("email");
                params.put("email", email);
                String formattedString = preferences.toString().replace("[", "").replace("]", "").replace(" ", "");
                String[] parts = formattedString.split(",");
                String part1 = parts[0];
                String part2 = parts[1];
                String part3 = parts[2];
                params.put("preference1", part1);
                params.put("preference2", part2);
                params.put("preference3", part3);
                System.out.println(part1 + part2 + part3);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}





