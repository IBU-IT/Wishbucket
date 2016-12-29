package dev.ibu.wishbucket.views.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import dev.ibu.wishbucket.R;

public class BuyProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        WebView wV = (WebView)findViewById(R.id.webView);
        String interest = "";
        wV.loadUrl("https://www.amazon.com/s/ref=nb_sb_noss_2/157-2504715-8808307?url=search-alias%3Daps&field-keywords="+interest);

    }

}
