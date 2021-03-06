package dev.ibu.wishbucket.views;

import android.app.Application;
import android.hardware.camera2.params.Face;

import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;

/**
 * Created by kemal on 12/21/16.
 */

public class WishBucketApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(this);
        FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_RAW_RESPONSES);
        FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS);
        FacebookSdk.setIsDebugEnabled(true);
        FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
    }
}
