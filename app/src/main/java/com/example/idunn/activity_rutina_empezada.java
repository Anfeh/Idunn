package com.example.idunn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
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
import com.example.idunn.Datos.Workout;
import com.example.idunn.Datos.Workout_timeline;
import com.example.idunn.Logica.CronometroFormateado;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

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
    DatosEntrenamiento datosEntrenamiento;
    private Chronometer chronometer;
    private LinearLayout additionalTextContainer;

    private TextView acabarEntrenamiento;
    private List<Series> seriesList;
    private DatabaseReference databaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutina_empezada);
        chronometer = findViewById(R.id.cronometro);
        chronometer = findViewById(R.id.cronometro);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();


        tituloEntrenamiento = findViewById(R.id.tituloEntrenamiento);
        additionalTextContainer = findViewById(R.id.additional_text_container);
        acabarEntrenamiento = findViewById(R.id.acabarEntrenamiento);
        seriesList = new ArrayList<>();

        Intent intent = getIntent();
        datosEntrenamiento = (DatosEntrenamiento) intent.getSerializableExtra("datosEntrenamiento");
        String[] seriesArray = intent.getStringArrayExtra("series");
        List<String> series = Arrays.asList(seriesArray);

        acabarEntrenamiento.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Get the workout name and date
                String workoutName = tituloEntrenamiento.getText().toString();
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                List<Exercises> exercises = getExercises();

                storeWorkout(workoutName, date, exercises);
                finish();
            }
        });
        if (datosEntrenamiento != null) {
            tituloEntrenamiento.setText(datosEntrenamiento.getNombreRutina());

            for (String additionalText : datosEntrenamiento.getNombreEntrenamiento()) {
                View additionalTextView = LayoutInflater.from(this).inflate(R.layout.additional_text_item, additionalTextContainer, false);

                TextView textView = additionalTextView.findViewById(R.id.tituloEjercicio);
                textView.setText(additionalText);


                additionalTextContainer.addView(additionalTextView);
                for (int i = 0; i < series.size(); i++) {
                    View seriesLinearLayout = LayoutInflater.from(this).inflate(R.layout.series_item, additionalTextContainer, false);
                    TextView numeroTextView = seriesLinearLayout.findViewById(R.id.numero);
                    numeroTextView.setText(String.valueOf(i + 1));



                    additionalTextContainer.addView(seriesLinearLayout);
                }


            }
        }


    }



    private void storeWorkout(String workoutName, String date, List<Exercises> exercises) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("workouts_timeline");

        Workout_timeline workoutTimeline = new Workout_timeline(workoutName, date, exercises);
        databaseRef.push().setValue(workoutTimeline).addOnCompleteListener(new OnCompleteListener<Void>() {
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
    private List<Exercises> getExercises() {
        List<Exercises> exercises = new ArrayList<>();
        for (int i = 0; i < additionalTextContainer.getChildCount(); i++) {
            View additionalTextView = additionalTextContainer.getChildAt(i);
            if (additionalTextView instanceof LinearLayout) {
                LinearLayout additionalExerciseLayout = (LinearLayout) additionalTextView;
                String exerciseName = ((TextView) additionalExerciseLayout.getChildAt(0)).getText().toString();
                List<Series> seriesList = new ArrayList<>();
                for (int j = 0; j < additionalExerciseLayout.getChildCount(); j++) {
                    View seriesLayout = additionalExerciseLayout.getChildAt(j);
                    if (seriesLayout instanceof LinearLayout && j > 0) {
                        LinearLayout seriesItemLayout = (LinearLayout) seriesLayout;
                        String serieText = ((TextView) seriesItemLayout.getChildAt(0)).getText().toString();
                        if (serieText.matches("\\d+")) {
                            int serie = Integer.parseInt(serieText);
                            int repetitions = getRepetitions(seriesItemLayout);
                            int weight = getWeight(seriesItemLayout);
                            seriesList.add(new Series(serie, repetitions, weight));
                        }
                    }
                }
                exercises.add(new Exercises(exerciseName, seriesList));
            }
        }
        return exercises;
    }

    private int getRepetitions(LinearLayout seriesItemLayout) {
        EditText repetitionsEditText = (EditText) seriesItemLayout.getChildAt(1);
        String repetitionsText = repetitionsEditText.getText().toString();
        if (repetitionsText.matches("\\d+")) {
            return Integer.parseInt(repetitionsText);
        } else {
            throw new IllegalArgumentException("Invalid repetitions value: " + repetitionsText);
        }
    }

    private int getWeight(LinearLayout seriesItemLayout) {
        EditText weightEditText = (EditText) seriesItemLayout.getChildAt(2);
        String weightText = weightEditText.getText().toString();
        if (weightText.matches("\\d+")) {
            return Integer.parseInt(weightText);
        } else {
            throw new IllegalArgumentException("Invalid weight value: " + weightText);
        }
    }

}