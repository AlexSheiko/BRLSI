package com.brlsi.brlsi;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
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
    @Bind(R.id.conditions)
    EditText conditionsField;
    @Bind(R.id.equipment)
    EditText equipmentField;
    @Bind(R.id.action)
    EditText actionField;
    @Bind(R.id.outcome)
    EditText outcomeField;
    @Bind(R.id.whatHappened)
    EditText whatHappenedField;
    @Bind(R.id.whyDidHappen)
    EditText whyDidHappenField;
    @Bind(R.id.howToUse)
    EditText howToUseField;

    @Bind(R.id.finishButton)
    Button finishButton;
    private ParseObject mExperiment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_experiment);
        ButterKnife.bind(this);

        // TODO: Insert photo icon


        Typeface signika = Typeface.createFromAsset(getAssets(), "Signika-Bold.otf");

        finishButton.setTypeface(signika);

        if (getIntent().hasExtra("experiment_id")) {
            String experimentId = getIntent().getStringExtra("experiment_id");
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Experiment");
            query.getInBackground(experimentId, new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject experiment, ParseException e) {
                    mExperiment = experiment;
                    populateFields(experiment);
                }
            });
        }
    }

    private void populateFields(ParseObject experiment) {
        String name = experiment.getString("name");
        String title = experiment.getString("title");
        String date = experiment.getString("date");
        String time = experiment.getString("time");
        String location = experiment.getString("location");
        String conditions = experiment.getString("conditions");
        String equipment = experiment.getString("equipment");
        String action = experiment.getString("action");
        String outcome = experiment.getString("outcome");
        String whatHappened = experiment.getString("whatHappened");
        String whyDidHappen = experiment.getString("whyDidHappen");
        String howToUse = experiment.getString("howToUse");

        if (name != null) {
            nameField.setText(name);
        }
        if (title != null && !title.equals("No title")) {
            titleField.setText(title);
        }
        if (date != null) {
            dateField.setText(date);
        }
        if (time != null) {
            timeField.setText(time);
        }
        if (location != null) {
            locationField.setText(location);
        }
        if (conditions != null) {
            conditionsField.setText(conditions);
        }
        if (equipment != null) {
            equipmentField.setText(equipment);
        }
        if (action != null) {
            actionField.setText(action);
        }
        if (outcome != null) {
            outcomeField.setText(outcome);
        }
        if (whatHappened != null) {
            whatHappenedField.setText(whatHappened);
        }
        if (whyDidHappen != null) {
            whyDidHappenField.setText(whyDidHappen);
        }
        if (howToUse != null) {
            howToUseField.setText(howToUse);
        }
    }

    public void finishExperiment(View view) {
        saveExperiment();

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
        String conditions = conditionsField.getText().toString();
        String equipment = equipmentField.getText().toString();
        String action = actionField.getText().toString();
        String outcome = outcomeField.getText().toString();
        String whatHappened = whatHappenedField.getText().toString();
        String whyDidHappen = whyDidHappenField.getText().toString();
        String howToUse = howToUseField.getText().toString();

        if (title.isEmpty()) {
            title = "No title";
            if (!date.isEmpty()) {
                title += " " + date;
            }
        }

        ParseObject experiment;
        if (mExperiment == null) {
            experiment = new ParseObject("Experiment");
        } else {
            experiment = mExperiment;
        }
        experiment.put("author", ParseUser.getCurrentUser());
        experiment.put("name", name);
        experiment.put("title", title);
        experiment.put("date", date);
        experiment.put("time", time);
        experiment.put("location", location);
        experiment.put("conditions", conditions);
        experiment.put("equipment", equipment);
        experiment.put("action", action);
        experiment.put("outcome", outcome);
        experiment.put("whatHappened", whatHappened);
        experiment.put("whyDidHappen", whyDidHappen);
        experiment.put("howToUse", howToUse);
        experiment.saveEventually();
    }

    @Override
    public void onBackPressed() {
        finishExperiment(new View(this));
        super.onBackPressed();
    }
}
