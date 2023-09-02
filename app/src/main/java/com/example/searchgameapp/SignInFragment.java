package com.example.searchgameapp;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.searchgameapp.Classes.Profile;
import com.example.searchgameapp.Interfaces.SendDataInterface;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class SignInFragment extends Fragment {

    private Button signInBtn;
    private Button signUpBtn;
    private EditText userNameEt;
    private EditText passwordEt;
    private Activity mContext;
    private static SignInFragment fragment;
    SendDataInterface sendDataInterface;

    private FirebaseAuth auth;

    public SignInFragment() {

    }


    public static SignInFragment newInstance() {
        return new SignInFragment();
    }
    public static SignInFragment getInstance(){
        if(fragment == null)
            fragment = newInstance();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        try {
            sendDataInterface = (SendDataInterface) mContext;
        } catch (RuntimeException e) {
            throw new RuntimeException(mContext + " Must implement a method");
        }
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (sendDataInterface.getLogedUser() != null) {
            FragmentTransaction fr = getParentFragmentManager().beginTransaction();
            fr.replace(R.id.fragmentContainerView, ProfilePageFragment.getInstance(sendDataInterface.getLogedUser()));
            fr.commit();
            return inflater.inflate(R.layout.fragment_profile_page, container, false);
        }
        else {
            View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
            signInBtn = view.findViewById(R.id.sign_in_button);
            signUpBtn = view.findViewById(R.id.sign_up_button);
            userNameEt = view.findViewById(R.id.userNameInput);
            passwordEt = view.findViewById(R.id.passwordInput);

            signInBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (userNameEt.getText().equals("") || passwordEt.getText().equals("")) {
                        Toast.makeText(view.getContext(), "Please enter your User Name and Password", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Profile p = checkForUser(userNameEt.getText().toString(), passwordEt.getText().toString());
                    if (p == null)
                        Toast.makeText(view.getContext(), "User Name or Password are incorrect, try again...", Toast.LENGTH_LONG).show();
                    else {

                        auth.signInWithEmailAndPassword(p.getEmail(), p.getPassword());
                        sendDataInterface.sendNewLogedUser(p);
                        FragmentTransaction fr = getParentFragmentManager().beginTransaction();
                        fr.replace(R.id.fragmentContainerView, ProfilePageFragment.getInstance(p));
                        fr.commit();
                    }
                }
            });
            signUpBtn.setOnClickListener(view1 -> {
                FragmentTransaction fr = getParentFragmentManager().beginTransaction();
                fr.replace(R.id.fragmentContainerView, new SignUpFragment());
                fr.commit();
            });
            return view;
        }
    }

    public  Profile checkForUser(String userName, String pass) {
        ArrayList<Profile> allProfiles = sendDataInterface.getAllUsers();
        for(Profile profile : allProfiles) {
            if (profile.getUserName().equals(userName) && profile.getPassword().equals(pass))
                return profile;
        }
            return null;
    }
}