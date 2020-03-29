package com.example.proyectonavigation.ui.search;

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
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;

    //VARIABLE PARA EL EMAIL
    private String email;
    private String rating;
    private String url_category = "https://proyectogrupodapp.000webhostapp.com/plans/plans_queries/get_all_plans_category.php?categoria=";
    private String url_subcategory = "https://proyectogrupodapp.000webhostapp.com/plans/plans_queries/get_all_plans_subcategory.php?subcategoria=";

    //VARIABLES PARA LA LISTA DE PREFERENCIAS EN RECYCLERVIEW
    private ArrayList<Plannings> plans;
    private RecyclerView recyclerView;
    private Button music_btn, culture_btn, food_btn, cinema_btn, outdoor_btn, rating_btn;
    private Button subcateg_btn1, subcateg_btn2, subcateg_btn3, subcateg_btn4, subcateg_btn5, subcateg_btn6, subcateg_btn7;
    private HorizontalScrollView scrollView_Subcategorias;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        searchViewModel = new ViewModelProvider( this ).get( SearchViewModel.class );
        final View view = inflater.inflate( R.layout.fragment_search, container, false );


        //INTENT PARA OBTENER EL EMAIL DEL USUARIO QUE SE USA EN EL SELECT DE LA BD PARA CONSEGUIR EL RESTO DE DATOS
        Intent intent = getActivity().getIntent();
        email = intent.getStringExtra( "email" );
        //rating = intent.getStringExtra("ratingbarActivity");

        //IMPLEMENTACION DE RECYCLERVIEW
        LinearLayoutManager layoutManager = new LinearLayoutManager( getContext() );
        layoutManager.setOrientation( LinearLayoutManager.VERTICAL );
        recyclerView = view.findViewById( R.id.recyclerViewPlans );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( layoutManager );

        //declaracion de los botones de las categorias
        music_btn = view.findViewById( R.id.buttonmusica );
        culture_btn = view.findViewById( R.id.buttonCulture );
        food_btn = view.findViewById( R.id.button_Food );
        cinema_btn = view.findViewById( R.id.cine_btn );
        outdoor_btn = view.findViewById( R.id.button_outdoors );
        //rating_btn = view.findViewById(R.id.ratingBar);

        //declaracion de los botones de las subcategorias
        subcateg_btn1 = view.findViewById( R.id.button1_subcategoria );
        subcateg_btn2 = view.findViewById( R.id.button2_subcategoria );
        subcateg_btn3 = view.findViewById( R.id.button3_subcategoria );
        subcateg_btn4 = view.findViewById( R.id.button4_subcategoria );
        subcateg_btn5 = view.findViewById( R.id.button5_subcategoria );
        subcateg_btn6 = view.findViewById( R.id.button6_subcategoria );
        subcateg_btn7 = view.findViewById( R.id.button7_subcategoria );

        //instanciarlos como invisibles para usarlos cuando los necesitemos
       /* subcateg_btn1.setVisibility(View.GONE);
        subcateg_btn2.setVisibility(View.GONE);
        subcateg_btn3.setVisibility(View.GONE);
        subcateg_btn4.setVisibility(View.GONE);
        subcateg_btn5.setVisibility(View.GONE);
        subcateg_btn6.setVisibility(View.GONE);
        subcateg_btn7.setVisibility(View.GONE);*/

        //declaracion de los scrollviews
        HorizontalScrollView scrollView_Categorias = view.findViewById( R.id.scrollView_Category );
        scrollView_Subcategorias = view.findViewById( R.id.scrollView_Subcategory );
        scrollView_Subcategorias.setVisibility( View.GONE );

        culture_btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView_Subcategorias.setVisibility( View.VISIBLE );
                subcateg_btn1.setVisibility( View.VISIBLE );
                subcateg_btn2.setVisibility( View.VISIBLE );
                subcateg_btn3.setVisibility( View.GONE );
                subcateg_btn4.setVisibility( View.GONE );
                subcateg_btn5.setVisibility( View.GONE );
                subcateg_btn6.setVisibility( View.GONE );
                subcateg_btn7.setVisibility( View.GONE );
                subcateg_btn1.setText( "museo" );
                subcateg_btn2.setText( "Teatro" );
                String categoria = "cultura";
                getPlans( url_category + categoria );
                subcateg_btn1.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String subcategoria = "museo";
                        getPlans( url_subcategory + subcategoria );
                    }
                } );
                subcateg_btn2.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String subcategoria = "teatro";
                        getPlans( url_subcategory + subcategoria );
                    }
                } );
            }
        } );

        music_btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoria = "musica";
                getPlans( url_category + categoria );
                scrollView_Subcategorias.setVisibility( View.VISIBLE );
                subcateg_btn1.setVisibility( View.VISIBLE );
                subcateg_btn2.setVisibility( View.VISIBLE );
                subcateg_btn3.setVisibility( View.VISIBLE );
                subcateg_btn4.setVisibility( View.GONE );
                subcateg_btn5.setVisibility( View.GONE );
                subcateg_btn6.setVisibility( View.GONE );
                subcateg_btn7.setVisibility( View.GONE );
                //cambiamos el nombre de los botones de la subcategoria
                subcateg_btn1.setText( "Rock" );
                subcateg_btn2.setText( "Flamenco" );
                subcateg_btn3.setText( "Metal" );
                subcateg_btn1.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String subcategoria = "rock";
                        getPlans( url_subcategory + subcategoria );
                    }
                } );
                subcateg_btn2.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String subcategoria = "flamenco";
                        getPlans( url_subcategory + subcategoria );
                    }
                } );
                subcateg_btn3.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String subcategoria = "metal";
                        getPlans( url_subcategory + subcategoria );
                    }
                } );
            }
        } );

        food_btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoria = "gastronomia";
                getPlans( url_category + categoria );
                scrollView_Subcategorias.setVisibility( View.VISIBLE );
                subcateg_btn1.setVisibility( View.VISIBLE );
                subcateg_btn2.setVisibility( View.VISIBLE );
                subcateg_btn3.setVisibility( View.VISIBLE );
                subcateg_btn4.setVisibility( View.VISIBLE );
                subcateg_btn5.setVisibility( View.VISIBLE );
                subcateg_btn6.setVisibility( View.GONE );
                subcateg_btn7.setVisibility( View.GONE );
                subcateg_btn1.setText( "bar" );
                subcateg_btn2.setText( "española" );
                subcateg_btn3.setText( "china" );
                subcateg_btn4.setText( "mexicana" );
                subcateg_btn5.setText( "india" );
                subcateg_btn1.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String subcategoria = "bar";
                        getPlans( url_subcategory + subcategoria );
                    }
                } );
                subcateg_btn2.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String subcategoria = "española";
                        getPlans( url_subcategory + subcategoria );
                    }
                } );
                subcateg_btn3.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String subcategoria = "china";
                        getPlans( url_subcategory + subcategoria );
                    }
                } );
                subcateg_btn4.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String subcategoria = "mexicano";
                        getPlans( url_subcategory + subcategoria );
                    }
                } );
                subcateg_btn5.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String subcategoria = "india";
                        getPlans( url_subcategory + subcategoria );
                    }
                } );
            }
        } );


        cinema_btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView_Subcategorias.setVisibility( View.VISIBLE );
                subcateg_btn1.setVisibility( View.VISIBLE );
                subcateg_btn2.setVisibility( View.VISIBLE );
                subcateg_btn3.setVisibility( View.GONE );
                subcateg_btn4.setVisibility( View.GONE );
                subcateg_btn5.setVisibility( View.GONE );
                subcateg_btn6.setVisibility( View.GONE );
                subcateg_btn7.setVisibility( View.GONE );
                String categoria = "cine";
                getPlans( url_category + categoria );
                subcateg_btn1.setText( "animación" );
                subcateg_btn2.setText( "infantil" );
                subcateg_btn1.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String subcategoria = "animacion";
                        getPlans( url_subcategory + subcategoria );
                    }
                } );
                subcateg_btn2.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String subcategoria = "infantil";
                        getPlans( url_subcategory + subcategoria );
                    }
                } );
            }
        } );


        outdoor_btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView_Subcategorias.setVisibility( View.VISIBLE );
                subcateg_btn1.setVisibility( View.VISIBLE );
                subcateg_btn2.setVisibility( View.VISIBLE );
                subcateg_btn3.setVisibility( View.VISIBLE );
                subcateg_btn4.setVisibility( View.VISIBLE );
                subcateg_btn5.setVisibility( View.GONE );
                subcateg_btn6.setVisibility( View.GONE );
                subcateg_btn7.setVisibility( View.GONE );
                subcateg_btn1.setText( "paseo" );
                subcateg_btn2.setText( "excursión" );
                subcateg_btn3.setText( "paintball" );
                subcateg_btn4.setText( "playa" );
                String categoria = "actividades al aire libre";
                getPlans( url_category + categoria );
                subcateg_btn1.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String subcategoria = "pasear";
                        getPlans( url_subcategory + subcategoria );
                    }
                } );
                subcateg_btn2.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String subcategoria = "excursion";
                        getPlans( url_subcategory + subcategoria );
                    }
                } );
                subcateg_btn3.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String subcategoria = "paintball";
                        getPlans( url_subcategory + subcategoria );
                    }
                } );
                subcateg_btn4.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String subcategoria = "playa";
                        getPlans( url_subcategory + subcategoria );
                    }
                } );
            }
        } );


        //intentand
       /* rating_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
                DialogFragment dialogFragment = new DialogFragmentRatingActivity();
                dialogFragment.show( getFragmentManager(), "dialog" );
                Toast.makeText(getContext(), rating, Toast.LENGTH_SHORT).show();
            }
        });*/

        return view;
    }

    //CONEXION A LA BASE DE DATOS PARA OBTENCION DE DATOS
    private void getPlans(String URL) {
        JsonArrayRequest request = new JsonArrayRequest( Request.Method.POST, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {
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

    //INICIALIZA LA CARDVIEW
    private void initializeAdapter() {

        PlanningsAdapter adapter = new PlanningsAdapter( plans, getContext() );
        recyclerView.setAdapter( adapter );
    }

    /*public void dialog() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag( "dialog" );
        if (prev != null) {
            ft.remove( prev );
        }
        ft.addToBackStack( null );
    }*/
}