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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nidheesha.realestate.adapters.ListAdapter;
import com.example.nidheesha.realestate.adapters.ListAdapter.OnListAdapterListener;
import com.example.nidheesha.realestate.components.listview.CustomListView;
import com.example.nidheesha.realestate.components.listview.CustomListView.OnItemListViewListener;
import com.example.nidheesha.realestate.components.utilities.Utilities;
import com.example.nidheesha.realestate.components.xmlparser.XMLRequest;
import com.example.nidheesha.realestate.config.Config;
import com.example.nidheesha.realestate.helper.asynctask.CustomAsyncTask;
import com.example.nidheesha.realestate.helper.asynctask.CustomAsyncTask.OnAsyncTaskListener;
import com.example.nidheesha.realestate.helper.image.TextTint;
import com.example.nidheesha.realestate.helper.sort.SortHelper;
import com.example.nidheesha.realestate.models.ListEntry;
import com.example.nidheesha.realestate.runtime.RuntimeApplication;
import com.example.nidheesha.realestate.main.MainActivity;
import com.example.nidheesha.realestate.R;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;

public class Tab2Fragment extends Fragment
{
	final int DOWNLOAD_XML_TAG = 1001;
	final int LIST_TAG = 2001;
	private int selectedIndex = -1;
	public View[] views;
	private View viewTab2;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
	{

		container.removeAllViews();
		
		if(viewTab2 == null)
		{
			viewTab2 = inflater.inflate(R.layout.tab2_fragment, container, false);
			
		}

        return viewTab2;
    }

	@Override
    public void onDestroyView() 
    {
        super.onDestroyView();
        if (viewTab2 != null) 
        {
            ViewGroup parentViewGroup = (ViewGroup) viewTab2.getParent();
            if (parentViewGroup != null) 
            {
                parentViewGroup.removeAllViews();
            }
        }
    }
	
    @Override
    public void onStart() 
    {
        super.onStart();
    }
    
    
    @Override
	public void onViewCreated(View view, Bundle savedInstanceState)
    {
    	super.onViewCreated(view, savedInstanceState);
    	
    	
    	RuntimeApplication runtimeApp = new RuntimeApplication();
				//(RuntimeApplication) this.getActivity().getApplication();
    	
    	if(runtimeApp.globalEntryListCities == null)
    	{
    		return;
    	}
    	
        if(runtimeApp.globalEntryListCities.size() == 0)
        {
        	downloadAsyncTask();
        }
        else
        {
        	setListData(runtimeApp.globalEntryListCities);
        }
        
        localSearch(runtimeApp);
    }
    
    public void localSearch(final RuntimeApplication runtimeApp)
    {
    	final EditText txtSearch = (EditText) this.getActivity().findViewById(R.id.txtSearch);
    	
    	txtSearch.addTextChangedListener(new TextWatcher()
    	{	
    		@Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
    		{
                // TODO Auto-generated method stub
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
            }
		});
    }
    
    public void search(String input)
    {
    	RuntimeApplication runtimeApp = new RuntimeApplication();//(RuntimeApplication) this.getActivity().getApplication();
    	List<ListEntry> listCities = runtimeApp.globalEntryListCities;
    	
    	List<ListEntry> entryList = null;
    	
    	if(entryList == null)
			entryList = new ArrayList<ListEntry>();
		
		entryList.clear();
		
		
		Log.e("listCities.size()", "size = "+listCities.size());
		
		if(input.length() > 1)
        {
			for(int x = 0; x < listCities.size(); x++)
		    {
				ListEntry listEntry = listCities.get(x);
				if( checkIfExistInWord(listEntry.title, input) )
	            {
	            	entryList.add(listEntry);
	            }
		        
		    }
            
        }
        else
        {
        	entryList = runtimeApp.globalEntryListCities;
        }
		
		setListData2(entryList);
    }
    
