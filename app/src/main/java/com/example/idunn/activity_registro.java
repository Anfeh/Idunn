package com.example.idunn;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.idunn.Datos.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activity_registro extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Button registerButton;
    private TextView loginTextView;
    private EditText emailEditText, passwordEditText, usernameEditText;
    private String username, email, password;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private User createUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        registerButton = findViewById(R.id.buttonRegister);
        loginTextView = findViewById(R.id.loginTextView);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_registro.this, activity_login.class);
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }
    private void registerUser() {
        emailEditText = findViewById(R.id.inputEmailRegister);
        passwordEditText = findViewById(R.id.inputPasswordRegister);
        usernameEditText = findViewById(R.id.inputUserRegister);

        email = emailEditText.getText().toString();
        username = usernameEditText.getText().toString();
        password = passwordEditText.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user = mAuth.getCurrentUser();
                            saveUserToDatabase(user.getUid(),username, email, password);
                        } else {
                            Toast.makeText(activity_registro.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveUserToDatabase(String userId, String username, String email, String password) {
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        createUser = new User(userId, username, email, password);
        databaseReference.child(userId).setValue(createUser);
    }
}