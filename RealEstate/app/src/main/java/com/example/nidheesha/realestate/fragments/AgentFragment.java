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

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nidheesha.realestate.components.utilities.Utilities;
import com.example.nidheesha.realestate.config.Config;
import com.example.nidheesha.realestate.helper.image.ImageHelper;
import com.example.nidheesha.realestate.cache.ImageLoader;
import com.example.nidheesha.realestate.cache.ImageLoader.OnCacheListener;
import com.example.nidheesha.realestate.models.ImageEntry;
import com.example.nidheesha.realestate.models.ListEntry;
import com.example.nidheesha.realestate.R;

public class AgentFragment extends Fragment
{
	final int DOWNLOAD_XML_TAG = 1001;
	final int LIST_TAG = 2001;
	private ListEntry listEntry = null;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tab5_fragment, container, false);
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
    	
    	setProfileData(listEntry);
    }

    
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
    	// TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
    }

    
    public void setAgentData(ListEntry listEntry)
    {
    	this.listEntry = listEntry;
    }
    

    public void setProfileData(final ListEntry listEntry)
    {
    	final ImageView imgThumb = (ImageView) this.getActivity().findViewById(R.id.imgThumb);
    	ImageView imgCover = (ImageView) this.getActivity().findViewById(R.id.imgCover);
    	
    	if(listEntry.imageList != null && listEntry.imageList.size() > 0)
		{
    		ImageEntry imgEntry = listEntry.imageList.get(0);
			
			// check if the bitmap is from server 
			// or not using the flag IS_DATA_FROM_SERVER
			if(Config.IS_DATA_FROM_SERVER)
			{
				if(Utilities.hasConnection(this.getActivity()))
				{
					ImageLoader loader1 = new ImageLoader(this.getActivity(), Config.IMAGE_VIEWER_PLACEHOLDER);
					
					loader1.DisplayImageWithTag(imgEntry.photoImageUrl, imgThumb, 1);
					loader1.setOnCacheListener(new OnCacheListener() 
					{	
						@Override
						public void onImageLoaded(ImageLoader loader, Bitmap bitmap, int tag)
						{
							// TODO Auto-generated method stub
							Bitmap framedBitmap = ImageHelper.
									convertBitmapWithRoundedFrame(AgentFragment.this.getActivity(),
											bitmap, Config.BORDER_THICKNESS_THUMB);
						    
						    imgThumb.setImageBitmap(framedBitmap);
						} 
					});
				}
				else
				{
					Bitmap bitmap = ImageHelper.getBitmapFromAsset(
							AgentFragment.this.getActivity(), imgEntry.photoImageUrl);
					
					Bitmap framedBitmap = ImageHelper.
							convertBitmapWithRoundedFrame(AgentFragment.this.getActivity(),
									bitmap, Config.BORDER_THICKNESS_THUMB);
				    
				    imgThumb.setImageBitmap(framedBitmap);
				}
				
			}
			else
			{
				Bitmap bitmap = ImageHelper.getBitmapFromAsset(
						AgentFragment.this.getActivity(), imgEntry.photoImageUrl);
				
				Bitmap framedBitmap = ImageHelper.
						convertBitmapWithRoundedFrame(AgentFragment.this.getActivity(),
								bitmap, Config.BORDER_THICKNESS_THUMB);
			    
			    imgThumb.setImageBitmap(framedBitmap);
			}
			
			
			if(Config.IS_DATA_FROM_SERVER)
			{
				ImageLoader loader = new ImageLoader(
						AgentFragment.this.getActivity(), Config.IMAGE_VIEWER_PLACEHOLDER);
				
				loader.DisplayImage(imgEntry.photoCoverUrl,	imgCover);
			}
			else
			{
				imgCover.setImageBitmap(
						ImageHelper.getBitmapFromAsset(
								AgentFragment.this.getActivity(), imgEntry.photoCoverUrl));
			}
		}
    	
    	
    	TextView tvTitle = (TextView) this.getActivity().findViewById(R.id.tvTitle);
    	tvTitle.setText(listEntry.title);
    	
    	TextView tvAddress = (TextView) this.getActivity().findViewById(R.id.tvAddress);
    	tvAddress.setText(listEntry.address);
    	
    	Button btnEmail = (Button) this.getActivity().findViewById(R.id.btnEmail);
    	btnEmail.setText(String.format("Email (%s)", listEntry.email));
    	btnEmail.setOnClickListener(new OnClickListener()
    	{	
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",listEntry.email, null));
				emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email Agent");
				startActivity(Intent.createChooser(emailIntent, "Send email..."));
			}
		});
    	
    	Button btnCall = (Button) this.getActivity().findViewById(R.id.btnCall);
    	btnCall.setText(String.format("Call (%s)", listEntry.telNo));
    	btnCall.setOnClickListener(new OnClickListener()
    	{	
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent callIntent = new Intent(Intent.ACTION_CALL);
			    callIntent.setData(Uri.parse(String.format("tel:%s",listEntry.telNo) ));
			    startActivity(callIntent);
			}
		});
    	
    }
	

}
