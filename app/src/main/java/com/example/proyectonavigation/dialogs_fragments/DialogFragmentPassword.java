package com.example.proyectonavigation.dialogs_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectonavigation.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class DialogFragmentPassword extends DialogFragment {

    //VARIABLES POR DEFECTO AL CREAR EL FRAGMENT
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String url_updatePassword = "https://proyectogrupodapp.000webhostapp.com/users/user_data_queries/ChangePassword.php";
    private String mParam1;
    private String mParam2;
    //VARIABLES DE LOS WIDGETS
    private TextView enterPassword;
    private Button save;
    private Button cancel;
    private String email;
    private String password;


    public DialogFragmentPassword() {
        // Required empty public constructor
    }

    public static DialogFragmentPassword newInstance(String param1, String param2) {
        DialogFragmentPassword fragment = new DialogFragmentPassword();
        Bundle args = new Bundle();
        args.putString( ARG_PARAM1, param1 );
        args.putString( ARG_PARAM2, param2 );
        fragment.setArguments( args );
        return fragment;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_dialog_password, container, false );

        Intent intent = getActivity().getIntent();
        email = intent.getStringExtra( "email" );


        //EDITTEXT PARA INTRODUCIR EL NUEVO PASSWORD
        enterPassword = view.findViewById( R.id.editTextNewPassword );
        enterPassword.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        } );

        //boton guardar
        save = view.findViewById( R.id.btnDone );
        save.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                password = enterPassword.getText().toString().trim();

                if (!password.isEmpty()) {
                    cambiarPassword();
                    enterPassword.setText( "" );
                    getDialog().hide();
                } else {
                    enterPassword.setError( "Introduzca su nueva contraseña" );
                }

            }
        } );

        //BOTON CANCELAR
        cancel = view.findViewById( R.id.btnExit );
        cancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        } );

        return view;
    }

    private void cambiarPassword() {

        //Variables para el metodo de encriptado hash
        byte[] passwordByte = hash( password );
        final String passwordHash = byteToHex( passwordByte );

        StringRequest stringRequest = new StringRequest( Request.Method.POST, url_updatePassword,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            String success = jsonObject.getString( "success" );

                            if (success.equals( "1" )) {
                                Toast.makeText( getContext(), "Password actualizado con exito", Toast.LENGTH_SHORT ).show();

                            } else {
                                Toast.makeText( getContext(), "Actualizacion fallida", Toast.LENGTH_SHORT ).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText( getContext(), "JSON Error " + e.toString(), Toast.LENGTH_SHORT ).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText( getContext(), "Response Error " + error.toString(), Toast.LENGTH_SHORT ).show();
                    }
                } ) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put( "email", email );
                //Se le añade la variable nueva de password con hash AÑADIDO DIA 24/01
                params.put( "password", passwordHash );
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue( getContext() );
        requestQueue.add( stringRequest );

    }

}
