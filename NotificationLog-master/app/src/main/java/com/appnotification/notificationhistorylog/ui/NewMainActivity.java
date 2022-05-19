package com.appnotification.notificationhistorylog.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.appnotification.notificationhistorylog.BuildConfig;
import com.appnotification.notificationhistorylog.CommonCl.SharedCommon;
import com.appnotification.notificationhistorylog.FAQActivity;
import com.appnotification.notificationhistorylog.FavActivity;
import com.appnotification.notificationhistorylog.R;
import com.appnotification.notificationhistorylog.SettingsActivity;

import com.appnotification.notificationhistorylog.TutorialActivity;
import com.appnotification.notificationhistorylog.misc.Const;
import com.appnotification.notificationhistorylog.misc.DatabaseHelper;
import com.appnotification.notificationhistorylog.misc.ExportTask;
import com.appnotification.notificationhistorylog.misc.Util;
import com.appnotification.notificationhistorylog.service.NotificationHandler;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.ShareCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.onesignal.OneSignal;

import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.view.Menu;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;

import static com.appnotification.notificationhistorylog.CommonCl.SharedCommon.key1;
import static com.appnotification.notificationhistorylog.CommonCl.SharedCommon.keyfaqs;
import static com.appnotification.notificationhistorylog.CommonCl.SharedCommon.keyopenrate;
import static com.appnotification.notificationhistorylog.CommonCl.SharedCommon.keyopenratetutorial;
import static com.appnotification.notificationhistorylog.CommonCl.SharedCommon.keyopensetting;
import static com.appnotification.notificationhistorylog.CommonCl.SharedCommon.keyoveruse;

public class NewMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, PopupMenu.OnMenuItemClickListener {

    public static final int NUMBER_OF_ADS = 10;
//    private AdLoader adLoader;
//    private List<UnifiedNativeAd> mNativeAds = new ArrayList<>();

    private FirebaseAuth mAuth;
    int versionCode = BuildConfig.VERSION_CODE;
    String versionName = BuildConfig.VERSION_NAME;


    ImageView acimage;
    TextView actitle, acsubtext;
    String appid = BuildConfig.APPLICATION_ID;
    FirebaseRemoteConfig firebaseRemoteConfigprice;

   // private InterstitialAd mInterstitialAd;

    public String whatnew;
    public String showallnotf;
    public String shownotif;
    public String showtuto;
    public String showfav;
    public String livenotice;

    public String versionfirebase;

    public String logno;

    TextView txtcount;
    String PrivacyUrl = "https://xenonstudio.in/helpnotifcation";
    private AdView mAdView;
    ImageView ivNotification;
    Animation shake;
    private boolean mlogtext = false;
    View view1, view2, view3, view4, view5;

    RelativeLayout rltip, rlfav, sharerl, tpfav, ncrl, seletcapps;
    private boolean mlogongoing = false;
    TextView txt, txtversion, logtextno;

    String apppackagename = "com.appnotification.notificationhistorylog";
    private DatabaseReference mUserRef, museref, mdatareport, mcredtref, mlinkupdate;

    private DatabaseHelper dbHelper;
    TextView txtlogcount;
    String onesignalid;
    String adnotshowing;
    Thread splashTread;
    private EditText searchEdit;
    private GuideView mGuideView;
    private GuideView.Builder buildergf, builder2;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Button buttonAllow;
    private FrameLayout frameLayout;
    private AlertDialog dialog = null;
    Button buttoncheck;
    ImageView imagelog;
    TextView txtcheck;
    public String lognumber;
    TextView offersnav, gallery, textGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);
        setTitle("");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        View headerview = navigationView.getHeaderView(0);

        txtlogcount = (TextView) headerview.findViewById(R.id.txtlogcount);
        // updatelognumber();
        initCountDrawer();
        txtlogcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatelognumber();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1A1F2B")));

        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.titleview, null);


        TextView actionbar_title = (TextView) v.findViewById(R.id.actionbar_title);
        //actionbar_title.setTypeface(you can set your font here also.);

        actionBar.setCustomView(v);
        getSupportActionBar().hide();
