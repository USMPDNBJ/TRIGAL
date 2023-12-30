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

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class ActReservarSala extends AppCompatActivity {

    private TextInputLayout inpNom,inpRes,inpPer,inpFec,inpHorI,inpHorF;
    private Button btnReservado;
    private  Spinner spnTipSal;
    private String salas[]={"Labroom","Meetingroom","Sala de capacitaciones"};
    private  Boolean verikai=true;
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
        inpFec= findViewById(R.id.edtFecha);
        inpHorI= findViewById(R.id.inpHoraInicio);
        inpHorF= findViewById(R.id.inpHoraFinal);
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
                    inpNom.setErrorEnabled(true);
                    inpNom.setError("*CAMPO OBLIGATORIO*");
                    inpNom.setErrorTextAppearance(R.style.ErrorAppearance);
                    verikai=false;
                }else{
                    inpNom.setErrorEnabled(false);
                }
            }
        });
        inpFec.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    inpFec.setErrorEnabled(true);
                    inpFec.setError("*CAMPO OBLIGATORIO*");
                    inpFec.setErrorTextAppearance(R.style.ErrorAppearance);
                    verikai=false;
                }else{
                    inpFec.setErrorEnabled(false);
                }
            }
        });
        inpHorI.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    inpHorI.setErrorEnabled(true);
                    inpHorI.setError("*CAMPO OBLIGATORIO*");
                    inpHorI.setErrorTextAppearance(R.style.ErrorAppearance);
                    verikai=false;
                }else{
                    inpHorI.setErrorEnabled(false);
                }
            }
        });
        inpHorF.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    inpHorF.setErrorEnabled(true);
                    inpHorF.setError("*CAMPO OBLIGATORIO*");
                    inpHorF.setErrorTextAppearance(R.style.ErrorAppearance);
                    verikai=false;
                }else{
                    inpHorF.setErrorEnabled(false);
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
                    verikai=false;
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
                    inpPer.setHelperText("*Desde 1 a 6 personas*".toUpperCase());
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
                                        inpPer.setError("*DESDE " + valorMinimo + " A " + valorMaximo +" PERSONAS*");
                                        inpPer.setErrorTextAppearance(R.style.ErrorAppearance);
                                        verikai=false;
                                    } else {
                                        inpPer.setErrorEnabled(false);
                                    }
                                } catch (NumberFormatException e) {
                                    verikai=false;
                                    inpPer.setErrorEnabled(true);
                                    inpPer.setError("*Debe ingresar un número válido*");
                                }
                            } else {
                                inpPer.setErrorEnabled(false);
                            }
                        }
                        // Implementación del TextWatcher para la opción 1
                    });
                } else if (selectedItem.equals("Meetingroom")) {
                    inpPer.setHelperText("*Desde 1 a 6 personas*".toUpperCase());
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
                                        inpPer.setErrorTextAppearance(R.style.ErrorAppearance);
                                        inpPer.setError("*DESDE " + valorMinimo + " A " + valorMaximo+" PERSONAS*");
                                        verikai=false;
                                    } else {
                                        inpPer.setErrorEnabled(false);
                                    }
                                } catch (NumberFormatException e) {
                                    verikai=false;
                                    inpPer.setErrorEnabled(true);
                                    inpPer.setErrorTextAppearance(R.style.ErrorAppearance);
                                    inpPer.setError("Debe ingresar un número válido");
                                }
                            } else {
                                inpPer.setErrorEnabled(false);
                            }
                        }
                        // Implementación del TextWatcher para la opción 2
                    });
                } else if (selectedItem.equals("Sala de capacitaciones")) {
                    inpPer.setHelperText("*Desde 7 a 33 personas*".toUpperCase());
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
                                        inpPer.setErrorTextAppearance(R.style.ErrorAppearance);
                                        inpPer.setError("-DESDE " + valorMinimo + " A " + valorMaximo+" PERSONAS-");
                                        verikai=false;
                                    } else {
                                        inpPer.setErrorEnabled(false);
                                    }
                                } catch (NumberFormatException e) {
                                    verikai=false;
                                    inpPer.setErrorEnabled(true);
                                    inpPer.setErrorTextAppearance(R.style.ErrorAppearance);
                                    inpPer.setError("Debe ingresar un número válido");
                                }
                            } else {
                                inpPer.setErrorEnabled(false);
                            }
                        }
                        // Implementación del TextWatcher para la opción 3
                    });
                }
                    inpPer.setErrorEnabled(false);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        btnReservado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verikai=true;
                String nom=inpNom.getEditText().getText().toString();
                String res=inpRes.getEditText().getText().toString();
                String numP=inpPer.getEditText().getText().toString();
                String tipS=spnTipSal.getSelectedItem().toString();
                String fec=inpFec.getEditText().getText().toString();
                String horI=inpHorI.getEditText().getText().toString();
                String horS=inpHorF.getEditText().getText().toString();
                if (nom.equals("")) {
                    inpNom.setErrorEnabled(true);
                    inpNom.setError("*Campo obligatorio*".toUpperCase());
                    inpNom.setErrorTextAppearance(R.style.ErrorAppearance);
                    verikai=false;
                }
                if(res.isEmpty()){
                    inpRes.setErrorEnabled(true);
                    inpRes.setError("*Campo obligatorio*".toUpperCase());
                    inpRes.setErrorTextAppearance(R.style.ErrorAppearance);
                    verikai=false;
                }
                if(numP.isEmpty()){
                    inpPer.setErrorEnabled(true);
                    inpPer.setError("*Campo obligatorio*".toUpperCase());
                    inpPer.setErrorTextAppearance(R.style.ErrorAppearance);
                    String salas[]={"Labroom","Meetingroom","Sala de capacitaciones"};
                    verikai=false;
                } else if (tipS.equals("Labroom") && (Integer.parseInt(numP)<1 || Integer.parseInt(numP)>6)) {
                    inpPer.setErrorEnabled(true);
                    inpPer.setErrorTextAppearance(R.style.ErrorAppearance);
                    inpPer.setError("*Desde 1 a 6 personas*".toUpperCase());
                    verikai=false;
                }else if (tipS.equals("Meetingroom") && (Integer.parseInt(numP)<1 || Integer.parseInt(numP)>6)) {
                    inpPer.setErrorEnabled(true);
                    inpPer.setErrorTextAppearance(R.style.ErrorAppearance);
                    inpPer.setError("*Desde 1 a 6 personas*".toUpperCase());
                    verikai=false;
                }else if (tipS.equals("Sala de capacitaciones") && (Integer.parseInt(numP)<7 || Integer.parseInt(numP)>33)) {
                    inpPer.setErrorEnabled(true);
                    inpPer.setErrorTextAppearance(R.style.ErrorAppearance);
                    inpPer.setError("*Desde 7 a 33 personas*".toUpperCase());
                    verikai=false;
                }
                if(fec.isEmpty()){
                    inpFec.setError("*Campo obligatorio*".toUpperCase());
                    inpFec.setErrorTextAppearance(R.style.ErrorAppearance);
                    verikai=false;
                }else{
                    inpFec.setError(null);
                    inpFec.setErrorTextAppearance(R.style.ErrorAppearance);
                }
                if(horI.isEmpty()){
                    inpHorI.setError("*Campo obligatorio*".toUpperCase());
                    inpHorI.setErrorTextAppearance(R.style.ErrorAppearance);
                    verikai=false;
                }else{
                    inpHorI.setError(null);
                    inpHorI.setErrorTextAppearance(R.style.ErrorAppearance);
                }
                if(horS.isEmpty()){
                    inpHorF.setError("*Campo obligatorio*".toUpperCase());
                    inpHorF.setErrorTextAppearance(R.style.ErrorAppearance);
                    verikai=false;
                }else{
                    inpHorF.setError(null);
                    inpHorF.setErrorTextAppearance(R.style.ErrorAppearance);
                }
                if(verikai == false){
                    Toast.makeText(ActReservarSala.this, "Error en el ingreso", Toast.LENGTH_SHORT).show();
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
    private int añoG,mesG,diaG;
    private void setUpDateAndTimePickers() {
        inpFec.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Calendar calendario = Calendar.getInstance();
                    int año = calendario.get(Calendar.YEAR);
                    int mes = calendario.get(Calendar.MONTH);
                    int día = calendario.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog dialogFecha = new DatePickerDialog(
                            ActReservarSala.this, R.style.DatePickerStyle,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    añoG=year; mesG=month; diaG=dayOfMonth;
                                    String fechaSeleccionada = dayOfMonth + "/" + (month + 1) + "/" + year;
                                    inpFec.getEditText().setText(fechaSeleccionada);
                                }
                            },
                            año, mes, día);

                    // Bloquear fechas anteriores al día de hoy
                    dialogFecha.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                    dialogFecha.show();
                    inpFec.getEditText().clearFocus();
                }
            }
        });
        inpHorI.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Calendar calendario = Calendar.getInstance();
                    int año = calendario.get(Calendar.YEAR);
                    int mes = calendario.get(Calendar.MONTH);
                    int dia = calendario.get(Calendar.DAY_OF_MONTH);
                    int hora = calendario.get(Calendar.HOUR_OF_DAY);
                    int minuto = calendario.get(Calendar.MINUTE);

                    TimePickerDialog dialogHora = new TimePickerDialog(
                            ActReservarSala.this, R.style.DatePickerStyle,
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    Calendar calendar = Calendar.getInstance();
                                    int horaActual = calendar.get(Calendar.HOUR_OF_DAY);
                                    int minutoActual = calendar.get(Calendar.MINUTE);

                                    if (  ((añoG < año && mesG < mes && diaG < dia) || (añoG == año && mesG == mes && diaG==dia && hourOfDay < horaActual) || (añoG == año && mesG == mes && diaG==dia && hourOfDay == horaActual && minute < minutoActual))) {
                                        // Restaurar la hora actual
                                        hourOfDay = horaActual;
                                        minute = minutoActual;
                                    }

                                    String x = "", y = "";
                                    if (hourOfDay < 10) {
                                        x = "0";
                                    }
                                    if (minute < 10) {
                                        y = "0";
                                    }
                                    String horaSeleccionada = x + hourOfDay + ":" + y + minute;
                                    inpHorI.getEditText().setText(horaSeleccionada);
                                }
                            },
                            hora, minuto, false);

                    inpHorI.getEditText().clearFocus();
                    dialogHora.show();
                }
            }
        });
        inpHorF.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Calendar calendario = Calendar.getInstance();
                    int hora = calendario.get(Calendar.HOUR_OF_DAY);
                    int minuto = calendario.get(Calendar.MINUTE);

                    TimePickerDialog dialogHora = new TimePickerDialog(
                            ActReservarSala.this, R.style.DatePickerStyle,
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    Calendar calendar = Calendar.getInstance();
                                    int año = calendario.get(Calendar.YEAR);
                                    int mes = calendario.get(Calendar.MONTH);
                                    int dia = calendario.get(Calendar.DAY_OF_MONTH);
                                    int horaActual = calendar.get(Calendar.HOUR_OF_DAY);
                                    int minutoActual = calendar.get(Calendar.MINUTE);
                                    if (  ((añoG < año && mesG < mes && diaG < dia) || (añoG == año && mesG == mes && diaG==dia && hourOfDay < horaActual) || (añoG == año && mesG == mes && diaG==dia && hourOfDay == horaActual && minute < minutoActual))) {
                                        // Restaurar la hora actual
                                        hourOfDay = horaActual;
                                        minute = minutoActual;
                                    }

                                    String x = "", y = "";
                                    if (hourOfDay < 10) {
                                        x = "0";
                                    }
                                    if (minute < 10) {
                                        y = "0";
                                    }
                                    String horaSeleccionada = x + hourOfDay + ":" + y + minute;
                                    inpHorF.getEditText().setText(horaSeleccionada);
                                }
                            },
                            hora, minuto, false);
                    inpHorF.getEditText().clearFocus();
                    dialogHora.show();
                }
            }
        });
    }
}