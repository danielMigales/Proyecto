package com.example.proyectonavigation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class DialogFragmentBirthday extends DialogFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    CalendarView calendarView;
    TextView dateView;
    private Button ok;
    private Button cancel;
    private String email;
    String url_updateBirth = "https://proyectogrupodapp.000webhostapp.com/users/ChangeBirthday.php";
    private String birthday;

    public DialogFragmentBirthday() {
        // Required empty public constructor
    }

    public static DialogFragmentBirthday newInstance(String param1, String param2) {
        DialogFragmentBirthday fragment = new DialogFragmentBirthday();
        Bundle args = new Bundle();
        args.putString( ARG_PARAM1, param1 );
        args.putString( ARG_PARAM2, param2 );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        if (getArguments() != null) {
            mParam1 = getArguments().getString( ARG_PARAM1 );
            mParam2 = getArguments().getString( ARG_PARAM2 );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_dialog_birthday, container, false );

        Intent intent = getActivity().getIntent();
        email = intent.getStringExtra( "email" );

        calendarView = view.findViewById( R.id.calendarView );
        calendarView.setFocusedMonthDateColor( Color.BLUE );
        dateView = view.findViewById( R.id.dateView );
        calendarView.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                birthday = year + "-" + (month + 1) + "-" + dayOfMonth;
                dateView.setText( birthday );
            }
        } );

        //boton guardar
        ok = view.findViewById( R.id.buttonOK );
        ok.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBirthday();
                getDialog().hide();
            }
        } );

        //BOTON CANCELAR
        cancel = view.findViewById( R.id.buttonCancel );
        cancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        } );

        return view;
    }

    public void changeBirthday() {

        StringRequest stringRequest = new StringRequest( Request.Method.POST, url_updateBirth,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains( "1" )) {
                            Toast.makeText( getContext(), "Fecha de nacimiento actualizada con exito", Toast.LENGTH_SHORT ).show();
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
                params.put( "email", email );
                params.put( "birthday", birthday);
                return params;
            }
        };
        Volley.newRequestQueue( getContext() ).add( stringRequest );
    }
}
