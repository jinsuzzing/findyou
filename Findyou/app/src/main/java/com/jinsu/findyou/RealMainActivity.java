package com.jinsu.findyou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RealMainActivity extends AppCompatActivity {

    Button btnFinder = null;
    Button btnUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_real_main);

        ActivityCompat.requestPermissions (this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);

        btnFinder = (Button)findViewById (R.id.btnFinder);
        btnUser = (Button)findViewById (R.id.btnUser);


        btnFinder.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //화면넘기기 Intent
                Intent i = new Intent(getApplicationContext (), FinderActivity.class);
                startActivity (i);
            }
        });

        btnUser.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (getApplicationContext (), UserActivity.class);
                startActivity (i);
            }
        });
    }
}