package com.example.idunn;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class DatosEntrenamiento implements Serializable {


    private String nombreRutina;
    private List<String> nombreEntrenamiento;
    private List<String> series;

    public DatosEntrenamiento(String nombreRutina, List<String> nombreEntrenamiento) {
        this.nombreRutina = nombreRutina;
        this.nombreEntrenamiento = nombreEntrenamiento;
    }

    public DatosEntrenamiento(String nombreRutina, List<String> nombreEntrenamiento, List<String> series) {
        this.nombreRutina = nombreRutina;
        this.nombreEntrenamiento = nombreEntrenamiento;
        this.series = series;
    }

    public String getNombreRutina() {
        return nombreRutina;
    }

    public List<String> getNombreEntrenamiento() {
        return nombreEntrenamiento;
    }

    public List<String> getSeries() {
        return series;
    }
}
