package com.proyect.trigal;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;

public class ActReservarSala extends AppCompatActivity {

    EditText edtFecha, edtHoraEntrada, edtHoraSalida;
    TextInputLayout inpNom,inpRes,inpPer;
    Button btnReservado;
    Spinner spnTipSal;
    String salas[]={"Labroom","Meetingroom","Sala de capacitaciones"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_form_registro);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        asignarReferencias();
        setUpDateAndTimePickers();
    }

    private void asignarReferencias() {
        inpNom= findViewById(R.id.inputNom);
        inpRes= findViewById(R.id.inputRes);
        inpPer= findViewById(R.id.inputPer);
        edtFecha = findViewById(R.id.edtFecha);
        edtHoraEntrada = findViewById(R.id.edtHoraEntrada);
        edtHoraSalida=findViewById(R.id.edtHoraSalida);
        spnTipSal=findViewById(R.id.sTipSala);
        ArrayAdapter adapterS=new ArrayAdapter(this,android.R.layout.simple_list_item_1, salas);
        spnTipSal.setAdapter(adapterS);
        btnReservado=findViewById(R.id.btnReservado);
        inpNom.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    inpPer.setErrorEnabled(true);
                    inpNom.setError("*CAMPO OBLIGATORIO*");
                    inpNom.setErrorTextAppearance(R.style.ErrorAppearance);
                }else{
                    inpNom.setErrorEnabled(false);
                }
            }
        });
        inpRes.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    inpPer.setErrorEnabled(true);
                    inpRes.setError("*CAMPO OBLIGATORIO*");
                    inpRes.setErrorTextAppearance(R.style.ErrorAppearance);
                }else{
                    inpRes.setErrorEnabled(false);
                }
            }
        });

        spnTipSal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                // Realizar la validación correspondiente según el texto seleccionado
                if (selectedItem.equals("Labroom")) {
                    // Validación para la opción 1
                    inpPer.getEditText().addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }
                        @Override
                        public void afterTextChanged(Editable s) {
                            String texto = s.toString().trim();

                            if (!TextUtils.isEmpty(texto)) {
                                try {
                                    int valor = Integer.parseInt(texto);
                                    int valorMinimo = 1; // Valor mínimo deseado
                                    int valorMaximo = 6; // Valor máximo deseado

                                    if (valor < valorMinimo || valor > valorMaximo) {
                                        inpPer.setErrorEnabled(true);
                                        inpPer.setError("El valor debe estar entre " + valorMinimo + " y " + valorMaximo);
                                        inpPer.setErrorTextAppearance(R.style.ErrorAppearance);
                                    } else {
                                        inpPer.setErrorEnabled(false);
                                    }
                                } catch (NumberFormatException e) {
                                    inpPer.setErrorEnabled(true);
                                    inpPer.setError("Debe ingresar un número válido");
                                    inpPer.setErrorTextAppearance(R.style.ErrorAppearance);
                                }
                            } else {
                                inpPer.setErrorEnabled(false);
                            }
                        }
                        // Implementación del TextWatcher para la opción 1
                    });
                } else if (selectedItem.equals("Meetingroom")) {
                    // Validación para la opción 2
                    inpPer.getEditText().addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            String texto = s.toString().trim();

                            if (!TextUtils.isEmpty(texto)) {
                                try {
                                    int valor = Integer.parseInt(texto);
                                    int valorMinimo = 1; // Valor mínimo deseado
                                    int valorMaximo = 6; // Valor máximo deseado

                                    if (valor < valorMinimo || valor > valorMaximo) {
                                        inpPer.setErrorEnabled(true);
                                        inpPer.setError("El valor debe estar entre " + valorMinimo + " y " + valorMaximo);
                                        inpPer.setErrorTextAppearance(R.style.ErrorAppearance);
                                    } else {
                                        inpPer.setErrorEnabled(false);
                                    }
                                } catch (NumberFormatException e) {
                                    inpPer.setErrorEnabled(true);
                                    inpPer.setError("Debe ingresar un número válido");
                                    inpPer.setErrorTextAppearance(R.style.ErrorAppearance);
                                }
                            } else {
                                inpPer.setErrorEnabled(false);
                            }
                        }
                        // Implementación del TextWatcher para la opción 2
                    });
                } else if (selectedItem.equals("Sala de capacitaciones")) {
                    // Validación para la opción 3
                    inpPer.getEditText().addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            String texto = s.toString().trim();

                            if (!TextUtils.isEmpty(texto)) {
                                try {
                                    int valor = Integer.parseInt(texto);
                                    int valorMinimo = 7; // Valor mínimo deseado
                                    int valorMaximo = 33; // Valor máximo deseado

                                    if (valor < valorMinimo || valor > valorMaximo) {
                                        inpPer.setErrorEnabled(true);
                                        inpPer.setError("El valor debe estar entre " + valorMinimo + " y " + valorMaximo);
                                        inpPer.setErrorTextAppearance(R.style.ErrorAppearance);
                                    } else {
                                        inpPer.setErrorEnabled(false);
                                    }
                                } catch (NumberFormatException e) {
                                    inpPer.setErrorEnabled(true);
                                    inpPer.setError("Debe ingresar un número válido");
                                    inpPer.setErrorTextAppearance(R.style.ErrorAppearance);
                                }
                            } else {
                                inpPer.setErrorEnabled(false);
                            }
                        }
                        // Implementación del TextWatcher para la opción 3
                    });
                }

                // Limpieza de errores
                inpPer.setErrorEnabled(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnReservado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom=inpNom.getEditText().getText().toString();
                String res=inpRes.getEditText().getText().toString();
                String numP=inpPer.getEditText().getText().toString();
                String tipS=spnTipSal.getSelectedItem().toString();
                String fec=edtFecha.getText().toString();
                String horE=edtHoraEntrada.getText().toString();
                String horS=edtHoraSalida.getText().toString();
                if (nom.equals("")) {
                    inpNom.setErrorEnabled(true);
                    inpNom.setError("Campo obligatorio".toUpperCase());
                    inpNom.setErrorTextAppearance(R.style.ErrorAppearance);
                }
                if(res.isEmpty()){
                    inpRes.setErrorEnabled(true);
                    inpRes.setError("Campo obligatorio".toUpperCase());
                    inpRes.setErrorTextAppearance(R.style.ErrorAppearance);
                }
                if(numP.isEmpty()){
                    inpPer.setErrorEnabled(true);
                    inpPer.setError("Campo obligatorio".toUpperCase());
                    inpPer.setErrorTextAppearance(R.style.ErrorAppearance);
                    String salas[]={"Labroom","Meetingroom","Sala de capacitaciones"};
                } else if (tipS.equals("Labroom") && Integer.parseInt(numP)<1 || Integer.parseInt(numP)>6) {
                    inpPer.setErrorEnabled(true);
                    inpPer.setError("Desde 1 a 6 personas".toUpperCase());
                    inpPer.setErrorTextAppearance(R.style.ErrorAppearance);
                }else if (tipS.equals("Meetingroom") && Integer.parseInt(numP)<1 || Integer.parseInt(numP)>6) {
                    inpPer.setErrorEnabled(true);
                    inpPer.setError("Desde 1 a 6 personas".toUpperCase());
                    inpPer.setErrorTextAppearance(R.style.ErrorAppearance);
                }else if (tipS.equals("Sala de capacitaciones") && Integer.parseInt(numP)<7 || Integer.parseInt(numP)>33) {
                    inpPer.setErrorEnabled(true);
                    inpPer.setError("Desde 7 a 33 personas".toUpperCase());
                    inpPer.setErrorTextAppearance(R.style.ErrorAppearance);
                }
                if(fec.isEmpty()){
                    edtFecha.setError("Campo obligatorio".toUpperCase());
                    edtFecha.setHintTextColor(0xffff0000);
                }else{
                    edtFecha.setError(null);
                    edtFecha.setHintTextColor(0xFF03DAC5);
                }
                if(horE.isEmpty()){
                    edtHoraEntrada.setError("Campo obligatorio".toUpperCase());
                    edtHoraEntrada.setHintTextColor(0xffff0000);
                }else{
                    edtHoraEntrada.setError(null);
                    edtHoraEntrada.setHintTextColor(0xFF03DAC5);
                }
                if(horS.isEmpty()){
                    edtHoraSalida.setError("Campo obligatorio".toUpperCase());
                    edtHoraSalida.setHintTextColor(0xffff0000);
                }else{
                    edtHoraSalida.setError(null);
                    edtHoraSalida.setHintTextColor(0xFF03DAC5);
                }
                if(nom.isEmpty() || res.isEmpty() || numP.isEmpty() || fec.isEmpty() || horE.isEmpty() || horS.isEmpty()){
                    Toast.makeText(ActReservarSala.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActReservarSala.this);
                    builder.setTitle("Confirmación");
                    builder.setMessage("¿Estás seguro de que quieres realizar la reserva?");
                    builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(ActReservarSala.this, "RESERVA REALIZADA CON EXITO", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ActReservarSala.this, ActSplash2.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }

    private void setUpDateAndTimePickers() {
        edtFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendario = Calendar.getInstance();
                int año = calendario.get(Calendar.YEAR);
                int mes = calendario.get(Calendar.MONTH);
                int día = calendario.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialogFecha = new DatePickerDialog(
                        ActReservarSala.this,R.style.DatePickerStyle,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String fechaSeleccionada = dayOfMonth + "/" + (month + 1) + "/" + year;
                                edtFecha.setText(fechaSeleccionada);
                            }
                        },
                        año, mes, día);
                dialogFecha.show();
            }
        });

        edtHoraEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendario = Calendar.getInstance();
                int hora = calendario.get(Calendar.HOUR_OF_DAY);
                int minuto = calendario.get(Calendar.MINUTE);

                TimePickerDialog dialogHora = new TimePickerDialog(
                        ActReservarSala.this,R.style.DatePickerStyle,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String horaSeleccionada = hourOfDay + ":" + minute;
                                edtHoraEntrada.setText(horaSeleccionada);
                            }
                        },
                        hora, minuto, true);
                dialogHora.show();
            }
        });
        edtHoraSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendario = Calendar.getInstance();
                int hora = calendario.get(Calendar.HOUR_OF_DAY);
                int minuto = calendario.get(Calendar.MINUTE);
                TimePickerDialog dialogHora = new TimePickerDialog(
                        ActReservarSala.this,R.style.DatePickerStyle,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String horaSeleccionada = hourOfDay + ":" + minute;
                                edtHoraSalida.setText(horaSeleccionada);
                            }
                        },
                        hora, minuto, true);
                dialogHora.show();
            }
        });
    }
}
