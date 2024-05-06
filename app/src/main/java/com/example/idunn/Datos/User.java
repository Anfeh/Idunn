package com.example.idunn.Datos;

import static java.time.MonthDay.now;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User {
    private String id;
    private String username;
    private String email;
    private String password;
    private Measurement measurements;
    private List<Workout> workouts;
    private List<Workout_timeline> workouts_timeline;

    public User() {
    }

    public User(String id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.measurements = new Measurement(0,0,0,0,0,0,0,0);
        this.workouts = new ArrayList<>();
        this.workouts.add(new Workout("Jalon", "2024-2-1", Arrays.asList(new Exercises("Jalon", Arrays.asList(new Series(1,2,25))))));
        this.workouts.add(new Workout("Pecho", "2024-2-2", Arrays.asList(new Exercises("Jalon de Pecho", Arrays.asList(new Series(1,2,25), new Series(2,3,25))),new Exercises("Aperturas", Arrays.asList(new Series(1,2,25), new Series(2,3,25))))));
        this.workouts_timeline = new ArrayList<>();
        this.workouts_timeline.add(new Workout_timeline("Jalon", "2024-2-1", Arrays.asList(new Exercises("Jalon", Arrays.asList(new Series(1,2,25))))));

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Measurement getMeasurements() {
        return measurements;
    }

    public void setMeasurements(Measurement measurements) {
        this.measurements = measurements;
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
    }

    public String getUsername() {
        return username;
    }

    public List<Workout_timeline> getWorkouts_timeline() {
        return workouts_timeline;
    }

    public void setWorkouts_timeline(List<Workout_timeline> workouts_timeline) {
        this.workouts_timeline = workouts_timeline;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
