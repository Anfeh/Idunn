package com.example.idunn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.Arrays;
import java.util.List;

public class activity_historial_detallado extends AppCompatActivity {



    /* Variables usadas */

    private LinearLayout additionalTextContainer;
    private Intent intent;
    private DatosEntrenamiento datosEntrenamiento;
    private String[] seriesArray;
    private List<String> series;
    private View additionalTextView, seriesLinearLayout;
    private TextView textView, numeroTextView, textViewCronometro, tituloEntrenamiento;
    /* -------------------------------------------------------------- */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_detallado);


        // Referenciamos a los elementos del xml.
        tituloEntrenamiento = findViewById(R.id.tituloEntrenamiento);
        additionalTextContainer = findViewById(R.id.additional_text_container);
        textViewCronometro = findViewById(R.id.cronometro);


        // Recogemos los datos que nos han pasado desde el intent anterior.
        intent = getIntent();
        datosEntrenamiento = (DatosEntrenamiento) intent.getSerializableExtra("clicked_item");
        seriesArray = intent.getStringArrayExtra("series");
        series = Arrays.asList(seriesArray);


        // Si los datos no son nulos mostramos el entrenamiento
        if (datosEntrenamiento != null) {


            // Ponemos el titulo del entrenamiento
            tituloEntrenamiento.setText(datosEntrenamiento.getNombreRutina());

            textViewCronometro.setText(datosEntrenamiento.getCronometro());

            // Recorremos los entrenamientos
            for (String additionalText : datosEntrenamiento.getNombreEntrenamiento()) {

                // Inflamos el layout para cada additionalText
                additionalTextView = LayoutInflater.from(this).inflate(R.layout.additional_text_item, additionalTextContainer, false);


                // Y los ponemos como el tituloEjercicio cada uno
                textView = additionalTextView.findViewById(R.id.tituloEjercicio);
                textView.setText(additionalText);

                // Lo agregamos al layout
                additionalTextContainer.addView(additionalTextView);


                // Recorremos las series
                for (int i = 0; i < series.size(); i++) {

                    // Inflamos el layout para cada seriesLinearLayout
                    seriesLinearLayout = LayoutInflater.from(this).inflate(R.layout.series_item, additionalTextContainer, false);

                    // Obtenemos el numero
                    numeroTextView = seriesLinearLayout.findViewById(R.id.numero);

                    // Si hay 3 series, nos va a agregar las series respectivas y va aumentando el valor del textView
                    numeroTextView.setText(String.valueOf(i + 1));

                    // Lo agregamos al layout
                    additionalTextContainer.addView(seriesLinearLayout);
                }
            }
        }


    }
}