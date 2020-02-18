package com.example.proyectonavigation;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MusicPreferencesActivity extends AppCompatActivity {

    private CheckBox metal;
    private CheckBox pop;
    private CheckBox rock;
    private CheckBox soul;
    private CheckBox reagge;
    private CheckBox techno;
    private CheckBox blues;
    private CheckBox rap;
    private CheckBox regaton;
    private CheckBox clasic;
    private Button submit;
    ArrayList<String> music = new ArrayList();
    private static String URL = "https://proyectogrupod.000webhostapp.com/register_php/setMusic.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_preferences);

        metal = (CheckBox) findViewById(R.id.checkBoxMetal);
        pop = (CheckBox) findViewById(R.id.checkBoxPop);
        rock = (CheckBox) findViewById(R.id.checkBoxRock);
        soul = (CheckBox) findViewById(R.id.checkBoxSoul);
        reagge = (CheckBox) findViewById(R.id.checkBoxReage);
        techno = (CheckBox) findViewById(R.id.checkBoxTechno);
        blues = (CheckBox) findViewById(R.id.checkBoxBlues);
        rap = (CheckBox) findViewById(R.id.checkBoxRap);
        regaton = (CheckBox) findViewById(R.id.checkBoxRegaton);
        clasic = (CheckBox) findViewById(R.id.checkBoxClasic);

        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");

        submit = findViewById(R.id.btn_save);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprobarCheckbox(v);
                savePreferences();

            }
        });
    }
    public void comprobarCheckbox(View v) {

        if (metal.isChecked()){
            music.add("metal");
        }
        if (rock.isChecked()){
            music.add("rock");
        }
        if (pop.isChecked()){
            music.add("pop");
        }
        if (regaton.isChecked()){
            music.add("regaton");
        }
        if (reagge.isChecked()){
            music.add("reagge");
        }
        if (clasic.isChecked()){
            music.add("classic");
        }
        if (rap.isChecked()){
            music.add("rap");
        }
        if (techno.isChecked()){
            music.add("techno");
        }
        if (soul.isChecked()){
            music.add("soul");
        }
        if (blues.isChecked()){
            music.add("blues");
        }
    }

    public void savePreferences(){

        for (int i = 0; i < music.size(); i++) {
            Toast.makeText(this,"You have selected: " + music.toString(), Toast.LENGTH_LONG).show();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText(MusicPreferencesActivity.this, "Preferences saved", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MusicPreferencesActivity.this, "JSON Error " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MusicPreferencesActivity.this, "Response Error " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                Intent intent = getIntent();
                String email = intent.getStringExtra("email");
                params.put("email", email);
                params.put("music", music.toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}

