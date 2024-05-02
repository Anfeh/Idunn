package com.example.idunn;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Perfil extends Fragment {


    public Perfil() {

    }

    public static Perfil newInstance(String param1, String param2) {
        Perfil fragment = new Perfil();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        Button medidasButton = view.findViewById(R.id.medidasButton);
        medidasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), activity_modificar_medidas.class);
                startActivity(intent);
            }
        });

        return view;
    }
}