package com.compunet.stuforia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Connector-4 on 4/28/2015.
 */
public class SingleBankDetailActivity extends ActionBarActivity
{

    // JSON node keys


String single_bank_details[];
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_detail);


        TextView title_checklist = (TextView) findViewById(R.id.title_checklist);
        TextView title_rateofinterest = (TextView) findViewById(R.id.title_rateofinterest);
        TextView title_studyabroad = (TextView) findViewById(R.id.title_studyabroad);
        TextView title_loanforgirls = (TextView) findViewById(R.id.title_loanforgirls);
        TextView title_collateralsecurity = (TextView) findViewById(R.id.title_collateralsecurity);
        TextView title_coapplicantselfemployed = (TextView) findViewById(R.id.title_coapplicantselfemployed);
        TextView title_coapplicantsalaried = (TextView) findViewById(R.id.title_coapplicantsalaried);

        // getting intent data
        Intent in = getIntent();
      single_bank_details=in.getStringArrayExtra("bank_details");
        getIntent().removeExtra("bank_details");
        // Get JSON values from previous intent

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Displaying all values on the screen
        TextView lblbankname = (TextView) findViewById(R.id.bankname_label);
        TextView lblchecklist = (TextView) findViewById(R.id.checklist_label);
        TextView lblrateofinterest = (TextView) findViewById(R.id.rateofinterest_label);
        TextView lblstudyabroad = (TextView) findViewById(R.id.studyabroad_label);
        TextView lblloanforgirls = (TextView) findViewById(R.id.loanforgirls_label);
        TextView lblcollateralsecurity = (TextView) findViewById(R.id.collateralsecurity_label);
        TextView lblcoapplicantselfemployed = (TextView) findViewById(R.id.coapplicantselfemployed_label);
        TextView lblcoapplicantsalaried = (TextView) findViewById(R.id.coapplicantsalaried_label);

        if(!(single_bank_details[2].isEmpty())){

            title_checklist.setVisibility(View.VISIBLE);
            lblchecklist.setVisibility(View.VISIBLE);
        }


        if(!(single_bank_details[3].isEmpty())){

            title_rateofinterest.setVisibility(View.VISIBLE);
            lblrateofinterest.setVisibility(View.VISIBLE);
        }

        if(!(single_bank_details[4].isEmpty())){

            title_studyabroad.setVisibility(View.VISIBLE);
            lblstudyabroad.setVisibility(View.VISIBLE);
        }

        if(!(single_bank_details[5].isEmpty())){

            title_loanforgirls.setVisibility(View.VISIBLE);
            lblloanforgirls.setVisibility(View.VISIBLE);
        }

        if(!(single_bank_details[6].isEmpty())){

            title_collateralsecurity.setVisibility(View.VISIBLE);
            lblcollateralsecurity.setVisibility(View.VISIBLE);
        }

        if(!(single_bank_details[7].isEmpty())){

            title_coapplicantselfemployed.setVisibility(View.VISIBLE);
            lblcoapplicantselfemployed.setVisibility(View.VISIBLE);
        }

        if(!(single_bank_details[8].isEmpty())){

            title_coapplicantsalaried.setVisibility(View.VISIBLE);
            lblcoapplicantsalaried.setVisibility(View.VISIBLE);
        }

        lblbankname.setText(single_bank_details[1]);
        lblchecklist.setText(single_bank_details[2]);
        lblrateofinterest.setText(single_bank_details[3]);
        lblstudyabroad.setText(single_bank_details[4]);
        lblloanforgirls.setText(single_bank_details[5]);
        lblcollateralsecurity.setText(single_bank_details[6]);
        lblcoapplicantselfemployed.setText(single_bank_details[7]);
        lblcoapplicantsalaried.setText(single_bank_details[8]);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
