package com.proyect.trigal;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class ActSplash2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_splash_reservas);

        ImageView logoImageView = findViewById(R.id.imageView3);

        // Cargar la animación de escala desde el archivo XML
        Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_animation);

        // Aplicar la animación de escala a la vista del logo
        logoImageView.startAnimation(scaleAnimation);

        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(ActSplash2.this, ActReservar.class);
                startActivity(intent);
                finish();
            }
        };

        Timer tiempo = new Timer();
        tiempo.schedule(tarea, 3000);
    }
}
