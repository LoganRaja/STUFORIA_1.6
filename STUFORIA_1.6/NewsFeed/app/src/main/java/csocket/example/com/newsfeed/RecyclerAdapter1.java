package csocket.example.com.newsfeed;

/**
 * Created by girid on 10/26/2015.
 */

import android.animation.ObjectAnimator;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;


public class RecyclerAdapter1 extends RecyclerView.Adapter<RecyclerAdapter1.CustomViewHolder> {
    private Context mContext;
    int iCountLike=11;
    HashMap<String,String> hmPostDetail;
    ArrayList<HashMap<String, String>> alPostList=new ArrayList<HashMap<String,String>>();
     int progressStatus = 0,progressStatus1=0;
     Handler handler = new Handler();
    private FeatureCoverFlow mCoverFlow;
    private CoverFlowAdapter mAdapter;
    private ArrayList<GameEntity> mData = new ArrayList<>(0);
    private TextSwitcher mTitle;
    public RecyclerAdapter1(Context context) {
        this.mContext = context;
         hmPostDetail=new HashMap<String,String>();
        hmPostDetail.put("profile_pic","profile_pic2");
        hmPostDetail.put("profile_name","Christine Pizzo ");
        hmPostDetail.put("post_type","Shared an article to your proflie:");
        hmPostDetail.put("posted_time","2h");
        hmPostDetail.put("posted_description","You have to check out these amazing vacation\n" +
                "spots in this article. We should really think about visiting then soon!");
        hmPostDetail.put("post","post1");
        hmPostDetail.put("post_des_old","BY JENNA BROCKHEIMER DEC 5,2015");
        hmPostDetail.put("comment_total_count","");
        hmPostDetail.put("comment1_profile_pic","profile_pic3");
        hmPostDetail.put("comment1_profile_name","James Harrison ");
        hmPostDetail.put("comment1_type","responded:");
        hmPostDetail.put("comment1_time","1h");
        hmPostDetail.put("comment1_description","Looks awesome. Let's talk about a convenient time to take vacation!");
        hmPostDetail.put("comment2_profile_pic","");
        hmPostDetail.put("comment2_profile_name","");
        hmPostDetail.put("comment2_type","");
        hmPostDetail.put("comment2_time","");
        hmPostDetail.put("comment2_description","");
        hmPostDetail.put("like_count","8");
        hmPostDetail.put("share_count","3");
        alPostList.add(hmPostDetail);

        hmPostDetail=new HashMap<String,String>();
        hmPostDetail.put("profile_pic","profile_pic3");
        hmPostDetail.put("profile_name","Brendan Lancaster ");
        hmPostDetail.put("post_type","posted a public message:");
        hmPostDetail.put("posted_time","1h");
        hmPostDetail.put("posted_description","Hey guys!Just so you IBM Interactive is hiring UX designers and developers for the Cambridge office!#Innovation#wastson");
        hmPostDetail.put("post","");
        hmPostDetail.put("post_des_old","");
        hmPostDetail.put("comment_total_count","SHOW PREVIOUS MESSAGES (3)");
        hmPostDetail.put("comment1_profile_pic","profile_pic1");
        hmPostDetail.put("comment1_profile_name","Hannah Cochran ");
        hmPostDetail.put("comment1_type","responded:");
        hmPostDetail.put("comment1_time","2m");
        hmPostDetail.put("comment1_description","Wow,seems like a great opportunity.\n" +
                                                 "I'll take a look at the position req!");
        hmPostDetail.put("comment2_profile_pic","profile_pic2");
        hmPostDetail.put("comment2_profile_name","Seth Priebatsch ");
        hmPostDetail.put("comment2_type","responded:");
        hmPostDetail.put("comment2_time","1m");
        hmPostDetail.put("comment2_description","I'll check with some of my friends.");
        hmPostDetail.put("like_count","2");
        hmPostDetail.put("share_count","1");
        alPostList.add(hmPostDetail);
        hmPostDetail=new HashMap<String,String>();
        hmPostDetail.put("profile_pic","profile_pic1");
        hmPostDetail.put("profile_name","Oren shatken ");
        hmPostDetail.put("post_type","liked a post by Columbia Records");
        hmPostDetail.put("posted_time","6h");
        hmPostDetail.put("posted_description","");
        hmPostDetail.put("post","post2");
        hmPostDetail.put("post_des_old","SPONSORED\n" +
                "\n" +
                "Adele's 25:Why growing up isn't so bad \n" +
                "Following Adele's breakout album,25 takes\n" +
                "the talented singer to even loftier heights.");
        hmPostDetail.put("comment_total_count","");
        hmPostDetail.put("comment1_profile_pic","");
        hmPostDetail.put("comment1_profile_name","");
        hmPostDetail.put("comment1_type","");
        hmPostDetail.put("comment1_time","");
        hmPostDetail.put("comment1_description","");
        hmPostDetail.put("comment2_profile_pic","");
        hmPostDetail.put("comment2_profile_name","");
        hmPostDetail.put("comment2_type","");
        hmPostDetail.put("comment2_time","");
        hmPostDetail.put("comment2_description","");
        hmPostDetail.put("like_count","211");
        hmPostDetail.put("share_count","131");
        alPostList.add(hmPostDetail);
        hmPostDetail=new HashMap<String,String>();;
        hmPostDetail.put("post_type","people_know");
        alPostList.add(hmPostDetail);
        hmPostDetail=new HashMap<String,String>();;
        hmPostDetail.put("post_type","post_poll");
        alPostList.add(hmPostDetail);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_feed_customlist, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, int i) {
         HashMap<String,String> hmPostDetail=new HashMap<String,String>();
         hmPostDetail=alPostList.get(i);

        if(hmPostDetail.get("post_type").equals("people_know"))
        {
            customViewHolder.general_post.setVisibility(View.GONE);
            customViewHolder.check.setVisibility(View.VISIBLE);
            customViewHolder.post_poll.setVisibility(View.GONE);

            mData.add(new GameEntity(R.drawable.coverflow_image1));
            mData.add(new GameEntity(R.drawable.coverflow_image2));
            mData.add(new GameEntity(R.drawable.coverflow_image3));
            mData.add(new GameEntity(R.drawable.coverflow_image4));
            mData.add(new GameEntity(R.drawable.coverflow_image5));

            mAdapter = new CoverFlowAdapter(mContext);
            mAdapter.setData(mData);
            customViewHolder.mCoverFlow.setAdapter(mAdapter);


            CoverImageAdapter coverImageAdapter = new CoverImageAdapter(mContext);
            customViewHolder.coverFlow.setAdapter(coverImageAdapter);
        }

        else if(hmPostDetail.get("post_type").equals("post_poll"))
        {

            customViewHolder.general_post.setVisibility(View.GONE);
            customViewHolder.check.setVisibility(View.GONE);
            customViewHolder.post_poll.setVisibility(View.VISIBLE);

        }
        else
        {
            customViewHolder.general_post.setVisibility(View.VISIBLE);
            customViewHolder.check.setVisibility(View.GONE);
            customViewHolder.post_poll.setVisibility(View.GONE);
            if(!hmPostDetail.get("profile_pic").isEmpty())
            {
                customViewHolder.profile_pic.setVisibility(View.VISIBLE);
                if(hmPostDetail.get("profile_pic").equals("profile_pic1"))
                customViewHolder.profile_pic.setImageResource(R.drawable.profile_pic1);
                if(hmPostDetail.get("profile_pic").equals("profile_pic2"))
                    customViewHolder.profile_pic.setImageResource(R.drawable.profile_pic2);
                if(hmPostDetail.get("profile_pic").equals("profile_pic3"))
                    customViewHolder.profile_pic.setImageResource(R.drawable.profile_pic3);
            }
            if(!hmPostDetail.get("profile_name").isEmpty())
            {
                customViewHolder.profile_name.setVisibility(View.VISIBLE);
                SpannableString strProflieName=  new SpannableString(hmPostDetail.get("profile_name")+hmPostDetail.get("post_type"));
                strProflieName.setSpan(new RelativeSizeSpan(.8f), 0,hmPostDetail.get("profile_name").length(), 0); // set size
                strProflieName.setSpan(new ForegroundColorSpan(Color.parseColor("#00c4ff")), 0, hmPostDetail.get("profile_name").length(), 0);
                customViewHolder.profile_name.setText(strProflieName);
            }
            if(!hmPostDetail.get("posted_time").isEmpty())
            {
                customViewHolder.posted_time.setVisibility(View.VISIBLE);
                customViewHolder.posted_time.setText(hmPostDetail.get("posted_time"));
            }
            if(!hmPostDetail.get("posted_description").isEmpty())
            {
                customViewHolder.posted_description.setVisibility(View.VISIBLE);
                customViewHolder.posted_description.setText(hmPostDetail.get("posted_description"));
            }
            if(!hmPostDetail.get("post").isEmpty())
            {
                customViewHolder.post.setVisibility(View.VISIBLE);
                if(hmPostDetail.get("post").equals("post1"))
                    customViewHolder.post.setImageResource(R.drawable.post1);
                if(hmPostDetail.get("post").equals("post2"))
                    customViewHolder.post.setImageResource(R.drawable.post2);
            }
            if(!hmPostDetail.get("post_des_old").isEmpty())
            {
                customViewHolder.post_des_old.setVisibility(View.VISIBLE);
                customViewHolder.post_des_old.setText(hmPostDetail.get("post_des_old"));
            }

            if(!hmPostDetail.get("comment_total_count").isEmpty())
            {
                customViewHolder.comment_total_count.setVisibility(View.VISIBLE);
                customViewHolder.comment_total_count.setText(hmPostDetail.get("comment_total_count"));
            }

            if(!hmPostDetail.get("comment1_profile_pic").isEmpty())
            {

                customViewHolder.comment1_border.setVisibility(View.VISIBLE);
                customViewHolder.comment1_layout.setVisibility(View.VISIBLE);
                customViewHolder.comment1_profile_pic.setVisibility(View.VISIBLE);
                if(hmPostDetail.get("comment1_profile_pic").equals("profile_pic1"))
                    customViewHolder.comment1_profile_pic.setImageResource(R.drawable.profile_pic1);
                if(hmPostDetail.get("comment1_profile_pic").equals("profile_pic2"))
                    customViewHolder.comment1_profile_pic.setImageResource(R.drawable.profile_pic2);
                if(hmPostDetail.get("comment1_profile_pic").equals("profile_pic3"))
                    customViewHolder.comment1_profile_pic.setImageResource(R.drawable.profile_pic3);
            }
            if(!hmPostDetail.get("comment1_profile_name").isEmpty())
            {
                customViewHolder.comment1_profile_name.setVisibility(View.VISIBLE);
                SpannableString strProflieName=  new SpannableString(hmPostDetail.get("comment1_profile_name")+hmPostDetail.get("comment1_type"));
                strProflieName.setSpan(new RelativeSizeSpan(.8f), 0,hmPostDetail.get("comment1_profile_name").length(), 0); // set size
                strProflieName.setSpan(new ForegroundColorSpan(Color.parseColor("#00c4ff")), 0, hmPostDetail.get("comment1_profile_name").length(), 0);
                customViewHolder.comment1_profile_name.setText(strProflieName);
            }
            if(!hmPostDetail.get("comment1_time").isEmpty())
            {
                customViewHolder.comment1_time.setVisibility(View.VISIBLE);
                customViewHolder.comment1_time.setText(hmPostDetail.get("comment1_time"));
            }
            if(!hmPostDetail.get("comment1_description").isEmpty())
            {
                customViewHolder.comment1_description.setVisibility(View.VISIBLE);
                customViewHolder.comment1_description.setText(hmPostDetail.get("comment1_description"));
            }

            if(!hmPostDetail.get("comment2_profile_pic").isEmpty())
            {
                customViewHolder.comment2_border.setVisibility(View.VISIBLE);
                customViewHolder.comment2_layout.setVisibility(View.VISIBLE);
                customViewHolder.comment2_profile_pic.setVisibility(View.VISIBLE);
                if(hmPostDetail.get("comment2_profile_pic").equals("profile_pic1"))
                    customViewHolder.comment2_profile_pic.setImageResource(R.drawable.profile_pic1);
                if(hmPostDetail.get("comment2_profile_pic").equals("profile_pic2"))
                    customViewHolder.comment2_profile_pic.setImageResource(R.drawable.profile_pic2);
                if(hmPostDetail.get("comment2_profile_pic").equals("profile_pic3"))
                    customViewHolder.comment2_profile_pic.setImageResource(R.drawable.profile_pic3);
            }
            if(!hmPostDetail.get("comment2_profile_name").isEmpty())
            {
                customViewHolder.comment2_profile_name.setVisibility(View.VISIBLE);
                SpannableString strProflieName=  new SpannableString(hmPostDetail.get("comment2_profile_name")+hmPostDetail.get("comment2_type"));
                strProflieName.setSpan(new RelativeSizeSpan(1f), 0,hmPostDetail.get("comment2_profile_name").length(), 0); // set size
                strProflieName.setSpan(new ForegroundColorSpan(Color.parseColor("#00c4ff")), 0, hmPostDetail.get("comment2_profile_name").length(), 0);
                customViewHolder.comment2_profile_name.setText(strProflieName);
            }
            if(!hmPostDetail.get("comment2_time").isEmpty())
            {
                customViewHolder.comment2_time.setVisibility(View.VISIBLE);
                customViewHolder.comment2_time.setText(hmPostDetail.get("comment2_time"));
            }
            if(!hmPostDetail.get("comment2_description").isEmpty())
            {
                customViewHolder.comment2_description.setVisibility(View.VISIBLE);
                customViewHolder.comment2_description.setText(hmPostDetail.get("comment2_description"));
            }
            if(!hmPostDetail.get("like_count").isEmpty())
            {
                if(Integer.parseInt(hmPostDetail.get("like_count"))>1)
                {
                    customViewHolder.like.setText(hmPostDetail.get("like_count")+" "+"Likes");
                }
                if(Integer.parseInt(hmPostDetail.get("like_count"))==1)
                {
                    customViewHolder.like.setText(hmPostDetail.get("like_count")+" "+"Like");
                }

            }



            if(!hmPostDetail.get("share_count").isEmpty())
            {
                if(Integer.parseInt(hmPostDetail.get("share_count"))>1)
                {
                    customViewHolder.share.setText(hmPostDetail.get("share_count")+" "+"Shares");
                }
                if(Integer.parseInt(hmPostDetail.get("share_count"))==1)
                {
                    customViewHolder.share.setText(hmPostDetail.get("share_count")+" "+"Share");
                }
            }




        }



        customViewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customViewHolder.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_red,0,0,0);
            }
        });


        customViewHolder.answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customViewHolder.answer_layout.setVisibility(View.GONE);
                customViewHolder.progress_layout.setVisibility(View.VISIBLE);
                ObjectAnimator animation = ObjectAnimator.ofInt(customViewHolder.progressbar1, "progress", 0,38);
                animation.setDuration(1000); // 0.5 second
                animation.setInterpolator(new DecelerateInterpolator());
                animation.start();
                ObjectAnimator animation1 = ObjectAnimator.ofInt(customViewHolder.progressbar2, "progress", 0,62);
                animation1.setDuration(1000); // 0.5 second
                animation1.setInterpolator(new DecelerateInterpolator());
                animation1.start();
                /*new Thread(new Runnable() {
                    public void run() {

                            // Update the progress bar and display the
                            //current value in the text view
                            handler.post(new Runnable() {
                                public void run() {
                                    ObjectAnimator progressAnimator = ObjectAnimator.ofInt(customViewHolder.progressbar1, "progress", 100, 0);
                                    progressAnimator.setDuration(30000);
                                    progressAnimator.setInterpolator(new LinearInterpolator());
                                    progressAnimator.start();
                                    //customViewHolder.progressbar1.setProgress(progressStatus);
                                    //textView.setText(progressStatus+"/"+progressBar.getMax());
                                }
                            });

                    }
                }).start();
                new Thread(new Runnable() {
                    public void run() {
                        while (progressStatus1 < 62) {
                            if(progressStatus1==60)
                                progressStatus1+=2;
                            else
                                progressStatus1 += 10;
                            // Update the progress bar and display the
                            //current value in the text view
                            handler.post(new Runnable() {
                                public void run() {
                                    customViewHolder.progressbar2.setProgress(progressStatus1);
                                    //textView.setText(progressStatus+"/"+progressBar.getMax());
                                }
                            });

                        }
                    }
                }).start();*/
            }
        });


        customViewHolder.answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customViewHolder.answer_layout.setVisibility(View.GONE);
                customViewHolder.progress_layout.setVisibility(View.VISIBLE);
                ObjectAnimator animation = ObjectAnimator.ofInt(customViewHolder.progressbar1, "progress", 0,38);
                animation.setDuration(500); // 0.5 second
                animation.setInterpolator(new DecelerateInterpolator());
                animation.start();
                ObjectAnimator animation1 = ObjectAnimator.ofInt(customViewHolder.progressbar2, "progress", 0,62);
                animation1.setDuration(500); // 0.5 second
                animation1.setInterpolator(new DecelerateInterpolator());
                animation1.start();
              /*  new Thread(new Runnable() {
                    public void run() {


                            // Update the progress bar and display the
                            //current value in the text view
                            handler.post(new Runnable() {
                                public void run() {
                                    ObjectAnimator animation = ObjectAnimator.ofInt(customViewHolder.progressbar1, "progress", 100,0);
                                    animation.setDuration(1000); // 0.5 second
                                    animation.setInterpolator(new DecelerateInterpolator());
                                    animation.start();
                                    //customViewHolder.progressbar1.setProgress(progressStatus);
                                    //textView.setText(progressStatus+"/"+progressBar.getMax());
                                }
                            });


                    }
                }).start();
                new Thread(new Runnable() {
                    public void run() {
                        while (progressStatus1 < 62) {

                            // Update the progress bar and display the
                            //current value in the text view
                            handler.post(new Runnable() {
                                public void run() {
                                    ObjectAnimator animation = ObjectAnimator.ofInt(customViewHolder.progressbar2, "progress", 0,62);
                                    animation.setDuration(1000); // 0.5 second
                                    animation.setInterpolator(new DecelerateInterpolator());
                                    animation.start();
                                   // customViewHolder.progressbar2.setProgress(progressStatus1);
                                    //textView.setText(progressStatus+"/"+progressBar.getMax());
                                }
                            });

                        }
                    }
                }).start();*/
            }
        });

    }




    @Override
    public int getItemCount() {
        return alPostList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder
        {
        protected Button like,share,answer1,answer2;
        protected TextView profile_name,posted_time,
                            posted_description,post_des_old,comment_total_count,comment1_profile_name,comment1_time,comment1_description,
                            comment2_profile_name,comment2_time,comment2_description;
        protected LinearLayout general_post,check,comment1_border,comment2_border,comment1_layout,comment2_layout,post_poll,progress_layout,answer_layout;
        protected CoverFlow coverFlow;
        protected  ImageView post;
        protected ProgressBar progressbar1,progressbar2;
        protected CircleImageView profile_pic,comment1_profile_pic,comment2_profile_pic;
            FeatureCoverFlow mCoverFlow;


        public CustomViewHolder(View view) {
            super(view);
            this.profile_pic=(CircleImageView) view.findViewById(R.id.profile_pic);
            this.profile_name=(TextView)view.findViewById(R.id.profile_name);
            this.posted_time =(TextView)view.findViewById(R.id.posted_time);
            this.posted_description=(TextView)view.findViewById(R.id.posted_description);
            this.post=(ImageView)view.findViewById(R.id.post);
            this.post_des_old=(TextView)view.findViewById(R.id.post_des_old);
            this.comment_total_count=(TextView)view.findViewById(R.id.comment_total_count);
            this.comment1_profile_pic=(CircleImageView)view.findViewById(R.id.comment1_profile_pic);
            this.comment1_profile_name=(TextView)view .findViewById(R.id.comment1_profile_name);
            this.comment1_time=(TextView)view.findViewById(R.id.comment1_time);
            this.comment1_description=(TextView)view.findViewById(R.id.comment1_description);
            this.comment1_border=(LinearLayout) view.findViewById(R.id.comment1_border);
            this.comment1_layout=(LinearLayout)view.findViewById(R.id.comment1_layout);
            this.comment2_profile_pic=(CircleImageView)view.findViewById(R.id.comment2_profile_pic);
            this.comment2_profile_name=(TextView)view .findViewById(R.id.comment2_profile_name);
            this.comment2_time=(TextView)view.findViewById(R.id.comment2_time);
            this.comment2_description=(TextView)view.findViewById(R.id.comment2_description);
            this.comment2_border=(LinearLayout) view.findViewById(R.id.comment2_border);
            this.comment2_layout=(LinearLayout)view.findViewById(R.id.comment2_layout);
            this.general_post=(LinearLayout)view.findViewById(R.id.general_post);
            this.check=(LinearLayout)view.findViewById(R.id.check);
            this.coverFlow = (CoverFlow) view.findViewById(R.id.cf_coverflow);
            this.like=(Button)view.findViewById(R.id.like_button);
            this.share=(Button)view.findViewById(R.id.share_button);
            this.post_poll=(LinearLayout)view.findViewById(R.id.post_poll);
            this.progressbar1=(ProgressBar)view.findViewById(R.id.progressbar1);
            this.progressbar2=(ProgressBar)view.findViewById(R.id.progressbar2);
            this.answer_layout=(LinearLayout)view.findViewById(R.id.answer_layout);
            this.progress_layout=(LinearLayout)view.findViewById(R.id.progress_layout);
            this.answer1=(Button)view.findViewById(R.id.answer1);
            this.answer2=(Button)view.findViewById(R.id.answer2);

            mCoverFlow = (FeatureCoverFlow) view.findViewById(R.id.coverflow);

        }
    }

}
