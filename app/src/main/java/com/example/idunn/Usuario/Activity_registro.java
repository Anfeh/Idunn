package com.example.idunn.Usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.idunn.Datos.User;
import com.example.idunn.Logica.ValidacionUsuario;
import com.example.idunn.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Activity_registro extends AppCompatActivity {
    /* Variables usadas */
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Button registerButton;
    private TextView loginTextView;
    private EditText emailEditText, passwordEditText, usernameEditText, confirmPasswordEditText;
    private String username, email, password, confirmPassword;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private User createUser;
    private Intent intent;
    /* ------------------------------------- */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        // Inicializamos todos los métodos
        initViews();
        setListeners();




    }

    private void setListeners(){
       try {
           // Si le damos a login nos mueve al activity_login
           loginTextView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   intent = new Intent(Activity_registro.this, Activity_login.class);
                   startActivity(intent);
               }
           });


           // Si le damos a register nos registra usando el metodo registerUser()
           registerButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   registerUser();
               }
           });
       }catch (Exception e){
           System.err.println("Error al pulsar un item");
       }
    }


    private void initViews(){
        // Referencia para usar FirebaseAuth y FirebaseDatabase para almacenar el valor del usuario
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Referenciamos a los elementos del xml.
        registerButton = findViewById(R.id.buttonRegister);
        loginTextView = findViewById(R.id.loginTextView);
    }
    private void registerUser() {

        // Referenciamos a los elementos del xml.
        try {
            emailEditText = findViewById(R.id.inputEmailRegister);
            passwordEditText = findViewById(R.id.inputPasswordRegister);
            usernameEditText = findViewById(R.id.inputUserRegister);
            confirmPasswordEditText = findViewById(R.id.inputPasswordRepeatRegister);

            // Referenciamos los valores de los EditText
            email = emailEditText.getText().toString();
            username = usernameEditText.getText().toString();
            password = passwordEditText.getText().toString();
            confirmPassword = confirmPasswordEditText.getText().toString();

            /* Validacion usando los patterns, si esta poniendo algo mal se vuelve el borde rojo por el contrario verde. */
            if (!ValidacionUsuario.isValidUsername(username)) {
                usernameEditText.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_border_error));
                if(username.length() <=5 || username.length() >=17){
                    Toast.makeText(Activity_registro.this, "Introduzca al menos 6 caracteres y máximo 16 porfavor...", Toast.LENGTH_SHORT).show();
                }
                return ;
            }
            usernameEditText.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_border_success));

            if (!ValidacionUsuario.isValidEmail(email)) {
                emailEditText.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_border_error));
                Toast.makeText(Activity_registro.this, "Introduzca bien el email...", Toast.LENGTH_SHORT).show();
                return;
            }
            emailEditText.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_border_success));

            if (!ValidacionUsuario.isValidPassword(password)) {
                passwordEditText.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_border_error));
                Toast.makeText(Activity_registro.this, "Mínimo 1 mayuscula, letra y caracter especial...", Toast.LENGTH_SHORT).show();
                return;
            }

            usernameEditText.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_border_success));
            if (!password.equals(confirmPassword)) {
                confirmPasswordEditText.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_border_error));
                Toast.makeText(Activity_registro.this, "Introduzca la misma contraseña...", Toast.LENGTH_SHORT).show();
                return;
            }
            confirmPasswordEditText.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_border_success));
            /* ---------------------------------------------------------------------------------------------------------------- */


            // Registramos el usuario una vez pasadas las validaciones
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Si el usuario puede ser creado la task sera succesful
                                // Referenciamos al usuario con getCurrentUser
                                user = mAuth.getCurrentUser();

                                // Guardamos el usuario con el metodo saveUserToDatabase
                                saveUserToDatabase(user.getUid(), username, email, password);

                                // Cambiamos a la activity principal una vez creado el usuario
                                intent = new Intent(Activity_registro.this, Activity_navegacion.class);
                                startActivity(intent);



                            } else {
                                Toast.makeText(Activity_registro.this, "La autenticación falló.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }catch (Exception e){
            System.err.println("Error al intentar validar o sacar los datos");
        }
    }

    // Método para guardar el usuario a la bbdd, referenciando dónde meteremos el usuario y lo creamos.
    private void saveUserToDatabase(String userId, String username, String email, String password) {
        try {
            databaseReference = FirebaseDatabase.getInstance().getReference("users");
            createUser = new User(userId, username, email, password);
            databaseReference.child(userId).setValue(createUser);
        }catch (Exception e){
            System.err.println("Error al intentar guardar los datos");
        }
    }
}