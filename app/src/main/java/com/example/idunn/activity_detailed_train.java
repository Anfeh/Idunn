package com.example.idunn;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class activity_detailed_train extends AppCompatActivity {

    private TextView tituloEntrenamiento;
    private LinearLayout additionalTextContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_train);

        tituloEntrenamiento = findViewById(R.id.tituloEntrenamiento);
        additionalTextContainer = findViewById(R.id.additional_text_container);

        Intent intent = getIntent();
        DatosEntrenamiento datosEntrenamiento = (DatosEntrenamiento) intent.getSerializableExtra("clicked_item");
        String[] seriesArray = intent.getStringArrayExtra("series");
        List<String> series = Arrays.asList(seriesArray);

        if (datosEntrenamiento != null) {
            tituloEntrenamiento.setText(datosEntrenamiento.getNombreRutina());

            for (String additionalText : datosEntrenamiento.getNombreEntrenamiento()) {
                View additionalTextView = LayoutInflater.from(this).inflate(R.layout.additional_text_item, additionalTextContainer, false);

                TextView textView = additionalTextView.findViewById(R.id.tituloEjercicio);
                textView.setText(additionalText);


                additionalTextContainer.addView(additionalTextView);
                for (int i = 0; i < series.size(); i++) {
                    View seriesLinearLayout = LayoutInflater.from(this).inflate(R.layout.series_item, additionalTextContainer, false);
                    TextView numeroTextView = seriesLinearLayout.findViewById(R.id.numero);
                    numeroTextView.setText(String.valueOf(i + 1));


                    additionalTextContainer.addView(seriesLinearLayout);
                }
            }
        }
        TextView empezarEntrenamientoTextView = findViewById(R.id.empezarEntrenamiento);
        empezarEntrenamientoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmpezarEntrenamientoClick(v);
            }
        });
    }
    public void onEmpezarEntrenamientoClick(View view) {
        Intent intentToRutinaEmpezada = new Intent(this, activity_rutina_empezada.class);

        Intent intentWithData = getIntent();
        DatosEntrenamiento datosEntrenamiento = (DatosEntrenamiento) intentWithData.getSerializableExtra("clicked_item");
        String[] seriesArray = intentWithData.getStringArrayExtra("series");

        intentToRutinaEmpezada.putExtra("datosEntrenamiento", datosEntrenamiento);
        intentToRutinaEmpezada.putExtra("series", seriesArray);

        startActivity(intentToRutinaEmpezada);

        // Change the text and color of the "Empezar entrenamiento" button
        TextView empezarEntrenamientoTextView = findViewById(R.id.empezarEntrenamiento);
        empezarEntrenamientoTextView.setText("Acabar entrenamiento");
        empezarEntrenamientoTextView.setTextColor(Color.RED);
    }


}
