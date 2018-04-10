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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.example.nidheesha.realestate.helper.asynctask.CustomAsyncTask;
import com.example.nidheesha.realestate.helper.asynctask.CustomAsyncTask.OnAsyncTaskListener;
import com.example.nidheesha.realestate.helper.image.ImageHelper;
import com.example.nidheesha.realestate.helper.image.TextTint;
import com.example.nidheesha.realestate.helper.sort.SortHelper;
import com.example.nidheesha.realestate.cache.ImageLoader;
import com.example.nidheesha.realestate.cache.ImageLoader.OnCacheListener;
import com.example.nidheesha.realestate.models.ImageEntry;
import com.example.nidheesha.realestate.models.ListEntry;
import com.example.nidheesha.realestate.models.ReviewEntry;
import com.example.nidheesha.realestate.runtime.RuntimeApplication;
import com.example.nidheesha.realestate.main.MainActivity;
import com.example.nidheesha.realestate.R;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

public class Tab1Fragment extends Fragment implements OnTabHeaderListener
{
	final int DOWNLOAD_XML_TAG = 1001;
	final int LIST_TAG = 2001;
	private int selectedIndex = 1;
	private List<ListEntry> entryList = null;
	public int searchTag = 0;
	public HashMap<String, String> searchHashMap = null;
	public Boolean isSearching = false;
	public BaseFragment baseFragment = null;
	View viewTab1 = null;
	public Boolean isTypedSomeText = false;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
	{
        // Inflate the layout for this fragment
		container.removeAllViews();
		
		if(viewTab1 == null)
		{
			viewTab1 = inflater.inflate(R.layout.tab1_fragment, container, false);
			
		}

        return viewTab1;
    }
	
