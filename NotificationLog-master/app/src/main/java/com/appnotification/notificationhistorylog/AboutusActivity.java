package com.appnotification.notificationhistorylog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class AboutusActivity extends AppCompatActivity {

    Button btncontact;
    LinearLayout harshil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        btncontact = (Button)findViewById(R.id.btncontact);
        btncontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent send = new Intent(Intent.ACTION_SENDTO);
                String uriText = "mailto:" + Uri.encode("thexenonstudio@gmail.com") +
                        "?subject=" + Uri.encode("Notification Log - Developer Contact") +
                        "&body=" + Uri.encode("Hello, Type Your Query/Problem/Bug/Suggestions Here");
                Uri uri = Uri.parse(uriText);

                send.setData(uri);
                startActivity(Intent.createChooser(send, "Send Mail Via : "));

                finish();
            }
        });

        harshil = (LinearLayout)findViewById(R.id.harshil);
        harshil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://www.xenonstudio.in/developer";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}
