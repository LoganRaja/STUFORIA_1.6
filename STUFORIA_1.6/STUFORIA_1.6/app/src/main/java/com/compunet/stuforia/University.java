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
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class University extends ActionBarActivity {
    ListView listView1;
    HttpPost httppost;

    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    ProgressDialog dialog = null;
    String myJSON="", myJSON1="";

    String[][] myStringArray ;
    String[][] searchuniversity;
    String[] universityname;
    String pause="pause";


    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter adapter;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    String pagination;
    AutoCompleteTextView text;
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    String search=null;
    LinearLayout filter,userhint;
   int universitytotal=0;
    int total=0,scrollpostion;
    int c=0;
   String shortlisted_univ;
    String[] usershortlisted_univ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.university_main);
        Intent intent1=getIntent();
        search=intent1.getStringExtra("search");
        filter=(LinearLayout)findViewById(R.id.filter);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
         shortlisted_univ = prefs.getString("shortlisted_univ", null);
        if(!prefs.getString("id", null).equals("skip_for_now"))
        {
            usershortlisted_univ = shortlisted_univ.split("\\,");
            userhint=(LinearLayout)findViewById(R.id.userhint);
            userhint.setVisibility(View.VISIBLE);
        }


        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(University.this, FilterSearch.class);
                startActivity(intent);
            }
        });
        text=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d5da7")));
        if(search==null) {
            nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("to","0"));
            pagination="university";
            Log.e("University Java","Entered");
            new  LoadForSearch().execute("");
            new LoadUniversitises().execute("");
        }
        else {
            myJSON=search;
            filter.setVisibility(View.GONE);
            String str=intent1.getStringExtra("proximity");
            pagination="filter";
            show(str);
        }
    }
