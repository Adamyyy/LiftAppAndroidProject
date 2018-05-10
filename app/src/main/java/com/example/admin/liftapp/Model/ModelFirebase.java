package com.example.admin.liftapp.Model;

import org.w3c.dom.Comment;

/**
 * Created by admin on 10/05/2018.
 */

public class ModelFirebase {

    public ModelFirebase() {

    }

    /*

     UserFirebase.addUser(user, UserFirebase.OnCreationComment() {
            @Override
            public void onCompletion(boolean success) {
                listener.onCompletion(success);
            }
        });
     */

    public interface OnCreation{
        public void onCompletion(boolean success);
    }

    public void addUser(User user, final OnCreation listener) {
        UserFirebase.addUser(user, new UserFirebase.OnCreationUser() {
            @Override
            public void onCompletion(boolean success) {
                listener.onCompletion(success);
            }
        });

    }
}
