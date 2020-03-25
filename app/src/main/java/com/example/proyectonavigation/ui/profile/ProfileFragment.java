package com.example.proyectonavigation.ui.profile;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectonavigation.R;
import com.example.proyectonavigation.dialogs_fragments.DialogFragmentPreferences;
import com.example.proyectonavigation.model.Categories;
import com.example.proyectonavigation.model.CategoriesAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileFragment extends Fragment {

    //VARIABLES DE WIDGETS
    private String email;
    private Bitmap imageBitmap;
    private ImageView photo;
    private TextView textName;
    private TextView textEmail;
    private TextView preferences;
    private Button addPreferences;
    private Button changeData;

    //VARIABLE PARA OBTENER FOTO DE CAMARA
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    //VARIABLES PARA LA LISTA DE PREFERENCIAS EN RECYCLERVIEW
    private List<Categories> categories;
    private RecyclerView recyclerView;

    public static final long PERIODO = 1000; // 1 segundos (1 * 1000 millisegundos)
    private Handler handler;
    private Runnable runnable;

    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of( this ).get( ProfileViewModel.class );
        View view = inflater.inflate( R.layout.fragment_profile, container, false );

        //INTENT PARA OBTENER EL EMAIL DEL USUARIO QUE SE USA EN EL SELECT DE LA BD PARA CONSEGUIR EL RESTO DE DATOS
        Intent intent = getActivity().getIntent();
        email = intent.getStringExtra( "email" );

        //OBTENER LOS DATOS DE LA BD AL CARGARSE LA ACTIVIDAD
        getData();

        // SOLICITAR PERMISO PARA LA CAMARA
        requestCameraPermission();

        textEmail = view.findViewById( R.id.textViewEmail );
        textName = view.findViewById( R.id.textViewName );

        changeData = view.findViewById( R.id.buttonChangeProfileData );
        changeData.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getContext(), ChangeProfileActivity.class );
                intent.putExtra( "email", email );
                startActivityForResult( intent, 0 );
            }
        } );

        photo = view.findViewById( R.id.imageViewPicture );
        photo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {"Usar camara", "Escoger de la galeria", "Cancelar"};

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Añadir fotografia");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Usar camara")) {
                            dispatchTakePictureIntent();
                        } else if (items[item].equals("Escoger de la galeria")) {

                        } else if (items[item].equals("Cancelar")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();

            }
        } );

        //ESTE BOTON MUESTRA EL DIALOGO PARA SELECCIONAR LAS CATEGORIAS PRINCIPALES
        addPreferences = view.findViewById( R.id.buttonAddPreferences );
        addPreferences.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragmentPreferences dialogFragment = new DialogFragmentPreferences();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag( "dialog" );
                if (prev != null) {
                    ft.remove( prev );
                }
                ft.addToBackStack( null );
                dialogFragment.show( ft, "dialog" );
            }
        } );

        //IMPLEMENTACION DE RECYCLERVIEW
        LinearLayoutManager layoutManager = new LinearLayoutManager( getContext() );
        layoutManager.setOrientation( LinearLayoutManager.VERTICAL );
        recyclerView = view.findViewById( R.id.recyclerviewPreferences );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( layoutManager );

        return view;
    }

    //LOS METODOS onResume Y onPause DEBERIAN SERVIR PARA QUE LA ACTIVITY SE REFRESCARA AUTOMATICAMENTE DESPUES DE VOLVER DE ALGUN DIALOGO (NO ESTA FUNCIONANDO)
    @Override
    public void onResume() {
        super.onResume();
        this.onCreate( null );

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed( this, PERIODO );
            }
        };
        handler.postDelayed( runnable, PERIODO );
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks( runnable );
    }

    //SOLICITAR PERMISOS
    private void requestCameraPermission() {
        ActivityCompat.requestPermissions( getActivity(),
                new String[]{Manifest.permission.CAMERA},
                REQUEST_IMAGE_CAPTURE );
    }

    //EL METODO FALLA PORQUE DENIEGA EL PERMISO A LA CAMARA. RECORDATORIO DE REVISAR PERMISOS
    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
        if (takePictureIntent.resolveActivity( getActivity().getPackageManager() ) != null) {
            startActivityForResult( takePictureIntent, REQUEST_IMAGE_CAPTURE );
        }
    }

    //COLOCA LA FOTO EN EL IMAGEVIEW
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get( "data" );
            photo.setImageBitmap( imageBitmap ); //COLOCA LA IMAGEN EN EL IMAGEVIEW
            uploadImage();
        }
    }

    //CODIGO PARA COMRPRIMIR LA IMAGEN A STRING
    public String getStringImagen(Bitmap bmp) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress( Bitmap.CompressFormat.JPEG, 100, baos );
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString( imageBytes, Base64.DEFAULT );
        return encodedImage;
    }

    //OBTIENE LOS DATOS CARGANDOLOS DE LA BD
    public void getData() {

        String url_userdata = "https://proyectogrupodapp.000webhostapp.com/users/user_data_queries/getUserData.php?email=" + email;
        JsonArrayRequest request = new JsonArrayRequest( Request.Method.POST, url_userdata, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject( i );
                                String user_email = jsonObject.getString( "email" );
                                String user_picture = jsonObject.getString( "picture" );
                                decodeImage( user_picture ); //LLAMADA AL METODO PARA DECODIFICAR LA IMAGEN QUE ES UN STRING
                                String user_name = jsonObject.getString( "name" );
                                String user_preferences_1 = jsonObject.getString( "preference_1" );
                                String user_preferences_2 = jsonObject.getString( "preference_2" );
                                String user_preferences_3 = jsonObject.getString( "preference_3" );
                                String user_preferences_4 = jsonObject.getString( "preference_4" );
                                String user_preferences_5 = jsonObject.getString( "preference_5" );
                                String user_preferences_6 = jsonObject.getString( "preference_6" );
                                String user_preferences_7 = jsonObject.getString( "preference_7" );
                                String user_preferences_8 = jsonObject.getString( "preference_8" );
                                String user_preferences_9 = jsonObject.getString( "preference_9" );
                                String formattedString = user_preferences_1 + ", " + user_preferences_2 + ", " + user_preferences_3 + ", " + user_preferences_4 + ", "
                                        + user_preferences_5 + ", " + user_preferences_6 + ", " + user_preferences_7 + ", " + user_preferences_8 + ", " + user_preferences_9;

                                //SE PASAN LOS RESULTADOS A LOS WIDGETS DE LA INTERFAZ
                                textEmail.setText( user_email );
                                textName.setText( user_name );

                                initializeData( formattedString );
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
                params.put( "email", email );
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue( getActivity() );
        requestQueue.add( request );
    }

    //DECODIFCA LA IMAGEN QUE RECIBE DE LA BD EN FORMATO STRING
    public void decodeImage(String picture) {

        byte[] imageBytes;
        imageBytes = Base64.decode( picture, Base64.DEFAULT );
        Bitmap decodedImage = BitmapFactory.decodeByteArray( imageBytes, 0, imageBytes.length );
        photo.setImageBitmap( decodedImage );
    }

    //CODIGO PARA SUBIR IMAGEN A BASE DE DATOS
    public void uploadImage() {

        String UPLOAD_URL = "https://proyectogrupodapp.000webhostapp.com/users/user_data_queries/uploadPhoto.php";

        //MUESTRA UN DIALOGO DE PROGRESO
        final ProgressDialog loading = ProgressDialog.show( getContext(), "Uploading...", "uploading...", false, false );

        StringRequest stringRequest = new StringRequest( Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        loading.dismiss();
                        Toast.makeText( getContext(), s, Toast.LENGTH_LONG ).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        loading.dismiss();
                        Toast.makeText( getContext(), volleyError.getMessage(), Toast.LENGTH_LONG ).show();
                    }
                } ) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Convertir bits a cadena
                String photo = getStringImagen( imageBitmap );
                params.put( "photo", photo );
                params.put( "email", email );
                //Parámetros de retorno
                return params;
            }
        };

        //Creación de una cola de solicitudes
        RequestQueue requestQueue = Volley.newRequestQueue( getContext() );

        //Agregar solicitud a la cola
        requestQueue.add( stringRequest );
    }

    //INICIALIZADOR DE RECYCLER VIEW Y OBJETOS DE SU INTERIOR
    private void initializeData(String lista) {

        String[] parts = lista.split( "," );

        categories = new ArrayList<>();

        for (int i = 0; i < parts.length; i++) {
            if (parts[i].contains( "cine" )) {
                categories.add( new Categories( "Cine", R.drawable.cine ) );
            }
            if (parts[i].contains( "gastronomia" )) {
                categories.add( new Categories( "Gastronomia", R.drawable.food ) );
            }
            if (parts[i].contains( "musica" )) {
                categories.add( new Categories( "Musica", R.drawable.music ) );
            }
            if (parts[i].contains( "actividades al aire libre" )) {
                categories.add( new Categories( "Actividades al aire libre", R.drawable.viaje ) );
            }
            if (parts[i].contains( "television" )) {
                categories.add( new Categories( "Television", R.drawable.tv ) );
            }
            if (parts[i].contains( "cultura" )) {
                categories.add( new Categories( "Cultura", R.drawable.culture ) );
            }
            if (parts[i].contains( "literatura" )) {
                categories.add( new Categories( "Literatura", R.drawable.books ) );
            }
            if (parts[i].contains( "videojuegos" )) {
                categories.add( new Categories( "Videojuegos", R.drawable.videogames ) );
            }
            if (parts[i].contains( "deportes" )) {
                categories.add( new Categories( "Deportes", R.drawable.sports ) );
            }
        }

        CategoriesAdapter adapter = new CategoriesAdapter( categories );
        recyclerView.setAdapter( adapter );
    }
}
