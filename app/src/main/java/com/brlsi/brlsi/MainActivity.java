package com.brlsi.brlsi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_EXPERIMENT = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startExperiment(View view) {
        startActivityForResult(new Intent(this, StartExperimentActivity.class), REQUEST_EXPERIMENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_EXPERIMENT && resultCode == RESULT_OK) {
            Toast.makeText(this, "+1", Toast.LENGTH_SHORT).show();
        }
    }
}
