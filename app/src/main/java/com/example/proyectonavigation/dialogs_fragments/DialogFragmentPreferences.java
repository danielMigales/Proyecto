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
import com.android.volley.toolbox.Volley;
import com.example.proyectonavigation.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class DialogFragmentPreferences extends DialogFragment implements
        View.OnClickListener {

    //PARAMETROS POR DEFECTO
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

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
    String url_updatePreferences = "https://proyectogrupodapp.000webhostapp.com/users/UpdatePreferences.php";

    //CONSTRUCTORES POR DEFECTO
    public DialogFragmentPreferences() {
        // Required empty public constructor
    }

    public static DialogFragmentPreferences newInstance(String param1, String param2) {
        DialogFragmentPreferences fragment = new DialogFragmentPreferences();
        Bundle args = new Bundle();
        args.putString( ARG_PARAM1, param1 );
        args.putString( ARG_PARAM2, param2 );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        if (getArguments() != null) {
            mParam1 = getArguments().getString( ARG_PARAM1 );
            mParam2 = getArguments().getString( ARG_PARAM2 );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.fragment_dialog_preferences, container, false );

        //OBTENCION DEL EMAIL PARA HACER LA CONSULTA EN LA BASE DE DATOS
        Intent intent = getActivity().getIntent();
        email = intent.getStringExtra( "email" );

        //SWITCHES DE PREFERENCIAS
        music = (Switch) view.findViewById( R.id.switchMusic );
        music.setOnClickListener(this);
        cine = (Switch) view.findViewById( R.id.switchCine );
        cine.setOnClickListener(this);
        food = (Switch) view.findViewById( R.id.switchFood );
        food.setOnClickListener(this);
        sports = (Switch) view.findViewById( R.id.switchSports );
        sports.setOnClickListener(this);
        tv = (Switch) view.findViewById( R.id.switchTV );
        tv.setOnClickListener(this);
        outdoor = (Switch) view.findViewById( R.id.switchOutdoor );
        outdoor.setOnClickListener(this);
        books = (Switch) view.findViewById( R.id.switchBooks );
        books.setOnClickListener(this);
        games = (Switch) view.findViewById( R.id.switchGames );
        games.setOnClickListener(this);
        culture = (Switch) view.findViewById( R.id.switchCulture );
        culture.setOnClickListener(this);
        save = view.findViewById( R.id.buttonSave );
        save.setOnClickListener(this);

        //OBTENCION DE LAS PREFERENCIAS DE LA BASE DE DATOS PARA MARCAR DE INICIO LAS QUE YA ESTAN ESCOGIDAS
        getData( email );




        return view;
    }

    public void getData(String email) {

        String url_userdata = "https://proyectogrupodapp.000webhostapp.com/users/getUserData.php?email=" + email;

        JsonArrayRequest request = new JsonArrayRequest( Request.Method.POST, url_userdata, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray jsonArray) {

                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject( i );

                                String user_email = jsonObject.getString( "email" );
                                String user_picture = jsonObject.getString( "picture" );
                                String user_name = jsonObject.getString( "name" );
                                String user_preferences = jsonObject.getString( "preferences" );

                                String formattedString = user_preferences
                                        .replace( "[", "" )  //quitar corchete
                                        .replace( "]", "" )  //quitar corchete
                                        .trim();

                                String[] parts = formattedString.split( "," );
                                for (i = 0; i < parts.length; i++) {
                                    if (parts[i].contains( "cine" )) {
                                        cine.setChecked( true );
                                    }

                                    if (parts[i].contains( "musica" )) {
                                        music.setChecked( true );
                                    }

                                    if (parts[i].contains( "television" )) {
                                        tv.setChecked( true );
                                    }

                                    if (parts[i].contains( "gastronomia" )) {
                                        food.setChecked( true );
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

                                    if (parts[i].contains( "salir" )) {
                                        outdoor.setChecked( true );
                                    }

                                    if (parts[i].contains( "cultura" )) {
                                        culture.setChecked( true );
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


    @Override
    public void onClick(View v) {

        boolean isChecked = true;
        
        switch (v.getId()) {

            case R.id.switchCine:


                if (isChecked) {

                }
                break;

            case R.id.switchMusic:

                break;

            case R.id.switchFood:

                break;

            case R.id.switchBooks:

                break;

            case R.id.switchCulture:

                break;

            case R.id.switchGames:

                break;

            case R.id.switchOutdoor:

                break;

            case R.id.switchSports:

                break;

            case R.id.switchTV:

                break;

            default:

                break;
        }
    }
}

