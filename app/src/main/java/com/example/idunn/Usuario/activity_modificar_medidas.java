package com.example.idunn.Usuario;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.idunn.Datos.Measurement;
import com.example.idunn.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class activity_modificar_medidas extends AppCompatActivity {

    /* Variables usadas*/

    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private TextInputEditText weightEditText, heightEditText, armEditText, legEditText, forearmEditText, chestEditText, calfEditText, waistEditText;
    int weightText, heightText, armText, legText, forearmText, chestText, calfText, waistText;
    private Measurement measurement;
    private DatabaseReference measurementsRef;
    private String userId;
    private Button saveButton;

    /* ------------------------------------------ */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_medidas);

        initViews();
        cargarMedidas();
        setListeners();

    }

    private void cargarMedidas() {
        userId = mAuth.getCurrentUser().getUid();
        measurementsRef = mDatabase.getReference("users").child(userId).child("measurements");
        measurementsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                measurement = dataSnapshot.getValue(Measurement.class);
                if (measurement != null) {
                    weightEditText.setText(String.valueOf(measurement.getWeight()));
                    heightEditText.setText(String.valueOf(measurement.getHeight()));
                    armEditText.setText(String.valueOf(measurement.getArm_size()));
                    legEditText.setText(String.valueOf(measurement.getLeg_size()));
                    forearmEditText.setText(String.valueOf(measurement.getForearm_size()));
                    chestEditText.setText(String.valueOf(measurement.getChest_size()));
                    calfEditText.setText(String.valueOf(measurement.getCalf_size()));
                    waistEditText.setText(String.valueOf(measurement.getWaist()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void initViews(){
        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        weightEditText = findViewById(R.id.weightEditText);
        heightEditText = findViewById(R.id.heightEditText);
        armEditText = findViewById(R.id.armEditText);
        legEditText = findViewById(R.id.legEditText);
        forearmEditText = findViewById(R.id.forearmEditText);
        chestEditText = findViewById(R.id.chestEditText);
        calfEditText = findViewById(R.id.calfEditText);
        waistEditText = findViewById(R.id.waistEditText);
        saveButton = findViewById(R.id.saveButton);
    }
    private void setListeners(){
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weightText = Integer.parseInt(weightEditText.getText().toString());
                heightText = Integer.parseInt(heightEditText.getText().toString());
                armText = Integer.parseInt(armEditText.getText().toString());
                legText = Integer.parseInt(legEditText.getText().toString());
                forearmText = Integer.parseInt(forearmEditText.getText().toString());
                chestText = Integer.parseInt(chestEditText.getText().toString());
                calfText = Integer.parseInt(calfEditText.getText().toString());
                waistText = Integer.parseInt(waistEditText.getText().toString());
                measurement = new Measurement(armText, calfText, chestText, forearmText, heightText, weightText, legText, waistText);
                userId = mAuth.getCurrentUser().getUid();
                measurementsRef = mDatabase.getReference("users").child(userId).child("measurements");

                measurementsRef.setValue(measurement);

                finish();
            }
        });
    }
}