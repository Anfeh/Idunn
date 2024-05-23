package com.example.idunn.Usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.idunn.Adaptadores.AdapterExercise;
import com.example.idunn.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity_agregar_entrenamiento extends AppCompatActivity {
    /* Variables usadas */
    private EditText editTextNombreEntrenamiento;
    private EditText editTextNumSeries;
    private List<String> ejercicios = new ArrayList<>();
    private RecyclerView recyclerViewEjercicios;
    private TextView editTextAgregarEjercicio, textViewAgregarEntrenamiento;
    private AdapterExercise adapter;
    private long workoutCount;
    private String workoutId;
    private DatabaseReference workoutsRef;
    private String nombreEntrenamiento, numSeries;
    private Map<String, Object> workout, exercise, series;
    private List<Map<String, Object>> exercises, seriesList;
    private ArrayList<String> selectedExerciseNames;
    /* ------------------------------------------------- */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_entrenamiento);

        // Inicializamos todos los métodos
        initViews();
        setAdapter();
        setListeners();
    }

    public void initViews() {
        // Referenciamos al xml
        editTextNombreEntrenamiento = findViewById(R.id.editTextNombreEntrenamiento);
        editTextNumSeries = findViewById(R.id.editTextNumSeries);
        recyclerViewEjercicios = findViewById(R.id.recycler_view_ejercicios);
        editTextAgregarEjercicio = findViewById(R.id.textViewAgregarEjercicio);
        textViewAgregarEntrenamiento = findViewById(R.id.textViewAgregarEntrenamiento);
    }

    public void setAdapter() {
        // Setteamos el adaptador
        recyclerViewEjercicios.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterExercise(ejercicios);
        recyclerViewEjercicios.setAdapter(adapter);
    }

    public void setListeners() {
        editTextAgregarEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_agregar_entrenamiento.this, Activity_ejercicios.class);
                startActivityForResult(intent, 1);
            }
        });

        textViewAgregarEntrenamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtenemos el nombre de entrenamiento y el numero de series introducidos
                nombreEntrenamiento = editTextNombreEntrenamiento.getText().toString();
                numSeries = editTextNumSeries.getText().toString();

                // Si ninguno de los 2 esta vacio procedemos
                if (!nombreEntrenamiento.isEmpty() && !numSeries.isEmpty()) {
                    if (Integer.parseInt(numSeries) > 5) {
                        Toast.makeText(Activity_agregar_entrenamiento.this, "Introduzca menos de 6 series porfavor...", Toast.LENGTH_SHORT).show();
                    } else {
                        workout = new HashMap<>();
                        workout.put("name", nombreEntrenamiento);

                        // Creamos una lista de ejercicios
                        exercises = new ArrayList<>();

                        // Recorremos los ejercicios que haya en el adaptador
                        for (int i = 0; i < ejercicios.size(); i++) {
                            exercise = new HashMap<>();

                            // Ponemos el nombre del ejericio en el HashMap
                            exercise.put("name", ejercicios.get(i));

                            // Creamos una lista de series
                            seriesList = new ArrayList<>();

                            // Recorremos num series para almacenarlo
                            for (int j = 1; j <= Integer.parseInt(numSeries); j++) {
                                // Creando y destruyendo el HashMap para asi poder recorrer bien
                                series = new HashMap<>();
                                series.put("serie", j);
                                series.put("repetitions", 0);
                                series.put("weight", 0);

                                // Los agregamos a su lista de series
                                seriesList.add(series);
                            }
                            // Metemos en el hash la lista de series
                            exercise.put("series", seriesList);
                            exercises.add(exercise);
                        }
                        // Metemos la lista general en el nodo de ejercicios con su lista de series
                        workout.put("exercises", exercises);

                        // Save workout to Firebase
                        workoutsRef = FirebaseDatabase.getInstance().getReference("users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("workouts");

                        workoutsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                workoutCount = snapshot.getChildrenCount();
                                workoutId = String.valueOf(workoutCount);
                                workoutsRef.child(workoutId).setValue(workout).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d("Firebase", "Se guardo correctamente el etrenamiento");
                                        } else {
                                            Log.e("Firebase", "Error al guardar", task.getException());
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.e("FirebaseError", "Error al leer datos de Firebase", error.toException());
                            }
                        });

                        finish();
                    }
                } else {
                    Toast.makeText(Activity_agregar_entrenamiento.this, "Porfavor rellene todos los campos...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            selectedExerciseNames = data.getStringArrayListExtra("selected_ejercicios");
            if (selectedExerciseNames != null && !selectedExerciseNames.isEmpty()) {
                ejercicios.addAll(selectedExerciseNames);
                adapter.notifyDataSetChanged();
            }
        }
    }

}
