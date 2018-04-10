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
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

public class SliderV2 extends ViewPager implements OnItemClickListener
{
	
	OnSliderListener mCallback;
	
	private Timer timer;
	private int currentIndex = 0;
	private Boolean isTimerRunning;
	
	private Activity activity;
	public ImageView[] imageViews;
	public Boolean isPaused = false;
	
	public SliderV2(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public SliderV2(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public void setActivity(Activity activity)
	{
		this.activity = activity;
	}

    public interface OnSliderListener 
    {
    	public void onItemSliderToView(SliderV2 slider, int pos);
    	public void onItemSliderViewClick(AdapterView<?> adapterView, View v, int pos, long resid);
    	public void onItemThumbCreated(SliderV2 slider, ImageView imgView, int pos);
    	public void onAllItemThumbCreated(SliderV2 slider, LinearLayout linearLayout);
    	public void onItemThumbSelected(SliderV2 slider, ImageView[] buttonPoint, ImageView imgView, int pos);
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

	@Override
	public void onItemClick(AdapterView<?> adapterView, View v, int pos, long resid)
	{
		// TODO Auto-generated method stub
		mCallback.onItemSliderViewClick(adapterView, v, pos, resid);
	}
	
	public void setSliderAnimation(long delay)
	{
		isTimerRunning = false;
		
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
						if(!isPaused)
							setCurrentSlide();
				    }
				});
			}
	    };
	    
	    setSlideAtIndex(0);
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
	
	public void pauseSliderAnimation()
	{
		isPaused = true;
	}
	
	public void resumeSliderAnimation()
	{
		isPaused = false;
	}
	
	public void showThumb(int thumbCount, int maxThumbDisplay)
	{
		LinearLayout linearLayout = new LinearLayout(activity);
		linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		linearLayout.setOrientation(LinearLayout.HORIZONTAL);
		
		imageViews = new ImageView[thumbCount];
		
		for(int x = 0; x < thumbCount; x++)
		{
			final ImageView img = new ImageView(activity);
			img.setAdjustViewBounds(true);
			android.widget.LinearLayout.LayoutParams lp = new
					LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			lp.gravity = Gravity.CENTER;
			
			if(x < thumbCount - 1)
			{
				lp.setMargins(0, 0, 5, 0);
			}
			img.setLayoutParams(lp);
			img.setTag(x);
			
			
			
			img.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					int pos = Integer.parseInt(img.getTag().toString());
					SliderV2.this.setCurrentItem(pos, true);
					
					mCallback.onItemThumbSelected(SliderV2.this, imageViews, img, pos);
					currentIndex = pos;
				}
			});
			
			
			
			mCallback.onItemThumbCreated(this, img, x);
			
			if(x <= maxThumbDisplay)
			{
				linearLayout.addView(img);
				imageViews[x] = img;
			}
			
		}
		
		mCallback.onAllItemThumbCreated(this, linearLayout);
	}
	
	
    public void setCurrentSlide()
    {
    	SliderAdapter sliderAdapter = (SliderAdapter)this.getAdapter();
        // if page is less than the slider array - 1, display it
		if(sliderAdapter.selectionViews!=null)
        if( this.currentIndex < sliderAdapter.selectionViews.length - 1)
        {
        	this.currentIndex++;
        	this.setCurrentItem(currentIndex, true);
        }
        // else start sliding from 1
        else
        {
        	this.setCurrentItem(0, true);
        	this.currentIndex = 0;
        }

        if(mCallback != null)
        	mCallback.onItemSliderToView(this, currentIndex);
    }
    
    public void setSlideAtIndex(int index)
    {
    	this.setCurrentItem(index, true);
    	this.currentIndex = index;
    	
    	if(mCallback != null)
    		mCallback.onItemSliderToView(this, index);
    }

}
