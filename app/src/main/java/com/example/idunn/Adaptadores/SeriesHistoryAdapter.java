package com.example.idunn.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.idunn.R;

import java.util.List;

public class SeriesHistoryAdapter extends RecyclerView.Adapter<SeriesHistoryAdapter.SeriesViewHolder> {
    private final List<String> nombres;
    private final List<String> series;
    private final List<String> repeticiones;
    private final List<String> pesos;

    public SeriesHistoryAdapter(List<String> nombres, List<String> series, List<String> repeticiones, List<String> pesos) {
        this.nombres = nombres;
        this.series = series;
        this.repeticiones = repeticiones;
        this.pesos = pesos;
    }

    @Override
    public SeriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.series_item, parent, false);
        return new SeriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SeriesViewHolder holder, int position) {
        holder.bind(nombres.get(position), series.get(position), repeticiones.get(position), pesos.get(position));
    }

    @Override
    public int getItemCount() {
        return nombres.size();
    }

    public static class SeriesViewHolder extends RecyclerView.ViewHolder {
        TextView nombreEjercicio;
        TextView numeroSerie;
        TextView repeticionesSerie;
        TextView pesoSerie;

        public SeriesViewHolder(View itemView) {
            super(itemView);
            numeroSerie = itemView.findViewById(R.id.numero);
            repeticionesSerie = itemView.findViewById(R.id.inputRepeticiones);
            pesoSerie = itemView.findViewById(R.id.inputPesos);
        }

        public void bind(String nombre, String serie, String repeticiones, String peso) {
            nombreEjercicio.setText(nombre);
            numeroSerie.setText(serie);
            repeticionesSerie.setText(repeticiones);
            pesoSerie.setText(peso);
        }
    }
}
