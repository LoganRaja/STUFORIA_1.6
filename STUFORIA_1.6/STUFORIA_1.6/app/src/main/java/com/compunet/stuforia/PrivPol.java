package com.compunet.stuforia;


import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.compunet.stuforia.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class PrivPol extends Fragment {


    public PrivPol() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_priv_pol_try, container, false);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        LinearLayout layout_privacy=(LinearLayout) view.findViewById(R.id.layout_privacy);
        LinearLayout.LayoutParams layout_privacyparams = (LinearLayout.LayoutParams) layout_privacy.getLayoutParams();
        layout_privacyparams.height =(int)(height*(0.2));
        return view;
    }


}
