package com.example.idunn.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.idunn.Datos.Series;
import com.example.idunn.R;

import java.util.List;

public class SeriesHistoryAdapter extends RecyclerView.Adapter<SeriesHistoryAdapter.SeriesViewHolder> {

    private List<Series> seriesList;
    private Series serie;

    public SeriesHistoryAdapter(List<Series> seriesList) {
        this.seriesList = seriesList;
    }

    @NonNull
    @Override
    public SeriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.series_item, parent, false);
        return new SeriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesViewHolder holder, int position) {

        try {
            serie = seriesList.get(position);
            holder.numero.setText(String.valueOf(serie.getSerie()));
            holder.inputRepeticiones.setText(String.valueOf(serie.getRepetitions()));
            holder.inputPesos.setText(String.valueOf(serie.getWeight()));
        }catch (Exception e){
            System.err.println("Error al settear textos");
        }
    }

    @Override
    public int getItemCount() {
        return seriesList.size();
    }

    public static class SeriesViewHolder extends RecyclerView.ViewHolder {
        TextView numero;
        EditText inputRepeticiones;
        EditText inputPesos;

        public SeriesViewHolder(@NonNull View itemView) {
            super(itemView);
            numero = itemView.findViewById(R.id.numero);
            inputRepeticiones = itemView.findViewById(R.id.inputRepeticiones);
            inputPesos = itemView.findViewById(R.id.inputPesos);
        }
    }
}
