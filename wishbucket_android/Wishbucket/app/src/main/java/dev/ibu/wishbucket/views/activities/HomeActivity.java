package dev.ibu.wishbucket.views.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import dev.ibu.wishbucket.R;
import dev.ibu.wishbucket.models.FBUser;
import dev.ibu.wishbucket.tasks.DownloadAndSetImageTask;

public class HomeActivity extends AppCompatActivity {
    final static String USERID_KEY = "USERID_KEY";

    private AccessToken accessToken;
    private ArrayList<FBUser> fbUsers;
    private String rawResponse;

    // JUST A SAMPLE RESPONSE
    private String rawResponseTest = "{ \"data\" : [ {\"birthday\" : \"09/3/1997\", \n" +
            "        \"id\" : \"991778467507780\",\n" +
            "        \"name\" : \"Kemal Mustafic\",\n" +
            "        \"picture\" : { \"data\" : { \"is_silhouette\" : false,\n" +
            "                \"url\" : \"https://scontent.fbeg2-1.fna.fbcdn.net/v/t1.0-1/p320x320/1535438_719852851367011_523618052_n.jpg?oh=0b381e293f674aa480c8afb4c91440d0&oe=592480EB\"\n" +
            "              } }\n" +
            "      },\n" +
            "      { \"birthday\" : \"10/10/1997\",\n" +
            "        \"id\" : \"609557192483402\",\n" +
            "        \"name\" : \"Dzanan Ganic\",\n" +
            "        \"picture\" : { \"data\" : { \"is_silhouette\" : false,\n" +
            "                \"url\" : \"https://scontent.fbeg2-1.fna.fbcdn.net/v/t1.0-1/p320x320/10358866_724200771019043_7719618574828729952_n.jpg?oh=c60780a3fe9512a4855d1da4047b7268&oe=58DB4C84\"\n" +
            "              } }\n" +
            "      },\n" +
            "      { \"birthday\" : \"07/25/1994\",\n" +
            "        \"id\" : \"1657271004556375\",\n" +
            "        \"name\" : \"Bećir Isakovic\",\n" +
            "        \"picture\" : { \"data\" : { \"is_silhouette\" : false,\n" +
            "                \"url\" : \"https://scontent.fbeg2-1.fna.fbcdn.net/v/t31.0-8/14939941_1828508317432642_1695792081257066790_o.jpg?oh=dc0d7384657ad3301828ba0a0eb26e03&oe=58E32CC2\"\n" +
            "              } }\n" +
            "      },\n" +
            "      { \"birthday\" : \"10/1/1995\",\n" +
            "        \"id\" : \"100000026280931\",\n" +
            "        \"name\" : \"Amil Cohadzic\",\n" +
            "        \"picture\" : { \"data\" : { \"is_silhouette\" : false,\n" +
            "                \"url\" : \"https://scontent.fbeg2-1.fna.fbcdn.net/v/t31.0-1/c85.156.576.576/s320x320/13403258_1204760626201469_8236912033376312545_o.jpg?oh=09da890d016f47653bf02d9497605af0&oe=5920E166\"\n" +
            "              } }\n" +
            "      },\n" +
            "      { \"birthday\" : \"09/09/1996\",\n" +
            "        \"id\" : \"1657271004556375\",\n" +
            "        \"name\" : \"Haris Botic\",\n" +
            "        \"picture\" : { \"data\" : { \"is_silhouette\" : false,\n" +
            "                \"url\" : \"https://scontent.fbeg2-1.fna.fbcdn.net/v/t1.0-1/p320x320/13006494_100257377052409_3635235463199482252_n.jpg?oh=02b854441a8d93cc3551ee39143ba525&oe=592087E5\"\n" +
            "              } }\n" +
            "      }\n" +
            "    ]\n" +
            "}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fbUsers = new ArrayList<>();

        Intent intent = getIntent();
        accessToken = intent.getParcelableExtra(MainActivity.ACCESS_TOKEN_KEY);

        callApi();
    }

    private void callApi() {
        GraphRequest request = GraphRequest.newMyFriendsRequest(
                accessToken,
                new GraphRequest.GraphJSONArrayCallback() {
                    @Override
                    public void onCompleted(JSONArray objects, GraphResponse response) {
                        //parseFBUsers(response.getRawResponse());
                        parseFBUsers(rawResponseTest);
                        displayFriends();
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,birthday,picture");
        parameters.putString("limit", "10");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void parseFBUsers(String jsonString) {
        try {
            JSONArray jsonArray = new JSONObject(jsonString).getJSONArray("data");
            for(int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject data = jsonArray.getJSONObject(i);
                String id = data.has("id")? data.getString("id") : null;
                String name = data.has("name")? data.getString("name") : null;
                String birthday = data.has("birthday")? data.getString("birthday") : null;

                String cover = null;
                if(data.has("picture")) {
                    JSONObject pictureObj = data.getJSONObject("picture");
                    if(pictureObj.has("data")) {
                        JSONObject pDataObj = pictureObj.getJSONObject("data");
                        cover = pDataObj.has("url") ? pDataObj.getString("url") : null;
                    }
                }

                fbUsers.add(new FBUser(id, name, birthday, cover));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void displayFriends() {
        sortFBUsers();

        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);
        for (final FBUser u: fbUsers) {
            RelativeLayout card = (RelativeLayout) getLayoutInflater().inflate(R.layout.fbuser_card, null);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HomeActivity.this, RecommendationActivity.class);
                    intent.putExtra(USERID_KEY, u.id);
                    startActivity(intent);
                }
            });

            TextView name = (TextView) card.findViewById(R.id.name);
            name.setText(u.name);

            TextView birthday = (TextView) card.findViewById(R.id.birthday);
            birthday.setText(u.getDaysUntilBirthday());

            ImageView cover = (ImageView) card.findViewById(R.id.cover);
            if(u.coverUrl != null)
                new DownloadAndSetImageTask(cover).execute(u.coverUrl);

            layout.addView(card);
        }
    }

    private void sortFBUsers() {
        Collections.sort(fbUsers, new Comparator<FBUser>() {
            @Override
            public int compare(FBUser fbUser, FBUser t1) {
                return fbUser.daysUntilBirthday - t1.daysUntilBirthday;
            }
        });
    }
}


