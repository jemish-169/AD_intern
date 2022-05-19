package com.appnotification.notificationhistorylog;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.UiModeManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.appnotification.notificationhistorylog.CommonCl.SharedCommon;
import com.appnotification.notificationhistorylog.misc.Util;
import com.appnotification.notificationhistorylog.ui.BrowseActivity;
import com.appnotification.notificationhistorylog.ui.MainActivity;
import com.appnotification.notificationhistorylog.ui.NewMainActivity;
import com.appnotification.notificationhistorylog.ui.SelectTheme;
import com.appnotification.notificationhistorylog.ui.SettingsFragment;
import com.appnotification.notificationhistorylog.ui.WhatsappActivity;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static com.appnotification.notificationhistorylog.CommonCl.SharedCommon.key1;
import static com.appnotification.notificationhistorylog.CommonCl.SharedCommon.keyfaqs;
import static com.appnotification.notificationhistorylog.CommonCl.SharedCommon.keytokenid;

public class SettingsActivity extends AppCompatActivity {

    CardView cdnoti, cdabout, acprivacy, cdterms, cdper, cdlog, cddarkmode, cd_notice, cddata;
    CheckBox cbpromo;

    TextView txtversion;

    private InterstitialAd mInterstitialAd;


    String username;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    TextView txtnotification;
    private ViewPager mViewPager;
    private DatabaseReference mUserRef, museref, mdatareport, mcredtref, mlinkupdate;

    private ProgressBar progressBar;


    ProgressDialog mprogreeinternet;

    Thread splashTread;

    Spinner splan;

    Switch aSwitch;

    String PrivacyUrl = "https://xenonstudio.in/privacynotificationapp";

    String TermsUrl = "http://imagerecognitionapp.blogspot.com/2018/10/terms-and-conditions.html";

    String apppackagename = "com.thetechroot.vision";

    int versionCode = BuildConfig.VERSION_CODE;
    String versionName = BuildConfig.VERSION_NAME;
    private static final String NIGHT_MODE = "night_mode";

    private SharedPreferences mSharedPref;


    String appid = BuildConfig.APPLICATION_ID;
    FirebaseRemoteConfig firebaseRemoteConfigprice;

    private AdView mAdView;
    public String whatnew;
    public String showdataus;
    public String noticedata;

    public String versionfirebase;
    Switch datacb = null;


    String datedata;

    @Override
    protected void onStop() {
        /*FirebaseUser currentUser = mAuth.getCurrentUser();
        //Toast.makeText(this, ""+currentUser, Toast.LENGTH_SHORT).show();

        if(currentUser == null){

            sendToStart();

        } else {


            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.getDefault());

            String datte = format.format(new Date());
            String idtimedate = datte.substring(5, 10);
            String idtime = datte.substring(11, 19);
            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
            String datetime = ft.format(dNow);
            String iddate = datetime.substring(0, 3);
            //String uidid = uid.substring(0,5);

            // mdatareport.child("D-Unique ID").setValue(iddate+idtimedate+idtime);


            String lan = Locale.getDefault().getDisplayLanguage();
            String country = SettingsActivity.this.getResources().getConfiguration().locale.getCountry();
            String buildID = ("" + Build.ID);
            TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            String countryCodeValue = tm.getNetworkCountryIso();

            TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            String carrierName = manager.getNetworkOperatorName();
            String devicetime = ("" + Build.USER);
            String buildd = ("" + Build.HOST);
            String devicedet = ("" + Build.BRAND + " " + Build.MODEL + " " + Build.DEVICE + " " + Build.TYPE + "" + Build.HOST);
            String tokenID = SharedCommon.getSharedPreferencesString(SettingsActivity.this, keytokenid,"");


            mdatareport.child("D-Date Time").setValue(datte);
            mdatareport.child("D-Language").setValue(lan);

            mdatareport.child("D-Status").setValue("Logged In");
            mdatareport.child("D-FirebaseToken").setValue(tokenID);


            //  mdatareport.child("U-FirebaseToken").setValue(tokenID);

            mdatareport.child("U-BuildID").setValue(buildID);

            mdatareport.child("U-Carrier").setValue(carrierName);
            mdatareport.child("U-Country").setValue(countryCodeValue);
            mdatareport.child("U-Version").setValue(versionName);
            mdatareport.child("U-BuildUser").setValue(devicetime);
            mdatareport.child("U-Device").setValue(devicedet);*/

        super.onStop();
    }

