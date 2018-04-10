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


package com.example.nidheesha.realestate.components.maps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

public class PopUpInfoAdapter implements InfoWindowAdapter
{
	public Context context;
	int resid = 0;
	OnPopUpInfoAdapterListener mCallback;
	
	public interface OnPopUpInfoAdapterListener 
    {
        public void onPopUpInfoCreated(PopUpInfoAdapter
                                               adapter, View v, Marker marker);
    }
	
	public void setOnPopUpInfoAdapterListener(OnPopUpInfoAdapterListener listener)
	{
		try 
		{
            mCallback = (OnPopUpInfoAdapterListener) listener;
        } catch (ClassCastException e)
        {
            throw new ClassCastException(this.toString() + " must implement OnListAdapterListener");
        }
	}
	
	public PopUpInfoAdapter(Context c, int resid)
	{
		this.context = c;
		this.resid = resid;
		
	}
	
	@Override
	public View getInfoContents(Marker marker)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public View getInfoWindow(Marker marker)
	{
		// TODO Auto-generated method stub
		
		LayoutInflater li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View v = li.inflate(resid, null);
		mCallback.onPopUpInfoCreated(this, v, marker);
		
		/*
	    TextView tv=(TextView)v.findViewById(R.id.title);

	    tv.setText(marker.getTitle());
	    tv=(TextView)v.findViewById(R.id.snippet);
	    tv.setText(marker.getSnippet());
	    */

	    return v;
	}
	
}
