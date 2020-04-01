package com.example.proyectonavigation.dialogs_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectonavigation.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DialogFragmentPreferences extends DialogFragment implements
        View.OnClickListener {

    //VARIABLES
    private Switch music;
    private Switch cine;
    private Switch food;
    private Switch books;
    private Switch tv;
    private Switch outdoor;
    private Switch sports;
    private Switch games;
    private Switch culture;
    private Button save;
    private String email;
    ArrayList<String> preferencesList;
    boolean categoriesLenght = false;

    //CONSTRUCTORES POR DEFECTO
    public DialogFragmentPreferences() {
        // Required empty public constructor
    }

    public static DialogFragmentPreferences newInstance(String param1, String param2) {
        DialogFragmentPreferences fragment = new DialogFragmentPreferences();
        Bundle args = new Bundle();
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.fragment_dialog_preferences, container, false );

        //OBTENCION DEL EMAIL PARA HACER LA CONSULTA EN LA BASE DE DATOS
        Intent intent = getActivity().getIntent();
        email = intent.getStringExtra( "email" );

        //SWITCHES DE PREFERENCIAS
        music = view.findViewById( R.id.switchMusic );
        music.setOnClickListener( this );
        cine = view.findViewById( R.id.switchCine );
        cine.setOnClickListener( this );
        food = view.findViewById( R.id.switchFood );
        food.setOnClickListener( this );
        sports = view.findViewById( R.id.switchSports );
        sports.setOnClickListener( this );
        tv = view.findViewById( R.id.switchTV );
        tv.setOnClickListener( this );
        outdoor = view.findViewById( R.id.switchOutdoor );
        outdoor.setOnClickListener( this );
        books = view.findViewById( R.id.switchBooks );
        books.setOnClickListener( this );
        games = view.findViewById( R.id.switchGames );
        games.setOnClickListener( this );
        culture = view.findViewById( R.id.switchCulture );
        culture.setOnClickListener( this );
        save = view.findViewById( R.id.buttonSave );
        save.setOnClickListener( this );

        //OBTENCION DE LAS PREFERENCIAS DE LA BASE DE DATOS PARA MARCAR DE INICIO LAS QUE YA ESTAN ESCOGIDAS
        getData( email );

        return view;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.buttonSave) {
            recuentoPreferencias();
            if (categoriesLenght) {
                savePreferences();
                Toast.makeText( getContext(), "Sus cambios han sido guardados.", Toast.LENGTH_SHORT ).show();
                dismiss();
            } else {
                Toast.makeText( getContext(), "Sus cambios no han sido guardados.", Toast.LENGTH_SHORT ).show();
            }
        }
    }

    public void getData(String email) {
        String url_userdata = "https://proyectogrupodapp.000webhostapp.com/users/user_data_queries/getUserData.php?email=" + email;
        JsonArrayRequest request = new JsonArrayRequest( Request.Method.POST, url_userdata, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject( i );
                                String user_preference_1 = jsonObject.getString( "preference_1" );
                                String user_preference_2 = jsonObject.getString( "preference_2" );
                                String user_preference_3 = jsonObject.getString( "preference_3" );
                                String[] parts = {user_preference_1, user_preference_2, user_preference_3};

                                for (i = 0; i < parts.length; i++) {
                                    if (parts[i].contains( "cine" )) {
                                        cine.setChecked( true );
                                    }
                                    if (parts[i].contains( "gastronomia" )) {
                                        food.setChecked( true );
                                    }
                                    if (parts[i].contains( "musica" )) {
                                        music.setChecked( true );
                                    }
                                    if (parts[i].contains( "outdoor" )) {
                                        outdoor.setChecked( true );
                                    }
                                    if (parts[i].contains( "television" )) {
                                        tv.setChecked( true );
                                    }
                                    if (parts[i].contains( "cultura" )) {
                                        culture.setChecked( true );
                                    }
                                    if (parts[i].contains( "literatura" )) {
                                        books.setChecked( true );
                                    }
                                    if (parts[i].contains( "videojuegos" )) {
                                        games.setChecked( true );
                                    }
                                    if (parts[i].contains( "deportes" )) {
                                        sports.setChecked( true );
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( getContext(), "Error al obtener los datos", Toast.LENGTH_SHORT ).show();
                    }
                } ) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue( getContext() );
        requestQueue.add( request );
    }

    public void comprobarEstados() {

        int contador = 0;
        preferencesList = new ArrayList<>();

        if (cine.isChecked()) {
            preferencesList.add( "cine" );
            contador++;
        }
        if (food.isChecked()) {
            preferencesList.add( "gastronomia" );
            contador++;
        }
        if (music.isChecked()) {
            preferencesList.add( "musica" );
            contador++;
        }
        if (outdoor.isChecked()) {
            preferencesList.add( "outdoor" );
            contador++;
        }
        if (tv.isChecked()) {
            preferencesList.add( "television" );
            contador++;
        }
        if (culture.isChecked()) {
            preferencesList.add( "cultura" );
            contador++;
        }
        if (books.isChecked()) {
            preferencesList.add( "literatura" );
            contador++;
        }
        if (games.isChecked()) {
            preferencesList.add( "videojuegos" );
            contador++;
        }
        if (sports.isChecked()) {
            preferencesList.add( "deportes" );
            contador++;
        }
        if (contador == 3) {
            Toast.makeText( getContext(), "Perfecto, sus gustos son excelentes.", Toast.LENGTH_SHORT ).show();
        } else {
            Toast.makeText( getContext(), "Debe escoger tres preferencias", Toast.LENGTH_SHORT ).show();
        }
    }

    public void recuentoPreferencias() {
        comprobarEstados();
        System.out.println( preferencesList.size() );
        if (preferencesList.size() == 3) {
            categoriesLenght = true;
        } else {
            categoriesLenght = false;
        }
    }

    public void savePreferences() {

        String url_updatePreferences = "https://proyectogrupodapp.000webhostapp.com/users/user_data_queries/setPreferences.php";
        StringRequest stringRequest = new StringRequest( Request.Method.POST, url_updatePreferences,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            String success = jsonObject.getString( "success" );
                            if (success.equals( "1" )) {
                            } else {
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( getContext(), "Response Error " + error.toString(), Toast.LENGTH_SHORT ).show();
                    }
                } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put( "email", email );
                String formattedString = preferencesList.toString().replace( "[", "" ).replace( "]", "" ).replace( " ", "" );
                String[] parts = formattedString.split( "," );
                String part1 = parts[0];
                String part2 = parts[1];
                String part3 = parts[2];
                params.put( "preference1", part1 );
                params.put( "preference2", part2 );
                params.put( "preference3", part3 );
                System.out.println( part1 + part2 + part3 );
                return params;
            }
        };
        Volley.newRequestQueue( getContext() ).add( stringRequest );
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

}
