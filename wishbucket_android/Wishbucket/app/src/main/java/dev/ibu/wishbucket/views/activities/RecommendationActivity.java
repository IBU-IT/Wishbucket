package dev.ibu.wishbucket.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dev.ibu.wishbucket.R;
import dev.ibu.wishbucket.tasks.SearchTask;

public class RecommendationActivity extends AppCompatActivity implements SearchTask.OnSearchTaskFinished {

    private AccessToken accessToken;
    private SearchTask mSearchTask;

    private void setUpProfilePicture(){
        ProfilePictureView profilePictureView;
        profilePictureView = (ProfilePictureView) findViewById(R.id.profile_picture);
        profilePictureView.setProfileId(Profile.getCurrentProfile().getId());
    }

    private void parseJSONInterests(JSONObject interests){
        try {
            JSONObject books = interests.getJSONObject("books");
            JSONObject movies = interests.getJSONObject("movies");
            JSONObject games = interests.getJSONObject("games");

            RecommendationProvider rp = new RecommendationProvider();

            rp.getBookRecommendations(books);
            rp.getMovieRecommendations(movies);
            games.getJSONArray("data");
            rp.getGameRecommendations(games, this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getUserInterests(String userId){
        GraphRequest.newGraphPathRequest(accessToken,
                userId+"?fields=books,movies,games",
                new GraphRequest.Callback(){

                    @Override
                    public void onCompleted(GraphResponse response) {
                        JSONObject i = response.getJSONObject();

                        if(i!=null){
                            parseJSONInterests(i);
                        }
                    }
                }
        ).executeAsync();
    }

    private void getAccessToken(){
        Intent intent = getIntent();
        accessToken = intent.getParcelableExtra(MainActivity.ACCESS_TOKEN_KEY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getAccessToken();
        setUpProfilePicture();
        getUserInterests("1353906817961608");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchTask.cancel();
    }

    @Override
    public void onSuccess(ArrayList<String> interests) {
        Log.d("onSucces","...");
    }

    @Override
    public void onError() {

    }
}
