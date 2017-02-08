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

public class BankRecyclerview extends RecyclerView.Adapter<BankRecyclerview.CustomViewHolder> {
    private Context mContext;
    String[][] bankdetails;
    public BankRecyclerview(Context context, String bankdetails[][]) {
        this.mContext = context;
        this.bankdetails=bankdetails;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bank_customlist_try, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {

             customViewHolder.bank_name.setText((bankdetails[i][1]));

            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CustomViewHolder holder = (CustomViewHolder) view.getTag();
                    int position = holder.getPosition();
                    Intent intent = new Intent(mContext, SingleBankDetailActivity.class);
                    intent.putExtra("bank_details", bankdetails[position]);
                    mContext.startActivity(intent);
                }
            };
        customViewHolder.bank_name.setOnClickListener(clickListener);
        customViewHolder.bank_name.setTag(customViewHolder);
    }

    @Override
    public int getItemCount() {
        return bankdetails.length;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView bank_name;

        public CustomViewHolder(View view) {
            super(view);
            this.bank_name = (TextView) view.findViewById(R.id.bank_name);
        }
    }

}
