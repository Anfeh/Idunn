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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Agregar extends Fragment {

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

        datosEntrenamientoArrayList.add(new DatosEntrenamiento("Pecho", Arrays.asList("Jalón de pene","Aguacate"), Arrays.asList("1", "2", "3")));
        datosEntrenamientoArrayList.add(new DatosEntrenamiento("Espalda", Arrays.asList("Jalon de pecho ", "aguacate", "simon"), Arrays.asList("1", "2", "3")));
        datosEntrenamientoArrayList.add(new DatosEntrenamiento("Pierna", Arrays.asList("Text 5", "Text 6"), Arrays.asList("1", "2", "3","4")));

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        Adaptador adaptador = new Adaptador(getActivity(), datosEntrenamientoArrayList);

        recyclerView.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();

    }




}