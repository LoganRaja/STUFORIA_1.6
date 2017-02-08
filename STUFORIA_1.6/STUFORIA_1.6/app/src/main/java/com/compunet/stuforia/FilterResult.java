package com.compunet.stuforia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FilterResult extends ActionBarActivity {
    ListView listView1;
    HttpPost httppost;

    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    ProgressDialog dialog = null;
    String myJSON="";

    String[][] myStringArray ;
    String[] universityname;
    String pause="pause";


    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter adapter;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    String pagination="filterresult";
    AutoCompleteTextView text;
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    String search=null;
    LinearLayout filter;
    int scrollpostion;
    String str="not_proximity";
   String shortlisted_univ;
    String[] usershortlisted_univ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.university_main);
        Intent intent1 = getIntent();
        search = intent1.getStringExtra("search");
        filter = (LinearLayout) findViewById(R.id.filter);


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
        shortlisted_univ = prefs.getString("shortlisted_univ", null);
        if (!prefs.getString("id", null).equals("skip_for_now"))
            usershortlisted_univ = shortlisted_univ.split("\\,");
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilterResult.this, FilterSearch.class);
                startActivity(intent);
                finish();
            }
        });
        text = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d5da7")));
        myJSON = search;
        filter.setVisibility(View.GONE);
        String str="not_proximity";
         str = intent1.getStringExtra("proximity");
        show();

    }
    public void show() {
        try {

                JSONArray mJsonArray = new JSONArray(myJSON);
                JSONObject mJsonObject = new JSONObject();
            myStringArray=new String[mJsonArray.length()][10];
            universityname=new String[mJsonArray.length()];
                for (int i = 0; i <mJsonArray.length(); i++) {
                    mJsonObject = mJsonArray.getJSONObject(i);
                    myStringArray[i][0] = mJsonObject.getString("id");
                    universityname[i]= myStringArray[i][1] = mJsonObject.getString("university_name");
                    myStringArray[i][2] = mJsonObject.getString("address");
                    myStringArray[i][3] = mJsonObject.getString("latitude");
                    myStringArray[i][4] = mJsonObject.getString("longitude");
                    myStringArray[i][5] = mJsonObject.getString("logo_file");
                    if(!prefs.getString("id", null).equals("skip_for_now"))
                    for(int j=0;j<usershortlisted_univ.length;j++)
                    {
                        if(myStringArray[i][0].equals(usershortlisted_univ[j])){
                            myStringArray[i][6]="remove";
                            break;
                        }
                        else{
                            myStringArray[i][6]="add";
                        }
                    }
                    if (str.equals("1")) {
                        myStringArray[i][7] = mJsonObject.getString("distance");
                    }
                   }

            adapter = new MyRecyclerAdapter(this, myStringArray,pagination);
            mRecyclerView.setAdapter(adapter);
        }
        catch (JSONException e)
        {
        Toast.makeText(FilterResult.this, "No universities found...Please go back and refine your search.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    @Override
    public void onResume()
    {
        super.onResume();
        if(pause.equals("resume")) {
            pause="pause";
            shortlisted_univ = prefs.getString("shortlisted_univ", null);
            if(!prefs.getString("id", null).equals("skip_for_now")) {
                usershortlisted_univ = shortlisted_univ.split("\\,");
                for (int j = 0; j < myStringArray.length; j++) {
                    for (int i = 0; i < usershortlisted_univ.length; i++) {
                        if (myStringArray[j][0].equals(usershortlisted_univ[i])) {
                            myStringArray[j][6] = "remove";
                            break;
                        } else {
                            myStringArray[j][6] = "add";
                        }
                    }
                }
                adapter = new MyRecyclerAdapter(this, myStringArray, pagination);
                mRecyclerView.setAdapter(adapter);
                mRecyclerView.scrollToPosition(scrollpostion);
                Log.e("shortlisted_univ", shortlisted_univ);
            }
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        pause="resume";
    }

public void resumepause(int postion)
{
    scrollpostion=postion;
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
}

