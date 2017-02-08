package com.compunet.stuforia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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

import java.util.ArrayList;

public class FilterSearch extends AppCompatActivity {
    private String array_spinner[];
    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    String myJSON;
    HttpPost httppost1;
    HttpResponse response1;
    HttpClient httpclient1;
    float gre_entered,gmat_entered,toefl_entered,ielts_entered,pte_entered;
    String myJSON1;
    String myStringArray[];
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    ProgressDialog cityDialog = null, courseDialog = null;
    ProgressDialog filterDialog = null,dialog1=null;
    ArrayList<String> mbaList;
    ArrayList<String> msList;
    ArrayList<String> state;
    ArrayList<String> city;
    ArrayAdapter adapter4;
    Spinner s4, s1, s2, s3;
    Button search;
    String id, timezone = "";
    EditText edgmat, tofelcom, ielt, edpte, gre_score;
    TextView fromFees, toFees;
    CheckBox check1, check2, check3, check4, check5, check6;
    ToggleButton proximity;
    ArrayList<NameValuePair> nameValuePairs;
    ArrayList<NameValuePair> filterNameValuePairs;
    boolean proximityFlag = false;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters_try);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d5da7")));

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();

        id = prefs.getString("id", null);
        proximity = (ToggleButton) findViewById(R.id.proximity);
        array_spinner = new String[3];
        array_spinner[0] = "ALL";
        array_spinner[1] = "MBA";
        array_spinner[2] = "MS";
        s1 = (Spinner) findViewById(R.id.Spinner01);
        gre_score = (EditText) findViewById(R.id.gre_score);
        edgmat = (EditText) findViewById(R.id.gmat);
        tofelcom = (EditText) findViewById(R.id.toefl_cb);
        ielt = (EditText) findViewById(R.id.ielts);
        edpte = (EditText) findViewById(R.id.pte);
        fromFees = (TextView) findViewById(R.id.fromFeesVal);
        toFees = (TextView) findViewById(R.id.toFeesVal);
        check1 = (CheckBox) findViewById(R.id.check1);
        check2 = (CheckBox) findViewById(R.id.check2);
        check3 = (CheckBox) findViewById(R.id.check3);
        check4 = (CheckBox) findViewById(R.id.check4);
        check5 = (CheckBox) findViewById(R.id.check5);
        check6 = (CheckBox) findViewById(R.id.check6);
        search = (Button) findViewById(R.id.search);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.custom_dropdown_list, array_spinner);
        s1.setAdapter(adapter);

        new LoadCourse().execute("");

        new LoadState().execute("");

        RangeSeekBar<Integer> seekBar = new RangeSeekBar<Integer>(10000, 50000, getApplicationContext());
        seekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                // handle changed range values
                fromFees.setText(minValue.toString());
                toFees.setText(maxValue.toString());
                Log.i("Seekbar Value", "User selected new range values: MIN=" + minValue + ", MAX=" + maxValue);
            }
        });

        LinearLayout rangebar = (LinearLayout) findViewById(R.id.rangeseekbar);
        rangebar.addView(seekBar);

        proximity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (s3.getSelectedItem().toString().equals("ALL") || s2.getSelectedItem().toString().equals("ALL")) {
                        new AlertDialog.Builder(FilterSearch.this)
                                .setTitle("PROXIMITY")
                                .setMessage("Please select State and City to enable proximity search...")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setNegativeButton(android.R.string.ok, null).show();
                        proximity.setChecked(false);
                    }

                }
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int flag=0;

                if(!(gre_score.getText().toString().isEmpty())){gre_entered= Float.parseFloat(gre_score.getText().toString());}
                if(!(edgmat.getText().toString().isEmpty())){gmat_entered= Float.parseFloat(edgmat.getText().toString());}
                if(!(tofelcom.getText().toString().isEmpty())){toefl_entered= Float.parseFloat(tofelcom.getText().toString());}
                if(!(ielt.getText().toString().isEmpty())){ielts_entered= Float.parseFloat(ielt.getText().toString());}
                if(!(edpte.getText().toString().isEmpty())){pte_entered= Float.parseFloat(edpte.getText().toString());}

                if(!(gre_score.getText().toString().isEmpty())&&(gre_entered<260 || gre_entered>340)){Toast.makeText(FilterSearch.this,"Invalid GRE score.", Toast.LENGTH_SHORT).show();}
                else if(!(edgmat.getText().toString().isEmpty())&&(gmat_entered<200 || gmat_entered>800)){Toast.makeText(FilterSearch.this,"Invalid GMAT score.", Toast.LENGTH_SHORT).show();}
                else if(!(tofelcom.getText().toString().isEmpty())&&(toefl_entered<0 || toefl_entered>120)){Toast.makeText(FilterSearch.this,"Invalid TOEFL score.", Toast.LENGTH_SHORT).show();}
                else if(!(ielt.getText().toString().isEmpty())&&(ielts_entered<0 || ielts_entered>9)){Toast.makeText(FilterSearch.this,"Invalid IELTS score.", Toast.LENGTH_SHORT).show();}
                else if(!(edpte.getText().toString().isEmpty())&&(pte_entered<10 || pte_entered>90)){Toast.makeText(FilterSearch.this,"Invalid PTE score.", Toast.LENGTH_SHORT).show();}

                else{
                    timezone = "";
                    if (check2.isChecked()) {
                        timezone = timezone.concat("'CST',");
                    }
                    if (check3.isChecked()) {
                        timezone = timezone.concat("'EST',");
                    }
                    if (check1.isChecked()) {
                        timezone = timezone.concat("'AKST',");
                    }
                    if (check4.isChecked()) {
                        timezone = timezone.concat("'MST',");
                    }
                    if (check5.isChecked()) {
                        timezone = timezone.concat("'PST',");
                    }
                    if (check6.isChecked()) {
                        timezone = timezone.concat("'HST',");
                    }
                    filterNameValuePairs = new ArrayList<NameValuePair>();
                    filterNameValuePairs.add(new BasicNameValuePair("timezone", timezone));
                    filterNameValuePairs.add(new BasicNameValuePair("coursetype", s1.getSelectedItem().toString()));
                    filterNameValuePairs.add(new BasicNameValuePair("course", s4.getSelectedItem().toString()));
                    filterNameValuePairs.add(new BasicNameValuePair("state", s3.getSelectedItem().toString()));
                    filterNameValuePairs.add(new BasicNameValuePair("city", s2.getSelectedItem().toString()));
                    filterNameValuePairs.add(new BasicNameValuePair("gre", gre_score.getText().toString()));
                    filterNameValuePairs.add(new BasicNameValuePair("gmat", edgmat.getText().toString()));
                    filterNameValuePairs.add(new BasicNameValuePair("ielts", ielt.getText().toString()));
                    filterNameValuePairs.add(new BasicNameValuePair("pte", edpte.getText().toString()));
                    filterNameValuePairs.add(new BasicNameValuePair("tofel_computer", tofelcom.getText().toString()));
                    filterNameValuePairs.add(new BasicNameValuePair("fees", toFees.getText().toString()));
                    if (proximity.isChecked()) {
                        filterNameValuePairs.add(new BasicNameValuePair("proximity", "1"));
                        proximityFlag = true;
                    }
                    Log.e("course", s4.getSelectedItem().toString());
                    Log.e("time", timezone);

                    if (flag == 0) {
                        new SearchResult().execute("");
                    }

                }


            }
        });


    }

    public void show(String tag) {

        if (tag.equals("course")) {
            try {

                JSONArray mJsonArray = new JSONArray(myJSON);
                JSONObject mJsonObject = new JSONObject();

                msList = new ArrayList<String>();
                msList.add("ALL");
                mbaList = new ArrayList<String>();
                mbaList.add("ALL");
                for (int i = 0; i < mJsonArray.length(); i++) {
                    mJsonObject = mJsonArray.getJSONObject(i);
                    if (("MBA").equals(mJsonObject.getString("MBA/MS"))) {
                        // myStringArray[mba]=mJsonObject.getString("course name");
                        mbaList.add(mJsonObject.getString("course name"));
                    }
                    if (("MS").equals(mJsonObject.getString("MBA/MS"))) {
                        msList.add(mJsonObject.getString("course name"));
                    }
                    // Toast.makeText(FilterSearch.this,""+mba+ms,Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {

              //  Toast.makeText(FilterSearch.this, "" + e, Toast.LENGTH_LONG).show();
            }
            s4 = (Spinner) findViewById(R.id.Spinner02);
            array_spinner = new String[1];
            array_spinner[0] = "ALL";
            adapter4 = new ArrayAdapter(FilterSearch.this, R.layout.custom_dropdown_list, array_spinner);
            s4.setAdapter(adapter4);
            /*adapter4 = new ArrayAdapter(this, R.layout.custom_dropdown_list, mbaList);
            s4.setAdapter(adapter4);*/

            s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                           long id) {

                    switch (s1.getSelectedItemPosition()) {
                        case 0:
                            array_spinner = new String[1];
                            array_spinner[0] = "ALL";
                            adapter4 = new ArrayAdapter(FilterSearch.this, R.layout.custom_dropdown_list, array_spinner);
                            s4.setAdapter(adapter4);
                            break;
                        case 1:
                            adapter4 = new ArrayAdapter(FilterSearch.this, R.layout.custom_dropdown_list, mbaList);
                            s4.setAdapter(adapter4);
                            break;
                        case 2:
                            adapter4 = new ArrayAdapter(FilterSearch.this, R.layout.custom_dropdown_list, msList);
                            s4.setAdapter(adapter4);

                            break;
                    }


                    // Spinner spinnerEnterprisel1 = (Spinner)
                    // findViewById(R.id.spinner_enterprise_folding_inserter);
                }

                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                    array_spinner = new String[1];
                    array_spinner[0] = "ALL";
                    adapter4 = new ArrayAdapter(FilterSearch.this, R.layout.custom_dropdown_list, array_spinner);
                    s4.setAdapter(adapter4);
                }
            });
        } else if (tag.equals("state")) {
            try {

                JSONArray mJsonArray = new JSONArray(myJSON1);
                JSONObject mJsonObject = new JSONObject();
                state = new ArrayList<String>();
                state.add("ALL");
                for (int i = 0; i < mJsonArray.length(); i++) {
                    mJsonObject = mJsonArray.getJSONObject(i);
                    state.add(mJsonObject.getString("state"));
                    //  Toast.makeText(FilterSearch.this,""+mJsonObject.getString("state"),Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
             //   Toast.makeText(FilterSearch.this, "" + e, Toast.LENGTH_LONG).show();
            }


            s3 = (Spinner) findViewById(R.id.state);
            adapter4 = new ArrayAdapter(FilterSearch.this, R.layout.custom_dropdown_list, state);
            s3.setAdapter(adapter4);
            s2 = (Spinner) findViewById(R.id.city);
            s3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                           long id) {

                    if (pos != 0) {

                        nameValuePairs = new ArrayList<NameValuePair>();
                        nameValuePairs.add(new BasicNameValuePair("state", parent.getItemAtPosition(pos).toString()));
                        new LoadCity().execute("http://www.venbaventures.com/stuforia/statecity.php");
                        StrictMode.setThreadPolicy(policy);


                    } else {
                        array_spinner = new String[1];
                        array_spinner[0] = "ALL";
                        adapter4 = new ArrayAdapter(FilterSearch.this, R.layout.custom_dropdown_list, array_spinner);
                        s2.setAdapter(adapter4);
                    }
                }

                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                    array_spinner = new String[1];
                    array_spinner[0] = "ALL";
                    adapter4 = new ArrayAdapter(FilterSearch.this, R.layout.custom_dropdown_list, array_spinner);
                    s2.setAdapter(adapter4);
                }
            });
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

    private class LoadCity extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            cityDialog = ProgressDialog.show(FilterSearch.this, "",
                    "Loading Cities...", true);
        }

        @Override
        protected String doInBackground(String... params) {
            String responseStr = "";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(params[0]);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());
            } catch (Exception ex) {
                ex.printStackTrace();
                responseStr="internetFailed";
            }
            return responseStr;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.equals("internetFailed")){
                Toast.makeText(FilterSearch.this, "Couldn't connect. Make sure that your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();
            }
            else{
                try {
                    myJSON = result;
                    JSONArray mJsonArray = new JSONArray(myJSON);
                    JSONObject mJsonObject = new JSONObject();
                    city = new ArrayList<String>();
                    city.add("ALL");
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        mJsonObject = mJsonArray.getJSONObject(i);
                        city.add(mJsonObject.getString("city"));
                        //Toast.makeText(FilterSearch.this, "" + mJsonObject.getString("city"), Toast.LENGTH_LONG).show();
                    }
                    adapter4 = new ArrayAdapter(FilterSearch.this, R.layout.custom_dropdown_list, city);
                    s2.setAdapter(adapter4);
                } catch (Exception e) {

                }
            }

            cityDialog.dismiss();


        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    private class LoadCourse extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {

            courseDialog = ProgressDialog.show(FilterSearch.this, "",
                    "Loading ...", true);
        }

        @Override
        protected String doInBackground(String... params) {


            StrictMode.setThreadPolicy(policy);

            try {
                httpclient = new DefaultHttpClient();
                httppost = new HttpPost("http://www.venbaventures.com/stuforia/coursetype.php"); // make sure the// is correct.
                response = httpclient.execute(httppost);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                final String response = httpclient.execute(httppost, responseHandler);
                myJSON = response.toString();

            } catch (Exception e) {
                courseDialog.dismiss();
                myJSON="internetFailed";
                // Toast.makeText(University.this,"Exception",Toast.LENGTH_SHORT).show();

                e.printStackTrace();
            }


            return myJSON;
        }
        @Override
        protected void onPostExecute(String result) {
            courseDialog.dismiss();
            if(result.equals("internetFailed")){
                Toast.makeText(FilterSearch.this, "Couldn't connect. Make sure that your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();
            }
            else{
                show("course");
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }

    }

    private class LoadState extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {

            dialog1 = ProgressDialog.show(FilterSearch.this, "",
                    "Loading ...", true);
        }

        @Override
        protected String doInBackground(String... params) {


            StrictMode.setThreadPolicy(policy);

            try {

                httpclient1 = new DefaultHttpClient();
                httppost1 = new HttpPost("http://www.venbaventures.com/stuforia/state.php"); // make sure the// is correct.
                response1 = httpclient1.execute(httppost1);
                ResponseHandler<String> responseHandler1 = new BasicResponseHandler();
                final String response1 = httpclient1.execute(httppost1, responseHandler1);

                myJSON1 = response1.toString();
            } catch (Exception e) {
                dialog1.dismiss();
                e.printStackTrace();
                myJSON="internetFailed";
            }

            return myJSON1;
        }

        @Override
        protected void onPostExecute(String result) {
           dialog1.dismiss();
            if(result.equals("internetFailed")){
                Toast.makeText(FilterSearch.this, "Couldn't connect. Make sure that your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();
            }
            else
            show("state");

        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }

    }

    private class SearchResult extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {

            filterDialog = ProgressDialog.show(FilterSearch.this, "",
                    "Filtering...", true);
        }

        @Override
        protected String doInBackground(String... params) {


            //StrictMode.setThreadPolicy(policy);

            String resu = null;

            try {
                httpclient = new DefaultHttpClient();
                httppost = new HttpPost("http://www.venbaventures.com/stuforia/filter_bk.php"); // make sure the// is correct.
                httppost.setEntity(new UrlEncodedFormEntity(filterNameValuePairs));
                response = httpclient.execute(httppost);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                final String response = httpclient.execute(httppost, responseHandler);
                resu = response.toString();
                Log.e("Result", resu);
            } catch (Exception f) {
                Log.e("error", f.toString());
                resu="internetFailed";
                //   Log.e("time",timezone);
                // Toast.makeText(FilterSearch.this, "" + e, Toast.LENGTH_LONG).show();
            }

            return resu;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.equals("internetFailed")){
                Toast.makeText(FilterSearch.this, "Couldn't connect. Make sure that your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();
            }
            if (!result.isEmpty()) {
                Log.e("Result post exec", result);
                Log.e("Result post exec", result.length() + "");
                filterDialog.dismiss();
                Intent intent = new Intent(FilterSearch.this, FilterResult.class);
               /* intent.putExtra("id", id);*/
                intent.putExtra("search", result);
                if (proximityFlag)
                    intent.putExtra("proximity", "1");
                else
                    intent.putExtra("proximity", "0");
                startActivity(intent);
            } else {
                filterDialog.dismiss();

                Log.e("Result post exec", "Empty data");
                Log.e("Result post exec", result.length() + "");
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }

    }
    @Override
    public void onBackPressed()
    {
        finish();
    }
}

