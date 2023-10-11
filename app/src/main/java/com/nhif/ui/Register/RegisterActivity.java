package com.nhif.ui.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhif.R;
import com.nhif.database.entities.User;
import com.nhif.ui.Login.LoginActivity;
import com.nhif.utils.DatePickerUtils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, idEditText, emailEditText,
            phoneEditText, dateOfBirthEditText, passwordEditText, confirmPasswordEditText;
    private Spinner genderSpinner;
    private Button registerButton, loginButton;
    private DatePickerUtils dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               goToLogin();
            }
        });

        EditText dateEdit=(EditText) findViewById(R.id.dateOfBirthEditText);
        this.dob=new DatePickerUtils(dateEdit);

        handleRegister();


    }

    private void goToLogin() {
        Intent intent=new Intent(getBaseContext(), LoginActivity.class);

        startActivity(intent);
    }

    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final DatabaseReference usersRef = database.getReference("users");


    private void handleRegister() {

        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        idEditText = findViewById(R.id.idEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        dateOfBirthEditText = findViewById(R.id.dateOfBirthEditText);
        genderSpinner = findViewById(R.id.genderEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        registerButton = findViewById(R.id.registerButton);
        loginButton = findViewById(R.id.loginButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String id = idEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String dateOfBirth = dateOfBirthEditText.getText().toString();
                String gender = genderSpinner.getSelectedItem().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();



                if(firstName.isEmpty()) {
                    firstNameEditText.setError("FirstName cannot be Empty");
                    Toast.makeText(getBaseContext(), "first name is empty", Toast.LENGTH_SHORT).show();

                    v.setEnabled(true);
                    return;
                }

                if(email.isEmpty()) {
                    passwordEditText.setError("email cannot be Empty");
                    Toast.makeText(getBaseContext(), "email is empty", Toast.LENGTH_SHORT).show();

                    v.setEnabled(true);
                    return;
                }
                else if(!isValidEmail(email))
                {
                    passwordEditText.setError("email is invalid");
                    Toast.makeText(getBaseContext(), "invalid email format", Toast.LENGTH_SHORT).show();

                    v.setEnabled(true);
                    return;
                }

                if(password.isEmpty()) {
                    passwordEditText.setError("password cannot be Empty");
                    Toast.makeText(getBaseContext(), "password is empty", Toast.LENGTH_SHORT).show();

                    v.setEnabled(true);
                    return;
                }

                if(!password.equals(confirmPassword)) {
                    confirmPasswordEditText.setError("passwords dont match");
                    Toast.makeText(getBaseContext(), "passwords dont match", Toast.LENGTH_SHORT).show();

                    v.setEnabled(true);
                    return;
                }

                //save the user
                usersRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            //System.out.println("Email already exists. User not saved.");
                            passwordEditText.setError("Email already Registered");
                            Toast.makeText(getBaseContext(), "Email already Registered", Toast.LENGTH_SHORT).show();

                            v.setEnabled(true);
                            return;

                        } else {
                            // Email doesn't exist, save the user
                            User user=new User();
                            user.setFirstName(firstName);
                            user.setEmail(email);
                            user.setId(id);
                            user.setLastName(lastName);
                            user.setPhone(phone);
                            user.setPassword(new BCryptPasswordEncoder().encode(password));
                            user.setDateOfBirth(dob.getMinDate().getTime());
                            user.setGender(genderSpinner.getSelectedItem().toString());

                            usersRef.push().setValue(user);

                            Toast.makeText(getBaseContext(), "Registered Successfully. Go To Login", Toast.LENGTH_SHORT).show();

                            goToLogin();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
//                        System.out.println("Error checking email existence: " + databaseError.getMessage());
                        databaseError.toException().printStackTrace();
                        Toast.makeText(RegisterActivity.this, "An Error occurred "+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                        v.setEnabled(true);
                        return;
                    }
                });




            }
        });

    }



    public static boolean isValidEmail(String email) {
        // Define the email regex pattern
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Create a Pattern object
        Pattern pattern = Pattern.compile(emailRegex);

        // Create matcher object
        Matcher matcher = pattern.matcher(email);

        // Return true if the email matches the regex, otherwise false
        return matcher.matches();
    }
}