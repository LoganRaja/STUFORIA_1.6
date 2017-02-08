package com.compunet.stuforia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Compunet on 1/5/2016.
 */
public class StudentExperienceIndividual extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_experience_individual);

        JustifiedTextView article_text = (JustifiedTextView) findViewById(R.id.article_text);
        TextView article_heading = (TextView) findViewById(R.id.article_heading);
        LinearLayout student_experience_mail = (LinearLayout)findViewById(R.id.student_experience_mail);


        student_experience_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Queries");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        intent.setData(Uri.parse("mailto:info@stuforia.com")); // or just "mailto:" for blank
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
        startActivity(intent);
            }
        });



        Intent in = getIntent();
        String option = in.getStringExtra("option");

        if(option.equals("0")){

            article_heading.setText("About landing in USA");

            article_text.setText("Why ranking of colleges is not important\n" +
                    "\n" +
                    "Firstly, no ranking of the Universities in US are official. Secondly, the ranking of a College depends on all the courses put together. Every University specializes in certain areas but their ranking maybe low due to certain other departments. Always feel free to apply to the best departments of each College that are not highly ranked because they are going to give you the same high level of quality education you would expect from a top tier College. Do not fall prey to the lot many biased sites and consultancies who list out Colleges. Make sure that the decision to apply to a certain University is your own0.\n");
        }

        if(option.equals("1")){

            article_heading.setText("Campus Experience");

            article_text.setText("Why ranking of colleges is not important\n" +
                    "\n" +
                    "Firstly, no ranking of the Universities in US are official. Secondly, the ranking of a College depends on all the courses put together. Every University specializes in certain areas but their ranking maybe low due to certain other departments. Always feel free to apply to the best departments of each College that are not highly ranked because they are going to give you the same high level of quality education you would expect from a top tier College. Do not fall prey to the lot many biased sites and consultancies who list out Colleges. Make sure that the decision to apply to a certain University is your own1.\n");
        }

        if(option.equals("2")){

            article_heading.setText("Colleges ranking is not important");

            article_text.setText("Why ranking of colleges is not important\n" +
                    "\n" +
                    "Firstly, no ranking of the Universities in US are official. Secondly, the ranking of a College depends on all the courses put together. Every University specializes in certain areas but their ranking maybe low due to certain other departments. Always feel free to apply to the best departments of each College that are not highly ranked because they are going to give you the same high level of quality education you would expect from a top tier College. Do not fall prey to the lot many biased sites and consultancies who list out Colleges. Make sure that the decision to apply to a certain University is your own2.\n");
        }

        if(option.equals("3")){

            article_heading.setText("How to apply for state driving license");

            article_text.setText("Why ranking of colleges is not important\n" +
                    "\n" +
                    "Firstly, no ranking of the Universities in US are official. Secondly, the ranking of a College depends on all the courses put together. Every University specializes in certain areas but their ranking maybe low due to certain other departments. Always feel free to apply to the best departments of each College that are not highly ranked because they are going to give you the same high level of quality education you would expect from a top tier College. Do not fall prey to the lot many biased sites and consultancies who list out Colleges. Make sure that the decision to apply to a certain University is your own3.\n");
        }

        if(option.equals("4")){

            article_heading.setText("Once you reach the US airport");

            article_text.setText("Why ranking of colleges is not important\n" +
                    "\n" +
                    "Firstly, no ranking of the Universities in US are official. Secondly, the ranking of a College depends on all the courses put together. Every University specializes in certain areas but their ranking maybe low due to certain other departments. Always feel free to apply to the best departments of each College that are not highly ranked because they are going to give you the same high level of quality education you would expect from a top tier College. Do not fall prey to the lot many biased sites and consultancies who list out Colleges. Make sure that the decision to apply to a certain University is your own4.\n");
        }



    }
}
