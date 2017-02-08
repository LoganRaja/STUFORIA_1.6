package com.compunet.stuforia;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.compunet.stuforia.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Pager3 extends Fragment {

    public Pager3() {
        // Required empty public constructor
    }
    String id;
    String details[];
    ImageView advertisement;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_pager3_try, container, false);
        advertisement=(ImageView)v.findViewById(R.id.advertisement);
        advertisement.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getActivity(),TemplateWebView.class);
        intent.putExtra("advertisement","advertisement");
        startActivity(intent);
    }
    });
        // Inflate the layout for this fragment
        return v;
    }

}
