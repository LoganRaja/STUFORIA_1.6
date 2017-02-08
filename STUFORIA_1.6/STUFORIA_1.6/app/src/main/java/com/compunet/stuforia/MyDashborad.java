package com.compunet.stuforia;


import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import com.compunet.stuforia.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyDashborad extends Fragment {

    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_GALLERY = 2;
    Bitmap result;
    ImageView dp;
    ImageView viewImage1;
    ImageView viewImage;
    ImageView logo;
    ImageView relativeLayout;
    ViewPager pager;
    Animation myAnim;
    LinearLayout myScores;

    LinearLayout l1,l2,l3,l4,l5,l6,l7,l8;
    RelativeLayout dashboard_1r1,dashboard_1r2,dashboard_1r;
    RelativeLayout dashboard_2r1,dashboard_2r2,dashboard_2r;
    RelativeLayout dashboard_3r1,dashboard_3r2,dashboard_3r;
    ImageView dashboard_2i1,dashboard_1i1,dashboard_3i1;
    TextView dashboard_2t1,dashboard_1t1,dashboard_3t1;

    LinearLayout shortUniv;
    RelativeLayout viewImageLayout;
    RelativeLayout myDashboard;
    RelativeLayout viewImage2;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Before VISA", "After VISA"};
    int Numboftabs = 2;
    private Uri fileUri;
    private File image;
    public static final int CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE = 1777;
    private int PICK_IMAGE_REQUEST = 1;
    String id;
    String sourceFileUri;
    String ar[];
    String profilepic[];
    Bitmap myBitmap;
    ProgressDialog dialog;
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    ArrayList<NameValuePair> nameValuePairs;
    public MyDashborad() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            /*id = activity.getMyData();
        profilepic=activity.myprofile();*/
        //passid(id);



        View view = inflater.inflate(R.layout.fragment_my_dashborad_try, container, false);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        l2 = (LinearLayout) view.findViewById(R.id.l2);
        l3= (LinearLayout) view.findViewById(R.id.l3);
        l8= (LinearLayout) view.findViewById(R.id.l8);
       /* l4= (LinearLayout) view.findViewById(R.id.l4);
        l5= (LinearLayout) view.findViewById(R.id.l5);
        l6= (LinearLayout) view.findViewById(R.id.l6);
        l7= (LinearLayout) view.findViewById(R.id.l7);
        l8= (LinearLayout) view.findViewById(R.id.l8);*/

        dashboard_1r= (RelativeLayout) view.findViewById(R.id.dashboard_1r);
        dashboard_1r1= (RelativeLayout) view.findViewById(R.id.dashboard_1r1);
        dashboard_1r2= (RelativeLayout) view.findViewById(R.id.dashboard_1r2);

        dashboard_2r= (RelativeLayout) view.findViewById(R.id.dashboard_2r);
        dashboard_2r1= (RelativeLayout) view.findViewById(R.id.dashboard_2r1);
        dashboard_2r2= (RelativeLayout) view.findViewById(R.id.dashboard_2r2);

        dashboard_3r= (RelativeLayout) view.findViewById(R.id.dashboard_3r);
        dashboard_3r1= (RelativeLayout) view.findViewById(R.id.dashboard_3r1);
        dashboard_3r2= (RelativeLayout) view.findViewById(R.id.dashboard_3r2);

        dashboard_2i1= (ImageView) view.findViewById(R.id.dashboard_2i1);
        dashboard_1i1= (ImageView) view.findViewById(R.id.dashboard_1i1);
        dashboard_3i1= (ImageView) view.findViewById(R.id.dashboard_3i1);

        dashboard_1t1= (TextView) view.findViewById(R.id.dashboard_1t1);
        dashboard_3t1= (TextView) view.findViewById(R.id.dashboard_3t1);


        prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        editor = prefs.edit();







       /* LinearLayout.LayoutParams paramsmain2 = (LinearLayout.LayoutParams) l2.getLayoutParams();
        paramsmain2.height =(int) (height * .2);
        LinearLayout.LayoutParams paramsmain3 = (LinearLayout.LayoutParams) l3.getLayoutParams();
        paramsmain3.height =(int) (height * .35);
        LinearLayout.LayoutParams paramsmain8 = (LinearLayout.LayoutParams) l8.getLayoutParams();
        paramsmain8.height =(int) (height * .45);*/




       /* LinearLayout.LayoutParams paramsmain3 = (LinearLayout.LayoutParams) l3.getLayoutParams();
        paramsmain3.height =(int) (height * .3);*/

        /*LinearLayout.LayoutParams paramsmain4 = (LinearLayout.LayoutParams) l4.getLayoutParams();
        paramsmain4.height =(int) (height * .1);

        LinearLayout.LayoutParams paramsmain5 = (LinearLayout.LayoutParams) l5.getLayoutParams();
        paramsmain5.height =(int) (height * .2);


        LinearLayout.LayoutParams paramsmain6 = (LinearLayout.LayoutParams) l6.getLayoutParams();
        paramsmain6.height =(int) (height * .1);


        LinearLayout.LayoutParams paramsmain7 = (LinearLayout.LayoutParams) l7.getLayoutParams();
        paramsmain7.height =(int) (height * .2);*/

        /*LinearLayout.LayoutParams paramsmain8 = (LinearLayout.LayoutParams) l8.getLayoutParams();
        paramsmain8.height =(int) (height * .2);
*/
       // dashboard_r.getLayoutParams().height =(int) (height * 0.3);
        dashboard_1r1.getLayoutParams().height =(int) (height * 0.1);
        dashboard_1r2.getLayoutParams().height =(int) (height * 0.25);
        dashboard_2i1.getLayoutParams().height =(int) (height * 0.2);

        dashboard_2r1.getLayoutParams().height =(int) (height * 0.1);
        dashboard_2r2.getLayoutParams().height =(int) (height * 0.25);

        dashboard_3r1.getLayoutParams().height =(int) (height * 0.1);
        dashboard_3r2.getLayoutParams().height =(int) (height * 0.25);


        ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(dashboard_2i1.getLayoutParams());
        marginParams.setMargins(0, 0, 0, -(int) (height * 0.15));
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);
        dashboard_2i1.setLayoutParams(layoutParams);

        dashboard_1i1.getLayoutParams().height =(int) (height * 0.05);
        dashboard_3i1.getLayoutParams().height =(int) (height * 0.05);

        dashboard_1t1.getLayoutParams().height =(int) (height * 0.1);
        dashboard_3t1.getLayoutParams().height =(int) (height * 0.1);


        dashboard_1r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShortListedUniversity.class);
                //intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        dashboard_3r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CompetitiveExams.class);
                //intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapter(getFragmentManager(), Titles, Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) view.findViewById(R.id.tabs);

        tabs.setCustomTabView(R.layout.custom_tab,R.id.customText);
        tabs.setSmoothScrollingEnabled(false);
        tabs.setViewPager(pager);

        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.white);
            }
        });
        dashboard_2i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        /*viewImage = (ImageView) view.findViewById(R.id.viewImage);
        prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        editor = prefs.edit();
        myScores = (LinearLayout) view.findViewById(R.id.myScores);
        shortUniv = (LinearLayout) view.findViewById(R.id.shortUniv);
        viewImage1 = (ImageView) view.findViewById(R.id.viewImage1);
        relativeLayout = (ImageView) view.findViewById(R.id.relativeLayout);
        myDashboard = (RelativeLayout) view.findViewById(R.id.myDashboard);
        logo = (ImageView) view.findViewById(R.id.logo);

        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int px = Math.round(120 * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        int px_half=Math.round(px/2);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        int imageHeight=height/20;

        viewImage.getLayoutParams().height = 5*imageHeight;
        viewImage.requestLayout();

        logo.getLayoutParams().height = 2*imageHeight;
        logo.requestLayout();


        *//*relativeLayout.getLayoutParams().height = 2*imageHeight;
        relativeLayout.requestLayout();*//*

        myDashboard.getLayoutParams().height = imageHeight*9;
        myDashboard.requestLayout();

        *//*ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(dp.getLayoutParams());
        marginParams.setMargins(0, 200, 0, 0);
        dp.setLayoutParams(marginParams);*//*



        viewImage2 = (RelativeLayout) view.findViewById(R.id.viewImage2);
        int viewImage2_height=viewImage2.getHeight();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(width / 3, (myDashboard.getLayoutParams().height)/3, 0, 0);
        viewImage2.setLayoutParams(params);

        viewImage2.setMinimumWidth(width/3);
        viewImage1.setMinimumWidth(width/3);

        shortUniv.setMinimumWidth(width/3);
        myScores.setMinimumWidth(width/3);

        shortUniv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShortListedUniversity.class);
                //intent.putExtra("id", id);
                startActivity(intent);
            }
        });


        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapter(getFragmentManager(), Titles, Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) view.findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.white);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

        viewImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();

            }
        });

        myScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tomyScores = new Intent(getActivity(), CompetitiveExams.class);
               *//* tomyScores.putExtra("id", id);
                tomyScores.putExtra("details", profilepic);*//*
                startActivity(tomyScores);
            }
        });*/

        return view;
    }

    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery","Set Default","Cancel"};
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
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent myIntent = new Intent(getActivity(), CropActivity.class);
                    myIntent.putExtra("tag","gallery");
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
                    dashboard_2i1.setImageResource(R.drawable.loading_white);
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
    /*  public String passid(String id) {
          return id;
      }*/

    @Override
    public void onPause() {
        super.onPause();
        //Toast.makeText(getActivity(),"Pause",Toast.LENGTH_LONG).show();
        Log.e("tag", "Pause");
    }

    @Override
    public void onResume() {
        super.onResume();
        dashboard_2i1.setImageResource(R.drawable.loading_white);
        myBitmap=null;
        new ImageLoad().execute(prefs.getString("profilepic", null));
    }

    private class ImageLoad extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute()
        {
            myAnim   = AnimationUtils.loadAnimation(getActivity(), R.anim.anim);
            dashboard_2i1.startAnimation(myAnim);
            // dialog = ProgressDialog.show(getActivity(), "", "Update ...please wait...", true);
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
                // dialog.dismiss();
                e.printStackTrace();

            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            //  dialog.dismiss();

            dashboard_2i1.clearAnimation();
            myAnim.setAnimationListener(null);
            dashboard_2i1.setAnimation(null);
            dashboard_2i1.setImageBitmap(myBitmap);

        }
        @Override
        protected void onProgressUpdate(Void... values) {
        }

    }

}





