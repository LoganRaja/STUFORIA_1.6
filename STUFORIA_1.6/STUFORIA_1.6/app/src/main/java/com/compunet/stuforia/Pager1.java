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
public class Pager1 extends Fragment {

    public Pager1() {
        // Required empty public constructor
    }
    LinearLayout introBtn;
    LinearLayout predepartureBtn;
    LinearLayout onusarrivalBtn;
    LinearLayout visaProcessingBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_pager1_try, container, false);
        introBtn=(LinearLayout)v.findViewById(R.id.introBtn);
        introBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toIntro=new Intent(getActivity(),Introduction.class);
                getActivity().startActivity(toIntro);
            }
        });


        predepartureBtn=(LinearLayout)v.findViewById(R.id.predepartureBtn);
        predepartureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toPreDeparture=new Intent(getActivity(),PreDeparture.class);
                getActivity().startActivity(toPreDeparture);
            }
        });

        onusarrivalBtn=(LinearLayout)v.findViewById(R.id.onusarrivalBtn);
        onusarrivalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toOnUSarrival=new Intent(getActivity(),OnUS.class);
                getActivity().startActivity(toOnUSarrival);
            }
        });

        visaProcessingBtn=(LinearLayout)v.findViewById(R.id.visaProcessingBtn);
        visaProcessingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toVisaProcessing=new Intent(getActivity(),VisaProcessing.class);
                getActivity().startActivity(toVisaProcessing);
            }
        });
        return v;
    }

}
