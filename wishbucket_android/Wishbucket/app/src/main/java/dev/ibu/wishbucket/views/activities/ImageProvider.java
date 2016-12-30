package dev.ibu.wishbucket.views.activities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dev.ibu.wishbucket.tasks.FetchImageTask;
import dev.ibu.wishbucket.tasks.SearchTask;

/**
 * Created by dzananganic1 on 29/12/2016.
 */
public class ImageProvider {

    public void getImages(ArrayList<ArrayList<String>> interests, FetchImageTask.OnFetchImageTaskFinished onFetchImageTaskFinished){

        ArrayList<String>gameCategory = new ArrayList<String>();
        ArrayList<String>movieCategory = new ArrayList<String>();
        ArrayList<String>bookCategory = new ArrayList<String>();

        gameCategory = interests.get(0);
        movieCategory = interests.get(1);
        bookCategory = interests.get(2);

        for(String gameItem: gameCategory){
            String url = "http://www.pixplorer.co.uk/image?word="+gameItem+"+cover";
            url = url.replace(" ", "+");
            FetchImageTask.createTask(onFetchImageTaskFinished,url,gameItem, "game");
        }

        for(String movieItem: movieCategory){
            String url = "http://www.pixplorer.co.uk/image?word="+movieItem+"+cover";
            url = url.replace(" ", "+");
            FetchImageTask.createTask(onFetchImageTaskFinished,url,movieItem, "movie");
        }

        for(String bookItem: bookCategory){
            String url = "http://www.pixplorer.co.uk/image?word="+bookItem+"+cover";
            url = url.replace(" ", "+");
            FetchImageTask.createTask(onFetchImageTaskFinished,url,bookItem, "book");
        }



        /*for(ArrayList<String> subInterests : interests) {
            if(subInterests.size()>0){


                String currentInterest = subInterests.get(0);

                String url = "http://www.pixplorer.co.uk/image?word="+currentInterest+"+cover";
                url = url.replace(" ", "+");
                FetchImageTask.createTask(onFetchImageTaskFinished,url,currentInterest, "game");
            }
        }*/
    }
}
