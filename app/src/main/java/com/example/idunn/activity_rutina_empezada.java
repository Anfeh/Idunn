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
                    adapter.addSeries(new Series(i+1, 2, 0));
                }
            }
        }
    }


    private void setupListeners() {
        acabarEntrenamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (RecyclerView recyclerView : recyclerViewsList) {
                    SeriesAdapter adapter = (SeriesAdapter) recyclerView.getAdapter();
                    List<Series> enteredData = adapter.getEnteredData();
                    for (Series series : enteredData) {
                        Log.d("SeriesAdapter", "Series = "+ series.getSerie() +", Repeticiones = " + series.getRepetitions() + ", Peso = " + series.getWeight());
                    }
                }
                finish();
            }
        });
    }




    private void storeWorkout(String workoutName, String date, List<Exercises> exercises) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("workouts_timeline");

        Map<String, Object> workoutData = new HashMap<>();
        workoutData.put("workout_name", workoutName);
        workoutData.put("date", date);

        List<Map<String, Object>> exercisesData = new ArrayList<>();
        for (Exercises exercise : exercises) {
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

        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long workoutTimelineCount = dataSnapshot.getChildrenCount();
                String workoutTimelineKey = String.valueOf(workoutTimelineCount);

                databaseRef.child(workoutTimelineKey).setValue(workoutData).addOnCompleteListener(new OnCompleteListener<Void>() {
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
                Log.e("Firebase", "Error getting workout timeline count", databaseError.toException());
            }
        });
    }
}