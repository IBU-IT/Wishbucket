package dev.ibu.wishbucket.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.AccessToken;

import dev.ibu.wishbucket.R;

public class HomeActivity extends AppCompatActivity {
    private AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        accessToken = intent.getParcelableExtra(MainActivity.ACCESS_TOKEN_KEY);

        if(accessToken == null)
            finish();

    }

}