    public Boolean checkIfExistInWord(String fromWord, String toBeSearchWord)
	{
		Boolean isMatched = fromWord.toLowerCase(Locale.getDefault()).contains(toBeSearchWord.toLowerCase());
		return isMatched;
		
	}
    
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }
    
    
    public void downloadAsyncTask()
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
					RuntimeApplication runtimeApp = new RuntimeApplication();//(RuntimeApplication)
							//Tab2Fragment.this.getActivity().getApplication();
					
					runtimeApp.globalEntryListCities = customAsyncTask.entryList;
			        setListData(customAsyncTask.entryList);
			        
			        Log.e("list count", ""+customAsyncTask.entryList.size());
				}
			}
			
			@Override
			public void doAsyncTaskInBackground(CustomAsyncTask customAsyncTask)
			{
				// TODO Auto-generated method stub
				if(customAsyncTask.tag == DOWNLOAD_XML_TAG)
				{
			     	try 
			     	{
			     		XMLRequest req = null;
			     		
			     		Boolean isServerSourceData = false;
			     		
			     		if(Config.IS_DATA_FROM_SERVER)
			     		{
			     			if(Utilities.hasConnection(Tab2Fragment.this.getActivity()))
	        				{
			     				req = new XMLRequest(Config.URL_PATH + Config.CITY_URL);
				     			isServerSourceData = true;
	        				}
			     			else
			     			{
			     				req = new XMLRequest(Config.ASSETS_FOLDER_PATH + Config.CITY_URL);
			     			}
			     		}
			     		else
			     		{
			     			req = new XMLRequest(Config.ASSETS_FOLDER_PATH + Config.CITY_URL);
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
        
        asyncTask.tag = DOWNLOAD_XML_TAG;
        asyncTask.startAsyncTask();
    }
    
    public void setListData2(List<ListEntry> entryList)
	{
    	final RuntimeApplication runtimeApp = new RuntimeApplication();
    			//(RuntimeApplication) this.getActivity().getApplication();
    	
    	final int color = runtimeApp.switcher.getSelectedColorSwitcher();
    	
    	
		CustomListView listView = (CustomListView) this.getActivity().findViewById(R.id.listView);
		listView.removeCacheColorSelectionAndDivider(Config.CACHE_COLOR_HINT);
		listView.setSelectionSelector(R.drawable.listbackgrounddown);
		
		ListAdapter adapter = new ListAdapter(this.getActivity(), entryList, R.layout.tab2_entry);
		
		views = new View[entryList.size()];
		
		adapter.setOnListAdapterListener(new OnListAdapterListener()
		{
			
			@Override
			public void onListAdapterCreated(ListAdapter adapter, View v, int position,
                                             ViewGroup viewGroup, ListEntry listEntry)
			{
				// TODO Auto-generated method stub
				
				TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
				tvTitle.setText(listEntry.title);
				TextTint.tintTextView(Tab2Fragment.this.getActivity(), tvTitle, color);
				
				if(position == selectedIndex)
				{
					ImageView imgLocation = (ImageView)v.findViewById(R.id.imgLocation);
					imgLocation.setImageResource(R.drawable.icondetailscity);
					Log.e("asd", "after --------");
				}
			}
		});
		
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		listView.setOnItemListViewListener(new OnItemListViewListener() 
		{
			@Override
			public void onItemListViewClick(AdapterView<?> adapterView, View v,
                                            int pos, long resid)
			{
				// TODO Auto-generated method stub
				Log.e("", "pos = "+ pos);
				selectedIndex = pos;
				
				RuntimeApplication runtimeApp = new RuntimeApplication();//(RuntimeApplication) Tab2Fragment.this.getActivity().getApplication();
				
				ListEntry listEntry = (ListEntry)adapterView.getAdapter().getItem(pos);
				runtimeApp.selectedZipCodeMax = Integer.parseInt(listEntry.zipCodeMax);
				runtimeApp.selectedZipCodeMin = Integer.parseInt(listEntry.zipCodeMin);
				
				MainActivity mainActivity = (MainActivity) Tab2Fragment.this.getActivity();
		    	mainActivity.baseFragment.showFragmentTab1();
		    	mainActivity.baseFragment.setTabBarItemSelected(0);
			}
		});
	}
    
    public void setListData(List<ListEntry> entryList)
	{
    	SortHelper.sort(entryList, "title");
    	
    	final RuntimeApplication runtimeApp =  new RuntimeApplication();
    			//(RuntimeApplication) this.getActivity().getApplication();
    	
    	final int color = runtimeApp.switcher.getSelectedColorSwitcher();
    	
		CustomListView listView = (CustomListView) this.getActivity().findViewById(R.id.listView);
		listView.removeCacheColorSelectionAndDivider(Config.CACHE_COLOR_HINT);
		listView.setSelectionSelector(R.drawable.listbackgrounddown);
		
		ListAdapter adapter = new ListAdapter(this.getActivity(), entryList, R.layout.tab2_entry);
		
		views = new View[entryList.size()];
		
		adapter.setOnListAdapterListener(new OnListAdapterListener()
		{
			@Override
			public void onListAdapterCreated(ListAdapter adapter, View v, int position,
                                             ViewGroup viewGroup, ListEntry listEntry)
			{
				// TODO Auto-generated method stub
				views[position] = v;
				
				TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
				tvTitle.setText(listEntry.title);
				TextTint.tintTextView(Tab2Fragment.this.getActivity(), tvTitle, color);
				
				Log.e("asdasd", "before ---------e");

				if(position == selectedIndex)
				{
					ImageView imgLocation = (ImageView)v.findViewById(R.id.imgLocation);
					imgLocation.setImageResource(R.drawable.icondetailscity);
					Log.e("asd", "after --------");
				}
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
				Log.e("onItemListViewClick()", "selectedIndex = "+ pos);
				
				selectedIndex = pos;
				
				
				RuntimeApplication runtimeApp = new RuntimeApplication();//(RuntimeApplication) Tab2Fragment.this.getActivity().getApplication();
				
				ListEntry listEntry = (ListEntry)adapterView.getAdapter().getItem(pos);
				runtimeApp.selectedZipCodeMax = Integer.parseInt(listEntry.zipCodeMax);
				runtimeApp.selectedZipCodeMin = Integer.parseInt(listEntry.zipCodeMin);
				
				for(int x = 0; x < views.length; x++)
				{
					ImageView imgLocation = (ImageView)views[x].findViewById(R.id.imgLocation);
					imgLocation.setImageResource(0);
				}
				
				MainActivity mainActivity = (MainActivity) Tab2Fragment.this.getActivity();
		    	mainActivity.baseFragment.showFragmentTab1();
		    	mainActivity.baseFragment.setTabBarItemSelected(0);
//				
//				int ind = views.length - pos;
//				ImageView imgLocation = (ImageView)views[ind].findViewById(R.id.imgLocation);
//				imgLocation.setImageResource(R.drawable.icondetailscity);
			}
		});
		
		if(selectedIndex == -1)
		{
			for(int x = 0; x < entryList.size(); x++)
			{
				ListEntry entry = entryList.get(x);
				if(entry.selected == 1)
				{
					selectedIndex = x;
//					setSelectedItems(listView, selectedIndex);
					
					
					break;
				}
			}
		}
		else
		{
//			setSelectedItems(listView, selectedIndex);
		}
	}

    public void setSelectedItems(CustomListView listView, int x)
    {	
		listView.performItemClick(
			    listView.getAdapter().getView(x, null, null), x, listView.getItemIdAtPosition(x) );
		
		listView.requestFocusFromTouch();
		listView.setSelectionSelector(R.drawable.listbackgrounddown);
		listView.setSelection(x);
		
		Log.e("sads", ""+x);
    }
    

}
