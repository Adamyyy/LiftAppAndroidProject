package com.example.admin.liftapp.Controller;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.admin.liftapp.Model.Authentication;
import com.example.admin.liftapp.R;


public class UserDetailFragment extends Fragment {
    ProgressBar progressBar;
    private OnFragmentUserInteractionListener mListener;
    private Button buttonCancel;
    private Button buttonSave;
    private EditText editTextEmail;
    private EditText editTextPassword;

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


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_detail, container, false);
        progressBar = view.findViewById(R.id.userp_progressbar);

        return view;
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

    }


}