//These lines should be added in the OnCreate() of your main activity
       /* refermenu=(TextView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.nav_refer));
        statsmenu =(TextView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.nav_stats));
//This method will initialize the count value
        initializeCountDrawer();*/
      /*  Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_stats).setTitle("New");*/
        //menu.findItem(R.id.nav_rigt_version).setTitle(versionName);
        // freeorpro =(TextView)headerview.findViewById(R.id.freeorpro);
        mAuth = FirebaseAuth.getInstance();
      //  AudienceNetworkAds.initialize(this);
  //      mInterstitialAd = new com.facebook.ads.InterstitialAd(this, getString(R.string.fb_interstitial_ad));
    //    mInterstitialAd.loadAd();
     //   mAdView = new com.facebook.ads.AdView(this, getString(R.string.fb_banner_ad), com.facebook.ads.AdSize.BANNER_HEIGHT_50);
       // mAdView.loadAd();
      //  ((LinearLayout) headerview.findViewById(R.id.fb_container)).addView(mAdView);

        mAuth = FirebaseAuth.getInstance();


        if (mAuth.getCurrentUser() != null) {


            mdatareport = FirebaseDatabase.getInstance().getReference().child("UsageReports").child(mAuth.getCurrentUser().getUid());
            museref = FirebaseDatabase.getInstance().getReference().child("mainacreport").child(mAuth.getCurrentUser().getUid());

            mUserRef = FirebaseDatabase.getInstance().getReference().child("openreport").child(mAuth.getCurrentUser().getUid());

        }

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                Log.d("debug", "User:" + userId);
                if (registrationId != null)
                    onesignalid = userId;
                Log.d("debug", "registrationId:" + registrationId);

            }
        });

        frameLayout = findViewById(R.id.fragment_container);
        acimage = findViewById(R.id.acimage);
        acimage.setVisibility(View.GONE);
        actitle = findViewById(R.id.actitle);
        acsubtext = findViewById(R.id.acsubtext);
        txtcheck = (TextView) findViewById(R.id.logtxt);
        /*buttoncheck = findViewById(R.id.button_check);
        imagelog = (ImageView)findViewById(R.id.logimage);
        txtcheck = (TextView)findViewById(R.id.logtxt);

        buttoncheck.setVisibility(View.GONE);
        imagelog.setVisibility(View.GONE);
        txtcheck.setVisibility(View.GONE);
*/
        txtcheck.setVisibility(View.GONE);
        buttonAllow = findViewById(R.id.button_allow);
        buttonAllow.setOnClickListener(view -> {
            startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        });

        if (savedInstanceState == null && Util.isNotificationAccessEnabled(getApplicationContext())) {
            Bundle bundle = new Bundle();
            bundle.putString("selected_navigation", "Recents");
            RecentsFragment recentsFragment = new RecentsFragment();
            recentsFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    recentsFragment).commit();
            //  navigationView.setCheckedItem(R.id.nav_recents);
        } else {
            openDialog();
        }


        //ADS + FIREBASE

        firebaseRemoteConfigprice = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder().build();
        firebaseRemoteConfigprice.setConfigSettings(configSettings);

        Map<String, Object> pricedata = new HashMap<>();
        pricedata.put("showads", "yn");
        pricedata.put("showfavtab", "yn");
        pricedata.put("showtutorial", "yn");
        pricedata.put("showbrowseallnotification", "yn");
        pricedata.put("showbrowsenotification", "yn");
        pricedata.put("livenotice", "yn");


        firebaseRemoteConfigprice.setDefaults(pricedata);

        checkadsstatus();

        gallery = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.nav_stats));
        initializeCountDrawer();
        /*mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        //testid-ca-app-pub-3940256099942544/1033173712
        //upid-ca-app-pub-6778147776084460/3563503620
        //my-INT-ID--ca-app-pub-8081344892743036/1424914117
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mAuth = FirebaseAuth.getInstance();


        if (mAuth.getCurrentUser() != null) {


            mdatareport = FirebaseDatabase.getInstance().getReference().child("UsageReports").child(mAuth.getCurrentUser().getUid());
            museref = FirebaseDatabase.getInstance().getReference().child("mainacreport").child(mAuth.getCurrentUser().getUid());

            mUserRef = FirebaseDatabase.getInstance().getReference().child("openreport").child(mAuth.getCurrentUser().getUid());

        }*/


    }

    public void openDrawer() {
        drawer.openDrawer(Gravity.LEFT);
    }

    private void initializeCountDrawer() {
        //Gravity property aligns the text


        gallery.setGravity(Gravity.CENTER_VERTICAL);
        gallery.setTypeface(null, Typeface.BOLD);


        gallery.setTextColor(getResources().getColor(R.color.textcolor2));
        gallery.setText("New");
        gallery.setTextSize(12);


    }

    private void checklogcount() {


        BrowseAdapter adapter = new BrowseAdapter(NewMainActivity.this);
        String count = String.valueOf(adapter.getItemCount());
        //Toast.makeText(this, ""+count, Toast.LENGTH_SHORT).show();
        int logs = Integer.parseInt(count);

        if (logs == 0) {
          /*  buttoncheck.setVisibility(View.VISIBLE);
            imagelog.setVisibility(View.VISIBLE);
            txtcheck.setVisibility(View.VISIBLE);*/

            acimage.setVisibility(View.VISIBLE);

            buttonAllow.setVisibility(View.VISIBLE);
            buttonAllow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(NewMainActivity.this, NewMainActivity.class));
                    finish();
                }
            });
            buttonAllow.setText("Click To Retry");
            txtcheck.setVisibility(View.VISIBLE);
            Toast.makeText(this, "No Notifications Logged Yet !", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkadsstatus() {


        firebaseRemoteConfigprice.fetch(0)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Log.e("TaskError", "info" + firebaseRemoteConfigprice.getInfo().getLastFetchStatus());


                        Log.e("TaskError", "firebaseremote" + firebaseRemoteConfigprice.getString("btn_text"));

                        if (task.isSuccessful()) {


                            firebaseRemoteConfigprice.activateFetched();
                            /*txt600.setText(firebaseRemoteConfigprice.getString("txt600"));
                            txt1500.setText(firebaseRemoteConfigprice.getString("txt1500"));
                            txt3200.setText(firebaseRemoteConfigprice.getString("txt3200"));
                            txt5000.setText(firebaseRemoteConfigprice.getString("txt5000"));

 pricedata.put("showfavtab", "yn");
        pricedata.put("showtutorial", "yn");
        pricedata.put("showbrowseallnotification", "yn");
        pricedata.put("showbrowsenotification", "yn");

*/
                            whatnew = (firebaseRemoteConfigprice.getString("showads"));
                            showallnotf = (firebaseRemoteConfigprice.getString("showbrowseallnotification"));
                            showfav = (firebaseRemoteConfigprice.getString("showfavtab"));
                            showtuto = (firebaseRemoteConfigprice.getString("showtutorial"));
                            livenotice = (firebaseRemoteConfigprice.getString("livenotice"));
                            shownotif = (firebaseRemoteConfigprice.getString("showbrowsenotification"));


                            if (showtuto.equals("yes")) {
                                //rltip.setVisibility(View.VISIBLE);
                            } else {

                                // rltip.setVisibility(View.GONE);
                            }

                            if (shownotif.equals("yes")) {
                                //seletcapps.setVisibility(View.VISIBLE);
                            } else if (shownotif.equals("shownoif")) {


                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(NewMainActivity.this, R.style.AlertDialog);

                                builder.setMessage(livenotice);

                                builder.setCancelable(false);
                                builder.setPositiveButton("GOT IT", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        dialogInterface.dismiss();
                                    }
                                }).setNegativeButton("Developer Contact", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        Intent send = new Intent(Intent.ACTION_SENDTO);
                                        String uriText = "mailto:" + Uri.encode("thexenonstudio@gmail.com") +
                                                "?subject=" + Uri.encode("Notification Log - Developer Contact") +
                                                "&body=" + Uri.encode("Hello, Type Your Query/Problem/Bug/Suggestions Here" + " \n\n\n ------------ \n\n Version Code : " + versionCode + "\n Version Name : " + versionName + "\n Application ID : " + appid);
                                        Uri uri = Uri.parse(uriText);

                                        send.setData(uri);
                                        startActivity(Intent.createChooser(send, "Send Mail Via : "));
                                    }
                                });

                                android.app.AlertDialog dialog = builder.create();
                                dialog.show();
                            } else if (shownotif.equals("no")) {
                                //seletcapps.setVisibility(View.GONE);
                            }


                            if (whatnew.equals("yes")) {
                             //   showads();
                               // showintads();
                                //Toast.makeText(NewMainActivity.this, "Showing", Toast.LENGTH_SHORT).show();
                            } else if (whatnew.equals("no")) {
                                mAdView.setVisibility(View.GONE);

                                // Toast.makeText(NewMainActivity.this, "Nope", Toast.LENGTH_SHORT).show();

                                Log.e("AdsStatus", "Not Showing");

                            }

                            Log.e("TaskError", "firebaseremote" + firebaseRemoteConfigprice.getString("btn_text"));


                                   /* Picasso.get().load(firebaseRemoteConfigprice.getString("image_link"))
                                            .into(img);*/
                        } else {


                            String exp = ("" + task.getException().getMessage());
                            if (exp.equals("null")) {

                                whatnew = ("Server Not Responding ");


                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(NewMainActivity.this);


                            } else {
                                Log.e("TaskError", "taskexcep :" + task.getException().getMessage() + task.getException() + task);
                                Toast.makeText(NewMainActivity.this, "Internet Connection Error", Toast.LENGTH_SHORT).show();
                                View parentLayout = findViewById(android.R.id.content);

                                Snackbar snackbar = Snackbar
                                        .make(parentLayout, "Internet Connection Error", Snackbar.LENGTH_INDEFINITE);
                                View snackbarView = snackbar.getView();
                                snackbarView.setBackgroundColor(Color.parseColor("#FF104162"));
                                snackbar.setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //  checkConnection();
                                        snackbar.dismiss();
                                    }
                                });

                                snackbar.show();
                            }
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!Util.isNotificationAccessEnabled(getApplicationContext())) {
            openDialog();
            acimage.setVisibility(View.VISIBLE);
            actitle.setVisibility(View.VISIBLE);
            acsubtext.setVisibility(View.VISIBLE);

            String logstring = "Notification Log ";
            txtlogcount.setText(logstring);

        } else {


            firstrun();
            buttonAllow.setVisibility(View.GONE);
            acimage.setVisibility(View.GONE);
            actitle.setVisibility(View.GONE);
            acsubtext.setVisibility(View.GONE);

            frameLayout.setVisibility(View.VISIBLE);

            checklogcount();
           /* Bundle bundle = new Bundle();
            bundle.putString("selected_navigation", "Recents");
            RecentsFragment recentsFragment = new RecentsFragment();
            recentsFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    recentsFragment).commit();*/
            updatelognumber();


            //navigationView.setCheckedItem(R.id.nav_recents);
        }

    }

    private void firstrun() {

        SharedPreferences settings = getSharedPreferences("FIRSTRUNINFO111", MODE_PRIVATE);
        if (settings.getBoolean("isFirstRunDialogBoxtex111", true)) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("isFirstRunDialogBoxtex111", false);
            editor.commit();
            sendnoifications();
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(NewMainActivity.this);

            builder.setIcon(R.drawable.notificationlogo);

            builder.setTitle("BETA Version");
            builder.setMessage("This Version Of App Is In Beta Testing, So You Might Get Bugs or App Might Not Work Well At Someplace.\nWe Request You To Report Us Any Bugs By Clicking On Help Button On Menu \n\nThank You For Using The Beta App, You Will Soon Get New Updated Version");

            builder.setCancelable(false);

            builder.setPositiveButton("Got It", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                    dialogInterface.dismiss();
                    Intent startIntent = new Intent(NewMainActivity.this, NewMainActivity.class);
                    startActivity(startIntent);
                }
            });
            builder.setNegativeButton("Report Issue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    helpdialog();
                }
            });


            android.app.AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void sendnoifications() {

        int requestID = (int) System.currentTimeMillis();

        PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(),
                0, new Intent(), PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder buildernotif = new NotificationCompat.Builder(getBaseContext());

        Intent pauseIntent = new Intent(this, MainActivity.class);
        pauseIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pauseIntent.putExtra("pause", true);
        PendingIntent pausePendingIntent = PendingIntent.getActivity(this, requestID, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        buildernotif.setAutoCancel(true);

        Intent cancelIntent = new Intent(this, MainActivity.class);
        buildernotif.setAutoCancel(true);

        buildernotif.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.notificationlogo)
                .setContentTitle("You Have Latest Version Of The App ")
                .setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))

                /*.addAction(R.drawable.ic_action_boom, "Action!", someOtherPendingIntent)*/
                .setContentText("Now Manage And Search Notification Easily!")
                .setContentIntent(pausePendingIntent);

