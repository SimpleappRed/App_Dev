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


package com.example.nidheesha.realestate.fragments;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nidheesha.realestate.adapters.ListAdapter;
import com.example.nidheesha.realestate.adapters.ListAdapter.OnListAdapterListener;
import com.example.nidheesha.realestate.components.listview.CustomListView;
import com.example.nidheesha.realestate.components.listview.CustomListView.OnItemListViewListener;
import com.example.nidheesha.realestate.components.tabheader.TabHeader;
import com.example.nidheesha.realestate.components.tabheader.TabHeader.OnTabHeaderListener;
import com.example.nidheesha.realestate.components.tabheader.TabSelection;
import com.example.nidheesha.realestate.components.utilities.Utilities;
import com.example.nidheesha.realestate.components.xmlparser.XMLRequest;
import com.example.nidheesha.realestate.config.Config;
import com.example.nidheesha.realestate.config.LayoutConfig;
import com.example.nidheesha.realestate.fragments.helper.FragmentHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.nidheesha.realestate.helper.asynctask.CustomAsyncTask;
import com.example.nidheesha.realestate.helper.asynctask.CustomAsyncTask.OnAsyncTaskListener;
import com.example.nidheesha.realestate.helper.image.ImageHelper;
import com.example.nidheesha.realestate.helper.image.TextTint;
import com.example.nidheesha.realestate.helper.sort.SortHelper;
import com.example.nidheesha.realestate.cache.ImageLoader;
import com.example.nidheesha.realestate.cache.ImageLoader.OnCacheListener;
import com.example.nidheesha.realestate.models.ImageEntry;
import com.example.nidheesha.realestate.models.ListEntry;
import com.example.nidheesha.realestate.runtime.RuntimeApplication;
import com.example.nidheesha.realestate.R;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

public class Tab4Fragment extends Fragment implements OnTabHeaderListener
{
	private final int DOWNLOAD_APARTMENTS_XML_TAG = 1002;
	private final int LIST_TAG = 2001;
	
	private GoogleMap googleMap;
	private final int RQS_GooglePlayServices = 1;
	private View v = null;
	private HashMap<String, ListEntry> markers = null;
	private List<Marker> markerList = null;
	private Location myLocation = null;
	private int selectedIndex = 0;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        // Inflate the layout for this fragment
		if(container!=null)
		container.removeAllViews();
		
		if(v == null)
			v = inflater.inflate(R.layout.tab4_fragment, container, false);
		
