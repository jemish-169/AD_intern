package com.appnotification.notificationhistorylog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.appnotification.notificationhistorylog.ui.BrowseActivity;
import com.appnotification.notificationhistorylog.ui.MainActivity;

import java.util.ArrayList;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity;

public class FavActivity extends AppCompatActivity {
    DbHelperIdeas dbHelper;
    ArrayAdapter<String> mAdapter;
    ListView lstTask;
    View view1,view2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        dbHelper = new DbHelperIdeas(this);



        lstTask = (ListView)findViewById(R.id.lstTaskidea);

        loadTaskList();
        doFirstRun();

    }

    private void loadTaskList() {
        ArrayList<String> taskList = dbHelper.getTaskList();
        if(mAdapter==null){
            mAdapter = new ArrayAdapter<String>(this,R.layout.row,R.id.task_title,taskList);
            lstTask.setAdapter(mAdapter);

        }
        else{
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void deleteTask(View view){
        View parent = (View)view.getParent();
        TextView taskTextView = (TextView)parent.findViewById(R.id.task_title);
        Log.e("String", (String) taskTextView.getText());
        String task = String.valueOf(taskTextView.getText());
        dbHelper.deleteTask(task);
        loadTaskList();
    }

    private void doFirstRun() {
        SharedPreferences settings = getSharedPreferences("FIRSTRUNTEXT2", MODE_PRIVATE);
        if (settings.getBoolean("isFirstRunDialogBoxtext2", true)) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("isFirstRunDialogBoxtext2", false);
            editor.commit();

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(FavActivity.this);

            builder.setIcon(R.drawable.notificationlogo);

            builder.setTitle("Add Notifications To Favorite  !");
            builder.setMessage("You Can Later Read Them  ");

            builder.setCancelable(false);


            builder.setPositiveButton("Start Adding ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent startIntent = new Intent(FavActivity.this, BrowseActivity.class);
                    startActivity(startIntent);

                }
            });
            builder.setNeutralButton("Got It ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                   dialogInterface.dismiss();

                }
            });

            android.app.AlertDialog dialog = builder.create();
            dialog.show();




        }
    }
    private void showtipshowcase() {

        new GuideView.Builder(this)
                .setTitle("Add Tasks")
                .setContentText("Click Here To Add Your Ideas")
                .setGravity(Gravity.auto) //optional
                .setDismissType(DismissType.outside) //optional - default DismissType.targetView
                .setTargetView(view1)
                .setContentTextSize(14)//optional
                .setTitleTextSize(16)//optional
                .build()
                .show();


    }
}
