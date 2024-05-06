package com.example.idunn;

import android.media.tv.TableRequest;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.idunn.Datos.Exercises;
import com.example.idunn.Datos.Series;
import com.example.idunn.Datos.User;
import com.example.idunn.Datos.Workout;
import com.example.idunn.Logica.CurrentUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Agregar extends Fragment {
    private CurrentUser mCurrentUser;

    ArrayList<DatosEntrenamiento> datosEntrenamientoArrayList;

    RecyclerView recyclerView;
    ImageView logo;

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

        datosEntrenamientoArrayList = new ArrayList<>();
        mCurrentUser = new CurrentUser(FirebaseDatabase.getInstance().getReference());
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        fetchDataFromDatabase(view,uid);
    }

    private void fetchDataFromDatabase(View view, String uid) {
        mCurrentUser.getCurrentUser(uid, new CurrentUser.GetUserCallback() {
            @Override
            public void onCallback(User user) {
                if (user != null) {
                    List<Workout> workouts = user.getWorkouts();
                    for (Workout workout : workouts) {
                        List<String> exerciseNames = new ArrayList<>();
                        List<Exercises> exercises = workout.getExercises();
                        List<String> numSeries = null;
                        for (Exercises exercise : exercises) {
                            exerciseNames.add(exercise.getName());
                            List<Series> series = exercise.getSeries();
                            numSeries = new ArrayList<>();
                            for (Series serie : series) {
                                numSeries.add(String.valueOf(serie.getSerie()));
                            }
                        }
                        datosEntrenamientoArrayList.add(new DatosEntrenamiento(workout.getName(), exerciseNames, numSeries));
                    }
                    recyclerView = view.findViewById(R.id.recyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setHasFixedSize(true);
                    Adaptador adaptador = new Adaptador(getActivity(), datosEntrenamientoArrayList);

                    recyclerView.setAdapter(adaptador);
                    adaptador.notifyDataSetChanged();
                }
            }
        });
    }




}