package com.compunet.stuforia;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by girid on 9/7/2015.
 */
class NewsFeedAdapter extends BaseAdapter {
    String[][] newsFeed;
    private Context context;


    public NewsFeedAdapter(Context context,String news[][]){
        this.context=context;
        this.newsFeed=news;
    }

    @Override
    public int getCount() {
        return newsFeed.length;
    }

    @Override
    public Object getItem(int position) {
        return newsFeed[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=null;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    row = mInflater.inflate(R.layout.listviewneesfeed,parent,false);
        }
        else {
            row=convertView;
        }
        TextView heading = (TextView) row.findViewById(R.id.heading);
        TextView content = (TextView) row.findViewById(R.id.content);
        heading.setText(newsFeed[position][1]);
        content.setText(newsFeed[position][2]);
        return row;
    }

}