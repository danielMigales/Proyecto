package com.example.proyectonavigation.ui.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectonavigation.BooksPreferencesActivity;
import com.example.proyectonavigation.ChangeProfileActivity;
import com.example.proyectonavigation.CinemaPreferencesActivity;
import com.example.proyectonavigation.CulturePreferencesActivity;
import com.example.proyectonavigation.FoodPreferencesActivity;
import com.example.proyectonavigation.MusicPreferencesActivity;
import com.example.proyectonavigation.OutdoorPreferencesActivity;
import com.example.proyectonavigation.R;
import com.example.proyectonavigation.SportsPreferencesActivity;
import com.example.proyectonavigation.TVShowsPreferencesActivity;
import com.example.proyectonavigation.VideoGamesPreferencesActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private ImageView photo;
    private TextView textName;
    private TextView textEmail;
    private String email;
    private String name;

    String url_userdata = "https://proyectogrupodapp.000webhostapp.com/users/getUserData.php";

    private Button editCinema;
    private Button editfood;
    private Button editMusic;
    private Button editOutdoor;
    private Button editTVShows;
    private Button editCulture;
    private Button editBooks;
    private Button editVideoGames;
    private Button editSports;
    private Button changeData;

    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of( this ).get( ProfileViewModel.class );
        View view = inflater.inflate( R.layout.fragment_profile, container, false );

        //INTENT PARA OBTENER EL EMAIL DEL USUARIO QUE SE USA EN EL SELECT DE LA BD PARA CONSEGUIR EL RESTO DE DATOS
        Intent intent = getActivity().getIntent();
        email = intent.getStringExtra( "email" );

        textEmail = view.findViewById( R.id.textViewEmail );
        textName = view.findViewById( R.id.textViewName );
        photo = view.findViewById( R.id.imageViewPicture );

        //OBTENER LOS DATOS DE LA BD

        getData();


        //ESTE BLOQUE SON TODOS LOS BOTONES QUE HAY EN EL SCROLLHORIZONTAL Y QUE REDIRIGEN HACIA LAS ACTIVITIES PARA SELECCIONAR PREFERENCIAS
        editCinema = view.findViewById( R.id.editCinema );
        editCinema.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getContext(), CinemaPreferencesActivity.class );
                startActivityForResult( intent, 0 );
            }
        } );

        editfood = view.findViewById( R.id.editFood );
        editfood.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getContext(), FoodPreferencesActivity.class );
                startActivityForResult( intent, 0 );
            }
        } );

        editMusic = view.findViewById( R.id.editMusic );
        editMusic.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getContext(), MusicPreferencesActivity.class );
                intent.putExtra( "email", email );
                startActivityForResult( intent, 0 );
            }
        } );

        editTVShows = view.findViewById( R.id.editTVShows );
        editTVShows.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getContext(), TVShowsPreferencesActivity.class );
                startActivityForResult( intent, 0 );
            }
        } );

        editOutdoor = view.findViewById( R.id.editOutdoor );
        editOutdoor.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getContext(), OutdoorPreferencesActivity.class );
                startActivityForResult( intent, 0 );
            }
        } );

        editCulture = view.findViewById( R.id.editCulture );
        editCulture.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getContext(), CulturePreferencesActivity.class );
                startActivityForResult( intent, 0 );
            }
        } );

        editBooks = view.findViewById( R.id.editBooks );
        editBooks.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getContext(), BooksPreferencesActivity.class );
                startActivityForResult( intent, 0 );
            }
        } );

        editVideoGames = view.findViewById( R.id.editVideoGames );
        editVideoGames.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getContext(), VideoGamesPreferencesActivity.class );
                startActivityForResult( intent, 0 );
            }
        } );

        editSports = view.findViewById( R.id.editSports );
        editSports.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getContext(), SportsPreferencesActivity.class );
                startActivityForResult( intent, 0 );
            }
        } );

        changeData = view.findViewById( R.id.buttonChangeProfileData );
        changeData.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getContext(), ChangeProfileActivity.class );
                intent.putExtra( "email", email );
                startActivityForResult( intent, 0 );
            }
        } );

        return view;
    }

    public void getData() {

        final JSONArray jsonArrayParams = new JSONArray();
        JSONObject jsonObjectParams = new JSONObject();
        try {
            jsonObjectParams.put( "email", email );
            jsonArrayParams.put( jsonObjectParams );
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonArrayRequest request = new JsonArrayRequest( Request.Method.POST, url_userdata, jsonArrayParams,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray jsonArray) {

                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject( i );
                                String user_email = jsonObject.getString( "email" );
                                String user_picture = jsonObject.getString( "picture" );
                                decodeImage( user_picture ); //LLAMADA AL METODO PARA DECODIFICAR LA IMAGEN QUE ES UN STRING
                                textEmail.setText( user_email );
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
            public int getMethod() {
                return Method.POST;
            }

            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put( "email", email.toString().trim() );
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue( getActivity() );
        requestQueue.add( request );
    }

    public void decodeImage(String picture) {

        byte[] imageBytes;
        imageBytes = Base64.decode( picture, Base64.DEFAULT );
        Bitmap decodedImage = BitmapFactory.decodeByteArray( imageBytes, 0, imageBytes.length );
        photo.setImageBitmap( decodedImage );
    }


}

