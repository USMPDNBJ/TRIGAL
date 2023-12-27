package com.proyect.trigal;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
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

import java.util.Calendar;

public class ActReservar extends AppCompatActivity {

    EditText edtNombre, edtResponsable, edtFecha, edtHora;
    Spinner spNumeroPersonas;
    Button btnReservado;
    String[] opcPersonas = {"","1-5", "10-15", "15-30", "30-40"};

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
        edtNombre = findViewById(R.id.edtNombreEmpresa);
        edtResponsable = findViewById(R.id.edtResponsable);
        edtFecha = findViewById(R.id.edtFecha);
        edtHora = findViewById(R.id.edtHora);
        btnReservado=findViewById(R.id.btnReservado);
        btnReservado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ActReservar.this);
                builder.setTitle("Confirmación");
                builder.setMessage("¿Estás seguro de que quieres realizar la reserva?");
                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ActReservar.this, "Reserva realizada", Toast.LENGTH_SHORT).show();
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
        });
        spNumeroPersonas = findViewById(R.id.spNumeroPersonas);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcPersonas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spNumeroPersonas.setAdapter(adapter);
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
                        ActReservar.this,R.style.DatePickerStyle,
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

        edtHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendario = Calendar.getInstance();
                int hora = calendario.get(Calendar.HOUR_OF_DAY);
                int minuto = calendario.get(Calendar.MINUTE);

                TimePickerDialog dialogHora = new TimePickerDialog(
                        ActReservar.this,R.style.DatePickerStyle,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String horaSeleccionada = hourOfDay + ":" + minute;
                                edtHora.setText(horaSeleccionada);
                            }
                        },
                        hora, minuto, true);
                dialogHora.show();
            }
        });
    }
}
