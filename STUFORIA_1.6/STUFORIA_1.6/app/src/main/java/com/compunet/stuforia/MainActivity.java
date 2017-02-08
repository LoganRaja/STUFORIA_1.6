package com.compunet.stuforia;

import android.app.Activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.Nullable;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.compunet.stuforia.R;
import com.winsontan520.wversionmanager.library.OnReceiveListener;
import com.winsontan520.wversionmanager.library.WVersionManager;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener,ActionBar.TabListener{
    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    String myJSON;
    String myStringArray[];
    ProgressDialog dialog=null;
    String id;

    private File image;
    public static final int CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE = 1777;
    private int PICK_IMAGE_REQUEST = 1;

    private DrawerLayout drawerLayout;
     ExpandableHeightListView listView;
     ExpandableHeightListView listView1;
    private String names[];
    private String names1[];
    Animation myAnim;

    private ActionBarDrawerToggle drawerListener;
    private NavDrawerListAdapter myadapter;
    private NavDrawerListAdapter1 myadapter1;
    ImageView dp;
    Bitmap result;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    String universitycount;
    private Toolbar mToolbar;
    HashMap<String, List<String>> listDataChild;
    private Uri fileUri;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_GALLERY = 2;
    Fragment dashboardFrag;
    Uri dpUri=null;
    Bitmap myBitmap;
    ArrayList<NameValuePair> nameValuePairs;
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private class ImageLoad extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute()
        {
          myAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim);
          dp.startAnimation(myAnim);
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
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
           // dialog.dismiss();

            dp.clearAnimation();
            myAnim.setAnimationListener(null);
            dp.setAnimation(null);
            dp.setImageBitmap(myBitmap);
        }
        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
    String resume="notchangepassword";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_try);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;


       /* RelativeLayout relativeLayout=(RelativeLayout) findViewById(R.id.skip);
        relativeLayout.getLayoutParams().width = width-60;*/
          LinearLayout linearLayout=(LinearLayout)findViewById(R.id.linear2);
        LinearLayout.LayoutParams paramsmain2 = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        paramsmain2.height =(int) (height * .2);

        final WVersionManager versionManager = new WVersionManager(this);
        versionManager.setVersionContentUrl("http://www.venbaventures.com/stuforia/version.txt"); // your update content url, see the response format below
        versionManager.setUpdateUrl("https://play.google.com/store/apps/details?id=com.compunet.stuforia"); // this is the link will execute when update now clicked. default will go to google play based on your package name.
// this mean checkVersion() will not take effect within 10 minutes
        versionManager.checkVersion();
        versionManager.setOnReceiveListener(new OnReceiveListener() {
            @Override
            public boolean onReceive(int status, String result) {
                Log.e("hello", result);

                try {
                    JSONObject mJsonObject = new JSONObject(result);
                    if (versionManager.getCurrentVersionCode() < Integer.parseInt(mJsonObject.getString("version_code"))) {
                        String update=mJsonObject.getString("content");
                        versionManager.setTitle("UPDATE"); // optional
                        versionManager.setMessage(update);// optional
                        versionManager.setAskForRatePositiveLabel("Update Now "); // optional
                        versionManager.setAskForRateNegativeLabel("Later");
                        versionManager.askForRate();
                        return true;
                    }
                } catch (Exception e) {

                }

                return false; // return true if you want to use library's default logic & dialog
            }
        });

          prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
          editor = prefs.edit();


        if(prefs.getString("AleartDialog", null)==null)
        {
            final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create(); //Read Update
            alertDialog.setTitle("HELP DESK");
            alertDialog.setIcon(R.drawable.ic_helpdesk);
            alertDialog.setMessage("Hey,\n\n    USA aspiring students\n\n• Any doubts in University Application form?\n\n• Looking for financial statements, assistance?\n\n• Mail us to info@stuforia.com\n\n");
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Later", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Don't \nshow Again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    editor.putString("AleartDialog","Don't_show_again");
                    editor.commit();
                }
            });
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Mail \nhere", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Queries");
                    intent.putExtra(Intent.EXTRA_TEXT, "");
                    intent.setData(Uri.parse("mailto:info@stuforia.com")); // or just "mailto:" for blank
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                    startActivity(intent);
                }
            });
            alertDialog.show();
        }




if(isOnline()) {
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    id = prefs.getString("id", null);

    /*myStringArray=intent.getStringArrayExtra("details");*/


    int key=0;
     if(!id.equals("skip_for_now")) {
         convertDateFormat(prefs.getString("DOB",null));
     }

    drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawerListener = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.closed) {
        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
             /* Toast.makeText(MainActivity.this, "Drawer Closed",
                        Toast.LENGTH_SHORT).show();*/
            super.onDrawerClosed(drawerView);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            /*   Toast.makeText(MainActivity.this, "Drawer Opened",
                        Toast.LENGTH_SHORT).show();*/
            super.onDrawerOpened(drawerView);
        }
    };



    listView = (ExpandableHeightListView) findViewById(R.id.list_slidermenu);
    listView1 = (ExpandableHeightListView) findViewById(R.id.list_slidermenu1);
    dp = (ImageView) findViewById(R.id.dp);

