package csocket.example.com.newsfeed;

/**
 * Created by girid on 10/26/2015.
 */

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder> {
    private Context mContext;
    int iCountLike=0;
    HashMap<String,String> hmPostDetail;
    ArrayList<Integer> alPostList=new ArrayList<Integer>();
     int progressStatus = 0,progressStatus1=0;
     Handler handler = new Handler();

    public RecyclerAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.test_layout, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, final int i) {

        if(alPostList.contains(i))
        {
            customViewHolder.check.setText("Nan Change Ayiden");
        }

        customViewHolder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iCountLike=i;
                customViewHolder.check.setText("Nan Change Ayiden");
                if(!alPostList.contains(iCountLike))
                    alPostList.add(iCountLike);
            }
        });
    }




    @Override
    public int getItemCount() {
        return 10;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder
        {
                protected Button check;
        public CustomViewHolder(View view) {
            super(view);
               check=(Button)view.findViewById(R.id.check);
        }
    }

}
