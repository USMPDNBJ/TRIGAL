package com.proyect.trigal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActLogin extends AppCompatActivity {
    EditText edtCorreo,edtContraseña;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_login_trigal);
        asignarReferencias();
    }
    private void asignarReferencias() {
        edtCorreo = findViewById(R.id.edtCorreo);
        edtContraseña = findViewById(R.id.edtContraseña);
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
}
