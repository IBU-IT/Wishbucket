package dev.ibu.wishbucket.views.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dev.ibu.wishbucket.R;
import dev.ibu.wishbucket.tasks.FetchImageTask;
import dev.ibu.wishbucket.tasks.SearchTask;

public class RecommendationActivity extends AppCompatActivity implements SearchTask.OnSearchTaskFinished, FetchImageTask.OnFetchImageTaskFinished {

    private AccessToken accessToken;
    private SearchTask mSearchTask;
    private FetchImageTask mFetchImageTask;
    public static ArrayList<ArrayList<String>> allInterests;
    public static HashMap<String, String> interestsImages = new HashMap<String, String>();
    private LinearLayout rowsContainer;
    public static int categoryCounter = 0;
    public static int limit = 999;
    public static ArrayList<String> bookInterests;
    public static ArrayList<String> gameInterests;
    public static ArrayList<String> movieInterests;

    public static HashMap<String, String> gameInterestsImages = new HashMap<String, String>();
    public static HashMap<String, String> movieInterestsImages = new HashMap<String, String>();
    public static HashMap<String, String> bookInterestsImages = new HashMap<String, String>();


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

            rp.getRecommendations("books", books, this);
            rp.getRecommendations("movies", movies, this);
            rp.getRecommendations("games", games, this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getUserInterests(String userId){
        GraphRequest.newGraphPathRequest(accessToken,
                userId+"?fields=books,movies,games&limit=5",
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

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    private void fetchImages(){
        ImageProvider ip= new ImageProvider();

        ArrayList<ArrayList<String>>all = new ArrayList<ArrayList<String>>();


        all.add(gameInterests);
        all.add(movieInterests);
        all.add(bookInterests);

        /*ArrayList<String>tmp = new ArrayList<String>();

        for(int i=0; i<allInterests.size(); i++){
            tmp = new ArrayList<String>();
            if(allInterests.get(i)!=null){
                for(int j=0; j<allInterests.get(i).size(); j++){
                    tmp.add(allInterests.get(i).get(j));
                }
                all.add(tmp);
            }
        }*/

        limit = gameInterests.size() + movieInterests.size() + bookInterests.size();

        ip.getImages(all, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        rowsContainer = (LinearLayout) findViewById(R.id.rowsContainer);

        rowsContainer.removeAllViews();

        bookInterests = new ArrayList<String>();
        gameInterests = new ArrayList<String>();
        movieInterests = new ArrayList<String>();

        /*HashMap<String, String> cat1Dataset = new HashMap<>();
        cat1Dataset.put("Battlefield 1.1", "http://image.shopon.pk/cache/data/product-23534/1-500x500.jpg");
        cat1Dataset.put("Battlefield 1.2", "http://image.shopon.pk/cache/data/product-23534/1-500x500.jpg");
        cat1Dataset.put("Battlefield 1.3", "http://image.shopon.pk/cache/data/product-23534/1-500x500.jpg");
        cat1Dataset.put("Battlefield 1.4", "http://image.shopon.pk/cache/data/product-23534/1-500x500.jpg");
        cat1Dataset.put("Battlefield 1.4", "http://image.shopon.pk/cache/data/product-23534/1-500x500.jpg");

        HashMap<String, String> cat2Dataset = new HashMap<>();
        cat2Dataset.put("Battlefield 2.1", "http://image.shopon.pk/cache/data/product-23534/1-500x500.jpg");
        cat2Dataset.put("Battlefield 2.2", "http://image.shopon.pk/cache/data/product-23534/1-500x500.jpg");
        cat2Dataset.put("Battlefield 2.3", "http://image.shopon.pk/cache/data/product-23534/1-500x500.jpg");
        cat2Dataset.put("Battlefield 2.4", "http://image.shopon.pk/cache/data/product-23534/1-500x500.jpg");
        cat2Dataset.put("Battlefield 2.5", "http://image.shopon.pk/cache/data/product-23534/1-500x500.jpg");

        HashMap<String, String> cat3Dataset = new HashMap<>();
        cat3Dataset.put("Battlefield 3.1", "http://image.shopon.pk/cache/data/product-23534/1-500x500.jpg");
        cat3Dataset.put("Battlefield 3.2", "http://image.shopon.pk/cache/data/product-23534/1-500x500.jpg");
        cat3Dataset.put("Battlefield 3.3", "http://image.shopon.pk/cache/data/product-23534/1-500x500.jpg");
        cat3Dataset.put("Battlefield 3.4", "http://image.shopon.pk/cache/data/product-23534/1-500x500.jpg");
        cat3Dataset.put("Battlefield 3.5", "http://image.shopon.pk/cache/data/product-23534/1-500x500.jpg");

        rowsContainer.removeAllViews();
        addRow("Category 1", cat1Dataset);
        addRow("Category 2", cat2Dataset);
        addRow("Category 3", cat3Dataset);*/




        allInterests = new ArrayList<ArrayList<String>>();

        getAccessToken();
        setUpProfilePicture();

        //TODO: put userId from homeActivity here
        getUserInterests("1046723582100092");
    }

    private void addRow(String category, HashMap<String, String> dataset) {
        ViewGroup row = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.item_row, rowsContainer, false);

        /* Row Title */
        TextView rowCategory = (TextView) row.findViewById(R.id.rowTitle);
        rowCategory.setText(category);

        /* Cards */
        LinearLayout cardsContainer = (LinearLayout) row.findViewById(R.id.cardsContainer);
        if (cardsContainer != null) {
            for (Map.Entry<String, String> p : dataset.entrySet()) {
                ViewGroup card = (ViewGroup) LayoutInflater.from(RecommendationActivity.this).inflate(R.layout.item_card, cardsContainer, false);

                ImageView image = (ImageView) card.findViewById(R.id.image);
                TextView title = (TextView) card.findViewById(R.id.title);

                Picasso.with(this).load(p.getValue()).into(image);

                title.setText(p.getKey());

                cardsContainer.addView(card);
            }
        }

        rowsContainer.addView(row);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchTask.cancel();
        mFetchImageTask.cancel();
    }

    @Override
    public void onImageSuccess(String[] images){
        //on index 0 interest, on index 1 image, on index 2 type

        //key is interest, value is interest

        if(images[2]=="game"){
            gameInterestsImages.put(images[0], images[1]);
        } else if(images[2]=="movie"){
            movieInterestsImages.put(images[0], images[1]);
        } else {
            bookInterestsImages.put(images[0], images[1]);
        }

        categoryCounter++;

        if(categoryCounter==limit){
            addRow("Games", gameInterestsImages);
            addRow("Movies", movieInterestsImages);
            addRow("Books", bookInterestsImages);
        }


        //interestsImages.put(images[0], images[1]);


       // if(interestsImages.size()==3 || (interestsImages.size()==2 && categoryCounter==2)){
        //    addRow("Category", interestsImages);
        //    categoryCounter++;
        //    interestsImages = new HashMap<String, String>();
        //}


        //Log.d("cii size", interestsImages.size()+"");

    }

    @Override
    public void onSuccess(ArrayList<String> interests) {

        if(interests!=null && interests.size()>0){
            if(interests.get(1).equals("game")){
                gameInterests.add(interests.get(0));
            } else if (interests.get(1).equals("book")){
                bookInterests.add(interests.get(0));
            } else {
                movieInterests.add(interests.get(0));
            }
        }
        allInterests.add(interests);


        Log.d("Koliko enteresa", allInterests.size()+"");
        if(allInterests.size()==45) {
            Log.d("game interesa", gameInterests.size()+"");
            Log.d("book interesa", bookInterests.size()+"");
            Log.d("movie interesa", movieInterests.size()+"");

            Log.d("game interesi", gameInterests.toString());
            Log.d("book interesi", bookInterests.toString());
            Log.d("movie interesi", movieInterests.toString());

            fetchImages();
        }

    }

    @Override
    public void onError() {

    }
}
