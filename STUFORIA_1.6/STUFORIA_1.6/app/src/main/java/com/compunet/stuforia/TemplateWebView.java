package com.compunet.stuforia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.compunet.stuforia.R;

/**
 * Created by connectors on 10/1/2015.
 */
public class TemplateWebView extends Activity {
    String pdf,advertisement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.templatewebview);
        WebView webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        Intent intent1=getIntent();
        pdf =intent1.getStringExtra("viewpdf");
        advertisement=intent1.getStringExtra("advertisement");
        if(pdf!=null)
        webview.loadUrl("http://docs.google.com/gview?embedded=true&url=" + pdf);
        if(advertisement!=null)
            webview.loadUrl("https://www.chennaishopping.com/cracking-the-gre-2016-edition/book/16725/");
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });

        //finish();
    }

    @Override
    public  void onBackPressed()
    {
        finish();
    }
}
