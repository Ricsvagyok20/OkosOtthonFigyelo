package com.example.okosotthonfigyelo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = LoginActivity.class.getName();

    EditText userNameEditText;

    EditText pwdEditText;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameEditText = findViewById(R.id.userNameLogin);
        pwdEditText = findViewById(R.id.pwdLogin);

        auth = FirebaseAuth.getInstance();

        Log.i(LOG_TAG, "onCreate");
    }

    public void login(View view) {
        String userName = userNameEditText.getText().toString();
        String password = pwdEditText.getText().toString();

        auth.signInWithEmailAndPassword(userName, password).addOnCompleteListener(this, task -> {
            if(task.isSuccessful()){
                Log.d(LOG_TAG, "User logged in successfully");
                startDeviceList();
            } else {
                Log.d(LOG_TAG, "User log in fail");
                Toast.makeText(LoginActivity.this, "User log in fail: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void cancel(View view) {
        startMain();
    }

    private void startDeviceList() {
        Intent intent = new Intent(this, DeviceListActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent);
            overridePendingTransition(R.anim.enter, R.anim.exit);
        } else {
            startActivity(intent);
        }
    }

    private void startMain() {
        Intent intent = new Intent(this, MainActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent);
            overridePendingTransition(R.anim.enter, R.anim.exit);
        } else {
            startActivity(intent);
        }
    }
}