package com.example.idunn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.idunn.Adaptadores.AdapterExercise;

import java.util.ArrayList;
import java.util.List;

public class activity_agregar_entrenamiento extends AppCompatActivity {
    private RecyclerView recyclerViewEjercicios;
    private TextView editTextAgregarEjercicio;
    private List<String> ejercicios = new ArrayList<>();
    private AdapterExercise adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_entrenamiento);

        recyclerViewEjercicios = findViewById(R.id.recycler_view_ejercicios);
        recyclerViewEjercicios.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterExercise(ejercicios);
        recyclerViewEjercicios.setAdapter(adapter);

        editTextAgregarEjercicio = findViewById(R.id.textViewAgregarEjercicio);
        editTextAgregarEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_agregar_entrenamiento.this, activity_ejercicios.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            ArrayList<String> selectedExerciseNames = data.getStringArrayListExtra("selected_ejercicios");
            if (selectedExerciseNames != null && !selectedExerciseNames.isEmpty()) {
                ejercicios.addAll(selectedExerciseNames);
                adapter.notifyDataSetChanged();
            }
        }
    }

}
