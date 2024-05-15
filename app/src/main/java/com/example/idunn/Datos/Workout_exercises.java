package com.example.idunn.Datos;

import java.util.ArrayList;
import java.util.List;

public class Workout_exercises {
    private String name;
    private List<Exercises> exercises;

    public Workout_exercises(String name, List<Exercises> exercises) {
        this.name = name;
        this.exercises = exercises;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Exercises> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercises> exercises) {
        this.exercises = exercises;
    }

}
