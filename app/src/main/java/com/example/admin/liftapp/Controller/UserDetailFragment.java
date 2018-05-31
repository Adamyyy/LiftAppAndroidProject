package com.example.admin.liftapp.Controller;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.admin.liftapp.Model.Authentication;
import com.example.admin.liftapp.Model.Model;
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
    private EditText height;
    private EditText weight;
    private EditText birth;
    private EditText claim;
    private ImageView userImage;
    int existingUser = 0;


    Bitmap imageBitmap;
    private String imgUrl;
    private static int Load_Image_results = 1;

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

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_detail, container, false);
        progressBar = view.findViewById(R.id.userp_progressbar);
        email = Authentication.getUserEmail();
        userName = view.findViewById(R.id.user_userName_et);
        height = view.findViewById(R.id.user_height_et);
        weight = view.findViewById(R.id.user_weight_et);
        birth = view.findViewById(R.id.newstudent_bdate_et);
        claim = view.findViewById(R.id.user_claimToFame_et);
        userImage = view.findViewById(R.id.user_avatar_img);
        view.findViewById(R.id.newstudent_Image_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG","Button pressed");
                dispatchTakePictureIntent();


            }
        });



             view.findViewById(R.id.newstudent_save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String text= userName.getText().toString();
                if (text.equals("")) {
                    Toast.makeText(MyApplication.getMyContext(), "Invalid Username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (existingUser == 0) {
                    for (User user : userList) {
                        if (text.equals(user.userName)) {
                            Toast.makeText(MyApplication.getMyContext(), "UserName Already Exists", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }

                progressBar.setVisibility(View.VISIBLE);
                User toAdd = new User();
                toAdd.setUserName(text);
                toAdd.setEmail(email);
                toAdd.setBirhday(birth.getText().toString());
                toAdd.setHeight(height.getText().toString());
                toAdd.setWeight(weight.getText().toString());
                toAdd.setClaim(claim.getText().toString());
                if (imgUrl != null) {
                    toAdd.setImageUrl(imgUrl);
                }

                Model.instance().addUser(toAdd, new Model.OnCreation() {
                    @Override
                    public void onCompletion(boolean success) {

                        if (success == true){
                        Log.d("TAG","created user");
                        Toast.makeText(getActivity(), "User Details Updated!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }

                        else {
                            Log.d("TAG","failed to create user");
                            Toast.makeText(getActivity(), "Failed to create user!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        view.findViewById(R.id.newstudent_cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                mListener.showaTraineraListFragment(); //Adam note after successfully created user we tell the listener (activity) to change fragment to user Fragment
                progressBar.setVisibility(View.GONE);

            }
        });


        view.findViewById(R.id.user_avatar_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        return view;
    }



    static final int REQUEST_IMAGE_CAPTURE = 1;
    final static int RESAULT_SUCCESS = -1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESAULT_SUCCESS) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            userImage.setImageBitmap(imageBitmap);
            if (imageBitmap != null) {
                Model.instance().saveImage(imageBitmap, email, new Model.SaveImageListener() {
                    @Override
                    public void complete(String url) {
                        imgUrl = url;
                    }

                    @Override
                    public void fail() {
                        Toast.makeText(MyApplication.getMyContext(), "Error handling image", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
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

        //Process step 1
        userViewModel.getUsersList().observe(this, new Observer<List<User>>() {



            //Adam Note when there is change to database from liveData after we started obsrving they are acsessed here
            @Override
            public void onChanged(@Nullable List<User> users) {
                userList = users;
                Log.d("TAG","Got Users");
               //   User toDisplay = new User();
                  for (User user : userList)
                  {  progressBar.setVisibility(View.VISIBLE);
                      if (user.email.equals(email)){
                          existingUser = 1;
                      userName.setText(user.userName);
                      userName.setEnabled(false);
                      height.setText(user.height);
                      weight.setText(user.weight);
                      birth.setText(user.birthday);
                      claim.setText(user.claim);
                          if (user.imageUrl !=null) {
                              if (!(user.imageUrl.equals(""))){
                              Model.instance().getImage(user.imageUrl, new Model.GetImageListener() {
                                  @Override
                                  public void onSuccess(Bitmap image) {
                                      userImage.setImageBitmap(image);

                                  }
                                  @Override
                                  public void onFail() {

                                  }
                              });
                          }
                  }
                      }
                  }
                progressBar.setVisibility(View.GONE);

            }
        });

    }


}
