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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectonavigation.R;

import java.util.HashMap;
import java.util.Map;

public class DialogFragmentUsername extends DialogFragment {

    //VARIABLES POR DEFECTO AL CREAR EL FRAGMENT
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    //VARIABLES DE LOS WIDGETS
    private TextView enterName;
    private Button save;
    private Button cancel;
    private String email;
    private String name;
    String url_updateName = "https://proyectogrupodapp.000webhostapp.com/users/user_data_queries/ChangeUsername.php";

    public DialogFragmentUsername() {
        // Required empty public constructor
    }

    //METODO POR DEFECTO AL CREAR FRAGMENT
    public static DialogFragmentUsername newInstance(String param1, String param2) {
        DialogFragmentUsername fragment = new DialogFragmentUsername();
        Bundle args = new Bundle();
        args.putString( ARG_PARAM1, param1 );
        args.putString( ARG_PARAM2, param2 );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.fragment_dialog_username, container, false );

        //EDITTEXT PARA INTRODUCIR EL NUEVO NOMBRE
        enterName = view.findViewById( R.id.editTextNewName );
        enterName.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        } );

        Intent intent = getActivity().getIntent();
        email = intent.getStringExtra( "email" );


        //boton guardar
        save = view.findViewById( R.id.btnDone );
        save.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = enterName.getText().toString().trim();

                if (!name.isEmpty()) {
                    changeName();
                    enterName.setText( "" );
                    getDialog().hide();
                } else {
                    enterName.setError( "Introduzca su  nuevo nombre" );
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

    public void changeName() {

        StringRequest stringRequest = new StringRequest( Request.Method.POST, url_updateName,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains( "1" )) {
                            Toast.makeText( getContext(), "Nombre actualizado con exito", Toast.LENGTH_SHORT ).show();
                        } else {
                            Toast.makeText( getContext(), "La actualizacion fallo", Toast.LENGTH_SHORT ).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText( getContext(), "Response Error " + error.toString(), Toast.LENGTH_SHORT ).show();
            }
        } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put( "email", email.toString().trim() );
                params.put( "name", name );
                return params;
            }
        };
        Volley.newRequestQueue( getContext() ).add( stringRequest );
    }
}

