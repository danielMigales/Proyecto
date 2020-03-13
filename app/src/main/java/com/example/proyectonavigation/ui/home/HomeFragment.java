package com.example.proyectonavigation.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private CardView card1;
    ImageView imageViewCardview1;
    private CardView card2;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of( this ).get( HomeViewModel.class );
        View view = inflater.inflate( R.layout.fragment_home, container, false );

        imageViewCardview1 = view.findViewById( R.id.imageViewCardview1 );

        card1 = view.findViewById( R.id.cardview1 );
        card1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getContext(), CardActivity.class );
                startActivityForResult( intent, 0 );
            }
        } );
        getData();

        return view;
    }

    public void getData() {

        String url_userdata = "https://proyectogrupodapp.000webhostapp.com/users/activity_food_cardview.php";

        JsonArrayRequest request = new JsonArrayRequest( Request.Method.POST, url_userdata, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        int cantidad = 0;
                        ActividadesComida ac;
                        ArrayList<ActividadesComida> mi_list = new ArrayList<>();


                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject( i );


                                String id = jsonObject.getString( "id" );
                                String name = jsonObject.getString( "name" );
                                String latitud = jsonObject.getString( "latitud" );
                                String longitud = jsonObject.getString( "longitud" );
                                String phone = jsonObject.getString( "phone" );
                                String price = jsonObject.getString( "price" );
                                String photo = jsonObject.getString( "photo" );
                                String horario_ini = jsonObject.getString( "horario_ini" );

                                mi_list.add( new ActividadesComida( id, name, latitud, longitud, phone, price, photo, horario_ini ) );

                                System.out.println(mi_list.toString());
                                System.err.println( i );

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            cantidad++;
                        }


                        System.err.println( "cantidad de vueltas" + cantidad );
                    }
                },
                new Response.ErrorListener() {
                    @Override

                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText( getContext(), "Error al obtener los datos", Toast.LENGTH_SHORT ).show();
                    }

                } ) {

            @Override
            public int getMethod() {
                return Method.POST;
            }

            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                // params.put( "email", email.toString().trim() );
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue( getActivity() );
        requestQueue.add( request );
    }


}