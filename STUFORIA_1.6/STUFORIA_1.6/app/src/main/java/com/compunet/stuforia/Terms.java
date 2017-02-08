package com.compunet.stuforia;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Terms extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_try);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        LinearLayout layout_terms=(LinearLayout) findViewById(R.id.layout_terms);
        LinearLayout.LayoutParams layout_termsparams = (LinearLayout.LayoutParams) layout_terms.getLayoutParams();
        layout_termsparams.height =(int)(height*.2);
    }

}
