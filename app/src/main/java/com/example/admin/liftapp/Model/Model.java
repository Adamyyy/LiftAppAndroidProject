package com.example.admin.liftapp.Model;

import android.content.SharedPreferences;

import com.example.admin.liftapp.Controller.MyApplication;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by admin on 10/05/2018.
 */

public class Model {
    private static Model instance = new Model();

    ModelFirebase modelFirebase;

    private Model() {
        this.modelFirebase  = new ModelFirebase();

    }
    public static Model instance() {
        return instance;
    }


    public void writeToSharedPreferences(String name, String key, String value) {
        SharedPreferences ref = MyApplication.getMyContext().getSharedPreferences(name,MODE_PRIVATE);
        SharedPreferences.Editor ed = ref.edit();
        ed.putString(key, value);
        ed.commit();
    }

    public interface OnCreation{
        public void onCompletion(boolean success);
    }

    public void addUser(User user, final OnCreation listener) {
        //this.databaseFirebase.addAlbum(album);
        modelFirebase.addUser(user, new ModelFirebase.OnCreation() {
            @Override
            public void onCompletion(boolean success) {
                listener.onCompletion(success);
            }
        });


    }


}
