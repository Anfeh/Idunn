package com.example.idunn.Usuario;

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
import com.example.idunn.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Activity_ejercicios extends AppCompatActivity {

    /* Variables usadas */
    private RecyclerView recyclerViewEjerciciosList, recyclerView;
    private List<String> exercisesList = new ArrayList<>(); // Asegúrate de inicializar la lista aquí
    private AdapterExercise adapter;
    private TextView tituloEjercicio, titleTextView;
    private LinearLayout linearLayout;
    private String exerciseName, exerciseItem, selectedExerciseName;
    private DatabaseReference ref;
    private ArrayList<String> selectedExerciseNames;
    private Intent intent;
    private List<String> exerciseList;

    // Se utiliza para establecer los parámetros de diseño del TextView.
    private LinearLayout.LayoutParams layoutParams;
    /* -------------------------------------------------------- */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios);

        linearLayout = findViewById(R.id.linearLayoutEjercicios);

        ref = FirebaseDatabase.getInstance().getReference("workout_exercises");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot exerciseSnapshot : dataSnapshot.getChildren()) {
                    String exerciseName = exerciseSnapshot.child("name").getValue(String.class);
                    TextView titleTextView = new TextView(Activity_ejercicios.this);
                    titleTextView.setText(exerciseName);
                    titleTextView.setTextSize(20);

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    titleTextView.setLayoutParams(layoutParams);

                    linearLayout.addView(titleTextView);

                    List<String> exerciseList = new ArrayList<>();
                    for (DataSnapshot exercise : exerciseSnapshot.child("exercises").getChildren()) {
                        exerciseList.add(exercise.child("name").getValue(String.class));
                    }

                    RecyclerView recyclerView = new RecyclerView(Activity_ejercicios.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Activity_ejercicios.this));
                    AdapterExercise adapter = new AdapterExercise(exerciseList, new AdapterExercise.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            String selectedExerciseName = exerciseList.get(position);
                            ArrayList<String> selectedExerciseNames = new ArrayList<>();
                            selectedExerciseNames.add(selectedExerciseName);
                            intent = new Intent();
                            intent.putStringArrayListExtra("selected_ejercicios", selectedExerciseNames);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    });
                    recyclerView.setAdapter(adapter);
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


        // Obtenemos el ejercicio que hayamos pulsado
        selectedExerciseName = exerciseList.get(position);

        // Creamos la lista y agregamos dicho ejercicio
        selectedExerciseNames = new ArrayList<>();
        selectedExerciseNames.add(selectedExerciseName);

        // Creamos un intent y ponemos un extra con dicha clave para referenciarlo después
        intent = new Intent();
        intent.putStringArrayListExtra("selected_ejercicios", selectedExerciseNames);

        // Si pasa correctamete  lanzamos el intent con los datos
        setResult(RESULT_OK, intent);

        // Finalizamos activity
        finish();
    }
}