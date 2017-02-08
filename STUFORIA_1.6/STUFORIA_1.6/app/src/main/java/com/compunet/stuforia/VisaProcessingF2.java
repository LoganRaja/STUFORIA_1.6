package com.compunet.stuforia;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class VisaProcessingF2 extends Fragment {


    public VisaProcessingF2() {
        // Required empty public constructor
    }

    String id;
    String[] details;
    Button uploadpass,uploadi20;
    LinearLayout apply;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_visa_f2_try, container, false);

        return view;
    }
}
