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
public class IntroTab1 extends Fragment {


    public IntroTab1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for t his fragment
        return inflater.inflate(R.layout.fragment_intro_tab1_try, container, false);
    }


}
