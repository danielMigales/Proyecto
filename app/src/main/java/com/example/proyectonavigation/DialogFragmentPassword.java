package com.example.proyectonavigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

public class DialogFragmentPassword extends DialogFragment {

    //VARIABLES POR DEFECTO AL CREAR EL FRAGMENT
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    //VARIABLES DE LOS WIDGETS
    private TextView enterPassword;
    private Button save;
    private Button cancel;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_dialog_password, container, false );

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
}
