package com.example.admin.liftapp.Model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Comment;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by menachi on 27/12/2017.
 */

public class UserFirebase {

    public interface Callback<T> {
        void onComplete(T data);
    }

    public static void getAllUsersAndObserve(final Callback<List<User>> callback) {
        Log.d("TAG", "getAllUsersAndObserve");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        ValueEventListener listener = myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> list = new LinkedList<User>();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    User user = snap.getValue(User.class);
                    list.add(user);
                }
                callback.onComplete(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onComplete(null);
            }
        });
    }


    /*
    public static void addUser(User user){
        Log.d("TAG", "add user to firebase");
        HashMap<String, Object> json = user.toJson();
        json.put("lastUpdated", ServerValue.TIMESTAMP);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        myRef.child(user.userName).setValue(json);
    }
    */


    public interface OnCreationUser {
        void onCompletion(boolean success);
    }


    public static void addUser(User user, final OnCreationUser listener) {
        Log.d("TAG", "add user to firebase");
        HashMap<String, Object> json = user.toJson();
        json.put("lastUpdated", ServerValue.TIMESTAMP);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        DatabaseReference ref = myRef.child(user.userName);
        ref.setValue(json, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.e("TAG", "Error: User could not be saved "
                            + databaseError.getMessage());
                    listener.onCompletion(false);
                } else {
                    Log.e("TAG", "Success : User saved successfully.");
                    listener.onCompletion(true);
                }
            }
        });

    }
}
