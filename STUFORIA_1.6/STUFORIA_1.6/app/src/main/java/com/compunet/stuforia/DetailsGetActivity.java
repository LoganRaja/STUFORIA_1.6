package com.compunet.stuforia;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by connectors on 10/7/2015.
 */
public class DetailsGetActivity extends Activity {
    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    String[] userdetails;
    String result=null,url,id,pdf;
    ProgressDialog dialog = null;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    Button submit;
    EditText studentname,fathername,mothername,fatherage,motherage,studentaddress,accountnumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_details);
        Intent intent=getIntent();

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();


        id=prefs.getString("id", null);
        pdf=intent.getStringExtra("pdf");

        getIntent().removeExtra("pdf");

        studentname=(EditText)findViewById(R.id.student_name);
        studentaddress=(EditText)findViewById(R.id.student_address);
        fathername=(EditText)findViewById(R.id.father_name);
        fatherage=(EditText)findViewById(R.id.father_age);
        mothername=(EditText)findViewById(R.id.mother_name);
        motherage=(EditText)findViewById(R.id.mother_age);
        accountnumber=(EditText)findViewById(R.id.account_number);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .8));
        submit=(Button)findViewById(R.id.submit);
        if(pdf.equals("FS"))
        {
            studentname.setVisibility(View.VISIBLE);
            fathername.setVisibility(View.VISIBLE);
            studentaddress.setVisibility(View.VISIBLE);
            accountnumber.setVisibility(View.VISIBLE);
        }
        else if(pdf.equals("AOSA"))
        {
            studentname.setVisibility(View.VISIBLE);
            fathername.setVisibility(View.VISIBLE);
            fatherage.setVisibility(View.VISIBLE);
            mothername.setVisibility(View.VISIBLE);
            motherage.setVisibility(View.VISIBLE);
        }
        else if(pdf.equals("AOSV"))
        {
            studentname.setVisibility(View.VISIBLE);
            fathername.setVisibility(View.VISIBLE);
            fatherage.setVisibility(View.VISIBLE);
            mothername.setVisibility(View.VISIBLE);
            motherage.setVisibility(View.VISIBLE);
            studentaddress.setVisibility(View.VISIBLE);
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pdf.equals("FS")&&(studentname.getText().toString().isEmpty()||fathername.getText().toString().isEmpty()||studentaddress.getText().toString().isEmpty()||accountnumber.getText().toString().isEmpty()))
                {
                    new AlertDialog.Builder(DetailsGetActivity.this)
                            .setTitle("DETAILS MISSING")
                            .setMessage("Please fill all the fields.")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setNegativeButton(android.R.string.ok, null).show();
                }
              else  if(pdf.equals("AOSA")&&(studentname.getText().toString().isEmpty()||fathername.getText().toString().isEmpty()||fatherage.getText().toString().isEmpty()||mothername.getText().toString().isEmpty()||motherage.getText().toString().isEmpty()))
                {
                    new AlertDialog.Builder(DetailsGetActivity.this)
                            .setTitle("DETAILS MISSING")
                            .setMessage("Please fill all the fields.")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setNegativeButton(android.R.string.ok, null).show();
                }
               else if(pdf.equals("AOSV")&&(studentname.getText().toString().isEmpty()||fathername.getText().toString().isEmpty()||fatherage.getText().toString().isEmpty()||mothername.getText().toString().isEmpty()||motherage.getText().toString().isEmpty()||studentaddress.getText().toString().isEmpty()))
                {
                    new AlertDialog.Builder(DetailsGetActivity.this)
                            .setTitle("DETAILS MISSING")
                            .setMessage("Please fill all the fields.")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setNegativeButton(android.R.string.ok, null).show();
                }
                else
                {
                    nameValuePairs.add(new BasicNameValuePair("id", id));
                    nameValuePairs.add(new BasicNameValuePair("templateType", pdf));
                    nameValuePairs.add(new BasicNameValuePair("name", studentname.getText().toString()));
                    nameValuePairs.add(new BasicNameValuePair("father_name", fathername.getText().toString()));
                    nameValuePairs.add(new BasicNameValuePair("address", studentaddress.getText().toString()));
                    nameValuePairs.add(new BasicNameValuePair("account_number", accountnumber.getText().toString()));
                    nameValuePairs.add(new BasicNameValuePair("mother_age", motherage.getText().toString()));
                    nameValuePairs.add(new BasicNameValuePair("father_age", fatherage.getText().toString()));
                    nameValuePairs.add(new BasicNameValuePair("mother_name", mothername.getText().toString()));
                    new LogIn().execute();
                }

            }
        });
    }
    public void view()
    {
        File pdfFile = new File(Environment.getExternalStoragePublicDirectory(new String("Stuforia")), "TEMPLATE_PDF/"+result);  // -> filename = maven.pdf
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        try{
            startActivity(pdfIntent);
            finish();
        }catch(ActivityNotFoundException e){
           Toast.makeText(DetailsGetActivity.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    private class DownloadFile extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute()
        {
           dialog = ProgressDialog.show(DetailsGetActivity.this, "",
                   "Downloading PDF...", true);
        }
        @Override
        protected String doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            File pdfFolder = new File(Environment.getExternalStoragePublicDirectory(new String("Stuforia")), "TEMPLATE_PDF");
            pdfFolder.mkdirs();
            pdfFolder.setWritable(true);
            File pdfFile = new File(pdfFolder,fileName);
            try {
                pdfFile.createNewFile();
                FileDownload.downloadFile(fileUrl, pdfFile);
            } catch (IOException e) {
                dialog.dismiss();
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(String output) {
            dialog.dismiss();
            view();

        }
    }
    private class LogIn extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute()
        {
            dialog = ProgressDialog.show(DetailsGetActivity.this, "",
                 "Generating PDF...", true);
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                httpclient = new DefaultHttpClient();
                httppost = new HttpPost("http://www.venbaventures.com/stuforia/AffidavitOfSupport-Admission.php"); // make sure the// is correct.
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                response = httpclient.execute(httppost);
                String responseStr = EntityUtils.toString(response.getEntity());
                result = responseStr.replaceAll("\\s+", "");
                url = "http://www.venbaventures.com/stuforia/Stuforia_pdf/";
                if (result != null) {
                    url = url + result;

                }
                else{
                    result="Not Connect";
                }

            } catch (Exception e) {
              dialog.dismiss();
                //Toast.makeText(DetailsGetActivity.this, "" + e, Toast.LENGTH_LONG).show();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String output) {
            dialog.dismiss();
            new UpdateDetails().execute("");
            new DownloadFile().execute(url, result);
        }
    }
    private class UpdateDetails extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute()
        {
            // dialog = ProgressDialog.show(getActivity(), "",
            // "pdf generate please wait", true);
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                nameValuePairs.add(new BasicNameValuePair("id", id));
                nameValuePairs.add(new BasicNameValuePair("pdf_type",pdf));
                nameValuePairs.add(new BasicNameValuePair("name", result));
                httpclient = new DefaultHttpClient();
                httppost = new HttpPost("http://www.venbaventures.com/stuforia/update_template.php"); // make sure the// is correct.
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                response = httpclient.execute(httppost);
                String responseStr = EntityUtils.toString(response.getEntity());
                String   result1 = responseStr.replaceAll("\\s+", "");
                //  url = "http://www.venbaventures.com/stuforia/Stuforia_pdf/";
                if (result1 != null) {
                    // url = url + result;

                }

            } catch (Exception e) {
                // dialog.dismiss();
              //  Toast.makeText(DetailsGetActivity.this, "" + e, Toast.LENGTH_LONG).show();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String output) {
            //   dialog.dismiss();
            // new DownloadFile().execute(url, result);
        }
    }

}
