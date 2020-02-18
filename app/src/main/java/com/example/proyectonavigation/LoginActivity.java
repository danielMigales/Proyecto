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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button btn_login;
    private TextView link_regist;
    private static String URL_LOGIN = "https://proyectogrupod.000webhostapp.com/register_php/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        link_regist = findViewById(R.id.registerTxt);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mEmail = email.getText().toString().trim();
                String mPassword = password.getText().toString().trim();

                if (!mEmail.isEmpty() || !mPassword.isEmpty()) {
                    Login();

                } else {
                    email.setError("Email is missing");
                    password.setError("Password is missing");
                }
            }
        });

        link_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void Login() {

        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();

        //Variables para el metodo de encriptado hash AÑADIDO DIA 24/01
        byte[] passwordByte = hash(password);
        final String passwordHash = byteToHex(passwordByte);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("1")) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Response Error "
                        + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email.toString().trim());
                //Se le añade la variable nueva de password con hash AÑADIDO DIA 24/01
                params.put("password", passwordHash);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }

    //Metodo que recibe el password desde el textview y lo convierte a hash AÑADIDO DIA 24/01
    public static byte[] hash(String password) {

        byte[] hash = null;
        try {
            byte[] data = password.getBytes();
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            hash = md.digest(data);
        } catch (Exception ex) {

        }
        return hash;
    }

    //Metodo que convierte el password con hash de nuevo a String AÑADIDO DIA 24/01
    public static String byteToHex(byte[] bytes) {
        BigInteger bi = new BigInteger(1, bytes);
        return String.format("%0" + (bytes.length << 1) + "X", bi);
    }
}
