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
import com.example.idunn.activity_navegacion;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class activity_login extends AppCompatActivity {
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



        // Referencia para usar FirebaseAuth.
        mAuth = FirebaseAuth.getInstance();

        // Referenciamos a los elementos del xml.
        loginButton = findViewById(R.id.botonLogin);
        signUp = findViewById(R.id.textView2);
        emailEditText = findViewById(R.id.inputUser);
        passwordEditText = findViewById(R.id.inputPassword);

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
                            .addOnCompleteListener(activity_login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // Si el usuario existe en la bbdd tendremos la task succesfull.
                                    if (task.isSuccessful()) {

                                        // Guardamos el usuario y nos metemos dentro de la aplicación
                                        user = mAuth.getCurrentUser();
                                        intent = new Intent(activity_login.this, activity_navegacion.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(activity_login.this, "Autenticación fallida.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(activity_login.this, "Porfavor rellene los campos.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Si le damos a signup nos mueve al activity_registro
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpIntent = new Intent(activity_login.this, activity_registro.class);
                startActivity(signUpIntent);
            }
        });
    }
}