    @Override
    protected void onResume() {
        // updatedata();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //Toast.makeText(this, ""+currentUser, Toast.LENGTH_SHORT).show();

        if (currentUser == null) {

            sendToStart();

        } else {

            username = currentUser.getUid();
            //SAVEDATAREPORT
            savereport(currentUser);
            int i = SharedCommon.getPreferencesInt(getApplicationContext(), key1, 0);

            museref.child("D-Ads Clicked").setValue(i);
        }
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowCustomEnabled(true);
//        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1A1F2B")));
//
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View v = inflater.inflate(R.layout.titleview, null);
//
//
//        TextView actionbar_title = (TextView)v.findViewById(R.id.actionbar_title);
//        //actionbar_title.setTypeface(you can set your font here also.);
//
//        actionBar.setCustomView(v);
        getSupportActionBar().hide();


        mSharedPref = getPreferences(Context.MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();
        //datacb = (CheckBox)findViewById(R.id.datacb);
        final Switch checkBox = (Switch) findViewById(R.id.datacb);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = preferences.edit();
        if (preferences.contains("checked") && preferences.getBoolean("checked", false) == true) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);

        }

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        datedata = formattedDate;

        mAuth = FirebaseAuth.getInstance();


        if (mAuth.getCurrentUser() != null) {


            mdatareport = FirebaseDatabase.getInstance().getReference().child("UsageReports").child(mAuth.getCurrentUser().getUid());
            museref = FirebaseDatabase.getInstance().getReference().child("mainacreport").child(mAuth.getCurrentUser().getUid());

            mUserRef = FirebaseDatabase.getInstance().getReference().child("openreport").child(mAuth.getCurrentUser().getUid());

        }


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (checkBox.isChecked()) {
                    editor.putBoolean("checked", true);
                    editor.apply();
                    signInAnonymously();
                } else {
                    editor.putBoolean("checked", false);
                    editor.apply();
                    logout();
                }
            }
        });
       /* boolean isChecked = getBooleanFromPreferences("isChecked");
        Log.i("start",""+isChecked);
        datacb.setChecked(isChecked);
        datacb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                Log.i("boolean",""+isChecked);
                SettingsActivity.this.putBooleanInPreferences(isChecked,"isChecked");
            }
        });*/


        AudienceNetworkAds.initialize(this);
        mInterstitialAd = new com.facebook.ads.InterstitialAd(this, getString(R.string.fb_interstitial_ad));
        mInterstitialAd.loadAd();
        mAdView = new com.facebook.ads.AdView(this, getString(R.string.fb_banner_ad), com.facebook.ads.AdSize.BANNER_HEIGHT_50);
        mAdView.loadAd();
        ((LinearLayout) findViewById(R.id.fb_container)).addView(mAdView);

        firebaseRemoteConfigprice = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder().build();
        firebaseRemoteConfigprice.setConfigSettings(configSettings);

        Map<String, Object> pricedata = new HashMap<>();
        pricedata.put("showads", "yn");
        pricedata.put("showdatausage", "yn");
        pricedata.put("noticefordata", "yn");


        firebaseRemoteConfigprice.setDefaults(pricedata);

        checkadsstatus();

        aSwitch = (Switch) findViewById(R.id.switchdark);

        if (isNightModeEnabled()) {
            setAppTheme(R.style.AppTheme_Base_Night);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            aSwitch.setChecked(true);
        } else {
            setAppTheme(R.style.AppTheme);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            aSwitch.setChecked(false);
        }


        txtversion = (TextView) findViewById(R.id.version);
        txtversion.setText("v" + versionName);

        cdterms = (CardView) findViewById(R.id.cd_terms);
        cdabout = (CardView) findViewById(R.id.cd_about);
        cdnoti = (CardView) findViewById(R.id.cd_noti);
        acprivacy = (CardView) findViewById(R.id.cd_privacy);

        //Permission Settings
        cdper = (CardView) findViewById(R.id.cd_per);
        cdper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        cddata = (CardView) findViewById(R.id.cd_data);
        cddata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdatadilog();
            }
        });
        cddata.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SettingsActivity.this, R.style.AlertDialog);


                builder.setMessage(username);

                builder.setCancelable(false)
                        .setNegativeButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }
        });
        cd_notice = (CardView) findViewById(R.id.cd_notice);
        cd_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseRemoteConfigprice.activateFetched();

                whatnew = (firebaseRemoteConfigprice.getString("showads"));


                if (whatnew.equals("yes")) {
                    showads();
                    showintads();
                    //Toast.makeText(MainActivity.this, "Showing", Toast.LENGTH_SHORT).show();
                } else if (whatnew.equals("no")) {
                    mAdView.setVisibility(View.GONE);

                    //  Toast.makeText(MainActivity.this, "Nope", Toast.LENGTH_SHORT).show();
                }
                Intent startIntent = new Intent(SettingsActivity.this, NoticeActivity.class);
                startActivity(startIntent);
            }
        });


        aSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            setIsNightModeEnabled(b);
            Toast.makeText(this, ""+b, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SettingsActivity.this, SettingsActivity.class));
            finish();
        });

        cddarkmode = (CardView) findViewById(R.id.cddarkmode);
        cddarkmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseRemoteConfigprice.activateFetched();

                whatnew = (firebaseRemoteConfigprice.getString("showads"));


                if (whatnew.equals("yes")) {
                    showads();
                    showintads();
                    //Toast.makeText(MainActivity.this, "Showing", Toast.LENGTH_SHORT).show();
                } else if (whatnew.equals("no")) {
                    mAdView.setVisibility(View.GONE);

                    //  Toast.makeText(MainActivity.this, "Nope", Toast.LENGTH_SHORT).show();
                }
                showlandailog();

            }
        });

        cdper.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);

                builder.setMessage("We Need Permission To Access Your Phones Notification");


                builder.setPositiveButton("GOT IT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                return false;
            }
        });

        cdnoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");

