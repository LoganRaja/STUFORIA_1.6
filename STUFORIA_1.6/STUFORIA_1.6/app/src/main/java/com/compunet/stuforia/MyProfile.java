package com.compunet.stuforia;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.compunet.stuforia.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*import static com.google.android.gms.internal.zzhl.runOnUiThread;*/

public class MyProfile extends Fragment {

    ImageView viewImage;
    LinearLayout edit;
    private File image;
    Animation myAnim;
    public static final int CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE = 1777;
    private int PICK_IMAGE_REQUEST = 1;
    public MyProfile() {
        // Required empty public constructor
    }


    TextView name,designation,email,phone,dob,gender,passportnumber,pincode,accountnumber;

    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    String myJSON;
    ProgressDialog dialog = null;
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    String id;
    Bitmap myBitmap;
    ArrayList<NameValuePair> nameValuePairs;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile_try, container, false);
        viewImage = (ImageView) view.findViewById(R.id.viewImage);
        name=(TextView) view.findViewById(R.id.profile_name);
        designation=(TextView) view.findViewById(R.id.designation);
        phone=(TextView) view.findViewById(R.id.phone_number);
        email=(TextView) view.findViewById(R.id.profile_emailid);
        dob=(TextView) view.findViewById(R.id.dob);
        gender=(TextView) view.findViewById(R.id.gender);
        passportnumber=(TextView) view.findViewById(R.id.passportnumber);
        accountnumber=(TextView) view.findViewById(R.id.accountnumber);
        pincode=(TextView) view.findViewById(R.id.pincode);
        edit=(LinearLayout) view.findViewById(R.id.edit);
        viewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), EditProfile.class);
                startActivity(i);
            }
        });
        prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        editor = prefs.edit();
        return view;
    }



    public void onPause() {
        super.onPause();

    }
    @Override
    public void onResume() {
        super.onResume();
        viewImage.setImageResource(R.drawable.loading_white_old);
        myBitmap=null;
        new ImageLoad().execute(prefs.getString("profilepic", null));
        Log.e("TAG","Resume");
        if(prefs.getString("firstname",null).equals("")){
            name.setText("N/A");
        } else
            name.setText(prefs.getString("firstname",null)+ " "+prefs.getString("lastname",null));
        if(prefs.getString("state",null).equals("")) {
            designation.setText("N/A");
        }
        else
            designation.setText(prefs.getString("state",null));
        if(prefs.getString("email",null).equals("")){
            email.setText("N/A");
        }
        else
            email.setText(prefs.getString("email",null));
        if(prefs.getString("DOB",null).equals("0000-00-00")){
            dob.setText("N/A");
        }
        else
            dob.setText(convertDateFormat(prefs.getString("DOB",null)));
        if(prefs.getString("mobilenumber",null).equals("")){
            phone.setText("N/A");
        }
        else
            phone.setText(prefs.getString("mobilenumber",null));
        if(prefs.getString("gender", null).equals("")){
            gender.setText("N/A");
        }
        else
            gender.setText(prefs.getString("gender",null));
        if(prefs.getString("passportnumber",null).equals("")){
            passportnumber.setText("N/A");
        }
        else
            passportnumber.setText(prefs.getString("passportnumber",null));
        if(prefs.getString("account_number",null).equals("")){
            accountnumber.setText("N/A");
        }
        else
            accountnumber.setText(prefs.getString("account_number",null));
        if(prefs.getString("pincode",null).equals("0")){
            pincode.setText("N/A");
        }
        else
            pincode.setText(prefs.getString("pincode",null));
    }
    public String convertDateFormat(String date){
        String newDate="";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat changed = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            Date modifiedDate = dateFormat.parse(date);
            newDate =  changed.format(modifiedDate);
        } catch (ParseException pe) {
            newDate="Exception";
        }
        Log.e("date convert",newDate);
        return newDate;
    }




    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery","Set Default", "Cancel"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    File imagesFolder = new File(Environment.getExternalStoragePublicDirectory(new String("Stuforia")), "ProfilePic");
                    imagesFolder.mkdirs();
                    imagesFolder.setWritable(true);
                    image = new File(imagesFolder, "dp1.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image));
                    intent.putExtra("return-data", true);
                    startActivityForResult(intent, CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);
                }
                else if (options[item].equals("Choose from Gallery")) {
                    Intent myIntent = new Intent(getActivity(), CropActivity.class);
                    myIntent.putExtra("tag", "gallery");
                    getActivity().startActivity(myIntent);
                }
                else if (options[item].equals("Set Default")) {
                    nameValuePairs = new ArrayList<NameValuePair>();
                    nameValuePairs.add(new BasicNameValuePair("image_type", "profliepic"));
                    nameValuePairs.add(new BasicNameValuePair("id",prefs.getString("id", null)));
                    StrictMode.setThreadPolicy(policy);
                    new Removeprofilepic().execute("");
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }
        });
        builder.show();
    }
    private class Removeprofilepic extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute()
        {
            dialog = ProgressDialog.show(getActivity(), "",
                    "Removing...", true);
        }
        @Override
        protected String doInBackground(String... params) {
            String responseStr=null;
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://www.venbaventures.com/stuforia/remove_documents.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());
                responseStr = responseStr.replaceAll("\\s+", "");
                Log.e("error", responseStr);
                return responseStr;
            }
            catch (Exception e) {
                Log.e("error","catch");
                dialog.dismiss();
            }
            return responseStr;
        }
        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            if(result.equals("success")){
                editor.putString("profilepic", "http://www.venbaventures.com/stuforia/profilepic/default.jpg");
                editor.commit();
                if(!prefs.getString("id", null).equals("skip_for_now")) {
                    viewImage.setImageResource(R.drawable.loading_white);
                    myBitmap=null;
                    new ImageLoad().execute(prefs.getString("profilepic", null));
                }
            }
        }


        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE  && resultCode == Activity.RESULT_OK) {
            try {
                Intent myIntent = new Intent(getActivity(), CropActivity.class);
                myIntent.putExtra("tag", "camera");
                getActivity().startActivity(myIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private class ImageLoad extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute()
        {
            myAnim    = AnimationUtils.loadAnimation(getActivity(), R.anim.anim);
            viewImage.startAnimation(myAnim);
            //dialog = ProgressDialog.show(getActivity(), "","Loading Data...", true);
        }
        @Override
        protected String doInBackground(String... params) {

            try {
                java.net.URL url = new java.net.URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                myBitmap = BitmapFactory.decodeStream(input);
            } catch (IOException e) {
               //dialog.dismiss();

                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
           //  dialog.dismiss();

            viewImage.clearAnimation();
            myAnim.setAnimationListener(null);
            viewImage.setAnimation(null);
            viewImage.setImageBitmap(myBitmap);
        }
        @Override
        protected void onProgressUpdate(Void... values) {
        }

    }

}
