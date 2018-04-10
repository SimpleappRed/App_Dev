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


package com.example.nidheesha.realestate.helper.image;

import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class TextTint 
{
	public static TextView tintTextView(Activity act, TextView textView, int residColor)
	{try {
		int tint = act.getColor(residColor);
		textView.setTextColor(tint);
	}catch (Resources.NotFoundException e){
		Log.d("tag","resource notfound");
	}
		return textView;
	}
	
	public static TextView tintButtonText(Activity act, Button button, int residColor)
	{try {
		int tint = act.getColor(residColor);
		button.setTextColor(tint);
	}
	catch (Resources.NotFoundException e){
		Log.d("tag","dkahfaifohanf");
	}
		return button;
	}
}
