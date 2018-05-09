package com.example.admin.liftapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.admin.liftapp.Controller.LoginFragment;
import com.example.admin.liftapp.Controller.UserDetailFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnFragmentLoginInteractionListener,UserDetailFragment.OnFragmentUserInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showLoginFragment();
    }

    public void showLoginFragment() {
        getSupportFragmentManager()
                .beginTransaction()
               .replace(R.id.container, LoginFragment.newInstance())
                .commit();
    }


    public void showUserFragment() {
        Log.d("TAG","Got to showUserFragment");
        getSupportFragmentManager().beginTransaction().replace(R.id.container, UserDetailFragment.newInstance()).commit();

    }

    @Override
    public void showaTraineraListFragment() {
        Log.d("TAG","Got to showTrainerFragment");
    }

    @Override
    public void showaRoutineFragment(String albumId) {
        Log.d("TAG","Got to showRoutineFragment");
    }
}