        return v;
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
    
    
    @Override
	public void onViewCreated(View view, Bundle savedInstanceState)
    {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		final RuntimeApplication runtimeApp = new RuntimeApplication();
    			//(RuntimeApplication) this.getActivity().getApplication();
		
		int color;
		if(runtimeApp.switcher!=null)

			color= runtimeApp.switcher.getSelectedColorSwitcher();
		else color = Color.parseColor("#ffffff");
		// set the empty view colors when the list doesnt show any data.
        TextView emptyView = (TextView) this.getActivity().findViewById(R.id.tvEmpty);
        TextTint.tintTextView(this.getActivity(), emptyView, color);
        

		try
		{
			FragmentManager myFragmentManager = this.getActivity().getSupportFragmentManager();

			SupportMapFragment mySupportMapFragment =
					(SupportMapFragment)myFragmentManager.findFragmentById(R.id.googleMap);
			OnMapReadyCallback var1;

			mySupportMapFragment.getMapAsync(new OnMapReadyCallback() {
				@Override
				public void onMapReady(GoogleMap gMap) {
					googleMap = gMap;
				}
			});
			int k = getContext().getPackageManager().checkPermission(android.Manifest.permission.ACCESS_FINE_LOCATION, getContext().getPackageName());
			if(k != 0)googleMap.setMyLocationEnabled(true);
			
			
			googleMap.setOnMyLocationChangeListener(new OnMyLocationChangeListener()
			{

				@Override
				public void onMyLocationChange(Location location)
				{
					// TODO Auto-generated method stub
					myLocation = location;

					CameraUpdate zoom = CameraUpdateFactory.zoomTo(Config.MAP_ZOOM_LEVEL);
			    	googleMap.moveCamera(zoom);

					CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(myLocation.getLatitude(),
							myLocation.getLongitude()));

					googleMap.animateCamera(center);
					googleMap.setOnMyLocationChangeListener(null);
				}
			});
		}
		catch(Exception e)
		{
			Toast.makeText(this.getActivity(), "Google Play Service is missing.", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		
		
		
		String[] tabNames = {"Near", "Apartments", "Agencies"};
        TabHeader tabHeader = (TabHeader) this.getActivity().findViewById(R.id.tabHeader);
        if(tabHeader != null)
		{ tabHeader.setOnTabHeaderListener(this);
        tabHeader.setTab(tabNames, R.layout.tab_entry, R.id.btnTab, LayoutConfig.INNER_TAB_WEIGHT);
        tabHeader.setSelectedTab(1);}

        if(runtimeApp.globalEntryListAgencies!=null){
        if(runtimeApp.globalEntryListApartments.size() == 0)
        {
        	downloadApartmentsData();
        }}
        else
        {
        	if(runtimeApp.location != null)
        	{
        		tabHeader.setSelectedTab(selectedIndex);
        		if(selectedIndex <= 1)
        		{
        			setListDataTab(runtimeApp.globalEntryListApartments, selectedIndex);
        		}
        		else
        		{
        			setListDataTab(runtimeApp.globalEntryListAgencies, selectedIndex);
        		}
        	}
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
			Log.e("isGooglePlayServices", "SUCCESS");
		}
		else
		{
			GooglePlayServicesUtil.getErrorDialog(resultCode, this.getActivity(), RQS_GooglePlayServices);
		}
	}


    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }
    
    
    /* 
	 * -------------------------------------------------------------------------------------
     * Tab header delegate
     * -------------------------------------------------------------------------------------
     */
	@Override
	public void onTabHeaderCreated(TabHeader tabHeader, View v, int position)
	{
		// TODO Auto-generated method stub
		final RuntimeApplication runtimeApp = new RuntimeApplication();
    			//(RuntimeApplication) this.getActivity().getApplication();
		
		Button btnTab = (Button) v.findViewById(R.id.btnTab);
		int color = 0;
		btnTab.postInvalidate();
		
		TabSelection tabSelection = LayoutConfig.INNER_TAB_BACKGROUND[position];
		
		if(position == 0)
		{
			btnTab.setBackgroundResource(tabSelection.tabSelectedResid);
			if(runtimeApp.switcher!=null)
				color = runtimeApp.switcher.getSelectedColorSwitcher();
			
		}
		else
		{
			btnTab.setBackgroundResource(tabSelection.tabUnselectedResid);
			color = R.color.text_unselected_color;
		}
		
		TextTint.tintButtonText(Tab4Fragment.this.getActivity(), btnTab, color);
		btnTab.requestLayout();
	}
	

	@Override
	public void onTabHeaderSelected(TabHeader tabHeader, View v, int position)
	{
		final RuntimeApplication runtimeApp = new RuntimeApplication();
    			//(RuntimeApplication) this.getActivity().getApplication();
		
		selectedIndex = position;
		
		// TODO Auto-generated method stub
		for(int x = 0; x < tabHeader.selectionViews.length; x++)
		{
			View selectedView = tabHeader.selectionViews[x];
			Button btnTab = (Button) selectedView.findViewById(R.id.btnTab);
			int color = 0;
			btnTab.postInvalidate();
			
			if(position == x)
			{
				btnTab.setBackgroundResource(R.drawable.tabfilterdown);
				if(runtimeApp.switcher!=null)
					color = runtimeApp.switcher.getSelectedColorSwitcher();
			}
			else
			{
				btnTab.setBackgroundResource(R.drawable.tabfilterup);
				color = R.color.text_unselected_color;
			}
			
			TextTint.tintButtonText(Tab4Fragment.this.getActivity(), btnTab, color);
			btnTab.requestLayout();
		}
		
		if(position == 2)
		{
			setListDataTab(runtimeApp.globalEntryListAgencies, position);
		}
		else
		{
			setListDataTab(runtimeApp.globalEntryListApartments, position);
		}
		
	}
	
	
	/* 
	 * -------------------------------------------------------------------------------------
     * Download Slider Data
     * -------------------------------------------------------------------------------------
     */
	public void downloadApartmentsData()
	{
		CustomAsyncTask asyncTask = new CustomAsyncTask(this.getActivity());
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
				
			}
			
