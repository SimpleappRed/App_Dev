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

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nidheesha.realestate.components.slider.SliderAdapter;
import com.example.nidheesha.realestate.components.slider.SliderAdapter.OnSliderAdapterListener;
import com.example.nidheesha.realestate.components.slider.SliderV2;
import com.example.nidheesha.realestate.components.slider.SliderV2.OnSliderListener;
import com.example.nidheesha.realestate.components.utilities.Utilities;
import com.example.nidheesha.realestate.components.xmlparser.XMLRequest;
import com.example.nidheesha.realestate.config.Config;
import com.example.nidheesha.realestate.config.LayoutConfig;
import com.example.nidheesha.realestate.helper.asynctask.CustomAsyncTask;
import com.example.nidheesha.realestate.helper.asynctask.CustomAsyncTask.OnAsyncTaskListener;
import com.example.nidheesha.realestate.helper.image.ImageHelper;
import com.example.nidheesha.realestate.helper.image.ImageTint;
import com.example.nidheesha.realestate.helper.image.TextTint;
import com.example.nidheesha.realestate.cache.ImageLoader;
import com.example.nidheesha.realestate.models.ImageEntry;
import com.example.nidheesha.realestate.models.ListEntry;
import com.example.nidheesha.realestate.models.ReviewEntry;
import com.example.nidheesha.realestate.runtime.RuntimeApplication;
import com.example.nidheesha.realestate.R;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

public class OpeningFragment extends Fragment
{
	private View v = null;
	private final int DOWNLOAD_SLIDER_XML_TAG = 1001;
	private final int LIST_TAG = 2001;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		
		if(v == null)
			v = inflater.inflate(R.layout.opening, container, false);
		
        return v;
	}

	
	@Override
	public void onDestroyView() 
	{
		// TODO Auto-generated method stub
		super.onDestroyView();
	}
	

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		RuntimeApplication runtimeApp =  new RuntimeApplication();
//				(RuntimeApplication) this.getActivity().getApplication();
		
		super.onViewCreated(view, savedInstanceState);
		int color;
		if(runtimeApp.switcher != null)
		  color = runtimeApp.switcher.getSelectedColorSwitcher();
		else
			color = Color.parseColor("#ffffff");
		
		TextView imgLogo = (TextView) this.getActivity().findViewById(R.id.imgLogo);
