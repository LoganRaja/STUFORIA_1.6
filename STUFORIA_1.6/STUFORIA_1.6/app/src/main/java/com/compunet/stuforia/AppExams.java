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
public class AppExams extends Fragment {
    public AppExams() {
        // Required empty public constructor
    }
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
     Button saveBtn;
    String id,output;
    EditText gre;
    float gre_entered,gmat_entered;
    EditText gmat;
    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    ProgressDialog dialog = null;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_app_exams_try, container, false);


        prefs = PreferenceManager.getDefaultSharedPreferences(v.getContext());
        editor = prefs.edit();
        id =prefs.getString("id", null);
        saveBtn=(Button)v.findViewById(R.id.save);
        gre=(EditText)v.findViewById(R.id.gre_competitive);
        gmat=(EditText)v.findViewById(R.id.gmat_competitive);
        if(prefs.getString("gre_score",null).equals("0"))
        {
            gre.setText("");
        }
        else
        {
           gre.setText(prefs.getString("gre_score",null));
        }
        if(prefs.getString("gmat_score",null).equals("0"))
        gmat.setText("");
        else
        {
            gmat.setText(prefs.getString("gmat_score",null));
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(gre.getText().toString().isEmpty())){gre_entered= Float.parseFloat(gre.getText().toString());}
                if(!(gmat.getText().toString().isEmpty())){gmat_entered= Float.parseFloat(gmat.getText().toString());}

                    if(!(gre.getText().toString().isEmpty())&&(gre_entered<260 || gre_entered>340)){Toast.makeText(getActivity(),"Invalid GRE score.", Toast.LENGTH_SHORT).show();}
                    else if(!(gmat.getText().toString().isEmpty())&&(gmat_entered<200 || gmat_entered>800)){Toast.makeText(getActivity(),"Invalid GMAT score.", Toast.LENGTH_SHORT).show();}
                    else{
                    nameValuePairs.add(new BasicNameValuePair("id", id));
                    nameValuePairs.add(new BasicNameValuePair("type", "gre"));
                    nameValuePairs.add(new BasicNameValuePair("gre_score", gre.getText().toString()));
                    nameValuePairs.add(new BasicNameValuePair("gmat_score", gmat.getText().toString()));
                    StrictMode.setThreadPolicy(policy);
                    new ScoreUpdate().execute("");
                    }
                }
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
                //   Toast.makeText(getActivity(), "Your score updated successfully" + output, Toast.LENGTH_SHORT).show();
                editor.putString("gre_score", gre.getText().toString());
                editor.putString("gmat_score", gmat.getText().toString());
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
