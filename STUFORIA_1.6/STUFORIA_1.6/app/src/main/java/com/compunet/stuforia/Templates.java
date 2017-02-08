package com.compunet.stuforia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.compunet.stuforia.R;


public class Templates extends ActionBarActivity {// Declaring Your View and Variables
    ImageView viewImage,indicator1,indicator2,indicator3,indicator4,indicator5;
    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapterTemplates adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Financial \nstatements","Financial affidavit \nof support - Admission","Financial affidavit \nof support - VISA","Letter of \nrecommendation","Statement of \npurpose"};
    /*CharSequence Titles[]={"FINANCIAL \n STATEMENTS","FINANCIAL AFFIDAVIT \nOF SUPPORT - Admission","FINANCIAL AFFIDAVIT \nOF SUPPORT - VISA","LETTER \nOF RECOMMENDATION","STATEMENT \nOF PURPOSE"};*/
    int Numboftabs =5;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
      String id,tag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_templates);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();
        id=prefs.getString("id", null);
       // Toast.makeText(this,"hello"+id+details[0]+details[1],Toast.LENGTH_SHORT).show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d5da7")));
        // Creating The Toolbar and setting it as the Toolbar for the activity

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapterTemplates(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.templatespager);
        pager.setAdapter(adapter);

        indicator1=(ImageView)findViewById(R.id.indicator1);
        indicator2=(ImageView)findViewById(R.id.indicator2);
        indicator3=(ImageView)findViewById(R.id.indicator3);
        indicator4=(ImageView)findViewById(R.id.indicator4);
        indicator5=(ImageView)findViewById(R.id.indicator5);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        indicator1.setBackgroundResource(R.drawable.carousel);
                        indicator2.setBackgroundResource(R.drawable.carousel_deselected);
                        indicator3.setBackgroundResource(R.drawable.carousel_deselected);
                        indicator4.setBackgroundResource(R.drawable.carousel_deselected);
                        indicator5.setBackgroundResource(R.drawable.carousel_deselected);
                        break;
                    case 1:
                        indicator1.setBackgroundResource(R.drawable.carousel_deselected);
                        indicator2.setBackgroundResource(R.drawable.carousel);
                        indicator3.setBackgroundResource(R.drawable.carousel_deselected);
                        indicator4.setBackgroundResource(R.drawable.carousel_deselected);
                        indicator5.setBackgroundResource(R.drawable.carousel_deselected);
                        break;
                    case 2:
                        indicator1.setBackgroundResource(R.drawable.carousel_deselected);
                        indicator2.setBackgroundResource(R.drawable.carousel_deselected);
                        indicator3.setBackgroundResource(R.drawable.carousel);
                        indicator4.setBackgroundResource(R.drawable.carousel_deselected);
                        indicator5.setBackgroundResource(R.drawable.carousel_deselected);
                        break;
                    case 3:
                        indicator1.setBackgroundResource(R.drawable.carousel_deselected);
                        indicator2.setBackgroundResource(R.drawable.carousel_deselected);
                        indicator3.setBackgroundResource(R.drawable.carousel_deselected);
                        indicator4.setBackgroundResource(R.drawable.carousel);
                        indicator5.setBackgroundResource(R.drawable.carousel_deselected);
                        break;
                    case 4:
                        indicator1.setBackgroundResource(R.drawable.carousel_deselected);
                        indicator2.setBackgroundResource(R.drawable.carousel_deselected);
                        indicator3.setBackgroundResource(R.drawable.carousel_deselected);
                        indicator4.setBackgroundResource(R.drawable.carousel_deselected);
                        indicator5.setBackgroundResource(R.drawable.carousel);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setCustomTabView(R.layout.custom_tab,R.id.customText);
        tabs.setViewPager(pager);// To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.white);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        /*getMenuInflater().inflate(R.menu.menu_main, menu);*/
        getMenuInflater().inflate(R.menu.menu_payment, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
