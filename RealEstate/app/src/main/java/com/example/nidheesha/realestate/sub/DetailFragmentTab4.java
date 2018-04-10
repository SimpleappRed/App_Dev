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


package com.example.nidheesha.realestate.sub;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nidheesha.realestate.adapters.review.ReviewAdapter;
import com.example.nidheesha.realestate.adapters.review.ReviewAdapter.OnReviewAdapterListener;
import com.example.nidheesha.realestate.components.listview.CustomListView;
import com.example.nidheesha.realestate.components.listview.CustomListView.OnItemListViewListener;
import com.example.nidheesha.realestate.config.Config;
import com.example.nidheesha.realestate.helper.image.ImageHelper;
import com.example.nidheesha.realestate.helper.image.TextTint;
import com.example.nidheesha.realestate.cache.ImageLoader;
import com.example.nidheesha.realestate.cache.ImageLoader.OnCacheListener;
import com.example.nidheesha.realestate.models.ListEntry;
import com.example.nidheesha.realestate.models.ReviewEntry;
import com.example.nidheesha.realestate.runtime.RuntimeApplication;
import com.example.nidheesha.realestate.R;

import java.util.Locale;

public class DetailFragmentTab4 extends Fragment
{
	private ListEntry listEntry;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.detail_tab4, container, false);
    }
	
	@Override
    public void onStart() 
    {
        super.onStart();
        
        
    }
	
	@Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }
	
	public void setListEntry(ListEntry listEntry)
	{
		this.listEntry = listEntry;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
    {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		CustomListView listView = (CustomListView) this.getActivity().findViewById(R.id.listView);
        listView.removeCacheColorSelectionAndDivider(Config.CACHE_COLOR_HINT);
        listView.setSelectionSelector(android.R.color.transparent);
		
        ReviewAdapter adapter = new ReviewAdapter(this.getActivity(), listEntry.reviewList, R.layout.review_entry);
		adapter.setOnReviewAdapterListener(new OnReviewAdapterListener()
		{	
			@Override
			public void onReviewAdapterCreated(ReviewAdapter adapter, View v,
                                               int position, ViewGroup viewGroup, ReviewEntry reviewEntry)
			{
				// TODO Auto-generated method stub
				final ImageView imgThumb = (ImageView) v.findViewById(R.id.imgThumb1);
				
				if(listEntry.imageList != null && listEntry.imageList.size() > 0)
				{
					ImageLoader loader = new ImageLoader(
							DetailFragmentTab4.this.getActivity(), Config.THUMB_PLACE_HOLDER);
					
					// check if the bitmap is from server 
					// or not using the flag IS_DATA_FROM_SERVER
					Boolean isHttp = reviewEntry.reviewImageThumbUrl.toLowerCase(Locale.getDefault()).contains("http://");
					if(isHttp)
					{
						loader.DisplayImageWithTag(reviewEntry.reviewImageThumbUrl, imgThumb, 1);
						loader.setOnCacheListener(new OnCacheListener() 
						{	
							@Override
							public void onImageLoaded(ImageLoader loader, Bitmap bitmap, int tag)
							{
								// TODO Auto-generated method stub
								Bitmap framedBitmap = ImageHelper.
										convertBitmapWithFrame(DetailFragmentTab4.this.getActivity(),
												bitmap, Config.BORDER_THICKNESS_THUMB);

							    imgThumb.setImageBitmap(framedBitmap);
							}
						});
					}
					else
					{
						Bitmap bitmap = ImageHelper.getBitmapFromAsset(
								DetailFragmentTab4.this.getActivity(), reviewEntry.reviewImageThumbUrl);
						
						if(bitmap != null)
						{
							Bitmap framedBitmap = ImageHelper.
									convertBitmapWithFrame(DetailFragmentTab4.this.getActivity(), bitmap,
											Config.BORDER_THICKNESS_THUMB);
							
							imgThumb.setImageBitmap(framedBitmap);
						}
						
					}
				}
				
				RuntimeApplication runtimeApp = (RuntimeApplication) 
						DetailFragmentTab4.this.getActivity().getApplication();
				
				int color = runtimeApp.switcher.getSelectedColorSwitcher();
				
				TextView tvName = (TextView) v.findViewById(R.id.tvName);
				tvName.setText(reviewEntry.reviewName);
				TextTint.tintTextView(DetailFragmentTab4.this.getActivity(), tvName, color);
				
				TextView tvDate = (TextView) v.findViewById(R.id.tvDate);
				tvDate.setText(reviewEntry.commentDate);
				
				TextView tvComment = (TextView) v.findViewById(R.id.tvComment);
				tvComment.setText(reviewEntry.comment);
				
			}
		});
		
		listView.setAdapter(adapter);
		listView.setOnItemListViewListener(new OnItemListViewListener() 
		{	
			@Override
			public void onItemListViewClick(AdapterView<?> adapterView, View v,
                                            int pos, long resid)
			{
				// TODO Auto-generated method stub
				
			}
		});
    }

}
