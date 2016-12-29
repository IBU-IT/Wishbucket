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

import java.io.IOException;
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
        Document doc;
        ArrayList<String> interestsNames = new ArrayList<String>();

        try {
            doc = Jsoup.connect(urls[0]).userAgent("Mozilla/5.0").timeout(5000).get();

            Elements links = doc.select("div.kltat > span");

            for (Element link : links) {

                Elements titles = link.select("span");

                String title = titles.text();

                interestsNames.add(link.text());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return interestsNames;
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
