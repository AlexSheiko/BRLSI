package com.brlsi.brlsi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseObject;
import com.parse.ParseUser;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ExperimentComposeActivity extends AppCompatActivity {

    @Bind(R.id.name)
    EditText nameField;
    @Bind(R.id.title)
    EditText titleField;
    @Bind(R.id.date)
    EditText dateField;
    @Bind(R.id.time)
    EditText timeField;
    @Bind(R.id.location)
    EditText locationField;

    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_experiment);
        ButterKnife.bind(this);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        restoreFieldValues();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveFieldValues();
    }

    public void finishExperiment(View view) {
        saveFieldValues();
        saveExperiment();
        clearFieldValues();

        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    private void saveExperiment() {
        String name = nameField.getText().toString();
        String title = titleField.getText().toString();
        String date = dateField.getText().toString();
        String time = timeField.getText().toString();
        String location = locationField.getText().toString();

        ParseObject experiment = new ParseObject("Experiment");
        experiment.put("author", ParseUser.getCurrentUser());
        experiment.put("name", name);
        experiment.put("title", title);
        experiment.put("date", date);
        experiment.put("time", time);
        experiment.put("location", location);
        experiment.saveEventually();
    }

    private void saveFieldValues() {
        String name = nameField.getText().toString();
        String title = titleField.getText().toString();
        String date = dateField.getText().toString();
        String time = timeField.getText().toString();
        String location = locationField.getText().toString();

        mPrefs.edit()
                .putString("name", name)
                .putString("title", title)
                .putString("date", date)
                .putString("time", time)
                .putString("location", location)
                .apply();
    }

    private void restoreFieldValues() {
//        String name = mPrefs.getString("name", ParseUser.getCurrentUser().getUsername());
        String title = mPrefs.getString("title", "");
        String date = mPrefs.getString("date", "");
        String time = mPrefs.getString("time", "");
        String location = mPrefs.getString("location", "");

//        nameField.setText(name);
        titleField.setText(title);
        dateField.setText(date);
        timeField.setText(time);
        locationField.setText(location);
    }

    private void clearFieldValues() {
        nameField.setText("");
        titleField.setText("");
        dateField.setText("");
        timeField.setText("");
        locationField.setText("");
        mPrefs.edit().clear().apply();
    }
}
