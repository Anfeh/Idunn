package com.example.idunn.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.idunn.R;

import java.util.List;

public class AdapterExercise extends RecyclerView.Adapter<AdapterExercise.ExerciseViewHolder> {
    private List<String> exercises;
    private OnItemClickListener listener;
    private  String exercise;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public AdapterExercise(List<String> exercises, OnItemClickListener listener) {
        this.exercises = exercises;
        this.listener = listener;
    }
    public AdapterExercise(List<String> exercises) {
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        exercise = exercises.get(position);
        holder.bind(exercise, listener);
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewExercise;
        private int position;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewExercise = itemView.findViewById(R.id.nombreEjercicio);
        }

        public void bind(final String exercise, final OnItemClickListener listener) {
            try {
                textViewExercise.setText(exercise);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                listener.onItemClick(position);
                            }
                        }
                    }
                });
            }catch (Exception e){
                System.err.println("Error al intentar hacer click en el item");
                System.err.println("Error al intentar hacer click en el item");
            }
        }
    }
}
