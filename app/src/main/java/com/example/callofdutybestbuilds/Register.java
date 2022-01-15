package com.example.callofdutybestbuilds;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.callofdutybestbuilds.Objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button registerButton, loginButton;
    TextView email;
    TextView password;
    TextView passwordCheck;
    private static final String TAG = "Register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.register);

        registerButton = findViewById(R.id.registerButton);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        passwordCheck = findViewById(R.id.password_check);
        loginButton = findViewById(R.id.loginButton);
        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText = email.getText().toString().trim();
                String passwordText = password.getText().toString().trim();
                String passwordCheckText = passwordCheck.getText().toString().trim();

                if(emailText.isEmpty()){
                    Context context = getApplicationContext();
                    CharSequence sequence = "Username cannot be empty!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, sequence, duration);
                    toast.show();
                    email.requestFocus();
                    return;
                }else if(passwordText.isEmpty()){
                    Context context = getApplicationContext();
                    CharSequence sequence = "Password cannot be empty!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, sequence, duration);
                    toast.show();

                    password.requestFocus();
                    return;
                }else if(password.length() < 6) {
                    Context context = getApplicationContext();
                    CharSequence sequence = "Password must have minimum 6 characters!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, sequence, duration);
                    toast.show();

                    passwordCheck.requestFocus();
                    return;
                }else if(passwordCheckText.isEmpty()) {
                    Context context = getApplicationContext();
                    CharSequence sequence = "Password check cannot be empty!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, sequence, duration);
                    toast.show();

                    passwordCheck.requestFocus();
                    return;
                }
                else if(!passwordText.equals(passwordCheckText)){
                    Context context = getApplicationContext();
                    CharSequence sequence = "Password not match!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, sequence, duration);
                    toast.show();

                    passwordCheck.requestFocus();
                    password.requestFocus();
                    return;

                }else{

                    mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                            .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            }else{
                                Log.w(TAG,"ERROR ====" + task.getException());
                                Toast.makeText(Register.this, "Incorrect email format!", Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
                }
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if(user == null){
            Log.w(TAG, "Something is wrong when try to login ( user is null");
        }else{
            startActivity(new Intent(Register.this, Index.class));
            finish();
        }
    }


}