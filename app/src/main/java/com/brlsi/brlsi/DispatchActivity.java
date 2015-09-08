package com.brlsi.brlsi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.parse.ParseUser;

public class DispatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ParseUser.getCurrentUser() != null) {
            // Start an intent for the logged in activity
            Log.d("BrlsiDebug", "onCreate, got user,  "
                    + ParseUser.getCurrentUser().getUsername());
            startActivity(new Intent(this, MainActivity.class));
        } else {
            // Start and intent for the logged out activity
            Log.d("BrlsiDebug", "onCreate, no user");
            startActivity(new Intent(this, LogInActivity.class));
        }
    }
}