package com.example.idunn.Usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


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
import java.util.List;

public class Activity_historial_detallado extends AppCompatActivity {



    /* Variables usadas */

    private LinearLayout additionalTextContainer;
    private Intent intent;
    private DatosEntrenamiento datosEntrenamiento;
    private List<Series> series, seriesList;
    private View additionalTextView, seriesLinearLayout;
    private TextView textView, numeroTextView, textViewCronometro, tituloEntrenamiento;
    private RecyclerView recyclerViewSeries;
    private SeriesAdapter seriesAdapter, adapter;
    private String workoutDate, workoutName, time, exerciseName;
    private Exercises exercise;
    private Series serie;
    private DatabaseReference databaseReference;
    /* -------------------------------------------------------------- */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_detallado);
        initViews();
        setupData();

    }
    private void initViews(){
        tituloEntrenamiento = findViewById(R.id.tituloEntrenamiento);
        additionalTextContainer = findViewById(R.id.additional_text_container);
        textViewCronometro = findViewById(R.id.cronometro);
        recyclerViewSeries = findViewById(R.id.recyclerViewSeries);
    }

    private void setupData() {
        try {
            recyclerViewSeries.setLayoutManager(new LinearLayoutManager(this));
            seriesList = new ArrayList<>();
            seriesAdapter = new SeriesAdapter(seriesList, this);
            recyclerViewSeries.setAdapter(seriesAdapter);

            intent = getIntent();
            datosEntrenamiento = (DatosEntrenamiento) intent.getSerializableExtra("clicked_item");

            if (datosEntrenamiento != null) {
                tituloEntrenamiento.setText(datosEntrenamiento.getNombreRutina());
                textViewCronometro.setText(datosEntrenamiento.getCronometro());

                obtenerDatosDeFirebase();
            }
        }catch (Exception e){
            System.err.println("Error al intentar setter los adaptadores");
        }
    }

    private void obtenerDatosDeFirebase() {
        try {
            databaseReference = FirebaseDatabase.getInstance().getReference("users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("workouts_timeline");

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {


                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Limpiamos la lista antes de agregar nuevos datos
                    seriesList.clear();

                    // Filtramos solo los datos que corresponden al entrenamiento seleccionado
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        workoutDate = snapshot.child("date").getValue(String.class);
                        workoutName = snapshot.child("workout_name").getValue(String.class);
                        time = snapshot.child("time").getValue(String.class);

                        // Verificamos que workoutDate, workoutName y time no sean nulos antes de compararlos
                        if (workoutDate != null && workoutName != null && time != null &&
                                workoutDate.equals(datosEntrenamiento.getFecha()) &&
                                workoutName.equals(datosEntrenamiento.getNombreRutina()) &&
                                time.equals(datosEntrenamiento.getCronometro())) {

                            for (DataSnapshot exerciseSnapshot : snapshot.child("exercises").getChildren()) {
                                exerciseName = exerciseSnapshot.child("name").getValue(String.class);
                                series = new ArrayList<>();

                                // Iteramos sobre las series dentro del ejercicio
                                for (DataSnapshot seriesSnapshot : exerciseSnapshot.child("series").getChildren()) {
                                    serie = seriesSnapshot.getValue(Series.class);
                                    series.add(serie);
                                }

                                exercise = new Exercises(exerciseName, series);

                                // Inflamos y añadimos el nombre del ejercicio al contenedor
                                additionalTextView = LayoutInflater.from(Activity_historial_detallado.this).inflate(R.layout.additional_text_item, additionalTextContainer, false);
                                textView = additionalTextView.findViewById(R.id.tituloEjercicio);
                                textView.setText(exercise.getName());
                                additionalTextContainer.addView(additionalTextView);

                                // Creamos un nuevo RecyclerView para cada ejercicio
                                recyclerViewSeries = new RecyclerView(Activity_historial_detallado.this);
                                recyclerViewSeries.setLayoutParams(new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                ));
                                additionalTextContainer.addView(recyclerViewSeries);

                                adapter = new SeriesAdapter(exercise.getSeries(), Activity_historial_detallado.this);
                                recyclerViewSeries.setLayoutManager(new LinearLayoutManager(Activity_historial_detallado.this));
                                recyclerViewSeries.setAdapter(adapter);
                            }
                        }
                    }
                    seriesAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Activity_historial_detallado.this, "Error al acceder a la bbdd", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            System.err.println("Error al intentar obtener los datos de Firebase");
        }
    }






}