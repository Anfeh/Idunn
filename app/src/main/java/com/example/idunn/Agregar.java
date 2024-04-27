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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Agregar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Agregar extends Fragment {

    ArrayList<DatosEntrenamiento> datosEntrenamientoArrayList;

    RecyclerView recyclerView;
    ImageView logo;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Agregar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Agregar.
     */
    // TODO: Rename and change types and number of parameters
    public static Agregar newInstance(String param1, String param2) {
        Agregar fragment = new Agregar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);



        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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