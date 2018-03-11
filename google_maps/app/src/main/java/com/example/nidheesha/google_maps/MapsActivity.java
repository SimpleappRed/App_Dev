package com.example.nidheesha.google_maps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    EditText place,event,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        place = findViewById(R.id.place);
        event = findViewById(R.id.event);
        time = findViewById(R.id.time);

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    public void search(View v) throws IOException {
        EditText e1 = (EditText) findViewById(R.id.place);
        String s = e1.getText().toString();
        String s1 = event.getText().toString();
        String s2 = time.getText().toString();
        Geocoder geocoder = new Geocoder(this);
        List <Address> addresses = geocoder.getFromLocationName(s, 1);
        Address address = addresses.get(0);
        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
        mMap.addMarker(new MarkerOptions().position(latLng).title(s+" "+s1+" "+s2));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));


    }
    public void changemap(View v)
    {
        if (mMap.getMapType()==GoogleMap.MAP_TYPE_NORMAL)
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
        else
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }

    public void zoom(View v)
    {
        if(v.getId()==R.id.b1)
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
        else if(v.getId()==R.id.b2)
            mMap.animateCamera(CameraUpdateFactory.zoomOut());

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
// Show rationale and request permission.
        }

    }
}
