package com.example.idunn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.idunn.databinding.ActivityAgregarBinding;
import com.example.idunn.databinding.FragmentAgregarBinding;

public class activity_agregar extends AppCompatActivity {

    ActivityAgregarBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAgregarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        cambiarFragmento(new Agregar());


        binding.bottomNavigationView.setOnItemSelectedListener(item ->{
            if(item.getItemId() == R.id.historial){
                cambiarFragmento(new Historial());
            } else if (item.getItemId() == R.id.agregar) {
                cambiarFragmento(new Agregar());
            } else if (item.getItemId() == R.id.perfil) {
                cambiarFragmento(new Perfil());
            }
            return true;
        });
    }

    private void cambiarFragmento(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }





}