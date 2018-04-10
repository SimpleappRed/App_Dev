// ================================================================================================
//
// ApartmentPro - RealEstate Android App
// Designed By: UI Designs www.uidesigns.us
// Android Project and ApartmentPro code-base is licensed to __PHILIPPINE GLOBAL OUTSOURCING__
// which allows them to publish and sell this app without attribution and royalty.
//
// However, you are not allowed to resell or redistribute it.
// You can modify this project to fit into your or your clients' project.
// Although support is included, this product provided as is. We are not legally liable for
// any misuse or damage caused by these files directly or indirectly.
//
// If you have questions or implementation issues, please don't hesitate to contact us
// at philippineoutsourcing@gmail.com.
//
// This file is exclusively distributed in the Envato Marketplaces.
// Additional license information is available in their website.
//
// Copyright 2013 UIDesigns (www.uidesigns.us). All Rights Reserved.
//
// ================================================================================================


package com.example.nidheesha.realestate.helper.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.example.nidheesha.realestate.main.MainActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationHelper 
{
	private LocationManager locationManager;
	public Location location;
	private boolean hasGpsProvider, hasNetwrokProvider;
	
	OnLocationListener mCallback;
	public static final int GPS_PROVIDER = 0;
	public static final int NETWORK_PROVIDER = 1;
	
	public interface OnLocationListener 
    {
        public void onLocationUpdated(LocationHelper helper, Location loc, int tag);
    }
	
	public void setOnLocationListener(OnLocationListener listener)
	{
		try 
		{
            mCallback = (OnLocationListener) listener;
        } catch (ClassCastException e)
        {
            throw new ClassCastException(this.toString() + " must implement OnLocationListener");
        }
	}
	
	public void getLocation(Context mContext)
	{

        if (locationManager == null) 
        {
            locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        }

        hasGpsProvider = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        hasNetwrokProvider = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (hasGpsProvider) 
        {
        	checkGPS();
        }
        
        if (hasNetwrokProvider) 
        {
        	checkNetwork();
        }
        
    }
	
	public void checkGPS()
	{ if(ActivityCompat.checkSelfPermission(null, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(null, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
	return ;
	}
		locationManager.requestLocationUpdates(
				LocationManager.GPS_PROVIDER, 0, 100, new LocationListener() {
					@Override
					public void onStatusChanged(String provider, int status, Bundle extras) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onProviderEnabled(String provider) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onProviderDisabled(String provider) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onLocationChanged(Location location) {
						// TODO Auto-generated method stub
						LocationHelper.this.location = location;
						mCallback.onLocationUpdated(LocationHelper.this, location, GPS_PROVIDER);
					}
				});



		location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	}
	
	public void checkNetwork()
	{
		if(ActivityCompat.checkSelfPermission(null, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(null, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			return ;
		}
		locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 0, 100, new LocationListener()
                {
					@Override
					public void onStatusChanged(String provider, int status, Bundle extras)
					{
						// TODO Auto-generated method stub

					}

					@Override
					public void onProviderEnabled(String provider)
					{
						// TODO Auto-generated method stub

					}

					@Override
					public void onProviderDisabled(String provider)
					{
						// TODO Auto-generated method stub

					}

					@Override
					public void onLocationChanged(Location location)
					{
						// TODO Auto-generated method stub
						LocationHelper.this.location = location;
						mCallback.onLocationUpdated(LocationHelper.this, location, GPS_PROVIDER);
					}
				});

        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	}
	
	
	public static List<Address> getAddress(Context c, Location loc)
	{
		Geocoder geocoder = new Geocoder(c, Locale.getDefault());
		List<Address> addresses = null;
		
		try 
		{
			addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return addresses;
	}
}
