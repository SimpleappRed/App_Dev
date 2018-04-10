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


package com.example.nidheesha.realestate.sub;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.nidheesha.realestate.models.ListEntry;
import com.example.nidheesha.realestate.R;

public class DetailFragmentTab3 extends Fragment
{
	private ListEntry listEntry;
	private GoogleMap googleMap;
	
	final int RQS_GooglePlayServices = 1;
	private static View v = null;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        // Inflate the layout for this fragment
		if (v != null) 
		{
	        ViewGroup parent = (ViewGroup) v.getParent();
	        if (parent != null)
	            parent.removeView(v);
	    }
		
	    try 
	    {
	        v = inflater.inflate(R.layout.detail_tab3, container, false);
	    } 
	    catch (InflateException e)
	    {
	        /* map is already there, just return view as it is */
	    	e.printStackTrace();
	    }
	    return v;
    }
	
	@Override
    public void onStart() 
    {
        super.onStart();
    }
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		try
		{
			FragmentManager myFragmentManager = this.getActivity().getSupportFragmentManager();
			SupportMapFragment mySupportMapFragment = (SupportMapFragment)myFragmentManager.findFragmentById(R.id.googleMapDetail);
			 mySupportMapFragment.getMapAsync(new OnMapReadyCallback() {
				@Override
				public void onMapReady(GoogleMap gMap) {
					googleMap = gMap;
				}
			});
			
			googleMap.clear();
			MarkerOptions markerOptions = new MarkerOptions();
			markerOptions.title(listEntry.title);
			markerOptions.snippet(listEntry.address);
			markerOptions.position(new LatLng(listEntry.latitude, listEntry.longitude));
			markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.mappointer));
			
			Marker marker = googleMap.addMarker(markerOptions);
			
			CameraUpdate center= CameraUpdateFactory.newLatLng(new LatLng(listEntry.latitude,
					listEntry.longitude));
		    CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

		    googleMap.moveCamera(center);
		    googleMap.animateCamera(zoom);
		    
		    marker.showInfoWindow();
		}
		catch(Exception e)
		{
			Toast.makeText(this.getActivity(), "Google Play Service is missing.", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		
			
    }
	

	@Override
	public void onResume() 
    {
		// TODO Auto-generated method stub
		super.onResume();
		
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.getActivity());

		if (resultCode == ConnectionResult.SUCCESS)
		{
//			Toast.makeText(getActivity(), "isGooglePlayServicesAvailable SUCCESS", Toast.LENGTH_LONG).show();
			Log.e("isGooglePlayServicesAva", "SUCCESS");
		}
		else
		{
			GooglePlayServicesUtil.getErrorDialog(resultCode, this.getActivity(), RQS_GooglePlayServices);
		}
	}
	
	public void setListEntry(ListEntry listEntry)
	{
		this.listEntry = listEntry;
	}
	
	@Override
    public void onDestroyView() 
    {
        super.onDestroyView();
        if (v != null) 
        {
            ViewGroup parentViewGroup = (ViewGroup) v.getParent();
            if (parentViewGroup != null) 
            {
                parentViewGroup.removeAllViews();
            }
        }
        
    }
}
