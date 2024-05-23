package com.example.idunn.Adaptadores;

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

import com.example.idunn.DatosEntrenamiento;
import com.example.idunn.R;
import com.example.idunn.Activity_historial_detallado;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private ArrayList<DatosEntrenamiento> datosEntrenamientoArrayList;
    private Context context;
    private Intent intent;
    private DatosEntrenamiento datos;
    private List<String> exerciseNames, seriesCounts;
    private String exerciseName, totalSeries;

    public HistoryAdapter(Context context, ArrayList<DatosEntrenamiento> datosEntrenamientoArrayList) {
        this.context = context;
        this.datosEntrenamientoArrayList = datosEntrenamientoArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        datos = datosEntrenamientoArrayList.get(position);
        holder.textFecha.setText(datos.getFecha());
        holder.tituloTarjeta.setText(datos.getNombreRutina());

        holder.additionalTextContainer.removeAllViews();

        exerciseNames = datos.getNombreEntrenamiento();
        seriesCounts = datos.getSeries();

        for (int i = 0; i < exerciseNames.size(); i++) {
            exerciseName = exerciseNames.get(i);
            totalSeries = seriesCounts.get(i);

            TextView additionalTextView = new TextView(context);
            additionalTextView.setText(exerciseName + " x " + totalSeries + " series");
            additionalTextView.setTextSize(13);
            additionalTextView.setTextColor(Color.BLACK);
            additionalTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            additionalTextView.setPadding(55, 10, 0, 0);

            holder.additionalTextContainer.addView(additionalTextView);
        }

        holder.itemView.setOnClickListener(v -> {
            intent = new Intent(context, Activity_historial_detallado.class);
            intent.putExtra("clicked_item", datos);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return datosEntrenamientoArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textFecha, tituloTarjeta;
        LinearLayout additionalTextContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textFecha = itemView.findViewById(R.id.textFecha);
            tituloTarjeta = itemView.findViewById(R.id.tituloTarjeta);
            additionalTextContainer = itemView.findViewById(R.id.additional_text_container);
        }
    }
}
