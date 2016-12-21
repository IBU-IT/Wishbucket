package dev.ibu.wishbucket.views.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import dev.ibu.wishbucket.R;

public class MainActivity extends AppCompatActivity {
    private LoginButton mLoginButton;
    private CallbackManager mCallbackManager;
    public AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoginButton = (LoginButton) findViewById(R.id.btnLogin);

        //set required fields from facebook api
        mLoginButton.setReadPermissions("email, user_friends");

        mCallbackManager = CallbackManager.Factory.create();

        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //get access token
                accessToken = loginResult.getAccessToken();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

}
