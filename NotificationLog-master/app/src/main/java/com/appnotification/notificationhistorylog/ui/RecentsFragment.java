package com.appnotification.notificationhistorylog.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appnotification.notificationhistorylog.BuildConfig;
import com.appnotification.notificationhistorylog.R;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AdView;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecentsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private DatabaseReference mUserRef, museref, mdatareport, mcredtref, mlinkupdate;
    public static final int NUMBER_OF_ADS = 10;
    private AdLoader adLoader;
    private List<UnifiedNativeAd> mNativeAds = new ArrayList<>();

    private FirebaseAuth mAuth;
    public ImageView imgmore, imgemnu;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView selectedNavigation;
    private EditText searchEdit;
    int versionCode = BuildConfig.VERSION_CODE;
    String versionName = BuildConfig.VERSION_NAME;

//    private AdView mAdView;
    public String whatnew;
    public String adappid;
    public String nativeadid;
    public String hisormine;
    public String eroorcode;
    public String livenotice;

    String appid = BuildConfig.APPLICATION_ID;
    FirebaseRemoteConfig firebaseRemoteConfigprice;
    public RecentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_recents, container, false);

        getActivity().setTitle("Notifications");

        Bundle bundle = this.getArguments();
        String fragmentName = bundle.getString("selected_navigation");

        // Initialize the Mobile Ads SDK.
        if (savedInstanceState == null) {
            //loadNativeAds();
            Log.e("nativeads","LOG");

        }
        //selectedNavigation = view.findViewById(R.id.selected_navigation);

        //selectedNavigation.setText(fragmentName);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(layoutManager);
        imgemnu = view.findViewById(R.id.imageView2);
        imgemnu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NewMainActivity)getActivity()).openDrawer();
            }
        });
        imgmore = view.findViewById(R.id.imgmore);
        imgmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NewMainActivity)getActivity()).showPopup(view);
                // ((NewMainActivity)getActivity()).openactionmenu();
            }
        });
        swipeRefreshLayout = view.findViewById(R.id.swiper);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);

        update();
        searchEdit = view.findViewById(R.id.edit_search);
        searchEdit.setCursorVisible(false);
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchEdit.setCursorVisible(false);            }

            @Override
            public void afterTextChanged(Editable editable) {
                BrowseAdapterHome mAdapter = new BrowseAdapterHome(getActivity(), mNativeAds);

                mAdapter.filterList(mAdapter.filter(editable.toString()));
                recyclerView.setAdapter(mAdapter);
                searchEdit.setCursorVisible(true);

                firebasedatabseupdate(editable.toString());
            }
        });
        //ADS+Firebase
        //mAdView = findViewById(R.id.adView);
        //AdRequest adRequest = new AdRequest.Builder().build();
       // mAdView.loadAd(adRequest);
        firebaseRemoteConfigprice = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder().build();
        firebaseRemoteConfigprice.setConfigSettings(configSettings);

        Map<String, Object> pricedata = new HashMap<>();
        pricedata.put("shownativeads", "yn");
        pricedata.put("appid", "yn");
        pricedata.put("nativeadid", "yn");
        pricedata.put("hisormine", "yn");

        firebaseRemoteConfigprice.setDefaults(pricedata);
        checkadstatus();

        return view;
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
                                whatnew=(firebaseRemoteConfigprice.getString("shownativeads"));
                                adappid=(firebaseRemoteConfigprice.getString("appid"));
                                nativeadid=(firebaseRemoteConfigprice.getString("nativeadid"));
                                hisormine=(firebaseRemoteConfigprice.getString("hisormine"));

                                Log.e("adappid",adappid);
                                Log.e("nativeadid",nativeadid);


                                if (whatnew.equals("yes")){
                                    loadNativeAds();

                                    //Toast.makeText(MainActivity.this, "Showing", Toast.LENGTH_SHORT).show();
                                }


                                else if (whatnew.equals("no")){
                                    mNativeAds.remove(true);
                                    // Toast.makeText(MainActivity.this, "Nope", Toast.LENGTH_SHORT).show();
                                    Log.e("AdsStatus","Not Showing");

                                }
                               /* if (hisormine.equals("mine")){
                                    loadNativeAds();

                                    //Toast.makeText(MainActivity.this, "Showing", Toast.LENGTH_SHORT).show();
                                } else if (hisormine.equals("his")){
                                    adappid = "";
                                    nativeadid = nativeadid;

                                    // Toast.makeText(MainActivity.this, "Nope", Toast.LENGTH_SHORT).show();
                                    Log.e("AdsStatus","his");

                                }
                                else if (hisormine.equals("test")){
                                    adappid = "ca-app-pub-8081344892743036~8262343723";
                                    nativeadid = "ca-app-pub-3940256099942544/2247696110";

                                    // Toast.makeText(MainActivity.this, "Nope", Toast.LENGTH_SHORT).show();
                                    Log.e("AdsStatus","his");

                                }*/


                                Log.e("TaskError","firebaseremote"+ firebaseRemoteConfigprice.getString("btn_text"));

                            } else {


                                String exp = (""+task.getException().getMessage());
                                if (exp.equals("null")){

                                    whatnew=("Server Not Responding ");
                                }
                                else {
                                    Log.e("TaskError","taskexcep :"+ task.getException().getMessage()+task.getException()+task);
                                    Toast.makeText(getActivity(), "Internet Connection Error" , Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        }

    }

    private void loadNativeAds() {

        AdLoader.Builder builder = new AdLoader.Builder(getActivity(), getString(R.string.ad_native_unit_id));
        adLoader = builder.forUnifiedNativeAd(
                new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        // A native ad loaded successfully, check if the ad loader has finished loading
                        // and if so, insert the ads into the list.
                        mNativeAds.add(unifiedNativeAd);
                        if (!adLoader.isLoading()) {
                            update();
                        }
                    }
                }).withAdListener(
                new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // A native ad failed to load, check if the ad loader has finished loading
                        // and if so, insert the ads into the list.
                        Log.e("MainActivity", "The previous native ad failed to load. Attempting to"
                                + " load another."+errorCode);

                        eroorcode = String.valueOf(errorCode);
                        if (!adLoader.isLoading()) {
                            update();
                        }
                    }
                }).build();

        // Load the Native ads.
        adLoader.loadAds(new AdRequest.Builder().build(), NUMBER_OF_ADS);

