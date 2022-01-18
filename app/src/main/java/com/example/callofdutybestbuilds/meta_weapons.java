package com.example.callofdutybestbuilds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.callofdutybestbuilds.Objects.Weapon_meta;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class meta_weapons extends AppCompatActivity {
    FirebaseAuth mAuth;
    LinearLayout layout;
    ArrayList<Weapon_meta> weaponsBuildsArray = new ArrayList<Weapon_meta>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meta_weapons);
        mAuth = FirebaseAuth.getInstance();
        layout = findViewById(R.id.metaWeaponsActivity);
        db.collection("meta_builds").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot doc : task.getResult()){
                        Weapon_meta weapon_temp = new Weapon_meta(doc.getData().get("weapon_name").toString(), doc.getData().get("att1").toString(), doc.getData().get("att2").toString(), doc.getId());
                        weaponsBuildsArray.add(weapon_temp);
                        Log.d("META: ---->",doc.getId() + " -> " + doc.getData().get("weapon_name") + "new obj:" + weapon_temp + "new size: " + weaponsBuildsArray.size());
                    }
                }else{
                    Log.d("META: ---->","Error getting documents ->>>" + task.getException());

                }
                Log.d("META: ---->","Error getting documents ->>>" + weaponsBuildsArray.size());
                createButtons();

            }
        });



    }


    public void createButtons(){
        for(int i = 0; i < weaponsBuildsArray.size(); i++){
            final Button button = new Button(this);
            button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,300));
            Log.w("meta", weaponsBuildsArray.get(i).toString());
            button.setBackgroundResource(R.drawable.meta_weapons_bg);
            button.setText(weaponsBuildsArray.get(i).getWeapon_name());
            button.setTextColor(Color.WHITE);
            button.setTextSize(25);
            button.setGravity(Gravity.LEFT|Gravity.BOTTOM);
            button.setPadding(10, 10 , 10, 10);
            button.setTypeface(Typeface.create("font/outfitregular.ttf", Typeface.NORMAL));
            button.setTag(weaponsBuildsArray.get(i).getWeapon_name());
            layout.addView(button);
            String wpName = weaponsBuildsArray.get(i).getWeapon_name();
            int iterator = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(meta_weapons.this, meta_weapon.class);
                    Bundle parameters = new Bundle();
                    parameters.putString("id_weapon", weaponsBuildsArray.get(iterator).getId());
                    intent.putExtras(parameters);
                    startActivity(intent);
                    Log.w("BUTTON", "Ai apasat pe butonul pentru " + wpName);
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

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
            Intent logoutIntent = new Intent(meta_weapons.this, Login.class);
            logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(logoutIntent);
        }
        return super.onOptionsItemSelected(mItem);
    }
}