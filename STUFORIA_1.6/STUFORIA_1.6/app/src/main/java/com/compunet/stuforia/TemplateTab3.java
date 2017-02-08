package com.compunet.stuforia;


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
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TemplateTab3 extends Fragment {

    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    String myJSON;
    String myStringArray[];
    ProgressDialog dialog = null;
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    Button fs,fs_v;
    String[] details;
    String[] userdetails=null;
    String id;
    String result=null,url;
    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

    SharedPreferences prefs;
    SharedPreferences.Editor editor;


    public TemplateTab3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_template_tab3_try, container, false);
        fs=(Button)view.findViewById(R.id.aosv);
        fs_v=(Button)view.findViewById(R.id.aosv_v);
        prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        editor = prefs.edit();
        id=prefs.getString("id", null);


        fs_v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id.equals("skip_for_now"))
                {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("UNREGISTERED USER")
                            .setMessage("Please sign up to use this feature.")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setNegativeButton(android.R.string.ok, null).show();
                }
                else {
                    Intent intent = new Intent(getActivity(), TemplateWebView.class);
                    intent.putExtra("viewpdf","http://www.venbaventures.com/stuforia/Stuforia_pdf/0998927edc5e05cb373925ad0ba5f235.pdf");
                    startActivity(intent);
                }
            }
        });
        fs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id.equals("skip_for_now"))
                {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("UNREGISTERED USER")
                            .setMessage("Please sign up to use this feature.")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setNegativeButton(android.R.string.ok, null).show();
                }

                else {
                    if (userdetails==null)
                    {
                        Intent intent = new Intent(getActivity(), DetailsGetActivity.class);
                        intent.putExtra("id",id);
                        intent.putExtra("pdf","AOSV");
                        startActivity(intent);
                    } else {
                        nameValuePairs.add(new BasicNameValuePair("id", id));
                        nameValuePairs.add(new BasicNameValuePair("templateType", "AOSV"));
                        nameValuePairs.add(new BasicNameValuePair("name", userdetails[0] + " " + userdetails[1]));
                        nameValuePairs.add(new BasicNameValuePair("father_name", userdetails[2]));
                        nameValuePairs.add(new BasicNameValuePair("address", userdetails[3]));
                        nameValuePairs.add(new BasicNameValuePair("account_number", userdetails[4]));
                        nameValuePairs.add(new BasicNameValuePair("mother_age", userdetails[5]));
                        nameValuePairs.add(new BasicNameValuePair("father_age", userdetails[6]));
                        nameValuePairs.add(new BasicNameValuePair("mother_name", userdetails[7]));
                        StrictMode.setThreadPolicy(policy);
                        new LogIn().execute();
                    }
                }
            }
        });
        return view;
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
        }catch(ActivityNotFoundException e){
            Toast.makeText(getActivity(), "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }
    }
    private class DownloadFile extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute()
        {
            dialog = ProgressDialog.show(getActivity(), "",
                    "pdf download please wait...", true);
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
            dialog = ProgressDialog.show(getActivity(), "",
                    "pdf generate please wait", true);
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

            } catch (Exception e) {
                dialog.dismiss();
                Toast.makeText(getActivity(), "" + e, Toast.LENGTH_LONG).show();
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
                nameValuePairs.add(new BasicNameValuePair("pdf_type", "AOSV"));
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
                Toast.makeText(getActivity(), "" + e, Toast.LENGTH_LONG).show();
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
