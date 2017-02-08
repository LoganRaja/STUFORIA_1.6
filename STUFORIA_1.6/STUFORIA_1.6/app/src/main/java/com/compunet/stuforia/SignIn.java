package com.compunet.stuforia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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


public class SignIn extends ActionBarActivity {
    TextView invalidDetails;
    ProgressDialog dialog = null;
    String jsonString=null;
    String output=null;
    EditText email;
    EditText password;
    Button signInBtn;
    TextView signup,forgot_password;
    String myJSON;
    String myStringArray[];
    public View rootView;
    CheckBox rememberme;
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    public static final String Password = "PasswordKey";
    public static final String Email = "emailKey";
    public static final String Check = "LoginKey";
    ArrayList<NameValuePair> nameValuePairs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_try);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d5da7")));*/




        invalidDetails=(TextView)findViewById(R.id.invalidDetails);
        email=(EditText)findViewById(R.id.emailInput_signIn);
        password=(EditText)findViewById(R.id.passInput_signIn);
        signInBtn=(Button)findViewById(R.id.signInBtn_signIn);
        forgot_password=(TextView)findViewById(R.id.forgot_password);
        signup=(TextView)findViewById(R.id.sign_up);
        rememberme=(CheckBox)findViewById(R.id.checkBox);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();
        if(prefs.getString("rememberKey",null)!=null) {
            if (prefs.getString(Email, null) != null) {
                email.setText(prefs.getString(Email, ""));
                rememberme.setChecked(true);
            }
            if (prefs.getString(Password, null) != null) {
                password.setText(prefs.getString(Password, ""));
            }
        }
        rememberme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               // Toast.makeText(SignIn.this,"remeberme "+prefs.getString("rememberKey", null),Toast.LENGTH_SHORT).show();
                if(isChecked)
                {
                    editor.putString("rememberKey", "on");
                    editor.commit();
                   // Toast.makeText(SignIn.this,"remeberme 1 "+prefs.getString("rememberKey",null),Toast.LENGTH_SHORT).show();
                }
                else {
                    editor.remove("rememberKey");
                    editor.commit();
                   // Toast.makeText(SignIn.this,"remeberme 2 "+prefs.getString("rememberKey",null),Toast.LENGTH_SHORT).show();

                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignIn.this,SignUp.class);
                startActivity(intent);
            }
        });


        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn();

            }
        });
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toForgotPassword = new Intent(SignIn.this, ForgotPassword.class);
                startActivity(toForgotPassword);
            }
        });
    }




    private void signIn(){
        String emailId=email.getText().toString();
        String pass=password.getText().toString();


        if (TextUtils.isEmpty(emailId)){
            email.setError("please fill this field");
            return;
        }
        else
        {
            email.setError(null);
        }
        if (TextUtils.isEmpty(pass)){

            password.setError("please fill this field");
            return;
        }
        else
        {
            password.setError(null);
        }
        if(rememberme.isChecked())
        {
            editor.putString("rememberKey","on");
            editor.commit();
        }
        editor.putString(Email, emailId);
        editor.putString(Password, pass);
        editor.commit();
        nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("email",emailId));
        nameValuePairs.add(new BasicNameValuePair("password", pass));
        StrictMode.setThreadPolicy(policy);
        new LogIn().execute("http://www.venbaventures.com/stuforia/login.php");


    }




    private class LogIn extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute()
        {
            dialog = ProgressDialog.show(SignIn.this, "",
                    "Signing In...", true);
        }
        @Override
        protected String doInBackground(String... params) {
            String responseStr="";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(params[0]);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());
            } catch (Exception ex) {
                ex.printStackTrace();
                responseStr="networkerror";
            }
            return responseStr;
        }
        @Override
        protected void onPostExecute(String result) {
           String output = result.replaceAll("\\s+", "");
            if(result.equals("networkerror"))
            {
                dialog.dismiss();
                Toast.makeText(SignIn.this, "Couldn't connect. Make sure that your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();
            }
           else if(output.equals("-1")) {
                invalidDetails.setVisibility(View.VISIBLE);
                dialog.dismiss();
               // Toast.makeText(SignIn.this, "id"+output, Toast.LENGTH_SHORT).show();
            }
            else {
                //  Toast.makeText(SignIn.this, "id"+output, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                try {
                    myJSON = output.toString();
                    JSONArray mJsonArray = new JSONArray(myJSON);
                    JSONObject mJsonObject = new JSONObject();
                   /* myStringArray = new String[100];
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        mJsonObject = mJsonArray.getJSONObject(i);
                        myStringArray[0] = mJsonObject.getString("firstname");
                        myStringArray[1] = mJsonObject.getString("lastname");
                        myStringArray[2] = mJsonObject.getString("gender");
                        myStringArray[3] = mJsonObject.getString("DOB");
                        myStringArray[4] = mJsonObject.getString("address");
                        myStringArray[5] = mJsonObject.getString("state");
                        myStringArray[6] = mJsonObject.getString("city");
                        myStringArray[7] = mJsonObject.getString("pincode");
                        myStringArray[8] = mJsonObject.getString("nationality");
                        myStringArray[9] = mJsonObject.getString("mobilenumber");
                        myStringArray[10] = mJsonObject.getString("passportnumber");
                        myStringArray[11] = mJsonObject.getString("hsc_education");
                        myStringArray[12] = mJsonObject.getString("ug_education");
                        myStringArray[13] = mJsonObject.getString("work_from");
                        myStringArray[14] = mJsonObject.getString("work_to");
                        myStringArray[15] = mJsonObject.getString("email");
                        myStringArray[16] = mJsonObject.getString("profilepic");
                        myStringArray[17] = mJsonObject.getString("father_name");
                        myStringArray[18] = mJsonObject.getString("father_age");
                        myStringArray[19] = mJsonObject.getString("mother_name");
                        myStringArray[20] = mJsonObject.getString("mother_age");
                        myStringArray[21] = mJsonObject.getString("account_number");
                        myStringArray[22] = mJsonObject.getString("id");
                        myStringArray[23] = mJsonObject.getString("passport_image");
                        myStringArray[24] = mJsonObject.getString("i20_image");
                        myStringArray[25] = mJsonObject.getString("gre_score");
                        myStringArray[26] = mJsonObject.getString("gmat_score");
                        myStringArray[27] = mJsonObject.getString("toefl_score");
                        myStringArray[28] = mJsonObject.getString("ielts_score");
                        myStringArray[29] = mJsonObject.getString("pte_score");
                        myStringArray[30] = mJsonObject.getString("universitycount");
                    }*/

                    mJsonObject = mJsonArray.getJSONObject(0);
                    editor.putString("firstname",mJsonObject.getString("firstname"));
                    editor.putString("lastname",mJsonObject.getString("lastname"));
                    editor.putString("gender",mJsonObject.getString("gender"));
                    editor.putString("DOB",mJsonObject.getString("DOB"));//date
                    editor.putString("address",mJsonObject.getString("address"));
                    editor.putString("state",mJsonObject.getString("state"));
                    editor.putString("city",mJsonObject.getString("city"));
                    editor.putString("pincode",mJsonObject.getString("pincode"));
                    editor.putString("nationality",mJsonObject.getString("nationality"));
                    editor.putString("mobilenumber",mJsonObject.getString("mobilenumber"));
                    editor.putString("passportnumber",mJsonObject.getString("passportnumber"));
                    editor.putString("hsc_education",mJsonObject.getString("hsc_education"));//date
                    editor.putString("ug_education",mJsonObject.getString("ug_education"));//date
                    editor.putString("work_from",mJsonObject.getString("work_from"));//date
                    editor.putString("work_to",mJsonObject.getString("work_to"));//date
                    editor.putString("email",mJsonObject.getString("email"));
                    editor.putString("profilepic",mJsonObject.getString("profilepic"));
                    editor.putString("father_name",mJsonObject.getString("father_name"));
                    editor.putString("father_age",mJsonObject.getString("father_age"));
                    editor.putString("mother_name",mJsonObject.getString("mother_name"));
                    editor.putString("mother_age",mJsonObject.getString("mother_age"));
                    editor.putString("account_number",mJsonObject.getString("account_number"));
                    editor.putString("id",mJsonObject.getString("id"));
                    editor.putString("passport_image",mJsonObject.getString("passport_image"));
                    editor.putString("i20_image",mJsonObject.getString("i20_image"));
                    editor.putString("gre_score",mJsonObject.getString("gre_score"));
                    editor.putString("gmat_score",mJsonObject.getString("gmat_score"));
                    editor.putString("toefl_score",mJsonObject.getString("toefl_score"));
                    editor.putString("ielts_score",mJsonObject.getString("ielts_score"));
                    editor.putString("pte_score",mJsonObject.getString("pte_score"));
                    editor.putString("universitycount",mJsonObject.getString("universitycount"));
                    editor.putString("shortlisted_univ",mJsonObject.getString("shortlisted_univ"));
                    editor.putString("seen",mJsonObject.getString("seen"));
                    editor.putString("newnotification",mJsonObject.getString("newnotification"));
                    editor.commit();

                    Intent toMain = new Intent(SignIn.this, MainActivity.class);
                    editor.putString("Login",mJsonObject.getString("id"));
                    editor.commit();
                    dialog.dismiss();
                    /*toMain.putExtra("id", myStringArray[22]);
                    toMain.putExtra("details",myStringArray);*/
                    startActivity(toMain);
                    finish();
                }
                    catch(Exception e)
                    {
                        dialog.dismiss();
                        Toast.makeText(SignIn.this, "Couldn't connect. Make sure that your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();
                    }

            }
        }


        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SignIn.this, PreLoader.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
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
    }

