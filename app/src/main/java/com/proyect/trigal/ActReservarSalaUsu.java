package com.proyect.trigal;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class ActReservarSalaUsu extends AppCompatActivity {
    private TextInputLayout inpNom,inpRes,inpPer,inpFec,inpHorI,inpHorF,inpBus;
    private Button btnReservado,btnEliminar,btnBuscar;
    private  Spinner spnTipSal,spnFil;
    private String salas[]={"Labroom","Meetingroom","Sala de capacitaciones"};
    private String filtros[]={"Buscar por:","Empresa","Responsable","Sala","Personas","Fecha","HoraEntrada","HoraSalida"};
    private  Boolean verikai=true;
    private ListView lstRes;
    private String nom,res,numP,tipS,fec,horI,horS;
    private Calendar selectUser;
    private int añoG,mesG,diaG,hourG,minuteG,hourF,minuteF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_form_reserva_usuario);
        ActionBar actionBar = getSupportActionBar();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Reservas");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Itera sobre los hijos del nodo "Reservas"
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String id = childSnapshot.getKey();
                    Toast.makeText(ActReservarSalaUsu.this,id+"",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        if (actionBar != null) {
            actionBar.hide();
        }
        asignarReferencias();
        setUpDateAndTimePickers();
        botonRegistrar();
        botonEliminar();
        botonBuscar();
        listarReservas();
    }
    private void asignarReferencias() {
        inpNom= findViewById(R.id.inputNomUsu);
        inpRes= findViewById(R.id.inputResUsu);
        inpPer= findViewById(R.id.inputPerUsu);
        inpFec= findViewById(R.id.edtFechaUsu);
        inpHorI= findViewById(R.id.inpHoraInicioUsu);
        inpHorF= findViewById(R.id.inpHoraFinalUsu);
        inpBus=findViewById(R.id.inpBuscarUsu);
        spnTipSal=findViewById(R.id.sTipSalaUsu);
        spnFil=findViewById(R.id.spnFiltroUsu);
        lstRes=findViewById(R.id.listReservasUsu);
        ArrayAdapter adapterS=new ArrayAdapter(this,android.R.layout.simple_list_item_1, salas);
        ArrayAdapter adapterF=new ArrayAdapter(this,android.R.layout.simple_list_item_1, filtros);
        spnTipSal.setAdapter(adapterS);
        spnFil.setAdapter(adapterF);
        btnReservado=findViewById(R.id.btnReservadoUsu);
        btnEliminar=findViewById(R.id.btnEliminarUsu);
        btnBuscar=findViewById(R.id.btnBuscarUsu);
        selectUser=Calendar.getInstance();
        hourG=selectUser.get(Calendar.HOUR_OF_DAY);
        minuteG=selectUser.get(Calendar.MINUTE);
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
                    inpPer.setHelperText("*Desde 1 a 6 personas*");
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
                    inpPer.setHelperText("*Desde 1 a 6 personas*");
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
                    inpPer.setHelperText("*Desde 7 a 33 personas*");
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
    }
    private void llenaryvalidar(){
       nom=inpNom.getEditText().getText().toString();
       res=inpRes.getEditText().getText().toString();
       numP=inpPer.getEditText().getText().toString();
       tipS=spnTipSal.getSelectedItem().toString();
       fec=inpFec.getEditText().getText().toString();
       horI=inpHorI.getEditText().getText().toString();
       horS=inpHorF.getEditText().getText().toString();
       verikai=true;
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
// Convertir los valores de hora a objetos LocalTime
           LocalTime horaEntradas = null;
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
               horaEntradas = LocalTime.parse(horI, DateTimeFormatter.ofPattern("HH:mm"));
           }
           LocalTime horaSalidas = null;
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
               horaSalidas = LocalTime.parse(horS, DateTimeFormatter.ofPattern("HH:mm"));
           }
