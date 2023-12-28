package com.proyect.trigal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ActOpcionAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_vista_admin_trigal);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        CardView cardView1 = findViewById(R.id.clientesCard);
        CardView cardView2 = findViewById(R.id.reservasCard);
        CardView cardView3 = findViewById(R.id.reservasCard2);

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActOpcionAdmin.this, ActRegistro.class);
                startActivity(intent);
            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActOpcionAdmin.this, ActReservarSala.class);
                startActivity(intent);
            }
        });


        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActOpcionAdmin.this, ActRegistro.class);
                startActivity(intent);
            }
        });


    }
}
