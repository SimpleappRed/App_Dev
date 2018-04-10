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

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nidheesha.realestate.components.utilities.Utilities;
import com.example.nidheesha.realestate.config.Config;
import com.example.nidheesha.realestate.helper.image.ImageHelper;
import com.example.nidheesha.realestate.cache.ImageLoader;
import com.example.nidheesha.realestate.models.ImageEntry;
import com.example.nidheesha.realestate.models.ListEntry;
import com.example.nidheesha.realestate.R;

import com.example.nidheesha.realestate.touchgallery.GalleryWidget.BasePagerAdapter2;
import com.example.nidheesha.realestate.touchgallery.GalleryWidget.GalleryViewPager;

public class ImageViewerFragment  extends Fragment
{
	private GalleryViewPager galleryPager;
	
	private ListEntry listEntry = null;
	private int position;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.image_viewer, container, false);
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
    	
    	BasePagerAdapter2 pagerAdapter = new BasePagerAdapter2(
    			this.getActivity(), this.listEntry.imageList, 
    			R.layout.image_viewer_entry, Config.IMAGE_VIEWER_PLACEHOLDER);
    	
        pagerAdapter.setOnItemChangeListener(new BasePagerAdapter2.OnItemChangeListener() 
        {
			
			@Override
			public void onItemChange(int currentPosition) 
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onGalleryAdapterCreated(BasePagerAdapter2 adapter, View v,
					int currentPosition, ImageEntry listEntry) 
			{
				// TODO Auto-generated method stub
				final ImageView imgView = (ImageView) v.findViewById(R.id.imgViewer);
				
				ImageLoader loader = new ImageLoader(
						ImageViewerFragment.this.getActivity(), Config.IMAGE_VIEWER_PLACEHOLDER);
				
//				loader.DisplayImage(listEntry.photoImageUrl, imgView);
				
				if(Config.IS_DATA_FROM_SERVER)
				{
					if(Utilities.hasConnection(ImageViewerFragment.this.getActivity()))
    				{
						loader.DisplayImage(listEntry.photoImageUrl, imgView);
    				}
					else
					{
						Bitmap bitmap = ImageHelper.getBitmapFromAsset(
								ImageViewerFragment.this.getActivity(), listEntry.photoImageUrl);
						
						imgView.setImageBitmap(bitmap);
					}
					
				}
				else
				{
					Bitmap bitmap = ImageHelper.getBitmapFromAsset(
							ImageViewerFragment.this.getActivity(), listEntry.photoImageUrl);
					
//					Bitmap framedBitmap = ImageHelper.
//							convertBitmapWithFrame(ImageViewerFragment.this.getActivity(), bitmap);
					
					imgView.setImageBitmap(bitmap);
				}
			}
		});
        
        galleryPager = (GalleryViewPager)view.findViewById(R.id.imageViewer);
        galleryPager.setOffscreenPageLimit(2);
        galleryPager.setAdapter(pagerAdapter);
        galleryPager.setCurrentItem(position);
    }

    
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
    	// TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
    }

    
    public void setListEntry(ListEntry listEntry, int position)
    {
    	this.listEntry = listEntry;
    	this.position = position;
    }
	

}
