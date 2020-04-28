package com.example.proyectonavigation.modelo.activities.dialogs_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.proyectonavigation.R;
import com.example.proyectonavigation.vista.search.SearchFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DialogFragmentRatingActivity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DialogFragmentRatingActivity extends DialogFragment implements
        View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private RatingBar ratingBar;

    public DialogFragmentRatingActivity() {
        // Required empty public constructor
    }

    public static DialogFragmentRatingActivity newInstance(String param1, String param2) {
        DialogFragmentRatingActivity fragment = new DialogFragmentRatingActivity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_rating_activity, container, false);
        // Inflate the layout for this fragment

        ratingBar = view.findViewById(R.id.ratingBar);
        int rating = ratingBar.getNumStars();
        String ratingString = Integer.toString(rating);
        Intent intent = new Intent(getActivity().getBaseContext(), SearchFragment.class);
        intent.putExtra("ratingbarActivity", ratingString);
        startActivity(intent);

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
