package com.example.okosotthonfigyelo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DeviceListActivity extends AppCompatActivity {

    private static final String LOG_TAG = DeviceListActivity.class.getName();

    //Esetleg majd userName és a phoneNumber tovább jöhet és majd valahova teszem ha lesz profil fül akkor oda vagy idk
    //még de ezek még nincsenek használva

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            Log.d(LOG_TAG, "Authentication successful!");
        }
        else{
            Log.d(LOG_TAG, "Authentication unsuccessful!");
            finish();
        }
    }
}