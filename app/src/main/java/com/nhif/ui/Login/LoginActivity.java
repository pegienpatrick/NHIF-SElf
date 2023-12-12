package com.nhif.ui.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nhif.R;
import com.nhif.database.entities.User;
import com.nhif.ui.Register.RegisterActivity;
import com.nhif.ui.session.SessionActivity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class LoginActivity extends AppCompatActivity {

    private EditText emailOrIdEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        findViewById(R.id.registerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), RegisterActivity.class);

                startActivity(intent);
            }
        });

        emailOrIdEditText = findViewById(R.id.emailOrIdEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);



        handleLogin();

        Boolean help=false;

//        help=true;

        if(help) {

        }
    }

    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final DatabaseReference usersRef = database.getReference("users");

    private void handleLogin() {


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailOrId = emailOrIdEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString();
                view.setEnabled(false);

                // Query users based on email
                Query queryByEmail = usersRef.orderByChild("email").equalTo(emailOrId);

                // Query users based on ID
//                Query queryById = usersRef.orderByChild("id").equalTo(emailOrId);

                // Combine the queries using OR
//                Query combinedQuery = queryByEmail.getRef().startAt(emailOrId).endAt(emailOrId + "\uf8ff")
//                        .limitToFirst(1).getRef().orderByKey().startAt(emailOrId).endAt(emailOrId + "\uf8ff");

                // Perform the combined query


                final Handler handler = new Handler(Looper.getMainLooper());
                queryByEmail.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        handler.removeCallbacksAndMessages(null);
                        if (dataSnapshot.exists()) {
                            // User found, handle accordingly
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                User user = snapshot.getValue(User.class);
                                // Do something with the user
                                if(new BCryptPasswordEncoder().matches(password,user.getPassword() ))
                                {
                                    System.out.println("password correct");
                                    Intent intent=new Intent(LoginActivity.this, SessionActivity.class);
                                    intent.putExtra("user",user.getEmail());
                                    LoginActivity.this.startActivity(intent);

                                    Toast.makeText(getBaseContext(), "Login Successfull", Toast.LENGTH_SHORT).show();



                                }
                                else
                                {
                                    passwordEditText.setError("Incorrect Password");
                                    Toast.makeText(getBaseContext(), "Incorrect Password", Toast.LENGTH_SHORT).show();

                                    view.setEnabled(true);
                                    return;
                                }
                            }
                        } else {
                            // User not found
                            emailOrIdEditText.setError("No User Found");
                            Toast.makeText(getBaseContext(), "No User Found", Toast.LENGTH_SHORT).show();

                            view.setEnabled(true);
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        handler.removeCallbacksAndMessages(null);
                        // Handle errors, if any

                        System.err.println(databaseError.getMessage());
                        Toast.makeText(getBaseContext(), "An error Occurred "+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                        view.setEnabled(true);
                        return;
                    }
                });

                handler.postDelayed(()->{
                    Toast.makeText(getBaseContext(), "Connection Error. Timeout", Toast.LENGTH_SHORT).show();

                    view.setEnabled(true);
                    return;
                },5000);


            }
        });

    }
}