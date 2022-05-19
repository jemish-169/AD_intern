package com.appnotification.notificationhistorylog.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.appnotification.notificationhistorylog.BuildConfig;
import com.appnotification.notificationhistorylog.CommonCl.SharedCommon;
import com.appnotification.notificationhistorylog.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;
import java.util.Map;

import static com.appnotification.notificationhistorylog.CommonCl.SharedCommon.key1;

public class GmailActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

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
    GmailAdapter adapter;

    private EditText searchEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmail);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(layoutManager);

        swipeRefreshLayout = findViewById(R.id.swiper);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);

        update();

        /* searchEdit = findViewById(R.id.edit_search);
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                GmailAdapter mAdapter = new GmailAdapter(GmailActivity.this);

                mAdapter.filterList(mAdapter.filter(editable.toString()));
                recyclerView.setAdapter(mAdapter);


            }
        });*/
       /* new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                if(direction == ItemTouchHelper.LEFT) {
                    adapter.deleteItem(((GmailViewHolder) viewHolder).getAdapterPosition());
                }

            }
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (viewHolder != null) {
                    final View foregroundView = ((GmailViewHolder) viewHolder).viewForeground;
                    getDefaultUIUtil().onSelected(foregroundView);
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                final View foregroundView = ((GmailViewHolder) viewHolder).viewForeground;
                getDefaultUIUtil().clearView(foregroundView);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                    RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                    int actionState, boolean isCurrentlyActive) {
                final View foregroundView = ((GmailViewHolder) viewHolder).viewForeground;

                getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                        actionState, isCurrentlyActive);

            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                        RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                        int actionState, boolean isCurrentlyActive) {
                final View foregroundView = ((GmailViewHolder) viewHolder).viewForeground;
                getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                        actionState, isCurrentlyActive);
                if(getSwipeDirs(recyclerView,viewHolder) == ItemTouchHelper.RIGHT){
                    ((GmailViewHolder) viewHolder).viewBackground_delete.setBackgroundColor(getResources().getColor(R.color.green));
                    ((GmailViewHolder) viewHolder).option_text.setText("ADD TO FAVORITES");
                }
            }


        }).attachToRecyclerView(recyclerView);*/
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
    private void search() {

        if (searchEdit.getVisibility() == View.VISIBLE) {
            searchEdit.setVisibility(View.GONE);
        } else {
            searchEdit.setVisibility(View.VISIBLE);
        }

    }
    private void update() {
       /* BrowseAdapter adapter = new BrowseAdapter(GmailActivity.this,"gp");
        recyclerView.setAdapter(adapter);

        if(adapter.getItemCount() == 0) {
            Toast.makeText(this, R.string.empty_log_file, Toast.LENGTH_LONG).show();
            finish();
        }*/
        GmailAdapter adapter = new GmailAdapter(this,"gp");
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