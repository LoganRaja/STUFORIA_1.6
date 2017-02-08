package com.compunet.stuforia;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;

/**
 * Created by connectors on 9/19/2015.
 */
public class ForgotPassword extends ActionBarActivity {
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    EditText emailInput_forgotPassword;
    EditText verification_code;
   LinearLayout emaillayout,forgotpasswordlayout,pauselayout;
    TextView invalidDetails;
    String result;
    String emailInput_forgotPassword_string;
    String id;
   Button Generate_Verification,submit_verificationCode;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String pause="resume";
    ProgressDialog dialog = null;
    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_try);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        emailInput_forgotPassword=(EditText)findViewById(R.id.emailInput_forgotPassword);
        verification_code=(EditText)findViewById(R.id.verification_code);
        submit_verificationCode=(Button)findViewById(R.id.submit_verificationCode);
        Generate_Verification=(Button)findViewById(R.id.Generate_Verification);


        invalidDetails=(TextView)findViewById(R.id.invalidDetails);
        emaillayout=(LinearLayout)findViewById(R.id.emaillayout);
        forgotpasswordlayout=(LinearLayout)findViewById(R.id.forgotpasswordlayout);
        pauselayout=(LinearLayout)findViewById(R.id.pasuelayout);


        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();


        Generate_Verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailId = emailInput_forgotPassword.getText().toString();

                if (emailId.isEmpty()) {
                    emailInput_forgotPassword.setError("Please fill this field !");
                } else {

                    nameValuePairs.add(new BasicNameValuePair("email", emailInput_forgotPassword.getText().toString()));
                    StrictMode.setThreadPolicy(policy);
                    new GenerateVerification().execute("");
                }
            }

        });
               submit_verificationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(ForgotPassword.this,"id"+result, Toast.LENGTH_SHORT).show();
                 nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("id",id));
                nameValuePairs.add(new BasicNameValuePair("verificationcode",verification_code.getText().toString()));
                StrictMode.setThreadPolicy(policy);
                new SumbitVerifcationCode().execute("");
            }
        });
    }

      private class GenerateVerification extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute()
        {
            dialog = ProgressDialog.show(ForgotPassword.this, "",
                    "Generating Verification Code...", true);
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://www.venbaventures.com/stuforia/forgotpassword.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                String responseStr = EntityUtils.toString(response.getEntity());
                result = responseStr.replaceAll("\\s+", "");
                id=result;
            }
            catch (Exception e)
            {
                dialog.dismiss();
              result="internetFailed";
                //Toast.makeText(ForgotPassword.this, "error"+e, Toast.LENGTH_SHORT).show();
            }
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            Log.e("result ennna da vara",result);
            dialog.dismiss();
            if(result.equals("internetFailed")){
                Toast.makeText(ForgotPassword.this, "Couldn't connect. Make sure that your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();
            }
            else if(result.equals("-1")||result.equals("null")) {
                Toast.makeText(ForgotPassword.this, "Your email ID is not correct ...", Toast.LENGTH_SHORT).show();
                invalidDetails.setVisibility(View.VISIBLE);
            }
            else if(result.equals("send_error"))
                Toast.makeText(ForgotPassword.this,"Couldn't send verification code. Please verify your email ID.", Toast.LENGTH_SHORT).show();
            else  if(!result.equals("-1")||!result.equals("send_error")) {
                new AlertDialog.Builder(ForgotPassword.this)
                        .setTitle("VERIFICATION CODE")
                        .setMessage("Your verification code has been send to your mail\n" +
                                "Please check your spam folder if incase you didn't receive in your inbox ...")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setNegativeButton(android.R.string.ok, null).show();
                //Toast.makeText(ForgotPassword.this, "Enter your verification code Here .." , Toast.LENGTH_SHORT).show();
                forgotpasswordlayout.setVisibility(View.VISIBLE);
                emaillayout.setVisibility(View.GONE);
                invalidDetails.setVisibility(View.GONE);
            }

        }


        @Override
        protected void onProgressUpdate(Void... values) {
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
        pauselayout.setAlpha(1);
    }

    private class SumbitVerifcationCode extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute()
        {
            dialog = ProgressDialog.show(ForgotPassword.this, "",
                    "Updating Password...", true);
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://www.venbaventures.com/stuforia/forgotpassword_verify.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                String responseStr = EntityUtils.toString(response.getEntity());
               result = responseStr.replaceAll("\\s+", "");
            }
            catch (Exception e)
            {
                dialog.dismiss();
                result="internetFailed";
               // Toast.makeText(ForgotPassword.this, "error"+e, Toast.LENGTH_SHORT).show();
            }
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            Log.e("check value", result);
            if(result.equals("internetFailed")){
                Toast.makeText(ForgotPassword.this, "Couldn't connect. Make sure that your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();
            }
            if(result.equals("match")) {
                Log.e("check value  1", result);
              //  Toast.makeText(ForgotPassword.this, "Verification code match ..." , Toast.LENGTH_SHORT).show();
                Intent toChanagePassword = new Intent(ForgotPassword.this, ChangePassword.class);
                toChanagePassword.putExtra("tag", "forgot");
                editor.putString("id",id);
                editor.commit();
                startActivity(toChanagePassword);
                finish();
            }
            else if(result.equals("not_match"))
            {
                Log.e("check value  2", result);
                Toast.makeText(ForgotPassword.this," Verification code doesn't match ", Toast.LENGTH_SHORT).show();
            }
        }


        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }


    @Override
    public void onBackPressed() {
      finish();
        
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
