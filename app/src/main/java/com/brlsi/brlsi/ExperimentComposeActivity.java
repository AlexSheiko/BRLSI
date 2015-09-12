package com.brlsi.brlsi;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ExperimentComposeActivity extends AppCompatActivity
        implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

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
            query.fromLocalDatastore();
            query.getInBackground(experimentId, new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject experiment, ParseException e) {
                    mExperiment = experiment;
                    populateFields(experiment);
                }
            });
        }

        dateField.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View inputField, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (((EditText) inputField).getText().toString().isEmpty()) {
                        new DatePickerDialog(
                                ExperimentComposeActivity.this,
                                ExperimentComposeActivity.this,
                                Calendar.getInstance().get(Calendar.YEAR),
                                Calendar.getInstance().get(Calendar.MONTH),
                                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                        ).show();
                    } else {
                        String dateString = ((EditText) inputField).getText().toString();
                        int day = Integer.parseInt(dateString.split("/")[0]);
                        int month = Integer.parseInt(dateString.split("/")[1]) - 1;
                        int year = 2000 + Integer.parseInt(dateString.split("/")[2]);

                        new DatePickerDialog(
                                ExperimentComposeActivity.this,
                                ExperimentComposeActivity.this,
                                year,
                                month,
                                day
                        ).show();
                    }
                }
                return true;
            }
        });

        timeField.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View inputField, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (((EditText)inputField).getText().toString().isEmpty()) {
                        new TimePickerDialog(
                                ExperimentComposeActivity.this,
                                ExperimentComposeActivity.this,
                                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                                Calendar.getInstance().get(Calendar.MINUTE),
                                true
                        ).show();
                    } else {
                        String timeString = ((EditText) inputField).getText().toString();
                        int hour = Integer.parseInt(timeString.split(":")[0]);
                        int minute = Integer.parseInt(timeString.split(":")[1]);

                        new TimePickerDialog(
                                ExperimentComposeActivity.this,
                                ExperimentComposeActivity.this,
                                hour,
                                minute,
                                true
                        ).show();
                    }
                }
                return true;
            }
        });
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

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String time = timeFormat.format(calendar.getTime());
        timeField.setText(time);
        timeField.setSelection(timeField.length());
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        String date = dateFormat.format(calendar.getTime());
        dateField.setText(date);
        dateField.setSelection(dateField.length());
    }
}
