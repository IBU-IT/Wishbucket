package dev.ibu.wishbucket.views.activities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

import dev.ibu.wishbucket.tasks.SearchTask;

/**
 * Created by dzananganic1 on 28/12/2016.
 */

public class RecommendationProvider {

    public void getGameRecommendations(JSONObject gameInterests, SearchTask.OnSearchTaskFinished onSearchTaskFinished){
        try {
            JSONArray interestsData = gameInterests.getJSONArray("data");

            for (int i = 0; i < interestsData.length(); i++) {
                JSONObject currentInterest = interestsData.getJSONObject(i);

                String currentInterestName = currentInterest.getString("name");

                String url = "https://www.google.ba/webhp?q=games+like+20minecraft";

                currentInterestName = currentInterestName.replace(" ", "+");
                url += currentInterestName;

                SearchTask.createTask(onSearchTaskFinished,url);

            }
        } catch (JSONException e){

        }

    }

    public JSONArray getMovieRecommendations(JSONObject movieInterests){

        return null;
    }

    public JSONArray getBookRecommendations(JSONObject bookInterests){

        return null;
    }
}
