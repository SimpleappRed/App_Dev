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


package com.example.nidheesha.realestate.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.nidheesha.realestate.models.ListEntry;

import java.util.List;

public class ListAdapter extends BaseAdapter
{	
	private Context c;
	public List<ListEntry> mutableList;
	private int resid;
	public int tag = 0;
	
	OnListAdapterListener mCallback;
	
	public interface OnListAdapterListener 
    {
        public void onListAdapterCreated(ListAdapter
                                                 adapter, View v, int position, ViewGroup viewGroup, ListEntry listEntry);
    }
	
	public void setOnListAdapterListener(OnListAdapterListener listener)
	{
		try 
		{
            mCallback = (OnListAdapterListener) listener;
        } catch (ClassCastException e)
        {
            throw new ClassCastException(this.toString() + " must implement OnListAdapterListener");
        }
	}
	
	public ListAdapter(Context c, List<ListEntry> mutableList, int resid)
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
		LayoutInflater li = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = li.inflate(resid, null);
		
		// get step by certain position
		ListEntry listEntry = mutableList.get(pos);
		
		// if step not equal to null, then display output.
		if(listEntry != null) 
		{
			mCallback.onListAdapterCreated(this, v, pos, viewGroup, listEntry);
		}
		
		return v;
	}
	
	@Override
	public void notifyDataSetChanged() // Create this function in your adapter class
	{
	    super.notifyDataSetChanged();
	    Log.e("notifyDataSetChanged(", ""+mutableList.size());
	}
}

