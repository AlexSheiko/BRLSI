package com.brlsi.brlsi;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseUser;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_EXPERIMENT = 123;

    @Bind(R.id.nameView)
    TextView nameView;
    @Bind(R.id.newButton)
    Button newButton;
    @Bind(R.id.pastButton)
    Button pastButton;
    @Bind(R.id.logoutButton)
    Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        nameView.setText(String.format("Hello, %s!",
                ParseUser.getCurrentUser().getString("name")));


        Typeface signika = Typeface.createFromAsset(getAssets(), "Signika-Bold.otf");

        nameView.setTypeface(signika);
        newButton.setTypeface(signika);
        pastButton.setTypeface(signika);
        logoutButton.setTypeface(signika);
    }

    public void startExperiment(View view) {
        startActivityForResult(new Intent(this, ExperimentComposeActivity.class), REQUEST_EXPERIMENT);
    }

    public void reviewExperiments(View view) {
        startActivity(new Intent(this, ExperimentListActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_EXPERIMENT) {
            if (resultCode == RESULT_OK) {
                ((Button) findViewById(R.id.pastButton)).setText("Past Experiments +1");
                ((Button) findViewById(R.id.newButton)).setText("New Experiment");
            } else {
                ((Button) findViewById(R.id.newButton)).setText("Resume Experiment");
            }
        }
    }

    public void logOut(View view) {
        ParseUser.logOutInBackground();
        startActivity(new Intent(this, LogInActivity.class));
        finish();
    }
}