//Then add the action to your notification

        NotificationManager manager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, buildernotif.build());


    }

    private void updatelognumber() {


        try {


            String logstring = "Notification Logged : ";
            BrowseAdapter adapter = new BrowseAdapter(this);
            String count = String.valueOf(adapter.getItemCount());
            //Toast.makeText(this, ""+count, Toast.LENGTH_SHORT).show();
            txtlogcount.setText("Notification Logged : " + count);
            Typeface face = Typeface.createFromAsset(getAssets(),
                    "fonts/montserratregulargoogle.ttf");
            txtlogcount.setTypeface(face);

            if (txtlogcount.getText().equals("0")) {
                buttonAllow.setVisibility(View.VISIBLE);
                buttonAllow.setText("No Notification Has Been Logged Yet");
            }
            if (txtlogcount.getText().equals("Notification Logged : 20")) {
                txtlogcount.setText("Notification Log");

            }
            int cc = Integer.parseInt(count);
            if (cc >= 15) {
                SharedCommon scor = new SharedCommon();

                int ortop = SharedCommon.getPreferencesInt(getApplicationContext(), keyoveruse, 0);


                ortop++;
                scor.putPreferencesInt(getApplicationContext(), SharedCommon.keyoveruse, ortop);

                int or = SharedCommon.getPreferencesInt(getApplicationContext(), keyoveruse, 0);

                if (or == 0) {
                    txtlogcount.setText("Notification Log");
                    int n = 15;
                    String nn = ("" + n);
                    overnotificationmessage(n, nn);
                }
                if (or == 5) {
                    txtlogcount.setText("Notification Log");
                    int n = 15;
                    String nn = ("" + n);
                    overnotificationmessage(n, nn);
                }
            }


            SQLiteDatabase db = dbHelper.getReadableDatabase();
            long numRowsPosted = DatabaseUtils.queryNumEntries(db, DatabaseHelper.PostedEntry.TABLE_NAME);
            lognumber = String.valueOf(numRowsPosted);
            txtlogcount.setText("Notification Logged : " + lognumber);
            //txtlogcount.setText(logstring+count);
            lognumber = String.valueOf(numRowsPosted);
            txtlogcount.setText("Notification Logged : " + lognumber);
            // Toast.makeText(this, ""+numRowsPosted+lognumber, Toast.LENGTH_SHORT).show();

            String nnn = String.valueOf(numRowsPosted);
            SharedPreferences pref = NewMainActivity.this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor edt = pref.edit();
            edt.putString("facebook_id", nnn);
            edt.commit();


          /*  if (numRowsPosted>=15000){
                int n = (int) numRowsPosted;
                String nn = (""+n);
                overnotificationmessage(n,nn);

            }*/
           /* long numRowsFavorite = DatabaseUtils.queryNumEntries(db, DatabaseHelper.PostedEntry.TABLE_NAME,
                    DatabaseHelper.PostedEntry.COLUMN_NAME_FAVORITE+"=?", new String[]{String.valueOf(1)});
            int stringResource1 = numRowsFavorite == 1 ? R.string.settings_browse_summary_singular : R.string.settings_browse_summary_plural;
            prefFavorites.setSummary(getString(stringResource1, numRowsFavorite));*/


        } catch (Exception e) {
            if (Const.DEBUG) e.printStackTrace();
        }
    }

    private void overnotificationmessage(int n, String nn) {


        firebaseRemoteConfigprice.activateFetched();

        whatnew = (firebaseRemoteConfigprice.getString("showads"));


        if (whatnew.equals("yes")) {
      //      showads();
        //    showintads();
            //Toast.makeText(MainActivity.this, "Showing", Toast.LENGTH_SHORT).show();
        } else if (whatnew.equals("no")) {
            mAdView.setVisibility(View.GONE);

            //  Toast.makeText(MainActivity.this, "Nope", Toast.LENGTH_SHORT).show();
        }


        // Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(NewMainActivity.this);

        builder.setIcon(R.drawable.notificationlogo);

        builder.setTitle("Note ");
        builder.setMessage("You Have More Than 15,000 Notifications Logged, We Request You To Clear Notifications So That App Can Run Smoothly ");

        builder.setCancelable(true);

        builder.setNeutralButton("DISMISS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("CLEAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                try {
                    DatabaseHelper dbHelper = new DatabaseHelper(NewMainActivity.this);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.execSQL(DatabaseHelper.SQL_DELETE_ENTRIES_POSTED);
                    db.execSQL(DatabaseHelper.SQL_CREATE_ENTRIES_POSTED);
                    db.execSQL(DatabaseHelper.SQL_DELETE_ENTRIES_REMOVED);
                    db.execSQL(DatabaseHelper.SQL_CREATE_ENTRIES_REMOVED);
                    Intent local = new Intent();
                    local.setAction(NotificationHandler.BROADCAST);
                    LocalBroadcastManager.getInstance(NewMainActivity.this).sendBroadcast(local);

                    recreate();
                } catch (Exception e) {
                    if (Const.DEBUG) e.printStackTrace();
                }


                Toast.makeText(NewMainActivity.this, "Cleared !", Toast.LENGTH_LONG).show();

                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openDialog() {

        setTitle("");

        frameLayout.setVisibility(View.GONE);
        buttonAllow.setVisibility(View.VISIBLE);


        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Alert!")
                .setMessage("Please allow permission to use app.")
                .setCancelable(true)
                .setPositiveButton("OK", null);

        if (dialog != null) {
            dialog.dismiss();
        }

        dialog = builder.show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_delete:
                if (Util.isNotificationAccessEnabled(getApplicationContext())) {
                    confirm();
                } else {
                    openDialog();
                }
                return true;
            case R.id.menu_export:
                if (Util.isNotificationAccessEnabled(getApplicationContext())) {
                    export();
                } else {
                    openDialog();
                }
                return true;
           /* case R.id.menu_logcount:
                if (Util.isNotificationAccessEnabled(getApplicationContext())){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new SettingsFragment()).commit();
                }else {
                    openDialog();
                }
                return true;*/

            case R.id.menu_refresh:
                Intent startIntentr = new Intent(NewMainActivity.this, NewMainActivity.class);
                startActivity(startIntentr);
                return true;
            case R.id.report:
                helpdialog();
                return true;

            case R.id.menu_sug:
                openhelp();
                return true;
            case R.id.menu_info:
                Intent startIntentrr = new Intent(NewMainActivity.this, InfoActivity.class);
                startActivity(startIntentrr);
                return true;
            case R.id.backup:
                return true;
            case R.id.restore:
                return true;



           /* case R.id.menu_settings:
                opensetting();
                return true;*/

            case R.id.faqs:
                SharedCommon scor = new SharedCommon();

                int orfavc = SharedCommon.getPreferencesInt(getApplicationContext(), keyfaqs, 0);


                orfavc++;
                scor.putPreferencesInt(getApplicationContext(), SharedCommon.keyfaqs, orfavc);
                Intent startIntent = new Intent(NewMainActivity.this, FAQActivity.class);
                startActivity(startIntent);
                /*String url = "https://xenonstudio.in/notificationlog#24f3eaf5-2efe-4e30-9c58-975c032e08e0";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);*/

                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void opensetting() {

        SharedCommon scor = new SharedCommon();

        int orfavc = SharedCommon.getPreferencesInt(getApplicationContext(), keyopensetting, 0);


        orfavc++;
        scor.putPreferencesInt(getApplicationContext(), SharedCommon.keyopensetting, orfavc);
        Intent startIntent = new Intent(NewMainActivity.this, SettingsActivity.class);
        startActivity(startIntent);

    }

    private void openhelp() {
       /* firebaseRemoteConfigprice.activateFetched();

        whatnew=(firebaseRemoteConfigprice.getString("showads"));


        if (whatnew.equals("yes")){
            showads();
            showintads();
            //Toast.makeText(NewMainActivity.this, "Showing", Toast.LENGTH_SHORT).show();
        }
        else if (whatnew.equals("no")){
            mAdView.setVisibility(View.GONE);

            //  Toast.makeText(NewMainActivity.this, "Nope", Toast.LENGTH_SHORT).show();
        }*/

        final EditText taskEditText = new EditText(this);
        android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(NewMainActivity.this, R.style.AlertDialogedit)
                .setTitle("Your Suggestion")
                .setMessage("Provide Details And Send Us Mail ")
                .setView(taskEditText)
                .setCancelable(false)
                .setPositiveButton("Send Mail", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(taskEditText.getText());
                        sendmailintent(task);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();


    }

//    private void showintads() {
//        if (mInterstitialAd.isAdLoaded()) {
//            mInterstitialAd.show();
//        } else {
//            Log.d("TAG", "The interstitial wasn't loaded yet.");
//        }
//    }

//    private void showads() {
//        //upid--ca-app-pub-6778147776084460/7588656349
//        //myid-ca-app-pub-8081344892743036/1017320664
//        mAdView.setAdListener(new AdListener() {
//            @Override
//            public void onError(Ad ad, AdError adError) {
//
//            }
//
//            @Override
//            public void onAdLoaded(Ad ad) {
//
//            }
//
//            @Override
//            public void onAdClicked(Ad ad) {
//                SharedCommon sc = new SharedCommon();
//
//                int i = SharedCommon.getPreferencesInt(getApplicationContext(), key1, 0);
//
//
//                i++;
//                sc.putPreferencesInt(getApplicationContext(), SharedCommon.key1, i);
//            }
//
//            @Override
//            public void onLoggingImpression(Ad ad) {
//
//            }
//        });
//
//    }

    private void helpdialog() {

        LayoutInflater li = LayoutInflater.from(NewMainActivity.this);
        View promptsView = li.inflate(R.layout.helplayout, null);

        final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(
                NewMainActivity.this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);


        final EditText edtname, edtemail, edtphone, edtpincode, edtid;
        edtname = (EditText) promptsView.findViewById(R.id.edtfullname);
        edtemail = (EditText) promptsView.findViewById(R.id.edtemail);
        edtphone = (EditText) promptsView.findViewById(R.id.edtphonenumber);
        edtpincode = (EditText) promptsView.findViewById(R.id.edtpincode);
        edtid = (EditText) promptsView.findViewById(R.id.edtpaypalorpaytm);


        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        final String countryCodeValue = tm.getNetworkCountryIso();

        final Spinner sp = (Spinner) promptsView
                .findViewById(R.id.spinnerpaypalpaytm);


        final Button userInput = (Button) promptsView
                .findViewById(R.id.btndiasub);

        userInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = edtname.getText().toString();
                String email = edtemail.getText().toString();
                String phone = edtphone.getText().toString();
                String pincode = edtpincode.getText().toString();
                String id = edtid.getText().toString();


                String Method = ("" + sp.getSelectedItem());
                if (name.equals("") || pincode.equals("") || id.equals("")) {

                    Toast.makeText(NewMainActivity.this, "Please Enter All The Details", Toast.LENGTH_SHORT).show();
                } else {

                    Toast toast = Toast.makeText(NewMainActivity.this, "SEND MAIL VIA GMAIL/YAHOO ", Toast.LENGTH_LONG);
                    View view1 = toast.getView();

                    view1.getBackground().setColorFilter((Color.parseColor("#FF104162")), PorterDuff.Mode.SRC_IN);


                    TextView text = view1.findViewById(android.R.id.message);
                    text.setTextColor(Color.WHITE);

                    toast.show();

                    Intent send = new Intent(Intent.ACTION_SENDTO);
                    String uriText = "mailto:" + Uri.encode("notificationapp.xenonstudio@gmail.com") +
                            "?subject=" + Uri.encode(Method + " - Notification Log App") +
                            "&body=" + Uri.encode("" + "Name: " + name + "\n" + "Country: " + pincode + "\n" + "Query Type: " + Method + "\n" + "Query: " + id + " \n\n\n ------------ \n\n Version Code : " + versionCode + "\n Build : " + Build.BRAND + "\n" + Build.MODEL + "\n" + Build.DEVICE);
                    Uri uri = Uri.parse(uriText);

                    send.setData(uri);
                    startActivity(Intent.createChooser(send, "Send Mail Via : "));

                    splashTread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                int waited = 0;
                                // Splash screen pause time
                                while (waited < 10600) {
                                    sleep(100);
                                    waited += 100;
                                }
                                sendFCMPush();
                                NewMainActivity.this.finish();
                            } catch (InterruptedException e) {
                                // do nothing
                            } finally {
                                NewMainActivity.this.finish();
                            }

                        }
                    };
                    splashTread.start();

                }
            }
        });
        alertDialogBuilder
                .setCancelable(false)
                .setNegativeButton("Go Back",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.dismiss();
                            }
                        });

        // create alert dialog
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    private void sendFCMPush() {


        int requestID = (int) System.currentTimeMillis();

        PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(),
                0, new Intent(), PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());

        Intent pauseIntent = new Intent(this, NewMainActivity.class);
        pauseIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pauseIntent.putExtra("pause", true);
        PendingIntent pausePendingIntent = PendingIntent.getActivity(this, requestID, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setAutoCancel(true);

        Intent cancelIntent = new Intent(this, NewMainActivity.class);
        builder.setAutoCancel(true);

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.notificationlogo)
                .setSound(Uri.parse("uri://notification_xperia.mp3"))
                .setContentTitle("Query Received ")
                .setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))
                /*.addAction(R.drawable.ic_action_boom, "Action!", someOtherPendingIntent)*/
                .setContentText("Contact Us If Don't Get Mail Within 7 Days")
                .setContentIntent(pausePendingIntent);


