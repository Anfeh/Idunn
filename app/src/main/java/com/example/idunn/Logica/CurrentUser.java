package com.example.idunn.Logica;

import com.example.idunn.Datos.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class CurrentUser {

    private DatabaseReference mDatabase;

    public CurrentUser(DatabaseReference database) {
        mDatabase = database;
    }

    public User getCurrentUser(String uid, final GetUserCallback callback) {
        DatabaseReference userRef = mDatabase.child("users").child(uid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
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

        return null;
    }

    public interface GetUserCallback {
        void onCallback(User user);
    }
}