// Validar que la hora de salida sea al menos una hora posterior a la hora de entrada
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
               if (horaSalidas.isBefore(horaEntradas.plusHours(1))) {
                   // La hora de salida es anterior a la hora de entrada + 1 hora
                   inpHorI.setErrorTextAppearance(R.style.ErrorAppearance);
                   inpHorF.setErrorTextAppearance(R.style.ErrorAppearance);
                   inpHorF.setError("*Mínimo 1 hora de diferencia*".toUpperCase());
                   inpHorI.setError("*Mínimo 1 hora de diferencia*".toUpperCase());
                   verikai=false;
               }
           }
       }
   }
   private void validacionSincronica(){
       FirebaseDatabase db=FirebaseDatabase.getInstance();
       DatabaseReference dbref=db.getReference(Reservas.class.getSimpleName());
       dbref.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                   for (DataSnapshot x : snapshot.getChildren()) {
                       Reservas r= x.getValue(Reservas.class);
                       DateTimeFormatter formatoFecha = null;
                       formatoFecha = DateTimeFormatter.ofPattern("d/M/yyyy");
                       String feccc=r.getFecha();
                       LocalDate fecha;
                       fecha = LocalDate.parse(feccc, formatoFecha);
                       int diaU = fecha.getDayOfMonth();
                       DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");
                       LocalTime horaI = LocalTime.parse(r.getHoraEntrada(),formatoHora);
                       LocalTime horaF = LocalTime.parse(r.getHoraSalida(), formatoHora);
                       String salaV=r.getSala();
                       LocalTime horaInicio = LocalTime.of(hourG, minuteG);
                       LocalTime horaFin = LocalTime.of(hourF, minuteF);
                       if(        (diaU==diaG && horaInicio.isAfter(horaI) && horaFin.isAfter(horaF) && horaInicio.isBefore(horaF))
                               || (diaU==diaG && horaInicio.isAfter(horaI) && horaFin.equals(horaF) && horaInicio.isBefore(horaF))
                               || (diaU==diaG && horaInicio.isAfter(horaI)  && horaFin.isBefore(horaF))
                               || (diaU==diaG && horaInicio.equals(horaI) && horaFin.isBefore(horaF))
                               || (diaU==diaG && horaInicio.equals(horaI)  && horaFin.equals(horaF))
                               || (diaU==diaG && horaInicio.equals(horaI)  && horaFin.isAfter(horaF))
                               || (diaU==diaG && horaInicio.equals(horaF)  && horaFin.isAfter(horaF))
                               || (diaU==diaG && horaInicio.isBefore(horaI)  && horaFin.isAfter(horaF))
                               || (diaU==diaG && horaInicio.isBefore(horaI)  && (horaFin.equals(horaF)  || horaFin.equals(horaI)))
                               || (diaU==diaG && horaInicio.isBefore(horaI) && horaFin.isBefore(horaF) && horaFin.isAfter(horaI))){
                           verikai=false;
                           inpHorI.setErrorTextAppearance(R.style.ErrorAppearance);
                           inpHorF.setErrorTextAppearance(R.style.ErrorAppearance);
                           inpHorF.setError("*Ya hay una reserva realizada*".toUpperCase());
                           inpHorI.setError("*Ya hay una reserva realizada*".toUpperCase());
                           break;
                       }else{
                           inpHorF.setError(null);
                       }
                   }
               }
               if(verikai == false){
                   verikai=false;
               }else {
                   AlertDialog.Builder builder = new AlertDialog.Builder(ActReservarSalaUsu.this);
                   builder.setTitle("Confirmación");
                   builder.setMessage("¿Estás seguro de que quieres realizar la reserva?");
                   builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot snapshot) {
                                   Reservas reserva = new Reservas(nom,res,tipS,numP,fec,horI,horS);
                                   dbref.push().setValue(reserva);
                                   ocultarTeclado();
                                   Toast.makeText(ActReservarSalaUsu.this, "Reserva realizada", Toast.LENGTH_LONG).show();
                                   limpiar();
                                   deshabilitar();
                                   hourG=selectUser.get(Calendar.HOUR_OF_DAY);
                                   minuteG=selectUser.get(Calendar.MINUTE);
                               }
                               @Override
                               public void onCancelled(@NonNull DatabaseError error) {}
                           });
                           Toast.makeText(ActReservarSalaUsu.this, "RESERVA REALIZADA CON EXITO", Toast.LENGTH_SHORT).show();
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
           @Override
           public void onCancelled(@NonNull DatabaseError error) {}
       });
   }
    private void botonRegistrar(){
        btnReservado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llenaryvalidar();
                validacionSincronica();
            }
        });
    }
    private void botonEliminar(){
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 FirebaseDatabase db=FirebaseDatabase.getInstance();
                    DatabaseReference dbref=db.getReference(Reservas.class.getSimpleName());
                    dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            boolean val = false;
                            String aux=inpHorI.getEditText().getText().toString();
                            String aux2=inpFec.getEditText().getText().toString();
                            for (DataSnapshot x : snapshot.getChildren()){
                                if (aux2.equalsIgnoreCase(x.child("fecha").getValue().toString()) && aux.equalsIgnoreCase(x.child("horaEntrada").getValue().toString())){
                                    AlertDialog.Builder alert=new AlertDialog.Builder(ActReservarSalaUsu.this);
                                    val=true;
                                    alert.setCancelable(false);
                                    alert.setTitle("PREGUNTA");
                                    alert.setMessage("¿Estás seguro de eliminar la reserva?");
                                    alert.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            limpiar();
                                            deshabilitar();
                                        }
                                    });
                                    alert.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ocultarTeclado();
                                            x.getRef().removeValue();
                                            listarReservas();
                                            Toast.makeText(ActReservarSalaUsu.this, "Reserva eliminada", Toast.LENGTH_LONG).show();
                                            limpiar();
                                            deshabilitar();
                                        }
                                    });
                                    alert.show();
                                    break;
                                }
                            }
                            if (val==false){
                                ocultarTeclado();
                                Toast.makeText(ActReservarSalaUsu.this, "No se ha encontrado la reserva", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
        });
    }
    private String obtenerValorAtributoComoFireBase(String atributo){
        String valorAtributo = "";
        switch (atributo) {
            case "Empresa":
                valorAtributo = "empresa";
                break;
            case "Responsable":
                valorAtributo = "responsable";
                break;
            case "Sala":
                valorAtributo = "sala";
                break;
            case "Personas":
                valorAtributo = "personas";
                break;
            case "Fecha":
                valorAtributo = "fecha";
                break;
            case "HoraEntrada":
                valorAtributo = "horaEntrada";
                break;
            case "HoraSalida":
                valorAtributo = "horaSalida";
                break;
            default:
                valorAtributo = "";
                break;
        }
        return valorAtributo;
    }
    private void mostrarReservasEncontrada(ArrayList<Reservas> reservas) {
        ArrayAdapter <Reservas> adapRes= new ArrayAdapter<Reservas>(ActReservarSalaUsu.this, android.R.layout.simple_list_item_1,reservas);
        lstRes.setAdapter(adapRes);
        lstRes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Reservas reservaF=reservas.get(position);
                enviarDatos(reservaF);
            }
        });
    }
    private void botonBuscar(){
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String campoIngresado = inpBus.getEditText().getText().toString();
                String atributo = spnFil.getSelectedItem().toString();
                String valorAtributo = obtenerValorAtributoComoFireBase(atributo);
                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(Reservas.class.getSimpleName());
                ArrayList<Reservas> reservasxfiltro=new ArrayList<Reservas>() ;
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean res = false;
                        for (DataSnapshot x : snapshot.getChildren()) {
                            if(campoIngresado.equalsIgnoreCase(x.child(valorAtributo).getValue().toString()))
                            {
                                res = true;
                                reservasxfiltro.add(x.getValue(Reservas.class));
                            }
                        }
                        if(res==false){
                            Toast.makeText(ActReservarSalaUsu.this, "No se encontro al usuario", Toast.LENGTH_SHORT).show();
                            listarReservas();
                            ocultarTeclado();
                        }else{
                            mostrarReservasEncontrada(reservasxfiltro);
                            ocultarTeclado();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
    }
    private void setUpDateAndTimePickers() {
        inpHorF.getEditText().setEnabled(false);
        inpHorI.getEditText().setEnabled(false);
        inpFec.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Calendar calendario = Calendar.getInstance();
                    int año = calendario.get(Calendar.YEAR);
                    int mes = calendario.get(Calendar.MONTH);
                    int dia=calendario.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog dialogFecha = new DatePickerDialog(
                            ActReservarSalaUsu.this, R.style.DatePickerStyle,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    añoG=year; mesG=month+1; diaG=dayOfMonth;
                                    String fechaSeleccionada = dayOfMonth + "/" + (month + 1) + "/" + year;
                                    inpFec.getEditText().setText(fechaSeleccionada);
                                    inpHorI.getEditText().setEnabled(true);
                                }
                            },
                            año, mes, dia);
                    // Bloquear fechas anteriores al día de hoy
                    dialogFecha.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                    dialogFecha.show();
                    inpFec.getEditText().clearFocus();
                }
            }
        });
        int horInp,minInp;
        inpHorI.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Calendar calendario=Calendar.getInstance();
                    int año = calendario.get(Calendar.YEAR);
                    int mes = calendario.get(Calendar.MONTH);
                    int dia = calendario.get(Calendar.DAY_OF_MONTH);
                    int horaActual;
                    int minutoActual = calendario.get(Calendar.MINUTE);
                    horaActual= calendario.get(Calendar.HOUR_OF_DAY);
                    TimePickerDialog dialogHora = new TimePickerDialog(
                            ActReservarSalaUsu.this, R.style.DatePickerStyle,
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    if ((añoG == año && mesG == mes+1 && diaG==dia && hourOfDay < horaActual) || (añoG == año && mesG == mes+1 && diaG==dia && hourOfDay == horaActual && minute <= minutoActual)) {
                                        hourOfDay = horaActual;
                                        minute = minutoActual;
                                    }
                                        hourG=hourOfDay;
                                        minuteG=minute;
                                    String x = "", y = "";
                                    if (hourOfDay < 10) {
                                        x = "0";
                                    }
                                    if (minute < 10) {
                                        y = "0";
                                    }
                                    String horaSeleccionada = x + hourOfDay + ":" + y + minute;
                                    inpHorI.getEditText().setText(horaSeleccionada);
                                    inpHorF.getEditText().setEnabled(true);
                                }
                            },
                        hourG, minuteG, true);
                        inpHorI.getEditText().clearFocus();
                        dialogHora.show();
                }
            }
        });
        inpHorF.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange (View v,boolean hasFocus){
                    Calendar calendar = Calendar.getInstance();
                    int año = calendar.get(Calendar.YEAR);
                    int mes = calendar.get(Calendar.MONTH);
                    int dia = calendar.get(Calendar.DAY_OF_MONTH);
                    int horaActual = calendar.get(Calendar.HOUR_OF_DAY);
                    int minutoActual = calendar.get(Calendar.MINUTE);
                        if (hasFocus) {
                            TimePickerDialog dialogHora = new TimePickerDialog(
                                    ActReservarSalaUsu.this, R.style.DatePickerStyle, new TimePickerDialog.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                            if ((hourOfDay < hourG+1) || (hourOfDay==hourG+1 && minute<minuteG)){
                                                    hourOfDay = hourG + 1;
                                                    minute = minuteG;
                                            }
                                            String x = "", y = "";
                                            if (hourOfDay < 10) {
                                                x = "0";
                                            }
                                            if (minute < 10) {
                                                y = "0";
                                            }
                                            minuteF=minute;
                                            hourF=hourOfDay;
                                            String horaSeleccionada = x + hourOfDay + ":" + y + minute;
                                            inpHorF.getEditText().setText(horaSeleccionada);
                                        }
                                    },
                                    hourG+1, minuteG+1, true);
                            inpHorF.getEditText().clearFocus();
                            dialogHora.show();
                        }
                    }
        });
    }
    private void listarReservas(){
        FirebaseDatabase db= FirebaseDatabase.getInstance();
        DatabaseReference dbref=db.getReference(Reservas.class.getSimpleName());
        ArrayList<Reservas> lisrec= new ArrayList<Reservas>();
        ArrayAdapter <Reservas> adapRes= new ArrayAdapter<Reservas>(ActReservarSalaUsu.this, android.R.layout.simple_list_item_1,lisrec);
        lstRes.setAdapter(adapRes);
        dbref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Reservas reserv=snapshot.getValue(Reservas.class);
                lisrec.add(reserv);
                adapRes.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                adapRes.notifyDataSetChanged();
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        lstRes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Reservas reservaF=lisrec.get(position);
                enviarDatos(reservaF);
            }
        });
    }
    private void enviarDatos(Reservas r){
        inpNom.getEditText().setText(r.getEmpresa());
        inpRes.getEditText().setText(r.getResponsable());
        String opcionSala=r.getSala();
        if (opcionSala.equals("Labroom")){
            spnTipSal.setSelection(0);
        } else if (opcionSala.equals("Meetingoom")) {
            spnTipSal.setSelection(1);
        }else{
            spnTipSal.setSelection(2);
        }
        inpPer.getEditText().setText(r.getPersonas());
        inpFec.getEditText().setText(r.getFecha());
        inpHorI.getEditText().setText(r.getHoraEntrada());
        inpHorF.getEditText().setText(r.getHoraSalida());
        inpHorF.setEnabled(true);
        inpHorI.setEnabled(true);
    }
    private void ocultarTeclado(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    private void limpiar(){
        inpNom.getEditText().setText("");
        inpRes.getEditText().setText("");
        inpPer.getEditText().setText("");
        inpFec.getEditText().setText("");
        inpHorI.getEditText().setText("");
        inpHorF.getEditText().setText("");
    }
    private void deshabilitar(){
        inpNom.setErrorEnabled(false);
        inpPer.setErrorEnabled(false);
        inpRes.setErrorEnabled(false);
        inpHorI.setErrorEnabled(false);
        inpHorF.setErrorEnabled(false);
        inpFec.setErrorEnabled(false);
    }
}