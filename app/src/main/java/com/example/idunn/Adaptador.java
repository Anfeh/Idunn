package com.example.idunn;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.MyViewHolder> {

    Context context;
    ArrayList<DatosEntrenamiento> datosEntrenamientos;

    public Adaptador(Context context, ArrayList<DatosEntrenamiento> datosEntrenamientos) {
        this.context = context;
        this.datosEntrenamientos = datosEntrenamientos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DatosEntrenamiento datosEntrenamiento = datosEntrenamientos.get(position);
        holder.tituloEjercicio.setText(datosEntrenamiento.getNombreRutina());
        holder.additionalTextContainer.removeAllViews();

        for (String additionalText : datosEntrenamiento.getNombreEntrenamiento()) {
            TextView additionalTextView = new TextView(context);
            additionalTextView.setText(additionalText);
            additionalTextView.setTextSize(13);
            additionalTextView.setTextColor(Color.BLACK);
            additionalTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            additionalTextView.setPadding(55, 10, 0, 0);

            holder.additionalTextContainer.addView(additionalTextView);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, activity_detailed_train.class);
                intent.putExtra("clicked_item", datosEntrenamiento);
                intent.putExtra("series", datosEntrenamiento.getSeries().toArray(new String[0]));
                context.startActivity(intent);
            }
        });;
    }

    @Override
    public int getItemCount() {
        return datosEntrenamientos.size();
    }

    public static class MyViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements View.OnClickListener {
        Context context;
        ArrayList<DatosEntrenamiento> datosEntrenamientos;
        TextView tituloEjercicio;
        LinearLayout additionalTextContainer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tituloEjercicio = itemView.findViewById(R.id.tituloTarjeta);
            additionalTextContainer = itemView.findViewById(R.id.additional_text_container);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                DatosEntrenamiento clickedItem = datosEntrenamientos.get(position);
                Intent intent = new Intent(context, activity_detailed_train.class);
                intent.putExtra("clicked_item", (CharSequence) clickedItem);
                context.startActivity(intent);
            }
        }
    }
}