package com.compunet.stuforia;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.compunet.stuforia.R;

/**
 * Created by girid on 9/7/2015.
 */
class NavDrawerListAdapter1 extends BaseAdapter {
    String[] name2;
    private Context context;


    public NavDrawerListAdapter1(Context context){
        this.context=context;
        name2=context.getResources().getStringArray(R.array.values1);
    }

    @Override
    public int getCount() {
        return name2.length;
    }

    @Override
    public Object getItem(int position) {
        return name2[position];
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
            row = mInflater.inflate(R.layout.list,parent,false);
        }
        else {
            row=convertView;
        }

        TextView txtTitle = (TextView) row.findViewById(R.id.textView1);


        txtTitle.setText(name2[position]);

        return row;
    }


}