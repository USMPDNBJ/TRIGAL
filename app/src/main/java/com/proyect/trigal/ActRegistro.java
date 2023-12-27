package com.proyect.trigal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ActRegistro extends AppCompatActivity {

    private Spinner spTipoDoc;
    private String[] tpD = {"", "RUC", "DNI"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_registrar_cliente);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        spTipoDoc = findViewById(R.id.spTipoDoc);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tpD);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoDoc.setAdapter(adapter);
    }

    public void onRegistrarClick(View view) {
        mostrarDialogo();
    }

    private void mostrarDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Deseas registrar un nuevo cliente?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        registrarCliente();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void registrarCliente() {
        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
    }
}
