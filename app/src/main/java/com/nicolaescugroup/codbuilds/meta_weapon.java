package com.nicolaescugroup.codbuilds;

import androidx.annotation.NonNull;

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
import android.widget.TextView;

import com.nicolaescugroup.codbuilds.Objects.Weapon_meta;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
    private FrameLayout adContainerView;
    private AdView adView;
    private LinearLayout weaponsDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meta_weapon);
        weaponsDetails = findViewById(R.id.metaWeaponsActivity);
        mAuth = FirebaseAuth.getInstance();

        ytView = (YouTubePlayerView) findViewById(R.id.youtubePlayer);
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(weapon.getYoutubeLink());
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
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
                    weapon = new Weapon_meta(doc.getData().get("weapon_name").toString(), doc.getData().get("att1").toString(), doc.getData().get("att2").toString(), doc.getData().get("att3").toString(), doc.getData().get("att4").toString(), doc.getData().get("att5").toString(), doc.getData().get("att6").toString(), doc.getData().get("att7").toString(), doc.getData().get("att8").toString(), doc.getData().get("att9").toString(), doc.getData().get("att10").toString(), doc.getId(), doc.get("youtubeLink").toString());
                    Log.d("META: ---->","NO ERROr getting documents ->>>" + weapon);

                }else{
                    Log.d("META: ---->","Error getting documents ->>>" + task.getException());
                }
                loadData();
            }


        });
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

    public void loadData(){



        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 175);
        lp.setMargins(0,10,0,0);
        final TextView textAttribute = new TextView(this);
        textAttribute.setLayoutParams(lp);
        textAttribute.setPadding(15,15,15,15);
        textAttribute.setTextSize(20);
        textAttribute.setGravity(Gravity.LEFT);
        textAttribute.setTextColor(Color.WHITE);
        Typeface typeface = getResources().getFont(R.font.outfitregular);
        textAttribute.setTypeface(typeface);
        String weaponName = "○Weapon name: \n    -" + weapon.getWeapon_name();
        textAttribute.setText(weaponName);
        weaponsDetails.addView(textAttribute);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 800);
        lp.setMargins(0,10,0,0);
        final TextView textAttach = new TextView(this);
        textAttach.setLayoutParams(lp2);
        textAttach.setPadding(15,15,15,15);
        textAttach.setTextSize(20);
        textAttach.setGravity(Gravity.LEFT);
        textAttach.setTextColor(Color.WHITE);
        textAttach.setTypeface(typeface);
        String attachName = "○Attachments name: \n    " + weapon.getAtt1()+ "\n    " + weapon.getAtt2()+ "\n    " + weapon.getAtt3()+ "\n    " + weapon.getAtt4()+ "\n    " + weapon.getAtt5()+ "\n    " + weapon.getAtt6()+ "\n    " + weapon.getAtt7()+ "\n    " + weapon.getAtt8()+ "\n    " + weapon.getAtt9()+ "\n    " + weapon.getAtt10();
        textAttach.setText(attachName);
        weaponsDetails.addView(textAttach);




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