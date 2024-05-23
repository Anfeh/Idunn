package com.example.idunn.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.idunn.DatosEntrenamiento;
import com.example.idunn.R;
import com.example.idunn.Usuario.Activity_detailed_train;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.MyViewHolder> {

    private Context context;
    private ArrayList<DatosEntrenamiento> datosEntrenamientos;
    private String userId;
    private DatosEntrenamiento datosEntrenamiento;
    private FirebaseDatabase database;
    private DatabaseReference userRef;

    public Adaptador(Context context, ArrayList<DatosEntrenamiento> datosEntrenamientos, String userId) {
        this.context = context;
        this.datosEntrenamientos = datosEntrenamientos;
        this.userId=userId;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DatosEntrenamiento datosEntrenamiento = datosEntrenamientos.get(position);
        holder.tituloEjercicio.setText(datosEntrenamiento.getNombreRutina());
        holder.additionalTextContainer.removeAllViews();

        for (String additionalText : datosEntrenamiento.getNombreEntrenamiento()) {
            TextView additionalTextView = new TextView(context);
            additionalTextView.setText(additionalText + " x " + datosEntrenamiento.getSeries().size() + " series");
            additionalTextView.setTextSize(13);
            additionalTextView.setTextColor(Color.BLACK);
            additionalTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            additionalTextView.setPadding(55, 10, 0, 0);

            holder.additionalTextContainer.addView(additionalTextView);
        }
        holder.eliminarEntrenamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarEntrenamiento(position);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Activity_detailed_train.class);
                intent.putExtra("clicked_item", datosEntrenamiento);
                intent.putExtra("series", datosEntrenamiento.getSeries().toArray(new String[0]));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datosEntrenamientos.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tituloEjercicio;
        LinearLayout additionalTextContainer;
        TextView eliminarEntrenamiento;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tituloEjercicio = itemView.findViewById(R.id.tituloTarjeta);
            additionalTextContainer = itemView.findViewById(R.id.additional_text_container);
            eliminarEntrenamiento = itemView.findViewById(R.id.eliminarEntrenamiento);
        }
    }

    private void eliminarEntrenamiento(int position) {
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users").child(userId).child("workouts").child(String.valueOf(position));

        userRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                datosEntrenamientos.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, datosEntrenamientos.size());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Error al eliminar el entrenamiento", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
