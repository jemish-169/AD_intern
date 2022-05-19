package com.appnotification.notificationhistorylog.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.appnotification.notificationhistorylog.R;


public class FacebookActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

private RecyclerView recyclerView;
private SwipeRefreshLayout swipeRefreshLayout;

String packageName;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout = findViewById(R.id.swiper);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);
        update();
        boolean isAppInstalled = appInstalledOrNot("com.facebook.katana");

        if (isAppInstalled) {
                //This intent will help you to launch if the package is already installed
                /*Intent LaunchIntent = getPackageManager()
                        .getLaunchIntentForPackage("com.facebook.katana");
                startActivity(LaunchIntent);*/

               // Log.i("Application is already installed.");
        } else {

                Toast.makeText(this, "You Don't Have This App On Your Phone", Toast.LENGTH_SHORT).show();
                // Do whatever we want to do if application not installed
                // For example, Redirect to play store

               // Log.i("Application is not currently installed.");
        }
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
        GmailAdapter adapter = new GmailAdapter(this,"fb");
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
        private boolean appInstalledOrNot(String uri) {
                PackageManager pm = getPackageManager();
                try {
                        pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
                        return true;
                } catch (PackageManager.NameNotFoundException e) {
                }

                return false;
        }
        }


