package com.compunet.stuforia;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class PreLoader extends ActionBarActivity {
    Button login;
    Button register;
    Button  skipfornowBtn;
    FileOutputStream outStream;
    String id;
    String email;
    String password;
    TextView text;
    String myJSON;
    String myStringArray[];
    SharedPreferences prefs;
    ProgressDialog dialog=null;
    SharedPreferences.Editor editor;
    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_loader_try);

        text=(TextView)findViewById(R.id.text);

        String sourceString = "Sign up to the World's Pioneer Higher "+"<b>" +"<i>" + "&nbsp;" +"'M'" + "&nbsp;" + "</i>"+"</b> " + " Education Enablers";
        text.setText(Html.fromHtml(sourceString));

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();
        id= prefs.getString("Login", null);
        if(id!=null)
        {
            email=prefs.getString("emailKey",null);
            password=prefs.getString("PasswordKey",null);
            if(email!=null&&password!=null)
            {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("email",email));
                nameValuePairs.add(new BasicNameValuePair("password", password));
                StrictMode.setThreadPolicy(policy);
                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://www.venbaventures.com/stuforia/login.php");
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    String responseStr = EntityUtils.toString(response.getEntity());
                    String result = responseStr.replaceAll("\\s+", "");
                    if (result.equals("-1")) {
                        Intent toSignIn = new Intent(this, SignIn.class);
                        startActivity(toSignIn);
                        // Toast.makeText(this, "Your UserName/Password Wrong\nplease verify ", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            myJSON = result.toString();
                            JSONArray mJsonArray = new JSONArray(myJSON);
                            JSONObject mJsonObject = new JSONObject();
                           /* myStringArray = new String[100];*/

                                //Store all the user details in Shared Preference
                                mJsonObject = mJsonArray.getJSONObject(0);
                                editor.putString("firstname",mJsonObject.getString("firstname"));
                                editor.putString("lastname",mJsonObject.getString("lastname"));
                                editor.putString("gender",mJsonObject.getString("gender"));
                                editor.putString("DOB",mJsonObject.getString("DOB"));
                                editor.putString("address",mJsonObject.getString("address"));
                                editor.putString("state",mJsonObject.getString("state"));
                                editor.putString("city",mJsonObject.getString("city"));
                                editor.putString("pincode",mJsonObject.getString("pincode"));
                                editor.putString("nationality",mJsonObject.getString("nationality"));
                                editor.putString("mobilenumber",mJsonObject.getString("mobilenumber"));
                                editor.putString("passportnumber",mJsonObject.getString("passportnumber"));
                                editor.putString("hsc_education",mJsonObject.getString("hsc_education"));
                                editor.putString("ug_education",mJsonObject.getString("ug_education"));
                                editor.putString("work_from",mJsonObject.getString("work_from"));
                                editor.putString("work_to",mJsonObject.getString("work_to"));
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

                                /*mJsonObject = mJsonArray.getJSONObject(i);
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
                                myStringArray[30]=mJsonObject.getString("universitycount");*/

                            Intent toMain = new Intent(PreLoader.this, MainActivity.class);

                            editor.putString("Login", mJsonObject.getString("id"));
                            editor.commit();

                            startActivity(toMain);
                            finish();
                        } catch (Exception h) {
                           // Toast.makeText(PreLoader.this, "Error:" + h, Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e) {
                   // Toast.makeText(PreLoader.this, "Error:" + e, Toast.LENGTH_SHORT).show();
                    Toast.makeText(PreLoader.this,"Couldn't connect. Make sure that your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();
                }
            }
        }


        login=(Button)findViewById(R.id.button_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toLogin=new Intent(getApplicationContext(),SignIn.class);
                startActivity(toLogin);
            }
        });
        skipfornowBtn=(Button)findViewById(R.id.button_skip_for_now);
        skipfornowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline()) {
                    nameValuePairs = new ArrayList<NameValuePair>();
                    nameValuePairs.add(new BasicNameValuePair("email", "skip_for_now"));
                    StrictMode.setThreadPolicy(policy);
                    new SkipForNow().execute("");


                }
            else{
                    Toast.makeText(getApplicationContext(),"Couldn't connect. Make sure that your phone has an internet connection and try again.",Toast.LENGTH_SHORT).show();
                }

            }
        });



        register=(Button)findViewById(R.id.button_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toRegister=new Intent(getApplicationContext(),SignUp.class);
                startActivity(toRegister);
            }
        });
        getSupportActionBar().hide();
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //window.setStatusBarColor(this.getResources().getColor(R.color.primary));
        };

    }

    private class SkipForNow extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute()
        {
            dialog = ProgressDialog.show(PreLoader.this, "",
                    "Loading...", true);
        }
        @Override
        protected String doInBackground(String... params) {
          String  result=null;
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://www.venbaventures.com/stuforia/login.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                String responseStr = EntityUtils.toString(response.getEntity());
                 result = responseStr.replaceAll("\\s+", "");
                return  result;
            } catch (Exception e) {
                result="internetFailed";
                //Toast.makeText(PreLoader.this,"some problem", Toast.LENGTH_SHORT).show();

            }
            return  result;
        }
        @Override
        protected void onPostExecute(String result) {
            if(result.equals("internetFailed")){
                Toast.makeText(PreLoader.this, "Couldn't connect. Make sure that your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();
            }
            if (result.equals("-1")) {
                Intent toMain = new Intent(PreLoader.this, SignIn.class);
                startActivity(toMain);
            } else{
                Intent toSkipfornow = new Intent(getApplicationContext(), MainActivity.class);
                /*toSkipfornow.putExtra("id", "skip_for_now");
                toSkipfornow.putExtra("university_count",result);*/
                editor.putString("id", "skip_for_now");
                editor.putString("universitycount", result);
                 editor.commit();
                startActivity(toSkipfornow);
            }
            dialog.dismiss();

        }


        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }


















    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pre_loader, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
