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


package com.example.nidheesha.realestate.components.tabheader;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class TabHeader extends LinearLayout
{
	public View[] selectionViews;
	
	public OnTabHeaderListener mCallback;
	
	public String[] tabNames;
	
	public int selectedIndex = -1;

    public interface OnTabHeaderListener 
    {
        public void onTabHeaderCreated(TabHeader tabHeader, View v, int position);
        public void onTabHeaderSelected(TabHeader tabHeader, View v, int position);
    }
    
	public TabHeader(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public TabHeader(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public void setOnTabHeaderListener(OnTabHeaderListener listener)
	{
		try 
		{
            mCallback = (OnTabHeaderListener) listener;
        } catch (ClassCastException e)
        {
            throw new ClassCastException(this.toString() + " must implement OnTabHeaderListener");
        }
	}
	
	public void setTab(String[] tabNames, int resid, int buttonResid, int weight)
	{
		this.tabNames = tabNames;
		this.selectionViews = null;
		this.removeAllViews();
		
		this.setOrientation(LinearLayout.HORIZONTAL);
		selectionViews = new View[tabNames.length];
		
		LayoutInflater inflater = (LayoutInflater) this.getContext().
				getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		for(int x = 0; x < tabNames.length; x++)
		{
			final View v = inflater.inflate(resid, null);
			v.setTag(x);
			
			selectionViews[x] = v;
			
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, weight);
			this.addView(v, params);
			
			mCallback.onTabHeaderCreated(this, v, x);
			
			final Button btnTab = (Button) v.findViewById(buttonResid);
			btnTab.setText(String.format("%s", tabNames[x]));
			btnTab.setTag(x);
			btnTab.setOnClickListener(new OnClickListener()
			{	
				@Override
				public void onClick(View view)
				{
					// TODO Auto-generated method stub
					mCallback.onTabHeaderSelected(TabHeader.this, v, Integer.parseInt(btnTab.getTag().toString()));
				}
			});
		}
		
	}
	
	public void setTabPlain(String[] tabNames, int resid, int weight)
	{
		this.tabNames = tabNames;
		this.selectionViews = null;
		this.removeAllViews();
		
		this.setOrientation(LinearLayout.HORIZONTAL);
		selectionViews = new View[tabNames.length];
		
		LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		for(int x = 0; x < tabNames.length; x++)
		{
			final View v = inflater.inflate(resid, null);
			v.setTag(x);
			
			selectionViews[x] = v;
			
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, weight);
			this.addView(v, params);
			
			mCallback.onTabHeaderCreated(this, v, x);
		}
	}
	
	public void setSelectedTab(int index)
	{
		for(int x = 0; x < tabNames.length; x++)
		{
			if(index == x)
			{
				final View v = selectionViews[x];
				mCallback.onTabHeaderSelected(TabHeader.this, v, Integer.parseInt(v.getTag().toString()) );
				
				selectedIndex = x;
			}
		}
	}
}
