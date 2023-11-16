package com.nhif.ui.session.profile;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nhif.R;
import com.nhif.database.entities.User;
import com.nhif.ui.Login.LoginActivity;
import com.nhif.ui.session.SessionActivity;
import com.nhif.utils.DatePickerUtils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ProfileFragment extends Fragment {

    private EditText firstNameEditText, lastNameEditText, idEditText, emailEditText,
            phoneEditText, dateOfBirthEditText, passwordEditText, confirmPasswordEditText;
    private Spinner genderSpinner;
    private Button updateInfoButton, changePasswordButton;
    private DatePickerUtils dob;
    private String userId;
    private User user;
    private View view;
    private String userKey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_profiles, container, false);
//        this.view=view;


        feedData();
        retrieveInitialData();

        return view;
    }


   


    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final DatabaseReference usersRef = database.getReference("users");


    private void feedData() {
        firstNameEditText = view.findViewById(R.id.firstNameEditText);
        lastNameEditText =  view.findViewById(R.id.lastNameEditText);
        idEditText =  view.findViewById(R.id.idEditText);
        emailEditText =  view.findViewById(R.id.emailEditText);
        phoneEditText =  view.findViewById(R.id.phoneEditText);
        dateOfBirthEditText =  view.findViewById(R.id.dateOfBirthEditText);
        genderSpinner =  view.findViewById(R.id.genderEditText);
        passwordEditText =  view.findViewById(R.id.passwordEditText);
        confirmPasswordEditText =  view.findViewById(R.id.confirmPasswordEditText);
        updateInfoButton =  view.findViewById(R.id.updateButton);
        changePasswordButton =  view.findViewById(R.id.changePasswordButton);

        dob=new DatePickerUtils(dateOfBirthEditText);
        emailEditText.setEnabled(false);


    }


    private void updateInfo() {
        if(user==null)
            return;

        firstNameEditText.setText(user.getFirstName());
        lastNameEditText.setText(user.getLastName());
        emailEditText.setText(user.getEmail());
        idEditText.setText(user.getId());
        phoneEditText.setText(user.getPhone());
        dob=new DatePickerUtils(dateOfBirthEditText,user.getDateOfBirth());

        if(user.getGender().equalsIgnoreCase("male"))
            genderSpinner.setSelection(1);
        else if (user.getGender().equalsIgnoreCase("female")) {
            genderSpinner.setSelection(2);
        }

        handleUpdates();
    }

    private void handleUpdates() {

        updateInfoButton.setOnClickListener((v)->{

            v.setEnabled(false);
            User user=new User();
            user.setFirstName(firstNameEditText.getText().toString());
            user.setEmail(emailEditText.getText().toString());
            user.setId(idEditText.getText().toString());
            user.setLastName(lastNameEditText.getText().toString());
            user.setPhone(phoneEditText.getText().toString());
            user.setDateOfBirth(dob.getMinDate().getTime());
            user.setGender(genderSpinner.getSelectedItem().toString());

            try {
                usersRef.child(userKey).setValue(user);

                Toast.makeText(getContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();

                v.setEnabled(true);
            }catch (Exception edd)
            {
                edd.printStackTrace();
            }

        });

        changePasswordButton.setOnClickListener((v)->{
            v.setEnabled(false);
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();


            if(password.isEmpty()) {
                passwordEditText.setError("password cannot be Empty");
                Toast.makeText(getContext(), "password is empty", Toast.LENGTH_SHORT).show();

                v.setEnabled(true);
                return;
            }

            if(!password.equals(confirmPassword)) {
                confirmPasswordEditText.setError("passwords dont match");
                Toast.makeText(getContext(), "passwords dont match", Toast.LENGTH_SHORT).show();

                v.setEnabled(true);
                return;
            }

            user.setPassword(new BCryptPasswordEncoder().encode(password));

            try {
                usersRef.child(userKey).setValue(user);
                Toast.makeText(getContext(), "Password Changed Successfully", Toast.LENGTH_SHORT).show();

                v.setEnabled(true);
            }catch (Exception es)
            {
                es.printStackTrace();
            }

        });


    }


    private void retrieveInitialData() {
        Intent intent=getActivity().getIntent();
        if(intent.hasExtra("user"))
        {
            this.userId=intent.getExtras().getString("user");
            Query queryByEmail = usersRef.orderByChild("email").equalTo(userId);
            final Handler handler = new Handler(Looper.getMainLooper());

            queryByEmail.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    handler.removeCallbacksAndMessages(null);
                    if (dataSnapshot.exists()) {
                        // User found, handle accordingly
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                           user = snapshot.getValue(User.class);
                           userKey=snapshot.getKey();
                           try {
                               updateInfo();
                           }catch (Exception eds)
                           {
                               eds.printStackTrace();
                           }


                        }
                        } else{
                            // User not found
                            Toast.makeText(getContext(), "No User Found", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        }
                    }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    handler.removeCallbacksAndMessages(null);
                    // Handle errors, if any
                    System.err.println(databaseError.getMessage());
                    Toast.makeText(getContext(), "An error Occurred "+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            handler.postDelayed(()->{
                Toast.makeText(getContext(), "Connection Error. Timeout", Toast.LENGTH_SHORT).show();
            },5000);

        }
    }




}