listView.setExpanded(true);
    listView1.setExpanded(true);
    names = getResources().getStringArray(R.array.values);
    names1 = getResources().getStringArray(R.array.values1);
    expListView = (ExpandableListView) findViewById(R.id.lvExp);
    nameValuePairs = new ArrayList<NameValuePair>();
    prepareListData();


    listAdapter = new ExpandableListAdapter(MainActivity.this, listDataHeader, listDataChild);
    expListView.setAdapter(listAdapter);
    // Listview Group click listener
    expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

        @Override
        public boolean onGroupClick(ExpandableListView parent, View v,
                                    int groupPosition, long id) {
            // Toast.makeText(getApplicationContext(),
            // "Group Clicked " + listDataHeader.get(groupPosition),
            // Toast.LENGTH_SHORT).show();
            return false;
        }
    });

    // Listview Group expanded listener
    expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

        @Override
        public void onGroupExpand(int groupPosition) {
                /*Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();*/
        }
    });

    // Listview Group collasped listener
    expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

        @Override
        public void onGroupCollapse(int groupPosition) {
                /*Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();*/

        }
    });

    // Listview on child click listener
    expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

        @Override
        public boolean onChildClick(ExpandableListView parent, View v,
                                    int groupPosition, int childPosition, long iid) {
            // TODO Auto-generated method stub
               /* Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();*/

            switch (childPosition) {

                case 0:
                    Intent intent1 = new Intent(MainActivity.this, ChangePassword.class);
                    intent1.putExtra("tag","changepassword");
                    startActivity(intent1);
                    resume="changepassword";
                    break;
                case 1:
                    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                    nameValuePairs.add(new BasicNameValuePair("id",id));
                    StrictMode.setThreadPolicy(policy);
                    new LogOut().execute("http://www.venbaventures.com/stuforia/logout.php");
                    break;
            }
            return false;
        }
    });
    //End of Expandable List Handling

    //  listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names));
    myadapter = new NavDrawerListAdapter(this);
    listView.setAdapter(myadapter);

    myadapter1 = new NavDrawerListAdapter1(this);
    listView1.setAdapter(myadapter1);





    dp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            selectImage();
        }
    });

    listView.setOnItemClickListener(this);

    listView1.setOnItemClickListener(this);
    Intent intent=getIntent();

    if (id.equals("skip_for_now")) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        drawerLayout.setDrawerListener(drawerListener);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        getSupportActionBar().setTitle("Guest");
        displayView(0, 1);
    } else {
        if(intent.getStringExtra("tag")!=null)
    {
        drawerLayout.setDrawerListener(drawerListener);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(names[2]);
        displayView(2, 1);
    }else {
            drawerLayout.setDrawerListener(drawerListener);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(names[0]);
            displayView(0, 1);
        }
    }
    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d5da7")));
    // getSupportActionBar().setDisplayShowHomeEnabled(false);
    drawerListener.syncState();
}
        else {
    Toast.makeText(MainActivity.this, "Couldn't connect. Make sure that your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();
        }
    }
    public String convertDateFormat(String date){
        String newDate="";
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat changed = new SimpleDateFormat("dd-MMM-yyyy");
                try {
                    Date  modifiedDate = dateFormat.parse(date);
                        newDate =  changed.format(modifiedDate);
                } catch (ParseException pe) {
                    newDate="Exception";
                }
            return newDate;
    }
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {

        String title = getSupportActionBar().getTitle().toString();
        if (title.equals("Home")) {
            new AlertDialog.Builder(this)
                    .setTitle("Exit")
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        else
        {
            if (title.equals("Guest")) {
                Intent intent = new Intent(MainActivity.this, PreLoader.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
            else
            {
                getSupportActionBar().setTitle("Guest");
                displayView(0, 1);
            }
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if(resume.equals("changepassword"))
        {
            drawerLayout.setAlpha((float) 0.1);
            resume="notchangepassword";
        }

    }
    @Override
    public void onResume() {
        super.onResume();

        if(!id.equals("skip_for_now")) {
            dp.setImageResource(R.drawable.loading_white);
            myBitmap=null;
            new ImageLoad().execute(prefs.getString("profilepic", null));
        }
        drawerLayout.setAlpha(1);
    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerListener.syncState();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.list_slidermenu:
            getSupportActionBar().setTitle(names[position]);
            displayView(position,1);
       break;
            case R.id.list_slidermenu1:
            getSupportActionBar().setTitle(names1[position]);
            displayView(position, 2);
                break;
        }
        drawerLayout.closeDrawers();

    }
    private void displayView(int position,int listview) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        if(listview==1) {
            switch (position) {
                case 0:
                    fragment = new Home();
                    break;
                case 1:
                    fragment = new MyDashborad();
                    break;
                case 2:
                    fragment = new MyProfile();
                    break;
                default:
                    break;
            }
        }
        else {
            switch (position) {
                case 0:
                    fragment = new About();
                    break;
                case 1:
                    fragment = new PrivPol();
                    break;
                case 2:
                    fragment = new TermCond();
                default:
                    break;
            }
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        if(!id.equals("skip_for_now")) {
            Integer seen=Integer.parseInt(prefs.getString("seen", null));
            Integer newnotification=Integer.parseInt(prefs.getString("newnotification", null));
            if (seen<newnotification) {
                MenuItem item = menu.findItem(R.id.action_notification);
                item.setIcon(R.drawable.ic_bank);
            }
        }


        // Associate searchable configuration with the SearchView
        /*SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));*/

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment= null;
        FragmentManager fragmentManager;
        switch (item.getItemId()) {
            /*case R.id.action_search:
            break;*/
            case R.id.action_call:

                if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
                    drawerLayout.closeDrawers();
                }

                // search action
                makeCall();
            break;
            case R.id.action_notification:
                if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
                    drawerLayout.closeDrawers();
                }
                Intent intent=new Intent(MainActivity.this,NewsFeedActivity.class);
                startActivity(intent);
                break;
            case R.id.action_about:
                 fragment=new About();
              fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).commit();
                getSupportActionBar().setTitle(names1[0]);
                if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
                    drawerLayout.closeDrawers();
                }

                break;
            case R.id.action_privacy:

                fragment=new PrivPol();
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).commit();
                getSupportActionBar().setTitle(names1[1]);
                if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
                    drawerLayout.closeDrawers();
                }

                break;
            case R.id.action_terms:
                fragment=new TermCond();
              fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).commit();
                getSupportActionBar().setTitle(names1[2]);
                if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
                    drawerLayout.closeDrawers();
                }
                break;
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");
                String shareBody = "Hey! I am using Stuforia -The best app for USA aspiring Students for pursuing higher studies.\nGo ahead, give it a try! https://play.google.com/store/apps/details?id=com.compunet.stuforia";
                sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Stuforia App ( share link )");
                sendIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sendIntent, "Share via"));
                break;
            case R.id.action_help:
                final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create(); //Read Update
                alertDialog.setTitle("HELP DESK");
                alertDialog.setMessage("Hey,\n\n    USA aspiring students\n\n• Any doubts in University Application form?\n\n• Looking for financial statements, assistance?\n\n• Mail us to info@stuforia.com\n\n");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Later", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Mail here", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Queries");
                        intent.putExtra(Intent.EXTRA_TEXT, "");
                        intent.setData(Uri.parse("mailto:info@stuforia.com")); // or just "mailto:" for blank
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                        startActivity(intent);
                    }
                });
                alertDialog.show();
                break;
            default:
                break;
        }
        if(drawerListener.onOptionsItemSelected(item))
        {
            return true;
        }
    return super.onOptionsItemSelected(item);
    }











    public void makeCall(){
        new AlertDialog.Builder(this)
                .setTitle("COMING SOON")
                .setMessage("This feature will be available soon.")
                .setIcon(android.R.drawable.alert_dark_frame)
                .setNegativeButton(android.R.string.ok, null).show();
    }
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
    private void prepareListData() {

            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<String>>();
        if(id.equals("skip_for_now"))
            listDataHeader.add("skip for now");
        else
        listDataHeader.add(prefs.getString("firstname",null));
            List<String> drawerSubMenu = new ArrayList<String>();
            drawerSubMenu.add("Change password");
            drawerSubMenu.add("Logout");
            listDataChild.put(listDataHeader.get(0), drawerSubMenu);
        }
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery","Set Default", "Cancel"};
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
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
                    Intent myIntent = new Intent(MainActivity.this, CropActivity.class);
                    myIntent.putExtra("tag", "gallery");
                    startActivity(myIntent);
                } else if (options[item].equals("Set Default")) {
                    nameValuePairs = new ArrayList<NameValuePair>();
                    nameValuePairs.add(new BasicNameValuePair("image_type", "profliepic"));
                    nameValuePairs.add(new BasicNameValuePair("id",id));
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
            dialog = ProgressDialog.show(MainActivity.this, "",
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
                if(!id.equals("skip_for_now")) {
                    dp.setImageResource(R.drawable.loading_white);
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
                Intent myIntent = new Intent(MainActivity.this, CropActivity.class);
                myIntent.putExtra("tag", "camera");
                startActivity(myIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private class LogOut extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute()
        {
            dialog = ProgressDialog.show(MainActivity.this, "",
                    "Logging Out...", true);
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
                responseStr  =responseStr.replaceAll("\\s+", "");
            } catch (Exception ex) {
                ex.printStackTrace();
                responseStr="internetFailed";
            }
            return responseStr;
        }
        @Override
        protected void onPostExecute(String result) {
            if(result.equals("internetFailed")){
                Toast.makeText(MainActivity.this, "Couldn't connect. Make sure that your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();
            }
            editor.remove("Login");
            editor.commit();
            String output = result.replaceAll("\\s+", "");
            Intent intent = new Intent(MainActivity.this, SignIn.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
             finish();
            dialog.dismiss();

        }
        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}
