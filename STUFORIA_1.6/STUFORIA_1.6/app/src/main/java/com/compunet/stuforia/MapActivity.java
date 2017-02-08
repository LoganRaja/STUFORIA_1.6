package com.compunet.stuforia;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapActivity extends FragmentActivity {
private GoogleMap googleMap;
    ToggleButton toggleButton;
    String id;
    String[] ar;

    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    String myJSON,output,tag=null;
    String myStringArray[][];
    String myStringArray1[][];
    List<String> MBA = new ArrayList<String>();
    String[] arrray;
    ProgressDialog dialog = null;
    ProgressDialog dialog1 = null;
    List<String> MS = new ArrayList<String>();
    ImageView logo;
    Animation myAnim;
    Bitmap myBitmap;
    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    LinearLayout pauselayout;
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity_try);
        toggleButton=(ToggleButton)findViewById(R.id.addremove);
        pauselayout=(LinearLayout)findViewById(R.id.pauselayout);
        Intent intent=getIntent();
        arrray=intent.getStringArrayExtra("string");

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();

        id = prefs.getString("id", null);

        new LoadCourseDetails().execute("");
        if(id.equals("skip_for_now"))
            toggleButton.setVisibility(View.GONE);
        else {
            toggleButton.setVisibility(View.VISIBLE);
            if(arrray[6].equals("remove")) {
                toggleButton.setBackgroundColor(Color.RED);
                toggleButton.setChecked(true);
            }
            else {
                toggleButton.setBackgroundColor(Color.GREEN);
            }
        }




        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tag = "add";
                    toggleButton.setEnabled(false);
                    nameValuePairs.add(new BasicNameValuePair("id", id));
                    nameValuePairs.add(new BasicNameValuePair("univ", arrray[0]));
                    nameValuePairs.add(new BasicNameValuePair("mode", tag));
                    StrictMode.setThreadPolicy(policy);
                    toggleButton.setEnabled(false);
                    new ShortListed().execute("");
                    Log.e("tag",tag);
                   /* HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://www.venbaventures.com/stuforia/stuforiatest/shortlisteduniversity_new.php");
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    String output = EntityUtils.toString(response.getEntity());
                    String result = output.replaceAll("\\s+", "");
                    // Toast.makeText(MapActivity.this,"remove"+result, Toast.LENGTH_SHORT).show();
                    if (result.equals("Record_updated_successfully")) {
                        editor.putString("shortlisted_univ", prefs.getString("shortlisted_univ", null) + "," + arrray[0]);
                        editor.commit();
                        Log.e("hello", prefs.getString("shortlisted_univ", null));
                        toggleButton.setEnabled(true);
                        toggleButton.setBackgroundColor(Color.RED);
                    }
*/

                } else {
                    tag= "remove";
                    Log.e("tag",tag);
                    nameValuePairs.add(new BasicNameValuePair("id", id));
                    nameValuePairs.add(new BasicNameValuePair("univ", arrray[0]));
                    nameValuePairs.add(new BasicNameValuePair("mode", tag));
                    StrictMode.setThreadPolicy(policy);
                    toggleButton.setEnabled(false);
                    new ShortListed().execute("");
                      /*  HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost("http://www.venbaventures.com/stuforia/stuforiatest/shortlisteduniversity_new.php");
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        HttpResponse response = httpclient.execute(httppost);
                        String output = EntityUtils.toString(response.getEntity());
                        String result = output.replaceAll("\\s+", "");
                        // Toast.makeText(MapActivity.this,"remove"+result, Toast.LENGTH_SHORT).show();
                        if (result.equals("Record_updated_successfully")) {
                            editor.putString("shortlisted_univ", prefs.getString("shortlisted_univ", null).replace(arrray[0], "0"));
                            editor.commit();
                            Log.e("hello", prefs.getString("shortlisted_univ", null));
                            toggleButton.setEnabled(true);
                            toggleButton.setBackgroundColor(Color.GREEN);
                        }*/

                    }
            }
        });

        TextView name=(TextView)findViewById(R.id.textView);
        TextView address=(TextView)findViewById(R.id.textView2);
        logo=(ImageView)findViewById(R.id.imageView);
        new  ImageLoad().execute(arrray[5]);
        name.setText(arrray[1]);
        address.setText(arrray[2]);

        try {
            googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(
                    R.id.map)).getMap();
            double lat=Double.parseDouble(arrray[3]);
            double lan= Double.parseDouble(arrray[4]);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lan), 3.0f));
            googleMap.addMarker(new MarkerOptions().position(new LatLng(lat, lan)).title(arrray[2]).snippet(arrray[1]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).alpha(1f));
        }
        catch (Exception e)
        {
           // Toast.makeText(MapActivity.this,"a"+e,Toast.LENGTH_LONG).show();
        }

        // Toast.makeText(this, "" + arrray[0] + arrray[1], Toast.LENGTH_LONG).show();
    }

    public void show() {
         try {
             Log.e("tag ","show");
             JSONArray mJsonArray = new JSONArray(myJSON);
             JSONObject mJsonObject = new JSONObject();
             myStringArray = new String[mJsonArray.length()][15];
             myStringArray1 = new String[mJsonArray.length()][15];
             int mba = 0;
             int ms = 0;
             Log.e("tag ",mJsonArray.length()+"");
             for (int i = 0; i < mJsonArray.length(); i++) {
                 mJsonObject = mJsonArray.getJSONObject(i);
                 if (("MBA").equals(mJsonObject.getString("MBA/MS"))) {
                     myStringArray[mba][0] = mJsonObject.getString("course name");
                     myStringArray[mba][1] = mJsonObject.getString("deadline");
                     myStringArray[mba][2] = mJsonObject.getString("gre_verbal");
                     myStringArray[mba][3] = mJsonObject.getString("gre_quantitative");
                     myStringArray[mba][4] = mJsonObject.getString("toefl_paperbased");
                     myStringArray[mba][5] = mJsonObject.getString("toefl_computerbased");
                     myStringArray[mba][6] = mJsonObject.getString("toefl_internet_based");
                     myStringArray[mba][7] = mJsonObject.getString("gmat");
                     myStringArray[mba][8] = mJsonObject.getString("ielts");
                     myStringArray[mba][9] = mJsonObject.getString("pte");
                     myStringArray[mba][10] = mJsonObject.getString("fees_structure");
                     myStringArray[mba][11] = mJsonObject.getString("gre_score");
                     MBA.add(myStringArray[mba][0]);
                     mba++;
                 }
                 else {
                     myStringArray1[ms][0] = mJsonObject.getString("course name");
                     myStringArray1[ms][1] = mJsonObject.getString("deadline");
                     myStringArray1[ms][2] = mJsonObject.getString("gre_verbal");
                     myStringArray1[ms][3] = mJsonObject.getString("gre_quantitative");
                     myStringArray1[ms][4] = mJsonObject.getString("toefl_paperbased");
                     myStringArray1[ms][5] = mJsonObject.getString("toefl_computerbased");
                     myStringArray1[ms][6] = mJsonObject.getString("toefl_internet_based");
                     myStringArray1[ms][7] = mJsonObject.getString("gmat");
                     myStringArray1[ms][8] = mJsonObject.getString("ielts");
                     myStringArray1[ms][9] = mJsonObject.getString("pte");
                     myStringArray1[ms][10] = mJsonObject.getString("fees_structure");
                     myStringArray1[ms][11] = mJsonObject.getString("gre_score");
                     MS.add(myStringArray1[ms][0]);
                     ms++;
                 }
                 //myStringArray[1] = mJsonObject.getString("univ_id");
                 prepareListData();
             }

         }  catch (Exception e) {
             Toast.makeText(getApplicationContext(),
                     "Couldn't connect. Make sure that your phone has an internet connection and try again.",
                     Toast.LENGTH_LONG).show();
         }


         expListView = (ExpandableListView) findViewById(R.id.lvExp1);

         // preparing list data


         listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

         // setting list adapter
         expListView.setAdapter(listAdapter);

         // Listview Group click listener
         expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

             @Override
             public boolean onGroupClick(ExpandableListView parent, View v,
                                         int groupPosition, long id) {
                 // Toast.makeText(getApplicationContext(),
                 // "Group Clicked " + listDataHeader.get(groupPosition),
                 // Toast.LENGTH_SHORT).show();
                 return false;
             }
         });

         // Listview Group expanded listener
         expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

             @Override
             public void onGroupExpand(int groupPosition) {
              /*  Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();*/
             }
         });

         // Listview Group collasped listener
         expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

             @Override
             public void onGroupCollapse(int groupPosition) {
                /*Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();
*/
             }
         });

         // Listview on child click listener
         expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

             @Override
             public boolean onChildClick(ExpandableListView parent, View v,
                                         int groupPosition, int childPosition, long id) {
                 // TODO Auto-generated method stub
                 Intent intent = new Intent(MapActivity.this, CourseDetail.class);
                 int number = expListView.getExpandableListAdapter().getGroupCount();
                 if(number==1) {
                     if(listDataHeader.get(groupPosition).equals("MBA")){
                     intent.putExtra("details", myStringArray[childPosition]);
                     intent.putExtra("mba/ms", "MBA");
                     }
                     else{
                         intent.putExtra("details", myStringArray1[childPosition]);
                         intent.putExtra("mba/ms", "MS");}
                     }

                 else {
                     if (groupPosition == 0) {
                         intent.putExtra("details", myStringArray[childPosition]);
                         intent.putExtra("mba/ms", "MBA");
                     }
                     if (groupPosition == 1) {
                         intent.putExtra("details", myStringArray1[childPosition]);
                         intent.putExtra("mba/ms", "MS");
                     }
                 }
                 startActivity(intent);
              /*   Toast.makeText(
                         getApplicationContext(),
                         listDataHeader.get(groupPosition)
                                 + " : "
                                 + listDataChild.get(
                                 listDataHeader.get(groupPosition)).get(
                                 childPosition), Toast.LENGTH_SHORT)
                         .show();*/
                 return false;
             }
         });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
       // listDataHeader.add("MBA");
       // listDataHeader.add("MS");
        if(!MBA.isEmpty())
        {
            listDataHeader.add("MBA");
            listDataChild.put(listDataHeader.get(0), MBA);
        }
        else {
            listDataHeader.add("MS");
            listDataChild.put(listDataHeader.get(0), MS);
        }
        if((!MS.isEmpty())&&(!MBA.isEmpty()))
        {
            listDataHeader.add("MS");
            listDataChild.put(listDataHeader.get(1), MS);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        pauselayout.setAlpha((float) 0.1);
    }

    @Override
    public void onResume() {
        super.onResume();
        pauselayout.setAlpha((float) 1.0);
    }

    private class ImageLoad extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute()
        {
            myAnim    = AnimationUtils.loadAnimation(MapActivity.this, R.anim.anim);
            logo.startAnimation(myAnim);
            // dialog = ProgressDialog.show(MainActivity.this, "",
            //"Update ...please wait...", true);
        }
        @Override
        protected String doInBackground(String... params) {

            try {
                java.net.URL url = new java.net.URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                myBitmap = BitmapFactory.decodeStream(input);


            } catch (IOException e) {
                dialog.dismiss();
                e.printStackTrace();

            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            // dialog.dismiss();
            logo.getAnimation().cancel();
            logo.clearAnimation();
            myAnim.setAnimationListener(null);
            logo.setAnimation(null);
            logo.setImageBitmap(myBitmap);
        }
        @Override
        protected void onProgressUpdate(Void... values) {
        }

    }

    private class LoadCourseDetails extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute()
         {

            dialog = ProgressDialog.show(MapActivity.this, "",
                    "Loading Courses ...", true);
        }
        @Override
        protected String doInBackground(String... params) {
            ArrayList<NameValuePair> nameValuePairs1 = new ArrayList<NameValuePair>();
            nameValuePairs1.add(new BasicNameValuePair("id", arrray[0]));
            StrictMode.setThreadPolicy(policy);
            try {
                httpclient = new DefaultHttpClient();
                httppost = new HttpPost("http://www.venbaventures.com/stuforia/singleuniversity.php"); // make sure the// is correct.
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs1));
                response = httpclient.execute(httppost);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                final String response = httpclient.execute(httppost, responseHandler);
                myJSON = response.toString();
                Log.e("print",myJSON);
            }
            catch (Exception e)
            {
                dialog.dismiss();
                e.printStackTrace();
                myJSON="internetFailed";
                Log.e("print", myJSON);
            }
            return myJSON;
        }
        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            if(result.equals("internetFailed")){
                Toast.makeText(MapActivity.this, "Couldn't connect. Make sure that your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();
            }
           else
            show();

        }
        @Override
        protected void onProgressUpdate(Void... values) {
        }

    }

    private class ShortListed extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute()
        {

        }
        @Override
        protected String doInBackground(String... params) {
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://www.venbaventures.com/stuforia/shortlisteduniversity_new.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                String responseStr = EntityUtils.toString(response.getEntity());
                output = responseStr.replaceAll("\\s+","");
                return output;
                // Toast.makeText(getActivity(), "output" + output, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                output="internetFailed";
                //Toast.makeText(ChangePassword.this, "Error:" + e, Toast.LENGTH_SHORT).show();
            }
            return output;
        }
        @Override
        protected void onPostExecute(String result) {
            if (result.equals("Record_updated_successfully")) {
                toggleButton.setEnabled(true);
                if (arrray[6].equals("remove")) {
                    editor.putString("shortlisted_univ", prefs.getString("shortlisted_univ", null).replace(arrray[0],"-1"));
                    editor.commit();
                    arrray[6] = "add";
                    toggleButton.setBackgroundColor(Color.GREEN);
                    Log.e("check1", prefs.getString("shortlisted_univ", null));
                } else {
                    if(prefs.getString("shortlisted_univ", null).contains("-1")) {
                        editor.putString("shortlisted_univ", prefs.getString("shortlisted_univ", null).replaceFirst("-1",arrray[0]));
                        editor.commit();
                        Log.e("check2", prefs.getString("shortlisted_univ", null));
                    }
                    else{
                        editor.putString("shortlisted_univ", prefs.getString("shortlisted_univ", null) + "," + arrray[0]);
                        editor.commit();
                        Log.e("check3", prefs.getString("shortlisted_univ", null));
                    }
                    arrray[6] = "remove";
                    toggleButton.setBackgroundColor(Color.RED);
                }
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}
