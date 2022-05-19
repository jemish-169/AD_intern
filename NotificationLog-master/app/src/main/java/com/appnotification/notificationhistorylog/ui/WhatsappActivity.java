package com.appnotification.notificationhistorylog.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.appnotification.notificationhistorylog.BuildConfig;
import com.appnotification.notificationhistorylog.CommonCl.SharedCommon;
import com.appnotification.notificationhistorylog.R;
import com.appnotification.notificationhistorylog.misc.Const;
import com.appnotification.notificationhistorylog.misc.DatabaseHelper;
import com.appnotification.notificationhistorylog.misc.Util;
import com.appnotification.notificationhistorylog.service.NotificationHandler;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static com.appnotification.notificationhistorylog.CommonCl.SharedCommon.key1;

public class WhatsappActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    int versionCode = BuildConfig.VERSION_CODE;
    String versionName = BuildConfig.VERSION_NAME;

    private AdView mAdView;
    private FirebaseAuth mAuth;
    public String whatnew;
    public String showallnotf;
    public String shownotif;
    public String showtuto;
    public String showfav;
    public String livenotice;

    String appid = BuildConfig.APPLICATION_ID;
    FirebaseRemoteConfig firebaseRemoteConfigprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatsapp);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout = findViewById(R.id.swiper);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);
        update();

        //ADS+Firebase
        mAdView = new com.facebook.ads.AdView(this, getString(R.string.fb_banner_ad), com.facebook.ads.AdSize.BANNER_HEIGHT_50);
        mAdView.loadAd();
        ((LinearLayout) findViewById(R.id.fb_container)).addView(mAdView);
        firebaseRemoteConfigprice = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder().build();
        firebaseRemoteConfigprice.setConfigSettings(configSettings);

        Map<String, Object> pricedata = new HashMap<>();
        pricedata.put("showads", "yn");

        firebaseRemoteConfigprice.setDefaults(pricedata);
        checkadstatus();
    }

    private void checkadstatus() {
        {



            firebaseRemoteConfigprice.fetch(0)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Log.e("TaskError","info"+ firebaseRemoteConfigprice.getInfo().getLastFetchStatus());


                            Log.e("TaskError","firebaseremote"+ firebaseRemoteConfigprice.getString("btn_text"));

                            if (task.isSuccessful()) {


                                firebaseRemoteConfigprice.activateFetched();
                                whatnew=(firebaseRemoteConfigprice.getString("showads"));




                                if (whatnew.equals("yes")){
                                    showads();

                                    //Toast.makeText(MainActivity.this, "Showing", Toast.LENGTH_SHORT).show();
                                }
                                else if (whatnew.equals("no")){
                                    mAdView.setVisibility(View.GONE);

                                    // Toast.makeText(MainActivity.this, "Nope", Toast.LENGTH_SHORT).show();

                                    Log.e("AdsStatus","Not Showing");

                                }

                                Log.e("TaskError","firebaseremote"+ firebaseRemoteConfigprice.getString("btn_text"));

                            } else {


                                String exp = (""+task.getException().getMessage());
                                if (exp.equals("null")){

                                    whatnew=("Server Not Responding ");
                                }
                                else {
                                    Log.e("TaskError","taskexcep :"+ task.getException().getMessage()+task.getException()+task);
                                    Toast.makeText(WhatsappActivity.this, "Internet Connection Error" , Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        }

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

                int i = SharedCommon.getPreferencesInt(getApplicationContext(), key1,0);


                i++;
                sc.putPreferencesInt(getApplicationContext(),SharedCommon.key1,i);
            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && DetailsActivity.ACTION_REFRESH.equals(data.getStringExtra(DetailsActivity.EXTRA_ACTION))) {
            update();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.browse_appwise, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                update();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void update() {
        GmailAdapter adapter = new GmailAdapter(WhatsappActivity.this,"wp");
        recyclerView.setAdapter(adapter);

        if(adapter.getItemCount() == 0) {
            Toast.makeText(this, R.string.empty_log_file, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void onRefresh() {
        update();
        swipeRefreshLayout.setRefreshing(false);
    }

}
