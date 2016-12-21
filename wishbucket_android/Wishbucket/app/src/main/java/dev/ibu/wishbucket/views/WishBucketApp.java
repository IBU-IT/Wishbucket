package dev.ibu.wishbucket.views;

import android.app.Application;

import com.facebook.FacebookSdk;

/**
 * Created by kemal on 12/21/16.
 */

public class WishBucketApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
}
