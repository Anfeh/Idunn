package com.example.idunn;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class DatosEntrenamiento implements Serializable {


    private String nombreRutina;
    private String fecha;
    private String cronometro;
    private List<String> nombreEntrenamiento;
    private List<String> series;



    public DatosEntrenamiento(String nombreRutina, List<String> nombreEntrenamiento, List<String> series) {
        this.nombreRutina = nombreRutina;
        this.nombreEntrenamiento = nombreEntrenamiento;
        this.series = series;
    }
    public DatosEntrenamiento(String fecha,String cronometro,String nombreRutina, List<String> nombreEntrenamiento, List<String> series) {
        this.fecha=fecha;
        this.cronometro=cronometro;
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

    public void setNombreRutina(String nombreRutina) {
        this.nombreRutina = nombreRutina;
    }

    public void setNombreEntrenamiento(List<String> nombreEntrenamiento) {
        this.nombreEntrenamiento = nombreEntrenamiento;
    }

    public void setSeries(List<String> series) {
        this.series = series;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCronometro() {
        return cronometro;
    }

    public void setCronometro(String cronometro) {
        this.cronometro = cronometro;
    }
}
