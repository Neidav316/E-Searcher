package com.example.searchgameapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.searchgameapp.Classes.Profile;
import com.example.searchgameapp.Interfaces.SendDataInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class SignUpFragment extends Fragment {

    private final int MINIMUM_AGE = 8;
    private EditText userNameEt, firstNameEt,lastNameEt,emailEt,passwordEt,confirmPasswordEt,birthDateEt;
    private DatePickerDialog.OnDateSetListener setListener;
    private Activity mContext;
    private SendDataInterface sendDataInterface;
    private FirebaseAuth auth;

    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
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

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        userNameEt = view.findViewById(R.id.input_new_user_name);
        firstNameEt = view.findViewById(R.id.input_first_name);
        lastNameEt = view.findViewById(R.id.input_last_name);
        emailEt = view.findViewById(R.id.input_new_email);
        passwordEt = view.findViewById(R.id.input_new_password);
        confirmPasswordEt = view.findViewById(R.id.input_confirm_password);
        birthDateEt = view.findViewById(R.id.input_new_birth_date);
        Button confirmNewUser = view.findViewById(R.id.confirm_new_user);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR) - MINIMUM_AGE;
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        birthDateEt.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, setListener,year,month,day);
                    datePickerDialog.show();
                }
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
                month = month+1;
                String date = year+"-"+(month>9 ? month : "0"+month)+"-"+(dayOfMonth > 9 ?dayOfMonth :"0"+dayOfMonth);
                birthDateEt.setText(date);

            }
        };

        confirmNewUser.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(checkInput(view)) {
                    long birthdate = LocalDate.parse(birthDateEt.getText()).toEpochDay();
                    Profile p = new Profile(userNameEt.getText().toString(),firstNameEt.getText().toString(),lastNameEt.getText().toString(),
                            emailEt.getText().toString(),passwordEt.getText().toString(),birthdate);
                    register(p);
                    FragmentTransaction fr = getParentFragmentManager().beginTransaction();
                    fr.replace(R.id.fragmentContainerView, ProfilePageFragment.getInstance(p));
                    fr.commit();
                }
            }
        });
        return view;
    }

    private void register(Profile p) {


        auth.createUserWithEmailAndPassword(p.getEmail(),p.getPassword()).addOnCompleteListener( mContext.getMainExecutor(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    sendDataInterface.registerNewUser( p );
                    Toast.makeText(mContext, "Sign up user successful!", Toast.LENGTH_SHORT).show();

                }
                else
                    Toast.makeText(mContext, "Sign up user failed..",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public boolean checkInput(View view) {
        if (userNameEt.getText().toString().equals("") || firstNameEt.getText().toString().equals("") ||
                lastNameEt.getText().toString().equals("") || passwordEt.getText().toString().equals("") ||
                confirmPasswordEt.getText().toString().equals("") || birthDateEt.getText().toString().equals("") ||
                emailEt.getText().toString().equals("")) {
            Toast.makeText(view.getContext(), "Please enter all required information", Toast.LENGTH_LONG).show();
            return false;
        }
        if (userNameEt.getText().length() <= 5 || checkStringForName(userNameEt.getText().toString())) {
            Toast.makeText(view.getContext(), "Please enter correct user Name, with more then 5 characters", Toast.LENGTH_LONG).show();
            return false;
        }
        if (firstNameEt.getText().length() <= 1 || checkStringForName(firstNameEt.getText().toString())) {
            Toast.makeText(view.getContext(), "Please enter correct first name correctly, with more then 1 characters", Toast.LENGTH_LONG).show();
            return false;
        }
        if (lastNameEt.getText().length() <= 1 || checkStringForName(lastNameEt.getText().toString())) {
            Toast.makeText(view.getContext(), "Please enter your last name correctly, with more then 1 characters", Toast.LENGTH_LONG).show();
            return false;
        }
        if (emailEt.getText().length() <= 4 ){//|| checkEmailFormat(emailEt.getText().toString())) {
            Toast.makeText(view.getContext(), "Please enter correct email, with more then 5 characters", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!checkCorrectDate(birthDateEt.getText().toString())) {
            Toast.makeText(view.getContext(), "Date is incorrect, please enter a year with 8 year gap atleast", Toast.LENGTH_LONG).show();
            return false;
        }
        if (passwordEt.getText().length() < 4 || passwordEt.getText().length() > 12) {
            Toast.makeText(view.getContext(), "Password needs to be between 5 to 12 characters", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!confirmPasswordEt.getText().toString().equals(passwordEt.getText().toString())) {
            Toast.makeText(view.getContext(), "Passwords don't match, please enter and repeat the same password", Toast.LENGTH_LONG).show();
            return false;
        }

        if (checkForDuplicateProfile()) {
            Toast.makeText(view.getContext(), "There is already a profile with the same User name or Password, try another", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }


    public  boolean checkStringForName(String name) {
        for (int i = 0; i < name.length(); i++) {
            if ((!Character.isWhitespace(name.charAt(i)) && !Character.isLetterOrDigit(name.charAt(i)))
                    || Character.isDigit(name.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    @SuppressLint("NewApi")
    public  boolean checkCorrectDate(String stringDate) {
        LocalDate date = LocalDate.parse(stringDate);
        LocalDate now = LocalDate.now();

        if (now.getYear()-date.getYear() < MINIMUM_AGE)
            return false;

        if(date.getMonthValue()>12 || date.getMonthValue() < 1)
            return false;
        return date.getDayOfMonth() <= 31 && date.getDayOfMonth() >= 1;

    }

    public  boolean checkForDuplicateProfile() {
        ArrayList<Profile> allUsers = sendDataInterface.getAllUsers();
        for (Profile profile : allUsers){
            if(profile.getUserName().equals(userNameEt.getText().toString()) ||
               profile.getPassword().equals(passwordEt.getText().toString()))
                return true;
        }
        return false;
    }
}
