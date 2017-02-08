package com.compunet.stuforia;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewsFeedActivity extends ActionBarActivity {
    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    ProgressDialog dialog = null;
    String myJSON="";
    String newsfeed_details[][];
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    ListView newsfeedlist;
    NewsFeedAdapter newsFeedAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        newsfeedlist = (ListView) findViewById(R.id.newsfeedlist);
            new LoadingNewsFeed().execute("");

    }


    public void show() {
        try {

            JSONArray mJsonArray = new JSONArray(myJSON);
            JSONObject mJsonObject = new JSONObject();
            newsfeed_details=new String[mJsonArray.length()][3];
            for (int i = 0; i <mJsonArray.length(); i++) {
                mJsonObject = mJsonArray.getJSONObject(i);
                newsfeed_details[i][0]=mJsonObject.getString("id");
                newsfeed_details[i][1]=mJsonObject.getString("heading");
                newsfeed_details[i][2]=mJsonObject.getString("content");
            }
            newsFeedAdapter=new NewsFeedAdapter(this,newsfeed_details);
            newsfeedlist.setAdapter(newsFeedAdapter);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_payment, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class LoadingNewsFeed extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute()
        {
            dialog = ProgressDialog.show(NewsFeedActivity.this, "",
                    "Loading ...", true);
        }
        @Override
        protected String doInBackground(String... params) {
            String responseStr="";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                 httppost = new HttpPost("http://www.venbaventures.com/stuforia/news_feed.php");
                 response=httpclient.execute(httppost);
                 ResponseHandler<String> responseHandler = new BasicResponseHandler();
                 final String response = httpclient.execute(httppost, responseHandler);
                 myJSON = response.toString();
            } catch (Exception ex) {
                ex.printStackTrace();
                myJSON="networkerror";
            }
            return myJSON;
        }
        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            if(result.equals("networkerror")){
                Toast.makeText(NewsFeedActivity.this, "Couldn't connect. Make sure that your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                show();
            }
        }

        }
}

