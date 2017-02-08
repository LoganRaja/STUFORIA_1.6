package com.compunet.stuforia;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.compunet.stuforia.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by connectors on 9/19/2015.
 */
public class ChangePassword extends Activity {
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    ProgressDialog dialog = null;
    EditText oldpass;
    EditText newpass;
    EditText conformpass;
    String result;
    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_popup_try);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .8));
        Button button=(Button)findViewById(R.id.change_password);
        oldpass=(EditText)findViewById(R.id.editText2);
        newpass=(EditText)findViewById(R.id.editText3);
        conformpass=(EditText)findViewById(R.id.editText4);

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();

        id=prefs.getString("id", null);

        Intent intent=getIntent();

           if(intent.getStringExtra("tag").equals("forgot"))
           {
           oldpass.setVisibility(View.GONE);
       }
           button.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (newpass.getText().toString().isEmpty()) {
                       Toast.makeText(ChangePassword.this, "Password field shouldn't be Empty Please enter a valid password", Toast.LENGTH_LONG).show();
                   }
                   else if (!newpass.getText().toString().equals(conformpass.getText().toString())) {
                       Toast.makeText(ChangePassword.this, "Your password doesn't match\n Please verify", Toast.LENGTH_LONG).show();
                   } else {
                       /*Intent intent = getIntent();
                       String str = intent.getStringExtra("id");*/

                       String password = oldpass.getText().toString();
                       nameValuePairs.add(new BasicNameValuePair("id", id));
                       nameValuePairs.add(new BasicNameValuePair("password", password));
                       nameValuePairs.add(new BasicNameValuePair("newpassword", newpass.getText().toString()));
                       new PasswordChange().execute("");
                   }
            }
        });

    }



    private class PasswordChange extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute()
        {
            dialog = ProgressDialog.show(ChangePassword.this, "",
                    "Updating Password...", true);
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                Intent intent=getIntent();
                HttpClient httpclient = new DefaultHttpClient();
                if (intent.getStringExtra("tag").equals("forgot")) {
                    HttpPost httppost = new HttpPost("http://www.venbaventures.com/stuforia/forgotpassword_change.php");
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    String responseStr = EntityUtils.toString(response.getEntity());
                    result = responseStr.replaceAll("\\s+", "");
                }
                else{
                    HttpPost httppost = new HttpPost("http://www.venbaventures.com/stuforia/change_password.php");
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    String responseStr = EntityUtils.toString(response.getEntity());
                    result = responseStr.replaceAll("\\s+", "");
                }
            }
            catch (Exception e)
            {
             dialog.dismiss();
             result="internetFailed";
            }
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            if(result.equals("internetFailed")){
                Toast.makeText(ChangePassword.this, "Couldn't connect. Make sure that your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();
            }
            if (result.equals("1")) {
                Toast.makeText(ChangePassword.this, "Your password has been changed successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else
                Toast.makeText(ChangePassword.this, "Your current password is incorrect", Toast.LENGTH_SHORT).show();
        }


        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }





}
