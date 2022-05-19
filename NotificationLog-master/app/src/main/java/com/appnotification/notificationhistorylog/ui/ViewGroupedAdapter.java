package com.appnotification.notificationhistorylog.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.appnotification.notificationhistorylog.R;
import com.appnotification.notificationhistorylog.misc.Util;


import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class ViewGroupedAdapter extends RecyclerView.Adapter<BrowseViewHolder> {

    private ArrayList<HelperObject> data = new ArrayList<>();
    private HashMap<String, Drawable> iconCache = new HashMap<>();
    private Activity context;
    private DateFormat format = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.getDefault());

    public ViewGroupedAdapter(Activity context,ArrayList<HelperObject> data){
        this.context = context;
        this.data = data;
        for (HelperObject itm : data){
            if (!iconCache.containsKey(itm.getPackageName())){
                iconCache.put(itm.getPackageName(), Util.getAppIconFromPackage(this.context,itm.getPackageName()));
            }
        }
    }

    @NonNull
    @Override
    public BrowseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_browse, parent, false);
        BrowseViewHolder vh = new BrowseViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull BrowseViewHolder vh, int position) {
            HelperObject item = data.get(position);

        if(iconCache.containsKey(item.getPackageName()) && iconCache.get(item.getPackageName()) != null) {
            vh.icon.setImageDrawable(iconCache.get(item.getPackageName()));
        } else {
            vh.icon.setImageResource(R.mipmap.ic_launcher);
        }

        vh.name.setText(item.getTitle());
        vh.text.setText(item.getPackageName());
        vh.preview.setText("Total notifications: "+item.getNotificationCount());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
