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


package com.example.nidheesha.realestate.runtime;

import android.app.Application;
import android.location.Address;
import android.location.Location;

import com.example.nidheesha.realestate.switcher.ColorSwitcher;
import com.example.nidheesha.realestate.config.Config;
import com.example.nidheesha.realestate.models.ListEntry;

import java.util.ArrayList;
import java.util.List;

public class RuntimeApplication extends Application
{
	public ColorSwitcher switcher ;
	public List<ListEntry> globalEntryListApartments;
	public List<ListEntry> globalEntryListSlider;
	public List<ListEntry> globalEntryListAgencies;
	public List<ListEntry> globalEntryListCities;
	public ListEntry globalProfileInfo;
	
	public List<ListEntry> globalEntryListPhotos;
	public List<ListEntry> globalEntryListReviews;
	
	public Address address;
	public Location location;
	
	public int selectedZipCodeMax;
	public int selectedZipCodeMin;

	@Override
	public void onCreate() 
	{
		// TODO Auto-generated method stub
		super.onCreate();
		
		switcher = new ColorSwitcher(Config.THEME_COLOR, this.getApplicationContext());
		
		globalEntryListApartments = new ArrayList<ListEntry>();
		globalEntryListSlider = new ArrayList<ListEntry>();
		globalEntryListAgencies = new ArrayList<ListEntry>();
		globalEntryListCities = new ArrayList<ListEntry>();
		
		globalEntryListPhotos = new ArrayList<ListEntry>();
		globalEntryListReviews = new ArrayList<ListEntry>();
		
		address = null;
		location = null;
		
		selectedZipCodeMax = -1;
		selectedZipCodeMin = -1;
		
		globalProfileInfo = null;
	}
	
}
