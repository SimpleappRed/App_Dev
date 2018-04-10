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

import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.example.nidheesha.realestate.components.slider.SliderAdapter;
import com.example.nidheesha.realestate.components.slider.SliderAdapter.OnSliderAdapterListener;
import com.example.nidheesha.realestate.components.slider.SliderV2;
import com.example.nidheesha.realestate.components.tabheader.TabHeader;
import com.example.nidheesha.realestate.components.tabheader.TabHeader.OnTabHeaderListener;
import com.example.nidheesha.realestate.components.tabheader.TabSelection;
import com.example.nidheesha.realestate.components.utilities.Utilities;
import com.example.nidheesha.realestate.components.xmlparser.XMLRequest;
import com.example.nidheesha.realestate.config.Config;
import com.example.nidheesha.realestate.config.LayoutConfig;
import com.example.nidheesha.realestate.fragments.helper.FragmentHelper;
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
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

public class Tab3Fragment extends Fragment implements OnTabHeaderListener
{
	private static View v = null;
	private final int DOWNLOAD_SLIDER_XML_TAG = 1001;
	private final int DOWNLOAD_APARTMENTS_XML_TAG = 1002;
	private final int LIST_TAG = 2001;
	private int selectedIndex = 1;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
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
 	        v = inflater.inflate(R.layout.tab3_fragment, container, false);
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
	public void onResume() 
    {
		// TODO Auto-generated method stub
		super.onResume();
		SliderV2 sliderV2 = (SliderV2) this.getActivity().findViewById(R.id.sliderV2);
		sliderV2.resumeSliderAnimation();
	}
	
	@Override
	public void onPause() 
    {
		// TODO Auto-generated method stub
		super.onPause();
		SliderV2 sliderV2 = (SliderV2) this.getActivity().findViewById(R.id.sliderV2);
		
		if(sliderV2 != null)
			sliderV2.pauseSliderAnimation();
	}
    
    @Override
	public void onViewCreated(View view, Bundle savedInstanceState)
    {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		final RuntimeApplication runtimeApp = new RuntimeApplication();
//    			(RuntimeApplication) this.getActivity().getApplication();
		
		final int color;
		if(runtimeApp.switcher!=null)
		color= runtimeApp.switcher.getSelectedColorSwitcher();
		else color = Color.parseColor("#ffffff");
		String[] tabNames = {"Near", "Featured", "Price"};
        TabHeader tabHeader = (TabHeader) this.getActivity().findViewById(R.id.tabHeader);
        tabHeader.setOnTabHeaderListener(this);
        tabHeader.setTab(tabNames, R.layout.tab_entry, R.id.btnTab, LayoutConfig.INNER_TAB_WEIGHT);
        
        // set the empty view colors when the list doesnt show any data.
        TextView emptyView = (TextView) this.getActivity().findViewById(R.id.tvEmpty);
        TextTint.tintTextView(this.getActivity(), emptyView, color);
        
        
        createSlider(runtimeApp.globalEntryListSlider);
        if(runtimeApp.globalEntryListApartments!=null)
        if(runtimeApp.globalEntryListApartments.size() == 0)
        {
        	downloadApartmentsData();
        }
        else
        {
        	tabHeader.setSelectedTab(selectedIndex);
        	setListDataTab(runtimeApp.globalEntryListApartments, selectedIndex);
        }   
    }

    
    @Override
    public void onDestroyView() 
    {
        super.onDestroyView();
        Log.e("onDestroyView() ", "onDestroyView()");
        
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
		
		TextTint.tintButtonText(Tab3Fragment.this.getActivity(), btnTab, color);
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
			if(runtimeApp.switcher !=null)
				color = runtimeApp.switcher.getSelectedColorSwitcher();
			}
			else
			{
				btnTab.setBackgroundResource(R.drawable.tabfilterup);
				
				color = R.color.text_unselected_color;
			}
			
