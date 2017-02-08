package com.compunet.stuforia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.compunet.stuforia.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShortListedUniversity extends ActionBarActivity {
    ListView listView1;
    HttpPost httppost;
    StringBuffer buffer;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    ProgressDialog dialog = null;
    String myJSON;
    private static final String TAG_Res="r";
    String[][] myStringArray ;
    JSONArray peoples = null;
    String[] languages;
    String[] images;
    AutoCompleteTextView text;
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    String pagination;
    String university[];
    LinearLayout filter;
    String pause="pause";
    int scrollpostion;
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter adapter;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String shortlisted_univ;
    String[] usershortlisted_univ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.university_main);


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        text=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        filter=(LinearLayout) findViewById(R.id.filter);
        filter.setVisibility(View.GONE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
        shortlisted_univ = prefs.getString("shortlisted_univ", null);
            usershortlisted_univ = shortlisted_univ.split("\\,");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d5da7")));
        pagination="short listed university";
        dialog = ProgressDialog.show(ShortListedUniversity.this, "",
                "Loading Universities...", true);
       new Thread(new Runnable() {
            public void run() {
                load();
            }
        }).start();

    }

    void load(){
        try{
            httpclient=new DefaultHttpClient();
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("id", prefs.getString("id", null)));
            httppost= new HttpPost("http://www.venbaventures.com/stuforia/update_shortlisted_university.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response=httpclient.execute(httppost);
            String responseStr = EntityUtils.toString(response.getEntity());
            final String output = responseStr.replaceAll("\\s+", "");
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost, responseHandler);
            runOnUiThread(new Runnable() {
                public void run() {
                    if(output.equals("0"))
                        Toast.makeText(ShortListedUniversity.this,"You have no shortlisted Universities...", Toast.LENGTH_LONG).show();
                    else {
                        myJSON = response.toString();
                        show();
                    }

                    dialog.dismiss();
                    //Toast.makeText(ShortListedUniversity.this, "check" + myJSON, Toast.LENGTH_LONG).show();
                }
            });

        }catch(Exception e){
            dialog.dismiss();
            myJSON="internetFailed";
          //  Toast.makeText(ShortListedUniversity.this,"Exception"+e,Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

        public void show() {
            if(myJSON.equals("internetFailed")){
                Toast.makeText(ShortListedUniversity.this, "Couldn't connect. Make sure that your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();
            }
            else
        try {


            JSONArray mJsonArray = new JSONArray(myJSON);
            JSONObject mJsonObject = new JSONObject();

            myStringArray=new String[mJsonArray.length()][10];
            languages=new String[mJsonArray.length()];
            for (int i = 0; i < mJsonArray.length(); i++) {
                mJsonObject = mJsonArray.getJSONObject(i);
                myStringArray[i][0]=mJsonObject.getString("id");
                myStringArray[i][1]= mJsonObject.getString("university_name");
                myStringArray[i][2]= mJsonObject.getString("address");
                myStringArray[i][3]= mJsonObject.getString("latitude");
                myStringArray[i][4]= mJsonObject.getString("longitude");
                myStringArray[i][5]= mJsonObject.getString("logo_file");
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
             //   myStringArray[i][10]=mJsonObject.getString("university_logo");
               languages[i]= mJsonObject.getString("university_name");
            }
            adapter = new MyRecyclerAdapter(this, myStringArray,pagination);
            mRecyclerView.setAdapter(adapter);
            ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,languages);
            text.setAdapter(adapter);
            text.setThreshold(1);
            text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    for (int i = 0; i < languages.length; i++) {
                        if (languages[i].equals(text.getText().toString())) {
                            Intent intent = new Intent(ShortListedUniversity.this, MapActivity.class);
                            intent.putExtra("string", myStringArray[i]);
                            startActivity(intent);
                            break;
                        }
                    }
                }
            });
        }
        catch (JSONException e)
        {
           // Toast.makeText(ShortListedUniversity.this,  e, Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onResume()
    {
        super.onResume();
        if(pause.equals("resume")) {
            pause="pause";
            shortlisted_univ = prefs.getString("shortlisted_univ", null);
            if(!prefs.getString("id", null).equals("skip_for_now"))
                usershortlisted_univ = shortlisted_univ.split("\\,");
            int count=0;
            String temp[][];
            for(int j=0;j<myStringArray.length;j++)
            {
                for(int i=0;i<usershortlisted_univ.length;i++) {
                    if (myStringArray[j][0].equals(usershortlisted_univ[i])) {
                        myStringArray[j][6] = "remove";
                        count ++;
                        break;
                    }
                    else {
                        myStringArray[j][6] = "add";
                    }
                }
            }
            temp=new String[count][10];
            int j=0;
            for(int i=0;i<myStringArray.length;i++)
            {
               if(myStringArray[i][6].equals("remove"))
               {
                   temp[j]=myStringArray[i];
                   j++;
               }
            }
            myStringArray = temp;
           myStringArray = temp;
            adapter = new MyRecyclerAdapter(this, myStringArray,pagination);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.scrollToPosition(scrollpostion);
            Log.e("shortlisted_univ",Integer.toString(myStringArray.length));
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

