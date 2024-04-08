package com.example.idunn;

public class DatosEntrenamiento {


    private String tituloEntrenamiento;
    private String tituloEjercicios;

    public DatosEntrenamiento(String tituloEntrenamiento, String tituloEjercicios) {
        this.tituloEntrenamiento = tituloEntrenamiento;
        this.tituloEjercicios = tituloEjercicios;
    }

    public String getTituloEntrenamiento() {
        return tituloEntrenamiento;
    }

    public String getTituloEjercicios() {
        return tituloEjercicios;
    }
}
