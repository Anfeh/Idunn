package com.example.idunn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.idunn.Adaptadores.AdapterExercise;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class activity_ejercicios extends AppCompatActivity {
    private RecyclerView recyclerViewEjerciciosList, recyclerView;
    private List<String> exercisesList = new ArrayList<>();
    private List<String> exercisesListDouble = new ArrayList<>();
    private AdapterExercise adapter;
    private TextView tituloEjercicio;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios);

        recyclerViewEjerciciosList = findViewById(R.id.recycler_view_ejercicios_list);
        recyclerViewEjerciciosList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterExercise(exercisesList);
        recyclerViewEjerciciosList.setAdapter(adapter);
        tituloEjercicio = findViewById(R.id.tituloEjercicio);
        obtenerDatosDeFirebase();
    }
    List<String> exerciseList = new ArrayList<>();
    private void obtenerDatosDeFirebase() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("workout_exercises");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot exerciseSnapshot : dataSnapshot.getChildren()) {

                    String exerciseName = exerciseSnapshot.child("name").getValue(String.class);

                    TextView titleTextView = new TextView(activity_ejercicios.this);
                    titleTextView.setText(exerciseName);
                    titleTextView.setTextSize(20);
                    linearLayout = findViewById(R.id.linearLayoutEjercicios);
                    linearLayout.addView(titleTextView);


                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    titleTextView.setLayoutParams(layoutParams);


                    exerciseList = new ArrayList<>();
                    for (DataSnapshot exercise : exerciseSnapshot.child("exercises").getChildren()) {
                        if(exerciseSnapshot.child("name").getValue(String.class).equals(exerciseName)){
                            String exerciseItem = exercise.child("name").getValue(String.class);
                            exerciseList.add(exerciseItem);
                        }

                    }

                    recyclerView = new RecyclerView(activity_ejercicios.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(activity_ejercicios.this));

                    AdapterExercise adapter = new AdapterExercise(exerciseList);

                    recyclerView.setAdapter(adapter);
                    linearLayout.setPadding(20,10,0,10);
                    linearLayout.addView(recyclerView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseError", "Error al leer datos de Firebase", databaseError.toException());
            }
        });
    }

    public void onItemClick(int position) {
        String selectedExerciseName = exerciseList.get(position);

        ArrayList<String> selectedExerciseNames = new ArrayList<>();
        selectedExerciseNames.add(selectedExerciseName);

        Intent intent = new Intent();
        intent.putStringArrayListExtra("selected_ejercicios", selectedExerciseNames);

        setResult(RESULT_OK, intent);

        finish();
    }






}