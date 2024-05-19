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
import com.example.idunn.Usuario.activity_detailed_train;
import com.example.idunn.activity_historial_detallado;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private ArrayList<DatosEntrenamiento> datosEntrenamientoArrayList;
    private Context context;

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
        DatosEntrenamiento datos = datosEntrenamientoArrayList.get(position);
        holder.textFecha.setText(datos.getFecha());
        holder.tituloTarjeta.setText(datos.getNombreRutina());

        holder.additionalTextContainer.removeAllViews();

        List<String> exerciseNames = datos.getNombreEntrenamiento();
        List<String> seriesCounts = datos.getSeries();

        for (int i = 0; i < exerciseNames.size(); i++) {
            String exerciseName = exerciseNames.get(i);
            String totalSeries = seriesCounts.get(i);

            TextView additionalTextView = new TextView(context);
            additionalTextView.setText(exerciseName + " x " + totalSeries + " series");
            additionalTextView.setTextSize(13);
            additionalTextView.setTextColor(Color.BLACK);
            additionalTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            additionalTextView.setPadding(55, 10, 0, 0);

            holder.additionalTextContainer.addView(additionalTextView);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, activity_historial_detallado.class);
                intent.putExtra("clicked_item", datos);
                intent.putExtra("series", datos.getSeries().toArray(new String[0]));
                context.startActivity(intent);
            }
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
