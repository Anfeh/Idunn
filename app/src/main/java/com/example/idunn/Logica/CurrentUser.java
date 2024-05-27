package com.example.idunn.Logica;

import com.example.idunn.Datos.Exercises;
import com.example.idunn.Datos.User;
import com.example.idunn.Datos.Workout;
import com.example.idunn.Datos.Workout_exercises;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CurrentUser {

    private DatabaseReference mDatabase, userRef;
    private User user;

    public CurrentUser(DatabaseReference database) {
        mDatabase = database;
    }

    public User getCurrentUser(String uid, final GetUserCallback callback) {
        try {
            userRef = mDatabase.child("users").child(uid);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        user.setId(uid);
                    }
                    callback.onCallback(user);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    callback.onCallback(null);
                }
            });
        }catch (Exception e){
            System.err.println("Error al intentar obtener el id");
        }

        return null;
    }

    public interface GetUserCallback {
        void onCallback(User user);
    }

}
