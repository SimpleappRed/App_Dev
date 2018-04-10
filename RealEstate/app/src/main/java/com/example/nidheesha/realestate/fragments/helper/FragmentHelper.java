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


package com.example.nidheesha.realestate.fragments.helper;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.nidheesha.realestate.fragments.AgentFragment;
import com.example.nidheesha.realestate.fragments.BaseFragment;
import com.example.nidheesha.realestate.fragments.DetailFragment;
import com.example.nidheesha.realestate.fragments.ImageViewerFragment;
import com.example.nidheesha.realestate.fragments.Tab1Fragment;
import com.example.nidheesha.realestate.models.ListEntry;
import com.example.nidheesha.realestate.R;

import java.util.HashMap;

public class FragmentHelper 
{
	/* -------------------------------------------------------------------------------------
     * Detail Fragment
     * -------------------------------------------------------------------------------------
     */
	public static void showDetailFragment(ListEntry listEntry, FragmentActivity fragmentActivity)
	{
		// Create an instance of ExampleFragment
        DetailFragment detailFragment = new DetailFragment();

        // In case this activity was started with special instructions from an Intent,
        // pass the Intent's extras to the fragment as arguments
        detailFragment.setArguments(fragmentActivity.getIntent().getExtras());
        detailFragment.setListEntry(listEntry);
        
        FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        
        // Replace whatever is in the fragmentContainer view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragmentContainer, detailFragment);
        transaction.addToBackStack("detailFragment");

        // Commit the transaction
        transaction.commit();
	}
	
	/* -------------------------------------------------------------------------------------
     * Image Viewer Fragment
     * -------------------------------------------------------------------------------------
     */
	public static void showImageViewerFragment(ListEntry listEntry, FragmentActivity fragmentActivity, int position)
	{
		
		// Create an instance of ExampleFragment
        ImageViewerFragment detailFragment = new ImageViewerFragment();

        // In case this activity was started with special instructions from an Intent,
        // pass the Intent's extras to the fragment as arguments
        detailFragment.setArguments(fragmentActivity.getIntent().getExtras());
        detailFragment.setListEntry(listEntry, position);
        
        FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        
        // Replace whatever is in the fragmentContainer view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragmentContainer, detailFragment);
        transaction.addToBackStack("imageViewerFragment");

        // Commit the transaction
        transaction.commit();
	}
	
	/* -------------------------------------------------------------------------------------
     * Detail Fragment
     * -------------------------------------------------------------------------------------
     */
	public static void showTab1FragmentInSearch(HashMap<String, String> searchHashMap, FragmentActivity fragmentActivity)
	{
		FragmentHelper.cleanBackStack(fragmentActivity);
		
		// Create an instance of ExampleFragment
        Tab1Fragment tab1Fragment = new Tab1Fragment();

        // In case this activity was started with special instructions from an Intent,
        // pass the Intent's extras to the fragment as arguments
//        detailFragment.setArguments(fragmentActivity.getIntent().getExtras());
        tab1Fragment.searchTag = 1;
        tab1Fragment.searchHashMap = searchHashMap;
//        detailFragment.setSearchData(searchHashMap);
        
        FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        
        // Replace whatever is in the fragmentContainer view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragmentContainer, tab1Fragment);
//        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
        
	}
	
	/* -------------------------------------------------------------------------------------
     * Detail Fragment
     * -------------------------------------------------------------------------------------
     */
	public static void showTab1Fragment( FragmentActivity fragmentActivity)
	{
		// Create an instance of ExampleFragment
        Tab1Fragment detailFragment = new Tab1Fragment();

        // In case this activity was started with special instructions from an Intent,
        // pass the Intent's extras to the fragment as arguments
//        detailFragment.setArguments(fragmentActivity.getIntent().getExtras());
        
        FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        
        // Replace whatever is in the fragmentContainer view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragmentContainer, detailFragment);
//        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
        
	}
	
	/* -------------------------------------------------------------------------------------
     * Detail Fragment
     * -------------------------------------------------------------------------------------
     */
	public static void showAgentFragment(ListEntry listEntry, FragmentActivity fragmentActivity)
	{
		// Create an instance of ExampleFragment
        AgentFragment detailFragment = new AgentFragment();

        // In case this activity was started with special instructions from an Intent,
        // pass the Intent's extras to the fragment as arguments
        detailFragment.setArguments(fragmentActivity.getIntent().getExtras());
        detailFragment.setAgentData(listEntry);
        
        FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        
        // Replace whatever is in the fragmentContainer view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragmentContainer, detailFragment);
        transaction.addToBackStack("agentFragment");

        // Commit the transaction
        transaction.commit();
	}
	
	
	public static void showBaseFragment(FragmentActivity fragmentActivity)
	{
		cleanBackStack(fragmentActivity);
		// Create an instance of ExampleFragment
        BaseFragment detailFragment = new BaseFragment();

        // In case this activity was started with special instructions from an Intent,
        // pass the Intent's extras to the fragment as arguments
//        detailFragment.setArguments(fragmentActivity.getIntent().getExtras());
//        detailFragment.setSearchData(searchHashMap);
        
        FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        
        // Replace whatever is in the fragmentContainer view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragmentContainer, detailFragment);
//        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
        fragmentActivity.getSupportFragmentManager().executePendingTransactions();
	}
	
	public static void cleanBackStack(FragmentActivity fragmentActivity)
	{
		FragmentManager fm = fragmentActivity.getSupportFragmentManager();
		for(int i = 0; i < fm.getBackStackEntryCount(); ++i) 
		{    
		    fm.popBackStack();
		}
		
	}
	
	public static void cleanAllPendingBackStack(FragmentActivity fragmentActivity)
	{
		FragmentManager fm = fragmentActivity.getSupportFragmentManager();
		fm.popBackStack("searchFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
		fm.popBackStack("agentFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
		fm.popBackStack("detailFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
		fm.popBackStack("imageViewerFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
	}
}