			@Override
			public void onAsyncTaskPostExecute(CustomAsyncTask customAsyncTask) 
			{
				// TODO Auto-generated method stub
				if(customAsyncTask.listTag == LIST_TAG && customAsyncTask.entryList != null)
				{
					RuntimeApplication runtimeApp = new RuntimeApplication();
							//(RuntimeApplication) Tab4Fragment.this.getActivity().getApplication();
					
					runtimeApp.globalEntryListApartments = customAsyncTask.entryList;
					runtimeApp.globalEntryListAgencies = customAsyncTask.entryListExtra;
					
					setListDataTab(customAsyncTask.entryList, 0);
				}
				
				Log.e("list count", ""+customAsyncTask.entryList.size());
			}
			
			@Override
			public void doAsyncTaskInBackground(CustomAsyncTask customAsyncTask) 
			{
				// TODO Auto-generated method stub
				if(customAsyncTask.tag == DOWNLOAD_APARTMENTS_XML_TAG)
				{
			     	try 
			     	{
			     		Boolean isServerSourceData = false;
			     		XMLRequest req = null;
			     		XMLRequest reqAgents = null;
			     		
			     		if(Config.IS_DATA_FROM_SERVER)
			     		{
			     			if(Utilities.hasConnection(Tab4Fragment.this.getActivity()))
	        				{
			     				req = new XMLRequest(Config.URL_PATH + Config.APARTMENTS_URL);
			     				reqAgents = new XMLRequest(Config.URL_PATH + Config.AGENTS_URL);
				     			isServerSourceData = true;
	        				}
			     			else
			     			{
			     				req = new XMLRequest(Config.ASSETS_FOLDER_PATH + Config.APARTMENTS_URL);
			     				reqAgents = new XMLRequest(Config.ASSETS_FOLDER_PATH + Config.AGENTS_URL);
			     			}
			     		}
			     		else
			     		{
			     			req = new XMLRequest(Config.ASSETS_FOLDER_PATH + Config.APARTMENTS_URL);
			     			reqAgents = new XMLRequest(Config.ASSETS_FOLDER_PATH + Config.AGENTS_URL);
			     		}
			     		
			     		
	        			List<ListEntry> entryList = isServerSourceData ?
								req.obtainXMLList() : 
									req.obtainXMLListFromLocal(customAsyncTask.activity);
								
        				customAsyncTask.entryList = entryList;
        				customAsyncTask.listTag = LIST_TAG;
        				
	        			List<ListEntry> entryList2 = isServerSourceData ?
	        					reqAgents.obtainXMLList() : 
	        						reqAgents.obtainXMLListFromLocal(customAsyncTask.activity);
								
        				customAsyncTask.entryListExtra = entryList2;
        				customAsyncTask.listTag = LIST_TAG;
					} 
			     	catch (ParserConfigurationException e)
					{
						e.printStackTrace();
					} 
			     	catch (SAXException e)
					{
						e.printStackTrace();
					} 
			     	catch (IOException e)
					{
						e.printStackTrace();
						Toast.makeText(customAsyncTask.activity,
								"Network Connection Error.", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		asyncTask.tag = DOWNLOAD_APARTMENTS_XML_TAG;
        asyncTask.startAsyncTask();
	}
	
	
	/* 
	 * -------------------------------------------------------------------------------------
     * Set List Data
     * -------------------------------------------------------------------------------------
     */
	public void setListDataTab(List<ListEntry> entryList, int tabIndex)
	{
		RuntimeApplication runtimeApp = new RuntimeApplication();//(RuntimeApplication) this.getActivity().getApplication();
		List<ListEntry> filteredEntryList = new ArrayList<ListEntry>();
		
		if(runtimeApp.selectedZipCodeMax == -1 && runtimeApp.selectedZipCodeMin == -1)
		{
			filteredEntryList.addAll(entryList);
		}
		else
		{
			for(ListEntry entry : entryList)
			{
				int zipCode = Integer.parseInt(entry.zipCode);
				if(runtimeApp.selectedZipCodeMin >= zipCode && zipCode <= runtimeApp.selectedZipCodeMax)
				{
					filteredEntryList.add(entry);
				}
			}
		}
		
		switch(tabIndex)
		{
			case 0:
				getNearby(filteredEntryList);
				break;
				
			case 1:
				SortHelper.sort(filteredEntryList, "title");
				initListView(filteredEntryList);
				break;
				
			case 2:
				SortHelper.sort(filteredEntryList, "title");
				getAgencies(filteredEntryList);
				break;
		}
	}
	public void getNearby(List<ListEntry> entryList)
	{
		RuntimeApplication runtimeApp = new RuntimeApplication();//(RuntimeApplication) this.getActivity().getApplication();
		List<ListEntry> filteredEntryList = new ArrayList<ListEntry>();
		
		Location loc = runtimeApp.location;
		if(loc == null)
		{
			initListView(filteredEntryList);
		}
		
		for(ListEntry entry : entryList)
		{
			Location entryLoc = new Location("Entry");
			entryLoc.setLatitude(entry.latitude);
			entryLoc.setLongitude(entry.longitude);
			
			if(loc.distanceTo(entryLoc) <= Config.MAP_RADIUS)
			{
				filteredEntryList.add(entry);
			}
		}
		
		initListView(filteredEntryList);
	}
	
	public void getAgencies(List<ListEntry> entryList)
	{
		
		initListView(entryList);
	}
	
	
	public void initListView(List<ListEntry> entryList)
	{
		final RuntimeApplication runtimeApp = new RuntimeApplication();
    			//(RuntimeApplication) this.getActivity().getApplication();
		
		final int color = runtimeApp.switcher.getSelectedColorSwitcher();
		
		CustomListView listView = (CustomListView) this.getActivity().findViewById(R.id.listView);
		listView.removeCacheColorSelectionAndDivider(Config.CACHE_COLOR_HINT);
		
		listView.setSelectionSelector(R.drawable.thumblistmapbackgrounddown);
		
		TextView emptyView = (TextView) this.getActivity().findViewById(R.id.tvEmpty);
		TextTint.tintTextView(this.getActivity(), emptyView, color);
		
		listView.setEmptyView(emptyView);
		
		ListAdapter adapter = new ListAdapter(this.getActivity(), entryList, R.layout.list_entry);
		adapter.setOnListAdapterListener(new OnListAdapterListener()
		{
			@Override
			public void onListAdapterCreated(ListAdapter adapter, View v, int position,
                                             ViewGroup viewGroup, ListEntry listEntry)
			{
				// TODO Auto-generated method stub
				if(listEntry.imageList != null && listEntry.imageList.size() > 0)
				{
					Random rand = new Random();
					int randomIndex = rand.nextInt(listEntry.imageList.size());
					
					ImageEntry imgEntry = listEntry.imageList.get(randomIndex);
					
					ImageLoader loader = new ImageLoader(
							Tab4Fragment.this.getActivity(), Config.THUMB_PLACE_HOLDER);
					
					final ImageView imgThumb = (ImageView) v.findViewById(R.id.imgThumb1);
					
					// check if the bitmap is from server 
					// or not using the flag IS_DATA_FROM_SERVER
					Boolean isHttp = imgEntry.photoImageThumbUrl.toLowerCase(Locale.getDefault()).contains("http://");
					if(isHttp)
					{
						loader.DisplayImageWithTag(imgEntry.photoImageThumbUrl, imgThumb, 1);
						loader.setOnCacheListener(new OnCacheListener() 
						{	
							@Override
							public void onImageLoaded(ImageLoader loader, Bitmap bitmap, int tag)
							{
								// TODO Auto-generated method stub
								Bitmap framedBitmap = ImageHelper.
										convertBitmapWithFrame(Tab4Fragment.this.getActivity(),
												bitmap, Config.BORDER_THICKNESS_THUMB);

							    imgThumb.setImageBitmap(framedBitmap);
							}
						});
					}
					else
					{
						Bitmap bitmap = ImageHelper.getBitmapFromAsset(
								Tab4Fragment.this.getActivity(), imgEntry.photoImageThumbUrl);
						
						if(bitmap != null)
						{
							Bitmap framedBitmap = ImageHelper.
									convertBitmapWithFrame(Tab4Fragment.this.getActivity(),
											bitmap, Config.BORDER_THICKNESS_THUMB);
							
							imgThumb.setImageBitmap(framedBitmap);
						}
					}
				}
				
				TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
				tvTitle.setText(listEntry.title);
				TextTint.tintTextView(Tab4Fragment.this.getActivity(), tvTitle, color);
				
				
				TextView tvAddress = (TextView) v.findViewById(R.id.tvAddress);
				tvAddress.setText(listEntry.address);
				
			}
		});
		
		listView.setAdapter(adapter);
		listView.setOnItemListViewListener(new OnItemListViewListener() 
		{
			@Override
			public void onItemListViewClick(AdapterView<?> adapterView, View v,
                                            int pos, long resid)
			{
				ListEntry listEntry = (ListEntry) adapterView.getAdapter().getItem(pos);
//				FragmentHelper.showDetailFragment(listEntry, Tab4Fragment.this.getActivity());
				
				CameraUpdate center= CameraUpdateFactory.newLatLng(new LatLng(listEntry.latitude + 0.004,
						listEntry.longitude));
				
			    if(googleMap.getCameraPosition().zoom == Config.MAP_ZOOM_LEVEL)
			    {
			    	googleMap.animateCamera(center);
			    }
			    else
			    {
			    	CameraUpdate zoom = CameraUpdateFactory.zoomTo(Config.MAP_ZOOM_LEVEL);
			    	googleMap.moveCamera(center);
			    	googleMap.animateCamera(zoom);
			    }
			    	
			    
			    Marker marker = markerList.get(pos);
			    marker.showInfoWindow();
			}
		});
		
		if(googleMap != null)
			googleMap.clear();
		
		markers = new HashMap<String, ListEntry>();
		markerList = new ArrayList<Marker>();
		for(ListEntry entry: entryList)
		{
			MarkerOptions markerOptions = new MarkerOptions();
			markerOptions.title(entry.title);
			markerOptions.snippet(entry.address);
			markerOptions.position(new LatLng(entry.latitude, entry.longitude));
			markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.mappointer));
			Marker mark = googleMap.addMarker(markerOptions);
			
			markerList.add(mark);
			markers.put(mark.getId(), entry);
		}
		
		/*
		 * Custom Map Integration Popup Window
		 * 
		// map marker integration
		PopUpInfoAdapter adapterInfo = new PopUpInfoAdapter(this.getActivity(), R.layout.map_popup);
		adapterInfo.setOnPopUpInfoAdapterListener(new OnPopUpInfoAdapterListener() 
		{
			@Override
			public void onPopUpInfoCreated(PopUpInfoAdapter adapter, View v,
					 Marker marker) 
			{
				// TODO Auto-generated method stub
				ListEntry listEntry = (ListEntry) markers.get(marker.getId());
				
				TextView tvTitle = (TextView)v.findViewById(R.id.tvTitle);
				tvTitle.setText(listEntry.title);
				
				TextView tvAddress = (TextView)v.findViewById(R.id.tvAddress);
				tvAddress.setText(listEntry.address);
			}
		});
		
		googleMap.setInfoWindowAdapter(adapterInfo);
		*/
		if(googleMap != null)
		{
			googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() 
			{
				@Override
				public void onInfoWindowClick(Marker marker) 
				{
					// TODO Auto-generated method stub
					ListEntry listEntry = markers.get(marker.getId());
					
					if(selectedIndex <= 1)
					{
						FragmentHelper.showDetailFragment(listEntry, getActivity());
					}
					else
					{
						FragmentHelper.showAgentFragment(listEntry, getActivity());
					}
					
					
				}
			});
		}
		
	}
	
	

}