//        final NativeAd nativeAd = new NativeAd(getContext(), getString(R.string.fb_native_ad));
//        nativeAd.loadAd();
//
//        if (nativeAd.isAdLoaded()) {
//            inflateAd(getContext(), nativeAd, nativeAdLayout);
//        }
    }

//    public static void inflateAd(Context context, NativeAd nativeAd, NativeAdLayout nativeAdLayout) {
//        nativeAd.unregisterView();
//        try {
//            int i = 0;
//            View view = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.native_ad_layout_1, nativeAdLayout, false);
//            nativeAdLayout.addView(view);
//            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ad_choices_container);
//            AdOptionsView adOptionsView = new AdOptionsView(context, nativeAd, nativeAdLayout);
//            linearLayout.removeAllViews();
//            linearLayout.addView(adOptionsView, 0);
//            TextView textView = (TextView) view.findViewById(R.id.native_ad_title);
//            MediaView mediaView2 = (MediaView) view.findViewById(R.id.native_ad_media);
//            TextView textView2 = (TextView) view.findViewById(R.id.native_ad_social_context);
//            TextView textView3 = (TextView) view.findViewById(R.id.native_ad_body);
//            TextView textView4 = (TextView) view.findViewById(R.id.native_ad_sponsored_label);
//            Button button = (Button) view.findViewById(R.id.native_ad_call_to_action);
//            textView.setText(nativeAd.getAdvertiserName());
//            textView3.setText(nativeAd.getAdBodyText());
//            textView2.setText(nativeAd.getAdSocialContext());
//            if (!nativeAd.hasCallToAction()) {
//                i = 4;
//            }
//            button.setVisibility(i);
//            button.setText(nativeAd.getAdCallToAction());
//            textView4.setText(nativeAd.getSponsoredTranslation());
//            List arrayList = new ArrayList();
//            arrayList.add(textView);
//            arrayList.add(button);
//            nativeAd.registerViewForInteraction(view, mediaView2, arrayList);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    private void firebasedatabseupdate(String s) {
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {

            sendToStart();

        } else {


            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.getDefault());

            String datte = format.format(new Date());
            String idtimedate = datte.substring(5, 10);
            String idtime = datte.substring(11, 19);
            mdatareport = FirebaseDatabase.getInstance().getReference().child("UsageReports").child(mAuth.getCurrentUser().getUid());
            museref = FirebaseDatabase.getInstance().getReference().child("mainacreport").child(mAuth.getCurrentUser().getUid());

            mUserRef = FirebaseDatabase.getInstance().getReference().child("Search-Query").child(mAuth.getCurrentUser().getUid());
            BrowseAdapterHome adapter = new BrowseAdapterHome(getActivity(), mNativeAds);

            String coint = String.valueOf(adapter.getItemCount());

            mUserRef.child("Last-Query").setValue(s);
            mUserRef.child("Log-Count-While-Searching").setValue(coint);
            mUserRef.child("Last-Query-Time").setValue(datte);
            mUserRef.child("ADERCO").setValue(eroorcode);

            //username = currentUser.getUid();
            //SAVEDATAREPORT
            //savereport(currentUser);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && DetailsActivity.ACTION_REFRESH.equals(data.getStringExtra(DetailsActivity.EXTRA_ACTION))) {
            update();
        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void sendToStart() {

        //username = "Not Signed In";
        //Toast.makeText(this, "Not Signed", Toast.LENGTH_SHORT).show();;

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                Toast.makeText(getActivity(), "Refersh", Toast.LENGTH_SHORT).show();
                update();
                return true;


        }
        return super.onOptionsItemSelected(item);
    }
/*    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }*/
    private void search() {
        if (searchEdit.getVisibility() == View.VISIBLE) {
            searchEdit.setVisibility(View.GONE);
            searchEdit.setText("");
            update();
           /* InputMethodManager imm = (InputMethodManager)getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(searchEdit.getWindowToken(), 0);*/
        } else {
            searchEdit.setVisibility(View.VISIBLE);
           /* searchEdit.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(searchEdit, InputMethodManager.SHOW_IMPLICIT);*/
        }

    }

    private void update() {
        BrowseAdapterHome adapter = new BrowseAdapterHome(getActivity(), mNativeAds);
        recyclerView.setAdapter(adapter);

        if(adapter.getItemCount() == 0) {
            Toast.makeText(getActivity(), "No Notifications Has Been Logged Yet", Toast.LENGTH_SHORT).show();

            /*Toast.makeText(getContext(), R.string.empty_log_file, Toast.LENGTH_LONG).show();
            Intent startIntent = new Intent(getActivity(), IssueActivity.class);
            startActivity(startIntent);*/
        }
    }

    @Override
    public void onRefresh() {
        update();
        swipeRefreshLayout.setRefreshing(false);
    }
}
