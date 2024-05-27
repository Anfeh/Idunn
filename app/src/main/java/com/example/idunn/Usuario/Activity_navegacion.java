package com.example.idunn.Usuario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.idunn.Fragments.Agregar;
import com.example.idunn.Fragments.Historial;
import com.example.idunn.Fragments.Perfil;
import com.example.idunn.R;
import com.example.idunn.databinding.ActivityNavegacionBinding;

public class Activity_navegacion extends AppCompatActivity {

    ActivityNavegacionBinding binding;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNavegacionBinding.inflate(getLayoutInflater());
        binding.bottomNavigationView.setSelectedItemId(R.id.agregar);
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
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }





}