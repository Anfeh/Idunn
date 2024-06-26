package com.example.idunn.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.idunn.Adaptadores.Adaptador;
import com.example.idunn.Datos.Exercises;
import com.example.idunn.Datos.Series;
import com.example.idunn.Datos.User;
import com.example.idunn.Datos.Workout;
import com.example.idunn.Datos.DatosEntrenamiento;
import com.example.idunn.Logica.CurrentUser;
import com.example.idunn.R;
import com.example.idunn.Usuario.Activity_agregar_entrenamiento;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class Agregar extends Fragment {
    private CurrentUser mCurrentUser;

    private ArrayList<DatosEntrenamiento> datosEntrenamientoArrayList;

    private RecyclerView recyclerView;
    private String uid;
    private List<Workout> workouts;
    private List<String> exerciseNames, numSeries;
    private List<Exercises> exercises;
    private List<Series> series;
    private Adaptador adaptador;
    private Button agregarEntenamiento;

    public Agregar() {

    }

    public static Agregar newInstance() {
        Agregar fragment = new Agregar();
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_agregar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        try {
            datosEntrenamientoArrayList = new ArrayList<>();
            mCurrentUser = new CurrentUser(FirebaseDatabase.getInstance().getReference());
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            agregarEntenamiento = view.findViewById(R.id.agregarEntrenamientoButton);
            agregarEntenamiento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (datosEntrenamientoArrayList.size() >= 3) {
                        Toast.makeText(getActivity(), "No se pueden agregar más de 3 entrenamientos", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(getActivity(), Activity_agregar_entrenamiento.class);
                        startActivity(intent);
                    }
                }
            });
            fetchFromDatabase(view, uid);
        }catch (Exception e){
            System.err.println("Error al intentar obtener los datos");
        }

    }
        private void fetchFromDatabase(View view, String uid) {
        try {
            mCurrentUser.getCurrentUser(uid, new CurrentUser.GetUserCallback() {
                @Override
                public void onCallback(User user) {
                    if (user != null) {
                        datosEntrenamientoArrayList.clear();
                        workouts = user.getWorkouts();
                        if (workouts != null) {
                            for (Workout workout : workouts) {
                                exerciseNames = new ArrayList<>();
                                exercises = workout.getExercises();
                                numSeries=null;
                                if (exercises != null) {
                                    for (Exercises exercise : exercises) {
                                        numSeries = new ArrayList<>();
                                        exerciseNames.add(exercise.getName());
                                        series = exercise.getSeries();
                                        if (series != null) {
                                            for (Series serie : series) {
                                                numSeries.add(String.valueOf(serie.getSerie()));
                                            }
                                        }
                                    }
                                    datosEntrenamientoArrayList.add(new DatosEntrenamiento(workout.getName(), exerciseNames, numSeries));
                                }
                            }
                        }
                        recyclerView = view.findViewById(R.id.recyclerView);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setHasFixedSize(true);
                        adaptador = new Adaptador(getActivity(), datosEntrenamientoArrayList, uid);

                        recyclerView.setAdapter(adaptador);
                        adaptador.notifyDataSetChanged();
                    }
                }
            });
        }catch (Exception e){
            System.err.println("Error al intentar obtener los datos");
        }
    }




}