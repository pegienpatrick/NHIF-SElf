package com.nhif.ui.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.nhif.R;
import com.nhif.ui.Login.LoginActivity;
import com.nhif.utils.DatePickerUtils;

public class RegisterActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, idEditText, emailEditText,
            phoneEditText, dateOfBirthEditText, passwordEditText, confirmPasswordEditText;
    private Spinner genderSpinner;
    private Button registerButton, loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), LoginActivity.class);

                startActivity(intent);
            }
        });

        EditText dateEdit=(EditText) findViewById(R.id.dateOfBirthEditText);
        new DatePickerUtils(dateEdit);

        handleRegister();
    }

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

                if(password.isEmpty())
                    passwordEditText.setError("password cannot be Empty");


            }
        });

    }
}