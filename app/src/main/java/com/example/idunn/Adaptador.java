package com.example.idunn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adaptador extends  RecyclerView.Adapter<Adaptador.MyViewHolder>{

    Context context;
    ArrayList<DatosEntrenamiento> datosEntrenamientos;
    public Adaptador(Context context, ArrayList<DatosEntrenamiento> datosEntrenamientos){


        this.context = context;
        this.datosEntrenamientos = datosEntrenamientos;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);



        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DatosEntrenamiento datosEntrenamiento = datosEntrenamientos.get(position);

        holder.tituloEntrenamiento.setText(datosEntrenamiento.getTituloEntrenamiento());
        holder.tituloEjercicio.setText(datosEntrenamiento.getTituloEjercicios());
    }

    @Override
    public int getItemCount() {
        return datosEntrenamientos.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tituloEntrenamiento, tituloEjercicio;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tituloEjercicio = itemView.findViewById(R.id.tituloTarjeta);
            tituloEntrenamiento = itemView.findViewById(R.id.entrenamiento);
        }
    }





}