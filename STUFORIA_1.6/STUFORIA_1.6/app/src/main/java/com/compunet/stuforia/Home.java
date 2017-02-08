package com.compunet.stuforia;


import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    private ViewPager viewPager;
    private int previousId=0;
    private int currentId=0;
    private boolean start=true;
    TextView text11,text12,text21,text22,text31,text32,text41,text42;
    LinearLayout layout11,layout22,layout33,layout44,mainlayout1,mainlayout2,BankLoans_Home,mainlayout4,mainlayout5,mainlayout6;
    LinearLayout university,payment,templates,checklist;
    ImageView image11,image12,image21,image22,image31,image32,image41,image42,indicator1,indicator2,indicator3;
    String id;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    //String details[];
    String totaluniverisity;


    public Home() {
        // Required empty public constructor
    }
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_home_try, container, false);

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;

        mainlayout1=(LinearLayout)v.findViewById(R.id.mainlayout1);
        mainlayout2=(LinearLayout)v.findViewById(R.id.mainlayout2);
        BankLoans_Home=(LinearLayout)v.findViewById(R.id.BankLoans_Home);
        mainlayout4=(LinearLayout)v.findViewById(R.id.mainlayout4);
        mainlayout5=(LinearLayout)v.findViewById(R.id.mainlayout5);
        mainlayout6=(LinearLayout)v.findViewById(R.id.mainlayout6);

        university=(LinearLayout)v.findViewById(R.id.university);
        templates=(LinearLayout)v.findViewById(R.id.templates);
        checklist=(LinearLayout)v.findViewById(R.id.checklist);
        payment=(LinearLayout)v.findViewById(R.id.payment);

        layout11=(LinearLayout)v.findViewById(R.id.layout11);
        image11=(ImageView)v.findViewById(R.id.image11);
        image12=(ImageView)v.findViewById(R.id.image12);
        text11=(TextView)v.findViewById(R.id.text11);
        text12=(TextView)v.findViewById(R.id.text12);

        layout22=(LinearLayout)v.findViewById(R.id.layout21);
        image21=(ImageView)v.findViewById(R.id.image21);
        image22=(ImageView)v.findViewById(R.id.image22);
        text21=(TextView)v.findViewById(R.id.text21);
        text22=(TextView)v.findViewById(R.id.text22);

        layout33=(LinearLayout)v.findViewById(R.id.layout31);
        image31=(ImageView)v.findViewById(R.id.image31);
        image32=(ImageView)v.findViewById(R.id.image32);
        text31=(TextView)v.findViewById(R.id.text31);
        text32=(TextView)v.findViewById(R.id.text32);

        layout44=(LinearLayout)v.findViewById(R.id.layout41);
        image41=(ImageView)v.findViewById(R.id.image41);
        image42=(ImageView)v.findViewById(R.id.image42);
        text41=(TextView)v.findViewById(R.id.text41);
        text42=(TextView)v.findViewById(R.id.text42);


       LinearLayout.LayoutParams paramsmain1 = (LinearLayout.LayoutParams) mainlayout1.getLayoutParams();
        paramsmain1.height =(int) (height * .2);
        LinearLayout.LayoutParams paramsmain2 = (LinearLayout.LayoutParams) mainlayout2.getLayoutParams();
        paramsmain2.height =(int) (height * .2);


        LinearLayout.LayoutParams paramstext11 = new LinearLayout.LayoutParams((int)(width*.5), (int)(paramsmain1.height*.2));
        LinearLayout.LayoutParams paramstext12 = new LinearLayout.LayoutParams((int)(width*.5),(int)(paramsmain1.height*.4));
        LinearLayout.LayoutParams paramslayout11 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int)(paramsmain1.height*.4) );
        LinearLayout.LayoutParams paramsimage11 = new LinearLayout.LayoutParams((int)(width*.4),(int)(paramsmain1.height*.4) );
        LinearLayout.LayoutParams paramsimage12 = new LinearLayout.LayoutParams((int)(width*.1),(int)(paramsmain1.height*.4) );
        image12.setLayoutParams(paramsimage12);
        image11.setLayoutParams(paramsimage11);
        layout11.setLayoutParams(paramslayout11);
        text11.setLayoutParams(paramstext11);
        text12.setLayoutParams(paramstext12);

        image22.setLayoutParams(paramsimage12);
        image21.setLayoutParams(paramsimage11);
        layout22.setLayoutParams(paramslayout11);
        text21.setLayoutParams(paramstext11);
        text22.setLayoutParams(paramstext12);

        image32.setLayoutParams(paramsimage12);
        image31.setLayoutParams(paramsimage11);
        layout33.setLayoutParams(paramslayout11);
        text31.setLayoutParams(paramstext11);
        text32.setLayoutParams(paramstext12);

        image42.setLayoutParams(paramsimage12);
        image41.setLayoutParams(paramsimage11);
        layout44.setLayoutParams(paramslayout11);
        text41.setLayoutParams(paramstext11);
        text42.setLayoutParams(paramstext12);

      LinearLayout.LayoutParams params3 = (LinearLayout.LayoutParams) BankLoans_Home.getLayoutParams();
        params3.height =(int) (height * .09);
        params3.width =width;
        LinearLayout.LayoutParams params4 = (LinearLayout.LayoutParams) mainlayout4.getLayoutParams();
        params4.height =(int) (height * .09);
        params4.width =width;
        LinearLayout.LayoutParams params5 = (LinearLayout.LayoutParams) mainlayout5.getLayoutParams();
        params5.height =(int) (height * .09);
        params5.width =width;
        LinearLayout.LayoutParams params6 = (LinearLayout.LayoutParams) mainlayout6.getLayoutParams();
        params6.height  =(int) (height * .09);
        params6.width =width;

        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = prefs.edit();
        id=prefs.getString("id",null);
        if(id!=null) {
            totaluniverisity=prefs.getString("universitycount", null);;
            text11.setText(totaluniverisity);
        }

            templates.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toTemplates = new Intent(getActivity(), Templates.class);
                    startActivity(toTemplates);
                }
            });

            university.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        Intent toUniversity = new Intent(getActivity(), University.class);
                        startActivity(toUniversity);
                }
            });


            mainlayout6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toAssured = new Intent(getActivity(), AssuredAdmits.class);
                    startActivity(toAssured);
                }
            });

            mainlayout5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toAirTicketsHome = new Intent(getActivity(), AirTickets.class);
                    startActivity(toAirTicketsHome);
                }
            });

            mainlayout4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toInsurance = new Intent(getActivity(), Insurance.class);
                    startActivity(toInsurance);
                }
            });
            payment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toPayments = new Intent(getActivity(), Payment.class);
                    startActivity(toPayments);
                }
            });

        BankLoans_Home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toBanking = new Intent(getActivity(), BankListActivity.class);
                    startActivity(toBanking);
                }
            });
            checklist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toChecklists = new Intent(getActivity(), Checklist.class);
                    startActivity(toChecklists);
                }
            });

            viewPager = (ViewPager) v.findViewById(R.id.viewPager);
            viewPager.setAdapter(new ViewPagerAdapterHome(getFragmentManager()));

        indicator1=(ImageView)v.findViewById(R.id.indicator1);
        indicator2=(ImageView)v.findViewById(R.id.indicator2);
        indicator3=(ImageView)v.findViewById(R.id.indicator3);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch(position){
                    case 0:
                        indicator1.setBackgroundResource(R.drawable.carousel);
                        indicator2.setBackgroundResource(R.drawable.carousel_deselected);
                        indicator3.setBackgroundResource(R.drawable.carousel_deselected);
                        break;
                    case 1:
                        indicator1.setBackgroundResource(R.drawable.carousel_deselected);
                        indicator2.setBackgroundResource(R.drawable.carousel);
                        indicator3.setBackgroundResource(R.drawable.carousel_deselected);
                        break;
                    case 2:
                        indicator1.setBackgroundResource(R.drawable.carousel_deselected);
                        indicator2.setBackgroundResource(R.drawable.carousel_deselected);
                        indicator3.setBackgroundResource(R.drawable.carousel);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return v;
    }



    public class ViewPagerAdapterHome extends FragmentStatePagerAdapter {

        private int WIZARD_PAGES_COUNT = 3;

        public ViewPagerAdapterHome(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment=null;

            switch(position){

                case 0:
                    fragment=new Pager1();

                    break;
                case 1:
                    fragment=new Pager2();

                    break;
                case 2:
                    fragment=new Pager3();

                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return WIZARD_PAGES_COUNT;
        }
    }
}

