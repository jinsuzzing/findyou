package com.jinsu.findyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FinderActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_finder);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager ()
                .findFragmentById (R.id.map);
        mapFragment.getMapAsync (this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("UserLocation");

        myRef.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DO data = snapshot.getValue (DO.class);
                //Toast.makeText (getApplicationContext (), "위도: " + data.getLatitude() + ", 경도" + data.getLongitude (), Toast.LENGTH_SHORT).show();

                LatLng point = new LatLng (Double.parseDouble (data.getLatitude ()), Double.parseDouble (data.getLongitude ()));
                mMap.addMarker (new MarkerOptions ().position (point).title ("현재 위치"));
                mMap.moveCamera (CameraUpdateFactory.newLatLng (point));
                mMap.moveCamera (CameraUpdateFactory.zoomTo(17.5f));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng (-34, 151);
        mMap.addMarker (new MarkerOptions ().position (sydney).title ("현재 위치"));
        mMap.moveCamera (CameraUpdateFactory.newLatLng (sydney));
    }
};