//for Android 5-7
                intent.putExtra("app_package", getPackageName());
                intent.putExtra("app_uid", getApplicationInfo().uid);

// for Android O
                intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());

                startActivity(intent);
            }
        });
        cdnoti.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));

                return false;
            }
        });


        cdabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent startIntent = new Intent(SettingsActivity.this, AboutusActivity.class);
                startActivity(startIntent);
                /*android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SettingsActivity.this, R.style.AlertDialog);

                builder.setMessage("At Xenon Studio, we believe everyone deserves to have a safe, secure and useful app.\n" +
                        "Innovation and simplicity makes us happy: our goal is to make AI reach every user, with the help of AI every user can feel his/her life easy.\n\n" +
                        "We Have partnered with Uptodown App Studio To make Amazing Notification App, where user can easily manage there phones notification\n\n" +
                        "App Developed By Xenon Studio,\n" +
                        "\n" +
                        "App Managed By UptoDown Apps Studio And Mob Appz\n\n" +
                        "For More - www.xenonstudio.in/notificationlog");

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

                AlertDialog dialog = builder.create();
                dialog.show();*/
            }
        });

        acprivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SettingsActivity.this, R.style.AlertDialog);

                builder.setIcon(R.drawable.notificationlogo);
                builder.setTitle("Privacy Policy ");
                builder.setMessage("Uptodown Apps built the Notification History Log app as a Free app. This SERVICE is provided by Uptodown Apps at no cost and is intended for use as is.\n" +
                        "This page is used to inform app users regarding our policies with the collection, use, and\n" +
                        "disclosure of any Information if anyone decided to use our Service.\n" +
                        "If you choose to use our Service, then you agree to the collection and use of information in relation\n" +
                        "to this policy. The Information that we collect is used for providing and improving the\n" +
                        "Service. We will not use or share your information with anyone except as described\n" +
                        "in this Privacy Policy.\n" +
                        "Permission Details\n" +
                        "Notification History Log uses the following permissions:\n" +
                        "Internet: To show Ads using Admob and log errors using Fabric\n" +
                        "Notification Access: To listen to all your incoming notifications and to store them in your local database.\n" +
                        "Storage: To export notifications into Excel or Text files.\n" +
                        "Note: All your notifications data will be stored in your local phone memory and we don’t have any access to your information. If you uninstall your app, all stored data will be deleted permanently.\n" +
                        "Information Collection and Use\n" +
                        "For a better experience, while using our app, we may require you to provide us with certain information. The information that we request is will be retained by us and used as described in this privacy policy.\n" +
                        "The app does use third-party services that may collect information used to identify you.\n" +
                        "Link to the privacy policy of third party service providers used by the app\n" +
                        "Google Play Services\n" +
                        "Fabric\n" +
                        "AdMob\n" +
                        "Log Data\n" +
                        "We want to inform you that whenever you use our Service, in the case of an error in the app we collect data and information (through Fabric) on your phone called Log Data. This Log Data may include information such as your device name, operating system version, the version of the app when utilizing our Service, the time and date of your use of the Service, and other statistics.\n" +
                        "Security\n" +
                        "We value your trust in providing us your Information, thus we are striving to use commercially acceptable means of protecting it. But remember that no method of transmission over the internet, or method of electronic storage is 100% secure and reliable, and we cannot guarantee its absolute security.\n" +
                        "Changes to This Privacy Policy\n" +
                        "We may update our Privacy Policy from time to time. Thus, you are advised to review\n" +
                        "this page periodically for any changes. We will notify you of any changes by posting\n" +
                        "the new Privacy Policy on this page. These changes are effective immediately after they are posted on\n" +
                        "this page.\n" +
                        "Contact Us\n" +
                        "If you have any questions or suggestions about our Privacy Policy, do not hesitate to contact us.\n \n\nFor More : barry@uptodownapp.com");

                AlertDialog dialog = builder.create();
                dialog.show();
                        /*WebView webView = new WebView(SettingsActivity.this);

                        webView.loadUrl(TermsUrl);*/
            }
        });


        cdterms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SettingsActivity.this, R.style.AlertDialog);

                builder.setIcon(R.drawable.notificationlogo);
                builder.setTitle("Terms and Conditions ");
                builder.setMessage("Notification History Log\n" +
                        "Terms and conditions\n" +
                        "These terms and conditions (\"Terms\", \"Agreement\") are an agreement between Mobile Application Developer (\"Mobile Application Developer\", \"us\", \"we\" or \"our\") and you (\"User\", \"you\" or \"your\"). This Agreement sets forth the general terms and conditions of your use of the Notification History Log mobile application and any of its products or services (collectively, \"Mobile Application\" or \"Services\").\n" +
                        "\n" +
                        "Backups\n" +
                        "We are not responsible for Content residing in the Mobile Application. In no event shall we be held liable for any loss of any Content. It is your sole responsibility to maintain appropriate backup of your Content. Notwithstanding the foregoing, on some occasions and in certain circumstances, with absolutely no obligation, we may be able to restore some or all of your data that has been deleted as of a certain date and time when we may have backed up data for our own purposes. We make no guarantee that the data you need will be available.\n" +
                        "\n" +
                        "Links to other mobile applications\n" +
                        "Although this Mobile Application may link to other mobile applications, we are not, directly or indirectly, implying any approval, association, sponsorship, endorsement, or affiliation with any linked mobile application, unless specifically stated herein. We are not responsible for examining or evaluating, and we do not warrant the offerings of, any businesses or individuals or the content of their mobile applications. We do not assume any responsibility or liability for the actions, products, services, and content of any other third-parties. You should carefully review the legal statements and other conditions of use of any mobile application which you access through a link from this Mobile Application. Your linking to any other off-site mobile applications is at your own risk.\n" +
                        "\n" +
                        "Prohibited uses\n" +
                        "In addition to other terms as set forth in the Agreement, you are prohibited from using the Mobile Application or its Content: (a) for any unlawful purpose; (b) to solicit others to perform or participate in any unlawful acts; (c) to violate any international, federal, provincial or state regulations, rules, laws, or local ordinances; (d) to infringe upon or violate our intellectual property rights or the intellectual property rights of others; (e) to harass, abuse, insult, harm, defame, slander, disparage, intimidate, or discriminate based on gender, sexual orientation, religion, ethnicity, race, age, national origin, or disability; (f) to submit false or misleading information; (g) to upload or transmit viruses or any other type of malicious code that will or may be used in any way that will affect the functionality or operation of the Service or of any related mobile application, other mobile applications, or the Internet; (h) to collect or track the personal information of others; (i) to spam, phish, pharm, pretext, spider, crawl, or scrape; (j) for any obscene or immoral purpose; or (k) to interfere with or circumvent the security features of the Service or any related mobile application, other mobile applications, or the Internet. We reserve the right to terminate your use of the Service or any related mobile application for violating any of the prohibited uses.\n" +
                        "\n" +
                        "Intellectual property rights\n" +
                        "This Agreement does not transfer to you any intellectual property owned by Mobile Application Developer or third-parties, and all rights, titles, and interests in and to such property will remain (as between the parties) solely with Mobile Application Developer. All trademarks, service marks, graphics and logos used in connection with our Mobile Application or Services, are trademarks or registered trademarks of Mobile Application Developer or Mobile Application Developer licensors. Other trademarks, service marks, graphics and logos used in connection with our Mobile Application or Services may be the trademarks of other third-parties. Your use of our Mobile Application and Services grants you no right or license to reproduce or otherwise use any Mobile Application Developer or third-party trademarks.\n" +
                        "\n" +
                        "Limitation of liability\n" +
                        "To the fullest extent permitted by applicable law, in no event will Mobile Application Developer, its affiliates, officers, directors, employees, agents, suppliers or licensors be liable to any person for (a): any indirect, incidental, special, punitive, cover or consequential damages (including, without limitation, damages for lost profits, revenue, sales, goodwill, use or content, impact on business, business interruption, loss of anticipated savings, loss of business opportunity) however caused, under any theory of liability, including, without limitation, contract, tort, warranty, breach of statutory duty, negligence or otherwise, even if Mobile Application Developer has been advised as to the possibility of such damages or could have foreseen such damages. To the maximum extent permitted by applicable law, the aggregate liability of Mobile Application Developer and its affiliates, officers, employees, agents, suppliers and licensors, relating to the services will be limited to an amount greater of one dollar or any amounts actually paid in cash by you to Mobile Application Developer for the prior one month period prior to the first event or occurrence giving rise to such liability. The limitations and exclusions also apply if this remedy does not fully compensate you for any losses or fails of its essential purpose.\n" +
                        "\n" +
                        "Indemnification\n" +
                        "You agree to indemnify and hold Mobile Application Developer and its affiliates, directors, officers, employees, and agents harmless from and against any liabilities, losses, damages or costs, including reasonable attorneys' fees, incurred in connection with or arising from any third-party allegations, claims, actions, disputes, or demands asserted against any of them as a result of or relating to your Content, your use of the Mobile Application or Services or any willful misconduct on your part.\n" +
                        "\n" +
                        "Severability\n" +
                        "All rights and restrictions contained in this Agreement may be exercised and shall be applicable and binding only to the extent that they do not violate any applicable laws and are intended to be limited to the extent necessary so that they will not render this Agreement illegal, invalid or unenforceable. If any provision or portion of any provision of this Agreement shall be held to be illegal, invalid or unenforceable by a court of competent jurisdiction, it is the intention of the parties that the remaining provisions or portions thereof shall constitute their agreement with respect to the subject matter hereof, and all such remaining provisions or portions thereof shall remain in full force and effect.\n" +
                        "\n" +
                        "Dispute resolution\n" +
                        "The formation, interpretation, and performance of this Agreement and any disputes arising out of it shall be governed by the substantive and procedural laws of Karnataka, India without regard to its rules on conflicts or choice of law and, to the extent applicable, the laws of India. The exclusive jurisdiction and venue for actions related to the subject matter hereof shall be the state and federal courts located in Karnataka, India, and you hereby submit to the personal jurisdiction of such courts. You hereby waive any right to a jury trial in any proceeding arising out of or related to this Agreement. The United Nations Convention on Contracts for the International Sale of Goods does not apply to this Agreement.\n" +
                        "\n" +
                        "Changes and amendments\n" +
                        "We reserve the right to modify this Agreement or its policies relating to the Mobile Application or Services at any time, effective upon posting of an updated version of this Agreement in the Mobile Application. When we do, we will post a notification in our Mobile Application. Continued use of the Mobile Application after any such changes shall constitute your consent to such changes. Policy was created with WebsitePolicies.\n" +
                        "\n" +
                        "Acceptance of these terms\n" +
                        "You acknowledge that you have read this Agreement and agree to all its terms and conditions. By using the Mobile Application or its Services you agree to be bound by this Agreement. If you do not agree to abide by the terms of this Agreement, you are not authorized to use or access the Mobile Application and its Services.\n" +
                        "\n" +
                        "Contacting us\n" +
                        "If you have any questions about this Agreement, please contact us.\n" +
                        "\n" +
                        "This document was last updated on June 27, 2019 \n\nFor More : barry@uptodownapp.com");

                AlertDialog dialog = builder.create();
                dialog.show();
                        /*WebView webView = new WebView(SettingsActivity.this);

                        webView.loadUrl(TermsUrl);*/
            }
        });


    }

    private void showdatadilog() {


        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SettingsActivity.this, R.style.AlertDialog);

        builder.setIcon(R.drawable.notificationlogo);
        builder.setTitle("Note: ");
        builder.setMessage("(Please Check The Box To Enable It)\n" +
                "If the setting is activated it will report the following to us:\n" +
                "\n" +
                "When a connection attempt is successful\n" +
                "When a connection attempt fails \n" +
                "If app crashes or find bugs\n" +
                "Which version of the app that is running\n" +
                "Error Reporting \n" +
                "Which language the app is been used \n" +
                "How long the app has been running And Similar Reports\n\n" +
                "*Your Notification Logs or Notification Detail or any sensitive information is not captured or saved");

        builder.setCancelable(false)
                .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final Switch checkBox = findViewById(R.id.datacb);
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
                        final SharedPreferences.Editor editor = preferences.edit();
                        checkBox.setChecked(preferences.contains("checked") && preferences.getBoolean("checked", false) == true);

                        editor.putBoolean("checked", true);
                        editor.apply();
                        signInAnonymously();
                    }
                })
                .setPositiveButton("Know More", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SettingsActivity.this, R.style.AlertDialog);

                        builder.setIcon(R.drawable.notificationlogo);
                        builder.setTitle("More Info ");
                        builder.setMessage("We log details that help app to improve, how our users are using the app and way to avoid crash in anonymous way \n" +
                                "We Will Never Collect data which are sensitive such as your notification, your notification details, your message or any sensitive information" +
                                "\n\n Your Sensitive Data is completely safe and not been collected/saved \n\n You Can Enable/Disable Whenever You Want \n\n Your Data Will Be Deleted If You Disable The Setting");

                        builder.setCancelable(false)
                                .setNegativeButton("Cancel", null);

                        AlertDialog dialog1 = builder.create();
                        dialog1.show();
                    }
                })
                .setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
                        /*WebView webView = new WebView(SettingsActivity.this);

                        webView.loadUrl(TermsUrl);*/
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


