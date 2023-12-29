package com.proyect.trigal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ActRegistro extends AppCompatActivity {
    private EditText edtNomU, edtResU, edtCelU, edtUsu, edtConU,edtNumDoc;
    private Spinner spTipoDoc;
    private String[] tpD = {"RUC", "DNI"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_registrar_cliente);
        asignarReferencia();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    public void asignarReferencia() {
        edtNomU = findViewById(R.id.edtNomU);
        edtResU = findViewById(R.id.edtResU);
        edtCelU = findViewById(R.id.edtCelU);
        edtUsu = findViewById(R.id.edtUsu);
        edtConU = findViewById(R.id.edtConU);
        edtNumDoc=findViewById(R.id.edtNumDoc);
        spTipoDoc = findViewById(R.id.spTipoDoc);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tpD);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoDoc.setAdapter(adapter);
    }

    public void onRegistrarClick(View view) {
        validarCampos();
    }

    private void validarCampos() {
        boolean camposCompletos = true;

        String nombre = edtNomU.getText().toString().trim();
        String responsable = edtResU.getText().toString().trim();
        String celular = edtCelU.getText().toString().trim();
        String usuario = edtUsu.getText().toString().trim();
        String contra = edtConU.getText().toString().trim();
        String numeroDoc=edtNumDoc.getText().toString().trim();
        String tipoDocumento = spTipoDoc.getSelectedItem().toString();

        if (nombre.isEmpty()) {
            edtNomU.setError("Campo obligatorio");
            camposCompletos = false;
        }

        if (responsable.isEmpty()) {
            edtResU.setError("Campo obligatorio");
            camposCompletos = false;
        }

        if (celular.isEmpty()) {
            edtCelU.setError("Campo obligatorio");
            camposCompletos = false;

        } else if (celular.length() != 9) {
            edtCelU.setError("El celular debe tener 9 dígitos");
            camposCompletos = false;

        }

        if (usuario.isEmpty()) {
            edtUsu.setError("Campo obligatorio");
            camposCompletos = false;
        }

        if (contra.isEmpty()) {
            edtConU.setError("Campo obligatorio");
            camposCompletos = false;
        }

        if (tipoDocumento.isEmpty()) {
            Toast.makeText(this, "Tipo de documento es un campo obligatorio", Toast.LENGTH_SHORT).show();
            camposCompletos = false;
        }

        if (numeroDoc.isEmpty()) {
            edtNumDoc.setError("Campo obligatorio");
            camposCompletos = false;
        } else if (tipoDocumento.equalsIgnoreCase("RUC") && numeroDoc.length() != 11) {
            edtNumDoc.setError("El RUC debe tener 11 dígitos");
            camposCompletos = false;
        } else if (tipoDocumento.equalsIgnoreCase("DNI") && numeroDoc.length() != 8) {
            edtNumDoc.setError("El DNI debe tener 8 dígitos");
            camposCompletos = false;
        }

        if (!camposCompletos) {
            Toast.makeText(this, "No se pudo registrar ,Verificar los campos", Toast.LENGTH_SHORT).show();
        } else {
            mostrarDialogo();
        }
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

        setError("Registro Exitoso", "Usuario registrado correctamente");
        limpiarCampos();
    }

    private void setError(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void limpiarCampos() {
        edtNomU.getText().clear();
        edtResU.getText().clear();
        edtCelU.getText().clear();
        edtUsu.getText().clear();
        edtConU.getText().clear();
        edtNumDoc.getText().clear();
        spTipoDoc.setSelection(0);
    }
}
