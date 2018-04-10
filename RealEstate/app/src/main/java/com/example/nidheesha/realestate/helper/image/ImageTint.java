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
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class ImageTint 
{	
	public static Drawable tintImage(Activity act, int resid, int residColor)
	{
		Drawable image = act.getResources().getDrawable(resid);
		int tint = act.getResources().getColor(residColor);
		ColorFilter filter = new PorterDuffColorFilter(tint, PorterDuff.Mode.SRC_ATOP);
		image.setColorFilter(filter);
		
		return image;
	}
	
	public static Drawable tintImage(Activity act, Drawable image, int residColor)
	{
//		int tint = act.getResources().getColor(residColor);
		int tint;
		try{
		 tint = act.getColor(residColor);
			ColorFilter filter = new PorterDuffColorFilter(tint, PorterDuff.Mode.SRC_ATOP);
			image.setColorFilter(filter);}
		catch (Resources.NotFoundException e){
			Log.d("TAG", "tintImage: akj");
		}

		
		return image;
	}

//	public static Drawable tintImage(Activity act, Drawable image, String colorHex)
//	{
//		ColorFilter cf = new PorterDuffColorFilter(Color
//                .parseColor(colorHex), Mode.MULTIPLY);
//		
//		image.setColorFilter(cf);
//		
//		return image;
//	}
	
//	public static TextView tintTextView(Activity act, TextView textView, String colorHex)
//	{
//		textView.setTextColor( Color.parseColor( colorHex ) );
//		
//		return textView;
//	}
//	
//	public static int getSelectedColor(Activity act, String colorHex)
//	{	
//		int tint = Color.parseColor( colorHex );
//		
//		return tint;
//	}
}
