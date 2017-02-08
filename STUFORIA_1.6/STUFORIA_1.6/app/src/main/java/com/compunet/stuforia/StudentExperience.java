package com.compunet.stuforia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class StudentExperience extends ActionBarActivity {

    ProgressDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_experience);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d5da7")));

        GridView gridView = (GridView)findViewById(R.id.gridview);
        gridView.setAdapter(new GridViewStudentExperience(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id)
         {
             Toast.makeText(getApplicationContext(), "Please wait...", Toast.LENGTH_LONG).show();
             Intent in = new Intent(getApplicationContext(), StudentExperienceIndividual.class);
             in.putExtra("option", Integer.toString(position));
             startActivity(in);
         }
         }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_payment, menu);
        return true;
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
