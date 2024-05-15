package com.example.idunn.Datos;

import java.util.List;

public class Exercises {
    private String name;
    private List<Series> series;

    public Exercises(String name, List<Series> series) {
        this.name = name;
        this.series = series;
    }
    public Exercises(){}
    public Exercises(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }
}