//Then add the action to your notification


        NotificationManager manager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());


    }

    private void sendmailintent(String task) {
        Toast toast = Toast.makeText(this, "SEND MAIL VIA GMAIL/YAHOO ", Toast.LENGTH_LONG);
        View view = toast.getView();

        view.getBackground().setColorFilter((Color.parseColor("#FF104162")), PorterDuff.Mode.SRC_IN);


        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(Color.WHITE);

        toast.show();

        Intent send = new Intent(Intent.ACTION_SENDTO);
        String uriText = "mailto:" + Uri.encode("notificationapp.xenonstudio@gmail.com") +
                "?subject=" + Uri.encode("Notification Log App") +
                "&body=" + Uri.encode("" + task + " \n\n\n ------------ \n\n Version Code : " + versionCode + "\n Build : " + Build.BRAND + "\n" + Build.MODEL + "\n" + Build.DEVICE);
        Uri uri = Uri.parse(uriText);

        send.setData(uri);
        startActivity(Intent.createChooser(send, "Send Mail Via : "));
    }

    private void shareapplink() {

        Toast.makeText(NewMainActivity.this, "Sharing App..", Toast.LENGTH_SHORT).show();


        ShareCompat.IntentBuilder.from(NewMainActivity.this)
                .setType("text/plain")
                .setChooserTitle("Share URL")
                .setText("Hey, Download This Awesome Notification History Log App - " + "https://play.google.com/store/apps/details?id=com.appnotification.notificationhistorylog")
                .startChooser();
    }

    private void confirm() {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setTitle(R.string.dialog_delete_header);
        builder.setMessage(R.string.dialog_delete_text);
        builder.setNegativeButton(R.string.dialog_delete_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setPositiveButton(R.string.dialog_delete_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                truncate();
                sendnotification();
            }
        });
        builder.show();
        /* AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setTitle(R.string.dialog_delete_header);
        builder.setMessage(R.string.dialog_delete_text);
        builder.setNegativeButton(R.string.dialog_delete_no, (dialogInterface, i) -> {});
        builder.setPositiveButton(R.string.dialog_delete_yes, (dialogInterface, i) -> truncate());
        builder.show();*/
    }

    private void sendnotification() {

        int requestID = (int) System.currentTimeMillis();

        PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(),
                0, new Intent(), PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder buildernotif = new NotificationCompat.Builder(getBaseContext());

        Intent pauseIntent = new Intent(this, NewMainActivity.class);
        pauseIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pauseIntent.putExtra("pause", true);
        PendingIntent pausePendingIntent = PendingIntent.getActivity(this, requestID, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        buildernotif.setAutoCancel(true);

        Intent cancelIntent = new Intent(this, NewMainActivity.class);
        buildernotif.setAutoCancel(true);

        buildernotif.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.notificationlogo)
                .setContentTitle("Deleted Successfully !")
                .setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))

                /*.addAction(R.drawable.ic_action_boom, "Action!", someOtherPendingIntent)*/
                .setContentText("Logs Has Been Deleted")
                .setContentIntent(pausePendingIntent);

