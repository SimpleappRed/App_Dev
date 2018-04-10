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
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nidheesha.realestate.components.tabheader.TabHeader;
import com.example.nidheesha.realestate.components.tabheader.TabHeader.OnTabHeaderListener;
import com.example.nidheesha.realestate.config.LayoutConfig;
import com.example.nidheesha.realestate.fragments.helper.FragmentHelper;
import com.example.nidheesha.realestate.helper.image.ImageTint;
import com.example.nidheesha.realestate.helper.image.TextTint;
import com.example.nidheesha.realestate.runtime.RuntimeApplication;
import com.example.nidheesha.realestate.main.MainActivity;
import com.example.nidheesha.realestate.R;

import java.util.HashMap;

public class BaseFragment extends Fragment implements OnClickListener
{
	final int DOWNLOAD_XML_TAG = 1001;
	final int LIST_TAG = 2001;
	public Boolean isSearching = false;
	public Boolean isSearchingFromOpening = false;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.base_fragment, container, false);
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
    	
    	RuntimeApplication runtimeApp = new RuntimeApplication();
				//(RuntimeApplication) this.getActivity().getApplication();
    	
    	TextView imgLogo = (TextView) this.getActivity().findViewById(R.id.imgLogo);
//		if(runtimeApp.switcher!=null){
//    	ImageTint.tintImage(this.getActivity(),
//				imgLogo.getDrawable(), runtimeApp.switcher.getSelectedColorSwitcher());}
//				else{
//			ImageTint.tintImage(this.getActivity(),
//					imgLogo.getDrawable(), Color.parseColor("#ffffff"));
//		}

		
		
		if(LayoutConfig.SHOW_NAVIGATION_BAR_ITEM_LEFT)
		{
			FrameLayout frameList = (FrameLayout) this.getActivity().findViewById(R.id.frameList);
			
			ImageView imgHeaderList = (ImageView) this.getActivity().findViewById(R.id.imgHeaderList);
			imgHeaderList.setImageResource(LayoutConfig.NAVIGATION_BAR_ITEM_LEFT_ICON);
			if(runtimeApp.switcher!=null)
			ImageTint.tintImage(this.getActivity(), 
					imgHeaderList.getDrawable(), runtimeApp.switcher.getSelectedColorSwitcher() );

			frameList.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View arg0)
				{
					// TODO Auto-generated method stub
					TabHeader tabHeaderMain = (TabHeader) 
							BaseFragment.this.getActivity().findViewById(R.id.tabHeaderMain);
					
					if(tabHeaderMain.selectedIndex != 0)
					{
						showFragmentTab1();
						tabHeaderMain.setSelectedTab(0);
					}
				}
			});
			
			if(LayoutConfig.NAVIGATION_BAR_ITEM_LEFT_SELECTOR > 0)
				frameList.setBackgroundResource(LayoutConfig.NAVIGATION_BAR_ITEM_LEFT_SELECTOR);
		}
		else
		{
			FrameLayout frameList = (FrameLayout) this.getActivity().findViewById(R.id.frameList);
			frameList.removeAllViews();
			frameList.setBackgroundResource(0);
		}
		
		if(LayoutConfig.SHOW_NAVIGATION_BAR_ITEM_RIGHT)
		{
			FrameLayout frameSearch = (FrameLayout) this.getActivity().findViewById(R.id.frameSearch);
			
			ImageView imgHeaderSearch = (ImageView) this.getActivity().findViewById(R.id.imgHeaderSearch);
			imgHeaderSearch.setImageResource(LayoutConfig.NAVIGATION_BAR_ITEM_RIGHT_ICON);
if(runtimeApp.switcher!=null)
			ImageTint.tintImage(this.getActivity(), 
					imgHeaderSearch.getDrawable(), runtimeApp.switcher.getSelectedColorSwitcher());
			
			setOnClickListenerOnFrameLayout(R.id.frameSearch);
			
			if(LayoutConfig.NAVIGATION_BAR_ITEM_RIGHT_SELECTOR > 0)
				frameSearch.setBackgroundResource(LayoutConfig.NAVIGATION_BAR_ITEM_RIGHT_SELECTOR);
		}
		else
		{
			FrameLayout frameSearch = (FrameLayout) this.getActivity().findViewById(R.id.frameSearch);
			frameSearch.removeAllViews();
			frameSearch.setBackgroundResource(0);
		}
	
		initTabs();
		
		MainActivity mainActivity = (MainActivity) this.getActivity();
    	mainActivity.baseFragment = this;
    }
    
    public void setOnClickListenerOnButton(int resid)
	{
		Button btn = (Button) this.getActivity().findViewById(resid);
		btn.setOnClickListener(this);
	}
	
	public void setOnClickListenerOnFrameLayout(int resid)
	{
		FrameLayout btn = (FrameLayout) this.getActivity().findViewById(resid);
		btn.setOnClickListener(this);
	}
	
	public void setOnClickListenerOnImageView(int resid)
	{
		ImageView btn = (ImageView) this.getActivity().findViewById(resid);
		btn.setOnClickListener(this);
	}

    
	public void setTabFromBaseFragment(int index)
	{
		TabHeader tabHeader = (TabHeader) this.getActivity().findViewById(R.id.tabHeaderMain);
		tabHeader.setSelectedTab(index);
	}
	
	
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
    	// TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
    }
    
    public void setTabBarItemSelected(int index)
    {
    	TabHeader tabHeader = (TabHeader) this.getActivity().findViewById(R.id.tabHeaderMain);
    	tabHeader.setSelectedTab(index);
    }
    
    public void initTabs()
	{
		
		
        TabHeader tabHeader = (TabHeader) this.getActivity().findViewById(R.id.tabHeaderMain);
        
        tabHeader.setOnTabHeaderListener(new OnTabHeaderListener() 
        {	
			@Override
			public void onTabHeaderSelected(TabHeader tabHeader, View v, int position)
			{
				tabHeader.selectedIndex = position;
				
				// TODO Auto-generated method stub
				RuntimeApplication runtimeApp = new RuntimeApplication();
//						(RuntimeApplication) BaseFragment.this.getActivity().getApplication();
				
				for(int x = 0; x < tabHeader.selectionViews.length; x++)
				{
					View selectedView = tabHeader.selectionViews[x];
					ImageView imgFooterTab = (ImageView) selectedView.findViewById(R.id.imgFooterTab);
					imgFooterTab.setDrawingCacheEnabled(false);
					imgFooterTab.buildDrawingCache(false);
					imgFooterTab.destroyDrawingCache();
					imgFooterTab.setImageResource(0);
					
					TextView tvFooterTitle = (TextView) selectedView.findViewById(R.id.tvLabel5);
					int color = 0;
					imgFooterTab.postInvalidate();
					
					final FrameLayout frameTab = (FrameLayout) selectedView.findViewById(R.id.frameTab);
					frameTab.setBackgroundResource(0);
					
					
					if(position == x)
					{
						Bitmap image = BitmapFactory.decodeResource(getResources(), LayoutConfig.TAB_ICONS[x]);
						Drawable d = new BitmapDrawable(getResources(),image);
						if(runtimeApp.switcher!=null)
							ImageTint.tintImage(BaseFragment.this.getActivity(),
								d, runtimeApp.switcher.getSelectedColorSwitcher());
						
						imgFooterTab.setImageDrawable(d);
						if(runtimeApp.switcher!=null)
							color = runtimeApp.switcher.getSelectedColorSwitcher();
						else color = Color.parseColor("#ffffff");
						if(LayoutConfig.ENABLE_TAB_BAR_ITEM)
						{
							if(LayoutConfig.TAB_BAR_ITEM_SELECTED > 0)
								frameTab.setBackgroundResource(LayoutConfig.TAB_BAR_ITEM_SELECTED);
						}
						
						if(position == LayoutConfig.TAB_BAR_ITEM_CENTER_POSITION && LayoutConfig.ENABLE_TAB_BAR_ITEM_CENTER)
						{
							if(LayoutConfig.TAB_BAR_ITEM_CENTER_SELECTED > 0)
								frameTab.setBackgroundResource(LayoutConfig.TAB_BAR_ITEM_CENTER_SELECTED);
						}
					}
					else
					{
						imgFooterTab.setImageDrawable(null);
						imgFooterTab.setImageResource(LayoutConfig.TAB_ICONS[x]);
						color = R.color.text_unselected_color;
						
						if(LayoutConfig.ENABLE_TAB_BAR_ITEM)
						{
							if(LayoutConfig.TAB_BAR_ITEM_SELECTED > 0)
								frameTab.setBackgroundResource(LayoutConfig.TAB_BAR_ITEM_CENTER_NORMAL);
						}
						
						if(position == LayoutConfig.TAB_BAR_ITEM_CENTER_POSITION && LayoutConfig.ENABLE_TAB_BAR_ITEM_CENTER)
						{
							if(LayoutConfig.TAB_BAR_ITEM_CENTER_SELECTED > 0)
								frameTab.setBackgroundResource(LayoutConfig.TAB_BAR_ITEM_CENTER_NORMAL);
						}
					}
					
					TextTint.tintTextView(BaseFragment.this.getActivity(), tvFooterTitle, color);
					imgFooterTab.requestLayout();
				}
				
				setFragment(position);
			}
			
			@Override
			public void onTabHeaderCreated(final TabHeader tabHeader, final View v, final int position)
			{
				// TODO Auto-generated method stub
				RuntimeApplication runtimeApp = new RuntimeApplication();
//						(RuntimeApplication) BaseFragment.this.getActivity().getApplication();
				
				ImageView imgFooterTab = (ImageView) v.findViewById(R.id.imgFooterTab);
				imgFooterTab.setDrawingCacheEnabled(false);
				imgFooterTab.buildDrawingCache(false);
				imgFooterTab.destroyDrawingCache();
				imgFooterTab.setImageResource(0);
			
				
				TextView tvFooterTitle = (TextView) v.findViewById(R.id.tvLabel5);
				tvFooterTitle.setText(LayoutConfig.TAB_TITLES[position]);
				
				int color = 0;
				imgFooterTab.postInvalidate();
				
				if(position == 0)
				{
					imgFooterTab.setImageResource(LayoutConfig.TAB_ICONS[position]);
					if(runtimeApp.switcher!=null)
					color = runtimeApp.switcher.getSelectedColorSwitcher();
					else color = Color.parseColor("#ffffff");
				}
				else
				{
					imgFooterTab.setImageResource(LayoutConfig.TAB_ICONS[position]);
					color = BaseFragment.this.getResources().getColor(R.color.text_unselected_color);
				}
				
				tvFooterTitle.setTextColor(color);
				imgFooterTab.requestLayout();
				
				final FrameLayout frameTab = (FrameLayout) v.findViewById(R.id.frameTab);
				frameTab.setBackgroundResource(0);
				
				if(LayoutConfig.ENABLE_TAB_BAR_ITEM)
				{
//					if(LayoutConfig.TAB_BAR_ITEM_NORMAL > 0)
//						frameTab.setBackgroundResource(LayoutConfig.TAB_BAR_ITEM_NORMAL);
				}
				
				if(position == LayoutConfig.TAB_BAR_ITEM_CENTER_POSITION && LayoutConfig.ENABLE_TAB_BAR_ITEM_CENTER)
				{
					if(LayoutConfig.TAB_BAR_ITEM_CENTER_POSITION > 0)
						frameTab.setBackgroundResource(LayoutConfig.TAB_BAR_ITEM_CENTER_NORMAL);
				}
				
				
				
				
				frameTab.setTag(position);
				frameTab.setOnClickListener(new OnClickListener()
				{	
					@Override
					public void onClick(View view)
					{
						Log.e("asdasd", ""+ Integer.parseInt(frameTab.getTag().toString()));
						// TODO Auto-generated method stub
						tabHeader.mCallback.onTabHeaderSelected(tabHeader, v, Integer.parseInt(frameTab.getTag().toString()));
		
					}
				});
			}
		});
        
        tabHeader.setTabPlain(LayoutConfig.TAB_TITLES, R.layout.footer_tab_entry, LayoutConfig.FOOTER_TAB_WEIGHT);
        
        if(isSearching)
		{
			isSearching = false;
			isSearchingFromOpening = true;
			showFragmentTab1();
		}
        
        tabHeader.setSelectedTab(0);
	}
    
    @Override
	public void onClick(View v)
	{
		FrameLayout fragmentContainer = (FrameLayout) this.getActivity().findViewById(R.id.fragmentContainer);
		fragmentContainer.removeAllViews();
		
		
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			case R.id.frameFooter:
				showFragmentTab1();
				break;
				
			case R.id.frameMyCity:
				showFragmentTab2();
				break;
				
			case R.id.frameFeatured:
				showFragmentTab3();
				break;
				
			case R.id.frameMaps:
				showFragmentTab4();
				break;
				
			case R.id.frameProfile:
				showFragmentTab5();
				break;
				
			case R.id.frameSearch:
				showFragmentSearch();
				break;
			case R.id.frameList:

				break;
		}
	}
	
	public void setFragment(int pos)
	{
		switch(pos)
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
			
		case 4:
			showFragmentTab5();
			break;
		}
	}
	
	
	Tab1Fragment tab1Fragment = null;
	public void showFragmentTab1()
	{
		FragmentHelper.cleanAllPendingBackStack(this.getActivity());
		
		// Create an instance of ExampleFragment
		if(tab1Fragment == null)
			tab1Fragment = new Tab1Fragment();

        // In case this activity was started with special instructions from an Intent,
        // pass the Intent's extras to the fragment as arguments
		if(tab1Fragment == null)
			tab1Fragment.setArguments(this.getActivity().getIntent().getExtras());
		
		if(isSearchingFromOpening)
			tab1Fragment.baseFragment = this;

        FragmentTransaction transaction = this.getActivity().getSupportFragmentManager().beginTransaction();
        
        // Replace whatever is in the fragmentContainer view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragmentContainer, tab1Fragment);
        tab1Fragment.setRetainInstance(true);
//        transaction.addToBackStack(null);
        
        // adding transition
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        // Commit the transaction
        transaction.commit();
//        this.getActivity().getSupportFragmentManager().executePendingTransactions();
	}
	
	Tab2Fragment tab2Fragment = null;
	public void showFragmentTab2()
	{
		FragmentHelper.cleanAllPendingBackStack(this.getActivity());
		
		// Create an instance of ExampleFragment
		if(tab2Fragment == null)
			tab2Fragment = new Tab2Fragment();

        // In case this activity was started with special instructions from an Intent,
        // pass the Intent's extras to the fragment as arguments
		if(tab2Fragment == null)
			tab2Fragment.setArguments(this.getActivity().getIntent().getExtras());

        FragmentTransaction transaction = this.getActivity().getSupportFragmentManager().beginTransaction();
        
        // Replace whatever is in the fragmentContainer view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragmentContainer, tab2Fragment);
        tab2Fragment.setRetainInstance(true);
//        transaction.addToBackStack(null);
        
        // adding transition
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        // Commit the transaction
        transaction.commit();
	}
	
	Tab3Fragment tab3Fragment = null;
	public void showFragmentTab3()
	{
		FragmentHelper.cleanAllPendingBackStack(this.getActivity());

		// Create an instance of ExampleFragment
		if(tab3Fragment == null)
			tab3Fragment = new Tab3Fragment();

        // In case this activity was started with special instructions from an Intent,
        // pass the Intent's extras to the fragment as arguments
		if(tab3Fragment == null)
			tab3Fragment.setArguments(this.getActivity().getIntent().getExtras());

        FragmentTransaction transaction = this.getActivity().getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragmentContainer view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragmentContainer, tab3Fragment);
        tab3Fragment.setRetainInstance(true);
