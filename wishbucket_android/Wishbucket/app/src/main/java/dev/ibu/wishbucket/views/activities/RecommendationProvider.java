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

/**
 * Created by dzananganic1 on 28/12/2016.
 */

public class RecommendationProvider {

    public ArrayList<String> getGameRecommendations(JSONObject gameInterests){
        try {
            JSONArray interestsData = gameInterests.getJSONArray("data");

            for(int i = 0; i < interestsData.length(); i++) {
                JSONObject currentInterest = interestsData.getJSONObject(i);

                String currentInterestName = currentInterest.getString("name");

                String url = "https://www.google.ba/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=games%20like%20";

                currentInterestName = currentInterestName.replace(" ", "%20");
                url += currentInterestName;

                new Thread() {
                    @Override
                    public void run() {
                        Document doc;

                        try {
                            doc = Jsoup.connect("https://www.google.com/search?as_q=&as_epq=%22Yorkshire+Capital%22+&as_oq=fraud+OR+allegations+OR+scam&as_eq=&as_nlo=&as_nhi=&lr=lang_en&cr=countryCA&as_qdr=all&as_sitesearch=&as_occt=any&safe=images&tbs=&as_filetype=&as_rights=").userAgent("Mozilla").ignoreHttpErrors(true).timeout(0).get();

                            Elements links = doc.select("div[class=kltat]");

                            ArrayList<String> interestsNames = new ArrayList<String>();

                            for (Element link : links) {

                                Elements titles = link.select("span");

                                String title = titles.text();

                                interestsNames.add(title);
                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();





            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public JSONArray getMovieRecommendations(JSONObject movieInterests){

        return null;
    }

    public JSONArray getBookRecommendations(JSONObject bookInterests){

        return null;
    }
}
