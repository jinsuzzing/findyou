package com.jinsu.findyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserActivity extends AppCompatActivity implements LocationListener {

    Button btnfir = null;
    Button btnpol = null;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_user);

        btnfir = (Button)findViewById (R.id.btnfir);
        btnpol = (Button)findViewById (R.id.btnpol);

        LocationManager manager = (LocationManager)getSystemService (Context.LOCATION_SERVICE);
        manager.requestLocationUpdates (LocationManager.GPS_PROVIDER, 0, 0, this);

//긴급전화 걸기
        btnfir.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Intent.ACTION_DIAL, Uri.parse ("tel:119"));
                startActivity (intent);
            }
        });

        btnpol.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Intent.ACTION_DIAL, Uri.parse ("tel:112"));
                startActivity (intent);
            }
        });
    }
    //데이터베이스에 위치전송
    @Override
    public void onLocationChanged(@NonNull Location location) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("UserLocation");

        double lat = location.getLatitude ();
        double lon = location.getLongitude ();

        myRef.child ("latitude").setValue (""+lat);
        myRef.child ("longitude").setValue (""+lon);

    }
}