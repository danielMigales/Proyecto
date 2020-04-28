package com.example.proyectonavigation.vista.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectonavigation.R;

public class CalendarFragment extends Fragment {

    private CalendarViewModel calendarViewModel;

    public static CalendarFragment newInstance() {
        return new CalendarFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        calendarViewModel = new ViewModelProvider( this ).get( CalendarViewModel.class );
        View view = inflater.inflate( R.layout.calendar_fragment, container, false );

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated( savedInstanceState );
        calendarViewModel = new ViewModelProvider( this ).get( CalendarViewModel.class );
    }

}
