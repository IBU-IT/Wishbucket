package dev.ibu.wishbucket.views.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import dev.ibu.wishbucket.R;

public class MainActivity extends AppCompatActivity {
    final static String ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY";

    private LoginButton mLoginButton;
    private CallbackManager mCallbackManager;
    public AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Profile.getCurrentProfile().getId()!=null){
            Intent intent = new Intent(MainActivity.this, RecommendationActivity.class);
            intent.putExtra(ACCESS_TOKEN_KEY, AccessToken.getCurrentAccessToken());
            startActivity(intent);
        }

        mLoginButton = (LoginButton) findViewById(R.id.btnLogin);

        //set required fields from facebook api
        mLoginButton.setReadPermissions("email", "user_birthday", "user_friends", "user_likes");

        mCallbackManager = CallbackManager.Factory.create();

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "dev.ibu.wishbucket",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //get access token
                accessToken = loginResult.getAccessToken();
                Log.d("STUFF", accessToken.toString());
                Intent intent = new Intent(MainActivity.this, RecommendationActivity.class);
                intent.putExtra(ACCESS_TOKEN_KEY, accessToken);
                startActivity(intent);
            }

            @Override
            public void onCancel() {
                Log.i("Cancel: ", "User canceled action!");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("Error: ", error.toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStop() {
        // call the superclass method first
        super.onStop();
        finish();
    }
}
