package com.example.idunn.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.idunn.R;
import com.example.idunn.activity_ejercicios;

import java.util.List;

public class AdapterExercise extends RecyclerView.Adapter<AdapterExercise.ViewHolder> {
    private List<String> exerciseNames;

    public AdapterExercise(List<String> exerciseNames) {
        this.exerciseNames = exerciseNames;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String exerciseName = exerciseNames.get(position);
        holder.textViewEjercicio.setText(exerciseName);
    }

    @Override
    public int getItemCount() {
        return exerciseNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewEjercicio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewEjercicio = itemView.findViewById(R.id.nombreEjercicio);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    ((activity_ejercicios) itemView.getContext()).onItemClick(position);
                }
            });
        }
    }
}