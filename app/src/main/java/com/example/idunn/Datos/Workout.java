package com.example.idunn.Datos;

import java.util.List;

public class Workout {
    private String name;
    private String date;
    private List<Exercises> exercises;

    public Workout(String name, String date, List<Exercises> exercises) {
        this.name = name;
        this.date = date;
        this.exercises = exercises;
    }
    public Workout(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Exercises> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercises> exercises) {
        this.exercises = exercises;
    }
}