	@Override
    public void onDestroyView() 
    {
        super.onDestroyView();
        if (viewTab1 != null) 
        {
            ViewGroup parentViewGroup = (ViewGroup) viewTab1.getParent();
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
    	
    	if(entryList == null)
			entryList = new ArrayList<ListEntry>();
    	
    	RuntimeApplication runtimeApp = new RuntimeApplication();
				//(RuntimeApplication)
    			//this.getActivity().getApplication();
        int color;
		if(runtimeApp.switcher!= null)
        color = runtimeApp.switcher.getSelectedColorSwitcher();
        else color = Color.parseColor("#ffffff");
	//    	Button btnSearchFilter = (Button) this.getActivity().findViewById(R.id.btnSearchFilter);
	//    	TextTint.tintButtonText(this.getActivity(), btnSearchFilter, color);
    	
        String[] tabNames = {"Apartments","Lands"};
        
        TabHeader tabHeader = (TabHeader) this.getActivity().findViewById(R.id.tabHeader);
        tabHeader.setOnTabHeaderListener(this);
        tabHeader.setTab(tabNames, R.layout.tab_entry, R.id.btnTab, LayoutConfig.INNER_TAB_WEIGHT);
        
        
        
        // set the empty view colors when the list doesnt show any data.
        TextView emptyView = (TextView) this.getActivity().findViewById(R.id.tvEmpty);
        TextTint.tintTextView(Tab1Fragment.this.getActivity(), emptyView, color);
        

        
        if(searchTag == 1)
        {
        	searchTag = 0;
        	
        	MainActivity mainActivity = (MainActivity) this.getActivity();
        	mainActivity.baseFragment.setTabFromBaseFragment(0);
        	
        	if(searchHashMap != null)
        		setSearchData(searchHashMap);
        }
        else
        {if(runtimeApp.globalEntryListAgencies !=null)
            if(runtimeApp.globalEntryListApartments.size() == 0)
            {
            	beginAsyncTask();
            }
            else
            {
            	
            	// disabled temporarily for searching purpose 
            	// to prevent reassigning content on the entryList
            	
            	entryList = runtimeApp.globalEntryListApartments;
            	tabHeader.setSelectedTab(selectedIndex);
        		setListDataTab(entryList, selectedIndex);
            	
            }
        }
        
        isTypedSomeText = false;
        localSearch();
    }
    
    
    public void selectFooterTabIndex(int index)
    {
    	TabHeader tabHeader = (TabHeader) this.getActivity().findViewById(R.id.tabHeader);
    	tabHeader.setSelectedTab(index);
    }
    
    public void localSearch()
    {
    	final EditText txtSearch = (EditText) viewTab1.findViewById(R.id.txtSearch);
    	txtSearch.addTextChangedListener(new TextWatcher()
    	{	
    		@Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
    		{
                // TODO Auto-generated method stub
    			if(isTypedSomeText)
    				search(txtSearch.getText().toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                // TODO Auto-generated method stub
            	if(txtSearch.getText().toString().length() > 0)
            	{
            		isTypedSomeText = true;
            	}
            }
		});
    }
    
    
    public void search(String input)
    {
    	EditText txtSearch = (EditText) viewTab1.findViewById(R.id.txtSearch);
    	
    	RuntimeApplication runtimeApp = new RuntimeApplication();//(RuntimeApplication) this.getActivity().getApplication();
    	List<ListEntry> listApartments = runtimeApp.globalEntryListApartments;
    	
    	List<ListEntry> entryList1 = null;
    	
    	if(entryList1 == null)
    		entryList1 = new ArrayList<ListEntry>();
		
    	entryList1.clear();
    	
    	if(input.length() > 1)
        {
    		for(int x = 0; listApartments!=null&& x < listApartments.size() ; x++)
		    {
				ListEntry listEntry = listApartments.get(x);
				
    			if( checkIfExistInWord(listEntry.title, input) || 
	            		checkIfExistInWord(listEntry.address, input) )
	            {
	            	entryList1.add(listEntry);
	            }
		    }
        }
    	if(input.length() <= 1 && txtSearch.isFocused())
        {
//    		entryList1 = entryList;
    		entryList1 = listApartments;
        }
    	
    	
    	entryList = entryList1;
    	setListDataTab(entryList, selectedIndex);
    }
    
    public void beginAsyncTask()
    {
    	CustomAsyncTask asyncTask = new CustomAsyncTask(this.getActivity());
        asyncTask.setAsyncTaskListener(new OnAsyncTaskListener() 
        {	
        	@Override
        	public void doAsyncTaskInBackground(CustomAsyncTask customAsyncTask) 
        	{
        		// TODO Auto-generated method stub
        		if(customAsyncTask.tag == DOWNLOAD_XML_TAG)
        		{
        			XMLRequest req = null;
        			XMLRequest reqAgents = null;
					XMLRequest reqCity = null;
					XMLRequest reqProfile = null;
					XMLRequest reqSlider = null;
					XMLRequest reqPhotos = null;
					XMLRequest reqReviews = null;
					
					Boolean isServerSourceData = false;
        			if(Config.IS_DATA_FROM_SERVER)
        			{
        				if(Utilities.hasConnection(Tab1Fragment.this.getActivity()))
        				{
        					req = new XMLRequest(Config.URL_PATH + Config.APARTMENTS_URL);
            				reqAgents = new XMLRequest(Config.URL_PATH + Config.AGENTS_URL);
            				reqCity = new XMLRequest(Config.URL_PATH + Config.CITY_URL);
            				reqProfile = new XMLRequest(Config.URL_PATH + Config.PROFILE_URL);
            				reqSlider = new XMLRequest(Config.URL_PATH + Config.SLIDER_URL);
            				reqPhotos = new XMLRequest(Config.URL_PATH + Config.PHOTOS_URL);
            				reqReviews = new XMLRequest(Config.URL_PATH + Config.REVIEWS_URL);
            				isServerSourceData = true;
        				}
        				else
        				{
        					req = new XMLRequest(Config.ASSETS_FOLDER_PATH + Config.APARTMENTS_URL);
            				reqAgents = new XMLRequest(Config.ASSETS_FOLDER_PATH + Config.AGENTS_URL);
            				reqCity = new XMLRequest(Config.ASSETS_FOLDER_PATH + Config.CITY_URL);
            				reqProfile = new XMLRequest(Config.ASSETS_FOLDER_PATH + Config.PROFILE_URL);
            				reqSlider = new XMLRequest(Config.ASSETS_FOLDER_PATH + Config.SLIDER_URL);
            				
            				reqPhotos = new XMLRequest(Config.ASSETS_FOLDER_PATH + Config.PHOTOS_URL);
            				reqReviews = new XMLRequest(Config.ASSETS_FOLDER_PATH + Config.REVIEWS_URL);
        				}
        				
        			}
        			else
        			{
        				req = new XMLRequest(Config.ASSETS_FOLDER_PATH + Config.APARTMENTS_URL);
        				reqAgents = new XMLRequest(Config.ASSETS_FOLDER_PATH + Config.AGENTS_URL);
        				reqCity = new XMLRequest(Config.ASSETS_FOLDER_PATH + Config.CITY_URL);
        				reqProfile = new XMLRequest(Config.ASSETS_FOLDER_PATH + Config.PROFILE_URL);
        				reqSlider = new XMLRequest(Config.ASSETS_FOLDER_PATH + Config.SLIDER_URL);
        				
        				reqPhotos = new XMLRequest(Config.ASSETS_FOLDER_PATH + Config.PHOTOS_URL);
        				reqReviews = new XMLRequest(Config.ASSETS_FOLDER_PATH + Config.REVIEWS_URL);
        			}
        			
        			
        	     	try 
        	     	{
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
        				
        				
        				List<ListEntry> entryList3 = isServerSourceData ?
        						reqCity.obtainXMLList() : 
        							reqCity.obtainXMLListFromLocal(customAsyncTask.activity);
								
        				customAsyncTask.entryListExtraInfo = entryList3;
        				customAsyncTask.listTag = LIST_TAG;
        				
        				
        				List<ListEntry> entryList4 = isServerSourceData ?
        						reqProfile.obtainXMLList() : 
        							reqProfile.obtainXMLListFromLocal(customAsyncTask.activity);
								
        				customAsyncTask.entryListExtra1 = entryList4;
        				customAsyncTask.listTag = LIST_TAG;
        				
        				List<ListEntry> entryList5 = isServerSourceData ?
        						reqSlider.obtainXMLList() : 
        							reqSlider.obtainXMLListFromLocal(customAsyncTask.activity);
								
        				customAsyncTask.entryListExtra2 = entryList5;
        				customAsyncTask.listTag = LIST_TAG;
        				
        				
        				List<ListEntry> entryList6 = isServerSourceData ?
        						reqPhotos.obtainXMLList() : 
        							reqPhotos.obtainXMLListFromLocal(customAsyncTask.activity);
								
        				customAsyncTask.entryListExtra3 = entryList6;
        				customAsyncTask.listTag = LIST_TAG;
        				
        				List<ListEntry> entryList7 = isServerSourceData ?
        						reqReviews.obtainXMLList() : 
        							reqReviews.obtainXMLListFromLocal(customAsyncTask.activity);
								
        				customAsyncTask.entryListExtra4 = entryList7;
        				customAsyncTask.listTag = LIST_TAG;
        				
        				
        				for(ListEntry entry : entryList)
        				{
        					List<ImageEntry> imageList = new ArrayList<ImageEntry>();
        					for(ListEntry entryPhotos : entryList6)
            				{
            					if(entry.id == entryPhotos.apartmentId)
            					{
            						ImageEntry imgEntry = new ImageEntry();
            						imgEntry.photoCoverUrl = entryPhotos.imageUrl;
            						imgEntry.photoImageUrl = entryPhotos.imageUrl;
            						imgEntry.photoImageThumbUrl = entryPhotos.imageThumbUrl;
            						
            						imageList.add(imgEntry);
            					}
            				}
        					entry.imageList = imageList;
        					
        					List<ReviewEntry> reviewList = new ArrayList<ReviewEntry>();
        					for(ListEntry entryReviews : entryList7)
            				{
        						if(entry.id == entryReviews.apartmentId)
            					{
        							ReviewEntry reviewEntry = new ReviewEntry();
                					reviewEntry.reviewName = entryReviews.title;
                					reviewEntry.reviewImageThumbUrl = entryReviews.imageThumbUrl;
                					reviewEntry.comment = entryReviews.reviews;
                					reviewEntry.commentDate = entryReviews.date;
                					
                					
                					reviewList.add(reviewEntry);
            					}
            					
            				}
        					entry.reviewList = reviewList;
        				}
        				
        				entryList5.clear();
        				
        				for(ListEntry entry : entryList)
        				{
        					if(entry.featured == 1)
        					{
        						entryList5.add(entry);
        					}
        				}
        				customAsyncTask.entryList = entryList;
        				customAsyncTask.entryListExtra2 = entryList5;
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
        	

        	@Override
        	public void onAsyncTaskProgressUpdate(CustomAsyncTask customAsyncTask) 
        	{
        		// TODO Auto-generated method stub
        		
        	}

        	
        	@Override
        	public void onAsyncTaskPostExecute(CustomAsyncTask customAsyncTask) 
        	{
        		// TODO Auto-generated method stub
        		if(customAsyncTask.listTag == LIST_TAG && customAsyncTask.entryList != null)
        		{
        			try
        			{
        				RuntimeApplication runtimeApp = new RuntimeApplication();//(RuntimeApplication)
            					//Tab1Fragment.this.getActivity().getApplication();
            			
            			runtimeApp.globalEntryListApartments = customAsyncTask.entryList;
            			runtimeApp.globalEntryListAgencies = customAsyncTask.entryListExtra;
            			runtimeApp.globalEntryListCities = customAsyncTask.entryListExtraInfo;
            			runtimeApp.globalProfileInfo = customAsyncTask.entryListExtra1.get(0);
            			runtimeApp.globalEntryListSlider = customAsyncTask.entryListExtra2;
            			
            			entryList = runtimeApp.globalEntryListApartments;
            			
            			runtimeApp.globalEntryListPhotos = customAsyncTask.entryListExtra3;
            			runtimeApp.globalEntryListReviews = customAsyncTask.entryListExtra4;
            			
            			Log.e("CAsyTask.entryListsize(", "size = "+customAsyncTask.entryList.size());
            			
            			if(isSearching)
            			{
            				isSearching = false;
            				FragmentHelper.showTab1Fragment(Tab1Fragment.this.getActivity());
            			}
            			else
            			{
            				setListDataTab(entryList, selectedIndex);
            				TabHeader tabHeader = (TabHeader) Tab1Fragment.this.getActivity().findViewById(R.id.tabHeader);
            				tabHeader.setSelectedTab(selectedIndex);
            			}
        			}
        			catch(Exception e)
        			{
        				
        			}
        			
        		}
        		
//        		getLocation();
        		
        	}
        	
        	@Override
        	public void onAsyncTaskPreExecute(CustomAsyncTask customAsyncTask) 
        	{
        		// TODO Auto-generated method stub
        		
        	}
        	
		});
        
        asyncTask.tag = DOWNLOAD_XML_TAG;
        asyncTask.startAsyncTask();
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
		RuntimeApplication runtimeApp = new RuntimeApplication();//(RuntimeApplication) this.getActivity().getApplication();
		
		Button btnTab = (Button) v.findViewById(R.id.btnTab);
		int color = 0;
		btnTab.postInvalidate();
		
		TabSelection tabSelection = LayoutConfig.INNER_TAB_BACKGROUND[position];
		
		if(position == 0)
		{
			btnTab.setBackgroundResource(tabSelection.tabSelectedResid);
			if(runtimeApp.switcher !=null)
			color = runtimeApp.switcher.getSelectedColorSwitcher();
		}
		else
		{
			btnTab.setBackgroundResource(tabSelection.tabUnselectedResid);
			color = R.color.text_unselected_color;
		}
		
		TextTint.tintButtonText(Tab1Fragment.this.getActivity(), btnTab, color);
		btnTab.requestLayout();
	}
	

	@Override
	public void onTabHeaderSelected(TabHeader tabHeader, View v, int position)
	{
		RuntimeApplication runtimeApp = new RuntimeApplication();//(RuntimeApplication) this.getActivity().getApplication();
		
		selectedIndex = position;
		
		// TODO Auto-generated method stub
		for(int x = 0; x < tabHeader.selectionViews.length; x++)
		{
			View selectedView = tabHeader.selectionViews[x];
			Button btnTab = (Button) selectedView.findViewById(R.id.btnTab);
			int color = 0;
			btnTab.postInvalidate();
			
			TabSelection tabSelection = LayoutConfig.INNER_TAB_BACKGROUND[x];
			
			if(position == x)
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
			
			TextTint.tintButtonText(Tab1Fragment.this.getActivity(), btnTab, color);
			btnTab.requestLayout();
		}
		
		setListDataTab(entryList, position);
		
		
	}
	
	
	/* 
	 * -------------------------------------------------------------------------------------
     * Set List Data Tab
     * -------------------------------------------------------------------------------------
     */
	public void setListDataTab(List<ListEntry> entryList, int tabIndex)
	{
		RuntimeApplication runtimeApp = new RuntimeApplication();//(RuntimeApplication) this.getActivity().getApplication();
//		List<ListEntry> filteredEntryList = new ArrayList<ListEntry>();
		List<ListEntry> newEntryList = new ArrayList<ListEntry>();
		
		
		for(ListEntry entry : entryList)
		{
			int zipCode = 0;
			
			try
			{
				zipCode = Integer.parseInt(entry.zipCode);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			if(runtimeApp.selectedZipCodeMax == -1 && runtimeApp.selectedZipCodeMin == -1)
			{
				newEntryList.add(entry);
				continue;
			}
			
			if(runtimeApp.selectedZipCodeMin <= zipCode && zipCode <= runtimeApp.selectedZipCodeMax)
			{
				newEntryList.add(entry);
			}
		}
		
		switch(tabIndex)
		{
			case 0:
				SortHelper.sort(newEntryList, "title");
				getNearby(newEntryList);
				break;
				
			case 1:
				SortHelper.sort(newEntryList, "title");
				getFeatured(newEntryList);
				break;
				
			case 2:
				SortHelper.sort(newEntryList, "price");
				initListView(newEntryList);
				break;
		}
	
	
	}
	
	
	/* 
	 * -------------------------------------------------------------------------------------
     * Get Nearby
     * -------------------------------------------------------------------------------------
     */
	public void getNearby(List<ListEntry> entryList)
	{
		RuntimeApplication runtimeApp = new RuntimeApplication();//(RuntimeApplication) this.getActivity().getApplication();
		List<ListEntry> filteredEntryList = new ArrayList<ListEntry>();
		
		for(ListEntry entry : entryList)
		{
			Location loc = runtimeApp.location;
			Location entryLoc = new Location("Entry");
			entryLoc.setLatitude(entry.latitude);
			entryLoc.setLongitude(entry.longitude);
			
			if(loc == null)
				break;
			
			if(loc.distanceTo(entryLoc) <= Config.MAP_RADIUS)
			{
				filteredEntryList.add(entry);
			}
		}
		initListView(filteredEntryList);
	}
	
	
	/* 
	 * -------------------------------------------------------------------------------------
     * Get Featured
     * -------------------------------------------------------------------------------------
     */
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
	
	
	/* 
	 * -------------------------------------------------------------------------------------
     * Init List View
     * -------------------------------------------------------------------------------------
     */
	public void initListView(List<ListEntry> entryList)
	{
		Log.e("initListView()", "size = "+entryList.size());
		
		final RuntimeApplication runtimeApp = new RuntimeApplication();
				//(RuntimeApplication) this.getActivity().getApplication();
		
		final int color;
		if(runtimeApp.switcher!=null)
			color= runtimeApp.switcher.getSelectedColorSwitcher();
		else color = Color.parseColor("#ffffff");
		CustomListView listView = (CustomListView) this.getActivity().findViewById(R.id.listViewTab1);
		listView.removeCacheColorSelectionAndDivider(Config.CACHE_COLOR_HINT);
		
		TextView emptyView = (TextView) this.getActivity().findViewById(R.id.tvEmpty);
		TextTint.tintTextView(Tab1Fragment.this.getActivity(), emptyView, color);
		listView.setEmptyView(emptyView);
		
		listView.setSelectionSelector(R.drawable.thumblistmapbackgrounddown);
		
		final ListAdapter adapter = new ListAdapter(this.getActivity(), entryList, R.layout.list_entry);
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
							Tab1Fragment.this.getActivity(), Config.THUMB_PLACE_HOLDER);
					
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
										convertBitmapWithFrame(Tab1Fragment.this.getActivity(),
												bitmap, Config.BORDER_THICKNESS_THUMB);

							    imgThumb.setImageBitmap(framedBitmap);
							}
						});
					}
					else
					{
						Bitmap bitmap = ImageHelper.getBitmapFromAsset(
								Tab1Fragment.this.getActivity(), imgEntry.photoImageThumbUrl);
						
						if(bitmap != null)
						{
							Bitmap framedBitmap = ImageHelper.
									convertBitmapWithFrame(Tab1Fragment.this.getActivity(),
											bitmap, Config.BORDER_THICKNESS_THUMB);
							
							imgThumb.setImageBitmap(framedBitmap);
						}
						
						
					}
					
				}
				
				TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
				tvTitle.setText(listEntry.title);
				TextTint.tintTextView(Tab1Fragment.this.getActivity(), tvTitle, color);
				
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
				Log.e("onItemListViewClick()", "pos = "+pos);
				
				ListEntry listEntry = (ListEntry) adapterView.getAdapter().getItem(pos);
				FragmentHelper.showDetailFragment(listEntry, Tab1Fragment.this.getActivity());
			}
		});
		
		adapter.notifyDataSetChanged();
    	adapter.notifyDataSetInvalidated();
    	
    	if(baseFragment != null)
    	{
    		// avoid endless showing of search fragment by setting baseFragment to null
    		baseFragment.showFragmentSearch();
    		baseFragment = null;
    	}
    	
