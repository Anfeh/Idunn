package com.example.idunn.Usuario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.idunn.DatosEntrenamiento;
import com.example.idunn.R;
import com.example.idunn.activity_rutina_empezada;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class activity_detailed_train extends AppCompatActivity {
    /* Variables usadas */

    private TextView tituloEntrenamiento;
    private LinearLayout additionalTextContainer;
    private Intent intent, intentWithData, intentToRutinaEmpezada;
    private DatosEntrenamiento datosEntrenamiento;
    private String[] seriesArray;
    private List<String> series;
    private View additionalTextView, seriesLinearLayout;
    private TextView textView, numeroTextView, empezarEntrenamientoTextView;
    /* -------------------------------------------------------------- */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_train);


        // Referenciamos a los elementos del xml.
        tituloEntrenamiento = findViewById(R.id.tituloEntrenamiento);
        additionalTextContainer = findViewById(R.id.additional_text_container);


        // Recogemos los datos que nos han pasado desde el intent anterior.
        intent = getIntent();
        datosEntrenamiento = (DatosEntrenamiento) intent.getSerializableExtra("clicked_item");
        seriesArray = intent.getStringArrayExtra("series");
        series = Arrays.asList(seriesArray);


        // Si los datos no son nulos mostramos el entrenamiento
        if (datosEntrenamiento != null) {


            // Ponemos el titulo del entrenamiento
            tituloEntrenamiento.setText(datosEntrenamiento.getNombreRutina());


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


        // Obtenemos la referencia del xml
        empezarEntrenamientoTextView = findViewById(R.id.empezarEntrenamiento);

        // Si lo pulsamos ejecutara el método onEmpezarEntrenamientoClick
        empezarEntrenamientoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmpezarEntrenamientoClick(v);
            }
        });
    }
    public void onEmpezarEntrenamientoClick(View view) {

        // Creamos el intent para pasar a activity_rutina_empezada
        intentToRutinaEmpezada = new Intent(this, activity_rutina_empezada.class);


        // Recogemos el valor del intent que nos han pasado anteriormente
        intentWithData = getIntent();
        datosEntrenamiento = (DatosEntrenamiento) intentWithData.getSerializableExtra("clicked_item");
        seriesArray = intentWithData.getStringArrayExtra("series");

        // Y lo pasamos a la siguiente activity a través de los intents
        intentToRutinaEmpezada.putExtra("datosEntrenamiento", datosEntrenamiento);
        intentToRutinaEmpezada.putExtra("series", seriesArray);

        // Empezamos dicha activity
        startActivity(intentToRutinaEmpezada);
    }


}
