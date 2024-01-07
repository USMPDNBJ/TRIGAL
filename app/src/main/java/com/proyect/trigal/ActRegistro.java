package com.proyect.trigal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActRegistro extends AppCompatActivity {
    //NO LA CAGUES MIERDA
    private final String[] tpD = {"", "DNI", "RUC"};
    private final String[] atributos = {"", "Empresa","Responsable", "Celular", "Usuario", "Número de documento"};
    private TextInputLayout inpNombre, inpResponsabe, inpCelular, inpCorreo, inpContraseña, inpNumDoc, inpBus;
    private Spinner spTipoDoc, spAtributoBus ;
    private Button btnAgregar, btnModificar, btnEliminar,btnBuscar;
    private ListView lvUsuarios;
    private FirebaseAuth mAuth;

    private ArrayList<Usuario> listUsu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_registrar_cliente);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        asignarReferencia();
        botonAgregar();
        listarUsuarios();
        botonBuscar();
        botonEliminar();
        botonModificar();
        lvUsuarios.setOnCreateContextMenuListener(this);
        lvUsuarios = findViewById(R.id.lvUsuarios);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_contextual, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Obtén la información del elemento seleccionado si es necesario
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        // Maneja la acción del menú según el ID del elemento seleccionado
        if (item.getItemId() == R.id.menu_ver) {
            mostrarInformacionUsuario(listUsu.get(info.position));
            return true;
        } else if (item.getItemId() == R.id.menu_modificar) {
            // Lógica para la opción "Modificar"
            // Puedes lanzar la actividad de modificación aquí
            // algo así como: lanzarActividadModificar(listUsu.get(info.position));
            return true;
        } else if (item.getItemId() == R.id.menu_delete) {
            // Lógica para la opción "Eliminar"
            eliminarUsuario(listUsu.get(info.position));
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    private void mostrarInformacionUsuario(Usuario usuario) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setTitle("Información del Usuario");

        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("Nombre de la empresa: ").append(usuario.getNombreEmpresa()).append("\n")
                .append("Responsable: ").append(usuario.getResponsable()).append("\n")
                .append("Número de documento: ").append(usuario.getNumeroDocumento()).append("\n")
                .append("Celular: ").append(usuario.getCelular()).append("\n")
                .append("Correo electrónico: ").append(usuario.getCorreo()).append("\n")
                .append("Contraseña: ").append(usuario.getContraseña()).append("\n");

        alertDialogBuilder.setMessage(messageBuilder.toString());
        alertDialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();
    }

    private void eliminarUsuario(Usuario usuario) {

    }

    public void asignarReferencia() {
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
                    inpNombre.setError("*CAMPO OBLIGATORIO");
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
        inpCorreo = findViewById(R.id.textInputCorreo);
        inpCorreo.setErrorTextAppearance(R.style.ErrorAppearance);
        inpCorreo.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    inpCorreo.setErrorEnabled(true);
                    inpCorreo.setError("*CAMPO OBLIGATORIO");
                } else {
                    inpCorreo.setErrorEnabled(false);
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
//////////////////////////////////////////////
//////////////////////////////////////////////
        lvUsuarios=findViewById(R.id.lvUsuarios);
        btnAgregar = findViewById(R.id.btnAgregar);
        btnModificar=findViewById(R.id.btnModificar);
        btnEliminar=findViewById(R.id.btnEliminar);
        spAtributoBus = findViewById(R.id.spAtributoBus);
        ArrayAdapter<String> adapterBus = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, atributos);
        adapterBus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAtributoBus.setAdapter(adapterBus);
        inpBus=findViewById(R.id.inpBus);
        btnBuscar=findViewById(R.id.btnBuscar);
        mAuth =FirebaseAuth.getInstance();
    }

