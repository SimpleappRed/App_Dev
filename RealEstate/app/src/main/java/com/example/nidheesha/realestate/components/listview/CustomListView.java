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


package com.example.nidheesha.realestate.components.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class CustomListView extends ListView implements android.widget.AdapterView.OnItemClickListener
{
	OnItemListViewListener mCallback;
	
	public interface OnItemListViewListener 
    {
        public void onItemListViewClick(AdapterView<?> adapterView, View v, int pos, long resid);
    }
	
	public void setOnItemListViewListener(OnItemListViewListener listener)
	{
		this.setOnItemClickListener(this);
		
		try 
		{
            mCallback = (OnItemListViewListener) listener;
        } catch (ClassCastException e)
        {
            throw new ClassCastException(this.toString() + " must implement OnItemListViewListener");
        }
	}
	
	public void removeCacheColorSelectionAndDivider(int color)
	{
		this.setCacheColorHint(color);
		this.setDivider(null);
	}
	
	public void setSelectionSelector(int selectorResid)
	{
		this.setSelector(selectorResid);
	}
	
	public CustomListView(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public CustomListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public CustomListView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View v, int pos, long resid)
	{
		// TODO Auto-generated method stub
		mCallback.onItemListViewClick(adapterView, v, pos, resid);
	}

	

	

}
