package com.example.proyectonavigation.ui.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.math.BigDecimal;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectonavigation.R;
import com.example.proyectonavigation.model.Plannings;
import com.example.proyectonavigation.model.PlanningsAdapter;
import com.example.proyectonavigation.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    User preferences = new User();
    PlanningsAdapter adapter;
    private HomeViewModel homeViewModel;
    private String email;
    private Button today;
    private Button soon;
    private Button best;
    private TextView counter;

    //VARIABLES PARA LA LISTA DE PREFERENCIAS EN RECYCLERVIEW
    private ArrayList<Plannings> plans;
    private RecyclerView recyclerView;
    //INSTANCIA DE LA CARDVIEW QUE AL PULSARLA REDIRIGE A LA DESCRIPCION
    private CardView cardviewPlan;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider( this ).get( HomeViewModel.class );
        View view = inflater.inflate( R.layout.fragment_home, container, false );

        //INTENT PARA OBTENER EL EMAIL DEL USUARIO QUE SE USA EN EL SELECT DE LA BD PARA CONSEGUIR EL RESTO DE DATOS
        Intent intent = getActivity().getIntent();
        email = intent.getStringExtra( "email" );

        //IMPLEMENTACION DE RECYCLERVIEW
        LinearLayoutManager layoutManager = new LinearLayoutManager( getContext() );
        layoutManager.setOrientation( LinearLayoutManager.VERTICAL );
        recyclerView = view.findViewById( R.id.recyclerViewPlans );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( layoutManager );

        //OBTENER DATOS DE USUARIO Y LLENAR CARDVIEWS
        getPreferences( "get_plans_with_preferences" );

        //BOTONES
        today = view.findViewById( R.id.buttontoday );
        today.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPreferences( "get_plans_with_preferences" );
            }
        } );

        soon = view.findViewById( R.id.buttonFuture );
        soon.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPreferences( "get_future_plans" );
            }
        } );

        best = view.findViewById( R.id.buttonBest );
        best.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPreferences( "get_best_plans" );
            }
        } );

        counter = view.findViewById( R.id.textViewNum );
        counter.setText( "0" );
        return view;
    }

    //OBTENER LAS PREFERENCIAS DEL USUARIO DE LA BASE DE DATOS Y LLAMAR AL METODO QUE OBTIENE LOS DATOS DE LAS ACTIVIDADES
    public void getPreferences(final String php) {
        String url_userdata = "https://proyectogrupodapp.000webhostapp.com/users/user_data_queries/getUserData.php?email=" + email;
        System.out.println( url_userdata );
        JsonArrayRequest request = new JsonArrayRequest( Request.Method.POST, url_userdata, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject( i );
                                String user_preferences_1 = jsonObject.getString( "preference_1" );
                                String user_preferences_2 = jsonObject.getString( "preference_2" );
                                String user_preferences_3 = jsonObject.getString( "preference_3" );
                                preferences = new User( user_preferences_1, user_preferences_2, user_preferences_3 );
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        //OBTENER LOS DATOS DE LA BD AL CARGARSE LA ACTIVIDAD
                        getData( preferences, php );
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( getContext(), "Error al obtener los datos", Toast.LENGTH_SHORT ).show();
                    }
                } ) {
            @Override
            public int getMethod() {
                return Method.POST;
            }

            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put( "email", email );
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue( getActivity() );
        requestQueue.add( request );
    }

    //OBTENCION DE LOS DATOS DE LA BASE DE DATOS
    public void getData(User preferences, String php) {

        //PREFERENCIAS GUARDADAS EN EL OBJETO
        String preferencia1 = preferences.getPreference_1();
        String preferencia2 = preferences.getPreference_2();
        String preferencia3 = preferences.getPreference_3();

        String url = "https://proyectogrupodapp.000webhostapp.com/plans/plans_queries/" + php + "_cardview.php?preferencia1=" + preferencia1 +
                "&preferencia2=" + preferencia2 + "&preferencia3=" + preferencia3;
        System.out.println( url );

        JsonArrayRequest request = new JsonArrayRequest( Request.Method.POST, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {
                            int contador = 0;
                            //CREACION DEL ARRAYLIST PARA LA CARDVIEW
                            plans = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject( i );
                                //OBTENCION DE TODOS LOS CAMPOS DE LA TABLA
                                int activity_id = jsonObject.getInt( "activity_id" );
                                String activity_name = jsonObject.getString( "activity_name" );
                                String activity_title = jsonObject.getString( "activity_title" );
                                float activity_rating = BigDecimal.valueOf( jsonObject.getDouble( "activity_rating" ) ).floatValue();
                                String activity_category = jsonObject.getString( "activity_category" );
                                String activity_subcategory = jsonObject.getString( "activity_subcategory" );
                                String activity_subcategory_1 = jsonObject.getString( "activity_subcategory_1" );
                                String activity_subcategory_2 = jsonObject.getString( "activity_subcategory_2" );
                                String activity_start_date = jsonObject.getString( "activity_start_date" );
                                String activity_end_date = jsonObject.getString( "activity_end_date" );
                                String activity_picture = jsonObject.getString( "activity_picture" );
                                Bitmap decoded_activity_picture = decodeImage( activity_picture );
                                plans.add( new Plannings( activity_id, activity_name, activity_title, activity_rating, activity_category, activity_subcategory, activity_subcategory_1, activity_subcategory_2,
                                        activity_start_date, activity_end_date, decoded_activity_picture ) );
                                //CONTADOR PARA VER CUANTOS ENCUENTRA
                                contador++;
                                String activityCounter = String.valueOf( contador );
                                counter.setText( activityCounter );

                                //INICIALIZADOR DEL RECYCLERVIEW
                                initializeAdapter();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( getContext(), "No hay actividades para hoy", Toast.LENGTH_SHORT ).show();
                    }
                } ) {
            @Override
            public int getMethod() {
                return Method.POST;
            }

            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put( "email", email );
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue( getActivity() );
        requestQueue.add( request );
    }

    //DECODIFCA LA IMAGEN QUE RECIBE DE LA BD EN FORMATO STRING
    public Bitmap decodeImage(String picture) {
        byte[] imageBytes;
        imageBytes = Base64.decode( picture, Base64.DEFAULT );
        Bitmap decodedImage = BitmapFactory.decodeByteArray( imageBytes, 0, imageBytes.length );
        return decodedImage;
    }

    private void initializeAdapter() {
        PlanningsAdapter adapter = new PlanningsAdapter( plans, getContext() );
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter( adapter );
    }

}