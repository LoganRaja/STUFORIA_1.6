package com.compunet.stuforia;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.widget.LinearLayout;

public class Privacy extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_try);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        LinearLayout layout_privacy=(LinearLayout) findViewById(R.id.layout_privacy);
        LinearLayout.LayoutParams layout_privacyparams = (LinearLayout.LayoutParams) layout_privacy.getLayoutParams();
        layout_privacyparams.height =(int)(height*(0.2));
    }

}
