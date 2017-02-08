package com.compunet.stuforia;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.compunet.stuforia.R;

/**
 * Created by Edwin on 15/02/2015.
 */
public class Tab1 extends Fragment {
    LinearLayout fouraBtn;
    LinearLayout admitsBtn;
    LinearLayout visaBtn;
    LinearLayout bankingBtn;
    LinearLayout airticketsBtn;
    LinearLayout statusBtn;
    String id;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_1_try,container,false);
        MainActivity activity = (MainActivity) getActivity();
        //id = activity.getMyData();
        fouraBtn=(LinearLayout)v.findViewById(R.id.fouraBtn);
        admitsBtn=(LinearLayout)v.findViewById(R.id.admitsBtn);
        visaBtn=(LinearLayout)v.findViewById(R.id.visaBtn);
        bankingBtn=(LinearLayout)v.findViewById(R.id.bankingBtn);
        airticketsBtn=(LinearLayout)v.findViewById(R.id.airticketsBtn);
        statusBtn=(LinearLayout)v.findViewById(R.id.statusBtn);
        fouraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toFourA=new Intent(getActivity(),FourA.class);
                toFourA.putExtra("id",id);
                getActivity().startActivity(toFourA);
            }
        });
        admitsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toAdmits=new Intent(getActivity(),Admits.class);
                getActivity().startActivity(toAdmits);
            }
        });

        visaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toVisa=new Intent(getActivity(),Visa.class);
                getActivity().startActivity(toVisa);
            }
        });

        bankingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toBanking=new Intent(getActivity(),Banking.class);
                getActivity().startActivity(toBanking);
            }
        });

        airticketsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toAirTickets=new Intent(getActivity(),AirTickets.class);
                getActivity().startActivity(toAirTickets);
            }
        });
        statusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toStatus=new Intent(getActivity(),Status.class);
                getActivity().startActivity(toStatus);
            }
        });
        return v;
    }
}
