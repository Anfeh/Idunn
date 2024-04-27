package com.example.idunn;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder> {

    private static List<String> series;

    public SeriesAdapter(List<String> series) {
        this.series = series;
    }

    @NonNull
    @Override
    public SeriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.series_item, parent, false);
        return new SeriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesViewHolder holder, int position) {
        String serie = series.get(position);
        holder.bind(serie);
    }

    @Override
    public int getItemCount() {
        return series.size();
    }

    public static class SeriesViewHolder extends RecyclerView.ViewHolder {
        private TextView numero;
        private EditText inputRepeticiones;
        private EditText inputPesos;

        public SeriesViewHolder(@NonNull View itemView) {
            super(itemView);
            numero = itemView.findViewById(R.id.numero);
            inputRepeticiones = itemView.findViewById(R.id.inputRepeticiones);
            inputPesos = itemView.findViewById(R.id.inputPesos);
        }

        public void bind(String serie) {
            // Set the text of the numero TextView to the current series number
            numero.setText(String.valueOf(series.indexOf(serie) + 1));
        }
    }
}
