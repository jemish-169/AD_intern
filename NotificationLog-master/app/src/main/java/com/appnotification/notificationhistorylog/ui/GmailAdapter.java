package com.appnotification.notificationhistorylog.ui;



import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.appnotification.notificationhistorylog.R;
import com.appnotification.notificationhistorylog.misc.Const;
import com.appnotification.notificationhistorylog.misc.DatabaseHelper;
import com.appnotification.notificationhistorylog.misc.Util;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

class GmailAdapter extends RecyclerView.Adapter<BrowseViewHolder> {
    private final static int LIMIT = Integer.MAX_VALUE;
    private final static String PAGE_SIZE = "9999";

    private DateFormat format = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.getDefault());

    private Activity context;
    private ArrayList<DataItem> data = new ArrayList<>();
    private HashMap<String, Drawable> iconCache = new HashMap<>();
    private Handler handler = new Handler();

    private String lastDate = "";
    private String type;
    private boolean shouldLoadMore = true;

    GmailAdapter(Activity context, String type) {
        this.context = context;
        this.type = type;
        loadMore(Integer.MAX_VALUE);
    }
    public void deleteItem(int position) {
        long id = data.get(position).id;
        Log.v("METHOD", "delete favorite");
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(DatabaseHelper.PostedEntry.TABLE_NAME, "_ID="+id,null );
        data.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, data.size());
    }
    @NonNull
    @Override
    public BrowseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_browse, parent, false);
        BrowseViewHolder vh = new BrowseViewHolder(view);
        vh.item.setOnClickListener(v -> {
            String id = (String) v.getTag();
            if(id != null) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra(DetailsActivity.EXTRA_ID, id);
                if(Build.VERSION.SDK_INT >= 21) {
                    Pair<View, String> p1 = Pair.create(vh.icon, "icon");
                    @SuppressWarnings("unchecked") ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(context, p1);
                    context.startActivityForResult(intent, 1, options.toBundle());
                } else {
                    context.startActivityForResult(intent, 1);
                }
            }
        });
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull BrowseViewHolder vh, int position) {
        DataItem item = data.get(position);
        if(type.equals("wp") && item.packageName.contains("whatsapp"))
        {
            if(iconCache.containsKey(item.getPackageName()) && iconCache.get(item.getPackageName()) != null) {
                vh.icon.setImageDrawable(iconCache.get(item.getPackageName()));
            } else {
                vh.icon.setImageResource(R.mipmap.ic_launcher);
            }

            vh.item.setTag("" + item.getId());
            vh.name.setText(item.getAppName());
            vh.time.setText(item.getTime());

            if(item.getPreview().length() == 0) {
                vh.preview.setVisibility(View.GONE);
                vh.text.setVisibility(View.VISIBLE);
                vh.text.setText(item.getText());
            } else {
                vh.text.setVisibility(View.GONE);
                vh.preview.setVisibility(View.VISIBLE);
                vh.preview.setText(item.getPreview());
            }
            Log.d("texts", "onBindViewHolder: "+item.shouldShowDate()+"  "+item.getDate());
            if(item.shouldShowDate()) {
                vh.date.setVisibility(View.VISIBLE);
                vh.date.setText(item.getDate());
            } else {
                vh.date.setVisibility(View.GONE);
            }
        }
        else if(type.equals("gp") && item.packageName.equals("com.google.android.gm"))
        {
            if(iconCache.containsKey(item.getPackageName()) && iconCache.get(item.getPackageName()) != null) {
                vh.icon.setImageDrawable(iconCache.get(item.getPackageName()));
            } else {
                vh.icon.setImageResource(R.mipmap.ic_launcher);
            }

            vh.item.setTag("" + item.getId());
            vh.name.setText(item.getAppName());

            if(item.getPreview().length() == 0) {
                vh.preview.setVisibility(View.GONE);
                vh.text.setVisibility(View.VISIBLE);
                vh.text.setText(item.getText());
            } else {
                vh.text.setVisibility(View.GONE);
                vh.preview.setVisibility(View.VISIBLE);
                vh.preview.setText(item.getPreview());
            }
            Log.d("texts", "onBindViewHolder: "+item.shouldShowDate()+"  "+item.getDate());
            if(item.shouldShowDate()) {
                vh.date.setVisibility(View.VISIBLE);
                vh.date.setText(item.getDate());
            } else {
                vh.date.setVisibility(View.GONE);
            }
        }
        else if(type.equals("is") && item.packageName.contains("instagram"))
        {
            if(iconCache.containsKey(item.getPackageName()) && iconCache.get(item.getPackageName()) != null) {
                vh.icon.setImageDrawable(iconCache.get(item.getPackageName()));
            } else {
                vh.icon.setImageResource(R.mipmap.ic_launcher);
            }

            vh.item.setTag("" + item.getId());
            vh.name.setText(item.getAppName());

            if(item.getPreview().length() == 0) {
                vh.preview.setVisibility(View.GONE);
                vh.text.setVisibility(View.VISIBLE);
                vh.text.setText(item.getText());
            } else {
                vh.text.setVisibility(View.GONE);
                vh.preview.setVisibility(View.VISIBLE);
                vh.preview.setText(item.getPreview());
            }
            Log.d("texts", "onBindViewHolder: "+item.shouldShowDate()+"  "+item.getDate());
            if(item.shouldShowDate()) {
                vh.date.setVisibility(View.VISIBLE);
                vh.date.setText(item.getDate());
            } else {
                vh.date.setVisibility(View.GONE);
            }
        }
        else if(type.equals("ca") && item.packageName.contains("calendar"))
        {
            if(iconCache.containsKey(item.getPackageName()) && iconCache.get(item.getPackageName()) != null) {
                vh.icon.setImageDrawable(iconCache.get(item.getPackageName()));
            } else {
                vh.icon.setImageResource(R.mipmap.ic_launcher);
            }

            vh.item.setTag("" + item.getId());
            vh.name.setText(item.getAppName());

            if(item.getPreview().length() == 0) {
                vh.preview.setVisibility(View.GONE);
                vh.text.setVisibility(View.VISIBLE);
                vh.text.setText(item.getText());
            } else {
                vh.text.setVisibility(View.GONE);
                vh.preview.setVisibility(View.VISIBLE);
                vh.preview.setText(item.getPreview());
            }
            Log.d("texts", "onBindViewHolder: "+item.shouldShowDate()+"  "+item.getDate());
            if(item.shouldShowDate()) {
                vh.date.setVisibility(View.VISIBLE);
                vh.date.setText(item.getDate());
            } else {
                vh.date.setVisibility(View.GONE);
            }
        }
        else if(type.equals("fb") && item.packageName.contains("facebook"))
        {
            if(iconCache.containsKey(item.getPackageName()) && iconCache.get(item.getPackageName()) != null) {
                vh.icon.setImageDrawable(iconCache.get(item.getPackageName()));
            } else {
                vh.icon.setImageResource(R.mipmap.ic_launcher);
            }

            vh.item.setTag("" + item.getId());
            vh.name.setText(item.getAppName());

            if(item.getPreview().length() == 0) {
                vh.preview.setVisibility(View.GONE);
                vh.text.setVisibility(View.VISIBLE);
                vh.text.setText(item.getText());
            } else {
                vh.text.setVisibility(View.GONE);
                vh.preview.setVisibility(View.VISIBLE);
                vh.preview.setText(item.getPreview());
            }
            Log.d("texts", "onBindViewHolder: "+item.shouldShowDate()+"  "+item.getDate());
            if(item.shouldShowDate()) {
                vh.date.setVisibility(View.VISIBLE);
                vh.date.setText(item.getDate());
            } else {
                vh.date.setVisibility(View.GONE);
            }
        }
        else if(type.equals("call") && item.packageName.contains("dialer"))
        {
            if(iconCache.containsKey(item.getPackageName()) && iconCache.get(item.getPackageName()) != null) {
                vh.icon.setImageDrawable(iconCache.get(item.getPackageName()));
            } else {
                vh.icon.setImageResource(R.mipmap.ic_launcher);
            }

            vh.item.setTag("" + item.getId());
            vh.name.setText(item.getAppName());

            if(item.getPreview().length() == 0) {
                vh.preview.setVisibility(View.GONE);
                vh.text.setVisibility(View.VISIBLE);
                vh.text.setText(item.getText());
            } else {
                vh.text.setVisibility(View.GONE);
                vh.preview.setVisibility(View.VISIBLE);
                vh.preview.setText(item.getPreview());
            }
            Log.d("texts", "onBindViewHolder: "+item.shouldShowDate()+"  "+item.getDate());
            if(item.shouldShowDate()) {
                vh.date.setVisibility(View.VISIBLE);
                vh.date.setText(item.getDate());
            } else {
                vh.date.setVisibility(View.GONE);
            }
        }
        else if(type.equals("all"))
        {
            if(iconCache.containsKey(item.getPackageName()) && iconCache.get(item.getPackageName()) != null) {
                vh.icon.setImageDrawable(iconCache.get(item.getPackageName()));
            } else {
                vh.icon.setImageResource(R.mipmap.ic_launcher);
            }

            vh.item.setTag("" + item.getId());
            vh.name.setText(item.getAppName());

            if(item.getPreview().length() == 0) {
                vh.preview.setVisibility(View.GONE);
                vh.text.setVisibility(View.VISIBLE);
                vh.text.setText(item.getText());
            } else {
                vh.text.setVisibility(View.GONE);
                vh.preview.setVisibility(View.VISIBLE);
                vh.preview.setText(item.getPreview());
            }

            if(item.shouldShowDate()) {
                vh.date.setVisibility(View.VISIBLE);
                vh.date.setText(item.getDate());
            } else {
                vh.date.setVisibility(View.GONE);
            }
        }else
        {
            vh.item.setVisibility(View.GONE);
        }
        if(position == getItemCount() - 1) {
            loadMore(item.getId());
        }
    }




    @Override
    public int getItemCount() {
        return data.size();
    }

    public void filterList(ArrayList<DataItem> filteredList){
        data = filteredList;
        notifyDataSetChanged();
    }

    private void loadMore(long afterId) {
        if(!shouldLoadMore) {
            if(Const.DEBUG) System.out.println("not loading more items");
            return;
        }

        if(Const.DEBUG) System.out.println("loading more items");
        int before = getItemCount();
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            SQLiteDatabase db = databaseHelper.getReadableDatabase();

            Cursor cursor = db.query(DatabaseHelper.PostedEntry.TABLE_NAME,
                    new String[] {
                            DatabaseHelper.PostedEntry._ID,
                            DatabaseHelper.PostedEntry.COLUMN_NAME_CONTENT
                    },
                    DatabaseHelper.PostedEntry._ID + " < ?",
                    new String[] {""+afterId},
                    null,
                    null,
                    DatabaseHelper.PostedEntry._ID + " DESC",
                    PAGE_SIZE);

            if(cursor != null && cursor.moveToFirst()) {
                for(int i = 0; i < cursor.getCount(); i++) {
                    DataItem dataItem = new DataItem(context, cursor.getLong(0), cursor.getString(1));

                    String thisDate = dataItem.getDate();
                    if(lastDate.equals(thisDate)) {
                        dataItem.setShowDate(false);
                    }else
                    {
                        dataItem.setShowDate(true);
                    }
                    lastDate = thisDate;
                    data.add(dataItem);
                    cursor.moveToNext();
                }
                cursor.close();
            }

            db.close();
            databaseHelper.close();
        } catch (Exception e) {
            if(Const.DEBUG) e.printStackTrace();
        }
        int after = getItemCount();

        if(before == after) {
            if(Const.DEBUG) System.out.println("no new items loaded: " + getItemCount());
            shouldLoadMore = false;
        }

        if(getItemCount() > LIMIT) {
            if(Const.DEBUG) System.out.println("reached the limit, not loading more items: " + getItemCount());
            shouldLoadMore = false;
        }

        handler.post(() -> notifyDataSetChanged());
    }

    public ArrayList filter(String text){
        ArrayList<DataItem> filteredList = new ArrayList<>();

        for (DataItem item: data){
            if (item.getAppName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        return filteredList;

    }

    private class DataItem {

        private long id;
        private String packageName;
        private String appName;
        private String text;
        private String preview;
        private String date;
        private String time;
        private boolean showDate;

        DataItem(Context context, long id, String str) {
            this.id = id;
            try {
                JSONObject json = new JSONObject(str);
                packageName = json.getString("packageName");
                appName = Util.getAppNameFromPackage(context, packageName, false);
                text = str;

                String title = json.optString("title");
                String text = json.optString("text");
                preview = (title + "\n" + text).trim();

                if(!iconCache.containsKey(packageName)) {
                    iconCache.put(packageName, Util.getAppIconFromPackage(context, packageName));
                }

                date = format.format(json.optLong("systemTime"));
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                time = df.format(json.optLong("systemTime"));
                showDate = true;
            } catch (JSONException e) {
                if(Const.DEBUG) e.printStackTrace();
            }
        }

        public long getId() {
            return id;
        }

        public String getPackageName() {
            return packageName;
        }

        public String getAppName() {
            return appName;
        }

        public String getText() {
            return text;
        }

        public String getPreview() {
            return preview;
        }

        public String getDate() {
            return date;
        }

        public boolean shouldShowDate() {
            return showDate;
        }

        public void setShowDate(boolean showDate) {
            this.showDate = showDate;
        }

        public String getTime() {
            return time;
        }
    }

}