//////////////////////////////////////
////////////////////////////////////////////////////////
    private void botonAgregar(){
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (camposVacios()) {
                    Toast.makeText(ActRegistro.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                } else if (hayErrores()) {
                    Toast.makeText(ActRegistro.this, "Solucione los errores para seguir el proceso", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActRegistro.this);
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
            }
        });
    }
    private void registrarCliente() {
        String nombreEmpresa = inpNombre.getEditText().getText().toString().trim();
        String responsable = inpResponsabe.getEditText().getText().toString().trim();
        String numeroDocumento = inpNumDoc.getEditText().getText().toString().trim();
        String celular = inpCelular.getEditText().getText().toString().trim();
        String correo = inpCorreo.getEditText().getText().toString().trim();
        String contraseña = inpContraseña.getEditText().getText().toString().trim();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbref = db.getReference(Usuario.class.getSimpleName());
        mAuth.createUserWithEmailAndPassword(correo, contraseña).addOnCompleteListener(ActRegistro.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Usuario usuario = new Usuario(nombreEmpresa, responsable, numeroDocumento, celular, correo, contraseña);
                        dbref.push().setValue(usuario);
                        setError("Registro Exitoso", "Usuario registrado correctamente");
                        limpiarCampos();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
////////////////////////////////////////////////////////
    private void listarUsuarios() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbref = db.getReference(Usuario.class.getSimpleName());

        ArrayList<Usuario> listUsu = new ArrayList<Usuario>();
        ArrayAdapter<Usuario> adapterUsu = new ArrayAdapter<>(ActRegistro.this, android.R.layout.simple_list_item_1, listUsu);
        lvUsuarios.setAdapter(adapterUsu);
        dbref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Usuario usu = snapshot.getValue(Usuario.class);
                listUsu.add(usu);
                adapterUsu.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                adapterUsu.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        lvUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Usuario usu = listUsu.get(position);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ActRegistro.this);
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setTitle("Usuario seleccionado");

                StringBuilder messageBuilder = new StringBuilder();
                messageBuilder.append("Nombre de la empresa: ").append(usu.getNombreEmpresa()).append("\n")
                        .append("Responsable: ").append(usu.getResponsable()).append("\n")
                        .append("Número de documento: ").append(usu.getNumeroDocumento()).append("\n")
                        .append("Celular: ").append(usu.getCelular()).append("\n")
                        .append("Correo electronico: ").append(usu.getCorreo()).append("\n")
                        .append("Contraseña: ").append(usu.getContraseña()).append("\n");

                alertDialogBuilder.setMessage(messageBuilder.toString());
                alertDialogBuilder.show();
            }
        });
    }
