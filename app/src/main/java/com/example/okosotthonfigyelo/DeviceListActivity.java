package com.example.okosotthonfigyelo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class DeviceListActivity extends AppCompatActivity {

    private static final String LOG_TAG = DeviceListActivity.class.getName();

    //Esetleg majd userName és a phoneNumber tovább jöhet és majd valahova teszem ha lesz profil fül akkor oda vagy idk
    //még de ezek még nincsenek használva

    private FirebaseAuth auth;
    private FirebaseUser user;

    private RecyclerView recyclerView;
    private ArrayList<Device> deviceList;
    private DeviceListAdapter adapter;
    private int gridNumber = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            Log.d(LOG_TAG, "Authentication successful!");
        }
        else{
            Log.d(LOG_TAG, "Authentication unsuccessful!");
            finish();
        }

        recyclerView = findViewById(R.id.recylerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, gridNumber));
        deviceList = new ArrayList<>();

        adapter = new DeviceListAdapter(this, deviceList);
        recyclerView.setAdapter(adapter);

        initalizeData();
    }

    private void initalizeData() {
        String[] deviceNames = getResources().getStringArray(R.array.device_names);
        String[] deviceManufacturers = getResources().getStringArray(R.array.device_manufacturers);
        String[] deviceDescriptions = getResources().getStringArray(R.array.device_descriptions);
        TypedArray deviceImages = getResources().obtainTypedArray(R.array.device_images);

        deviceList.clear();

        for (int i = 0; i < deviceNames.length; i++){
            deviceList.add(new Device(deviceNames[i], deviceManufacturers[i], deviceDescriptions[i], false, deviceImages.getResourceId(i ,0)));
        }

        deviceImages.recycle();
        adapter.notifyDataSetChanged();
    }


}