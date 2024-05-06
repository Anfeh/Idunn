package com.example.idunn.Datos;

public class Series {
    private int serie, repetitions, weight;

    public Series(int serie, int repetitions, int weight) {
        this.serie = serie;
        this.repetitions = repetitions;
        this.weight = weight;
    }
    public Series(){}
    public int getSerie() {
        return serie;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public void setSerie(int serie) {
        this.serie = serie;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
