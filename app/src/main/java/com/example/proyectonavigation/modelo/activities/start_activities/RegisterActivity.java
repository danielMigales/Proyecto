package com.example.proyectonavigation.modelo.activities.start_activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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
import com.example.proyectonavigation.R;
import com.example.proyectonavigation.modelo.activities.preferencias.PreferencesActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private static String URL_REGIST = "https://proyectogrupodapp.000webhostapp.com/users/user_data_queries/register.php";
    boolean correoOk = false;
    private EditText name, email, password, c_password;
    private Button btn_regist;
    private TextView back_login;

    //Metodo que recibe el password desde el textview y lo convierte a hash
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

    //Metodo que convierte el password con hash de nuevo a String
    public static String byteToHex(byte[] bytes) {
        BigInteger bi = new BigInteger(1, bytes);
        return String.format("%0" + (bytes.length << 1) + "X", bi);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        c_password = findViewById(R.id.c_password);
        btn_regist = findViewById(R.id.btn_regist);
        back_login = findViewById(R.id.loginTxt);
        back_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mName = name.getText().toString().trim();
                String mEmail = email.getText().toString().trim();
                String mPassword = password.getText().toString().trim();
                String mCPassword = c_password.getText().toString().trim();

                if (!mName.isEmpty()) {
                    if (esNombreValido(mName)) {
                        if (!mEmail.isEmpty()) {
                            if (!mPassword.isEmpty()) {
                                if (!mCPassword.isEmpty()) {
                                    if (esCorreoValido(mEmail)) {
                                        comprobarDuplicados();
                                        if (correoOk = true) {
                                            Regist();
                                        } else {
                                            Toast.makeText(RegisterActivity.this, "Introduzca datos correctos", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } else {
                                    c_password.setError("Confirme su password");
                                }
                            } else {
                                password.setError("Introduzca su password");
                            }
                        } else {
                            email.setError("Introduzca su email");
                        }
                    }
                } else {
                    name.setError("Introduzca su nombre ");
                }
            }
        });
    }

    //VALIDACION DE NOMBRE DE USUARIO
    private boolean esNombreValido(String nombre) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        if (!patron.matcher(nombre).matches() || nombre.length() > 30) {
            name.setError("Nombre inválido");
            return false;
        } else {
            name.setError(null);
        }
        return true;
    }

    //VALIDACION DE CORREO ELECTRONICO
    private boolean esCorreoValido(String correo) {

        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            email.setError("Correo electrónico inválido");
            return false;
        } else {
            email.setError(null);
        }
        return true;
    }

    private void Regist() {

        btn_regist.setVisibility(View.GONE);
        final String name = this.name.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();

        //Variables para el metodo de encriptado hash
        byte[] passwordByte = hash(password);
        final String passwordHash = byteToHex(passwordByte);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                btn_regist.setVisibility(View.VISIBLE);
                                Toast.makeText(RegisterActivity.this, "Registro Completo", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, PreferencesActivity.class);
                                intent.putExtra("email", email);
                                intent.putExtra("name", name);
                                startActivityForResult(intent, 0);
                            } else {
                                Toast.makeText(RegisterActivity.this, "Registro Fallido", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "JSON Error "
                                    + e.toString(), Toast.LENGTH_SHORT).show();
                            btn_regist.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(RegisterActivity.this, "Response Error "
                                + error.toString(), Toast.LENGTH_SHORT).show();
                        btn_regist.setVisibility(View.VISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                //Se le añade la variable nueva de password con hash AÑADIDO DIA 24/01
                params.put("password", passwordHash);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private boolean comprobarDuplicados() {

        final String email = this.email.getText().toString().trim();
        String URL = "https://proyectogrupodapp.000webhostapp.com/users/user_data_queries/comprobarUsuario.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                correoOk = true;
                                Toast.makeText(RegisterActivity.this, "Direccion de correo correcta", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Su direccion de correo ya esta registrada", Toast.LENGTH_SHORT).show();
                                correoOk = false;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "JSON Error "
                                    + e.toString(), Toast.LENGTH_SHORT).show();
                            btn_regist.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "Response Error " + error.toString(), Toast.LENGTH_SHORT).show();
                        btn_regist.setVisibility(View.VISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        return correoOk;
    }

}