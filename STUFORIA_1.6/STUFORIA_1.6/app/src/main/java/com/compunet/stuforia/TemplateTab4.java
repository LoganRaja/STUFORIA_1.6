package com.compunet.stuforia;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class TemplateTab4 extends Fragment {
    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    String myJSON;
    String myStringArray[];
    ProgressDialog dialog = null;
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    LinearLayout fs,fs_v;
    String[] details;
    String id;
    String result=null,url;
    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    public TemplateTab4() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_template_tab4_try, container, false);
      /*fs_v=(LinearLayout)view.findViewById(R.id.lor_v);*/
        /*fs=(LinearLayout)view.findViewById(R.id.lor);*/


        return view;
    }



}