//Then add the action to your notification

        NotificationManager manager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, buildernotif.build());

    }

    private void initCountDrawer() {

        txtlogcount.setGravity(Gravity.CENTER_VERTICAL);
        txtlogcount.setTypeface(null, Typeface.BOLD);
        txtlogcount.setTextColor(getResources().getColor(R.color.white));
        txtlogcount.setText("99+");


    }

    private void truncate() {
        try {
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(DatabaseHelper.SQL_DELETE_ENTRIES_POSTED);
            db.execSQL(DatabaseHelper.SQL_CREATE_ENTRIES_POSTED);
            db.execSQL(DatabaseHelper.SQL_DELETE_ENTRIES_REMOVED);
            db.execSQL(DatabaseHelper.SQL_CREATE_ENTRIES_REMOVED);
            Intent local = new Intent();
            local.setAction(NotificationHandler.BROADCAST);
            LocalBroadcastManager.getInstance(this).sendBroadcast(local);

            startActivity(new Intent(NewMainActivity.this, NewMainActivity.class));
            finish();
        } catch (Exception e) {
            if (Const.DEBUG) e.printStackTrace();
        }
    }

    private void export() {
        if (!ExportTask.exporting) {
            ExportTask exportTask = new ExportTask(this, findViewById(android.R.id.content));
            exportTask.execute();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

           /* case R.id.nav_recents:
                if (Util.isNotificationAccessEnabled(getApplicationContext())){
                    Bundle bundle = new Bundle();
                    bundle.putString("selected_navigation", "Recents");
                    RecentsFragment recentsFragment = new RecentsFragment();
                    recentsFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            recentsFragment).commit();
                }else {
                    openDialog();
                }
                break;
*/
            case R.id.nav_all_notifications:

                Intent startIntent = new Intent(NewMainActivity.this, BrowseActivity.class);
                startActivity(startIntent);

                break;
            case R.id.nav_notifications_by_apps:

                browsenotificationappwise();

                break;
            case R.id.nav_refer:

                firebaseRemoteConfigprice.activateFetched();

                whatnew = (firebaseRemoteConfigprice.getString("showads"));


                if (whatnew.equals("yes")) {
//                    showads();
//                    showintads();
                    //Toast.makeText(MainActivity.this, "Showing", Toast.LENGTH_SHORT).show();
                } else if (whatnew.equals("no")) {
                    mAdView.setVisibility(View.GONE);

                    //  Toast.makeText(MainActivity.this, "Nope", Toast.LENGTH_SHORT).show();
                }


                //startActivity(new Intent(NewMainActivity.this, ViewGrouped.class));

                referandearndailog();

                break;
            case R.id.nav_stats:
                Intent startIntentt1 = new Intent(NewMainActivity.this, ViewGrouped.class);
                startActivity(startIntentt1);
                return true;

            case R.id.nav_help:
                helpdialog();
                return true;
            case R.id.nav_how_to_use:
                Intent startIntentt = new Intent(NewMainActivity.this, TutorialActivity.class);
                startActivity(startIntentt);
                return true;
            case R.id.nav_support:
                String url = "https://www.buymeacoffee.com/NotificationApp";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

                return true;

            case R.id.nav_favourites:
                if (Util.isNotificationAccessEnabled(getApplicationContext())) {
                    Intent startIntent1 = new Intent(NewMainActivity.this, FavActivity.class);
                    startActivity(startIntent1);
                } else {
                    openDialog();
                }
                break;

            case R.id.nav_export_logs:
                if (Util.isNotificationAccessEnabled(getApplicationContext())) {
                    shareapplink();
                } else {
                    openDialog();
                }
                break;

            case R.id.nav_settings:

                Intent startIntent2 = new Intent(NewMainActivity.this, SettingsActivity.class);
                startActivity(startIntent2);
                /* getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new SettingsFragment()).commit();*/

                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void referandearndailog() {

        Intent startIntent = new Intent(NewMainActivity.this, TestActivity.class);
        startActivity(startIntent);

       /* android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(NewMainActivity.this);

        builder.setIcon(R.drawable.notificationlogo);

        builder.setTitle("Coming Soon!");

        builder.setMessage("Get Amazing Google Play Coupons, Discount Code, Remove Ads and Lot More By Just Referring App To Your Friends");
        builder.setCancelable(false);

        builder.setPositiveButton("Got It", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Intent startIntent = new Intent(NewMainActivity.this, NewMainActivity.class);
                startActivity(startIntent);

            }
        });
        builder.setNegativeButton("Notify Me", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(NewMainActivity.this, "We Will Notify You When Refer & Earn Is Available ", Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });



        android.app.AlertDialog dialog = builder.create();
        dialog.show();*/
    }

    private void browsenotificationappwise() {

        final String[] listitems = {"WhatsApp", "Gmail", "Facebook", "Instagram", "Calender", "Calls"};
        android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(NewMainActivity.this, R.style.AlertDialogedit);
        mBuilder.setTitle("Browse Notifications Of  ");
        mBuilder.setSingleChoiceItems(listitems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


               /* String name = "show";
                SharedCommon.putSharedPreferencesString(NewMainActivity.this,SharedCommon.keysettingdailog,name);*/


                if (i == 0) {

                    //  setlocale("en");
                    Intent startIntent = new Intent(NewMainActivity.this, WhatsappActivity.class);
                    startActivity(startIntent);
                } else if (i == 1) {

                    // setlocale("hi");
                    Toast.makeText(NewMainActivity.this, "Gmail Notifications", Toast.LENGTH_SHORT).show();
                    /*recreate();*/
                    Intent startIntent = new Intent(NewMainActivity.this, GmailActivity.class);
                    startActivity(startIntent);
                } else if (i == 2) {

                    // setlocale("tr");
                    // Toast.makeText(NewMainActivity.this, "tr", Toast.LENGTH_SHORT).show();

                    Intent startIntent = new Intent(NewMainActivity.this, FacebookActivity.class);
                    startActivity(startIntent);
                } else if (i == 3) {

                    // setlocale("de");
                    // Toast.makeText(NewMainActivity.this, "de", Toast.LENGTH_SHORT).show();

                    Intent startIntent = new Intent(NewMainActivity.this, InstaActivity.class);
                    startActivity(startIntent);
                } else if (i == 4) {

                    //  Toast.makeText(NewMainActivity.this, "it", Toast.LENGTH_SHORT).show();

                    Intent startIntent = new Intent(NewMainActivity.this, CalenderActivity.class);
                    startActivity(startIntent);
                } else if (i == 5) {


                    //Toast.makeText(NewMainActivity.this, "gu", Toast.LENGTH_SHORT).show();

                    Intent startIntent = new Intent(NewMainActivity.this, CallsActivity.class);
                    startActivity(startIntent);
                }

                dialogInterface.dismiss();
            }
        });

        android.app.AlertDialog mDialog = mBuilder.create();

        mDialog.show();

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void openactionmenu() {
        //NewMainActivity.this.openOptionsMenu();


    }

    public void showPopup(View v) {
       /* PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.main, popup.getMenu());
        popup.show();*/
        PopupMenu popup = new PopupMenu(NewMainActivity.this, v);
        popup.setOnMenuItemClickListener(NewMainActivity.this);
        popup.inflate(R.menu.main);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                if (Util.isNotificationAccessEnabled(getApplicationContext())) {
                    confirm();
                } else {
                    openDialog();
                }
                return true;
            case R.id.menu_export:
                if (Util.isNotificationAccessEnabled(getApplicationContext())) {
                    export();
                } else {
                    openDialog();
                }
                return true;
           /* case R.id.menu_logcount:
                if (Util.isNotificationAccessEnabled(getApplicationContext())){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new SettingsFragment()).commit();
                }else {
                    openDialog();
                }
                return true;*/

            case R.id.menu_refresh:
                Intent startIntentr = new Intent(NewMainActivity.this, NewMainActivity.class);
                startActivity(startIntentr);
                return true;
            case R.id.report:
                helpdialog();
                return true;

            case R.id.menu_sug:
                openhelp();
                return true;
            case R.id.menu_info:
                Intent startIntentrr = new Intent(NewMainActivity.this, InfoActivity.class);
                startActivity(startIntentrr);
                return true;

            case R.id.backup:
//                Toast.makeText(NewMainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= 23 && !(checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED && checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                    } else {
                        backUp();
                    }
                } else {
                    backUp();
                }
                return true;
            case R.id.restore:
