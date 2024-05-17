package com.example.idunn.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.idunn.Adaptadores.HistoryAdapter;
import com.example.idunn.DatosEntrenamiento;
import com.example.idunn.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class Historial extends Fragment {

    private ArrayList<DatosEntrenamiento> datosEntrenamientoArrayList;
    private RecyclerView recyclerView;
    private HistoryAdapter adaptador;

    private String date, workoutName, exerciseName, totalSeries, uid;
    private List<String> exerciseNames, seriesCount;
    private DatosEntrenamiento datos;


    public Historial() {

    }

    public static Historial newInstance() {
        Historial fragment = new Historial();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_historial, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        datosEntrenamientoArrayList = new ArrayList<>();
        adaptador = new HistoryAdapter(getActivity(), datosEntrenamientoArrayList);
        recyclerView.setAdapter(adaptador);

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("users").child(uid).child("workouts_timeline")
                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DataSnapshot snapshot : task.getResult().getChildren()) {

                                date = snapshot.child("date").getValue(String.class);
                                workoutName = snapshot.child("workout_name").getValue(String.class);
                                exerciseNames = new ArrayList<>();
                                seriesCount = new ArrayList<>();

                                for (DataSnapshot exerciseSnapshot : snapshot.child("exercises").getChildren()) {
                                    exerciseName = exerciseSnapshot.child("name").getValue(String.class);
                                    exerciseNames.add(exerciseName);
                                    totalSeries = String.valueOf(exerciseSnapshot.child("series").getChildrenCount());
                                    seriesCount.add(totalSeries);
                                }

                                datos = new DatosEntrenamiento(date, workoutName, exerciseNames, seriesCount);
                                datosEntrenamientoArrayList.add(datos);
                            }
                            adaptador.notifyDataSetChanged();
                        }
                    }
                });
    }


}