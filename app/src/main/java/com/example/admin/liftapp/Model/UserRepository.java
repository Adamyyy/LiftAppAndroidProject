package com.example.admin.liftapp.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.admin.liftapp.Controller.MyApplication;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by admin on 09/05/2018.
 */

public class UserRepository {


    //Adam Note repositories are singleTon hence we use instance.
    public static final UserRepository instance = new UserRepository();


    UserRepository() {
    }

    MutableLiveData<List<User>> UserListliveData;

    public LiveData<List<User>> getUserList() {
        synchronized (this) {                                // Adam Note Synchronized means only one thread can enter function (critical section)
            if (UserListliveData == null) {             //Adam Note if the employeeListliveData isnt null it means it was already created and can be sent to ViewModel's
                UserListliveData = new MutableLiveData<List<User>>();
                UserFirebase.getAllUsersAndObserve(new UserFirebase.Callback<List<User>>() {

                    @Override
                    public void onComplete(List<User> data) {
                        if (data != null) UserListliveData.setValue(data); //Adam note = sends data to listening fragments (mostly via ViewModel)
                        Log.d("TAG", "got user data");
                    }
                });
            }
        }
        return UserListliveData;
    }



    public LiveData<List<User>> getAllUsers() {
        synchronized (this) {
            if (UserListliveData == null) {
                UserListliveData = new MutableLiveData<List<User>>();

                //1. get the last update date
                final float lastUpdateDate = MyApplication.getMyContext()
                        .getSharedPreferences("TAG", MODE_PRIVATE).getFloat("lastUpdateDate", 0);

                //2. get all users records that where updated since last update date
                UserFirebase.getAllUsersAndObserve(new UserFirebase.Callback<List<User>>() {
                    @Override
                    public void onComplete(List<User> data) {
                        if (data != null && data.size() > 0) {
                            //3. update the local DB
                            float reacentUpdate = lastUpdateDate;
                            for (User user : data) {
                                AppLocalStore.db.userDao().insertAll(user);
                                if (user.lastUpdated > reacentUpdate) {
                                    reacentUpdate = user.lastUpdated;
                                }
                                Log.d("TAG", "updating: " + user.toString());
                            }
                            SharedPreferences.Editor editor = MyApplication.getMyContext().getSharedPreferences("TAG", MODE_PRIVATE).edit();
                            editor.putFloat("lastUpdateDate", reacentUpdate);
                        }
                        //return the complete student list to the caller
                        List<User> empList = AppLocalStore.db.userDao().getAll();
                        UserListliveData.setValue(empList);

                    }
                });
            }
        }
        return UserListliveData;
    }
}
