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


package com.example.nidheesha.realestate.switcher;

import android.content.Context;

import com.example.nidheesha.realestate.R;

public class ColorSwitcher 
{
	int selectedColor;
	String unParsedColor = "";
	
	public ColorSwitcher(String color, Context c)
	{
		unParsedColor = color;
		
		if(color.compareToIgnoreCase("YELLOW") == 0)
		{
			selectedColor = R.color.color_switcher_yellow;
		}
		else if(color.compareToIgnoreCase("PINK") == 0)
		{
			selectedColor = R.color.color_switcher_pink;
		}
		else if(color.compareToIgnoreCase("RED") == 0)
		{
			selectedColor = R.color.color_switcher_red;
		}
		else if(color.compareToIgnoreCase("GREEN") == 0)
		{
			selectedColor = R.color.color_switcher_green;
		}
		else if(color.compareToIgnoreCase("BLUE") == 0)
		{
			selectedColor = R.color.color_switcher_blue;
		}
		else if(color.compareToIgnoreCase("MyCustomColor") == 0)
		{
			selectedColor = R.color.color_switcher_my_custom_color;
		}
		else
		{
			selectedColor = R.color.color_switcher_default; 
		}
	}
	
	public int getSelectedColorSwitcher()
	{
		return selectedColor;
	}

}
