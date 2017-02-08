package com.compunet.stuforia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.compunet.stuforia.R;

/**
 * Created by connectors on 9/24/2015.
 */

public class CourseDetail extends Activity {
    String[] details;
    TextView GMAT_heading,GRE_textview1,GRE_textview2,TOEFL_textview1,TOEFL_textview2;
    LinearLayout GMAT_layout,IELTS_layout,PTE_layout,whiteline1;
    String mbams;
    TextView title,fees,deadline,internet,gmat,ielts,pte,gre_range;
    Button close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_details_try);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .8));
        Intent intent=getIntent();
        details= intent.getStringArrayExtra("details");
        mbams= intent.getStringExtra("mba/ms");
       // Toast.makeText(getApplicationContext(),""+details.length,Toast.LENGTH_LONG).show();
        getIntent().removeExtra("details");
        title=(TextView)findViewById(R.id.course_name);
        fees=(TextView)findViewById(R.id.fees);
        deadline=(TextView)findViewById(R.id.deadline);
        gre_range=(TextView)findViewById(R.id.gre_range);
        internet=(TextView)findViewById(R.id.toefl_ib);
        gmat=(TextView)findViewById(R.id.gmat);
        ielts=(TextView)findViewById(R.id.ielts);
        pte=(TextView)findViewById(R.id.pte);

        GMAT_layout=(LinearLayout) findViewById(R.id.GMAT_layout);

        IELTS_layout=(LinearLayout) findViewById(R.id.IELTS_layout);
        whiteline1=(LinearLayout) findViewById(R.id.whiteline1);




        PTE_layout=(LinearLayout) findViewById(R.id.PTE_layout);

        GRE_textview1=(TextView) findViewById(R.id.GRE_textview1);
        GRE_textview2=(TextView) findViewById(R.id.GRE_textview2);
        GMAT_heading=(TextView) findViewById(R.id.GMAT_heading);

        close=(Button) findViewById(R.id.ok);

        int key = 0;
        for (String temp : details){
            if(temp!=null){
                if(temp.equals("0")){
                    details[key]="N/A";
                }
                key++;
            }else{
                break;
            }
        }
        title.setText(details[0]);
        fees.setText(details[10]);
        deadline.setText(details[1]);
        internet.setText(details[6]);
        gmat.setText(details[7]);
        ielts.setText(details[8]);
        pte.setText(details[9]);
        gre_range.setText(details[11]);

        if(mbams.equals("MBA")){

            GRE_textview1.setVisibility(View.GONE);
            GRE_textview2.setVisibility(View.GONE);
            gre_range.setVisibility(View.GONE);
            IELTS_layout.setVisibility(View.GONE);
            whiteline1.setVisibility(View.GONE);
            PTE_layout.setVisibility(View.GONE);
        }

        if(mbams.equals("MS")){

            GMAT_layout.setVisibility(View.GONE);
            PTE_layout.setVisibility(View.GONE);

        }

       close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });
    }
}
