package com.compunet.stuforia;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.compunet.stuforia.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Pager2 extends Fragment {


    public Pager2() {
        // Required empty public constructor
    }
    LinearLayout universityBtn;
    LinearLayout studentexperienceBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_pager2_try, container, false);
        universityBtn=(LinearLayout)v.findViewById(R.id.universityBtn);
        studentexperienceBtn=(LinearLayout)v.findViewById(R.id.studentexperienceBtn);


        universityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toUniversity=new Intent(getActivity(),University.class);
                getActivity().startActivity(toUniversity);
            }
        });

        studentexperienceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toStudentExperience=new Intent(getActivity(),StudentExperience.class);
                getActivity().startActivity(toStudentExperience);
            }
        });

        return v;
    }


}
