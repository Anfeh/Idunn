package com.example.idunn;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.idunn.Adaptadores.Adaptador;

import java.util.ArrayList;


public class Historial extends Fragment {

    ArrayList<DatosEntrenamiento> datosEntrenamientoArrayList;
    RecyclerView recyclerView;
    Adaptador adaptador;


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

        datosEntrenamientoArrayList = new ArrayList<>();

       // datosEntrenamientoArrayList.add(new DatosEntrenamiento("Pecho", Arrays.asList("Text 1", "Text 2", "H", "p","k","l","s","f","w"), Arrays.asList("1","2","3")));

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        adaptador = new Adaptador(getActivity(), datosEntrenamientoArrayList);

        recyclerView.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();
    }
}