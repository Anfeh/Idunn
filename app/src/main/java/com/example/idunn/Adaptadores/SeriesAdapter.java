package com.example.idunn.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.idunn.Datos.Series;
import com.example.idunn.R;

import java.util.ArrayList;
import java.util.List;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder> {

    private List<Series> seriesList;
    private Context mContext;
    private RecyclerView recyclerView;
    private List<Series> enteredData;
    private Series series;
    private SeriesViewHolder holder;
    public SeriesAdapter(List<Series> seriesList, Context mContext) {
        this.seriesList = seriesList;
        this.mContext = mContext;
        this.recyclerView = recyclerView;
    }

    public SeriesAdapter(List<Series> seriesList, Context mContext, RecyclerView recyclerView) {
        this.seriesList = seriesList;
        this.mContext = mContext;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public SeriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.series_item, parent, false);
        return new SeriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesViewHolder holder, int position) {
        series = seriesList.get(position);
        holder.bind(series);
    }

    @Override
    public int getItemCount() {
        return seriesList.size();
    }
    public List<Series> getEnteredData() {
        try {
            enteredData = new ArrayList<>();
            for (int i = 0; i < seriesList.size(); i++) {
                series = seriesList.get(i);
                holder = (SeriesViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
                if (holder!= null) {
                    series.setSerie(Integer.parseInt(holder.serieTextView.getText().toString()));
                    series.setRepetitions(Integer.parseInt(holder.repetitionsEditText.getText().toString()));
                    series.setWeight(Integer.parseInt(holder.weightEditText.getText().toString()));
                    enteredData.add(series);
                }
            }
            return enteredData;
        }catch (Exception e){
            System.err.println("Error al devolver datos");
        }finally {
            return enteredData;
        }
    }


    public void addSeries(Series series) {
        seriesList.add(series);
        notifyDataSetChanged();
    }

    public class SeriesViewHolder extends RecyclerView.ViewHolder {

        private TextView serieTextView;
        private EditText repetitionsEditText;
        private EditText weightEditText;

        public SeriesViewHolder(@NonNull View itemView) {
            super(itemView);
            serieTextView = itemView.findViewById(R.id.numero);
            repetitionsEditText = itemView.findViewById(R.id.inputRepeticiones);
            weightEditText = itemView.findViewById(R.id.inputPesos);
        }

        public void bind(Series series) {
            serieTextView.setText(String.valueOf(series.getSerie()));
            repetitionsEditText.setText(String.valueOf(series.getRepetitions()));
            weightEditText.setText(String.valueOf(series.getWeight()));

        }
    }
}
