package com.proyect.trigal;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class ActReservarSala extends AppCompatActivity {

    EditText edtNombre, edtResponsable, edtFecha, edtHoraEntrada, edtHoraSalida;
    Button btnReservado;
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
        edtHoraEntrada = findViewById(R.id.edtHoraEntrada);
        edtHoraSalida=findViewById(R.id.edtHoraSalida);
        btnReservado=findViewById(R.id.btnReservado);
        btnReservado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
