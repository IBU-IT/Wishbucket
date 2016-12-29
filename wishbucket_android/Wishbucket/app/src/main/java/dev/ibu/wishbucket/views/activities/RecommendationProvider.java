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

    public void getRecommendations(String type, JSONObject gameInterests, SearchTask.OnSearchTaskFinished onSearchTaskFinished){
        try {
            JSONArray interestsData = gameInterests.getJSONArray("data");

            int length  = interestsData.length()>5? 5:interestsData.length();

            for (int i = 0; i < interestsData.length(); i++) {
                JSONObject currentInterest = interestsData.getJSONObject(i);

                String currentInterestName = currentInterest.getString("name");

                String url = "https://www.tastekid.com/api/similar?k=253273-tesnoIme-G6LXKSNV&type=" + type + "&q=";

                currentInterestName = currentInterestName.replace(" ", "+");
                url += currentInterestName;

                SearchTask.createTask(onSearchTaskFinished,url);

            }
        } catch (JSONException e){

        }

    }
}
