package com.example.proyectonavigation.ui.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectonavigation.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private MapViewModel mViewModel;
    MapView mapView;
    GoogleMap googleMap;
    private static final int LOCATION_REQUEST_CODE = 1;


    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider( this ).get( MapViewModel.class );
        View view = inflater.inflate( R.layout.fragment_map, container, false );


        mapView = view.findViewById( R.id.mapViewFragment );
        mapView.onCreate( savedInstanceState );
        mapView.getMapAsync( (OnMapReadyCallback) this );

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated( savedInstanceState );
        mViewModel = new ViewModelProvider( this ).get( MapViewModel.class );
    }

    //METODOS PARA IMPLEMENTACION DEL MAPVIEW
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        // Controles UI
        if (ContextCompat.checkSelfPermission( getContext(), Manifest.permission.ACCESS_FINE_LOCATION )
                == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled( true );
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale( getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION )) {
                // Mostrar diálogo explicativo
            } else {
                // Solicitar permiso
                ActivityCompat.requestPermissions( getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE );
            }
        }

        String url = "https://proyectogrupodapp.000webhostapp.com/plans/plans_queries/get_coordenates.php";
        JsonArrayRequest request = new JsonArrayRequest( Request.Method.POST, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {
                            int contador = 0;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject( i );
                                String activity_name = jsonObject.getString( "activity_name" );
                                double activity_latitude = jsonObject.getDouble( "activity_latitude" );
                                double activity_longitude = jsonObject.getDouble( "activity_longitude" );
                                //OBJETO DE COORDENADAS CON LOS DATOS DE LA CONSULTA
                                LatLng latilongi = new LatLng( activity_latitude, activity_longitude );
                                googleMap.getUiSettings().setZoomControlsEnabled( true );
                                googleMap.setMapType( GoogleMap.MAP_TYPE_HYBRID );
                                googleMap.addMarker( new MarkerOptions()

                                        .position( latilongi )
                                        .icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_YELLOW ) )
                                        .title( activity_name ) );
                                CameraPosition cameraPosition = CameraPosition.builder()
                                        .target( latilongi )
                                        .zoom( 15 )
                                        .build();
                                googleMap.moveCamera( CameraUpdateFactory.newCameraPosition( cameraPosition ) );
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
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue( getContext() );
        requestQueue.add( request );
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (permissions.length > 0 &&
                    permissions[0].equals( Manifest.permission.ACCESS_FINE_LOCATION ) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText( getContext(), "Error de permisos", Toast.LENGTH_LONG ).show();
            }
        }
    }

}
