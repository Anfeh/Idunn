package com.example.idunn;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.idunn.Datos.Series;

import java.util.ArrayList;
import java.util.List;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder> {

    private static List<Series> seriesList;

    public SeriesAdapter(List<Series> series) {
        this.seriesList = series;
    }

    @NonNull
    @Override
    public SeriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.series_item, parent, false);
        return new SeriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesViewHolder holder, int position) {
        Series seriesItem = seriesList.get(position);
        holder.bind(seriesItem);
    }

    @Override
    public int getItemCount() {
        return seriesList.size();
    }

    public List<Series> getSeriesList() {
        return seriesList;
    }

    public static class SeriesViewHolder extends RecyclerView.ViewHolder {
        public TextView numero;
        public EditText inputRepeticiones;
        public EditText inputPesos;

        public SeriesViewHolder(@NonNull View itemView) {
            super(itemView);
            numero = itemView.findViewById(R.id.numero);
            inputRepeticiones = itemView.findViewById(R.id.inputRepeticiones);
            inputPesos = itemView.findViewById(R.id.inputPesos);
        }

        public void bind(Series seriesItem) {
            numero.setText(String.valueOf(seriesItem.getSerie()));
            inputRepeticiones.setText(String.valueOf(seriesItem.getRepetitions()));
            inputPesos.setText(String.valueOf(seriesItem.getWeight()));
            Series newSeriesItem = new Series(seriesItem.getSerie(), Integer.parseInt(inputRepeticiones.getText().toString()), Integer.parseInt(inputPesos.getText().toString()));
            seriesList.set(getAdapterPosition(), newSeriesItem);
        }
    }
}
