package com.example.proyectonavigation;

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

import java.util.HashMap;
import java.util.Map;

public class DialogFragmentEmail extends DialogFragment {

    //VARIABLES POR DEFECTO AL CREAR EL FRAGMENT
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    //VARIABLES DE LOS WIDGETS
    private TextView enterMail;
    private Button save;
    private Button cancel;
    private String email;

    public DialogFragmentEmail() {
        // Required empty public constructor
    }

    public static DialogFragmentEmail newInstance(String param1, String param2) {
        DialogFragmentEmail fragment = new DialogFragmentEmail();
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
        View view = inflater.inflate( R.layout.fragment_dialog_email, container, false );

        //EDITTEXT PARA INTRODUCIR EL NUEVO MAIL
        enterMail = view.findViewById( R.id.editTextNewMail );
        enterMail.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        } );

        //boton guardar
        save = view.findViewById( R.id.btnDone );
        save.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeEmail();
                enterMail.setText( "" );
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
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

    public void changeEmail() {

        Intent intent = getActivity().getIntent();
        email = intent.getStringExtra( "email" );
        final String newEmail = this.enterMail.getText().toString().trim();
        String url_update = "https://proyectogrupodapp.000webhostapp.com/users/ChangeEmail.php";

        StringRequest stringRequest = new StringRequest( Request.Method.POST, url_update,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains( "1" )) {
                            Toast.makeText( getContext(), "Email actualizado con exito", Toast.LENGTH_SHORT ).show();
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
                params.put( "newEmail", newEmail );
                return params;
            }
        };
        Volley.newRequestQueue( getContext() ).add( stringRequest );
    }
}
