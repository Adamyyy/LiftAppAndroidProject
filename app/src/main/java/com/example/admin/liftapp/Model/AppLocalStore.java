package com.example.admin.liftapp.Model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.example.admin.liftapp.Controller.MyApplication;

/**
 * Created by admin on 21/04/2018.
 */

@Database(entities = {User.class}, version = 1)
abstract class AppLocalStoreDb extends RoomDatabase {
    public abstract UserDao userDao();
}

public class AppLocalStore{
    static public AppLocalStoreDb db = Room.databaseBuilder(MyApplication.getMyContext(),
            AppLocalStoreDb.class,
            "database-name").build();


}