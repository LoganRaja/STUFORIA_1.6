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
public class TermCond extends Fragment {


    public TermCond() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_term_cond, container, false);
        Display display =getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        LinearLayout layout_terms=(LinearLayout) view.findViewById(R.id.layout_terms);
        LinearLayout.LayoutParams layout_termsparams = (LinearLayout.LayoutParams) layout_terms.getLayoutParams();
        layout_termsparams.height =(int)(height*.2);
        return view;
    }


}
