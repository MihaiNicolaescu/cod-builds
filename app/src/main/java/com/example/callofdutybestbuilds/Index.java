package com.example.callofdutybestbuilds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Index extends AppCompatActivity {
    Button logOutButton;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.index);

        logOutButton = findViewById(R.id.logOutButton);

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent logoutIntent = new Intent(Index.this, Login.class);
                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(logoutIntent);
            }
        });
    }
}