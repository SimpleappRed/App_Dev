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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nidheesha.realestate.components.xmlparser.XMLRequest;
import com.example.nidheesha.realestate.config.Config;
import com.example.nidheesha.realestate.helper.asynctask.CustomAsyncTask;
import com.example.nidheesha.realestate.helper.asynctask.CustomAsyncTask.OnAsyncTaskListener;
import com.example.nidheesha.realestate.helper.image.TextTint;
import com.example.nidheesha.realestate.models.ListEntry;
import com.example.nidheesha.realestate.runtime.RuntimeApplication;
import com.example.nidheesha.realestate.main.MainActivity;
import com.example.nidheesha.realestate.R;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

public class SearchFragment  extends Fragment
{
	final int DOWNLOAD_XML_TAG = 1001;
	final int LIST_TAG = 2001;
	private HashMap<String, String> searchMap;
	View v = null;
	Boolean isClear = false;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.search, container, false);
    }

	
    @Override
    public void onStart() 
    {
    	// TODO Auto-generated method stub
        super.onStart();

    }

    
    @Override
	public void onViewCreated(View view, Bundle savedInstanceState)
    {
    	// TODO Auto-generated method stub
    	super.onViewCreated(view, savedInstanceState);
    	
    	searchMap = new HashMap<String, String>();
    	
    	RuntimeApplication runtimeApp =new RuntimeApplication();
				//(RuntimeApplication) this.getActivity().getApplication();
   
    	v = view;
     if(runtimeApp.globalEntryListAgencies!=null && runtimeApp.globalEntryListCities!=null)
        if(runtimeApp.globalEntryListApartments.size() == 0 
        		&& runtimeApp.globalEntryListCities.size() == 0)
        {
        	beginAsyncTask();
        }
        else
        {
        	initSpinnersAndTextField(v);
        }
    }
    
    
    private void initSpinnersAndTextField(View view)
    {
    	RuntimeApplication runtimeApp = (RuntimeApplication) this.getActivity().getApplication();
    	int color = runtimeApp.switcher.getSelectedColorSwitcher();
    	
    	TextView tvLabel1 = (TextView) view.findViewById(R.id.tvLabel1);
    	TextTint.tintTextView(this.getActivity(), tvLabel1, color);
    	
    	TextView tvLabel2 = (TextView) view.findViewById(R.id.tvLabel2);
    	TextTint.tintTextView(this.getActivity(), tvLabel2, color);
    	
    	
    	TextView tvLabel3 = (TextView) view.findViewById(R.id.tvLabel3);
    	TextTint.tintTextView(this.getActivity(), tvLabel3, color);
    	
    	TextView tvLabel4 = (TextView) view.findViewById(R.id.tvLabel4);
    	TextTint.tintTextView(this.getActivity(), tvLabel4, color);
    	
    	TextView tvLabel5 = (TextView) view.findViewById(R.id.tvLabel5);
    	TextTint.tintTextView(this.getActivity(), tvLabel5, color);
    	
    	TextView tvLabel6 = (TextView) view.findViewById(R.id.tvLabel6);
    	TextTint.tintTextView(this.getActivity(), tvLabel6, color);
    	
    	TextView tvLabel7 = (TextView) view.findViewById(R.id.tvLabel7);
    	TextTint.tintTextView(this.getActivity(), tvLabel7, color);
    	
    	TextView tvLabel8 = (TextView) view.findViewById(R.id.tvLabel8);
    	TextTint.tintTextView(this.getActivity(), tvLabel8, color);
    	
    	// Price Min
    	Spinner spinnerPriceMin = (Spinner) view.findViewById(R.id.spinnerPriceMin);
    	spinnerPriceMin.setAdapter(
    			setSpinnerAdapter( convertArrayStringToList(Config.SEARCH_PRICE) ) );
    	
    	// Price Max
    	Spinner spinnerPriceMax = (Spinner) view.findViewById(R.id.spinnerPriceMax);
    	spinnerPriceMax.setAdapter(
    			setSpinnerAdapter( convertArrayStringToList(Config.SEARCH_PRICE) ) );
    	
    	// Bathrooms
    	Spinner spinnerBathrooms = (Spinner) view.findViewById(R.id.spinnerBathrooms);
    	spinnerBathrooms.setAdapter(
    			setSpinnerAdapter( convertArrayStringToList(Config.SEARCH_NO_OF_BATHROOMS) ) );
    	
    	// Bedrooms
    	Spinner spinnerBedrooms = (Spinner) view.findViewById(R.id.spinnerBedrooms);
        spinnerBedrooms.setAdapter(
    			setSpinnerAdapter( convertArrayStringToList(Config.SEARCH_NO_OF_BEDROOMS) ) );
    	
    	// Cities
    	Spinner spinnerCity = (Spinner) view.findViewById(R.id.spinnerCity1);
    	
    	String[] cities = new String[runtimeApp.globalEntryListCities.size()];
    	for(int x = 0; x < runtimeApp.globalEntryListCities.size(); x++)
    	{
    		ListEntry entry = runtimeApp.globalEntryListCities.get(x);
    		cities[x] = entry.title;
    	}
    	List<String> citiesList = convertArrayStringToList(cities);
    	Collections.sort(citiesList);

    	spinnerCity.setAdapter(
    			setSpinnerAdapter(citiesList) );
    	
    	// Apartment Type
    	Spinner spinnerApartmentType = (Spinner) view.findViewById(R.id.spinnerApartmentType);
    	spinnerApartmentType.setAdapter(
    			setSpinnerAdapter(convertArrayStringToList(Config.SEARCH_APARTMENT_TYPE)));
    	
    	final View v = view;
    	
    	Button btnSearch = (Button) this.getActivity().findViewById(R.id.btnSearch);
    	TextTint.tintButtonText(this.getActivity(), btnSearch, color);
    	btnSearch.setOnClickListener(new OnClickListener()
    	{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				isClear = false;
				search(v);
			}
		});
    	
    	Button btnClear = (Button) this.getActivity().findViewById(R.id.btnClear);
    	TextTint.tintButtonText(this.getActivity(), btnClear, color);
    	btnClear.setOnClickListener(new OnClickListener()
    	{
			
			@Override
			public void onClick(View arg0)
			{
				isClear = true;
				// TODO Auto-generated method stub
				search(v);
			}
		});
    }
    
    
    private ArrayAdapter<String> setSpinnerAdapter(List<String> stringList)
    {
    	ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(),
        		android.R.layout.simple_spinner_item, stringList);
        	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        	
        return dataAdapter;
    }

    
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
    	// TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
    }
    
    public void search(View view)
    {
    	EditText txtKeywords = (EditText) this.getActivity().findViewById(R.id.txtKeywords);
    	
    	// Price Min
    	Spinner spinnerPriceMin = (Spinner) this.getActivity().findViewById(R.id.spinnerPriceMin);
    	
    	// Price Max
    	Spinner spinnerPriceMax = (Spinner) this.getActivity().findViewById(R.id.spinnerPriceMax);
    	
    	EditText txtZipCode = (EditText) this.getActivity().findViewById(R.id.txtZipCode);
    	
    	// Bathrooms
    	Spinner spinnerBathrooms = (Spinner) this.getActivity().findViewById(R.id.spinnerBathrooms);
    	
    	// Bedrooms
    	Spinner spinnerBedrooms = (Spinner) this.getActivity().findViewById(R.id.spinnerBedrooms);
    	
    	// Cities
    	Spinner spinnerCity = (Spinner) this.getActivity().findViewById(R.id.spinnerCity1);
    	
    	// Apartment Type
    	Spinner spinnerApartmentType = (Spinner) this.getActivity().findViewById(R.id.spinnerApartmentType);
    	
    	String keywordToSearch = txtKeywords.getText().toString();
    	String zipCode = txtZipCode.getText().toString();
    	String strBedrooms = spinnerBedrooms.getSelectedItem().toString().replace("-", "");
    	String strBathrooms = spinnerBathrooms.getSelectedItem().toString().replace("-", "");
    	
    	String strPriceMin = spinnerPriceMin.getSelectedItem().toString().replace("-", "");
    	String strPriceMax = spinnerPriceMax.getSelectedItem().toString().replace("-", "");
    	
    	strPriceMin = strPriceMin.replace(Config.CURRENCY, "");
    	strPriceMax = strPriceMax.replace(Config.CURRENCY, "");
    	
    	strPriceMin = strPriceMin.replace(" ", "");
    	strPriceMax = strPriceMax.replace(" ", "");
    	
    	strPriceMin = strPriceMin.replace(",", "");
    	strPriceMax = strPriceMax.replace(",", "");
    	
    	
    	String city = spinnerCity.getSelectedItem().toString();
    	String apartmentType = spinnerApartmentType.getSelectedItem().toString();
    	
    	searchMap.put("keywordToSearch", isClear ? "" : keywordToSearch );
    	searchMap.put("priceMin", isClear ? "" : strPriceMin);
    	searchMap.put("priceMax", isClear ? "" : strPriceMax);
    	searchMap.put("zipCode", isClear ? "" : zipCode);
    	searchMap.put("bathrooms", isClear ? "" : strBathrooms);
    	searchMap.put("bedrooms", isClear ? "" : strBedrooms);
    	searchMap.put("city", isClear ? "" : city);
    	searchMap.put("apartmentType", isClear ? "" : apartmentType);

    	if(isClear)
    	{
    		txtKeywords.setText("");
        	spinnerPriceMin.setSelection(0);
        	spinnerPriceMax.setSelection(0);
        	txtZipCode.setText("");
        	spinnerBathrooms.setSelection(0);
        	spinnerBedrooms.setSelection(0);
        	spinnerCity.setSelection(0);
        	spinnerApartmentType.setSelection(0);
    	}
    	
    	if( (spinnerPriceMin.getSelectedItemPosition() == 0 && 
    			spinnerPriceMax.getSelectedItemPosition() > 0) || 
    			(spinnerPriceMin.getSelectedItemPosition() > 0 && 
    			spinnerPriceMax.getSelectedItemPosition() == 0) )
    	{
    		Toast.makeText(SearchFragment.this.getActivity(),
    				"Min and Max Price must both contain numeric values", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	
    	MainActivity mainActivity = (MainActivity) this.getActivity();
    	mainActivity.baseFragment.showTab1FragmentInSearch(searchMap);
    	
//    	FragmentHelper.showTab1FragmentInSearch(searchMap, SearchFragment.this.getActivity());
    }
    
    public List<String> convertArrayStringToList(String[] stringArray)
    {
    	List<String> list = new ArrayList<String>();
    	
    	for(int i = 0; i< stringArray.length; i++)
    	{
    		list.add(stringArray[i]);
    	}
    	
    	return list;
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
        			XMLRequest req = new XMLRequest(Config.APARTMENTS_URL);
        			XMLRequest reqAgents = new XMLRequest(Config.AGENTS_URL);
        			XMLRequest reqCity = new XMLRequest(Config.CITY_URL);
        			
        	     	try 
        	     	{
        				List<ListEntry> entryList = req.obtainXMLList();
        				customAsyncTask.entryList = entryList;
        				customAsyncTask.listTag = LIST_TAG;
        				
        				List<ListEntry> entryList2 = reqAgents.obtainXMLList();
        				customAsyncTask.entryListExtra = entryList2;
        				customAsyncTask.listTag = LIST_TAG;
        				
        				List<ListEntry> entryList3 = reqCity.obtainXMLList();
        				customAsyncTask.entryListExtraInfo = entryList3;
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
        			
        			RuntimeApplication runtimeApp = (RuntimeApplication) 
        					SearchFragment.this.getActivity().getApplication();
        			
        			runtimeApp.globalEntryListApartments = customAsyncTask.entryList;
        			runtimeApp.globalEntryListAgencies = customAsyncTask.entryListExtra;
        			runtimeApp.globalEntryListCities = customAsyncTask.entryListExtraInfo;
        			
        			Log.e("list count", ""+customAsyncTask.entryList.size());
        			initSpinnersAndTextField(v);
        			
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
}
