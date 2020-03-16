package com.example.proyectonavigation.ui.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

public class CardActivity extends AppCompatActivity {

    ImageView imageViewPicture;
    TextView textViewTitulo;
    TextView textviewAddress;
    TextView textviewPrice;
    TextView textviewPhone;
    TextView textviewTimetable;
    TextView textviewDescription;
    TextView textviewURL;
    int activityID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_card );

        getSupportActionBar().hide();

        //INTENT PARA OBTENER EL EMAIL DEL USUARIO QUE SE USA EN EL SELECT DE LA BD PARA CONSEGUIR EL RESTO DE DATOS
        Intent intent = getIntent();
        activityID = intent.getIntExtra("activity_id",activityID );

        //WIDGETS DE LA ACTIVITY
        imageViewPicture = findViewById( R.id.imageViewPicture );
        textViewTitulo = findViewById( R.id.textViewTitulo );
        textviewAddress = findViewById( R.id.textviewAddress );
        textviewPrice = findViewById( R.id.textviewPrice );
        textviewPhone = findViewById( R.id.textviewPhone );
        textviewTimetable = findViewById( R.id.textviewTimetable );
        textviewDescription = findViewById( R.id.textviewDescription );
        textviewURL = findViewById( R.id.textViewURL );

        getData();


    }

    //OBTENCION DE LOS DATOS DE LA BASE DE DATOS
    public void getData() {

        String url_getPlans = "https://proyectogrupodapp.000webhostapp.com/users/get_plan_activity.php?id=" + activityID + "";

        JsonArrayRequest request = new JsonArrayRequest( Request.Method.POST, url_getPlans, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray jsonArray) {

                        try {
                            int contador = 0;

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject( i );

                                //OBTENCION DE TODOS LOS CAMPOS DE LA TABLA
                                //int id = jsonObject.getInt( "id" );
                                //String activity_name = jsonObject.getString( "activity_name" );
                                String activity_title = jsonObject.getString( "activity_title" );
                                textViewTitulo.setText( activity_title );

                                String activity_picture_1 = jsonObject.getString( "activity_picture_1" );
                                Bitmap decoded_activity_picture = decodeImage( activity_picture_1 );
                                imageViewPicture.setImageBitmap( decoded_activity_picture );

                                String activitiy_address = jsonObject.getString( "activity_address" );
                                textviewAddress.setText( activitiy_address );
                                System.out.println(activitiy_address);

                                Double activity_price = jsonObject.getDouble( "activity_price" );
                                textviewPrice.setText( activity_price.toString() );

                                String activity_phone = jsonObject.getString( "activity_phone" );
                                textviewPhone.setText( activity_phone );
                                System.out.println(activity_phone);
                                String activity_timetable = jsonObject.getString( "activity_timetable" );
                                textviewTimetable.setText( activity_timetable );

                                String activity_description = jsonObject.getString( "activity_description" );
                                textviewDescription.setText( activity_description );
                                System.out.println(activity_description);

                                String activity_url = jsonObject.getString( "activity_url" );
                                textviewURL.setText( activity_url );
                                System.out.println(activity_url);

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
}
