package com.compunet.stuforia;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
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

public class BankListActivity extends ActionBarActivity {
    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    ProgressDialog dialog = null;
    String myJSON="";
    String bank_details[][];


    private RecyclerView mRecyclerView;
    private BankRecyclerview adapter;

    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bank_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView = (RecyclerView) findViewById(R.id.bank_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            new LoadingBankList().execute("");

    }


    public void show() {
        try {

            JSONArray mJsonArray = new JSONArray(myJSON);
            JSONObject mJsonObject = new JSONObject();
            bank_details=new String[mJsonArray.length()][10];
            for (int i = 0; i <mJsonArray.length(); i++) {
                mJsonObject = mJsonArray.getJSONObject(i);
                bank_details[i][0]=mJsonObject.getString("id");
                bank_details[i][1]=mJsonObject.getString("bank_name");
                bank_details[i][2]=mJsonObject.getString("check_list");
                bank_details[i][3]=mJsonObject.getString("rate_of_interest");
                bank_details[i][4]=mJsonObject.getString("study_abroad");
                bank_details[i][5]=mJsonObject.getString("loan_for_girls");
                bank_details[i][6]=mJsonObject.getString("collateral_security");
                bank_details[i][7]=mJsonObject.getString("coapplicant_self_employed");
                bank_details[i][8]=mJsonObject.getString("coapplicant_salaried");
            }
            adapter = new BankRecyclerview(this,bank_details);
            mRecyclerView.setAdapter(adapter);
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

    private class LoadingBankList extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute()
        {
            dialog = ProgressDialog.show(BankListActivity.this, "",
                    "Loading Bank list...", true);
        }
        @Override
        protected String doInBackground(String... params) {
            String responseStr="";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                 httppost = new HttpPost("http://www.venbaventures.com/stuforia/bank_details.php");
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
                Toast.makeText(BankListActivity.this, "Couldn't connect. Make sure that your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                show();
            }
        }

        }
}

