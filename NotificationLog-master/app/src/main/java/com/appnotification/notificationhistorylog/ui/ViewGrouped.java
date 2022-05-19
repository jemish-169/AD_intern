package com.appnotification.notificationhistorylog.ui;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.appnotification.notificationhistorylog.CommonCl.SharedCommon;
import com.appnotification.notificationhistorylog.FAQActivity;
import com.appnotification.notificationhistorylog.R;
import com.appnotification.notificationhistorylog.SettingsActivity;
import com.appnotification.notificationhistorylog.misc.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.appnotification.notificationhistorylog.CommonCl.SharedCommon.keyfaqs;

public class ViewGrouped extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<BrowseAppAdapter.DataItem> data = new ArrayList<>();
    private ArrayList<GroupedDataItem> newData = new ArrayList<>();
    private ArrayList<HelperObject> holderObjs = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_grouped);

        recyclerView = findViewById(R.id.list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //start filling recycler view
        BrowseAppAdapter adapter = new BrowseAppAdapter(this);
        data = adapter.getData();
        for(BrowseAppAdapter.DataItem itm : data){
            newData.add(new GroupedDataItem(this,itm.getStr()));
        }

        @SuppressLint({"NewApi", "LocalSuppress"}) Map<String, List<GroupedDataItem>> groupedByPackageName = newData
                .stream()
                .collect(Collectors.groupingBy(w -> w.getPackageName()));

        for (String str : groupedByPackageName.keySet()){
            HelperObject obj = new HelperObject();
            obj.setPackageName(str);
            obj.setTitle(groupedByPackageName.get(str).get(0).getAppName());
            obj.setNotificationCount(groupedByPackageName.get(str).size());
            holderObjs.add(obj);
        }

        ViewGroupedAdapter groupedAdapter = new ViewGroupedAdapter(this,holderObjs);
        recyclerView.setAdapter(groupedAdapter);
//        Log.e("Map size","Size: "+groupedByPackageName.size());
//        recyclerView.setAdapter(adapter);

        if(adapter.getItemCount()==0){
            Toast.makeText(this, R.string.empty_log_file, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menustats, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.count:
              graphinfo();
                    /*getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new SettingsFragment()).commit();*/


                return true;



        }
        return super.onOptionsItemSelected(item);
    }

    private void graphinfo() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ViewGrouped.this);

        builder.setIcon(R.drawable.notificationlogo);

        builder.setTitle("Coming Soon!");

        builder.setMessage("Get Insights Of Notifications, View Data Of Notifications Into Graph And Manage Easily");
        builder.setCancelable(false);

        builder.setPositiveButton("Got It", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Intent startIntent = new Intent(ViewGrouped.this, NewMainActivity.class);
                startActivity(startIntent);

            }
        });
        builder.setNegativeButton("Notify Me", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ViewGrouped.this, "We Will Notify You ", Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });



        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }
}
