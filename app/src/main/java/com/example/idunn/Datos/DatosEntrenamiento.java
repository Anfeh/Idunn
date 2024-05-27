package com.example.idunn.Datos;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class DatosEntrenamiento implements Serializable {
    private String name;
    private String date;
    private String cronometro;
    private List<String> exercises;
    private List<String> series;



    public DatosEntrenamiento(String name, List<String> exercises, List<String> series) {
        this.name = name;
        this.exercises = exercises;
        this.series = series;
    }
    public DatosEntrenamiento(String fecha,String cronometro,String name, List<String> exercises, List<String> series) {
        this.date=fecha;
        this.cronometro=cronometro;
        this.name = name;
        this.exercises = exercises;
        this.series = series;
    }

    public String getNombreRutina() {
        return name;
    }

    public List<String> getNombreEntrenamiento() {
        return exercises;
    }

    public List<String> getSeries() {
        return series;
    }

    public void setNombreRutina(String name) {
        this.name = name;
    }

    public void setNombreEntrenamiento(List<String> exercises) {
        this.exercises = exercises;
    }

    public void setSeries(List<String> series) {
        this.series = series;
    }

    public String getFecha() {
        return date;
    }

    public void setFecha(String date) {
        this.date = date;
    }

    public String getCronometro() {
        return cronometro;
    }

    public void setCronometro(String cronometro) {
        this.cronometro = cronometro;
    }
}
