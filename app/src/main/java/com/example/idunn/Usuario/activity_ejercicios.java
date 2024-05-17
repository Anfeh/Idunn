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

public class activity_ejercicios extends AppCompatActivity {

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

        // Declaración para el uso del recycler view y ajustando su adaptador
        recyclerViewEjerciciosList = findViewById(R.id.recycler_view_ejercicios_list);
        recyclerViewEjerciciosList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterExercise(exercisesList);
        recyclerViewEjerciciosList.setAdapter(adapter);

        // Referencias al xml
        tituloEjercicio = findViewById(R.id.tituloEjercicio);

        // Llamada para obtener los datos de Firebase
        ref = FirebaseDatabase.getInstance().getReference("workout_exercises");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Recorremos los ejercicios
                for (DataSnapshot exerciseSnapshot : dataSnapshot.getChildren()) {


                    // Obtenemos si nombre y los ponemos en los TextViews
                    exerciseName = exerciseSnapshot.child("name").getValue(String.class);
                    titleTextView = new TextView(activity_ejercicios.this);
                    titleTextView.setText(exerciseName);
                    titleTextView.setTextSize(20);

                    // Referenciamos en el xml al LinearLayout
                    linearLayout = findViewById(R.id.linearLayoutEjercicios);

                    // Lo agregamos al LinearLayout
                    linearLayout.addView(titleTextView);

                    // Creación de parámetros de diseño con una anchura de MATCH_PARENT y una altura de WRAP_CONTENT
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    // Y lo aplicamos
                    titleTextView.setLayoutParams(layoutParams);

                    // Creación y destrucción continua para obtener los ejercicios de cada Grupo muscular
                    exerciseList = new ArrayList<>();

                    // Recorremos los nodos de exercises
                    for (DataSnapshot exercise : exerciseSnapshot.child("exercises").getChildren()) {

                        // Y los almacenamos en la lista
                        if(exerciseSnapshot.child("name").getValue(String.class).equals(exerciseName)){
                            exerciseItem = exercise.child("name").getValue(String.class);
                            exerciseList.add(exerciseItem);
                        }

                    }


                    // Creamos el recyclerview y adaptador y los seteamos
                    recyclerView = new RecyclerView(activity_ejercicios.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(activity_ejercicios.this));
                    adapter = new AdapterExercise(exerciseList);
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
