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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nidheesha.realestate.components.utilities.Utilities;
import com.example.nidheesha.realestate.components.xmlparser.XMLRequest;
import com.example.nidheesha.realestate.config.Config;
import com.example.nidheesha.realestate.helper.asynctask.CustomAsyncTask;
import com.example.nidheesha.realestate.helper.asynctask.CustomAsyncTask.OnAsyncTaskListener;
import com.example.nidheesha.realestate.helper.image.ImageHelper;
import com.example.nidheesha.realestate.helper.image.TextTint;
import com.example.nidheesha.realestate.cache.ImageLoader;
import com.example.nidheesha.realestate.cache.ImageLoader.OnCacheListener;
import com.example.nidheesha.realestate.models.ListEntry;
import com.example.nidheesha.realestate.runtime.RuntimeApplication;
import com.example.nidheesha.realestate.R;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

public class Tab5Fragment extends Fragment
{
	final int DOWNLOAD_XML_TAG = 1001;
	final int LIST_TAG = 2001;
	
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
    	
    	RuntimeApplication runtimeApp = new RuntimeApplication();//(RuntimeApplication) this.getActivity().getApplication();
        if(runtimeApp.globalProfileInfo == null)
        {
        	downloadAsyncTask();
        }
        else
        {
        	setProfileData(runtimeApp.globalProfileInfo);
        }
    	
    }

    
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
    	// TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
    }

    public void downloadAsyncTask()
    {
    	CustomAsyncTask asyncTask = new CustomAsyncTask(this.getActivity());
        asyncTask.setAsyncTaskListener(new OnAsyncTaskListener() 
        {	
			@Override
			public void onAsyncTaskProgressUpdate(CustomAsyncTask customAsyncTask) 
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAsyncTaskPreExecute(CustomAsyncTask customAsyncTask) 
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAsyncTaskPostExecute(CustomAsyncTask customAsyncTask) 
			{
				// TODO Auto-generated method stub
				if(customAsyncTask.listTag == LIST_TAG && customAsyncTask.entryList != null)
				{
					RuntimeApplication runtimeApp = new RuntimeApplication();//(RuntimeApplication)
							//Tab5Fragment.this.getActivity().getApplication();
					if(runtimeApp.globalProfileInfo !=null)
						if(customAsyncTask.entryList!=null){
					runtimeApp.globalProfileInfo = customAsyncTask.entryList.get(0);
			        setProfileData(runtimeApp.globalProfileInfo);}
				}
				
				Log.e("list count", ""+customAsyncTask.entryList.size());
			}
			
			@Override
			public void doAsyncTaskInBackground(CustomAsyncTask customAsyncTask) 
			{
				// TODO Auto-generated method stub
				if(customAsyncTask.tag == DOWNLOAD_XML_TAG)
				{
			     	try 
			     	{
			     		XMLRequest req = null;
			     		
			     		Boolean isServerSourceData = false;
			     		
			     		if(Config.IS_DATA_FROM_SERVER)
			     		{
			     			if(Utilities.hasConnection(Tab5Fragment.this.getActivity()))
	        				{
			     				req = new XMLRequest(Config.URL_PATH + Config.PROFILE_URL);
				     			isServerSourceData = true;
	        				}
			     			else
			     			{
			     				req = new XMLRequest(Config.ASSETS_FOLDER_PATH + Config.PROFILE_URL);
			     			}
			     		}
			     		else
			     		{
			     			req = new XMLRequest(Config.ASSETS_FOLDER_PATH + Config.PROFILE_URL);
			     		}
			     		
	        			List<ListEntry> entryList = isServerSourceData ?
								req.obtainXMLList() : 
									req.obtainXMLListFromLocal(customAsyncTask.activity);
								
        				customAsyncTask.entryList = entryList;
        				customAsyncTask.listTag = LIST_TAG;
        				
					} 
			     	catch (ParserConfigurationException e)
					{
						e.printStackTrace();
					} 
			     	catch (SAXException e)
					{
						e.printStackTrace();
					} 
			     	catch (IOException e)
					{
						e.printStackTrace();
						Toast.makeText(customAsyncTask.activity,
								"Network Connection Error.", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
        
        asyncTask.tag = DOWNLOAD_XML_TAG;
        asyncTask.startAsyncTask();
    }
    

    public void setProfileData(final ListEntry listEntry)
    {
    	final RuntimeApplication runtimeApp = new RuntimeApplication();
    			//(RuntimeApplication) this.getActivity().getApplication();
    	
    	final int color = runtimeApp.switcher.getSelectedColorSwitcher();
    	
    	final ImageView imgThumb = (ImageView) this.getActivity().findViewById(R.id.imgThumb);
    	final ImageView imgCover = (ImageView) Tab5Fragment.this.getActivity().findViewById(R.id.imgCover);
    	
    	if(listEntry!= null)
		{
//    		ImageEntry imgEntry = listEntry.imageList.get(0);
			
			// check if the bitmap is from server 
			// or not using the flag IS_DATA_FROM_SERVER
    		Boolean isHttp = listEntry.imageThumbUrl.contains("http://");
			if(isHttp)
			{
				ImageLoader loader1 = new ImageLoader(this.getActivity(), Config.IMAGE_VIEWER_PLACEHOLDER);
				
				loader1.DisplayImageWithTag(listEntry.imageThumbUrl, imgThumb, 1);
				loader1.setOnCacheListener(new OnCacheListener() 
				{	
					@Override
					public void onImageLoaded(ImageLoader loader, Bitmap bitmap, int tag)
					{
						// TODO Auto-generated method stub
						Bitmap framedBitmap = ImageHelper.
								convertBitmapWithRoundedFrame(Tab5Fragment.this.getActivity(),
										bitmap, Config.BORDER_THICKNESS_THUMB);
					    
					    imgThumb.setImageBitmap(framedBitmap);
					} 
				});
			}
			else
			{
				Bitmap bitmap = ImageHelper.getBitmapFromAsset(
						Tab5Fragment.this.getActivity(), listEntry.imageThumbUrl);
				
				Bitmap framedBitmap = ImageHelper.
						convertBitmapWithRoundedFrame(Tab5Fragment.this.getActivity(), bitmap,
								Config.BORDER_THICKNESS_THUMB);
			    
			    imgThumb.setImageBitmap(framedBitmap);
			}
			
			
			isHttp = listEntry.imageBgUrl.contains("http://");
			if(isHttp)
			{
				ImageLoader loader = new ImageLoader(Tab5Fragment.this.getActivity(), Config.IMAGE_VIEWER_PLACEHOLDER);
				loader.DisplayImage(listEntry.imageBgUrl, imgCover);
			}
			else
			{
				imgCover.setImageBitmap(
						ImageHelper.getBitmapFromAsset(
								Tab5Fragment.this.getActivity(), listEntry.imageBgUrl));
			}
		}
		
    	TextView tvTitle = (TextView) this.getActivity().findViewById(R.id.tvTitle);
    	tvTitle.setText(listEntry.title);
    	
    	TextTint.tintTextView(this.getActivity(), tvTitle, color);
    	
    	TextView tvAddress = (TextView) this.getActivity().findViewById(R.id.tvAddress);
    	tvAddress.setText(listEntry.address);
    	
    	Button btnEmail = (Button) this.getActivity().findViewById(R.id.btnEmail);
    	btnEmail.setText(String.format("Email (%s)", listEntry.email));
    	
    	TextTint.tintButtonText(Tab5Fragment.this.getActivity(), btnEmail, color);
    	
    	btnEmail.setOnClickListener(new OnClickListener()
    	{	
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
						Uri.fromParts("mailto",listEntry.email, null));
				
				emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email Agent");
				startActivity(Intent.createChooser(emailIntent, "Send email..."));
			}
		});
    	
    	Button btnCall = (Button) this.getActivity().findViewById(R.id.btnCall);
    	btnCall.setText(String.format("Call (%s)", listEntry.telNo));
    	
    	TextTint.tintButtonText(Tab5Fragment.this.getActivity(), btnCall, color);
    	
    	
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
