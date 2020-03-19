package com.example.proyectonavigation.ui.home;

import android.content.DialogInterface;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectonavigation.R;
import com.example.proyectonavigation.model.Plannings;
import com.example.proyectonavigation.model.PlanningsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    private String email;


    //VARIABLES PARA LA LISTA DE PREFERENCIAS EN RECYCLERVIEW
    private ArrayList<Plannings> plans;
    private RecyclerView recyclerView;

    //INSTANCIA DE LA CARDVIEW QUE AL PULSARLA REDIRIGE A LA DESCRIPCION
    private CardView cardviewPlan;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = ViewModelProviders.of( this ).get( HomeViewModel.class );
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

        //IMPLEMENTACION DEL BOTON FILTRO CON ALERT DIALOG
        Button filter = view.findViewById( R.id.buttonFilter );
        filter.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
                builder.setTitle( "FILTRAR ACTIVIDADES " );
                int checkedItem = 0; // Ver todas las actividades
                builder.setCancelable( true );
                //LOS ITEMS QUE USA EL LISTADO ESTAN EN LA CARPETA RES/VALUES/ARRAYS. TAMBIEN SE PUEDE HACER UN ARRAY AQUI MISMO
                builder.setSingleChoiceItems( R.array.filtradoHome, checkedItem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: // Ver todas las actividades
                                getPlans();
                                dialog.dismiss();
                                break;
                            case 1: // Ver solo mis preferencias

                                dialog.dismiss();
                                break;
                            case 2: // Ver las mejor valoradas

                                dialog.dismiss();
                                break;
                        }
                    }
                } );
                AlertDialog dialog = builder.create();
                dialog.show();
                Toast.makeText( getContext(), "Filtrando...", Toast.LENGTH_SHORT ).show();

            }
        } );

        //OBTENER LOS DATOS DE LA BD AL CARGARSE LA ACTIVIDAD
        getPlans();

        return view;
    }

    //OBTENCION DE LOS DATOS DE LA BASE DE DATOS
    private void getPlans() {

        String url_getPlans = "https://proyectogrupodapp.000webhostapp.com/users/get_plannings_cardview.php";

        JsonArrayRequest request = new JsonArrayRequest( Request.Method.POST, url_getPlans, null,
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
                                String activitiy_category = jsonObject.getString( "activitiy_category" );
                                String activity_subcategory = jsonObject.getString( "activity_subcategory" );
                                String activity_start_date = jsonObject.getString( "activity_start_date" );
                                String activity_end_date = jsonObject.getString( "activity_end_date" );
                                String activity_picture = jsonObject.getString( "activity_picture" );

                                Bitmap decoded_activity_picture = decodeImage( activity_picture );
                                plans.add( new Plannings( activity_id, activity_name, activity_title, activity_rating, activitiy_category, activity_subcategory,
                                        activity_start_date, activity_end_date, decoded_activity_picture ) );

                                //CONTADOR PARA VER CUANTOS ENCUENTRA
                                contador++;

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
                        Toast.makeText( getContext(), "No se han encontrado actividades para hoy", Toast.LENGTH_SHORT ).show();
                    }

                } ) {

            @Override
            public int getMethod() {
                return Method.POST;
            }

            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put( "email", email );
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue( getActivity() );
        requestQueue.add( request );
    }

    //DECODIFCA LA IMAGEN QUE RECIBE DE LA BD EN FORMATO STRING
    private Bitmap decodeImage(String picture) {

        byte[] imageBytes;
        imageBytes = Base64.decode( picture, Base64.DEFAULT );
        return BitmapFactory.decodeByteArray( imageBytes, 0, imageBytes.length );
    }

    private void initializeAdapter() {

        PlanningsAdapter adapter = new PlanningsAdapter( plans );
        recyclerView.setAdapter( adapter );
    }
    }