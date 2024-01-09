package com.proyect.trigal;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ActLogin extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextInputLayout inpCor, inpCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_login_trigal);

        mAuth = FirebaseAuth.getInstance();

        inpCor = findViewById(R.id.inpCorreoLogin);
        inpCon = findViewById(R.id.inpContraseñaLogin);
        TextView txtLogin = findViewById(R.id.txtLogin);

        Button loginButton = findViewById(R.id.btnIniciar);

        txtLogin.setOnClickListener(v -> abrirVistaAdmin());

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
        inpCor.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    inpCor.setErrorEnabled(true);
                    inpCor.setError("*CAMPO OBLIGATORIO*");
                    inpCor.setErrorTextAppearance(R.style.ErrorAppearance);
                }else{
                    inpCor.setErrorEnabled(false);
                }
            }
        });
        inpCon.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    inpCon.setErrorEnabled(true);
                    inpCon.setError("*CAMPO OBLIGATORIO*");
                    inpCon.setErrorTextAppearance(R.style.ErrorAppearance);
                }else{
                    inpCon.setErrorEnabled(false);
                }
            }
        });
    }

    private void abrirVistaAdmin() {
        Intent intent = new Intent(ActLogin.this, ActAdmin.class);
        startActivity(intent);
    }
    private void loginUser() {
        String email = inpCor.getEditText().getText().toString().trim();
        String password = inpCon.getEditText().getText().toString().trim();
        boolean verikai=true;
        if(email.isEmpty()){
            verikai=false;
            inpCor.setError("Esta vacio");
            inpCor.setErrorTextAppearance(R.style.ErrorAppearance);
        }else{
            inpCor.setError(null);
        }
        if(password.isEmpty()){
            verikai=false;
            inpCon.setError("Esta vacio");
            inpCon.setErrorTextAppearance(R.style.ErrorAppearance);

        }else{
            inpCon.setError(null);
        }
        if(verikai==false){
            Toast.makeText(ActLogin.this, "Llene los campos porfavor", Toast.LENGTH_SHORT).show();
        }else{
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Usuario");
                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                    if (email.equals(childSnapshot.getValue(Usuario.class).getCorreo())){
                                        String key = childSnapshot.getKey().toString();
                                        Intent intent = new Intent(ActLogin.this, MainActivity.class);
                                        intent.putExtra("key", key);
                                        startActivity(intent);
                                        finish();
                                        break;
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } else {
                        Toast.makeText(ActLogin.this, "No existe el usuario", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}