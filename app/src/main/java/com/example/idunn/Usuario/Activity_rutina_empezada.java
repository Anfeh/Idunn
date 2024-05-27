package com.example.idunn.Usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.idunn.Adaptadores.SeriesAdapter;
import com.example.idunn.Datos.Exercises;
import com.example.idunn.Datos.Series;
import com.example.idunn.Datos.DatosEntrenamiento;
import com.example.idunn.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity_rutina_empezada extends AppCompatActivity {

    private TextView tituloEntrenamiento;
    private DatosEntrenamiento datosEntrenamiento;
    private LinearLayout additionalTextContainer;
    private TextView acabarEntrenamiento, textView;
    private RecyclerView recyclerViewSeries, recyclerView;
    private SeriesAdapter adapter;
    private List<RecyclerView> recyclerViewsList = new ArrayList<>();
    private boolean workoutStored = false;
    private Intent intent;
    private String[] seriesArray;
    private List<String> series;
    private View exerciseTitleView;
    private List<Exercises> exercisesList;
    private String nombreEjercicio, workoutId, fechaActual;
    private List<Series> enteredData, seriesList;
    private FirebaseDatabase database;
    private DatabaseReference databaseRef;
    private Map<String, Object> workoutData, exerciseData, seriesItemData;
    private List<Map<String, Object>> exercisesData, seriesData;
    private long workoutCount;
    private Chronometer chronometer;
    private Calendar calendar;

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
        chronometer = findViewById(R.id.cronometro);
        chronometer.setFormat("%s");
        chronometer.start();
    }

    private void setupRecyclerView() {
        recyclerViewSeries.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SeriesAdapter(new ArrayList<>(), this, recyclerViewSeries);
    }

    private void setupData() {
        try {
            intent = getIntent();
            datosEntrenamiento = (DatosEntrenamiento) intent.getSerializableExtra("datosEntrenamiento");
            seriesArray = intent.getStringArrayExtra("series");
            series = Arrays.asList(seriesArray);

            if (datosEntrenamiento != null) {
                tituloEntrenamiento.setText(datosEntrenamiento.getNombreRutina());

                for (String additionalText : datosEntrenamiento.getNombreEntrenamiento()) {
                    exerciseTitleView = LayoutInflater.from(this).inflate(R.layout.additional_text_item, additionalTextContainer, false);

                    textView = exerciseTitleView.findViewById(R.id.tituloEjercicio);
                    textView.setText(additionalText);

                    additionalTextContainer.addView(exerciseTitleView);

                    recyclerViewSeries = new RecyclerView(this);
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
                        adapter.addSeries(new Series(i + 1, 0, 0));
                    }
                }
            }
        }catch (Exception e){
            System.err.println("Error al settear los datos");
        }
    }


    private void setupListeners() {
        try {
            acabarEntrenamiento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    exercisesList = new ArrayList<>();


                    for (int i = 0; i < recyclerViewsList.size(); i++) {
                        recyclerView = recyclerViewsList.get(i);
                        nombreEjercicio = datosEntrenamiento.getNombreEntrenamiento().get(i);

                        adapter = (SeriesAdapter) recyclerView.getAdapter();
                        enteredData = adapter.getEnteredData();
                        seriesList = new ArrayList<>();
                        for (Series series : enteredData) {

                            seriesList.add(new Series(series.getSerie(), series.getRepetitions(), series.getWeight()));
                        }
                        exercisesList.add(new Exercises(nombreEjercicio, seriesList));
                    }
                    storeWorkout(datosEntrenamiento.getNombreRutina(), getFechaActual(), chronometer.getText().toString(), exercisesList);
                    finish();
                }
            });
        }catch (Exception e){
            System.err.println("Error al intentar clickear en un item");
        }
    }

    private String getFechaActual(){
        calendar = Calendar.getInstance();
        return fechaActual = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
    }
    private void storeWorkout(String workoutName, String date, String tiempo, List<Exercises> exercisesList) {
        try {
            database = FirebaseDatabase.getInstance();
            databaseRef = database.getReference("users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("workouts_timeline");

            workoutData = new HashMap<>();
            workoutData.put("workout_name", workoutName);
            workoutData.put("date", date);
            workoutData.put("time", tiempo);
            exercisesData = new ArrayList<>();

            for (Exercises exercise : exercisesList) {
                exerciseData = new HashMap<>();
                exerciseData.put("name", exercise.getName());

                seriesData = new ArrayList<>();
                for (Series series : exercise.getSeries()) {
                    seriesItemData = new HashMap<>();
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
                    workoutCount = dataSnapshot.getChildrenCount();
                    workoutId = String.valueOf(workoutCount);
                    databaseRef.child(workoutId).setValue(workoutData);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("Firebase", "Error al intentar recoger el count de la bbdd", databaseError.toException());
                }
            });
        }catch (Exception e){
            System.err.println("Error al guardar los datos ");
        }
    }


}