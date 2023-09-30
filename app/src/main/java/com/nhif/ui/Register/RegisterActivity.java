package com.nhif.ui.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.nhif.R;
import com.nhif.ui.Login.LoginActivity;
import com.nhif.utils.DatePickerUtils;

public class RegisterActivity extends AppCompatActivity {

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
    }
}