//		ImageTint.tintImage(this.getActivity(), imgLogo.getDrawable(), color );
		
		ImageView imgHeaderList = (ImageView) this.getActivity().findViewById(R.id.imgHeaderList);
		ImageTint.tintImage(this.getActivity(), imgHeaderList.getDrawable(), color );
		
		ImageView imgHeaderSearch = (ImageView) this.getActivity().findViewById(R.id.imgHeaderSearch);
		ImageTint.tintImage(this.getActivity(), imgHeaderSearch.getDrawable(), color );
		
		Button buttonClose = (Button) this.getActivity().findViewById(R.id.buttonClose);
		TextTint.tintButtonText(this.getActivity(), buttonClose, color);
		
		buttonClose.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				showBaseFragment(false);
			}
		});
		
		FrameLayout frameList = (FrameLayout) this.getActivity().findViewById(R.id.frameList);
		frameList.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				showBaseFragment(false);
			}
		});
		
		FrameLayout frameSearch = (FrameLayout) this.getActivity().findViewById(R.id.frameSearch);
		frameSearch.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				showBaseFragment(true);
			}
		});
		
		
		downloadSliderData();
	}
	
	
	/* 
	 * -------------------------------------------------------------------------------------
     * Slider Creation
     * -------------------------------------------------------------------------------------
     */
	public void createSlider(List<ListEntry> sliderList)
	{	try{
		SliderV2 sliderV2 = (SliderV2) this.getActivity().findViewById(R.id.sliderV2);
    	SliderAdapter adapter = new SliderAdapter(R.layout.slider_entry, sliderList);
    	sliderV2.setOnSliderListener(new OnSliderListener()

		{
			@Override
			public void onItemThumbSelected(
                    SliderV2 slider, ImageView[] buttonPoint, ImageView imgView, int pos)
			{
				// TODO Auto-generated method stub
				for(int x = 0; x < buttonPoint.length; x++)
				{
					buttonPoint[x].setImageResource(LayoutConfig.SLIDER_DOT_DOWN);
				}
				
				imgView.setImageResource(LayoutConfig.SLIDER_DOT_UP);
			}
			
			@Override
			public void onItemThumbCreated(SliderV2 slider, ImageView imgView, int pos)
			{
				// TODO Auto-generated method stub
				imgView.setImageResource(LayoutConfig.SLIDER_DOT_DOWN);
			}
			
			@Override
			public void onItemSliderViewClick(AdapterView<?> adapterView, View v,
                                              int pos, long resid)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAllItemThumbCreated(SliderV2 slider,
					LinearLayout linearLayout)
			{
				// TODO Auto-generated method stub
				FrameLayout frameSliderThumb =
						(FrameLayout)OpeningFragment.this.getActivity().findViewById(R.id.frameSliderThumb);
				
				frameSliderThumb.removeAllViews();
				frameSliderThumb.addView(linearLayout);
				
			}

			@Override
			public void onItemSliderToView(SliderV2 slider, int pos) 
			{
				// TODO Auto-generated method stub
				if(slider.imageViews!=null)
				for(ImageView imgView : slider.imageViews)
				{

					imgView.setImageResource(LayoutConfig.SLIDER_DOT_DOWN);
				}
				try{
					if(slider.imageViews!=null)
				slider.imageViews[pos].setImageResource(LayoutConfig.SLIDER_DOT_UP);
			}catch (ArrayIndexOutOfBoundsException e){
					Log.d("TAG", "onItemSliderToView: ");
				}
			}
		});
    	
    	adapter.setOnSliderAdapterListener(new OnSliderAdapterListener() 
    	{
			@Override
			public void onSliderdapterCreated(SliderAdapter adapter, View v,
					int position, ListEntry listEntry) 
			{
				// TODO Auto-generated method stub
				Random rand = new Random();
				int randomIndex = rand.nextInt(listEntry.imageList.size());
				
				ImageView imgViewSlider = (ImageView) v.findViewById(R.id.imgViewSlider);
				ImageEntry imgEntry = listEntry.imageList.get(randomIndex);
				
				Boolean isHttp = imgEntry.photoImageThumbUrl.toLowerCase(Locale.getDefault()).contains("http://");
				if(isHttp)
				{
					ImageLoader loader = new ImageLoader(OpeningFragment.this.getActivity(), -1);
					loader.DisplayImage(imgEntry.photoImageUrl,	imgViewSlider);
				}
				else
				{
					imgViewSlider.setImageBitmap(
							ImageHelper.getBitmapFromAsset(
									OpeningFragment.this.getActivity(), imgEntry.photoImageUrl));
				}
				
				TextView tvAddress = (TextView) v.findViewById(R.id.tvAddress);
				tvAddress.setText(listEntry.address);
				
				TextView tvPrice = (TextView) v.findViewById(R.id.tvPrice);
				tvPrice.setText( String.format("%s%s/month", Config.CURRENCY, listEntry.price) );
			}
			
		});
    	
    	sliderV2.setAdapter(adapter);
    	sliderV2.setActivity(this.getActivity());
    	if(sliderList!= null)sliderV2.showThumb(sliderList.size(), 3);
    	sliderV2.setSliderAnimation(3000);
	}catch (NullPointerException e){
		Log.d("TAG", "createSlider: ");
	}
	}
	
	/* 
	 * -------------------------------------------------------------------------------------
     * Download Slider Data
     * -------------------------------------------------------------------------------------
     */
	public void downloadSliderData()
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
				RuntimeApplication runtimeApp = new RuntimeApplication();
