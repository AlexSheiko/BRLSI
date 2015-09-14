package com.brlsi.brlsi;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ExperimentComposeActivity extends AppCompatActivity
        implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

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

    @Bind(R.id.camera_conditions)
    ImageButton cameraConditionsButton;
    @Bind(R.id.camera_equipment)
    ImageButton cameraEquipmentButton;
    @Bind(R.id.camera_action)
    ImageButton cameraActionButton;
    @Bind(R.id.camera_outcome)
    ImageButton cameraOutcomeButton;
    @Bind(R.id.camera_what_happened)
    ImageButton cameraWhatHappenedButton;
    @Bind(R.id.camera_why_did_happen)
    ImageButton cameraWhyDidHappenButton;
    @Bind(R.id.camera_how_to_use)
    ImageButton cameraHowToUseButton;

    @Bind(R.id.finishButton)
    Button finishButton;
    private ParseObject mExperiment;

    private static final int REQUEST_IMAGE_CAPTURE = 101;
    String mCurrentPhotoPath;
    private int mPhotoPickerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_experiment);
        ButterKnife.bind(this);


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
        } else {
            mExperiment = new ParseObject("Experiment");
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
                    if (((EditText) inputField).getText().toString().isEmpty()) {
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

        ParseFile conditionsPhoto = experiment.getParseFile("conditionsPhoto");
        ParseFile equipmentPhoto = experiment.getParseFile("equipmentPhoto");
        ParseFile actionPhoto = experiment.getParseFile("actionPhoto");
        ParseFile outcomePhoto = experiment.getParseFile("outcomePhoto");
        ParseFile whatHappenedPhoto = experiment.getParseFile("whatHappenedPhoto");
        ParseFile whyDidHappenPhoto = experiment.getParseFile("whyDidHappenPhoto");
        ParseFile howToUsePhoto = experiment.getParseFile("howToUsePhoto");

        if (conditionsPhoto != null) {
            setImage(conditionsPhoto, cameraConditionsButton);
        }
        if (equipmentPhoto != null) {
            setImage(equipmentPhoto, cameraEquipmentButton);
        }
        if (actionPhoto != null) {
            setImage(actionPhoto, cameraActionButton);
        }
        if (outcomePhoto != null) {
            setImage(outcomePhoto, cameraOutcomeButton);
        }
        if (whatHappenedPhoto != null) {
            setImage(whatHappenedPhoto, cameraWhatHappenedButton);
        }
        if (whyDidHappenPhoto != null) {
            setImage(whyDidHappenPhoto, cameraWhyDidHappenButton);
        }
        if (howToUsePhoto != null) {
            setImage(howToUsePhoto, cameraHowToUseButton);
        }
    }

    private void setImage(ParseFile photo, final ImageButton imageButton) {
        photo.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] imageData, ParseException e) {
                if (e == null) {
                    imageButton.setAlpha(1f);

                    Glide.with(ExperimentComposeActivity.this)
                            .load(imageData).fitCenter().centerCrop().into(imageButton);
                }
            }
        });
    }

    public void finishExperiment(View view) {
        saveExperiment();

        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    private void saveExperiment() {
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

        mExperiment.put("author", ParseUser.getCurrentUser());
        mExperiment.put("name", ParseUser.getCurrentUser().getUsername());
        mExperiment.put("title", title);
        mExperiment.put("date", date);
        mExperiment.put("time", time);
        mExperiment.put("location", location);
        mExperiment.put("conditions", conditions);
        mExperiment.put("equipment", equipment);
        mExperiment.put("action", action);
        mExperiment.put("outcome", outcome);
        mExperiment.put("whatHappened", whatHappened);
        mExperiment.put("whyDidHappen", whyDidHappen);
        mExperiment.put("howToUse", howToUse);

        mExperiment.saveEventually();
    }

    public void cancel(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        String title = titleField.getText().toString();
        if (!title.isEmpty()) {
            finishExperiment(new View(this));
        }
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

    public void attachPhoto(View view) {
        mPhotoPickerId = view.getId();
        dispatchTakePictureIntent();
    }

    private void dispatchTakePictureIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ignored) {
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        } else {
            Toast.makeText(this, "Install camera app to take a picture", Toast.LENGTH_LONG).show();
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            ImageButton cameraButton = (ImageButton) findViewById(mPhotoPickerId);
            cameraButton.setAlpha(1f);
            Glide.with(this).load(mCurrentPhotoPath).fitCenter().centerCrop().into(cameraButton);

            try {
                InputStream iStream = getContentResolver().openInputStream(Uri.parse(mCurrentPhotoPath));
                byte[] inputData = getBytes(iStream);
                final ParseFile photo = new ParseFile("image.jpg", inputData);
                photo.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            savePhoto(photo, mPhotoPickerId);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private void savePhoto(ParseFile photo, int pickerId) {
        switch (pickerId) {
            case R.id.camera_conditions:
                mExperiment.put("conditionsPhoto", photo);
                break;
            case R.id.camera_equipment:
                mExperiment.put("equipmentPhoto", photo);
                break;
            case R.id.camera_action:
                mExperiment.put("actionPhoto", photo);
                break;
            case R.id.camera_outcome:
                mExperiment.put("outcomePhoto", photo);
                break;
            case R.id.camera_what_happened:
                mExperiment.put("whatHappenedPhoto", photo);
                break;
            case R.id.camera_why_did_happen:
                mExperiment.put("whyDidHappenPhoto", photo);
                break;
            case R.id.camera_how_to_use:
                mExperiment.put("howToUsePhoto", photo);
                break;
        }
        mExperiment.saveEventually();
    }
}