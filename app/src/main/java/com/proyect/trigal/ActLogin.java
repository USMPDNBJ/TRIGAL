package com.proyect.trigal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ActLogin extends AppCompatActivity {
    private EditText edtCorreo, edtContraseña;

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

        imgFacebook.setOnClickListener(v -> abrirUrl("https://www.facebook.com/search/top?q=trigal%20coworking"));

        imgInstagram.setOnClickListener(v -> abrirUrl("https://www.instagram.com/trigalcoworking/"));

        txtLogin.setOnClickListener(v -> abrirVistaAdmin());
    }

    private void abrirVistaAdmin() {
        Intent intent = new Intent(ActLogin.this, ActAdmin.class);
        startActivity(intent);
    }

    public void IniciarSesion(View view) {
        String correo = edtCorreo.getText().toString().trim();
        String contra = edtContraseña.getText().toString().trim();

        // Limpiar errores previos
        edtCorreo.setError(null);
        edtContraseña.setError(null);

        // Verificar si algún campo está vacío
        if (TextUtils.isEmpty(correo)) {
            edtCorreo.setError("Por favor, ingresa tu correo");
            return;
        }

        if (TextUtils.isEmpty(contra)) {
            edtContraseña.setError("Por favor, ingresa tu contraseña");
            return;
        }

        if (validarCredenciales(correo, contra)) {
            // Verificar si es un usuario y contraseña válidos (solo para ejemplo)
            Intent intent = new Intent(ActLogin.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(ActLogin.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validarCredenciales(String correo, String contra) {
        // Lógica para validar las credenciales (ejemplo)
        return correo.equals("1") && contra.equals("1");
    }

    private void abrirUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