//						(RuntimeApplication)
//    					OpeningFragment.this.getActivity().getApplication();
    			
    			runtimeApp.globalEntryListSlider = customAsyncTask.entryList;
    			
    			runtimeApp.globalEntryListPhotos = customAsyncTask.entryListExtra3;
    			runtimeApp.globalEntryListReviews = customAsyncTask.entryListExtra4;

				setSliderComponentsAndData(runtimeApp.globalEntryListSlider);
			}
			
			@Override
			public void doAsyncTaskInBackground(CustomAsyncTask customAsyncTask) 
			{
				// TODO Auto-generated method stub
				if(customAsyncTask.tag == DOWNLOAD_SLIDER_XML_TAG)
				{
					XMLRequest reqSlider = null;
					XMLRequest reqPhotos = null;
					XMLRequest reqReviews = null;
					
					Boolean isServerSourceData = false;
        			if(Config.IS_DATA_FROM_SERVER)
        			{
        				if(Utilities.hasConnection(OpeningFragment.this.getActivity()))
        				{
        					reqSlider = new XMLRequest(Config.URL_PATH + Config.SLIDER_URL);
            				reqPhotos = new XMLRequest(Config.URL_PATH + Config.PHOTOS_URL);
            				reqReviews = new XMLRequest(Config.URL_PATH + Config.REVIEWS_URL);
            				isServerSourceData = true;
        				}
        				else
        				{
        					reqSlider = new XMLRequest(Config.ASSETS_FOLDER_PATH + Config.SLIDER_URL);
            				reqPhotos = new XMLRequest(Config.ASSETS_FOLDER_PATH + Config.PHOTOS_URL);
            				reqReviews = new XMLRequest(Config.ASSETS_FOLDER_PATH + Config.REVIEWS_URL);
        				}
        				
        			}
        			else
        			{
        				reqSlider = new XMLRequest(Config.ASSETS_FOLDER_PATH + Config.SLIDER_URL);
        				reqPhotos = new XMLRequest(Config.ASSETS_FOLDER_PATH + Config.PHOTOS_URL);
        				reqReviews = new XMLRequest(Config.ASSETS_FOLDER_PATH + Config.REVIEWS_URL);
        			}
        			
			     	try 
			     	{
			     		List<ListEntry> entryList = isServerSourceData ?
			     				reqSlider.obtainXMLList() : 
			     					reqSlider.obtainXMLListFromLocal(customAsyncTask.activity);
								
        				customAsyncTask.entryList = entryList;
        				customAsyncTask.listTag = LIST_TAG;

        				List<ListEntry> entryList6 = isServerSourceData ?
        						reqPhotos.obtainXMLList() : 
        							reqPhotos.obtainXMLListFromLocal(customAsyncTask.activity);
								
        				customAsyncTask.entryListExtra3 = entryList6;
        				customAsyncTask.listTag = LIST_TAG;
        				
        				List<ListEntry> entryList7 = isServerSourceData ?
        						reqReviews.obtainXMLList() : 
        							reqReviews.obtainXMLListFromLocal(customAsyncTask.activity);
								
        				customAsyncTask.entryListExtra4 = entryList7;
        				customAsyncTask.listTag = LIST_TAG;
        				
        				
        				for(ListEntry entry : entryList)
        				{
        					List<ImageEntry> imageList = new ArrayList<ImageEntry>();
        					for(ListEntry entryPhotos : entryList6)
            				{
            					if(entry.id == entryPhotos.apartmentId)
            					{
            						ImageEntry imgEntry = new ImageEntry();
            						imgEntry.photoCoverUrl = entryPhotos.imageUrl;
            						imgEntry.photoImageUrl = entryPhotos.imageUrl;
            						imgEntry.photoImageThumbUrl = entryPhotos.imageThumbUrl;
            						
            						imageList.add(imgEntry);
            					}
            				}
        					entry.imageList = imageList;
        					
        					List<ReviewEntry> reviewList = new ArrayList<ReviewEntry>();
        					for(ListEntry entryReviews : entryList7)
            				{
        						if(entry.id == entryReviews.apartmentId)
            					{
        							ReviewEntry reviewEntry = new ReviewEntry();
                					reviewEntry.reviewName = entryReviews.title;
                					reviewEntry.reviewImageThumbUrl = entryReviews.imageThumbUrl;
                					reviewEntry.comment = entryReviews.reviews;
                					reviewEntry.commentDate = entryReviews.date;
                					
                					
                					reviewList.add(reviewEntry);
            					}
            					
            				}
        					entry.reviewList = reviewList;
        				}
        			
        				customAsyncTask.entryList = entryList;
						
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
					}
				}
			}
		});
        
        asyncTask.tag = DOWNLOAD_SLIDER_XML_TAG;
        asyncTask.startAsyncTask();
	}
	
	
	/* 
	 * -------------------------------------------------------------------------------------
     * Set Slider Data
     * -------------------------------------------------------------------------------------
     */
	public void setSliderComponentsAndData(List<ListEntry> entryListSlider)
	{
		createSlider(entryListSlider);
	}
	
	
	/* 
	 * -------------------------------------------------------------------------------------
     * Fragment Instantiation and commits
     * -------------------------------------------------------------------------------------
     */
	BaseFragment baseFragment = null;
	public void showBaseFragment(Boolean isSearching)
	{
		// Create an instance of ExampleFragment
		if(baseFragment == null)
			baseFragment = new BaseFragment();

        // In case this activity was started with special instructions from an Intent,
        // pass the Intent's extras to the fragment as arguments
		if(baseFragment == null)
			baseFragment.setArguments(this.getActivity().getIntent().getExtras());
		
		if(isSearching)
        	baseFragment.isSearching = true;

        FragmentTransaction transaction = this.getActivity()
        		.getSupportFragmentManager().beginTransaction();
       
        // Replace whatever is in the fragmentContainer view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragmentMainContainerWrapper, baseFragment);
        baseFragment.setRetainInstance(true);
//        transaction.addToBackStack(null);
        
        // adding transition
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        // Commit the transaction
        transaction.commit();
        
        
	}
	
	@Override
	public void onResume() 
    {
		// TODO Auto-generated method stub
		super.onResume();
		SliderV2 sliderV2 = (SliderV2) this.getActivity().findViewById(R.id.sliderV2);
		sliderV2.resumeSliderAnimation();
	}
	
	@Override
	public void onPause() 
    {
		// TODO Auto-generated method stub
		super.onPause();
		SliderV2 sliderV2 = (SliderV2) this.getActivity().findViewById(R.id.sliderV2);
		sliderV2.pauseSliderAnimation();
	}
	
}
