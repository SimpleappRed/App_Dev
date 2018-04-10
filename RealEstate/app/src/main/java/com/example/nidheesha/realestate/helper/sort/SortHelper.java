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


package com.example.nidheesha.realestate.helper.sort;

import com.example.nidheesha.realestate.models.ListEntry;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortHelper 
{
	public static void sort(List<ListEntry> entryList, final String descriptor)
	{
		if (entryList.size() > 0) 
		{
		    Collections.sort(entryList, new Comparator<ListEntry>()
		    {
		    	@Override
				public int compare(ListEntry entry1, ListEntry entry2) 
				{
					// TODO Auto-generated method stub
		    		if(descriptor.compareTo("title") == 0)
		    		{
		    			return entry1.title.compareToIgnoreCase(entry2.title);
		    		}
		    		
		    		if(descriptor.compareTo("price") == 0)
		    		{
		    		
		    			String strPrice1 = entry1.price.replace(",", "");
		    			strPrice1 = strPrice1.replace(" ", "");
		    			strPrice1 = strPrice1.replace("$", "");
		    			
		    			String strPrice2 = entry2.price.replace(",", "");
		    			strPrice2 = strPrice2.replace(" ", "");
		    			strPrice2 = strPrice2.replace("$", "");
		    			
		    			float price1 = Float.parseFloat(strPrice1);
		    			float price2 = Float.parseFloat(strPrice2);
		    			
		    			if(price1 < price2)
		    				return -1;
		    			
		    			if(price1 > price2)
		    				return 1;
		    		}
		    		
		    		return 0;
				}
					
    		} );
		}
	}
}
