package com.example.idunn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class activity_registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button registerButton;
        TextView loginTextView;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        registerButton = findViewById(R.id.buttonRegister);
        loginTextView = findViewById(R.id.loginTextView);
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
                Intent intent = new Intent(activity_registro.this, activity_navegacion.class);
                startActivity(intent);
            }
        });


    }
}