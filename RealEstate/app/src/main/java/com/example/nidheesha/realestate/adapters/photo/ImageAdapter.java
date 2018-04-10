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


package com.example.nidheesha.realestate.adapters.photo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.nidheesha.realestate.models.ImageEntry;

import java.util.List;

public class ImageAdapter extends BaseAdapter
{	
	private Context c;
	private List<ImageEntry> mutableList;
	private int resid;
	public int tag = 0;
	
	OnImageAdapterListener mCallback;
	
	public interface OnImageAdapterListener 
    {
        public void onListAdapterCreated(ImageAdapter
                                                 adapter, View v, int position, ViewGroup viewGroup, ImageEntry imageEntry);
    }
	
	public void setOnImageAdapterListener(OnImageAdapterListener listener)
	{
		try 
		{
            mCallback = (OnImageAdapterListener) listener;
        } catch (ClassCastException e)
        {
            throw new ClassCastException(this.toString() + " must implement OnImageAdapterListener");
        }
	}
	
	public ImageAdapter(Context c, List<ImageEntry> mutableList, int resid)
	{
		this.c = c;
		this.mutableList = mutableList;
		this.resid = resid;
	}

	@Override
	public int getCount() 
	{
		// TODO Auto-generated method stub
		return mutableList.size();
	}

	@Override
	public Object getItem(int pos)
	{
		// TODO Auto-generated method stub
		return mutableList.get(pos);
	}

	@Override
	public long getItemId(int pos) 
	{
		// TODO Auto-generated method stub
		return pos;
	}

	@Override
	public View getView(int pos, View v, ViewGroup viewGroup)
	{
		// TODO Auto-generated method stub
		
		// check if the view is not equal to null, if null, then inflate ingredients_row
		if(v == null) 
		{
			LayoutInflater li = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = li.inflate(resid, null);
		}
		
		// get step by certain position
		ImageEntry imageEntry = mutableList.get(pos);
		
		// if step not equal to null, then display output.
		if(imageEntry != null) 
		{
			mCallback.onListAdapterCreated(this, v, pos, viewGroup, imageEntry);
		}
		
		return v;
	}
	
	
}