//        transaction.addToBackStack(null);

        // adding transition
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        // Commit the transaction
        transaction.commit();

	}
	
	Tab4Fragment tab4Fragment = null;
	public void showFragmentTab4()
	{
		FragmentHelper.cleanAllPendingBackStack(this.getActivity());
		
		// Create an instance of ExampleFragment
		if(tab4Fragment == null)
			tab4Fragment = new Tab4Fragment();

        // In case this activity was started with special instructions from an Intent,
        // pass the Intent's extras to the fragment as arguments
		if(tab4Fragment == null)
			tab4Fragment.setArguments(this.getActivity().getIntent().getExtras());

        FragmentTransaction transaction = this.getActivity().getSupportFragmentManager().beginTransaction();
        transaction.remove(tab4Fragment);
        // Replace whatever is in the fragmentContainer view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragmentContainer, tab4Fragment);
        tab4Fragment.setRetainInstance(true);
//        transaction.addToBackStack(null);
        
        // adding transition
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        // Commit the transaction
        transaction.commit();
	}
	
	Tab5Fragment tab5Fragment = null;
	public void showFragmentTab5()
	{
		FragmentHelper.cleanAllPendingBackStack(this.getActivity());
		
		// Create an instance of ExampleFragment
		if(tab5Fragment == null)
        	tab5Fragment = new Tab5Fragment();

        // In case this activity was started with special instructions from an Intent,
        // pass the Intent's extras to the fragment as arguments
		if(tab5Fragment == null)
			tab5Fragment.setArguments(this.getActivity().getIntent().getExtras());

        FragmentTransaction transaction = this.getActivity().getSupportFragmentManager().beginTransaction();
        
        // Replace whatever is in the fragmentContainer view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragmentContainer, tab5Fragment);
        tab5Fragment.setRetainInstance(true);
//        transaction.addToBackStack(null);
        
        // adding transition
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        // Commit the transaction
        transaction.commit();
	}

	SearchFragment searchFragment = null;
	public void showFragmentSearch()
	{
		FragmentHelper.cleanAllPendingBackStack(this.getActivity());
		
		// Create an instance of ExampleFragment
		if(searchFragment == null)
			searchFragment = new SearchFragment();

        // In case this activity was started with special instructions from an Intent,
        // pass the Intent's extras to the fragment as arguments
		if(searchFragment == null)
			searchFragment.setArguments(this.getActivity().getIntent().getExtras());

		FragmentTransaction transaction = this.getActivity().getSupportFragmentManager().beginTransaction();
        
        // Replace whatever is in the fragmentContainer view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragmentContainer, searchFragment);
        searchFragment.setRetainInstance(true);
        transaction.addToBackStack("searchFragment");
        
        // adding transition
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        // Commit the transaction
        transaction.commit();
	}
	
