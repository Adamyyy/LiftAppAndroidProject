package com.example.admin.liftapp.Controller;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.admin.liftapp.Model.Authentication;
import com.example.admin.liftapp.Model.User;
import com.example.admin.liftapp.Model.UserViewModel;
import com.example.admin.liftapp.R;

import java.util.LinkedList;
import java.util.List;


public class UserDetailFragment extends Fragment {

    List<User> userList = new LinkedList<>();;
    private UserViewModel userViewModel; //Adam Note this is the viewmodel for the Fragment it is created in the Onattach and holds data for fragement
    public String email;


    ProgressBar progressBar;
    private OnFragmentUserInteractionListener mListener;

    private Button buttonCancel;
    private Button buttonSave;
    private EditText userName;
    private EditText editTextHeight;
    private EditText editTextWeight;
    private EditText editTextBirth;
    private EditText editTectClaim;

    public interface OnFragmentUserInteractionListener {

        void showaTraineraListFragment();
        void showaRoutineFragment(String albumId);

    }




    public static UserDetailFragment newInstance() {
        UserDetailFragment fragment = new UserDetailFragment();
        return fragment;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userName = view.findViewById(R.id.user_userName_et);
        final EditText height = view.findViewById(R.id.user_height_et);
        final EditText weight = view.findViewById(R.id.user_weight_et);
        final EditText birth = view.findViewById(R.id.newstudent_bdate_et);
        final EditText claim = view.findViewById(R.id.user_claimToFame_et);

    }

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_detail, container, false);
        progressBar = view.findViewById(R.id.userp_progressbar);
        email = Authentication.getUserEmail();
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentUserInteractionListener) {
            mListener = (OnFragmentUserInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

        //Adam Note - Here we connect fragement to its viewModel that holds the live data and that employeeListViewModel is of type EmployeeListViewModel step 1
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        //Adam Note - Here we activate the getEmployeeList function from the viewModel which returns the LiveData step 2
        userViewModel.getUsersList().observe(this, new Observer<List<User>>() {



            //Adam Note when there is change to database from liveData after we started obsrving they are acsessed here
            @Override
            public void onChanged(@Nullable List<User> users) {
                userList = users;
                Log.d("TAG","Got Users");
                User toDisplay = userList.get(0);
                userName.setText(toDisplay.userName);

               // if (adapter != null) adapter.notifyDataSetChanged();
            }
        });

    }


}
