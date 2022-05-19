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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteViewHolder> {

    private final static int LIMIT = Integer.MAX_VALUE;
    private final static String PAGE_SIZE = "20";
    private Boolean flag = false;

    private DateFormat format = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.getDefault());

    private Activity context;
    private ArrayList<DataItem> data = new ArrayList<>();
    private HashMap<String, Drawable> iconCache = new HashMap<>();
    private Handler handler = new Handler();

    private String lastDate = "";
    private boolean shouldLoadMore = true;

    FavoriteAdapter(Activity context, Boolean flag) {
        this.context = context;
        this.flag = flag;
        loadMore(Integer.MAX_VALUE);
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_browse, parent, false);
        FavoriteViewHolder vh = new FavoriteViewHolder(view);
        vh.item.setOnClickListener(v -> {
            String id = (String) v.getTag();
            if(id != null) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("EXTRA_INFO", "hide");
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
    public void onBindViewHolder(@NonNull FavoriteViewHolder vh, int position) {
        FavoriteAdapter.DataItem item = data.get(position);

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


            vh.date.setVisibility(View.GONE);


        if(position == getItemCount() - 1) {
            loadMore(item.getId());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
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
            Cursor cursor=null;

                cursor = db.query(DatabaseHelper.PostedEntry.TABLE_NAME,
                        new String[] {
                                DatabaseHelper.PostedEntry._ID,
                                DatabaseHelper.PostedEntry.COLUMN_NAME_CONTENT
                        },
                        DatabaseHelper.PostedEntry.COLUMN_NAME_FAVORITE + "= ? AND "+DatabaseHelper.PostedEntry._ID + " < ?",
                        new String[]{ "" + String.valueOf(1), "" + afterId},
                        null,
                        null,
                        DatabaseHelper.PostedEntry._ID + " DESC",
                        PAGE_SIZE);
                Log.v("CURSOR", String.valueOf(cursor.getCount()));


            if(cursor != null && cursor.moveToFirst()) {
                for(int i = 0; i < cursor.getCount(); i++) {
                    FavoriteAdapter.DataItem dataItem = new FavoriteAdapter.DataItem(context, cursor.getLong(0), cursor.getString(1));

                    String thisDate = dataItem.getDate();
                    if(lastDate.equals(thisDate)) {
                        dataItem.setShowDate(false);
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

    public void deleteFromFavorite(int position){

        long id = data.get(position).id;
        Log.v("METHOD", "delete favorite");
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.PostedEntry.COLUMN_NAME_FAVORITE, 0);
        db.update(DatabaseHelper.PostedEntry.TABLE_NAME, contentValues, "_ID="+id,null );

        data.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, data.size());

    }

    private class DataItem {

        private long id;
        private String packageName;
        private String appName;
        private String text;
        private String preview;
        private String date;
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

    }

}