*/
                            whatnew = (firebaseRemoteConfigprice.getString("showads"));
                            showdataus = (firebaseRemoteConfigprice.getString("showdatausage"));
                            noticedata = (firebaseRemoteConfigprice.getString("noticefordata"));

                            if (showdataus.equals("yes")) {
                                cddata.setVisibility(View.VISIBLE);
                            } else if (showdataus.equals("notice")) {
                                Toast.makeText(SettingsActivity.this, "" + noticedata, Toast.LENGTH_SHORT).show();
                            } else if (showdataus.equals("no")) {
                                cddata.setVisibility(View.GONE);
                            }


                            if (whatnew.equals("yes")) {
                                showads();
                                showintads();
                                //Toast.makeText(SettingsActivity.this, "Showing", Toast.LENGTH_SHORT).show();
                            } else if (whatnew.equals("no")) {
                                mAdView.setVisibility(View.GONE);
                                // Toast.makeText(SettingsActivity.this, "Nope", Toast.LENGTH_SHORT).show();
                            }

                            Log.e("TaskError", "firebaseremote" + firebaseRemoteConfigprice.getString("btn_text"));


                                   /* Picasso.get().load(firebaseRemoteConfigprice.getString("image_link"))
                                            .into(img);*/
                        } else {


                            String exp = ("" + task.getException().getMessage());
                            if (exp.equals("null")) {

                                whatnew = ("Server Not Responding ");

                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SettingsActivity.this);


                            } else {
                                Log.e("TaskError", "taskexcep :" + task.getException().getMessage() + task.getException() + task);
                                Toast.makeText(SettingsActivity.this, "Internet Connection Error", Toast.LENGTH_SHORT).show();

                                //Toast.makeText(SettingsActivity.this, "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent startIntent = new Intent(SettingsActivity.this, NewMainActivity.class);
        startActivity(startIntent);
    }

    private void showads() {

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {

            }

            @Override
            public void onAdClicked(Ad ad) {
                SharedCommon sc = new SharedCommon();

                int i = SharedCommon.getPreferencesInt(getApplicationContext(), key1, 0);


                i++;
                sc.putPreferencesInt(getApplicationContext(), SharedCommon.key1, i);
            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });

    }

    private void showlandailog() {
        final String[] listitems = {"English", "हिंदी", "Deutschland", "Português", "My Language is Not Listed Here"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingsActivity.this);
        mBuilder.setTitle("Choose Language");
        mBuilder.setSingleChoiceItems(listitems, -1, (dialogInterface, i) -> {
            if (i == 0) {
                //English
                setLocale("en");
                Intent startIntent = new Intent(SettingsActivity.this, NewMainActivity.class);
                startActivity(startIntent);
            } else if (i == 1) {
                //Hindi
                setLocale("hi");

                Intent startIntent = new Intent(SettingsActivity.this, NewMainActivity.class);
                startActivity(startIntent);
            } else if (i == 2) {
                //Germany
                setLocale("de");
                Intent startIntent = new Intent(SettingsActivity.this, NewMainActivity.class);
                startActivity(startIntent);
            } else if (i == 3) {
                //Germany
                setLocale("pt");
                Intent startIntent = new Intent(SettingsActivity.this, NewMainActivity.class);
                startActivity(startIntent);
            } else if (i == 4) {
                //Germany
                //setLocale("pt");
                landailog();
            }
            dialogInterface.dismiss();
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    private void landailog() {
        final EditText taskEditText = new EditText(SettingsActivity.this);
        android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(SettingsActivity.this)
                .setTitle("Provide Us Your Language Preference ")
                .setMessage(" So We Can Add It In Our Future Updates ")
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

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

    }


/*    final EditText taskEditText = new EditText(SettingsActivity.this);
    android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(SettingsActivity.this)
            .setTitle("Provide Us Your Language Preference ")
            .setMessage(" So We Can Add It In Our Future Updates ")
            .setView(taskEditText)
            .setCancelable(false)
            .setPositiveButton("Send Mail", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String task = String.valueOf(taskEditText.getText());
                    sendmailintent(task);
                }
            })
            .setNegativeButton("Cancel",null)
            .create();
                    dialog.show();
}

                dialogInterface.dismiss();*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // Inflate switch
     /*   Switch mSwitchNightMode = (Switch) menu.findItem(R.id.action_night_mode)
                .getActionView().findViewById(R.id.item_switch);

        // Get state from preferences
        if (isNightModeEnabled()) {
            mSwitchNightMode.setChecked(true);
        } else {
            mSwitchNightMode.setChecked(false);
        }

        mSwitchNightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (isNightModeEnabled()) {
                    setIsNightModeEnabled(false);
                    setAppTheme(R.style.AppTheme);
                    Toast.makeText(SettingsActivity.this, "We Will Not Notify You About BETA Versions ", Toast.LENGTH_SHORT).show();
                } else {
                    setIsNightModeEnabled(true);
                    Toast.makeText(SettingsActivity.this, "We Will Notify You When BETA Version Is Out", Toast.LENGTH_LONG).show();

                    setAppTheme(R.style.AppTheme_Base_Night);
                }

                // Recreate activity
                recreate();
            }
        });
*/
      /*  if (Util.isNotificationAccessEnabled(getApplicationContext())){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new SettingsFragment()).commit();
        }else {
            openDialog();
        }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
           /* case R.id.menu_logcount:

                firebaseRemoteConfigprice.activateFetched();

                whatnew=(firebaseRemoteConfigprice.getString("showads"));


                if (whatnew.equals("yes")){
                    showads();
                    showintads();
                    //Toast.makeText(MainActivity.this, "Showing", Toast.LENGTH_SHORT).show();
                }
                else if (whatnew.equals("no")){
                    mAdView.setVisibility(View.GONE);

                    //  Toast.makeText(MainActivity.this, "Nope", Toast.LENGTH_SHORT).show();
                }
                graphdailog();
                return true;*/


        }
        return super.onOptionsItemSelected(item);
    }

    private void graphdailog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SettingsActivity.this);

        builder.setIcon(R.drawable.notificationlogo);

        builder.setTitle("Coming Soon!");

        builder.setMessage("Get Insights Of Notifications, View Data Of Notifications Into Graph And Manage Easily");
        builder.setCancelable(false);

        builder.setPositiveButton("Got It", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Intent startIntent = new Intent(SettingsActivity.this, NewMainActivity.class);
                startActivity(startIntent);

            }
        });
        builder.setNegativeButton("Notify Me", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(SettingsActivity.this, "We Will Notify You When Refer & Earn Is Available ", Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });


        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void sendmailintent(String task) {
        Toast toast = Toast.makeText(this, "SEND MAIL VIA GMAIL/YAHOO ", Toast.LENGTH_LONG);
        View view = toast.getView();

        view.getBackground().setColorFilter((Color.parseColor("#FF104162")), PorterDuff.Mode.SRC_IN);


        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(Color.WHITE);

        toast.show();
        String locale = SettingsActivity.this.getResources().getConfiguration().locale.getCountry();
        Intent send = new Intent(Intent.ACTION_SENDTO);
        String uriText = "mailto:" + Uri.encode("notificationapp.xenonstudio@gmail.com") +
                "?subject=" + Uri.encode("Language - Notification Log App ") +
                "&body=" + Uri.encode("" + task + " \n\n\n ------------ \n\n Version Code : " + versionCode + "\n Build : " + Build.BRAND + "\n" + Build.MODEL + "\n" + Build.DEVICE + "\n" + locale);
        Uri uri = Uri.parse(uriText);

        send.setData(uri);
        startActivity(Intent.createChooser(send, "Send Mail Via : "));
    }

    private void setAppTheme(@StyleRes int style) {
        setTheme(style);
    }

    private boolean isNightModeEnabled() {
        return mSharedPref.getBoolean(NIGHT_MODE, false);
    }

    private void setIsNightModeEnabled(boolean state) {
        SharedPreferences.Editor mEditor = mSharedPref.edit();
        mEditor.putBoolean(NIGHT_MODE, state);
        mEditor.apply();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        checkConnection();
        updatedata();
        // Check if user is signed in (non-null) and update UI accordingly.
     /*   FirebaseUser currentUser = mAuth.getCurrentUser();
        //Toast.makeText(this, ""+currentUser, Toast.LENGTH_SHORT).show();

        if(currentUser == null){

            sendToStart();

        } else {

            username = currentUser.getUid();
            //SAVEDATAREPORT
            savereport(currentUser);
        }*/
        //updateUI(currentUser);
    }

    private void savereport(FirebaseUser currentUser) {
        mdatareport.child("User").setValue(currentUser);
        mdatareport.child("M-Note For User(For Dev)").setValue("-");
        mdatareport.child("M-Note Date (For Dev)").setValue("-");

    }

    private void sendToStart() {

        username = "Not Signed In";
        //Toast.makeText(this, "Not Signed", Toast.LENGTH_SHORT).show();;

    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    public void checkConnection() {
        if (isOnline()) {

            Log.d("Hello", "Net");


        } else {

            cddata.setVisibility(View.GONE);
            View parentLayout = findViewById(android.R.id.content);

            Snackbar snackbar = Snackbar
                    .make(parentLayout, "Internet Connection Not Found! ", Snackbar.LENGTH_SHORT);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.parseColor("#FF104162"));

            snackbar.show();
        }
    }


    private void signInAnonymously() {
        // showProgressDialog();
        // [START signin_anonymously]
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInAnonymously:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            View parentLayout = findViewById(android.R.id.content);

                            Snackbar snackbar = Snackbar
                                    .make(parentLayout, "Thanks For Improving Our App !", Snackbar.LENGTH_LONG);
                            View snackbarView = snackbar.getView();
                            snackbarView.setBackgroundColor(Color.parseColor("#FF104162"));
                            snackbar.show();
                            mAuth = FirebaseAuth.getInstance();


                            if (mAuth.getCurrentUser() != null) {


                                mdatareport = FirebaseDatabase.getInstance().getReference().child("UsageReports").child(mAuth.getCurrentUser().getUid());

                            }


                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInAnonymously:failure", task.getException());
                            Toast.makeText(SettingsActivity.this, "Something Went Wrong",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        // hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END signin_anonymously]
    }

    private void updateUI(FirebaseUser user) {
        boolean isSignedIn = (user != null);

        // Status text
        if (isSignedIn) {
            username = user.getUid();

        } else {
            username = null;
        }
    }

    public void putBooleanInPreferences(boolean isChecked, String key) {
        SharedPreferences sharedPreferences = this.getPreferences(Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, isChecked);
        editor.commit();
        signInAnonymously();
    }

    public boolean getBooleanFromPreferences(String key) {
        SharedPreferences sharedPreferences = this.getPreferences(Activity.MODE_PRIVATE);
        Boolean isChecked = sharedPreferences.getBoolean(key, false);

        return isChecked;

    }

    public void updatedata() {

        FirebaseUser currentUser = mAuth.getCurrentUser();
        //Toast.makeText(this, ""+currentUser, Toast.LENGTH_SHORT).show();

        if (currentUser == null) {

            sendToStart();

        } else {


            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.getDefault());

            String datte = format.format(new Date());
            String idtimedate = datte.substring(5, 10);
            String idtime = datte.substring(11, 19);
            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
            String datetime = ft.format(dNow);
            String iddate = datetime.substring(0, 3);
            //String uidid = uid.substring(0,5);

            // mdatareport.child("D-Unique ID").setValue(iddate+idtimedate+idtime);


            String lan = Locale.getDefault().getDisplayLanguage();
            String country = SettingsActivity.this.getResources().getConfiguration().locale.getCountry();
            String buildID = ("" + Build.ID);
            TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            String countryCodeValue = tm.getNetworkCountryIso();

            TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            String carrierName = manager.getNetworkOperatorName();
            String devicetime = ("" + Build.USER);
            String buildd = ("" + Build.HOST);
            String devicedet = ("" + Build.BRAND + " " + Build.MODEL + " " + Build.DEVICE + " " + Build.TYPE + "" + Build.HOST);
            String tokenID = SharedCommon.getSharedPreferencesString(SettingsActivity.this, keytokenid, "");


            mdatareport.child("D-Date Time").setValue(datte);
            mdatareport.child("D-Language").setValue(lan);

            mdatareport.child("D-Status").setValue("Logged In");
            mdatareport.child("D-FirebaseToken").setValue(tokenID);


            //  mdatareport.child("U-FirebaseToken").setValue(tokenID);

            mdatareport.child("U-BuildID").setValue(buildID);

            mdatareport.child("U-Carrier").setValue(carrierName);
            mdatareport.child("U-Country").setValue(countryCodeValue);
            mdatareport.child("U-Version").setValue(versionName);
            mdatareport.child("U-BuildUser").setValue(devicetime);
            mdatareport.child("U-Device").setValue(devicedet);
        }
    }

    private void logout() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.getDefault());

        String datte = format.format(new Date());
        String idtimedate = datte.substring(5, 10);
        String idtime = datte.substring(11, 19);

        mdatareport.child("D-Logged Out Time").setValue(datte);


        mdatareport.child("D-Status").setValue("Logged Out");
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //Toast.makeText(this, "Your Data Usage Is Will Not Been Logged", Toast.LENGTH_SHORT).show();
        View parentLayout = findViewById(android.R.id.content);

        Snackbar snackbar = Snackbar
                .make(parentLayout, "Your Data Usages Will Not Be Logged", Snackbar.LENGTH_INDEFINITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.parseColor("#FF104162"));
        snackbar.setAction("Know More", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SettingsActivity.this, R.style.AlertDialog);

                builder.setIcon(R.drawable.notificationlogo);
                builder.setTitle("More Info ");
                builder.setMessage("Your All Data Has Been Deleted For User ID " + currentUser.getUid());
                builder.setCancelable(false)
                        .setPositiveButton("Got It", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                snackbar.dismiss();
                            }
                        });

                AlertDialog dialog1 = builder.create();
                dialog1.show();
            }
        });

        snackbar.show();

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3600) {
                        sleep(100);
                        waited += 100;
                    }
                    snackbar.dismiss();

                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    snackbar.dismiss();
                    // SettingsActivity.this.finish();
                }

            }
        };
        splashTread.start();
        //Toast.makeText(this, ""+currentUser, Toast.LENGTH_SHORT).show();


        // mdatareport = FirebaseDatabase.getInstance().getReference().child("usagereport").child(mAuth.getCurrentUser().getUid());


        if (currentUser == null) {

            sendToStart();

        } else {
            mdatareport.removeValue();
            museref.removeValue();
            mUserRef.removeValue();
            currentUser.delete();
            username = "Not Tracking Data Usage";
        }

        //mAuth.signOut();
        //updateUI(null);
    }

    private void showintads() {
        if (mInterstitialAd.isAdLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }
}
