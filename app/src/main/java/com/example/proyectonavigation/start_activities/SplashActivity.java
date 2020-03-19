package com.example.proyectonavigation.start_activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectonavigation.R;

public class SplashActivity extends AppCompatActivity {

    private AnimationDrawable animation;
    private ImageView loading;
    private Animation transition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        View decorView = getWindow().getDecorView();
        //La bandera View.SYSTEM_UI_FLAG_HIDE_NAVIGATION permite ocultar el menú de navegación típico, y la bandera View.SYSTEM_UI_FLAG_FULLSCREEN activa el modo fullscreen.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        //oculta la varra superior
        getSupportActionBar().hide();
        setContentView( R.layout.activity_splash );

        loading = findViewById(R.id.loading);
        loading.setBackgroundResource(R.drawable.loading );

        //El objeto AnimationDrawable se encargará de la animación de la imagen, y el objeto Animation de la transición de una activity hacia otra.
        animation = (AnimationDrawable) loading.getBackground();
        animation.start();
        transition = AnimationUtils.loadAnimation(this,R.anim.transition );
        loading.startAnimation( transition );
        transition.setAnimationListener( new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                nextActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void nextActivity(){
        animation.stop(); //Paramos el AnimationDrawable
        Intent intent = new Intent(this, LoginActivity.class); // Lanzamos Siguiente Activity
        startActivity(intent);
        finish(); //Finalizamos este activity
    }
}
