package com.compunet.stuforia;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.compunet.stuforia.R;

/**
 * Created by Edwin on 15/02/2015.
 */
public class Tab2 extends Fragment {

    LinearLayout housingBtn;
    LinearLayout shoppingBtn;
    LinearLayout insuranceBtn;
    LinearLayout thingstodoBtn;
    LinearLayout travellingtipsBtn;

        @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_2_try,container,false);
        housingBtn=(LinearLayout)v.findViewById(R.id.housing_btn);
            shoppingBtn=(LinearLayout)v.findViewById(R.id.shoppingBtn);
            insuranceBtn=(LinearLayout)v.findViewById(R.id.insuranceBtn);
            thingstodoBtn=(LinearLayout)v.findViewById(R.id.thingstodoBtn);
            travellingtipsBtn=(LinearLayout)v.findViewById(R.id.travellingtipsBtn);

            housingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Intent toHousing=new Intent(getActivity(),Housing.class);
              //  getActivity().startActivity(toHousing);
                new AlertDialog.Builder(getActivity())
                        .setTitle("ON PROGRESS")
                        .setMessage("This feature will be available soon.. \nKindly contact enquiries@stuforia.com")
                        .setIcon(R.drawable.exclamatory_ic)
                        .setNegativeButton(android.R.string.ok, null).show();
            }
        });

            shoppingBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toShopping=new Intent(getActivity(),Shopping.class);
                    getActivity().startActivity(toShopping);
                }
            });

            insuranceBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toInsurance=new Intent(getActivity(),Insurance.class);
                    getActivity().startActivity(toInsurance);
                }
            });
            thingstodoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toThingsToDo=new Intent(getActivity(),ThingsToDo.class);
                    getActivity().startActivity(toThingsToDo);
                }
            });
            travellingtipsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toTravellingBtn=new Intent(getActivity(),TravellingTips.class);
                    getActivity().startActivity(toTravellingBtn);
                }
            });

            return v;
    }
}