package com.example.idunn.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.idunn.Datos.User;
import com.example.idunn.Logica.CurrentUser;
import com.example.idunn.R;
import com.example.idunn.Usuario.activity_login;
import com.example.idunn.Usuario.activity_modificar_medidas;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Perfil extends Fragment {

    private FirebaseAuth mAuth;
    private CurrentUser mCurrentUser;
    private View view;
    private Button medidasButton;
    private TextView nameTextView, emailTextView;
    private String uid;
    private Intent intent;
    private TextView logOutTextView;
    public Perfil() {

    }

    public static Perfil newInstance(String param1, String param2) {
        Perfil fragment = new Perfil();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_perfil, container, false);
        medidasButton = view.findViewById(R.id.medidasButton);
        mCurrentUser = new CurrentUser(FirebaseDatabase.getInstance().getReference());

        nameTextView = view.findViewById(R.id.nameTextView);
        emailTextView = view.findViewById(R.id.emailTextView);
        logOutTextView = view.findViewById(R.id.logOutTextView);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mCurrentUser.getCurrentUser(uid, new CurrentUser.GetUserCallback() {
            @Override
            public void onCallback(User user) {
                if (user != null) {
                    nameTextView.setText(user.getUsername());
                    emailTextView.setText(user.getEmail());
                }
            }
        });
        medidasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), activity_modificar_medidas.class);
                startActivity(intent);
            }
        });
        logOutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cerramos sesión del usuario
                FirebaseAuth.getInstance().signOut();

                // Redirigirimos a la pantalla de inicio de sesión
                Intent intent = new Intent(getActivity(), activity_login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish(); // Cierra la actividad actual
            }
        });

        return view;
    }
}