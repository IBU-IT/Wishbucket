package dev.ibu.wishbucket.views.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequest.GraphJSONObjectCallback;
import com.facebook.GraphResponse;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

import dev.ibu.wishbucket.R;

public class HomeActivity extends AppCompatActivity {
    private AccessToken accessToken;
    Button b;
    TextView testTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        b = (Button) findViewById(R.id.open_recommendation_button);
        testTV = (TextView) findViewById(R.id.jsonResponse);

        Intent intent = getIntent();
        accessToken = intent.getParcelableExtra(MainActivity.ACCESS_TOKEN_KEY);

        callApi();

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recommendationIntent = new Intent(HomeActivity.this, RecommendationActivity.class);
                recommendationIntent.putExtra(MainActivity.ACCESS_TOKEN_KEY, accessToken);
                HomeActivity.this.startActivity(recommendationIntent);
            }
        });

        //if (accessToken == null)
         //   finish();
    }

    private void callApi() {
        GraphRequest request = GraphRequest.newMyFriendsRequest(
                accessToken,
                new GraphRequest.GraphJSONArrayCallback() {
                    @Override
                    public void onCompleted(JSONArray objects, GraphResponse response) {

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }
}