//	OpeningFragment openingFragment = null;
//	public void showOpeningFragment()
//	{
//
//		// Create an instance of ExampleFragment
//		if(openingFragment == null)
//			openingFragment = new OpeningFragment();
//
//        // In case this activity was started with special instructions from an Intent,
//        // pass the Intent's extras to the fragment as arguments
//		if(openingFragment == null)
//			openingFragment.setArguments(this.getActivity().getIntent().getExtras());
//
//        FragmentTransaction transaction = this.getActivity().getSupportFragmentManager().beginTransaction();
//
//        // Replace whatever is in the fragmentContainer view with this fragment,
//        // and add the transaction to the back stack so the user can navigate back
//        transaction.replace(R.id.fragmentMainContainerWrapper, openingFragment);
//        openingFragment.setRetainInstance(true);
//
//        // adding transition
//        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//
//        // Commit the transaction
//        transaction.commit();
//	}
	
	
	public void showTab1FragmentInSearch(HashMap<String, String> searchHashMap)
	{
		FragmentHelper.cleanBackStack(this.getActivity());
		
		// Create an instance of ExampleFragment
		if(tab1Fragment == null)
			tab1Fragment = new Tab1Fragment();

        // In case this activity was started with special instructions from an Intent,
        // pass the Intent's extras to the fragment as arguments
//        detailFragment.setArguments(fragmentActivity.getIntent().getExtras());
        tab1Fragment.searchTag = 1;
        tab1Fragment.searchHashMap = searchHashMap;
        
        FragmentTransaction transaction = this.getActivity().getSupportFragmentManager().beginTransaction();
        
        // Replace whatever is in the fragmentContainer view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragmentContainer, tab1Fragment);
//        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
        
	}

}
