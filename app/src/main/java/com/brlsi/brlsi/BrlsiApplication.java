package com.brlsi.brlsi;

import android.app.Application;

import com.parse.Parse;

public class BrlsiApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "WKUcjYvVqvAJVon2XhmbfnzaQljT4H5Bp0m4mFJT", "I4JXF2mJRqIE1HpLbjUGeFkXYn4yYBgWSIz0ugqA");
    }
}
