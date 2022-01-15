package com.example.callofdutybestbuilds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Index extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.index);



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem mItem){
        if(mItem.getItemId() == R.id.idLogout){
            mAuth.signOut();
            Intent logoutIntent = new Intent(Index.this, Login.class);
            logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(logoutIntent);
        }
        return super.onOptionsItemSelected(mItem);
    }
}