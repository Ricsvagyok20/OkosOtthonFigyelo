package com.example.okosotthonfigyelo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getName();
    EditText userNameEditText;
    EditText emailEditText;
    EditText phoneNumberEditText;
    EditText pwdEditText;
    EditText pwdAgainEditText;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mAuth = FirebaseAuth.getInstance();
        userNameEditText = findViewById(R.id.userName);
        emailEditText = findViewById(R.id.email);
        phoneNumberEditText = findViewById(R.id.phoneNumber);
        pwdEditText = findViewById(R.id.pwd);
        pwdAgainEditText = findViewById(R.id.pwdAgain);

        auth = FirebaseAuth.getInstance();

        Log.i(LOG_TAG, "onCreate");
    }

    public void register(View view) {
        String userName = userNameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = pwdEditText.getText().toString();
        String passwordAgain = pwdAgainEditText.getText().toString();

        if (!password.equals(passwordAgain)) {
            Log.e(LOG_TAG, "Nem egyenlő a jelszó és a megerősítése.");
            return;
        }

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startDeviceList();
                    Log.i(LOG_TAG, "User was created successfully!");
                }
                else{
                    Toast.makeText(MainActivity.this, "Couldn't create user!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void login(View view) {
        startLogin();
    }

    private void startDeviceList() {
        Intent intent = new Intent(this, DeviceListActivity.class);
        startActivity(intent);
    }

    private void startLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}