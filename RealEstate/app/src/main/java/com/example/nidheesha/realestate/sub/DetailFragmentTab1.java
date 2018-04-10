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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nidheesha.realestate.helper.image.TextTint;
import com.example.nidheesha.realestate.models.ListEntry;
import com.example.nidheesha.realestate.runtime.RuntimeApplication;
import com.example.nidheesha.realestate.R;

public class DetailFragmentTab1 extends Fragment
{
	private ListEntry listEntry;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.detail_tab1, container, false);
    }
	
	@Override
    public void onStart() 
    {
        super.onStart();
    }
	
	@Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }
	
	public void setListEntry(ListEntry listEntry)
	{
		this.listEntry = listEntry;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
    {
		final RuntimeApplication runtimeApp = 
    			(RuntimeApplication) this.getActivity().getApplication();
		
		int color = runtimeApp.switcher.getSelectedColorSwitcher();
		
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText(listEntry.title);
        TextTint.tintTextView(this.getActivity(), tvTitle, color);
        
        TextView tvAddress = (TextView) view.findViewById(R.id.tvAddress);
        tvAddress.setText(listEntry.address);
        
        TextView tvOfficeHours = (TextView) view.findViewById(R.id.tvOfficeHours);
        tvOfficeHours.setText(listEntry.checkInTime);
        
        TextView tvNoOfRooms = (TextView) view.findViewById(R.id.tvLabel3);
        tvNoOfRooms.setText(String.format("%s Room(s)", listEntry.noOfRooms) );
        
        TextView tvBathrooms = (TextView) view.findViewById(R.id.tvLabel5);
        tvBathrooms.setText(String.format("%s Bathroom(s)", listEntry.bathrooms) );
        
        TextView tvApartmentType = (TextView) view.findViewById(R.id.tvLabel1);
        tvApartmentType.setText(listEntry.apartmentType);
        
        TextView tvLabel1 = (TextView) view.findViewById(R.id.tvLabel7);
        TextTint.tintTextView(this.getActivity(), tvLabel1, color);
        
        TextView tvLabel2 = (TextView) view.findViewById(R.id.tvLabel2);
        TextTint.tintTextView(this.getActivity(), tvLabel2, color);
        
        TextView tvLabel3 = (TextView) view.findViewById(R.id.tvLabel4);
        TextTint.tintTextView(this.getActivity(), tvLabel3, color);
        
        TextView tvLabel4 = (TextView) view.findViewById(R.id.tvLabel6);
        TextTint.tintTextView(this.getActivity(), tvLabel4, color);
        
        if(listEntry.reviewList.size() > 0)
        {
        	TextView tvReview = (TextView) view.findViewById(R.id.tvReview);
            tvReview.setText(listEntry.reviewList.get(0).comment);
        }
        
    }

}
