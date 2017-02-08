package com.compunet.stuforia;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.compunet.stuforia.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class InsuranceTab1 extends Fragment {

   TextView textView;

    public InsuranceTab1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_insurance_tab1, container, false);
        TextView textView =(TextView)v.findViewById(R.id.textView);



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_insurance_tab1, container, false);
    }

}
