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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.example.nidheesha.realestate.adapters.photo.ImageAdapter;
import com.example.nidheesha.realestate.adapters.photo.ImageAdapter.OnImageAdapterListener;
import com.example.nidheesha.realestate.components.gridview.CustomGridView;
import com.example.nidheesha.realestate.components.gridview.CustomGridView.OnGridViewListener;
import com.example.nidheesha.realestate.config.Config;
import com.example.nidheesha.realestate.fragments.helper.FragmentHelper;
import com.example.nidheesha.realestate.helper.image.ImageHelper;
import com.example.nidheesha.realestate.cache.ImageLoader;
import com.example.nidheesha.realestate.cache.ImageLoader.OnCacheListener;
import com.example.nidheesha.realestate.models.ImageEntry;
import com.example.nidheesha.realestate.models.ListEntry;
import com.example.nidheesha.realestate.R;

public class DetailFragmentTab2 extends Fragment
{
	private ListEntry listEntry;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.detail_tab2, container, false);
    }
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
    {
        CustomGridView gridView = (CustomGridView) this.getActivity().findViewById(R.id.gridViewPhotos);
        gridView.setNumColumns(5);
        
        ImageAdapter adapter = new ImageAdapter(this.getActivity(), listEntry.imageList, R.layout.photo_entry);
        adapter.setOnImageAdapterListener(new OnImageAdapterListener()
        {
			@Override
			public void onListAdapterCreated(ImageAdapter adapter, View v,
                                             int position, ViewGroup viewGroup, ImageEntry imageEntry)
			{
				// TODO Auto-generated method stub
				final ImageView imgThumb = (ImageView) v.findViewById(R.id.imgViewPhoto);
				
				ImageLoader loader = new ImageLoader(DetailFragmentTab2.this.getActivity(),
						Config.THUMB_PLACE_HOLDER);
				
				// check if the bitmap is from server 
				// or not using the flag IS_DATA_FROM_SERVER
				
				Boolean isHttp = imageEntry.photoImageThumbUrl.toLowerCase().contains("http://");
				if(isHttp)
				{
					loader.DisplayImageWithTag(imageEntry.photoImageThumbUrl, imgThumb, 1);
					loader.setOnCacheListener(new OnCacheListener() 
					{	
						@Override
						public void onImageLoaded(ImageLoader loader, Bitmap bitmap, int tag)
						{
							// TODO Auto-generated method stub
							Bitmap framedBitmap = ImageHelper.
									convertBitmapWithFrame(DetailFragmentTab2.this.getActivity(),
											bitmap, Config.BORDER_THICKNESS_THUMB);

						    imgThumb.setImageBitmap(framedBitmap);
						}
					});
				}
				else
				{
					Bitmap bitmap = ImageHelper.getBitmapFromAsset(
							DetailFragmentTab2.this.getActivity(), imageEntry.photoImageThumbUrl);
					
					Bitmap framedBitmap = ImageHelper.
							convertBitmapWithFrame(DetailFragmentTab2.this.getActivity(),
									bitmap, Config.BORDER_THICKNESS_THUMB);
					
					imgThumb.setImageBitmap(framedBitmap);
				}
				
			}
		});
        gridView.setAdapter(adapter);
        gridView.setOnGridViewListener(new OnGridViewListener()
        {
			@Override
			public void onGridViewClicked(AdapterView<?> adapterView, View v, int pos,
                                          long resid, ImageEntry imageEntry)
			{
				// TODO Auto-generated method stub
				Log.e("Photo Clicked", "pos = "+pos);
				FragmentHelper.showImageViewerFragment(listEntry, DetailFragmentTab2.this.getActivity(), pos);
			}
		});
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

}
