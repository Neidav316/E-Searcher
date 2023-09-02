package com.example.searchgameapp;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.searchgameapp.Classes.Profile;
import com.example.searchgameapp.Interfaces.SendDataInterface;
import com.google.firebase.auth.FirebaseAuth;

public class ProfilePageFragment extends Fragment {

    private Profile profile;
    private Button signOutButton;
    private static ProfilePageFragment fragment;
    private Activity mContext;
    private FirebaseAuth auth;
    SendDataInterface sendDataInterface;

    public ProfilePageFragment(Profile p) {
        // Required empty public constructor
        profile = p;
    }

    public static ProfilePageFragment newInstance(Profile p) {
        return new ProfilePageFragment(p);
    }
    public static ProfilePageFragment getInstance(Profile p){
        if(fragment == null)
            fragment = newInstance(p);
        else
            fragment.setProfile(p);
        return fragment;
    }

    public void setProfile(Profile p) {
        this.profile = p;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_page, container, false);
        TextView welcomeUserNametxt = view.findViewById(R.id.welcome_user_txt);
        signOutButton = view.findViewById(R.id.sign_out_button);
        welcomeUserNametxt.setText("Welcome "+ profile.getUserName()+ "\ncheck out your favorites");
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                profile = null;
                sendDataInterface.sendNewLogedUser(null);
                FragmentTransaction fr = getParentFragmentManager().beginTransaction();
                fr.replace(R.id.fragmentContainerView, SignInFragment.getInstance());
                fr.commit();
            }
        });

        return view;
    }
}
