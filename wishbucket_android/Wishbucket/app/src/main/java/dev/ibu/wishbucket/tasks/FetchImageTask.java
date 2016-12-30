package dev.ibu.wishbucket.tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by dzananganic1 on 29/12/2016.
 */
public class FetchImageTask extends AsyncTask<String,Void,String[]> {
    private OnFetchImageTaskFinished mOnFetchImageTaskFinished;

    public static FetchImageTask createTask(OnFetchImageTaskFinished mOnFetchImageTaskFinished,String... urls){
        FetchImageTask fetchImageTask = new FetchImageTask();
        fetchImageTask.setOnFetchImageTaskFinished(mOnFetchImageTaskFinished);
        fetchImageTask.execute(urls);
        return fetchImageTask;
    }

    public void cancel(){
        cancel(true);
    }

    @Override
    protected String[] doInBackground(String... urls) {
        ArrayList<String> imageUrls = new ArrayList<String>();

        URL url = null;
        String interestName = "";
        String interestType = "";
        try {
            url = new URL(urls[0]);
            interestName = urls[1];
            interestType = urls[2];

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


            int beginIndex = content.indexOf("http");
            int endIndex = content.indexOf("word")-3;

            String contentUrl = content.substring(beginIndex, endIndex);

            String[] interestImagePair = new String[3];
            interestImagePair[0] = interestName;
            interestImagePair[1] = contentUrl;
            interestImagePair[2] = interestType;

            return interestImagePair;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String[] strings) {
        super.onPostExecute(strings);
        Log.d("onPostExecute","finished");
        if(mOnFetchImageTaskFinished != null){
            mOnFetchImageTaskFinished.onImageSuccess(strings);
        }
    }

    public void setOnFetchImageTaskFinished(OnFetchImageTaskFinished onSearchTaskFinished) {
        mOnFetchImageTaskFinished = onSearchTaskFinished;
    }

    public interface OnFetchImageTaskFinished{
        public void onImageSuccess(String[] interests);
        public void onError();
    }
}
