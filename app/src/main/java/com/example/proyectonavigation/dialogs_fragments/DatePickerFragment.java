package com.example.proyectonavigation.dialogs_fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.DatePicker;
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

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private DatePickerDialog.OnDateSetListener listener;

    public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setListener(listener);
        return fragment;
    }

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        final String selectedDate = year + "-" + (month+1) + "-" + day;
        updateBirthday(selectedDate);
        Toast.makeText(getActivity(), "Fecha cambiada con exito", Toast.LENGTH_LONG).show();
    }

    public void updateBirthday(final String selectedDate) {

        Intent intent = getActivity().getIntent();
        final String email = intent.getStringExtra( "email" );

        String url_updateBirth = "https://proyectogrupodapp.000webhostapp.com/users/user_data_queries/ChangeBirthday.php";

        StringRequest stringRequest = new StringRequest( Request.Method.POST, url_updateBirth,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains( "1" )) {

                        } else {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put( "email", email );
                params.put( "birthday", selectedDate );
                return params;
            }
        };
        Volley.newRequestQueue( getContext() ).add( stringRequest );
    }
}
