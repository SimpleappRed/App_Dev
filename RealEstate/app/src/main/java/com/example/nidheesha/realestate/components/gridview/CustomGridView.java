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


package com.example.nidheesha.realestate.components.gridview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.example.nidheesha.realestate.models.ImageEntry;

import java.util.List;

public class CustomGridView extends GridView implements OnItemClickListener
{
	public List<ImageEntry> imageList;
	
	
	OnGridViewListener mCallback;
	
	public interface OnGridViewListener 
    {
        public void onGridViewClicked(
                AdapterView<?> adapterView, View v, int pos, long resid, ImageEntry imageEntry);
    }
	
	public void setOnGridViewListener(OnGridViewListener listener)
	{
		try 
		{
            mCallback = (OnGridViewListener) listener;
            setOnItemClickListener(this);
        } catch (ClassCastException e)
        {
            throw new ClassCastException(this.toString() + " must implement OnGridViewListener");
        }
	}
	
	public CustomGridView(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public CustomGridView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public CustomGridView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	public void setListEntry(List<ImageEntry> imageList)
	{
		this.imageList = imageList;
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View v, int pos, long resid)
	{
		// TODO Auto-generated method stub
		ImageEntry imageEntry = (ImageEntry)adapterView.getAdapter().getItem(pos);
		mCallback.onGridViewClicked(adapterView, v, pos, resid, imageEntry);
	}

	

	

}
