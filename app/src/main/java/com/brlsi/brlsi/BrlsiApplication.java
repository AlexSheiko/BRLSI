package com.brlsi.brlsi;

import android.app.Application;

import com.parse.Parse;

public class BrlsiApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "7p9hrd9VKFWIjdQH9jwEEK4jO1CBlRaJxWryHIup", "VHShjJUzYIXiFiRvjvwFuOVAvj5l3yOUwtOaDbKf");
    }
}
