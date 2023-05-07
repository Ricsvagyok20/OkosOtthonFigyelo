package com.example.okosotthonfigyelo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddDevice extends AppCompatActivity {

    private static final String LOG_TAG = AddDevice.class.getName();

    EditText newDeviceName;

    EditText newDeviceManufacturer;

    EditText newDeviceDescription;

    private FirebaseFirestore firestore;
    private CollectionReference items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_device);

        newDeviceName = findViewById(R.id.newDeviceName);
        newDeviceManufacturer = findViewById(R.id.newDeviceManufacturer);
        newDeviceDescription = findViewById(R.id.newDeviceDescription);

        firestore = FirebaseFirestore.getInstance();
        items = firestore.collection("Devices");
    }


    public void add(View view) {
        Device newDevice = new Device(
                newDeviceName.getText().toString(),
                newDeviceManufacturer.getText().toString(),
                newDeviceDescription.getText().toString(),
                false,
                R.drawable.no_photo);
        items.add(newDevice).addOnFailureListener(fail -> {
            Toast.makeText(this, "Device " + newDevice.getName() + " couldn't be added!", Toast.LENGTH_LONG).show();
        });
        startDeviceList();
    }

    public void back(View view) {
        startDeviceList();
    }

    private void startDeviceList() {
        Intent intent = new Intent(this, DeviceListActivity.class);
        startActivity(intent);
    }
}
