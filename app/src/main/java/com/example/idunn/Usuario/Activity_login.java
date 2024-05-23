package com.example.idunn.Usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.idunn.R;
import com.example.idunn.Activity_navegacion;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity_login extends AppCompatActivity {
    /* Variables usadas */
    private FirebaseAuth mAuth;
    private Button loginButton;
    private TextView signUp;
    private EditText emailEditText, passwordEditText;
    private String email, password;
    private FirebaseUser user;
    private Intent intent, signUpIntent;

    /*----------------------*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Inicializamos todos los métodos
        initViews();
        setListeners();
        if (mAuth.getCurrentUser() != null) {
            // Si usuario se ha logueado, redirigimos a Activity_navegacion
            intent = new Intent(Activity_login.this, Activity_navegacion.class);
            startActivity(intent);
            finish();
        }
    }

    public void initViews(){
        // Referencia para usar FirebaseAuth.
        mAuth = FirebaseAuth.getInstance();

        // Referenciamos a los elementos del xml.
        loginButton = findViewById(R.id.botonLogin);
        signUp = findViewById(R.id.textView2);
        emailEditText = findViewById(R.id.inputMail);
        passwordEditText = findViewById(R.id.inputPassword);
    }

    public void setListeners(){
        // Si le damos click en el login haremos lo siguiente
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Primero almacenamos el valor de los textos una vez le demos click
                email = emailEditText.getText().toString();
                password = passwordEditText.getText().toString();

                // Si no están vacios nos meteremos usando signInWithEmailAndPassword
                if(!email.isEmpty() && !password.isEmpty()){
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Activity_login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // Si el usuario existe en la bbdd tendremos la task succesfull.
                                    if (task.isSuccessful()) {

                                        // Guardamos el usuario y nos metemos dentro de la aplicación
                                        user = mAuth.getCurrentUser();
                                        intent = new Intent(Activity_login.this, Activity_navegacion.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(Activity_login.this, "Autenticación fallida.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(Activity_login.this, "Porfavor rellene los campos.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Si le damos a signup nos mueve al activity_registro
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpIntent = new Intent(Activity_login.this, Activity_registro.class);
                startActivity(signUpIntent);
            }
        });
    }
}