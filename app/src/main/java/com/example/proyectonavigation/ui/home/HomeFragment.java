package com.example.proyectonavigation.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.proyectonavigation.CardActivity;
import com.example.proyectonavigation.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private CardView card1;
    private CardView card2;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        card1 = view.findViewById(R.id.cardview1);
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CardActivity.class);
                startActivityForResult(intent,0); }
        });







        return view;
    }
}