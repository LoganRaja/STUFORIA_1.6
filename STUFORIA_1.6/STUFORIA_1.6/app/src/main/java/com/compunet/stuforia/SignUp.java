package com.compunet.stuforia;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
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

import java.util.ArrayList;
import java.util.regex.Pattern;


public class SignUp extends ActionBarActivity {
    EditText fullNameEditText;
    EditText email;
    TextView terms;
    TextView privacy_policy;
    EditText phone;
    EditText password;
    EditText cnfPassword;
    public String jsonString=null;
    public View rootView;
    Button registerBtn;
    ArrayList<NameValuePair> nameValuePairs ;
    ProgressDialog dialog = null;
    TextView linearLayout;
    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
    public final Pattern PASS_ADDRESS_PATTERN = Pattern.compile("[A-Z]{0,1}" +"[0-9][0-9]{1}" +"\\ " +"[0-9][0-9]{4}");
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_try);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fullNameEditText=(EditText)findViewById(R.id.fullnameInput_signUp);
        email=(EditText)findViewById(R.id.emailInput_signUp);
        phone=(EditText)findViewById(R.id.phoneInput_signUp);
        password=(EditText)findViewById(R.id.passwordInput_SignUp);
        cnfPassword=(EditText)findViewById(R.id.cnfPassInput_signUp);
        terms=(TextView)findViewById(R.id.terms);
        privacy_policy=(TextView)findViewById(R.id.privacy_policy);
        registerBtn=(Button)findViewById(R.id.signUpBtn_signUp);
        linearLayout=(TextView)findViewById(R.id.duplicateUser);
        privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignUp.this, Privacy.class);
                startActivity(intent);

            }
        });

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignUp.this, Terms.class);
                startActivity(intent);

            }
        });


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailId = email.getText().toString();
                String pass = password.getText().toString();
                String cnfPass = cnfPassword.getText().toString();
                String fullName = fullNameEditText.getText().toString();
                String phoneNo = phone.getText().toString();
               if(emailId.isEmpty())
               {
                   email.setError("Please fill this field !");
               }
               else if(emailId!=null) {

                    if (!checkEmail(emailId)) {
                        Toast.makeText(SignUp.this, "Invalid email ID", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        linearLayout.setVisibility(View.GONE);
                        if (pass.isEmpty()) {
                            password.setError("Please fill this field !");
                        } else {
                            if (cnfPass.isEmpty()) {
                                cnfPassword.setError("Please fill this field !");
                            } else {
                                if (phoneNo.isEmpty()) {
                                    phone.setError("Please fill this field !");
                                } else {
                                    if (fullName.isEmpty()) {
                                        fullNameEditText.setError("Please fill this field !");
                                    } else {

                                        if (cnfPass.equals(pass)) {
                                               if(phone.getText().toString().length()!=10)
                                               {
                                                   Toast.makeText(SignUp.this, "Please enter valid phone number.. ", Toast.LENGTH_SHORT).show();
                                               }
                                            else
                                               {
                                                   nameValuePairs = new ArrayList<NameValuePair>();
                                                   nameValuePairs.add(new BasicNameValuePair("fullname", fullName));
                                                   nameValuePairs.add(new BasicNameValuePair("email", emailId));
                                                   nameValuePairs.add(new BasicNameValuePair("phone", phoneNo));
                                                   nameValuePairs.add(new BasicNameValuePair("password", pass));
                                                   StrictMode.setThreadPolicy(policy);
                                                   new UserSignUp().execute("");
                                               }


                                        }

                                        else {
                                            Toast.makeText(SignUp.this, "Your password is doesn't match", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
        return true;
    }


    private class UserSignUp extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute()
        {
            dialog = ProgressDialog.show(SignUp.this, "",
                    "Registering...", true);
        }
        @Override
        protected String doInBackground(String... params) {
            String out="0";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://www.venbaventures.com/stuforia/register.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                String responseStr = EntityUtils.toString(response.getEntity());
                 out = responseStr.replaceAll("\\s+", "");
                 return out;
            }
            catch (Exception e)
            {
                dialog.dismiss();
                out="internetFailed";
            }
           return  out;
        }
        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            if(result.equals("internetFailed")){
                Toast.makeText(SignUp.this, "Couldn't connect. Make sure that your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();
            }
            if (result.equals("1")) {
                //Toast.makeText(SignUp.this, "Sign up success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUp.this, SignIn.class);
                startActivity(intent);
                finish();
            } else if (result.equals("0"))
              Toast.makeText(SignUp.this, "Sign up failed", Toast.LENGTH_SHORT).show();
            else if (result.equals("-1")) {
                linearLayout.setVisibility(View.VISIBLE);
            }
        }


        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }




    private boolean checkEmail(String email){
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
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
