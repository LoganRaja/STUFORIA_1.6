package com.compunet.stuforia;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class EnglishExams extends Fragment {


    public EnglishExams() {
        // Required empty public constructor
    }
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    Button saveMark;
    String id,output;
    EditText toefl;
    float toefl_entered,ielts_entered,pte_entered;
    EditText ielts;
    EditText pte;
    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    ProgressDialog dialog = null;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View  v=inflater.inflate(R.layout.fragment_english_exams_try, container, false);

        prefs = PreferenceManager.getDefaultSharedPreferences(v.getContext());
        editor = prefs.edit();
        id = prefs.getString("id", null);
        saveMark=(Button)v.findViewById(R.id.savemark);
        toefl=(EditText)v.findViewById(R.id.toefl);
        ielts=(EditText)v.findViewById(R.id.ielts);
        pte=(EditText)v.findViewById(R.id.pte);
        if(prefs.getString("toefl_score",null).equals("0"))
        {
            toefl.setText("");
        }
        else
        {
            toefl.setText(prefs.getString("toefl_score",null));
        }
        if(prefs.getString("ielts_score",null).equals("0"))
        {
            ielts.setText("");
        }
        else
        {
            ielts.setText(prefs.getString("ielts_score",null));
        }
        if(prefs.getString("pte_score",null).equals("0"))
        {
            pte.setText("");
        }
        else
        {
            pte.setText(prefs.getString("pte_score",null));
        }



        saveMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(toefl.getText().toString().isEmpty())){toefl_entered= Float.parseFloat(toefl.getText().toString());}
                if(!(ielts.getText().toString().isEmpty())){ielts_entered= Float.parseFloat(ielts.getText().toString());}
                if(!(pte.getText().toString().isEmpty())){pte_entered= Float.parseFloat(pte.getText().toString());}


                    if(!(toefl.getText().toString().isEmpty())&&(toefl_entered<0 || toefl_entered>120)){Toast.makeText(getActivity(),"Invalid TOEFL score.", Toast.LENGTH_SHORT).show();}
                    else if(!(ielts.getText().toString().isEmpty())&&(ielts_entered<0 || ielts_entered>9)){Toast.makeText(getActivity(),"Invalid IELTS score.", Toast.LENGTH_SHORT).show();}
                    else if(!(pte.getText().toString().isEmpty())&&(pte_entered<10 || pte_entered>90)){Toast.makeText(getActivity(),"Invalid PTE score.", Toast.LENGTH_SHORT).show();}
                    else{
                    nameValuePairs.add(new BasicNameValuePair("id", id));
                    nameValuePairs.add(new BasicNameValuePair("type", "toefl"));
                    nameValuePairs.add(new BasicNameValuePair("toefl_score", toefl.getText().toString()));
                    nameValuePairs.add(new BasicNameValuePair("ielts_score", ielts.getText().toString()));
                    nameValuePairs.add(new BasicNameValuePair("pte_score", pte.getText().toString()));
                    StrictMode.setThreadPolicy(policy);
                    new ScoreUpdate().execute("");}}
        });
        return v;
    }
     private class ScoreUpdate extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute()
        {
            dialog = ProgressDialog.show(getActivity(), "",
                    "Scores Updating...", true);
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://www.venbaventures.com/stuforia/update_score.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                String responseStr = EntityUtils.toString(response.getEntity());
                output = responseStr.replaceAll("\\s+", "");
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
            dialog.dismiss();
            if (result.equals("correct")) {
                // Toast.makeText(getActivity(), "Your score updated successfully" + output, Toast.LENGTH_SHORT).show();
                editor.putString("toefl_score", toefl.getText().toString());
                editor.putString("ielts_score", ielts.getText().toString());
                editor.putString("pte_score", pte.getText().toString());
                editor.commit();
                Toast.makeText(getActivity(), "Your score has been updated successfully", Toast.LENGTH_SHORT).show();
            }
            if(result.equals("internetFailed")){
                Toast.makeText(getActivity(), "Couldn't connect. Make sure that your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();
            }
        }


        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

}