//    	MainActivity mainActivity = (MainActivity) this.getActivity();
//    	mainActivity.baseFragment.setTabFromBaseFragment(0);
	}
	
	
	/* 
	 * -------------------------------------------------------------------------------------
     * Set Search Data
     * -------------------------------------------------------------------------------------
     */
	public void setSearchData(HashMap<String, String> searchHashMap)
	{
		String keyword = searchHashMap.get("keywordToSearch");
		String priceMin = searchHashMap.get("priceMin");
		String priceMax = searchHashMap.get("priceMax");
		String zipCode = searchHashMap.get("zipCode");
		String bathrooms = searchHashMap.get("bathrooms");
		String apartmentType = searchHashMap.get("apartmentType");
		String bedroom = searchHashMap.get("bedrooms");
		String city = searchHashMap.get("city");
	    
	    int countParams = 0;
	    
	    countParams += keyword.length() > 0 ? 1 : 0;
	    countParams += priceMin.length() > 0 && priceMax.length() > 0 ? 1 : 0;
	    countParams += zipCode.length() > 0 ? 1 : 0;
	    countParams += bathrooms.length() > 0 ? 1 : 0;
	    countParams += apartmentType.length() > 0 ? 1 : 0;
	    countParams += bedroom.length() > 0 ? 1 : 0;
	    countParams += city.length() > 0 ? 1 : 0;
	    
	    entryList = new ArrayList<ListEntry>();
	    
	    RuntimeApplication runtimeApp = new RuntimeApplication();//(RuntimeApplication) this.getActivity().getApplication();

	    for(ListEntry listEntry : runtimeApp.globalEntryListApartments)
	    {
	        int countCandidate = 0;
	        
	        if(keyword.length() > 0)
	        {
	            if( checkIfExistInWord(listEntry.title, keyword) || checkIfExistInWord(listEntry.address, keyword) )
	                countCandidate += 1;
	        }
	        if(priceMin.length() > 0 && priceMax.length() > 0)
	        {
	            String strPrice = listEntry.price;
	            strPrice = strPrice.replace(",", "");
	            strPrice = strPrice.replace(" ", "");
	            strPrice = strPrice.replace("$", "");
	           
	            float price = Float.parseFloat(strPrice);
	            
	            if( Float.parseFloat(priceMin) <= price && price <= Float.parseFloat(priceMax) )
	                countCandidate += 1;
	        }
	        if(zipCode.length() > 0)
	        {
	            if( checkIfExistInWord(listEntry.zipCode, zipCode) )
	                countCandidate += 1;
	        }
	        
	        if(bathrooms.length() > 0)
	        {
	            if( Integer.parseInt(listEntry.bathrooms) == Integer.parseInt(bathrooms) )
	                countCandidate += 1;
	        }
	        if(apartmentType.length() > 0)
	        {
	            if(apartmentType.compareToIgnoreCase("All Homes") == 0)
	            {
	                countCandidate += 1;
	            }
	            else if( checkIfExistInWord(listEntry.apartmentType, apartmentType) )
	                countCandidate += 1;
	        }
	        if(bedroom.length() > 0)
	        {
	            if( Integer.parseInt(listEntry.noOfRooms) == Integer.parseInt(bedroom) )
	                countCandidate += 1;
	        }
	        
	        if(city.length() > 0)
	        {
	        	if(city.compareToIgnoreCase("All Cities") == 0)
	            {
	                countCandidate += 1;
	            }
	            else if( checkIfExistInWord(listEntry.address, city) )
	                countCandidate += 1;
	        	
	        }
	        
	        if(countCandidate == countParams)
	        {
	        	entryList.add(listEntry);
	        }
	        
	    }
	   
	    this.selectFooterTabIndex(selectedIndex);
//	    setListDataTab(entryList, selectedIndex);
	}
	
	
	public Boolean checkIfExistInWord(String fromWord, String toBeSearchWord)
	{
		Boolean isMatched = fromWord.toLowerCase(Locale.getDefault()).contains(toBeSearchWord.toLowerCase());
		return isMatched;
		
	}
	
}
