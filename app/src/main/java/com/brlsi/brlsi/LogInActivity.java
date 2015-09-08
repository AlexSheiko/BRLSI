package com.brlsi.brlsi;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LogInActivity extends AppCompatActivity {

    @Bind(R.id.usernameField)
    EditText usernameField;
    @Bind(R.id.passwordField)
    EditText passwordField;
    @Bind(R.id.loginButton)
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ButterKnife.bind(this);

        Typeface droidSans = Typeface.createFromAsset(getAssets(), "Signika-Bold.otf");


        usernameField.setTypeface(droidSans);
        passwordField.setTypeface(droidSans);
        loginButton.setTypeface(droidSans);
    }

    public void logIn(View view) {
        String username = usernameField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    // Hooray! The user is logged in.
                    startActivity(new Intent(LogInActivity.this, MainActivity.class));
                } else {
                    // Signup failed. Look at the ParseException to see what happened.
                    Toast.makeText(LogInActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
