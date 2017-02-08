package csocket.example.com.newsfeed;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

public class NewsFeedMainActivity extends Activity {



    private RecyclerView mRecyclerView;
    private RecyclerAdapter1 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_feed_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter1(this);
        mRecyclerView.setAdapter(adapter);
        Typeface iconFont = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");
        TextView img_font=(TextView)findViewById(R.id.img_menu);
        TextView img_search=(TextView)findViewById(R.id.img_search);
        img_font.setTypeface(iconFont);
        img_search.setTypeface(iconFont);
    }

}

