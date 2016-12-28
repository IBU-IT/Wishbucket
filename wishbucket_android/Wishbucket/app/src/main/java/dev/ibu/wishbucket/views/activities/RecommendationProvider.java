package dev.ibu.wishbucket.views.activities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dzananganic1 on 28/12/2016.
 */
public class RecommendationProvider {

    public JSONArray getGameRecommendations(JSONObject gameInterests){
        try {
            return new JSONArray("[]");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONArray getMovieRecommendations(JSONObject movieInterests){
        try {
            return new JSONArray("[]");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONArray getBookRecommendations(JSONObject bookInterests){
        try {
            return new JSONArray("[]");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
