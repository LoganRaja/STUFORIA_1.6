package com.compunet.stuforia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.compunet.stuforia.R;


public class Payment extends ActionBarActivity {
    Button sevis,bpm,compatitive,visa_processing,foreign_exchange;
    String id;
    String details[];

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_try);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d5da7")));
        sevis=(Button)findViewById(R.id.sevis);
        Intent intent=getIntent();

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();
        id=prefs.getString("id", null);


        compatitive=(Button)findViewById(R.id.competitive_exams);
        visa_processing=(Button)findViewById(R.id.foreign_exchange);
        foreign_exchange=(Button)findViewById(R.id.visa_processing);


        sevis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSevis = new Intent(Payment.this, Sevis.class);
                toSevis.putExtra("id", id);
                toSevis.putExtra("details",details);
                startActivity(toSevis);
            }
        });

        compatitive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id.equals("skip_for_now"))
                {
                    new AlertDialog.Builder(Payment.this)
                            .setTitle("UNREGISTERED USER")
                            .setMessage("Please sign up to use this feature.")
                            .setIcon(R.drawable.exclamatory_ic)
                            .setNegativeButton(android.R.string.ok, null).show();
                }
                else {
                    new AlertDialog.Builder(Payment.this)
                            .setTitle("ON PROGRESS")
                            .setMessage("This feature will be available soon. \nKindly contact enquiries@stuforia.com")
                            .setIcon(R.drawable.exclamatory_ic)
                            .setNegativeButton(android.R.string.ok, null).show();
                }
            }
        });

        foreign_exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id.equals("skip_for_now"))
                {
                    new AlertDialog.Builder(Payment.this)
                            .setTitle("UNREGISTERED USER")
                            .setMessage("Please sign up to use this feature.")
                            .setNegativeButton(android.R.string.ok, null).show();
                }
                else {
                    new AlertDialog.Builder(Payment.this)
                            .setTitle("ON PROGRESS")
                            .setMessage("This feature will be available soon.\nKindly contact enquiries@stuforia.com")
                            .setIcon(R.drawable.exclamatory_ic)
                            .setNegativeButton(android.R.string.ok, null).show();
                }
            }
        });

        visa_processing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id.equals("skip_for_now"))
                {
                    new AlertDialog.Builder(Payment.this)
                            .setTitle("UNREGISTERED USER")
                            .setMessage("Please sign up to use this feature.")
                            .setIcon(R.drawable.exclamatory_ic)
                            .setNegativeButton(android.R.string.ok, null).show();
                }
                else {
                    new AlertDialog.Builder(Payment.this)
                            .setTitle("ON PROGRESS")
                            .setMessage("This feature will be available soon.\nKindly contact enquiries@stuforia.com")
                            .setMessage("This feature will be available soon.\nKindly contact enquiries@stuforia.com")
                            .setIcon(R.drawable.exclamatory_ic)
                            .setNegativeButton(android.R.string.ok, null).show();
                }
            }
        });
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
