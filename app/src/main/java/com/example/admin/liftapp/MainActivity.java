package com.example.admin.liftapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.admin.liftapp.Controller.LoginFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnFragmentLoginInteractionListener {

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

    @Override
    public void showAlbumsFragment() {
    //CommitCheck
    }
}