///////////////////////////////////////////////////////
    private String obtenerAtributo() {
        return spAtributoBus.getSelectedItem().toString();
    }
    private String obtenerCampo() {
        return inpBus.getEditText().getText().toString().trim();
    }
    private String obtenerValorAtributoComoFireBase(String atributo) {
        String valorAtributo = "";
        switch (atributo) {
            case "Empresa":
                valorAtributo = "nombreEmpresa";
                break;
            case "Responsable":
                valorAtributo = "responsable";
                break;
            case "Celular":
                valorAtributo = "celular";
                break;
            case "Usuario":
                valorAtributo = "nombreUsuario";
                break;
            case "Número de documento":
                valorAtributo = "numeroDocumento";
                break;
            default:
                valorAtributo = "";
                break;
        }

        return valorAtributo;
    }
    private void botonBuscar() {
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String campoIngresado = obtenerCampo();
                String atributo = obtenerAtributo();
                String valorAtributo = obtenerValorAtributoComoFireBase(atributo);
                if (campoIngresado.isEmpty()){
                    Toast.makeText(ActRegistro.this, "Complete el campo", Toast.LENGTH_SHORT).show();
                }else{
                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(Usuario.class.getSimpleName());
                    dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            boolean res = false;
                            for (DataSnapshot x : snapshot.getChildren()) {
                                if(campoIngresado.equalsIgnoreCase(x.child(valorAtributo).getValue().toString()))
                                {
                                    res = true;
                                    Usuario usu = x.getValue(Usuario.class);
                                    mostrarUsuarioEncontrado(usu);
                                    break;
                                }
                            }
                            if(res==false){
                                Toast.makeText(ActRegistro.this, "No se encontro al usuario", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
    private void mostrarUsuarioEncontrado(Usuario usuario) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setTitle("Usuario encontrado");

        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("Nombre de la empresa: ").append(usuario.getNombreEmpresa()).append("\n")
                .append("Responsable: ").append(usuario.getResponsable()).append("\n")
                .append("Número de documento: ").append(usuario.getNumeroDocumento()).append("\n")
                .append("Celular: ").append(usuario.getCelular()).append("\n")
                .append("Correo electronico: ").append(usuario.getCorreo()).append("\n")
                .append("Contraseña: ").append(usuario.getContraseña()).append("\n");

        alertDialogBuilder.setMessage(messageBuilder.toString());
        alertDialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();
    }

///////////////////////////////////////////////////////
    private void botonEliminar(){
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String campoIngresado = obtenerCampo();
                String atributo = obtenerAtributo();
                String valorAtributo = obtenerValorAtributoComoFireBase(atributo);
                if (campoIngresado.isEmpty()){
                    Toast.makeText(ActRegistro.this, "Complete el campo", Toast.LENGTH_SHORT).show();
                }else{
                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(Usuario.class.getSimpleName());
                    dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            final boolean[] res = {false};
                            for (DataSnapshot x : snapshot.getChildren()) {
                                if(campoIngresado.equalsIgnoreCase(x.child(valorAtributo).getValue().toString()))
                                {
                                    AlertDialog.Builder a = new AlertDialog.Builder(ActRegistro.this);
                                    a.setCancelable(false);
                                    a.setTitle("Confirmación de eliminación");
                                    a.setMessage("¿Esta seguro(a) que desea eliminar al usuario?");
                                    a.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            res[0] = true;
                                            x.getRef().removeValue();
                                            listarUsuarios();
                                        }
                                    });
                                    a.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                    a.show();
                                    break;
                                }
                            }
                            if(res[0] ==false){
                                Toast.makeText(ActRegistro.this, "No se encontro al usuario", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
///////////////////////////////////////////////////////
/*    private void botonModificar(){
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (camposVacios()) {
                    Toast.makeText(ActReservar.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                } else if (hayErrores()) {
                    Toast.makeText(ActReservar.this, "Solucione los errores para seguir el proceso", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActReservar.this);
                    builder.setMessage("¿Deseas modifcar los datos del cliente?").setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            modificarCliente();
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
            }
        });
    }
    private void modificarCliente() {
        String nomEmpresa = inpNombre.getEditText().getText().toString().trim();
        String responsable = inpResponsabe.getEditText().getText().toString().trim();
        String numeroDocumento = inpNumDoc.getEditText().getText().toString().trim();
        String celular = inpCelular.getEditText().getText().toString().trim();
        String nombreUsuario = inpCorreo.getEditText().getText().toString().trim();
        String contraseña = inpContraseña.getEditText().getText().toString().trim();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbref = db.getReference(Usuario.class.getSimpleName());
        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean res = false;
                String campoIngresado = obtenerCampo();
                String atributo = obtenerAtributo();
                String valorAtributo = obtenerValorAtributoComoFireBase(atributo);
                for(DataSnapshot x : snapshot.getChildren()){
                    if(x.child(valorAtributo).getValue().toString().equalsIgnoreCase(campoIngresado)){
                        res=true;
                        //inpNombre.setText(x.child(campoIngresado).getValue().toString());
                        break;
                    }
                }
                if(res==false){
                    Toast.makeText(ActReservar.this, "NO SE PUEDE MODIFICAR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/

    private void botonModificar(){
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActRegistro.this, ActLogin.class);
                startActivity(intent);
            }
        });
    }
///////////////////////////////////////////////////////
    private boolean hayErrores() {
        return tieneError(inpNombre) || tieneError(inpResponsabe) || tieneError(inpNumDoc) || tieneError(inpCelular) || tieneError(inpCorreo) || tieneError(inpContraseña);
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
        String usuario = inpCorreo.getEditText().getText().toString();
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
    private void limpiarCampos() {
        inpNombre.getEditText().setText("");
        inpResponsabe.getEditText().setText("");
        inpNumDoc.getEditText().setText("");
        inpCelular.getEditText().setText("");
        inpCorreo.getEditText().setText("");
        inpContraseña.getEditText().setText("");
        spTipoDoc.setSelection(0);
    }

}
