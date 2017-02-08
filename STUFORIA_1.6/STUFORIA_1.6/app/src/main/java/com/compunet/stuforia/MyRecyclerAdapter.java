package com.compunet.stuforia;

/**
 * Created by girid on 10/26/2015.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.CustomViewHolder> {
    private Context mContext;
    String[][] id;
    String output,pagination;
     int position;
    int c=1;
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    public MyRecyclerAdapter(Context context,String fir1[][],String pagination) {
        this.mContext = context;
        this.id=fir1;
        this.pagination=pagination;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.university_customlist_try, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        if(reachedEndOfList(i)) {
            if(pagination.equals("university")) {
                ((University) mContext).refersh(1);
            }

        }
        //Download image using picasso library
        Picasso.with(mContext).load(id[i][5])
                .error(R.drawable.ic_universities)
                .placeholder(R.drawable.ic_universities).into(customViewHolder.imageView);
        //Setting text view title
        customViewHolder.textView.setText((id[i][1]));
        customViewHolder.textView1.setText((id[i][2]));
        if(id[i][6].equals("remove")) {
            customViewHolder.textView.setTextColor(Color.WHITE);
            customViewHolder.textView1.setTextColor(Color.WHITE);
            /*customViewHolder.textcourse.setTextColor(Color.WHITE);
            customViewHolder.textdeadline.setTextColor(Color.WHITE);*/
            customViewHolder.distance.setTextColor(Color.WHITE);
            customViewHolder.item.setBackgroundColor(Color.parseColor("#2d5da7"));
        }
        else {
            customViewHolder.textView.setTextColor(Color.BLACK);
            customViewHolder.textView1.setTextColor(Color.GRAY);
            /*customViewHolder.textcourse.setTextColor(Color.BLACK);
            customViewHolder.textdeadline.setTextColor(Color.BLACK);*/
            customViewHolder.item.setBackgroundColor(Color.WHITE);
            customViewHolder.distance.setTextColor(Color.BLACK);
        }
        if(id[position][7]!=null)
        {
            customViewHolder.distance.setVisibility(View.VISIBLE);
            customViewHolder.distance.setText((id[position][7] +" Miles"));
        }
        View.OnLongClickListener longClickListener=new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
                editor = prefs.edit();
                String userid = prefs.getString("id", null);
                CustomViewHolder holder = (CustomViewHolder) v.getTag();
                position = holder.getPosition();
                nameValuePairs.add(new BasicNameValuePair("id",userid));
                nameValuePairs.add(new BasicNameValuePair("univ", id[position][0]));
                nameValuePairs.add(new BasicNameValuePair("mode", id[position][6]));
                StrictMode.setThreadPolicy(policy);
                if(!userid.equals("skip_for_now"))
                new ShortListed().execute("");
                return true;
            }
        };
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               CustomViewHolder holder = (CustomViewHolder) view.getTag();
                int position1 = holder.getPosition();


                if(pagination.equals("university"))
                ((University) mContext).resumepause(position1);
                if(pagination.equals("short listed university"))
                ((ShortListedUniversity) mContext).resumepause(position1);
                if(pagination.equals("filterresult"))
               ((FilterResult) mContext).resumepause(position1);
                Intent intent = new Intent(mContext, MapActivity.class);
                intent.putExtra("string", id[position1]);
                mContext.startActivity(intent);
            }
        };

        customViewHolder.textView.setOnClickListener(clickListener);
        customViewHolder.imageView.setOnClickListener(clickListener);
        customViewHolder.textView1.setOnClickListener(clickListener);
        customViewHolder.textView1.setOnLongClickListener(longClickListener);
        customViewHolder.item.setOnClickListener(clickListener);
        customViewHolder.item.setOnLongClickListener(longClickListener);
        customViewHolder.distance.setOnClickListener(clickListener);
        customViewHolder.distance.setOnLongClickListener(longClickListener);
        customViewHolder.textView.setOnLongClickListener(longClickListener);
        customViewHolder.imageView.setOnLongClickListener(longClickListener);
        customViewHolder.textView.setTag(customViewHolder);
        customViewHolder.imageView.setTag(customViewHolder);
        customViewHolder.textView1.setTag(customViewHolder);
        customViewHolder.item.setTag(customViewHolder);
        /*customViewHolder.textcourse.setTag(customViewHolder);
        customViewHolder.textdeadline.setTag(customViewHolder);*/
    }



    private boolean reachedEndOfList(int position) {
        // can check if close or exactly at the end
        return position == getItemCount() - 1;
    }
    @Override
    public int getItemCount() {
        return id.length;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textView;
        protected TextView textdeadline;
        protected TextView textcourse;
        protected TextView textView1;
        protected LinearLayout item;
        protected TextView distance;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.university_logo);
            this.textView = (TextView) view.findViewById(R.id.textView);
            this.textView1 = (TextView) view.findViewById(R.id.textView1);
            this.item = (LinearLayout) view.findViewById(R.id.item);
            this.distance = (TextView) view.findViewById(R.id.distance);
            /*this.textcourse = (TextView) view.findViewById(R.id.textView21);
            this.textdeadline = (TextView) view.findViewById(R.id.textView24);*/
        }
    }
    private class ShortListed extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute()
        {

        }
        @Override
        protected String doInBackground(String... params) {
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://www.venbaventures.com/stuforia/shortlisteduniversity_new.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                String responseStr = EntityUtils.toString(response.getEntity());
                output = responseStr.replaceAll("\\s+","");
                return output;
                // Toast.makeText(getActivity(), "output" + output, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                output="internetFailed";
                //Toast.makeText(ChangePassword.this, "Error:" + e, Toast.LENGTH_SHORT).show();
            }
            return output;
        }
        @Override
        protected void onPostExecute(String result) {

            if (result.equals("Record_updated_successfully")) {
                if (id[position][6].equals("remove")) {
                    editor.putString("shortlisted_univ", prefs.getString("shortlisted_univ", null).replace(id[position][0],"-1"));
                    editor.commit();
                    id[position][6] = "add";
                    notifyDataSetChanged();
                    Log.e("check1", prefs.getString("shortlisted_univ", null));
                } else {
                    if(prefs.getString("shortlisted_univ", null).contains("-1")) {
                        editor.putString("shortlisted_univ", prefs.getString("shortlisted_univ", null).replaceFirst("-1",id[position][0]));
                        editor.commit();
                        Log.e("check2", prefs.getString("shortlisted_univ", null));
                    }
                    else{
                        editor.putString("shortlisted_univ", prefs.getString("shortlisted_univ", null) + "," + id[position][0]);
                        editor.commit();
                        Log.e("check3", prefs.getString("shortlisted_univ", null));
                    }
                    id[position][6] = "remove";
                    notifyDataSetChanged();
                }
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}
