package com.compunet.stuforia;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class EditProfile extends ActionBarActivity {
    private int year=1990;
    private int month;
    private int day;
    Bitmap myBitmap,myBitmap1;
    int date1pick = 1111;
    RadioGroup radioGroup;
    RadioGroup radiogrop1;
    private File pass;
    private File i20;
    public static final int CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE = 1777;
    private int PICK_IMAGE_REQUEST_FOR_I20 = 2;
    private int PICK_IMAGE_REQUEST = 1;
    String name=null,image_type;
    ImageView changeDate;
    ProgressDialog dialog = null;
    ProgressDialog dialog1 = null;
    ImageView tochangeDate,dob,hsc_date,ug_date;
    ImageView passimage,i20image;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;



    ScrollView scrollViewEditProfile;
    EditText first,last,father_name,father_age,mother_name,mother_age,account_number,DOB,address,state,city,pincode,mobile,email,passport,hsc,ug,workfrom,workto;
    LinearLayout i20remove,passcemara,passattach,i20cemara,i20attach,passportlayout,i20layout,passportLayoutAttach,i20LayoutAttach,cancelPassUpdate,canceli20Update,removePass,removeI20;
    String id;
    int idx,idx1;
    String datepickertag;
    String sourceFileUri;
    Button updatei20_image,updatepassport_image,ok;
    ArrayList<NameValuePair> nameValuePairs;
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
    public final Pattern PASS_ADDRESS_PATTERN = Pattern.compile("[A-Z]{0,1}" + "[0-9][0-9]{6}");
    private File image;
    Animation myAnim;
    ImageView viewImage,cleardob,clearhsc,clearug,clearfrom,clearto;
    public static final int CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE_DB=1325;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_alaga_try);
        scrollViewEditProfile=(ScrollView)findViewById(R.id.scrollViewEditProfile);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        LinearLayout linear=(LinearLayout)findViewById(R.id.linear);
        LinearLayout.LayoutParams paramsmain1 = (LinearLayout.LayoutParams) linear.getLayoutParams();
        paramsmain1.height =(int) (height * .2);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2d5da7")));




        passportlayout=(LinearLayout)findViewById(R.id.passportlayout);
        i20layout=(LinearLayout)findViewById(R.id.i20layout);
        passportLayoutAttach=(LinearLayout)findViewById(R.id.passportLayoutAttach);
        i20LayoutAttach=(LinearLayout)findViewById(R.id.i20LayoutAttach);
        cancelPassUpdate=(LinearLayout)findViewById(R.id.cancelPassUpdate);
        canceli20Update=(LinearLayout)findViewById(R.id.canceli20Update);
        passimage=(ImageView)findViewById(R.id.passimage);
        passimage.setDrawingCacheEnabled(false);
        i20image=(ImageView)findViewById(R.id.i20image);
        i20image.setDrawingCacheEnabled(false);
        updatei20_image =(Button)findViewById(R.id.updatei20_image);
        updatepassport_image=(Button)findViewById(R.id.updatepassport_image);
        Intent intent=getIntent();

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();
        id=prefs.getString("id", null);

        radioGroup = (RadioGroup) findViewById(R.id.genderradio);
        if(prefs.getString("gender", null).equals("Third Gender")){
            ((RadioButton)radioGroup.getChildAt(2)).setTextColor(Color.WHITE);
            ((RadioButton)radioGroup.getChildAt(2)).setChecked(true);
        }
        else if(prefs.getString("gender", null).equals("Female")){
            ((RadioButton)radioGroup.getChildAt(1)).setTextColor(Color.WHITE);
            ((RadioButton)radioGroup.getChildAt(1)).setChecked(true);
        }
        else{
            ((RadioButton)radioGroup.getChildAt(0)).setTextColor(Color.WHITE);
            ((RadioButton)radioGroup.getChildAt(0)).setChecked(true);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
          case R.id.gendermale:
              ((RadioButton)group.getChildAt(0)).setTextColor(Color.WHITE);
              ((RadioButton)group.getChildAt(1)).setTextColor(Color.BLACK);
              ((RadioButton)group.getChildAt(2)).setTextColor(Color.BLACK);
              break;
          case R.id.genderfemale:
              ((RadioButton)radioGroup.getChildAt(0)).setTextColor(Color.BLACK);
              ((RadioButton)radioGroup.getChildAt(1)).setTextColor(Color.WHITE);
              ((RadioButton)radioGroup.getChildAt(2)).setTextColor(Color.BLACK);
              break;
          case R.id.gender3rd:
              ((RadioButton)radioGroup.getChildAt(0)).setTextColor(Color.BLACK);
              ((RadioButton)radioGroup.getChildAt(1)).setTextColor(Color.BLACK);
              ((RadioButton)radioGroup.getChildAt(2)).setTextColor(Color.WHITE);
              break;
        }
        }
    });

        radiogrop1 = (RadioGroup) findViewById(R.id.radiogroup1);
        if(prefs.getString("nationality", null).equals("Foreigner")){
            ((RadioButton)radiogrop1.getChildAt(2)).setTextColor(Color.WHITE);
            ((RadioButton)radiogrop1.getChildAt(2)).setChecked(true);
        }
        else if(prefs.getString("nationality", null).equals("NRI")){
            ((RadioButton)radiogrop1.getChildAt(1)).setTextColor(Color.WHITE);
            ((RadioButton)radiogrop1.getChildAt(1)).setChecked(true);
        }
        else{
            ((RadioButton)radiogrop1.getChildAt(0)).setTextColor(Color.WHITE);
            ((RadioButton)radiogrop1.getChildAt(0)).setChecked(true);
        }

       radiogrop1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio4:
                        ((RadioButton)radiogrop1.getChildAt(0)).setTextColor(Color.WHITE);
                        ((RadioButton)radiogrop1.getChildAt(1)).setTextColor(Color.BLACK);
                        ((RadioButton)radiogrop1.getChildAt(2)).setTextColor(Color.BLACK);
                        break;
                    case R.id.radio5:
                        ((RadioButton)radiogrop1.getChildAt(0)).setTextColor(Color.BLACK);
                        ((RadioButton)radiogrop1.getChildAt(1)).setTextColor(Color.WHITE);
                        ((RadioButton)radiogrop1.getChildAt(2)).setTextColor(Color.BLACK);
                        break;
                    case R.id.radio6:
                        ((RadioButton)radiogrop1.getChildAt(0)).setTextColor(Color.BLACK);
                        ((RadioButton)radiogrop1.getChildAt(1)).setTextColor(Color.BLACK);
                        ((RadioButton)radiogrop1.getChildAt(2)).setTextColor(Color.WHITE);
                        break;
                }
            }
        });

        viewImage=(ImageView) findViewById(R.id.viewImage);
        changeDate = (ImageView) findViewById(R.id.from_work);
        tochangeDate = (ImageView) findViewById(R.id.to_work);
        hsc_date= (ImageView) findViewById(R.id.date_hsc);
        ug_date= (ImageView) findViewById(R.id.date_ug);
        dob = (ImageView) findViewById(R.id.dobpicker);
        first=(EditText)findViewById(R.id.firstname);
        last=(EditText)findViewById(R.id.lastname);
        father_name=(EditText)findViewById(R.id.fathername);
        father_age=(EditText)findViewById(R.id.fatherage);
        mother_name=(EditText)findViewById(R.id.mothername);
        mother_age=(EditText)findViewById(R.id.motherage);
        account_number=(EditText)findViewById(R.id.account_number);
        DOB=(EditText)findViewById(R.id.DOB);
        address=(EditText)findViewById(R.id.address);
        state=(EditText)findViewById(R.id.state);
        city=(EditText)findViewById(R.id.city);
        pincode=(EditText)findViewById(R.id.pin);
        mobile=(EditText)findViewById(R.id.phone);
        email=(EditText)findViewById(R.id.email);
        passport=(EditText)findViewById(R.id.passport);
        hsc=(EditText)findViewById(R.id.hsc);
        ug=(EditText)findViewById(R.id.to);
        workfrom=(EditText)findViewById(R.id.from);
        workto=(EditText)findViewById(R.id.work_to);
        removeI20=(LinearLayout)findViewById(R.id.removeI20);
        removePass=(LinearLayout)findViewById(R.id.removePass);


        LinearLayout.LayoutParams paramsmain2 = (LinearLayout.LayoutParams) passportlayout.getLayoutParams();
        paramsmain2.height =(int) (height * .25);

        LinearLayout.LayoutParams paramsmain3 = (LinearLayout.LayoutParams) passportLayoutAttach.getLayoutParams();
        paramsmain3.height =(int) (height * .25);

        LinearLayout.LayoutParams paramsmain4 = (LinearLayout.LayoutParams) i20layout.getLayoutParams();
        paramsmain4.height =(int) (height * .25);

        LinearLayout.LayoutParams paramsmain5 = (LinearLayout.LayoutParams) i20LayoutAttach.getLayoutParams();
        paramsmain5.height =(int) (height * .25);

        if(intent.getBooleanExtra("not_myprofile",false))
        {
            scrollViewEditProfile.post(new Runnable() {
                @Override
                public void run() {
                    scrollViewEditProfile.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }

        viewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        first.setText(prefs.getString("firstname", null));
        last.setText(prefs.getString("lastname", null));
        father_name.setText(prefs.getString("father_name", null));


        if(prefs.getString("father_age", null).equals("0"))
        {
            father_age.setText("");
        }
        else
        father_age.setText(prefs.getString("father_age", null));
        mother_name.setText(prefs.getString("mother_name", null));
        if(prefs.getString("mother_age", null).equals("0"))
        {
            mother_age.setText("");
        }
        else
        mother_age.setText(prefs.getString("mother_age", null));
        account_number.setText(prefs.getString("account_number", null));
        email.setText(prefs.getString("email", null));
        address.setText(prefs.getString("address", null));
        state.setText(prefs.getString("state", null));
        city.setText(prefs.getString("city", null));
        if(prefs.getString("pincode", null).equals("0"))
        {
            pincode.setText("");
        }
        else
        pincode.setText(prefs.getString("pincode", null));
        mobile.setText(prefs.getString("mobilenumber", null));
        passport.setText(prefs.getString("passportnumber", null));




        cleardob=(ImageView)findViewById(R.id.cleardob);
        clearhsc=(ImageView)findViewById(R.id.clearhsc);
        clearug=(ImageView)findViewById(R.id.clearug);
        clearfrom=(ImageView)findViewById(R.id.clearfrom);
        clearto=(ImageView)findViewById(R.id.clearto);


        if(prefs.getString("DOB",null).equals("0000-00-00"))
        {
            DOB.setText("");
            cleardob.setVisibility(View.GONE);
        }
        else
        {
            DOB.setText(convertDateFormat(prefs.getString("DOB", null)));
        }

        if(prefs.getString("hsc_education", null).equals("0000-00-00"))
        {
            hsc.setText("");
            clearhsc.setVisibility(View.GONE);
        }
        else
        hsc.setText(convertDateFormat(prefs.getString("hsc_education", null)));

        if(prefs.getString("ug_education", null).equals("0000-00-00"))
        {
            ug.setText("");
            clearug.setVisibility(View.GONE);
        }
        else
        ug.setText(convertDateFormat(prefs.getString("ug_education", null)));
        if(prefs.getString("work_from", null).equals("0000-00-00"))
        {
            workfrom.setText("");
            clearfrom.setVisibility(View.GONE);
        }
        else
            workfrom.setText(convertDateFormat(prefs.getString("work_from", null)));
        if(prefs.getString("work_to", null).equals("0000-00-00"))
        {
            workto.setText("");
            clearto.setVisibility(View.GONE);
        }
        else
        workto.setText(convertDateFormat(prefs.getString("work_to", null)));


        cleardob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DOB.setText("");
                cleardob.setVisibility(View.GONE);
            }
        });

        clearhsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hsc.setText("");
                clearhsc.setVisibility(View.GONE);
            }
        });

        clearug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ug.setText("");
                clearug.setVisibility(View.GONE);
            }
        });

        clearfrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workfrom.setText("");
                clearfrom.setVisibility(View.GONE);
            }
        });

        clearto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workto.setText("");
                clearto.setVisibility(View.GONE);
            }
        });

        if(!prefs.getString("passport_image", null).isEmpty())
        {
            passportlayout.setVisibility(View.VISIBLE);
            passportLayoutAttach.setVisibility(View.GONE);
            image_type="passport";
            new ImageLoad1().execute("http://www.venbaventures.com/stuforia/profileattch/thm_" +prefs.getString("passport_image", null));
            passportlayout.setVisibility(View.VISIBLE);
            passportLayoutAttach.setVisibility(View.GONE);
        }
        else
        {
            passportlayout.setVisibility(View.GONE);
            passportLayoutAttach.setVisibility(View.VISIBLE);
            cancelPassUpdate.setVisibility(View.GONE);
            removePass.setVisibility(View.GONE);
        }
        if(!prefs.getString("i20_image", null).isEmpty())
        {
            i20layout.setVisibility(View.VISIBLE);
            i20LayoutAttach.setVisibility(View.GONE);
            image_type="i20";
            new ImageLoad().execute("http://www.venbaventures.com/stuforia/profileattch/thm_" + prefs.getString("i20_image", null));
            i20layout.setVisibility(View.VISIBLE);
            i20LayoutAttach.setVisibility(View.GONE);

        }
        else{
            i20layout.setVisibility(View.GONE);
            i20LayoutAttach.setVisibility(View.VISIBLE);
            canceli20Update.setVisibility(View.GONE);
            removeI20.setVisibility(View.GONE);
        }

        updatepassport_image.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        passportlayout.setVisibility(View.GONE);
        passportLayoutAttach.setVisibility(View.VISIBLE);
        cancelPassUpdate.setVisibility(View.VISIBLE);
        removePass.setVisibility(View.VISIBLE);
    }
    });


        updatei20_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i20layout.setVisibility(View.GONE);
                i20LayoutAttach.setVisibility(View.VISIBLE);
                canceli20Update.setVisibility(View.VISIBLE);
                removeI20.setVisibility(View.VISIBLE);
            }
        });

        canceli20Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i20layout.setVisibility(View.VISIBLE);
                i20LayoutAttach.setVisibility(View.GONE);
            }
        });

        cancelPassUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passportlayout.setVisibility(View.VISIBLE);
                passportLayoutAttach.setVisibility(View.GONE);
            }
        });





        removeI20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canceli20Update.setVisibility(View.GONE);
                removeI20.setVisibility(View.GONE);
                name=md5(id+"_"+"i20form");
                editor.putString("i20_image", "");
                editor.commit();
                nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("id", id));
                nameValuePairs.add(new BasicNameValuePair("image_type", "i20"));
                nameValuePairs.add(new BasicNameValuePair("name", name + ".jpg"));
                StrictMode.setThreadPolicy(policy);
                new RemoveAttach().execute("http://www.venbaventures.com/stuforia/remove_documents.php");
            }
        });


        removePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelPassUpdate.setVisibility(View.GONE);
                removePass.setVisibility(View.GONE);
                name=md5(id+"_"+"passport");
                editor.putString("passport_image", "");
                editor.commit();
                nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("id", id));
                nameValuePairs.add(new BasicNameValuePair("image_type", "passport"));
                nameValuePairs.add(new BasicNameValuePair("name", name + ".jpg"));
                StrictMode.setThreadPolicy(policy);
                new RemoveAttach().execute("http://www.venbaventures.com/stuforia/remove_documents.php");
            }
        });


        ok=(Button)findViewById(R.id.ok);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  if(mobile.getText().toString()!=null){
                      if(mobile.getText().toString().length()!=10)
                      {
                          Toast.makeText(EditProfile.this,"Please provide valid phone number ",Toast.LENGTH_LONG).show();
                      }
                      else if(!checkEmail(email.getText().toString())&&!email.getText().toString().isEmpty())
                      {
                          Toast.makeText(EditProfile.this,"Please provide valid email ",Toast.LENGTH_LONG).show();
                      }
                      else if(!checkPass(passport.getText().toString()) &&! passport.getText().toString().isEmpty())
                      {
                          Toast.makeText(EditProfile.this,"Please provide valid passport number ",Toast.LENGTH_LONG).show();
                      }
                      else{
                          int radioButtonID = radioGroup.getCheckedRadioButtonId();
                          View radioButton = radioGroup.findViewById(radioButtonID);
                          idx = radioGroup.indexOfChild(radioButton);
                          int radioButtonID1 = radiogrop1.getCheckedRadioButtonId();
                          View radioButton1 = radiogrop1.findViewById(radioButtonID1);
                          idx1 = radiogrop1.indexOfChild(radioButton1);
                          nameValuePairs = new ArrayList<NameValuePair>();
                          nameValuePairs.add(new BasicNameValuePair("id", id));
                          nameValuePairs.add(new BasicNameValuePair("firstname", first.getText().toString()));
                          nameValuePairs.add(new BasicNameValuePair("lastname", last.getText().toString()));
                          nameValuePairs.add(new BasicNameValuePair("father_name", father_name.getText().toString()));
                          nameValuePairs.add(new BasicNameValuePair("father_age", father_age.getText().toString()));
                          nameValuePairs.add(new BasicNameValuePair("mother_name", mother_name.getText().toString()));
                          nameValuePairs.add(new BasicNameValuePair("mother_age", mother_age.getText().toString()));
                          nameValuePairs.add(new BasicNameValuePair("account_number", account_number.getText().toString()));
                          nameValuePairs.add(new BasicNameValuePair("gender", Integer.toString(idx + 1)));
                          nameValuePairs.add(new BasicNameValuePair("address", address.getText().toString()));
                          nameValuePairs.add(new BasicNameValuePair("state", state.getText().toString()));
                          nameValuePairs.add(new BasicNameValuePair("city", city.getText().toString()));
                          nameValuePairs.add(new BasicNameValuePair("email", email.getText().toString()));
                          nameValuePairs.add(new BasicNameValuePair("pincode", pincode.getText().toString()));
                          nameValuePairs.add(new BasicNameValuePair("nationality", Integer.toString(idx1 + 1)));
                          nameValuePairs.add(new BasicNameValuePair("mobilenumber", mobile.getText().toString()));
                          nameValuePairs.add(new BasicNameValuePair("passportnumber", passport.getText().toString()));
                          nameValuePairs.add(new BasicNameValuePair("dob",convertDateFormatForDataBase(DOB.getText().toString())));
                          nameValuePairs.add(new BasicNameValuePair("hsc_education",convertDateFormatForDataBase(hsc.getText().toString())));
                          nameValuePairs.add(new BasicNameValuePair("ug_education",convertDateFormatForDataBase(ug.getText().toString())));
                          nameValuePairs.add(new BasicNameValuePair("work_from",convertDateFormatForDataBase(workfrom.getText().toString())));
                          nameValuePairs.add(new BasicNameValuePair("work_to",convertDateFormatForDataBase(workto.getText().toString())));
                          StrictMode.setThreadPolicy(policy);
                          new SaveDetails().execute("http://www.venbaventures.com/stuforia/update_profile.php");
                      }
                  }
            }
        });
        changeDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                date1pick=1;
                showDialog(date1pick);

            }

        });

        tochangeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date1pick=2;
                showDialog(date1pick);

            }
        });

        hsc_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                date1pick=3;
                showDialog(date1pick);

            }

        });
        ug_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                date1pick=4;
                showDialog(date1pick);

            }

        });
        dob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                date1pick=5;
                showDialog(date1pick);

            }

        });








        passcemara=(LinearLayout)findViewById(R.id.passcemara);
        passattach=(LinearLayout)findViewById(R.id.passattach);
        i20cemara=(LinearLayout)findViewById(R.id.i20cemara);
        i20attach=(LinearLayout)findViewById(R.id.i20attach);

        passcemara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                File imagesFolder = new File(Environment.getExternalStoragePublicDirectory(new String("Stuforia")), "Profile_Attach");
                imagesFolder.mkdirs();
                imagesFolder.setWritable(true);
                name=md5(id+"_"+"passport");
                image_type="passport";
                pass = new File(imagesFolder, name+".jpg");
                Log.e("name",name);
                Log.e("pass", String.valueOf(pass));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(pass));
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);
            }
        });
       passattach.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
               startActivityForResult(photoPickerIntent, PICK_IMAGE_REQUEST);
           }
       });
        i20cemara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                File imagesFolder = new File(Environment.getExternalStoragePublicDirectory(new String("Stuforia")), "Profile_Attach");
                imagesFolder.mkdirs();
                imagesFolder.setWritable(true);
                name=md5(id+"_"+"i20form");
                image_type="i20";
                i20 = new File(imagesFolder, name+".jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(i20));
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);
            }
        });
        i20attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
                startActivityForResult(photoPickerIntent, PICK_IMAGE_REQUEST_FOR_I20);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
           if(resultCode==RESULT_CANCELED)
           {
               scrollViewEditProfile.post(new Runnable() {
                   @Override
                   public void run() {
                       scrollViewEditProfile.fullScroll(ScrollView.FOCUS_DOWN);
                   }
               });
           }
        if (requestCode == CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK)
        {
            scrollViewEditProfile.post(new Runnable() {
                @Override
                public void run() {
                    scrollViewEditProfile.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
            Uri gallery =null;
            if(image_type.equals("passport")) {
                 gallery = Uri.fromFile(pass);
                Log.e("uri", String.valueOf(gallery));
            }
            if(image_type.equals("i20")) {
                 gallery = Uri.fromFile(i20);
                Log.e("uri", String.valueOf(gallery));
            }
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), gallery);
                FileOutputStream fos = null;
                fos = new FileOutputStream("storage/emulated/0/Stuforia/Profile_Attach/" + name + ".jpg");
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            }
            catch (Exception e)
            {
                Log.e("exception", String.valueOf(e));
            }
            sourceFileUri = "storage/emulated/0/Stuforia/Profile_Attach/"+name+".jpg";
            Log.e("filename",sourceFileUri);
            new UploadFileAsync().execute("");
        }
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            scrollViewEditProfile.post(new Runnable() {
                @Override
                public void run() {
                    scrollViewEditProfile.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
            File imagesFolder = new File(Environment.getExternalStoragePublicDirectory(new String("Stuforia")),"Profile_Attach");
            imagesFolder.mkdirs();
            imagesFolder.setWritable(true);
            name=md5(id+"_"+"passport");
            pass = new File(imagesFolder, name+".jpg");
            Log.e("name",name);
            Log.e("pass", String.valueOf(pass));
            Uri gallery=data.getData();
            Log.e("uri", String.valueOf(gallery));
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), gallery);
                String strMyImagePath = pass.getAbsolutePath();
                FileOutputStream fos = null;
                fos = new FileOutputStream(strMyImagePath);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
                Log.e("imagepath", strMyImagePath);
                Log.e("bitmap", String.valueOf(bitmap));
                sourceFileUri = "storage/emulated/0/Stuforia/Profile_Attach/"+name+".jpg";
                image_type="passport";
                new UploadFileAsync().execute("");
            }
            catch (Exception e)
            {
                Log.e("error passport", String.valueOf(e));
            }
        }
        if (requestCode == PICK_IMAGE_REQUEST_FOR_I20 && resultCode == RESULT_OK) {
            scrollViewEditProfile.post(new Runnable() {
                @Override
                public void run() {
                    scrollViewEditProfile.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
            File imagesFolder = new File(Environment.getExternalStoragePublicDirectory(new String("Stuforia")),"Profile_Attach");
            imagesFolder.mkdirs();
            imagesFolder.setWritable(true);
             name=md5(id+"_"+"i20form");
            image_type="i20";
            i20 = new File(imagesFolder, name+".jpg");
            Uri gallery=data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), gallery);
                String strMyImagePath = i20.getAbsolutePath();
                FileOutputStream fos = null;
                fos = new FileOutputStream(strMyImagePath);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
                sourceFileUri = "storage/emulated/0/Stuforia/Profile_Attach/"+name+".jpg";
                new UploadFileAsync().execute("");
            }
            catch (Exception e)
            {
                Log.e("error i20", String.valueOf(e));
            }
        }
        if (requestCode == CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE_DB  && resultCode == Activity.RESULT_OK) {
            try {
                Intent myIntent = new Intent(EditProfile.this, CropActivity.class);
                myIntent.putExtra("tag", "camera");
                startActivity(myIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public String convertDateFormat(String date){
        String newDate="";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat changed = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            Date modifiedDate = dateFormat.parse(date);
            newDate =  changed.format(modifiedDate);
        } catch (ParseException pe) {
            newDate="DOB";
        }
        Log.e("date convert",newDate);
        return newDate;
    }

    public String convertDateFormatForDataBase(String date){
        String newDate="";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat changed = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date modifiedDate = dateFormat.parse(date);
            newDate =  changed.format(modifiedDate);
        } catch (ParseException pe) {
            newDate="DOB";
        }
        Log.e("date convertdatabase",newDate);
        return newDate;
    }
    private class SaveDetails extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute()
        {
            dialog = ProgressDialog.show(EditProfile.this, "",
                    "Updating Profile details...", true);
        }
        @Override
        protected String doInBackground(String... params) {
            String responseStr="";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(params[0]);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());
            } catch (Exception ex) {
                responseStr="InternetFailed";
                ex.printStackTrace();
            }
            return responseStr;
        }
        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            String output = result.replaceAll("\\s+", "");
            if(output.equals("InternetFailed"))
            {
                Toast.makeText(EditProfile.this, "Couldn't connect. Make sure that your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();
            }
            if(output.equals("update_successfully")){

                editor.putString("firstname",first.getText().toString());
                editor.putString("lastname", last.getText().toString());
                editor.putString("address",address.getText().toString());
                editor.putString("state",state.getText().toString());
                editor.putString("city",city.getText().toString());
                editor.putString("mobilenumber", mobile.getText().toString());
                editor.putString("passportnumber",passport.getText().toString());
                editor.putString("email",email.getText().toString());
                editor.putString("father_name",father_name.getText().toString());
                editor.putString("mother_name",mother_name.getText().toString());
                editor.putString("account_number",account_number.getText().toString());
                if(!father_age.getText().toString().isEmpty())
                {
                    editor.putString("father_age",father_age.getText().toString());
                }
                if(!pincode.getText().toString().isEmpty())
                {
                    editor.putString("pincode",pincode.getText().toString());
                }
                if(!mother_age.getText().toString().isEmpty())
                {
                    editor.putString("mother_age",mother_age.getText().toString());
                }
                if(!DOB.getText().toString().isEmpty())
                {
                    editor.putString("DOB",convertDateFormatForDataBase(DOB.getText().toString()));
                }
                else{
                    editor.putString("DOB","0000-00-00");
                }
                if(!hsc.getText().toString().isEmpty())
                {
                    editor.putString("hsc_education",convertDateFormatForDataBase(hsc.getText().toString()));
                }
                else{
                    editor.putString("hsc_education","0000-00-00");
                }
                if(!ug.getText().toString().isEmpty())
                {
                    editor.putString("ug_education",convertDateFormatForDataBase(ug.getText().toString()));
                }
                else{
                    editor.putString("ug_education","0000-00-00");
                }
                if(!workfrom.getText().toString().isEmpty())
                {
                    editor.putString("work_from",convertDateFormatForDataBase(workfrom.getText().toString()));
                }
                else{
                    editor.putString("work_from","0000-00-00");
                }
                if(!workto.getText().toString().isEmpty())
                {
                    editor.putString("work_to",convertDateFormatForDataBase(workto.getText().toString()));
                }
                else{
                    editor.putString("work_to","0000-00-00");
                }
                if(idx==0)
                editor.putString("gender","Male");
                if(idx==1)
                    editor.putString("gender","Female");
                if(idx==2)
                    editor.putString("gender","Third Gender");
                if(idx1==0)
                    editor.putString("nationality","Indian");
                if(idx1==1)
                    editor.putString("nationality","NRI");
                if(idx1==2)
                    editor.putString("nationality","Foreigner");
                editor.commit();
               finish();
            }
            else if(output.equals("failed"))
            {
                Toast.makeText(EditProfile.this,"Your email ID or phone number already exist ",Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    private class UploadFileAsync extends AsyncTask<String, Void, String> {

        @Override
        protected  void onPreExecute()
        {
            dialog = ProgressDialog.show(EditProfile.this, "",
                    "Uploading high resolution image...This might take few minutes", true);
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                HttpURLConnection conn = null;
                DataOutputStream dos = null;
                String lineEnd = "\r\n";
                String twoHyphens = "--";
                String boundary = "*****";
                int bytesRead, bytesAvailable, bufferSize;
                byte[] buffer;
                int maxBufferSize = 1 * 100 * 100;

                File sourceFile = new File(sourceFileUri);

                if (sourceFile.isFile()) {

                    try {
                        String upLoadServerUri = "http://www.venbaventures.com/stuforia/passportattach.php?";

                        // open a URL connection to the Servlet
                        FileInputStream fileInputStream = new FileInputStream(
                                sourceFile);
                        URL url = new URL(upLoadServerUri);

                        // Open a HTTP connection to the URL
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true); // Allow Inputs
                        conn.setDoOutput(true); // Allow Outputs
                        conn.setUseCaches(false); // Don't use a Cached Copy
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Connection", "Keep-Alive");
                        conn.setRequestProperty("ENCTYPE",
                                "multipart/form-data");
                        conn.setRequestProperty("Content-Type",
                                "multipart/form-data;boundary=" + boundary);
                        //  conn.setRequestProperty("bill", sourceFileUri);

                        dos = new DataOutputStream(conn.getOutputStream());

                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                                + sourceFileUri + "\"" + lineEnd);

                        dos.writeBytes(lineEnd);

                        // create a buffer of maximum size
                        bytesAvailable = fileInputStream.available();

                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        buffer = new byte[bufferSize];

                        // read file and write it into form...
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                        while (bytesRead > 0) {

                            dos.write(buffer, 0, bufferSize);
                            bytesAvailable = fileInputStream.available();
                            bufferSize = Math.min(bytesAvailable, maxBufferSize);
                            bytesRead = fileInputStream.read(buffer, 0,
                                    bufferSize);

                        }

                        // send multipart form data necesssary after file
                        // data...
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + twoHyphens
                                + lineEnd);

                        // Responses from the server (code and message)

                        String serverResponseMessage = conn
                                .getResponseMessage();



                        // close the streams //
                        fileInputStream.close();
                        dos.flush();
                        dos.close();

                    } catch (Exception e) {

                          dialog.dismiss();
                        Log.e("exception1", String.valueOf(e));
                    }


                } // End else block


            } catch (Exception ex) {

                             dialog.dismiss();
                Log.e("exception2", String.valueOf(ex));
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("id", id));
            nameValuePairs.add(new BasicNameValuePair("image_type",image_type));
            nameValuePairs.add(new BasicNameValuePair("name",name+".jpg"));
            StrictMode.setThreadPolicy(policy);
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://www.venbaventures.com/stuforia/update_documents.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
            }
            catch (Exception e)
            {
                dialog.dismiss();
                Log.e("exception3", String.valueOf(e));
            }
            if(image_type.equals("passport"))
            {
                new ImageLoad().execute("http://www.venbaventures.com/stuforia/profileattch/thm_"+name+".jpg");
                editor.putString("passport_image",name+".jpg");
                editor.commit();
                passportlayout.setVisibility(View.VISIBLE);
                passportLayoutAttach.setVisibility(View.GONE);
            }
            if(image_type.equals("i20"))
            {
                new ImageLoad().execute("http://www.venbaventures.com/stuforia/profileattch/thm_"+name+".jpg");
                editor.putString("i20_image",name+".jpg");
                editor.commit();
                i20layout.setVisibility(View.VISIBLE);
                i20LayoutAttach.setVisibility(View.GONE);
            }
            dialog.dismiss();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //Toast.makeText(getActivity(),"Pause",Toast.LENGTH_LONG).show();
        Log.e("tag", "Pause");
    }
    @Override
    public void onResume() {
        super.onResume();
        viewImage.setImageResource(R.drawable.loading_white);
        myBitmap1=null;
        new ImageLoaddp().execute(prefs.getString("profilepic", null));
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    private boolean checkEmail(String email){
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    private boolean checkPass(String pass) {
        return PASS_ADDRESS_PATTERN.matcher(pass).matches();
    }

    protected Dialog onCreateDialog(int id) {

                return new DatePickerDialog(this, pickerListener, year, month, day);
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,int selectedMonth, int selectedDay)
        {
            year = selectedYear;
            month = selectedMonth;
            String dayfordatabase="";
            String monthfordatabase="";
            day = selectedDay;
            if(day<10){
                dayfordatabase="0"+day;
            }
            else {
                dayfordatabase=Integer.toString(day);
            }
            if(month<10){
                int temp=month;
                temp++;
                monthfordatabase="0"+temp;
            }
            else {
                monthfordatabase=Integer.toString(month);
            }
            String[] montharray={"Jan","Feb","Mar","Apr","May","Jun","July","Aug","Sep","Oct","Nov","Dec"};
            if(date1pick==1) {
                workfrom.setText(new StringBuilder().append(day)
                                .append("-").append(montharray[month]).append("-").append(year)
                );
                clearfrom.setVisibility(View.VISIBLE);
               // str[13]=year+"-"+monthfordatabase+"-"+ dayfordatabase;
            }
            if(date1pick==2) {
                workto.setText(new StringBuilder().append(day)
                                .append("-").append(montharray[month]).append("-").append(year)
                );
                clearto.setVisibility(View.VISIBLE);
              //  str[14]=year+"-"+monthfordatabase+"-"+ dayfordatabase;
            }
            if(date1pick==3) {
                hsc.setText(new StringBuilder().append(day)
                                .append("-").append(montharray[month]).append("-").append(year)
                );
                //str[11]=year+"-"+monthfordatabase+"-"+ dayfordatabase;
               clearhsc.setVisibility(View.VISIBLE);
            }
            if(date1pick==4) {
                ug.setText(new StringBuilder().append(day)
                                .append("-").append(montharray[month]).append("-").append(year)
                );
                clearug.setVisibility(View.VISIBLE);
              //  str[12]=year+"-"+monthfordatabase+"-"+ dayfordatabase;
            }
            if(date1pick==5) {
                DOB.setText(new StringBuilder().append(day)
                                .append("-").append(montharray[month]).append("-").append(year)
                );
                        cleardob.setVisibility(View.VISIBLE);
               // str[3]=year+"-"+monthfordatabase+"-"+ dayfordatabase;
            }
        }
    };

    private class ImageLoad extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute()
        {
         dialog1 = ProgressDialog.show(EditProfile.this, "",
            " Loading...", true);
        }
        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                 myBitmap = BitmapFactory.decodeStream(input);


            } catch (IOException e) {
              dialog1.dismiss();
                e.printStackTrace();

            }
          return null;
        }
        @Override
        protected void onPostExecute(String result) {
        dialog1.dismiss();
            if(image_type.equals("i20"))
            {
                i20image.setImageBitmap(myBitmap);
            }
            if(image_type.equals("passport"))
            {
                passimage.setImageBitmap(myBitmap);
            }
            myBitmap=null;
        }
        @Override
        protected void onProgressUpdate(Void... values) {
        }

    }
    private class ImageLoad1 extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute()
        {
            //  dialog1 = ProgressDialog.show(EditProfile.this, "",
            //        "Update ...please wait...", true);
        }
        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                myBitmap = BitmapFactory.decodeStream(input);
                } catch (IOException e) {
            // dialog.dismiss();
                e.printStackTrace();

            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
         passimage.setImageBitmap(myBitmap);
         myBitmap=null;

        }
        @Override
        protected void onProgressUpdate(Void... values) {
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_payment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery","Set Default", "Cancel"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    File imagesFolder = new File(Environment.getExternalStoragePublicDirectory(new String("Stuforia")), "ProfilePic");
                    imagesFolder.mkdirs();
                    imagesFolder.setWritable(true);
                    image = new File(imagesFolder, "dp1.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image));
                    intent.putExtra("return-data", true);
                    startActivityForResult(intent, CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE_DB);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent myIntent = new Intent(EditProfile.this, CropActivity.class);
                    myIntent.putExtra("tag","gallery");
                   startActivity(myIntent);
                }else if (options[item].equals("Set Default")) {
                    nameValuePairs = new ArrayList<NameValuePair>();
                    nameValuePairs.add(new BasicNameValuePair("image_type", "profliepic"));
                    nameValuePairs.add(new BasicNameValuePair("id",id));
                    StrictMode.setThreadPolicy(policy);
                    new Removeprofilepic().execute("");
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }
        });
        builder.show();
    }
    private class Removeprofilepic extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute()
        {
            dialog = ProgressDialog.show(EditProfile.this, "",
                    "Removing...", true);
        }
        @Override
        protected String doInBackground(String... params) {
            String responseStr=null;
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://www.venbaventures.com/stuforia/remove_documents.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                responseStr = EntityUtils.toString(response.getEntity());
                responseStr = responseStr.replaceAll("\\s+", "");
                Log.e("error", responseStr);
                return responseStr;
            }
            catch (Exception e) {
                Log.e("error","catch");
                dialog.dismiss();
            }
            return responseStr;
        }
        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            if(result.equals("success")){
                editor.putString("profilepic", "http://www.venbaventures.com/stuforia/profilepic/default.jpg");
                editor.commit();
                if(!id.equals("skip_for_now")) {
                    viewImage.setImageResource(R.drawable.loading_white);
                    myBitmap=null;
                    new ImageLoaddp().execute(prefs.getString("profilepic", null));
                }
            }
        }


        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
    private class ImageLoaddp extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute()
        {
            myAnim    = AnimationUtils.loadAnimation(EditProfile.this, R.anim.anim);
            viewImage.startAnimation(myAnim);
            //dialog = ProgressDialog.show(getActivity(), "","Loading Data...", true);
        }
        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                myBitmap1 = BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                //dialog.dismiss();

                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            //  dialog.dismiss();

            viewImage.clearAnimation();
            myAnim.setAnimationListener(null);
            viewImage.setAnimation(null);
            viewImage.setImageBitmap(myBitmap1);
        }
        @Override
        protected void onProgressUpdate(Void... values) {
        }

    }


    private class RemoveAttach extends AsyncTask<String, Void, String> {
        @Override
        protected  void onPreExecute()
        {
            dialog = ProgressDialog.show(EditProfile.this, "",
                    "Removing...", true);
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(params[0]);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                String responseStr = EntityUtils.toString(response.getEntity());
               Log.e("error", responseStr);
            }
            catch (Exception e) {
              Log.e("error","catch");
                dialog.dismiss();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
dialog.dismiss();
        }


        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }


}
