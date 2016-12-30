package dev.ibu.wishbucket.tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by kemal on 12/29/16.
 */

public class SearchTask extends AsyncTask<String,Void,ArrayList<String>> {
    private OnSearchTaskFinished mOnSearchTaskFinished;

    public static SearchTask createTask(OnSearchTaskFinished onSearchTaskFinished,String... urls){
        SearchTask searchTask = new SearchTask();
        searchTask.setOnSearchTaskFinished(onSearchTaskFinished);
        searchTask.execute(urls);
        return searchTask;
    }

    public void cancel(){
        cancel(true);
    }

    @Override
    protected ArrayList<String> doInBackground(String... urls) {
        ArrayList<String> interestsNames = new ArrayList<String>();

        URL url = null;
        try {
            url = new URL(urls[0]);


            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(false);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String content = "", line;
            while ((line = rd.readLine()) != null) {
                content += line + "\n";
            }

            JSONObject contentJson = new JSONObject(content);

            JSONObject similarJson = contentJson.getJSONObject("Similar");

            JSONArray resultsJson = similarJson.getJSONArray("Results");

            if(resultsJson.length()>0){
                interestsNames.add(resultsJson.getJSONObject(0).getString("Name"));
                interestsNames.add(resultsJson.getJSONObject(0).getString("Type"));
                /*int length  = resultsJson.length()>1? 1:resultsJson.length();
                for(int i = 0; i < length; i++) {
                    JSONObject currentResult = resultsJson.getJSONObject(i);
                    interestsNames.add(currentResult.getString("Name"));
                }

                Log.d("interestNames", interestsNames.toString());*/
            }

            return interestsNames;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        super.onPostExecute(strings);
        Log.d("onPostExecute","finished");
        if(mOnSearchTaskFinished != null){
            mOnSearchTaskFinished.onSuccess(strings);
        }
    }

    public void setOnSearchTaskFinished(OnSearchTaskFinished onSearchTaskFinished) {
        mOnSearchTaskFinished = onSearchTaskFinished;
    }

    public interface OnSearchTaskFinished{
        public void onSuccess(ArrayList<String> interests);
        public void onError();
    }
}
