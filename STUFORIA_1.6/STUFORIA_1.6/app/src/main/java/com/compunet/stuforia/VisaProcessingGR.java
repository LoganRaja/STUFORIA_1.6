package com.compunet.stuforia;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * A simple {@link Fragment} subclass.
 */
public class VisaProcessingGR extends Fragment {


    public VisaProcessingGR() {
        // Required empty public constructor
    }

    String id;
    String[] details;
    Button gr_from_india,gr_from_usa;
    LinearLayout apply;
    ScrollView scrollView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_visa_gr_try, container, false);
        gr_from_india=(Button)view.findViewById(R.id.gr_from_india);
        gr_from_usa=(Button)view.findViewById(R.id.gr_from_usa);
     scrollView=(ScrollView)view.findViewById(R.id.scrollmain);

        gr_from_india.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GraduationVisaChecklist.class);
                intent.putExtra("tag", "india");
                startActivity(intent);
            }
        });

        gr_from_usa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GraduationVisaChecklist.class);
                intent.putExtra("tag", "usa");
                startActivity(intent);
            }
        });
        return view;
    }

    /*@Override
    public void onPause() {
        super.onPause();
        scrollView.setAlpha((float) 0.1);

    }*/
    /*@Override
    public void onResume() {
        super.onResume();
       scrollView.setAlpha(1);

    }*/
}