//                Toast.makeText(NewMainActivity.this, "Hello Res", Toast.LENGTH_SHORT).show();

                if (Build.VERSION.SDK_INT >= 23 && !(checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED && checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                    } else {
                        restore();
                    }
                } else {
                    restore();
                }
                return true;


           /* case R.id.menu_settings:
                opensetting();
                return true;
*/
            case R.id.faqs:
                SharedCommon scor = new SharedCommon();

                int orfavc = SharedCommon.getPreferencesInt(getApplicationContext(), keyfaqs, 0);


                orfavc++;
                scor.putPreferencesInt(getApplicationContext(), SharedCommon.keyfaqs, orfavc);
                Intent startIntent = new Intent(NewMainActivity.this, FAQActivity.class);
                startActivity(startIntent);
                /*String url = "https://xenonstudio.in/notificationlog#24f3eaf5-2efe-4e30-9c58-975c032e08e0";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);*/

                return true;

            default:
                return false;
        }

        // return false;
    }

    public void backUp() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + getApplicationContext().getPackageName() + "//databases//notifications.db";

                String backupDBPath = "/NotificationBackup/notifications.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);
                File destination = new File(sd,"/NotificationBackup");
                if (!destination.exists()) {
                    destination.mkdir();
                }
                try {
                    backupDB = new File(sd, backupDBPath);
                } catch (Exception e) {
                    Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }


                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {
            Log.e("tag", " = = =  = = =  = " + e.getMessage());
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    public void restore() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + getApplicationContext().getPackageName() + "//databases//notifications.db";
                String backupDBPath = "/NotificationBackup/notifications.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);
                String path = "//data//" + getApplicationContext().getPackageName() + "//databases";
                File file = new File(data,path);
                if (!file.exists()){
                    file.mkdir();
                }
                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(backupDB).getChannel();
                    FileChannel dst = new FileOutputStream(currentDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    Toast.makeText(getApplicationContext(), "Database Restored successfully Please Restart App", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
        }
    }

}
