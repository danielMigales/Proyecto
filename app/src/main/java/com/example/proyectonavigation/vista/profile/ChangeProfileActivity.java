package com.example.proyectonavigation.vista.profile;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.proyectonavigation.R;
import com.example.proyectonavigation.modelo.activities.dialogs_fragments.DatePickerFragment;
import com.example.proyectonavigation.modelo.activities.dialogs_fragments.DialogFragmentEmail;
import com.example.proyectonavigation.modelo.activities.dialogs_fragments.DialogFragmentPassword;
import com.example.proyectonavigation.modelo.activities.dialogs_fragments.DialogFragmentUsername;
import com.example.proyectonavigation.modelo.activities.preferencias.BooksPreferencesActivity;
import com.example.proyectonavigation.modelo.activities.preferencias.CinemaPreferencesActivity;
import com.example.proyectonavigation.modelo.activities.preferencias.CulturePreferencesActivity;
import com.example.proyectonavigation.modelo.activities.preferencias.FoodPreferencesActivity;
import com.example.proyectonavigation.modelo.activities.preferencias.MusicPreferencesActivity;
import com.example.proyectonavigation.modelo.activities.preferencias.OutdoorPreferencesActivity;
import com.example.proyectonavigation.modelo.activities.preferencias.SportsPreferencesActivity;
import com.example.proyectonavigation.modelo.activities.preferencias.TVShowsPreferencesActivity;
import com.example.proyectonavigation.modelo.activities.preferencias.VideoGamesPreferencesActivity;
import com.example.proyectonavigation.modelo.activities.start_activities.LoginActivity;

public class ChangeProfileActivity extends AppCompatActivity {

    private Button editCinema;
    private Button editfood;
    private Button editMusic;
    private Button editOutdoor;
    private Button editTVShows;
    private Button editCulture;
    private Button editBooks;
    private Button editVideoGames;
    private Button editSports;
    private Button ChangeName;
    private Button ChangeEmail;
    private Button ChangePassword;
    private Button ChangeBirthday;
    private Button logout;
    private String email;


    public void dialog() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag( "dialog" );
        if (prev != null) {
            ft.remove( prev );
        }
        ft.addToBackStack( null );
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_change_profile );
        getSupportActionBar().hide();

        Intent intent = getIntent();
        email = intent.getStringExtra( "email" );

        //ESTE BLOQUE SON TODOS LOS BOTONES QUE HAY EN EL SCROLLHORIZONTAL Y QUE REDIRIGEN HACIA LAS ACTIVITIES PARA SELECCIONAR PREFERENCIAS
        editCinema = findViewById( R.id.editCinema );
        editCinema.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), CinemaPreferencesActivity.class );
                startActivityForResult( intent, 0 );
            }
        } );

        editfood = findViewById( R.id.editFood );
        editfood.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), FoodPreferencesActivity.class );
                startActivityForResult( intent, 0 );
            }
        } );

        editMusic = findViewById( R.id.editMusic );
        editMusic.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), MusicPreferencesActivity.class );
                intent.putExtra( "email", email );
                startActivityForResult( intent, 0 );
            }
        } );

        editTVShows = findViewById( R.id.editTVShows );
        editTVShows.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), TVShowsPreferencesActivity.class );
                startActivityForResult( intent, 0 );
            }
        } );

        editOutdoor = findViewById( R.id.editOutdoor );
        editOutdoor.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), OutdoorPreferencesActivity.class );
                startActivityForResult( intent, 0 );
            }
        } );

        editCulture = findViewById( R.id.editCulture );
        editCulture.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), CulturePreferencesActivity.class );
                startActivityForResult( intent, 0 );
            }
        } );

        editBooks = findViewById( R.id.editBooks );
        editBooks.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), BooksPreferencesActivity.class );
                startActivityForResult( intent, 0 );
            }
        } );

        editVideoGames = findViewById( R.id.editVideoGames );
        editVideoGames.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), VideoGamesPreferencesActivity.class );
                startActivityForResult( intent, 0 );
            }
        } );

        editSports = findViewById( R.id.editSports );
        editSports.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), SportsPreferencesActivity.class );
                startActivityForResult( intent, 0 );
            }
        } );


        //CAMBIAR NOMBRE DE USUARIO
        ChangeName = findViewById( R.id.buttonName );
        ChangeName.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
                DialogFragment dialogFragment = new DialogFragmentUsername();
                dialogFragment.show( getSupportFragmentManager(), "dialog" );
            }
        } );

        //CAMBIAR EMAIL
        ChangeEmail = findViewById( R.id.buttonEmail );
        ChangeEmail.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
                DialogFragment dialogFragment = new DialogFragmentEmail();
                dialogFragment.show( getSupportFragmentManager(), "dialog" );
            }
        } );

        //CAMBIAR PASSWORD
        ChangePassword = findViewById( R.id.buttonPassword );
        ChangePassword.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
                DialogFragment dialogFragment = new DialogFragmentPassword();
                dialogFragment.show( getSupportFragmentManager(), "dialog" );
            }
        } );

        //CAMBIAR NACIMIENTO
        ChangeBirthday = findViewById( R.id.buttonBirthday );
        ChangeBirthday.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        } );

        //CERRAR SESION
        logout = findViewById( R.id.buttonLogOut );
        logout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), LoginActivity.class );
                intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                intent.putExtra( "EXIT", true );
                startActivity( intent );
            }
        } );
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance( new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Toast.makeText( getApplicationContext(), "Fecha cambiada con exito", Toast.LENGTH_SHORT ).show();
            }
        } );
        newFragment.show( getSupportFragmentManager(), "datePicker" );
    }


}



