package com.compunet.stuforia;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.compunet.stuforia.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class BankingTab1 extends Fragment {


    public BankingTab1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_banking_tab1, container, false);
    }


}
