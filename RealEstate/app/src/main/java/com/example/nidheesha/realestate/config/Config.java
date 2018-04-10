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

import com.example.nidheesha.realestate.R;

public class Config 
{
	/*
	 * path to be used for server data
	 */
	public final static String URL_PATH = "http://labs.philippineglobaloutsourcing.com/apartmentpro/datav2/";
	
	/*
	 * path to be used for local data, where xml files are stored
	 */
	public final static String ASSETS_FOLDER_PATH = "xml/";
	
	/*
	 * specify filename for the apartments xml
	 */
	public final static String APARTMENTS_URL = "apartments.xml";
	
	/*
	 * specify filename for the city xml
	 */
	public final static String CITY_URL = "cities.xml";
	
	/*
	 * specify filename for the slider xml
	 */
	public final static String SLIDER_URL = "slider.xml";
	
	/*
	 * specify filename for the profiles xml
	 */
	public final static String PROFILE_URL = "profiles.xml";
	
	/*
	 * specify filename for the agents xml
	 */
	public final static String AGENTS_URL = "agencies.xml";
	
	/*
	 * specify filename for the agents xml
	 */
	public final static String REVIEWS_URL = "reviews.xml";
	
	/*
	 * specify filename for the agents xml
	 */
	public final static String PHOTOS_URL = "photos.xml";
	
	/*
	 * specify color used to theme the application,
	 * YELLOW, BLUE, RED, PINK, GREEN
	 * Ignores case sensitivity
	 */
	public final static String THEME_COLOR = "YELLOW";
	
	/*
	 * if data from server set it to true, 
	 * if data from local set to false
	 */
	public final static Boolean IS_DATA_FROM_SERVER = true;
	
	/*
	 * set true if opening view is shown, false if not.
	 */
	public final static Boolean IS_OPENING_VIEW_SHOWN = true;
	
	/*
	 * placeholder used for thumbs in the list as default if the image failed to load
	 */
	public static int THUMB_PLACE_HOLDER = R.drawable.list_thumb50x50_001; 
	
	
	/*
	 * placeholder used for images that fail to load in image viewer
	 */
	public static int IMAGE_VIEWER_PLACEHOLDER = R.drawable.no_photo; 
	
	
	/*
	 * cache color for list view when scrolled
	 */
	public final static int CACHE_COLOR_HINT = 0x00000000;
	
	
	/*
	 * currency to be used in displaying prices in the application
	 */
	public final static String CURRENCY = "$";
	
	
	/*
	 * map radius for computing the near appartments in meters
	 */
	public final static int MAP_RADIUS = 700;
	
	
	/*
	 * set map zoom level
	 */
	public final static int MAP_ZOOM_LEVEL = 14;
	
	
	/*
	 * border thickness for the thumbnails
	 */
	public final static int BORDER_THICKNESS_THUMB = 5;
	
	
	/*
	 * border radius for
	 */
	public final static int BORDER_RADIUS = 90;

	/*
	 * max slider thumb to be shown
	 */
	public final static int MAX_SLIDER_THUMB = 3;
	
	
	/*
	 * spinner items for prices, allowed characters are "-", NUMBERS and ","
	 */
	public final static String[] SEARCH_PRICE =
	{
		"-",
		CURRENCY +" 1",
		CURRENCY +" 50",
		CURRENCY +" 100",
		CURRENCY +" 500",
		CURRENCY +" 1,000",
		CURRENCY +" 5,000",
		CURRENCY +" 10,000",
		CURRENCY +" 50,000",
		CURRENCY +" 100,000",
		CURRENCY +" 500,000",
		CURRENCY +" 1,000,000",
	};
	
	
	/*
	 * spinner items for number of bathrooms. NUMBER ONLY and "-" which indicates no selection
	 */
	public final static String[] SEARCH_NO_OF_BATHROOMS =
	{
		"-",
		"1",
		"2",
		"3",
		"4",
		"5",
		"6",
		"7",
		"8",
		"9",
		"10"
	};
	
	
	/*
	 * spinner items for number of bedrooms. NUMBER ONLY and "-" which indicates no selection
	 */
	public final static String[] SEARCH_NO_OF_BEDROOMS =
	{
		"-",
		"1",
		"2",
		"3",
		"4",
		"5",
		"6",
		"7",
		"8",
		"9",
		"10"
	};
	
	
	/*
	 * spinner items for apartment types
	 */
	public final static String[] SEARCH_APARTMENT_TYPE =
	{
		"All Homes",
		"Foreclosures",
		"New Homes",
		"Rentals",
		"Condominium"
	};
}
