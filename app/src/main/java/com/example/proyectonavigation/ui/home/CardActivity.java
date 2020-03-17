package com.example.proyectonavigation.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectonavigation.R;
import com.example.proyectonavigation.model.Coordenadas;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CardActivity extends AppCompatActivity implements OnMapReadyCallback {

    ImageView imageViewPicture;
    TextView textViewTitulo;
    TextView textviewAddress;
    TextView textviewPrice;
    TextView textviewPhone;
    TextView textviewTimetable;
    TextView textviewDescription;
    TextView textviewURL;

    int activityID;
    String activity_title;
    String category;
    String tableName;
    ArrayList<Coordenadas> listaCoordenadas = new ArrayList<>();
    Coordenadas coordenada;

    MapView mapView;
    GoogleMap googleMap;
    private static final int LOCATION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_card );

        getSupportActionBar().hide();

        //INTENT PARA OBTENER EL ID DE LA ACTIVIDAD Y LA CATEGORIA. CON ESTO LANZAMOS EN LA URL UNA BUSQUEDA ESPECIFICA EN UNA TABLA
        Intent intent = getIntent();
        activityID = intent.getIntExtra( "activity_id", activityID );
        category = intent.getStringExtra( "activity_category" );

        //WIDGETS DE LA ACTIVITY
        imageViewPicture = findViewById( R.id.imageViewPicture );
        textViewTitulo = findViewById( R.id.textViewTitulo );
        textviewAddress = findViewById( R.id.textviewAddress );
        textviewPrice = findViewById( R.id.textviewPrice );
        textviewPhone = findViewById( R.id.textviewPhone );
        textviewTimetable = findViewById( R.id.textviewTimetable );
        textviewDescription = findViewById( R.id.textviewDescription );
        textviewURL = findViewById( R.id.textViewURL );
        mapView = (MapView) findViewById( R.id.mapViewPlan );
        mapView.onCreate( savedInstanceState );
        mapView.getMapAsync( this );

        //DATOS DE LA BD
        getData();


    }

    //CON ESTE METODO SE SELECCIONA A PARTIR DE LA CATEGORIA OBTENIDA EN LA ACTIVIDAD UNA TABLA U OTRA DE LA BASE DE DATOS
    private void getCategory() {

        if (category.equals( "musica" )) {
            tableName = "music_plans";
        }
        if (category.equals( "cine" )) {
            tableName = "cinema_plans";
        }

        if (category.equals( "cultura" )) {
            tableName = "culture_plans";
        }
        if (category.equals( "deportes" )) {
            tableName = "sports_plans";
        }

        if (category.equals( "actividades al aire libre" )) {
            tableName = "outdoor_plans";
        }

        if (category.equals( "gastronomia" )) {
            tableName = "food_plans";
        }

        if (category.equals( "literatura" )) {
            tableName = "books_plans";
        }

        if (category.equals( "videojuegos" )) {
            tableName = "videogames_plans";
        }

        if (category.equals( "television" )) {
            tableName = "tv_plans";
        }

    }

    //OBTENCION DE LOS DATOS DE LA BASE DE DATOS
    public void getData() {

        getCategory();

        String url_getPlans = "https://proyectogrupodapp.000webhostapp.com/users/get_plan_activity.php?id=" + activityID + "&table_name=" + tableName;
        System.out.println( url_getPlans );

        JsonArrayRequest request = new JsonArrayRequest( Request.Method.POST, url_getPlans, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray jsonArray) {

                        try {
                            int contador = 0;

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject( i );

                                //OBTENCION DE TODOS LOS CAMPOS DE LA TABLA
                                int id = jsonObject.getInt( "id" );

                                String activity_name = jsonObject.getString( "activity_name" );

                                activity_title = jsonObject.getString( "activity_title" );
                                textViewTitulo.setText( activity_title );

                                String activity_picture_1 = jsonObject.getString( "activity_picture_1" );
                                Bitmap decoded_activity_picture = decodeImage( activity_picture_1 );
                                imageViewPicture.setImageBitmap( decoded_activity_picture );

                                String activitiy_address = jsonObject.getString( "activity_address" );
                                textviewAddress.setText( activitiy_address );

                                String activity_latitude = jsonObject.getString( "activity_latitude" );
                                String activity_longitude = jsonObject.getString( "activity_longitude" );

                                coordenada = new Coordenadas( Double.parseDouble( activity_latitude ), Double.parseDouble( activity_longitude ) );

                                Double activity_price = jsonObject.getDouble( "activity_price" );
                                textviewPrice.setText( activity_price.toString() );

                                String activity_phone = jsonObject.getString( "activity_phone" );
                                textviewPhone.setText( activity_phone );

                                String activity_timetable = jsonObject.getString( "activity_timetable" );
                                textviewTimetable.setText( activity_timetable );

                                String activity_description = jsonObject.getString( "activity_description" );
                                textviewDescription.setText( activity_description );

                                String activity_url = jsonObject.getString( "activity_url" );
                                textviewURL.setText( activity_url );

                                //CONTADOR PARA VER CUANTOS ENCUENTRA
                                contador++;
                                System.out.println( contador );

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override

                    public void onErrorResponse(VolleyError error) {

                    }

                } ) {

            @Override
            public int getMethod() {
                return Method.POST;
            }

            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue( getApplicationContext() );
        requestQueue.add( request );
    }

    //DECODIFCA LA IMAGEN QUE RECIBE DE LA BD EN FORMATO STRING
    public Bitmap decodeImage(String picture) {

        byte[] imageBytes;
        imageBytes = Base64.decode( picture, Base64.DEFAULT );
        Bitmap decodedImage = BitmapFactory.decodeByteArray( imageBytes, 0, imageBytes.length );
        return decodedImage;
    }

    //METODOS PARA IMPLEMENTACION DEL MAPVIEW
    @Override
    public void onMapReady(GoogleMap googleMap) {

        // Controles UI
        if (ContextCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION )
                == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled( true );
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale( this,
                    Manifest.permission.ACCESS_FINE_LOCATION )) {
                // Mostrar diálogo explicativo
            } else {
                // Solicitar permiso
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE );
            }
        }

        Double latitud = coordenada.getLatitude();
        Double longitud = coordenada.getLongitude();
        LatLng coordenadas = new LatLng( latitud,longitud );
        System.out.println( coordenadas.toString() );


        googleMap.getUiSettings().setZoomControlsEnabled( true );
        googleMap.setMapType( GoogleMap.MAP_TYPE_HYBRID );

        googleMap.addMarker( new MarkerOptions()
                .position( coordenadas )
                .title( activity_title ) );

        CameraPosition cameraPosition = CameraPosition.builder()
                .target( coordenadas )
                .zoom( 15 )
                .build();

        googleMap.moveCamera( CameraUpdateFactory.newCameraPosition( cameraPosition ) );

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (permissions.length > 0 &&
                    permissions[0].equals( Manifest.permission.ACCESS_FINE_LOCATION ) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                googleMap.setMyLocationEnabled( true );
            } else {
                Toast.makeText( this, "Error de permisos", Toast.LENGTH_LONG ).show();
            }

        }
    }

}
