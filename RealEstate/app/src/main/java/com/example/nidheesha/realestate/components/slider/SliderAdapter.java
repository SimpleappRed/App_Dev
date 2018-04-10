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


package com.example.nidheesha.realestate.components.slider;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.example.nidheesha.realestate.models.ListEntry;

import java.util.List;

public class SliderAdapter extends PagerAdapter
{
	private int resid = 0;
	public List<ListEntry> entryList;
	private OnSliderAdapterListener mCallback;
	public View[] selectionViews;
	
	public interface OnSliderAdapterListener 
    {
        public void onSliderdapterCreated(SliderAdapter
                                                  adapter, View v, int position, ListEntry listEntry);
    }
	
	public void setOnSliderAdapterListener(OnSliderAdapterListener listener)
	{
		try 
		{
            mCallback = (OnSliderAdapterListener) listener;
        } catch (ClassCastException e)
        {
            throw new ClassCastException(this.toString() + " must implement OnSliderAdapterListener");
        }
	}
	
	
	public SliderAdapter(int resid, List<ListEntry> entryList)
	{
		// TODO Auto-generated constructor stub
		this.resid = resid;
		this.entryList = entryList;
		if(entryList != null)
		selectionViews = new View[entryList.size()];
	}
	
	@Override
	public int getCount() 
	{
		// TODO Auto-generated method stub
		if(entryList!=null)
		return entryList.size();
		else return 0;
	}

	@Override
	public boolean isViewFromObject(View v, Object obj)
	{
		// TODO Auto-generated method stub
		return v == ((View) obj);
	}

	@Override
	public Object instantiateItem(View container, int position)
	{
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater)
				container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View view = inflater.inflate(resid, null);
		
		selectionViews[position] = view;
		
		((ViewPager) container).addView(view,0);
		
		ListEntry listEntry = entryList.get(position);
		
		mCallback.onSliderdapterCreated(this, view, position, listEntry);
		
		return view;
	}

	@Override
	public int getItemPosition(Object object)
	{
		// TODO Auto-generated method stub
		return super.getItemPosition(object);
	}

	@Override
	public Parcelable saveState() {
		// TODO Auto-generated method stub
		return super.saveState();
	}
	
	@Override
    public void destroyItem(View collection, int position, Object view)
	{
		// disabled so that the view will not be removed and to avoid
		// flickering when animation begins
//         ((ViewPager) collection).removeView((View) view);
    }

}
