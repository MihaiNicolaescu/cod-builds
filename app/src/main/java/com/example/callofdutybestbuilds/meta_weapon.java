package com.example.callofdutybestbuilds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.callofdutybestbuilds.Objects.Weapon_meta;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class meta_weapon extends YouTubeBaseActivity {
    private TextView weaponName;
    private TextView weaponAtt1;
    private TextView weaponAtt2;
    private Button playYoutube;
    private YouTubePlayerView ytView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Weapon_meta weapon;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meta_weapon);

        mAuth = FirebaseAuth.getInstance();
        weaponName = findViewById(R.id.weaponName);
        weaponAtt1 = findViewById(R.id.weaponAtt1);
        weaponAtt2 = findViewById(R.id.weaponAtt2);
        ytView = (YouTubePlayerView) findViewById(R.id.youtubePlayer);
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("1-S1_5bxHbs");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };


        playYoutube = findViewById(R.id.buttonPlayYoutube);

        playYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ytView.initialize("AIzaSyDNHLdIsuaR7t9APZxola29P1WsXnX0BW8", onInitializedListener);
            }
        });

        Bundle b = getIntent().getExtras();
        String weaponID = ""; // or other values
        if(b != null)
            weaponID = b.getString("id_weapon");


        db.collection("meta_builds").document(weaponID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    weapon = new Weapon_meta(doc.getData().get("weapon_name").toString(), doc.getData().get("att1").toString(), doc.getData().get("att2").toString(), doc.getId());
                    Log.d("META: ---->","NO ERROr getting documents ->>>" + weapon);

                }else{
                    Log.d("META: ---->","Error getting documents ->>>" + task.getException());
                }
                loadData();
            }


        });
    }

    public void loadData(){
        weaponName.setText(weapon.getWeapon_name());
        weaponAtt1.setText(weapon.getAtt1());
        weaponAtt2.setText(weapon.getAtt2());
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
            Intent logoutIntent = new Intent(meta_weapon.this, Login.class);
            logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(logoutIntent);
        }
        return super.onOptionsItemSelected(mItem);
    }
}