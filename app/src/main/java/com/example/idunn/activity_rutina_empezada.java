package com.example.idunn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.idunn.Datos.Exercises;
import com.example.idunn.Datos.Series;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class activity_rutina_empezada extends AppCompatActivity {

    private TextView tituloEntrenamiento;
    private DatosEntrenamiento datosEntrenamiento;
    private LinearLayout additionalTextContainer;
    private TextView acabarEntrenamiento;
    private RecyclerView recyclerViewSeries;
    private SeriesAdapter adapter;
    private List<RecyclerView> recyclerViewsList = new ArrayList<>();
    private boolean workoutStored = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutina_empezada);

        initViews();
        setupRecyclerView();
        setupData();
        setupListeners();
    }

    private void initViews() {
        tituloEntrenamiento = findViewById(R.id.tituloEntrenamiento);
        additionalTextContainer = findViewById(R.id.additional_text_container);
        acabarEntrenamiento = findViewById(R.id.acabarEntrenamiento);
        recyclerViewSeries = findViewById(R.id.recyclerViewSeries);
    }

    private void setupRecyclerView() {
        recyclerViewSeries.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SeriesAdapter(new ArrayList<>(), this, recyclerViewSeries);
    }

    private void setupData() {
        Intent intent = getIntent();
        datosEntrenamiento = (DatosEntrenamiento) intent.getSerializableExtra("datosEntrenamiento");
        String[] seriesArray = intent.getStringArrayExtra("series");
        List<String> series = Arrays.asList(seriesArray);
        Log.d("SeriesAdapter", "Elemento: Repeticiones = " + series);

        if (datosEntrenamiento != null) {
            tituloEntrenamiento.setText(datosEntrenamiento.getNombreRutina());

            for (String additionalText : datosEntrenamiento.getNombreEntrenamiento()) {
                View exerciseTitleView = LayoutInflater.from(this).inflate(R.layout.additional_text_item, additionalTextContainer, false);

                TextView textView = exerciseTitleView.findViewById(R.id.tituloEjercicio);
                textView.setText(additionalText);

                additionalTextContainer.addView(exerciseTitleView);

                RecyclerView recyclerViewSeries = new RecyclerView(this);
                recyclerViewSeries.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));

                additionalTextContainer.addView(recyclerViewSeries);

                recyclerViewsList.add(recyclerViewSeries);

                adapter = new SeriesAdapter(new ArrayList<>(), this, recyclerViewSeries);
                recyclerViewSeries.setLayoutManager(new LinearLayoutManager(this));
                recyclerViewSeries.setAdapter(adapter);

                for (int i = 0; i < series.size(); i++) {
                    adapter.addSeries(new Series(i + 1, 2, 0));
                }
            }
        }
    }


    private void setupListeners() {
        acabarEntrenamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Exercises> exercisesList = new ArrayList<>();
                for (int i = 0; i < recyclerViewsList.size(); i++) {
                    RecyclerView recyclerView = recyclerViewsList.get(i);
                    String nombreEjercicio = datosEntrenamiento.getNombreEntrenamiento().get(i);

                    SeriesAdapter adapter = (SeriesAdapter) recyclerView.getAdapter();
                    List<Series> enteredData = adapter.getEnteredData();

                    // Crear la lista de series solo para el ejercicio actual
                    List<Series> seriesList = new ArrayList<>();
                    for (Series series : enteredData) {
                        seriesList.add(new Series(series.getSerie(), series.getRepetitions(), series.getWeight()));
                    }

                    // Agregar el ejercicio con su lista de series correspondiente
                    exercisesList.add(new Exercises(nombreEjercicio, seriesList));

                    Log.d("SeriesAdapter", "Ejercicio: " + nombreEjercicio + ", Series = " + seriesList);
                }

                // Almacenar los datos de entrenamiento
                storeWorkout(datosEntrenamiento.getNombreRutina(), "2024-05-07", exercisesList);

                // Finalizar la actividad
                finish();
            }
        });
    }



    private void storeWorkout(String workoutName, String date, List<Exercises> exercisesList) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("workouts_timeline");

        Map<String, Object> workoutData = new HashMap<>();
        workoutData.put("workout_name", workoutName);
        workoutData.put("date", date);

        List<Map<String, Object>> exercisesData = new ArrayList<>();
        for (Exercises exercise : exercisesList) {
            Map<String, Object> exerciseData = new HashMap<>();
            exerciseData.put("name", exercise.getName());

            List<Map<String, Object>> seriesData = new ArrayList<>();
            for (Series series : exercise.getSeries()) {
                Map<String, Object> seriesItemData = new HashMap<>();
                seriesItemData.put("serie", series.getSerie());
                seriesItemData.put("repetitions", series.getRepetitions());
                seriesItemData.put("weight", series.getWeight());
                seriesData.add(seriesItemData);
            }
            exerciseData.put("series", seriesData);
            exercisesData.add(exerciseData);
        }
        workoutData.put("exercises", exercisesData);

        // Guardar los datos del entrenamiento en Firebase
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long workoutCount = dataSnapshot.getChildrenCount();
                String workoutId = String.valueOf(workoutCount);

                // Agregar el entrenamiento con el ID autoincremental
                databaseRef.child(workoutId).setValue(workoutData)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("Firebase", "Workout stored successfully");
                                } else {
                                    Log.e("Firebase", "Error storing workout", task.getException());
                                }
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error getting workout count", databaseError.toException());
            }
        });
    }

    private List<Exercises> obtenerDatosEjercicios() {
        List<Exercises> exercisesList = new ArrayList<>();

        // Recorrer cada RecyclerView en additionalTextContainer
        for (int i = 0; i < additionalTextContainer.getChildCount(); i++) {
            View childView = additionalTextContainer.getChildAt(i);

            if (childView instanceof RecyclerView) {
                RecyclerView recyclerView = (RecyclerView) childView;
                SeriesAdapter adapter = (SeriesAdapter) recyclerView.getAdapter();


                // Obtener las series del adaptador del RecyclerView
                List<Series> seriesList = adapter.getEnteredData();

                // Crear un objeto Exercises con el nombre del ejercicio y las series
                Exercises exercise = new Exercises("exerciseName", seriesList);

                // Agregar el ejercicio a la lista de ejercicios
                exercisesList.add(exercise);
            }
        }

        return exercisesList;
    }
}