void  refersh(int refresh)
{
    if(universitytotal!=total) {
        nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("to",Integer.toString(total /15)));
        new LoadUniversitises().execute("");
    }
}
    public void show(String str) {
        try {

                JSONArray mJsonArray = new JSONArray(myJSON);
                JSONObject mJsonObject = new JSONObject();
           // Toast.makeText(University.this, "page number"+mJsonArray.length(), Toast.LENGTH_SHORT).show();
            if(c!=0) {
                String[][] temp = new String[myStringArray.length + mJsonArray.length()][10];
                System.arraycopy(myStringArray, 0, temp, 0, myStringArray.length);
                myStringArray = temp;
                mJsonObject = mJsonArray.getJSONObject(0);
                myStringArray[c][0] = mJsonObject.getString("id");
            }
        else{
                myStringArray=new String[mJsonArray.length()][10];
                universityname=new String[mJsonArray.length()];
            }

                for (int i = 0; i <mJsonArray.length(); i++) {
                    mJsonObject = mJsonArray.getJSONObject(i);
                    if( pagination.equals("university"))
                    {
                        total= myStringArray.length;
                        universitytotal=Integer.parseInt( mJsonObject.getString("count"));
                    }

                    myStringArray[c][0] = mJsonObject.getString("id");
                    myStringArray[c][1] = mJsonObject.getString("university_name");
                    myStringArray[c][2] = mJsonObject.getString("address");
                    myStringArray[c][3] = mJsonObject.getString("latitude");
                    myStringArray[c][4] = mJsonObject.getString("longitude");
                    myStringArray[c][5] = mJsonObject.getString("logo_file");
                    if(!prefs.getString("id", null).equals("skip_for_now"))
                    for(int j=0;j<usershortlisted_univ.length;j++)
                    {
                        if(myStringArray[c][0].equals(usershortlisted_univ[j])){
                            myStringArray[c][6]="remove";
                            break;
                        }
                        else{
                            myStringArray[c][6]="add";
                        }
                    }
                    else{
                        myStringArray[c][6]="add";
                    }
                    if (str.equals("1")) {
                        myStringArray[c][7] = mJsonObject.getString("distance");
                    }
                    if(!pagination.equals("university"))
                        universityname[c]=mJsonObject.getString("university_name")+"( "+mJsonObject.getString("university_short_form")+" )";
                    c++;
                   }
            if(!pagination.equals("university")) {
                ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, universityname);
                text.setAdapter(adapter);
                text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        for (int i = 0; i < universityname.length; i++) {
                            if (universityname[i].equals(text.getText().toString())) {
                                Intent intent = new Intent(University.this, MapActivity.class);
                                intent.putExtra("string", myStringArray[i]);
                                startActivity(intent);
                                break;
                            }
                        }
                    }
                });
                text.setThreshold(1);
            }

            adapter = new MyRecyclerAdapter(this, myStringArray,pagination);
            mRecyclerView.setAdapter(adapter);

            if(pagination.equals("university"))
               mRecyclerView.scrollToPosition(myStringArray.length-16);
        }
        catch (JSONException e)
        {
        Toast.makeText(University.this, "No universities found...Please go back and refine your search.", Toast.LENGTH_SHORT).show();
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
    private class LoadUniversitises extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute()
        {
            dialog = ProgressDialog.show(University.this, "",
                    "Loading Universities...", true);
            /*dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);*/
        }
        @Override
        protected String doInBackground(String... params) {


            StrictMode.setThreadPolicy(policy);

            try{
                httpclient=new DefaultHttpClient();
                httppost= new HttpPost("http://www.venbaventures.com/stuforia/university_details_bk.php");// make sure the// is correct.
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                response=httpclient.execute(httppost);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                final String response = httpclient.execute(httppost, responseHandler);
                System.out.println("Response : " + response);

                myJSON = response.toString();


            }catch(Exception e){
                dialog.dismiss();
                myJSON="internetFailed";
               // Toast.makeText(University.this,"Exception",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }


            return myJSON;
        }
        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            if(result.equals("internetFailed")){
                Toast.makeText(University.this, "Couldn't connect. Make sure that your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();
            }else{
                show("0");
            }



        }
        @Override
        protected void onProgressUpdate(Void... values) {
        }

    }

    private class LoadForSearch extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute()
        {


        }
        @Override
        protected String doInBackground(String... params) {


            StrictMode.setThreadPolicy(policy);

            try{
                httpclient=new DefaultHttpClient();
                httppost= new HttpPost("http://www.venbaventures.com/stuforia/university_details.php");// make sure the// is correct.
                response=httpclient.execute(httppost);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                final String response = httpclient.execute(httppost, responseHandler);
                System.out.println("Response : " + response);
                myJSON1 = response.toString();
            }
            catch(Exception e)
            {
               myJSON1="internetFailed";
              //  Toast.makeText(University.this,"Exception",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return myJSON1;
        }
        @Override
        protected void onPostExecute(String result) {
            if(result.equals("internetFailed")){
                Toast.makeText(University.this, "Couldn't connect. Make sure that your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();
            }
            else
            try{
                JSONArray mJsonArray = new JSONArray(result);
                JSONObject mJsonObject = new JSONObject();
                searchuniversity=new String[mJsonArray.length()][10];
                universityname=new String[mJsonArray.length()];
                for (int i = 0; i <mJsonArray.length(); i++) {
                   mJsonObject = mJsonArray.getJSONObject(i);
                    searchuniversity[i][0] = mJsonObject.getString("id");
                    searchuniversity[i][1] = mJsonObject.getString("university_name");
                    searchuniversity[i][2] = mJsonObject.getString("address");
                    searchuniversity[i][3] = mJsonObject.getString("latitude");
                    searchuniversity[i][4] = mJsonObject.getString("longitude");
                    searchuniversity[i][5] = mJsonObject.getString("logo_file");
                    universityname[i]=mJsonObject.getString("university_name")+"( "+mJsonObject.getString("university_short_form")+" )";
                    if(!prefs.getString("id", null).equals("skip_for_now"))
                    for(int j=0;j<usershortlisted_univ.length;j++)
                    {
                        if(searchuniversity[i][0].equals(usershortlisted_univ[j])){
                            searchuniversity[i][6]="remove";
                            break;
                        }
                        else{
                            searchuniversity[i][6]="add";
                        }
                    }
                }
                }
                catch(Exception e){

                }
                ArrayAdapter adapter = new ArrayAdapter(University.this, android.R.layout.simple_list_item_1, universityname);
                text.setAdapter(adapter);
                text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int pos = text.getText().toString().lastIndexOf("(");
                        String output =text.getText().toString().substring(pos , text.getText().toString().length());
                        output=text.getText().toString().replace(output,"");
                        for (int i = 0; i < searchuniversity.length; i++) {
                            if (searchuniversity[i][1].equals(output)) {
                                Intent intent = new Intent(University.this, MapActivity.class);
                                intent.putExtra("string", searchuniversity[i]);
                                startActivity(intent);
                                break;
                            }
                        }
                    }
                });
            text.setThreshold(1);

        }
        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}

