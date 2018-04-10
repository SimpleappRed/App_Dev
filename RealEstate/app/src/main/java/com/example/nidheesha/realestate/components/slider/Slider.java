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

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.nidheesha.realestate.models.ListEntry;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Slider extends HorizontalScrollView
{
	private Timer timer;
	private int currentIndex = 0;
	private int width = 0;
	private Activity activity;
	private Boolean isTimerRunning;
	
	public Slider(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public Slider(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public Slider(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	
	public View[] selectionViews;
	
	OnSliderListener mCallback;
	
	public List<ListEntry> entryList;

    public interface OnSliderListener 
    {
        public void onSliderCreated(Slider slider, View v, int position, ListEntry listEntry);
    }
    

	public void setOnSliderListener(OnSliderListener listener)
	{
		try 
		{
            mCallback = (OnSliderListener) listener;
        } catch (ClassCastException e)
        {
            throw new ClassCastException(this.toString() + " must implement OnSliderListener");
        }
	}
	
	public void setSlider(Activity activity, List<ListEntry> entryList, int resid)
	{
		this.entryList = null;
		this.entryList = entryList;
		this.removeAllViews();

		this.activity = activity;
		this.isTimerRunning = false;
		this.setHorizontalFadingEdgeEnabled(false);
		this.setHorizontalScrollBarEnabled(false);
		
		LinearLayout linearLayout = new LinearLayout(activity);
		linearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		linearLayout.setOrientation(LinearLayout.HORIZONTAL);
		
		selectionViews = null;
		selectionViews = new View[entryList.size()];
		
		LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		for(int x = 0; x < entryList.size(); x++)
		{
			final View v = inflater.inflate(resid, null);
			v.setTag(x);
			selectionViews[x] = v;
			
			ListEntry listEntry = entryList.get(x);
			
			
			DisplayMetrics metrics = new DisplayMetrics();
			activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
			int width = metrics.widthPixels;
			
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.width = width;
			this.width = width;
			
			linearLayout.addView(v, params);
			
			mCallback.onSliderCreated(this, v, x, listEntry);
		}
		
		this.addView(linearLayout);
		
	}
	
	public void setSliderAnimation(long delay)
	{
		if(timer == null)
			timer = new Timer();
		
		if(isTimerRunning)
		{
			Log.e("Timer_is_running_now",
					"Timer is currently running. please stop the slider animation first by " +
					"calling Slider.stopSliderAnimation()");
			
			return;
		}
			
		
		TimerTask timerTask = new TimerTask()
	    {
			@Override
			public void run() 
			{
				// TODO Auto-generated method stub
				activity.runOnUiThread(new Runnable()
				{
					public void run() 
					{
						setCurrentSlide();
				    }
				});
			}
	    };
	    
		timer.schedule(timerTask, delay, delay);
		this.isTimerRunning = true;
	}
	
	public void stopSliderAnimation()
	{
		timer.cancel();
		timer.purge();
		this.isTimerRunning = false;
		timer = null;
	}
	
	
    
    
    public void setCurrentSlide()
    {
        // if page is less than the slider array - 1, display it
        if( currentIndex < selectionViews.length - 1)
        {
        	currentIndex++;
        	this.smoothScrollTo(width*currentIndex, 0);
        }
        // else start sliding from 1
        else
        {
        	this.smoothScrollTo(0, 0);
        	currentIndex = 0;
        }

    }

}
