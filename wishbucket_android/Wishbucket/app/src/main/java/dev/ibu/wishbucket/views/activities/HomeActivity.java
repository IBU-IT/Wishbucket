package dev.ibu.wishbucket.views.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequest.GraphJSONObjectCallback;
import com.facebook.GraphResponse;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.Console;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import dev.ibu.wishbucket.R;
import dev.ibu.wishbucket.views.models.FBUser;

public class HomeActivity extends AppCompatActivity {
    final static String USERID_KEY = "USERID_KEY";

    private AccessToken accessToken;
    private ArrayList<FBUser> fbUsers;
    private String rawResponse;
    private String rawResponseTest = "{\"data\":[{\"id\":\"1353906817961608\",\"name\":\"Kemal Mustafic\",\n" +
            "      \"birthday\": \"06/25/1994\"}, {\"id\":\"1353906817961608\",\"name\":\"Kemal Mustafic\",\n" +
            "      \"birthday\": \"06/26/1994\"}, {\"id\":\"1353906817961608\",\"name\":\"Kemal Mustafic\",\n" +
            "      \"birthday\": \"12/31/1994\", \"cover\": {\n" +
            "        \"id\": \"1835369660079841\",\n" +
            "        \"offset_y\": 0,\n" +
            "        \"source\": \"https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/15134544_1835369660079841_3566795614903459210_n.jpg?oh=7aa5d869ca91ddc92ee0248bd1eefbbf&oe=58E0A9E0\"\n" +
            "      }}],\"paging\":{\"cursors\":{\"before\":\"QVFIUkJVV2lxcVlJcW9SenhsWjJRbWdaY0h2VERWVkVCNjJUUTBxb2ltRXlnRDB1b2hCZAml1MFZAxVmxCUnk4b2pYbWQ0UE04X2s0SzlvOU9RMlA5YzZA5Sk9B\",\"after\":\"QVFIUkJVV2lxcVlJcW9SenhsWjJRbWdaY0h2VERWVkVCNjJUUTBxb2ltRXlnRDB1b2hCZAml1MFZAxVmxCUnk4b2pYbWQ0UE04X2s0SzlvOU9RMlA5YzZA5Sk9B\"}},\"summary\":{\"total_count\":77}}";


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
        parameters.putString("fields", "id,name,birthday");
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
                if(data.has("cover")) {
                    JSONObject coverObj = data.getJSONObject("cover");
                    cover = coverObj.has("source") ? coverObj.getString("source") : null;
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
                new DownloadAndSetImageTask(cover).execute("https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/15134544_1835369660079841_3566795614903459210_n.jpg?oh=7aa5d869ca91ddc92ee0248bd1eefbbf&oe=58E0A9E0");

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

    private class DownloadAndSetImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadAndSetImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}


