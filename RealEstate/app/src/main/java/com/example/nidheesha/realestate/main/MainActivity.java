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


package com.example.nidheesha.realestate.main;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.Window;

import com.example.nidheesha.realestate.config.Config;
import com.example.nidheesha.realestate.fragments.BaseFragment;
import com.example.nidheesha.realestate.fragments.OpeningFragment;
import com.example.nidheesha.realestate.fragments.helper.FragmentHelper;
import com.example.nidheesha.realestate.helper.asynctask.CustomAsyncTask;
import com.example.nidheesha.realestate.helper.asynctask.CustomAsyncTask.OnAsyncTaskListener;
import com.example.nidheesha.realestate.helper.location.LocationHelper;
import com.example.nidheesha.realestate.helper.location.LocationHelper.OnLocationListener;
import com.example.nidheesha.realestate.runtime.RuntimeApplication;
import android.app.Application;
import com.example.nidheesha.realestate.R;

import java.util.List;

//import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity
{
	OpeningFragment openingFragment = null;
	public BaseFragment baseFragment;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_main);
		
		downloadLocationData();

		if(Config.IS_OPENING_VIEW_SHOWN)
			showOpeningFragment();
		else
			showBaseFragment(false);
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	/* 
	 * -------------------------------------------------------------------------------------
     * Show Opening Fragment
     * -------------------------------------------------------------------------------------
     */
	public void showOpeningFragment()
	{
		FragmentHelper.cleanBackStack(this);
		
//		 Create an instance of ExampleFragment
		if(openingFragment == null)
			openingFragment = new OpeningFragment();

        // In case this activity was started with special instructions from an Intent,
        // pass the Intent's extras to the fragment as arguments
		if(openingFragment == null)
			openingFragment.setArguments(getIntent().getExtras());

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragmentContainer view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragmentMainContainerWrapper, openingFragment);
        openingFragment.setRetainInstance(true);

        // adding transition
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        // Commit the transaction
        transaction.commit();
		Log.d("Tag","#######################################################################");
	}
	
	
	/* 
	 * -------------------------------------------------------------------------------------
     * Show Base Fragment
     * -------------------------------------------------------------------------------------
     */
	public void showBaseFragment(Boolean isSearching)
	{
		// Create an instance of ExampleFragment
		if(baseFragment == null)
			baseFragment = new BaseFragment();

        // In case this activity was started with special instructions from an Intent,
        // pass the Intent's extras to the fragment as arguments
		if(baseFragment == null)
			baseFragment.setArguments(this.getIntent().getExtras());
		
		if(isSearching)
        	baseFragment.isSearching = true;

        FragmentTransaction transaction = this
        		.getSupportFragmentManager().beginTransaction();
       
        // Replace whatever is in the fragmentContainer view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragmentMainContainerWrapper, baseFragment);
        baseFragment.setRetainInstance(true);
//        transaction.addToBackStack(null);
        
        // adding transition
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        // Commit the transaction
        transaction.commit();
        
        
	}
	

	/* 
	 * -------------------------------------------------------------------------------------
     * Get Location Data handler
     * -------------------------------------------------------------------------------------
     */
	public void getLocation()
    { int k  = getBaseContext().getPackageManager().checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION,getBaseContext().getPackageName());
    if( k!=0){
    	LocationHelper helper = new LocationHelper();
		helper.setOnLocationListener(new OnLocationListener() 
		{
			@Override
			public void onLocationUpdated(LocationHelper helper, Location loc, int tag)
			{
				// TODO Auto-generated method stub
				RuntimeApplication runtimeApp = new RuntimeApplication();
//						(RuntimeApplication) MainActivity.this.getApplication();
//				Application runtimeApp = (Application)
//						MainActivity.this.getApplication();
//				RuntimeApplication runtimeApp = (RuntimeApplication)runtime;
				runtimeApp.location = loc;


				List<Address> addressList = LocationHelper.getAddress(MainActivity.this, loc);

				if(addressList != null)
				{
					if(addressList.get(0) != null)
    				{
    					runtimeApp.address = addressList.get(0);

        				Log.e("lat = "+loc.getLatitude(), "long = "+loc.getLongitude());
        				Log.e("postal code = " + runtimeApp.address.getPostalCode(),
        						"Country Code = " + runtimeApp.address.getCountryCode());
    				}
				}


			}
		});
		helper.getLocation(MainActivity.this);
    }}
	
	
	/* 
	 * -------------------------------------------------------------------------------------
     * Download Location Data
     * -------------------------------------------------------------------------------------
     */
	public void downloadLocationData()
	{
		CustomAsyncTask asyncTask = new CustomAsyncTask(this);
        asyncTask.setAsyncTaskListener(new OnAsyncTaskListener() 
        {
			@Override
			public void onAsyncTaskProgressUpdate(CustomAsyncTask customAsyncTask) 
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAsyncTaskPreExecute(CustomAsyncTask customAsyncTask) 
			{
				// TODO Auto-generated method stub
				customAsyncTask.dialog.hide();
			}
			
			@Override
			public void onAsyncTaskPostExecute(CustomAsyncTask customAsyncTask) 
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void doAsyncTaskInBackground(CustomAsyncTask customAsyncTask) 
			{
				// TODO Auto-generated method stub
				getLocation();
				
			}
		});
        asyncTask.startAsyncTask();
        
        this.runOnUiThread(new Runnable()
        {
            public void run() 
            {
            	getLocation();
            }
        });
	}

	
	@Override
	protected void onDestroy() 
	{
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	
}
