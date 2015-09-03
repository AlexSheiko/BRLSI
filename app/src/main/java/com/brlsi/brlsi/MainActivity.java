package com.brlsi.brlsi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseUser;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_EXPERIMENT = 123;

    @Bind(R.id.name)
    EditText nameField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        nameField.setText(String.format("Hello %s!",
                ParseUser.getCurrentUser().getUsername()));
    }

    public void startExperiment(View view) {
        startActivityForResult(new Intent(this, ExperimentActivity.class), REQUEST_EXPERIMENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_EXPERIMENT) {
            if (resultCode == RESULT_OK) {
                ((Button) findViewById(R.id.newButton)).setText("Past Experiments +1");
            } else {
                ((Button) findViewById(R.id.newButton)).setText("Resume Experiment");
            }
        }
    }
}
