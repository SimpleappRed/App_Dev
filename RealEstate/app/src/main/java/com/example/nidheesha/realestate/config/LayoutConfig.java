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


package com.example.nidheesha.realestate.config;

import com.example.nidheesha.realestate.components.tabheader.TabSelection;
import com.example.nidheesha.realestate.R;


public class LayoutConfig 
{	
	public static int NAVIGATION_BAR_ITEM_LEFT_SELECTOR = R.drawable.header_buton_left;
	
	public static int NAVIGATION_BAR_ITEM_RIGHT_SELECTOR = -1;
	
	
	public static int NAVIGATION_BAR_ITEM_LEFT_ICON = 0;
	
	public static int NAVIGATION_BAR_ITEM_RIGHT_ICON = R.drawable.headericonsearch;
	
	

	public static Boolean SHOW_NAVIGATION_BAR_ITEM_LEFT = true;
	
	public static Boolean SHOW_NAVIGATION_BAR_ITEM_RIGHT = true;
	
	
	
	public static Boolean ENABLE_TAB_BAR_ITEM_CENTER = false;
	
	public static Boolean ENABLE_TAB_BAR_ITEM = false;
	
	public static int TAB_BAR_ITEM_SELECTED = R.drawable.footerbuttonselected;
	
	public static int TAB_BAR_ITEM_NORMAL = 0;
	
	
	public static int TAB_BAR_ITEM_CENTER_POSITION = 2;
	
	public static int TAB_BAR_ITEM_CENTER_NORMAL = 0;
	
	public static int TAB_BAR_ITEM_CENTER_SELECTED = 0;	
	
	public static int INNER_TAB_WEIGHT = 1;
	
	public static int INNER_TAB_WEIGHT_DETAIL = 1;
	
	public static int FOOTER_TAB_WEIGHT = 1;
	
	public static int SLIDER_DOT_DOWN = R.drawable.sliderdotup;

	public static int SLIDER_DOT_UP = R.drawable.sliderdotdown;
	
	public static TabSelection[] INNER_TAB_BACKGROUND =
	{
		new TabSelection(R.drawable.tabfilterdown, R.drawable.tabfilterup),
		new TabSelection(R.drawable.tabfilterdown, R.drawable.tabfilterup),
		new TabSelection(R.drawable.tabfilterdown, R.drawable.tabfilterup)
	};
	
	
	public static TabSelection[] INNER_TAB_BACKGROUND_DETAIL =
	{
		new TabSelection(R.drawable.tabfilterdown, R.drawable.tabfilterup),
		new TabSelection(R.drawable.tabfilterdown, R.drawable.tabfilterup),
		new TabSelection(R.drawable.tabfilterdown, R.drawable.tabfilterup),
		new TabSelection(R.drawable.tabfilterdown, R.drawable.tabfilterup)
	};
	
	
	public static String[] TAB_TITLES = {"Top", "My City stats", "Featured", "Map", "Contact"};
	public static Integer[] TAB_ICONS =
		{
			R.drawable.footericonapartment, 
			R.drawable.footericonmycity, 
			R.drawable.footericonfeatured, 
			R.drawable.footericonmap, 
			R.drawable.footericoncontact
		};
}
