package com.compunet.stuforia;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.compunet.stuforia.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class VisaProcessingM1 extends Fragment {


    public VisaProcessingM1() {
        // Required empty public constructor
    }


    Button uploadpass,uploadi20;

    String id;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_visa_m1_try, container, false);
        final VisaProcessing activity = (VisaProcessing) getActivity();
        uploadpass=(Button)view.findViewById(R.id.uploadpass);
        uploadi20=(Button)view.findViewById(R.id.uploadi20);

        prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        editor = prefs.edit();

        id=prefs.getString("id", null);


        uploadi20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id.equals("skip_for_now"))
                {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("UNREGISTERED USER")
                            .setMessage("Please sign up to use this feature.")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setNegativeButton(android.R.string.ok, null).show();
                }
                else {
                    Intent intent = new Intent(getActivity(), EditProfile.class);
                    intent.putExtra("not_myprofile", true);
                    startActivity(intent);
                }
            }
        });
        uploadpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id.equals("skip_for_now"))
                {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("UNREGISTERED USER")
                            .setMessage("Please sign up to use this feature.")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setNegativeButton(android.R.string.ok, null).show();
                }
                else {
                    Intent intent = new Intent(getActivity(), EditProfile.class);
                    intent.putExtra("not_myprofile", true);
                    startActivity(intent);
                }
            }
        });

        return view;
    }
}
