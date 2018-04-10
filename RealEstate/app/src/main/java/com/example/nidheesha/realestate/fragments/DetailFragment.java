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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nidheesha.realestate.components.tabheader.TabHeader;
import com.example.nidheesha.realestate.components.tabheader.TabHeader.OnTabHeaderListener;
import com.example.nidheesha.realestate.components.tabheader.TabSelection;
import com.example.nidheesha.realestate.config.Config;
import com.example.nidheesha.realestate.config.LayoutConfig;
import com.example.nidheesha.realestate.sub.DetailFragmentTab1;
import com.example.nidheesha.realestate.sub.DetailFragmentTab2;
import com.example.nidheesha.realestate.sub.DetailFragmentTab3;
import com.example.nidheesha.realestate.sub.DetailFragmentTab4;
import com.example.nidheesha.realestate.helper.image.TextTint;
import com.example.nidheesha.realestate.cache.ImageLoader;
import com.example.nidheesha.realestate.models.ListEntry;
import com.example.nidheesha.realestate.runtime.RuntimeApplication;
import com.example.nidheesha.realestate.R;

public class DetailFragment extends Fragment implements OnTabHeaderListener
{
	private ListEntry listEntry;
	public int selectedIndex = 0;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.detail_fragment, container, false);
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
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
    {
		super.onViewCreated(view, savedInstanceState);
		
		RuntimeApplication runtimeApp = 
				(RuntimeApplication) this.getActivity().getApplication();
		
		String[] tabNames = {"Details", "Photos", "Map", "Reviews"};
        TabHeader tabHeader = (TabHeader) this.getActivity().findViewById(R.id.tabHeader);
        tabHeader.setOnTabHeaderListener(this);
        tabHeader.setTab(tabNames, R.layout.tab_entry, R.id.btnTab, LayoutConfig.INNER_TAB_WEIGHT_DETAIL);
        tabHeader.setSelectedTab(selectedIndex);
        
        TextView tvPrice = (TextView) view.findViewById(R.id.tvPrice);
        tvPrice.setText(String.format("%s%s", Config.CURRENCY, listEntry.price));
        
        TextView tvAddress = (TextView) view.findViewById(R.id.tvAddress);
        tvAddress.setText(listEntry.address);
        
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText(listEntry.title);
        TextTint.tintTextView(this.getActivity(), tvTitle, runtimeApp.switcher.getSelectedColorSwitcher());
        
        ImageLoader loader = new ImageLoader(this.getActivity(), Config.THUMB_PLACE_HOLDER);
        
        if(listEntry.imageList.size() > 0)
		{
			ImageView imgCover = (ImageView) view.findViewById(R.id.imgCover);
			loader.DisplayImage(listEntry.imageList.get(0).photoImageUrl, imgCover);
		}
        
        
    }
	
	
	/* 
	 * -------------------------------------------------------------------------------------
     * List Entry Setter method
     * -------------------------------------------------------------------------------------
     */
	public void setListEntry(ListEntry listEntry)
	{
		this.listEntry = listEntry;
	}
	
	
	/* 
	 * -------------------------------------------------------------------------------------
     * Tab header delegate
     * -------------------------------------------------------------------------------------
     */
	@Override
	public void onTabHeaderCreated(TabHeader tabHeader, View v, int position)
	{
		// TODO Auto-generated method stub
		RuntimeApplication runtimeApp = 
				(RuntimeApplication) this.getActivity().getApplication();
		
		Button btnTab = (Button) v.findViewById(R.id.btnTab);
		int color = 0;
		btnTab.postInvalidate();
		
		TabSelection tabSelection = LayoutConfig.INNER_TAB_BACKGROUND_DETAIL[position];
		
		if(position == 0)
		{
			btnTab.setBackgroundResource(tabSelection.tabSelectedResid);
			color = runtimeApp.switcher.getSelectedColorSwitcher();
		}
		else
		{
			btnTab.setBackgroundResource(tabSelection.tabUnselectedResid);
			color = R.color.text_unselected_color;
		}
		
		TextTint.tintButtonText(this.getActivity(), btnTab, color);
		btnTab.requestLayout();
	}

	@Override
	public void onTabHeaderSelected(TabHeader tabHeader, View v, int position)
	{
		// TODO Auto-generated method stub
		selectedIndex = position;
		
		RuntimeApplication runtimeApp = 
				(RuntimeApplication) this.getActivity().getApplication();
		
		for(int x = 0; x < tabHeader.selectionViews.length; x++)
		{
			
			TabSelection tabSelection = LayoutConfig.INNER_TAB_BACKGROUND_DETAIL[x];
			
			View selectedView = tabHeader.selectionViews[x];
			Button btnTab = (Button) selectedView.findViewById(R.id.btnTab);
			int color = 0;
			btnTab.postInvalidate();
			
			if(position == x)
			{
				btnTab.setBackgroundResource(tabSelection.tabSelectedResid);
				color = runtimeApp.switcher.getSelectedColorSwitcher();
			}
			else
			{
				btnTab.setBackgroundResource(tabSelection.tabUnselectedResid);
				color = R.color.text_unselected_color;
			}
			
			TextTint.tintButtonText(this.getActivity(), btnTab, color);
			btnTab.requestLayout();
		}
		
		
		FrameLayout detailContainer = (FrameLayout) this.getActivity().findViewById(R.id.detailContainer);
		detailContainer.removeAllViews();
		
		switch(position)
		{
			case 0:
				showFragmentTab1();
				break;
				
			case 1:
				showFragmentTab2();
				break;
				
			case 2:
				showFragmentTab3();
				break;
				
			case 3:
				showFragmentTab4();
				break;
		}
	}
	
	public void showFragmentTab1()
	{
		// Create an instance of ExampleFragment
        DetailFragmentTab1 detailFragment = new DetailFragmentTab1();

        // In case this activity was started with special instructions from an Intent,
        // pass the Intent's extras to the fragment as arguments
        detailFragment.setArguments(this.getActivity().getIntent().getExtras());
        detailFragment.setListEntry(listEntry);

        FragmentTransaction transaction = this.getActivity().getSupportFragmentManager().beginTransaction();
        
        // Replace whatever is in the fragmentContainer view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.detailContainer, detailFragment);
//        transaction.addToBackStack(null);

        // adding transition
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        
        // Commit the transaction
        transaction.commit();
	}
	
	public void showFragmentTab2()
	{
		// Create an instance of ExampleFragment
        DetailFragmentTab2 detailFragment = new DetailFragmentTab2();

        // In case this activity was started with special instructions from an Intent,
        // pass the Intent's extras to the fragment as arguments
        detailFragment.setArguments(this.getActivity().getIntent().getExtras());
        detailFragment.setListEntry(listEntry);

        FragmentTransaction transaction = this.getActivity().getSupportFragmentManager().beginTransaction();
        
        // Replace whatever is in the fragmentContainer view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.detailContainer, detailFragment);
//        transaction.addToBackStack(null);
        
        // adding transition
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        // Commit the transaction
        transaction.commit();
	}
	
	DetailFragmentTab3 detailFragmentTab3;
	public void showFragmentTab3()
	{
		// Create an instance of ExampleFragment
		if(detailFragmentTab3 == null)
		{
			detailFragmentTab3 = new DetailFragmentTab3();

	        // In case this activity was started with special instructions from an Intent,
	        // pass the Intent's extras to the fragment as arguments
			detailFragmentTab3.setArguments(this.getActivity().getIntent().getExtras());
			detailFragmentTab3.setListEntry(listEntry);
		}

        FragmentTransaction transaction = this.getActivity().getSupportFragmentManager().beginTransaction();
        transaction.remove(detailFragmentTab3);
        // Replace whatever is in the fragmentContainer view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.detailContainer, detailFragmentTab3);
        detailFragmentTab3.setRetainInstance(true);
//        transaction.addToBackStack(null);
        
        // adding transition
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        // Commit the transaction
        transaction.commit();
	}
	
	public void showFragmentTab4()
	{
		// Create an instance of ExampleFragment
        DetailFragmentTab4 detailFragment = new DetailFragmentTab4();

        // In case this activity was started with special instructions from an Intent,
        // pass the Intent's extras to the fragment as arguments
        detailFragment.setArguments(this.getActivity().getIntent().getExtras());
        detailFragment.setListEntry(listEntry);

        FragmentTransaction transaction = this.getActivity().getSupportFragmentManager().beginTransaction();
        
        // Replace whatever is in the fragmentContainer view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.detailContainer, detailFragment);
//        transaction.addToBackStack(null);
        
        // adding transition
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        // Commit the transaction
        transaction.commit();
	}

}
