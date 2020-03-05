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
import com.android.volley.toolbox.StringRequest;
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

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private ImageView photo;
    private TextView textName;
    private TextView textEmail;
    private String email;
    private String name;
    private static String URL_PHOTO = "https://proyectogrupod.000webhostapp.com/register_php/downloadPhoto.php";
    private static String URL_NAME = "https://proyectogrupod.000webhostapp.com/register_php/getName.php";
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
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //ESTE BLOQUE EXTRAE EL NOMBRE Y EL EMAIL QUE SE HAN PASADO DESDE OTRAS ACTIVIDADES PARA COLOCARLOS EN EL PERFIL
        //EN UNA FUTURA ACTUALIZACION LOS DATOS SE OBTENDRAN DE LA BBDD
        Intent intent = getActivity().getIntent();

        email = intent.getStringExtra("email");
        textEmail = view.findViewById(R.id.textViewEmail);
        textEmail.setText(email);

        //EL NOMBRE HA DE VENIR DE LA BD YA QUE CUANDO INICIAS DESDE LOGIN NO SE OBTIENE EL NAME
        name = intent.getStringExtra("name");
        textName = view.findViewById(R.id.textViewName);
        textName.setText(name);

        photo = view.findViewById(R.id.imageViewPicture);
        downloadImage();


        //ESTE BLOQUE SON TODOS LOS BOTONES QUE HAY EN EL SCROLLHORIZONTAL Y QUE REDIRIGEN HACIA LAS ACTIVITIES PARA SELECCIONAR PREFERENCIAS
        editCinema = view.findViewById(R.id.editCinema);
        editCinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CinemaPreferencesActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        editfood = view.findViewById(R.id.editFood);
        editfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FoodPreferencesActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        editMusic = view.findViewById(R.id.editMusic);
        editMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MusicPreferencesActivity.class);
                intent.putExtra("email", email);
                startActivityForResult(intent, 0);
            }
        });

        editTVShows = view.findViewById(R.id.editTVShows);
        editTVShows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TVShowsPreferencesActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        editOutdoor = view.findViewById(R.id.editOutdoor);
        editOutdoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OutdoorPreferencesActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        editCulture = view.findViewById(R.id.editCulture);
        editCulture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CulturePreferencesActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        editBooks = view.findViewById(R.id.editBooks);
        editBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BooksPreferencesActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        editVideoGames = view.findViewById(R.id.editVideoGames);
        editVideoGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), VideoGamesPreferencesActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        editSports = view.findViewById(R.id.editSports);
        editSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SportsPreferencesActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        changeData = view.findViewById(R.id.buttonChangeProfileData);
        changeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChangeProfileActivity.class);
                intent.putExtra("email", email);
                startActivityForResult(intent, 0);
            }
        });

        return view;
    }

    private void downloadImage() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PHOTO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            byte[] byteData = Base64.decode( response, Base64.DEFAULT);
                            Bitmap picture = BitmapFactory.decodeByteArray( byteData, 0,
                                    byteData.length);
                            photo.setImageBitmap(picture);
                        }
                        catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getContext(), volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };

        //Creación de una cola de solicitudes
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //Agregar solicitud a la cola
        requestQueue.add(stringRequest);
    }

    private void getName() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_NAME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };

        //Creación de una cola de solicitudes
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //Agregar solicitud a la cola
        requestQueue.add(stringRequest);
    }


}