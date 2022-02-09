package com.nicolaescugroup.codbuilds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import com.nicolaescugroup.codbuilds.Objects.Weapon_meta;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;


public class meta_weapons extends AppCompatActivity {
    FirebaseAuth mAuth;
    LinearLayout layout;
    private FrameLayout adContainerView;
    private AdView adView;
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
                        Weapon_meta weapon_temp = new Weapon_meta(doc.getData().get("weapon_name").toString(), doc.getData().get("att1").toString(), doc.getData().get("att2").toString(), doc.getData().get("att3").toString(), doc.getData().get("att4").toString(), doc.getData().get("att5").toString(), doc.getData().get("att6").toString(), doc.getData().get("att7").toString(), doc.getData().get("att8").toString(), doc.getData().get("att9").toString(), doc.getData().get("att10").toString(), doc.getId(), doc.getData().get("youtubeLink").toString());
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


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        //get the reference to your FrameLayout
        adContainerView = findViewById(R.id.adView_container);

        //Create an AdView and put it into your FrameLayout
        adView = new AdView(this);
        adContainerView.addView(adView);
        adView.setAdUnitId("ca-app-pub-3863363113592693/6041741159");
        loadBanner();



    }


    public void createButtons(){
        for(int i = 0; i < weaponsBuildsArray.size(); i++){
            final Button button = new Button(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,300);
            lp.setMargins(30,40,30,0);
            button.setLayoutParams(lp);
            Log.w("meta", weaponsBuildsArray.get(i).toString());
            button.setBackgroundResource(R.drawable.weapons_meta);
            //button.setBackgroundColor(Color.parseColor("#525856"));
            button.setText(weaponsBuildsArray.get(i).getWeapon_name());
            button.setTextColor(Color.WHITE);
            button.setTextSize(45);
            button.setAllCaps(false);
            button.setGravity(Gravity.CENTER|Gravity.RIGHT);
            button.setPadding(10, 10 , 30, 10);
            Typeface typeface = getResources().getFont(R.font.outfitregular);
            button.setTypeface(typeface);
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

    private void loadBanner() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        AdSize adSize = getAdSize();
        // Set the adaptive ad size to the ad view.
        adView.setAdSize(adSize);

        // Start loading the ad in the background.
        adView.loadAd(adRequest);
    }
    private AdSize getAdSize() {
        //Determine the screen width to use for the ad width.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        //you can also pass your selected width here in dp
        int adWidth = (int) (widthPixels / density);

        //return the optimal size depends on your orientation (landscape or portrait)
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
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