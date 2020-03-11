package com.example.proyectonavigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText name, email, password, c_password;
    private Button btn_regist;
    private TextView back_login;

    private static String URL_REGIST = "https://proyectogrupodapp.000webhostapp.com/users/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );

        getSupportActionBar().hide();

        name = findViewById( R.id.name );
        email = findViewById( R.id.email );
        password = findViewById( R.id.password );
        c_password = findViewById( R.id.c_password );
        btn_regist = findViewById( R.id.btn_regist );

        back_login = findViewById( R.id.loginTxt );

        back_login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( RegisterActivity.this, LoginActivity.class ) );
            }
        } );

        btn_regist.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mName = name.getText().toString().trim();
                String mEmail = email.getText().toString().trim();
                String mPassword = password.getText().toString().trim();
                String mCPassword = c_password.getText().toString().trim();

                if (!mName.isEmpty()) {
                    if (!mEmail.isEmpty()) {
                        if (!mPassword.isEmpty()) {
                            if (!mCPassword.isEmpty()) {
                                Regist();
                            }
                        }
                    }
                } else {
                    name.setError( "Introduzca su nombre " );
                    email.setError( "Introduzca su email" );
                    password.setError( "Introduzca su password" );
                    c_password.setError( "Confirme su password" );
                }
            }
        } );
    }

    private void Regist() {

        btn_regist.setVisibility( View.GONE );

        final String name = this.name.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();

        //Variables para el metodo de encriptado hash
        byte[] passwordByte = hash( password );
        final String passwordHash = byteToHex( passwordByte );

        StringRequest stringRequest = new StringRequest( Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            String success = jsonObject.getString( "success" );

                            if (success.equals( "1" )) {
                                btn_regist.setVisibility( View.VISIBLE );
                                Toast.makeText( RegisterActivity.this, "Registro Completo", Toast.LENGTH_SHORT ).show();
                                Intent intent = new Intent( RegisterActivity.this, PreferencesActivity.class );
                                intent.putExtra( "email", email);
                                intent.putExtra( "name", name );
                                startActivityForResult( intent, 0 );
                            }
                            else{
                                Toast.makeText( RegisterActivity.this, "Registro Fallido", Toast.LENGTH_SHORT ).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText( RegisterActivity.this, "JSON Error "
                                    + e.toString(), Toast.LENGTH_SHORT ).show();
                            btn_regist.setVisibility( View.VISIBLE );
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText( RegisterActivity.this, "Response Error "
                                + error.toString(), Toast.LENGTH_SHORT ).show();
                        btn_regist.setVisibility( View.VISIBLE );
                    }
                } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put( "name", name );
                params.put( "email", email );
                //Se le añade la variable nueva de password con hash AÑADIDO DIA 24/01
                params.put( "password", passwordHash );
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue( this );
        requestQueue.add( stringRequest );

    }

    //Metodo que recibe el password desde el textview y lo convierte a hash
    public static byte[] hash(String password) {

        byte[] hash = null;
        try {
            byte[] data = password.getBytes();
            MessageDigest md = MessageDigest.getInstance( "SHA-256" );
            hash = md.digest( data );
        } catch (Exception ex) {

        }
        return hash;
    }

    //Metodo que convierte el password con hash de nuevo a String
    public static String byteToHex(byte[] bytes) {
        BigInteger bi = new BigInteger( 1, bytes );
        return String.format( "%0" + (bytes.length << 1) + "X", bi );
    }
}