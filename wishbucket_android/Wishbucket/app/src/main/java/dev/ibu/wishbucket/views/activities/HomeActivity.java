package dev.ibu.wishbucket.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;

import dev.ibu.wishbucket.R;

public class HomeActivity extends AppCompatActivity {
    private AccessToken accessToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button b = (Button)findViewById(R.id.open_recommendation_button);
        Intent intent = getIntent();
        accessToken = intent.getParcelableExtra(MainActivity.ACCESS_TOKEN_KEY);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recommendationIntent = new Intent(HomeActivity.this, RecommendationActivity.class);
                recommendationIntent.putExtra("ACCESS_TOKEN_KEY", accessToken);
                HomeActivity.this.startActivity(recommendationIntent);
            }
        });

        if(accessToken == null)
            finish();

    }

}