			TextTint.tintButtonText(Tab3Fragment.this.getActivity(), btnTab, color);
			btnTab.requestLayout();
		}
		
		setListDataTab(runtimeApp.globalEntryListApartments, position);
	}

	
	/* 
	 * -------------------------------------------------------------------------------------
     * Slider Creation
     * -------------------------------------------------------------------------------------
     */
	public void createSlider(List<ListEntry> sliderList)
	{
		
		SliderV2 sliderV2 = (SliderV2) this.getActivity().findViewById(R.id.sliderV2);
    	SliderAdapter adapter = new SliderAdapter(R.layout.slider_entry, sliderList);
    	adapter.setOnSliderAdapterListener(new OnSliderAdapterListener() 
    	{
			@Override
			public void onSliderdapterCreated(SliderAdapter adapter, View v,
					int position, ListEntry listEntry) 
			{
				// TODO Auto-generated method stub
				
				final ListEntry mListEntry = listEntry;
				
				ImageView imgViewSlider = (ImageView) v.findViewById(R.id.imgViewSlider);
				imgViewSlider.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						// TODO Auto-generated method stub
						FragmentHelper.showDetailFragment(mListEntry, Tab3Fragment.this.getActivity());
					}
				});

				if(listEntry.imageList != null && listEntry.imageList.size() > 0)
				{
					Random rand = new Random();
					int randomIndex = rand.nextInt(listEntry.imageList.size());
					ImageEntry imgEntry = listEntry.imageList.get(randomIndex);
				
					Boolean isHttp = imgEntry.photoImageThumbUrl.toLowerCase(Locale.getDefault()).contains("http://");
					if(isHttp)
					{
						ImageLoader loader = new ImageLoader(
								Tab3Fragment.this.getActivity(), Config.THUMB_PLACE_HOLDER);
						
//						loader.DisplayImage(listEntry.imageUrl,	imgViewSlider);
						loader.DisplayImage(imgEntry.photoImageUrl,	imgViewSlider);
					}
					else
					{
						imgViewSlider.setImageBitmap(
								ImageHelper.getBitmapFromAsset(
										Tab3Fragment.this.getActivity(), imgEntry.photoImageUrl));
					}
					
					TextView tvAddress = (TextView) v.findViewById(R.id.tvAddress);
					tvAddress.setText(listEntry.address);
					
					TextView tvPrice = (TextView) v.findViewById(R.id.tvPrice);
					tvPrice.setText( String.format("%s%s/month", Config.CURRENCY, listEntry.price) );
				}
			}
		});
    	if(sliderV2!=null) {
			sliderV2.setAdapter(adapter);
			sliderV2.setActivity(this.getActivity());
			sliderV2.setSliderAnimation(3000);
		}
	}
	
	
	/* 
	 * -------------------------------------------------------------------------------------
     * Download Slider Data
     * -------------------------------------------------------------------------------------
     */
	public void downloadSliderData()
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
				RuntimeApplication runtimeApp =new RuntimeApplication(); //(RuntimeApplication)
						//Tab3Fragment.this.getActivity().getApplication();
				
		        runtimeApp.globalEntryListSlider = customAsyncTask.entryList;
				createSlider(customAsyncTask.entryList);
			}
			
			@Override
			public void doAsyncTaskInBackground(CustomAsyncTask customAsyncTask) 
			{
				// TODO Auto-generated method stub
				if(customAsyncTask.tag == DOWNLOAD_SLIDER_XML_TAG)
				{
			     	try 
			     	{
			     		Boolean isServerSourceData = false;
			     		XMLRequest req = null;
			     		if(Config.IS_DATA_FROM_SERVER)
			     		{
			     			if(Utilities.hasConnection(Tab3Fragment.this.getActivity()))
	        				{
			     				req = new XMLRequest(Config.URL_PATH + Config.SLIDER_URL);
				     			isServerSourceData = true;
	        				}
			     			else
			     			{
			     				req = new XMLRequest(Config.ASSETS_FOLDER_PATH + Config.SLIDER_URL);
			     			}
			     		}
			     		else
			     		{
			     			req = new XMLRequest(Config.ASSETS_FOLDER_PATH + Config.SLIDER_URL);
			     		}
			     		
			     		
	        			List<ListEntry> entryList = isServerSourceData ?
								req.obtainXMLList() : 
									req.obtainXMLListFromLocal(customAsyncTask.activity);
								
        				customAsyncTask.entryList = entryList;
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
        asyncTask.tag = DOWNLOAD_SLIDER_XML_TAG;
        asyncTask.startAsyncTask();
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
					setListDataTab(customAsyncTask.entryList, selectedIndex);
					
					RuntimeApplication runtimeApp =new RuntimeApplication(); //(RuntimeApplication)
							//Tab3Fragment.this.getActivity().getApplication();
					
					runtimeApp.globalEntryListApartments = customAsyncTask.entryList;
					runtimeApp.globalEntryListAgencies = customAsyncTask.entryListExtra;
				}
				
				Log.e("list count", ""+customAsyncTask.entryList.size());
			}
			
			@Override
			public void doAsyncTaskInBackground(CustomAsyncTask customAsyncTask) 
			{
				// TODO Auto-generated method stub
				if(customAsyncTask.tag == DOWNLOAD_APARTMENTS_XML_TAG)
				{
					XMLRequest req = new XMLRequest(Config.APARTMENTS_URL);
					XMLRequest reqAgents = new XMLRequest(Config.AGENTS_URL);
					
			     	try 
			     	{
						List<ListEntry> entryList = req.obtainXMLList();
						customAsyncTask.entryList = entryList;
						customAsyncTask.listTag = LIST_TAG;
						
						List<ListEntry> entryList2 = reqAgents.obtainXMLList();
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
					}
				}
			}
		});
		asyncTask.tag = DOWNLOAD_APARTMENTS_XML_TAG;
        asyncTask.startAsyncTask();
	}
	
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
				getFeatured(filteredEntryList);
				break;
				
			case 2:
				SortHelper.sort(filteredEntryList, "price");
				initListView(filteredEntryList);
				break;
		}
	}
	
	public void getNearby(List<ListEntry> entryList)
	{
		RuntimeApplication runtimeApp = new RuntimeApplication();//(RuntimeApplication) this.getActivity().getApplication();
		List<ListEntry> filteredEntryList = new ArrayList<ListEntry>();
		
		if(runtimeApp.location == null)
		{
			initListView(filteredEntryList);
			return;
		}
		
		for(ListEntry entry : entryList)
		{
			Location loc = runtimeApp.location;
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
	
	public void getFeatured(List<ListEntry> entryList)
	{
		List<ListEntry> filteredEntryList = new ArrayList<ListEntry>();
		
		for(ListEntry entry : entryList)
		{
			if(entry.featured == 1)
			{
				filteredEntryList.add(entry);
			}
		}
		initListView(filteredEntryList);
	}
	
	
	public void initListView(List<ListEntry> entryList)
	{
		final RuntimeApplication runtimeApp =  new RuntimeApplication();
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
					ImageLoader loader = new ImageLoader(Tab3Fragment.this.getActivity(), Config.THUMB_PLACE_HOLDER);
					
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
										convertBitmapWithFrame(Tab3Fragment.this.getActivity(), 
												bitmap, Config.BORDER_THICKNESS_THUMB);

							    imgThumb.setImageBitmap(framedBitmap);
							}
						});
					}
					else
					{
						Bitmap bitmap = ImageHelper.getBitmapFromAsset(
								Tab3Fragment.this.getActivity(), imgEntry.photoImageThumbUrl);
						
						if(bitmap != null)
						{
							Bitmap framedBitmap = ImageHelper.
									convertBitmapWithFrame(Tab3Fragment.this.getActivity(), bitmap, Config.BORDER_THICKNESS_THUMB);
							
							imgThumb.setImageBitmap(framedBitmap);
						}
						
					}
				}
				
				TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
				tvTitle.setText(listEntry.title);
				
				TextTint.tintTextView(Tab3Fragment.this.getActivity(), tvTitle, color);
				
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
				// TODO Auto-generated method stub
				Log.e("", ""+pos);
				
				ListEntry listEntry = (ListEntry) adapterView.getAdapter().getItem(pos);
				FragmentHelper.showDetailFragment(listEntry, Tab3Fragment.this.getActivity());
			}
		});
	}

}
