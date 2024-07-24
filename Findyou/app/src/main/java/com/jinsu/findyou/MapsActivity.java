package com.jinsu.findyou;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private EditText edtLat;
    private EditText edtLon;
    private Button btnTest;

    @SuppressWarnings ("all")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_maps);

        ActivityCompat.requestPermissions (this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager ()
                .findFragmentById (R.id.map);
        mapFragment.getMapAsync (this);

        edtLat = (EditText)findViewById (R.id.edtLat);
        edtLon = (EditText)findViewById (R.id.edtLon);
        btnTest = (Button)findViewById (R.id.btnTest);

        btnTest.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                float lat = Float.parseFloat (edtLat.getText ().toString ());
                float lon = Float.parseFloat (edtLon.getText ().toString ());

                LatLng sydney = new LatLng (lat, lon);
                mMap.addMarker (new MarkerOptions ().position (sydney).title ("현재 위치"));
                mMap.moveCamera (CameraUpdateFactory.newLatLng (sydney));
                mMap.moveCamera (CameraUpdateFactory.zoomTo(17.5f));
            }
        });

        LocationManager manager = (LocationManager)getSystemService (Context.LOCATION_SERVICE);
        manager.requestLocationUpdates (LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng (-34, 151);
        mMap.addMarker (new MarkerOptions ().position (sydney).title ("현재 위치"));
        mMap.moveCamera (CameraUpdateFactory.newLatLng (sydney));
    }
//    위치값 변경
    @Override
    public void onLocationChanged(@NonNull Location location) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("UserLocation");

        double lat = location.getLatitude ();
        double lon = location.getLongitude ();

        myRef.child ("latitude").setValue (""+lat);
        myRef.child ("longitude").setValue (""+lon);


        myRef.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DO data = snapshot.getValue (DO.class);
                Toast.makeText (getApplicationContext (), "위도: " + data.getLatitude() + ", 경도" + data.getLongitude (), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }



        });

//        myRef.setValue (lat);
//        myRef.setValue (lon);
    }
}