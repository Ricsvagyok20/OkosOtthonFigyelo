package com.example.okosotthonfigyelo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collection;

public class DeviceListActivity extends AppCompatActivity {

    private static final String LOG_TAG = DeviceListActivity.class.getName();

    //Esetleg majd userName és a phoneNumber tovább jöhet és majd valahova teszem ha lesz profil fül akkor oda vagy idk
    //még de ezek még nincsenek használva

    private FirebaseAuth auth;
    private FirebaseUser user;

    private FirebaseFirestore firestore;
    private CollectionReference items;

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

        firestore = FirebaseFirestore.getInstance();
        items = firestore.collection("Devices");

        queryData();
    }

    private void queryData() {
        deviceList.clear();
        items.orderBy("name", Query.Direction.DESCENDING).limit(10).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                Device device = document.toObject(Device.class);
                deviceList.add(device);
            }

            if (deviceList.size() == 0) {
                initializeData();
                queryData();
            }

            // Notify the adapter of the change.
            adapter.notifyDataSetChanged();
        });
    }

    private void initializeData() {
        String[] deviceNames = getResources().getStringArray(R.array.device_names);
        String[] deviceManufacturers = getResources().getStringArray(R.array.device_manufacturers);
        String[] deviceDescriptions = getResources().getStringArray(R.array.device_descriptions);
        TypedArray deviceImages = getResources().obtainTypedArray(R.array.device_images);

        deviceList.clear();

        for (int i = 0; i < deviceNames.length; i++){
            items.add(new Device(deviceNames[i], deviceManufacturers[i], deviceDescriptions[i], false, deviceImages.getResourceId(i ,0)));
        }

        deviceImages.recycle();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(LOG_TAG, s);
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.view_selector:
                if (gridNumber == 2) {
                    changeSpanCount(item, R.drawable.single_view, 1);
                } else {
                    changeSpanCount(item, R.drawable.default_view, 2);
                }
                return true;
            case R.id.sort_by_active:
                Log.d(LOG_TAG, "Sort by active clicked!");
                //TODO Ezt majd Firebase lekerdezeskent kene megvalositani
                adapter.getFilterByActive().filter("");
                return true;
            case R.id.log_out_button:
                Log.d(LOG_TAG, "Log out clicked!");
                FirebaseAuth.getInstance().signOut();
                finish();
                return true;
            case R.id.settings_button:
                Log.d(LOG_TAG, "Settings clicked!");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void changeSpanCount(MenuItem item, int drawableId, int spanCount) {
        if(gridNumber == 2){
            gridNumber = 1;
        }
        else{
            gridNumber = 2;
        }
        item.setIcon(drawableId);
        GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        layoutManager.setSpanCount(spanCount);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

}