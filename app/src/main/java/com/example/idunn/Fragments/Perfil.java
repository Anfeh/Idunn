package com.example.idunn.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.idunn.Datos.User;
import com.example.idunn.Logica.CurrentUser;
import com.example.idunn.R;
import com.example.idunn.Usuario.Activity_login;
import com.example.idunn.Usuario.Activity_modificar_medidas;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Perfil extends Fragment {

    private CurrentUser mCurrentUser;
    private View view, popupView;
    private Button medidasButton;
    private TextView nameTextView, emailTextView, closeTextView, textViewSobreMi, logOutTextView;
    private String uid;
    private Intent intent;
    private Animation animEnter;
    private int width, height;
    private boolean focusable;
    private LayoutInflater inflater;

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

        try {
            medidasButton = view.findViewById(R.id.medidasButton);
            nameTextView = view.findViewById(R.id.nameTextView);
            emailTextView = view.findViewById(R.id.emailTextView);
            logOutTextView = view.findViewById(R.id.logOutTextView);


            mCurrentUser = new CurrentUser(FirebaseDatabase.getInstance().getReference());
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
                    intent = new Intent(getActivity(), Activity_modificar_medidas.class);
                    startActivity(intent);
                }
            });
            logOutTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();

                    intent = new Intent(getActivity(), Activity_login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
            textViewSobreMi = view.findViewById(R.id.textViewSobreMi);

            textViewSobreMi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopup();
                }
            });
        }catch (Exception e){
            System.err.println("Error al intentar cambiar de activity");
        }

        return view;
    }

    private void showPopup() {
        try {
            inflater = LayoutInflater.from(getContext());
            popupView = inflater.inflate(R.layout.popup_sobremi, null);

            width = ViewGroup.LayoutParams.WRAP_CONTENT;
            height = ViewGroup.LayoutParams.WRAP_CONTENT;
            focusable = true;
            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

            animEnter = AnimationUtils.loadAnimation(getContext(), R.anim.popup_aparecer);
            popupView.startAnimation(animEnter);

            popupWindow.showAtLocation(textViewSobreMi, Gravity.CENTER, 0, 0);

            closeTextView = popupView.findViewById(R.id.closeTextView);
            closeTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation animExit = AnimationUtils.loadAnimation(getContext(), R.anim.popup_desaparecer);
                    popupView.startAnimation(animExit);
                    popupWindow.dismiss();
                }
            });
        }catch (Exception e){
            System.err.println("Error al intentar cargar el popup");
        }
    }

}