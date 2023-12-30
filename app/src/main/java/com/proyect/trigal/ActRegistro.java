package com.proyect.trigal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class ActRegistro extends AppCompatActivity {
    private final String[] tpD = {"", "DNI", "RUC"};
    private TextInputLayout inpNombre, inpResponsabe, inpCelular, inpUsuario, inpContraseña, inpNumDoc;
    private Spinner spTipoDoc;
    private Button btnAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_registrar_cliente);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        asignarReferencia();
    }

    public void asignarReferencia() {
//////////////////////////////////////////////
        inpNombre = findViewById(R.id.textInputNomU);
        inpNombre.setErrorTextAppearance(R.style.ErrorAppearance);
        inpNombre.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    inpNombre.setErrorEnabled(true);
                    inpNombre.setError("SEXO EN NEW YORK");
                } else {
                    inpNombre.setErrorEnabled(false);
                }
            }
        });
//////////////////////////////////////////////
        inpResponsabe = findViewById(R.id.textInputResU);
        inpResponsabe.setErrorTextAppearance(R.style.ErrorAppearance);
        inpResponsabe.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    inpResponsabe.setErrorEnabled(true);
                    inpResponsabe.setError("*CAMPO OBLIGATORIO");
                } else {
                    inpResponsabe.setErrorEnabled(false);
                }
            }
        });
//////////////////////////////////////////////
        spTipoDoc = findViewById(R.id.spTipoDoc);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tpD);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoDoc.setAdapter(adapter);
        spTipoDoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = tpD[position];
                int maxLength = 0;

                if (selectedItem.equals("DNI")) {
                    maxLength = 8;
                } else if (selectedItem.equals("RUC")) {
                    maxLength = 11;
                }
                inpNumDoc.setCounterMaxLength(maxLength);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                inpNumDoc.setCounterMaxLength(0);
            }
        });
        inpNumDoc = findViewById(R.id.textInputNumDoc);
        inpNumDoc.setErrorTextAppearance(R.style.ErrorAppearance);
        inpNumDoc.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString().trim();
                if (!TextUtils.isEmpty(input)) {
                    try {
                        long value = Long.parseLong(input);
                        if (input.length() != inpNumDoc.getCounterMaxLength()) {
                            inpNumDoc.setErrorEnabled(true);
                            if (inpNumDoc.getCounterMaxLength() == -1) {
                                inpNumDoc.setError("Seleccione un tipo de documento");
                            } else {
                                inpNumDoc.setError("Debe tener " + inpNumDoc.getCounterMaxLength() + " dígitos");
                            }
                        } else {
                            inpNumDoc.setErrorEnabled(false);
                        }
                    } catch (NumberFormatException e) {
                        inpNumDoc.setErrorEnabled(true);
                        inpNumDoc.setError("Debe ingresar un número válido");
                    }
                } else {
                    inpNumDoc.setErrorEnabled(false);
                }
            }

        });
//////////////////////////////////////////////
        inpCelular = findViewById(R.id.textInputCelU);
        inpCelular.setErrorTextAppearance(R.style.ErrorAppearance);
        inpCelular.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String num = s.toString().trim();
                if (!TextUtils.isEmpty(num)) {
                    try {
                        long valor = Long.parseLong(num);
                        if (num.length() != 9) {
                            inpCelular.setErrorEnabled(true);
                            inpCelular.setError("El número debe tener 9 dígitos");
                        } else {
                            inpCelular.setErrorEnabled(false);
                        }
                    } catch (NumberFormatException e) {
                        inpCelular.setErrorEnabled(true);
                        inpCelular.setError("Debe ingresar un número válido");
                    }
                } else {
                    // Si el campo está vacío, activar el error de campo obligatorio
                    inpCelular.setErrorEnabled(true);
                    inpCelular.setError("*CAMPO OBLIGATORIO");
                }
            }
        });

//////////////////////////////////////////////
        inpUsuario = findViewById(R.id.textInputUsu);
        inpUsuario.setErrorTextAppearance(R.style.ErrorAppearance);
        inpUsuario.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    inpUsuario.setErrorEnabled(true);
                    inpUsuario.setError("*CAMPO OBLIGATORIO");
                } else {
                    inpUsuario.setErrorEnabled(false);
                }
            }
        });
//////////////////////////////////////////////
        inpContraseña = findViewById(R.id.textInputContraseña);
        inpContraseña.setErrorTextAppearance(R.style.ErrorAppearance);
        inpContraseña.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = s.toString().trim();
                if (password.isEmpty()) {
                    inpContraseña.setErrorEnabled(true);
                    inpContraseña.setError("*CAMPO OBLIGATORIO");
                } else if (isValidPassword(password)) {
                    inpContraseña.setErrorEnabled(false);
                } else {
                    inpContraseña.setErrorEnabled(true);
                    inpContraseña.setError("Debe tener al menos 2 caracteres especiales y 8 dígitos");
                }
            }
        });

//////////////////////////////////////////////
        btnAgregar = findViewById(R.id.btnAgregar);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (camposVacios()) {
                    Toast.makeText(ActRegistro.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                } else if (hayErrores()) {
                    Toast.makeText(ActRegistro.this, "Solucione los errores para seguir el proceso", Toast.LENGTH_SHORT).show();
                } else {
                    mostrarDialogo();
                }
            }
        });
    }

    private boolean hayErrores() {
        return tieneError(inpNombre) || tieneError(inpResponsabe) || tieneError(inpNumDoc) || tieneError(inpCelular) || tieneError(inpUsuario) || tieneError(inpContraseña);
    }

    private boolean tieneError(TextInputLayout inputLayout) {
        String texto = inputLayout.getEditText().getText().toString().trim();
        if (TextUtils.isEmpty(texto)) {
            inputLayout.setErrorEnabled(true);
            inputLayout.setError("*CAMPO OBLIGATORIO");
            return true;
        } else if (inputLayout.getError() != null && !inputLayout.getError().toString().isEmpty()) {
            return true;
        } else {
            inputLayout.setErrorEnabled(false);
            return false;
        }
    }

    private boolean camposVacios() {
        String nombre = inpNombre.getEditText().getText().toString();
        String responsable = inpResponsabe.getEditText().getText().toString();
        String numDoc = inpNumDoc.getEditText().getText().toString();
        String celular = inpCelular.getEditText().getText().toString();
        String usuario = inpUsuario.getEditText().getText().toString();
        String contraseña = inpContraseña.getEditText().getText().toString();

        return nombre.isEmpty() && responsable.isEmpty() && numDoc.isEmpty() && celular.isEmpty() && usuario.isEmpty() && contraseña.isEmpty();
    }

    private boolean isValidPassword(@NonNull String password) {
        String specialChars = "[!@#$%^&*()_+\\-{}\\[\\]|;',./<>?`~\\\\:\"\\\\]";
        int specialCount = 0;
        int charCount = 0;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isDigit(c)) {
                specialCount++;
            } else if (String.valueOf(c).matches(specialChars)) {
                specialCount++;
            } else if (!Character.isWhitespace(c)) {
                charCount++;
            }
        }
        return specialCount >= 2 && charCount >= 6;
    }

    private void mostrarDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Deseas registrar un nuevo cliente?").setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                registrarCliente();
                dialog.dismiss();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void registrarCliente() {

        setError("Registro Exitoso", "Usuario registrado correctamente");
        //limpiarCampos();
    }

    private void setError(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title).setMessage(message).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
