package com.proyect.trigal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ActLogin extends AppCompatActivity {
    EditText edtCorreo, edtContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_login_trigal);
        asignarReferencias();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    private void asignarReferencias() {
        edtCorreo = findViewById(R.id.edtCorreo);
        edtContraseña = findViewById(R.id.edtContraseña);

        TextView txtLogin = findViewById(R.id.txtLogin);

        ImageView imgFacebook = findViewById(R.id.imgFb);
        ImageView imgInstagram = findViewById(R.id.imgIg);

        imgFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirUrl("https://www.facebook.com/search/top?q=trigal%20coworking");
            }
        });

        imgInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirUrl("https://www.instagram.com/trigalcoworking/");
            }
        });



        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirvistaAdmin();
            }
        });
    }

    private void abrirvistaAdmin() {
        Intent intent = new Intent(ActLogin.this, ActAdmin.class);
        startActivity(intent);
    }

    public void IniciarSesion(View view) {
        String correo, contra;
        correo = edtCorreo.getText().toString();
        contra = edtContraseña.getText().toString();

        if (correo.equals("1") && contra.equals("1")) {
            Intent intent = new Intent(ActLogin.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(ActLogin.this, "Usuario no válido", Toast.LENGTH_SHORT).show();
        }
    }

    private void abrirUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
